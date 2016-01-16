package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by aravindkoneru on 10/28/15.
 */
//TODO: Left and right Encoders are switched
public class OpModeHelperClean extends OpMode {


    //left drive motors
    DcMotor frontLeft,
            backLeft;

    //right drive motors
    DcMotor frontRight,
            backRight;

    //arm motors
    DcMotor armMotor1,
            armMotor2,
            armPivot,
            prop;

    //zipliner servo
    Servo zipLinerR,
            ziplinerL;

    //target encoder position
    private int rightTarget,
            leftTarget;

    //SERVO CONSTANTS
    private final double
            SERVO_MAX = 1,
            SERVO_MIN = -1;

    //MOTOR RANGES
    private final double
            MOTOR_MAX = 1,
            MOTOR_MIN = - 1;

    //ENCODER CONSTANTS TODO: Calibrate all of these values
    private final double
            CIRCUMFERENCE_INCHES = 39.5,
            TICKS_PER_ROTATION = 3193,
            TICKS_PER_INCH = TICKS_PER_ROTATION / CIRCUMFERENCE_INCHES,
            TOLERANCE = 5,
            ROBOT_WIDTH = 15.5;

    private boolean servoL = false;

    public void init() {
        //drive motors
        frontLeft = hardwareMap.dcMotor.get("l1");
        backLeft = hardwareMap.dcMotor.get("l2");

        frontRight = hardwareMap.dcMotor.get("r1");
        backRight = hardwareMap.dcMotor.get("r2");

        //pivot motor
        armPivot = hardwareMap.dcMotor.get("arm");

        //tape measure arms
        armMotor1 = hardwareMap.dcMotor.get("tm1");
        armMotor2 = hardwareMap.dcMotor.get("tm2");

        //zip line motors
        zipLinerR = hardwareMap.servo.get("zipR");
        ziplinerL = hardwareMap.servo.get("zipL");

        //Propeller / sweeper
        prop = hardwareMap.dcMotor.get("Propeller");

        setDirection(); //ensures the proper motor directions

        resetDriveEncoders(); //ensures that the encoders have reset
    }

    //sets the proper direction for the motors
    public void setDirection() {
        //drive motors
        if (frontLeft.getDirection() == DcMotor.Direction.REVERSE) {
            frontLeft.setDirection(DcMotor.Direction.FORWARD);
        }

        if (backLeft.getDirection() == DcMotor.Direction.FORWARD) {
            backLeft.setDirection(DcMotor.Direction.REVERSE);
        }

        if (frontRight.getDirection() == DcMotor.Direction.REVERSE) {
            frontRight.setDirection(DcMotor.Direction.FORWARD);
        }

        if (backRight.getDirection() == DcMotor.Direction.REVERSE) {
            backRight.setDirection(DcMotor.Direction.FORWARD);
        }

        //arm motor
        if(armMotor1.getDirection() == DcMotor.Direction.FORWARD){
            armMotor1.setDirection(DcMotor.Direction.REVERSE);
        }
    }


    //ENCODER MANIPULATION
    public boolean resetDriveEncoders() {
        frontLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        backLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        frontRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        backRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        return (frontLeft.getCurrentPosition() == 0 &&
                backLeft.getCurrentPosition() == 0 &&
                frontRight.getCurrentPosition() == 0 &&
                backRight.getCurrentPosition() == 0);
    }

    public void setToEncoderMode() {
        frontLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        frontRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
    }

    public void setToWOEncoderMode() {
        frontLeft.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        backLeft.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

        frontRight.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        backRight.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
    }


    //ENCODER MOVEMENT
    public boolean runStraight(double distance_in_inches, boolean fast) {//Sets values for driving straight, and indicates completion
        leftTarget = (int) (distance_in_inches * TICKS_PER_INCH);
        rightTarget =  leftTarget;
        setTargetValueMotor();

        if(fast) {
            setMotorPower(.8, .8);
        } else{
            setMotorPower(.2,.2);
        }

        if (hasReached()) {
            setMotorPower(0, 0);
            return true;//done traveling
        }
        return false;
    }

    public boolean setTargetValueTurn(double degrees) {
        int encoderTarget = (int) (degrees/360*Math.PI*ROBOT_WIDTH*TICKS_PER_INCH);     //theta/360*PI*D
        leftTarget = encoderTarget;
        rightTarget = -encoderTarget;

        setTargetValueMotor();
        setMotorPower(.7, .7);

        if (hasReached()) {
            setMotorPower(0, 0);
            return true;//done traveling
        }
        return false;
    }

    public void setTargetValueMotor() {
        frontLeft.setTargetPosition(leftTarget);
        backLeft.setTargetPosition(leftTarget);

        frontRight.setTargetPosition(rightTarget);
        backRight.setTargetPosition(rightTarget);
    }

    public boolean hasReached() {
        return (Math.abs(frontLeft.getCurrentPosition() - leftTarget) <= TOLERANCE ||
                Math.abs(backLeft.getCurrentPosition() - leftTarget) <= TOLERANCE ||
                Math.abs(frontRight.getCurrentPosition() - rightTarget) <= TOLERANCE ||
                Math.abs(backRight.getCurrentPosition() - rightTarget) <= TOLERANCE);
    }

    //DRIVER CONTROLS
    public void manualDrive(boolean turtleMode) {
        setToWOEncoderMode();

        double rightPower = gamepad1.right_stick_y;
        double leftPower = gamepad1.left_stick_y;

        if(turtleMode){
            setMotorPower(rightPower*.5, leftPower*.5);
        } else{
            setMotorPower(rightPower, leftPower);
        }
    }

    public void setMotorPower(double leftPower, double rightPower) {//only accepts clipped values
        clipValues(leftPower, ComponentType.MOTOR);
        clipValues(rightPower, ComponentType.MOTOR);

        frontLeft.setPower(leftPower);
        backLeft.setPower(leftPower);

        frontRight.setPower(rightPower);
        backRight.setPower(rightPower);
    }


    //OPERATOR CONTROLS
    public void activateLeft(boolean trigger){
        if(trigger){
            ziplinerL.setPosition(0);
        } else{
            ziplinerL.setPosition(1);
        }
    }

    public void activateRight(boolean trigger){
        if(trigger){
            zipLinerR.setPosition(0);
        } else{
            zipLinerR.setPosition(1);
        }
    }

    public void setArmPivot(double power) {
        armPivot.setPower(clipValues(power, ComponentType.MOTOR));
    }

    public void moveTapeMeasure(double power){
        armMotor2.setPower(power);
        armMotor1.setPower(power);
    }

    public void movePropeller(int dir){
        if(dir == -1){
            prop.setPower(-1);
        } if(dir == 1){
            prop.setPower(1);
        } else{
            prop.setPower(0);
        }
    }


    //AUTON OPERATOR HELPER
    public void setZipLinerL (double pos) {
        ziplinerL.setPosition(clipValues(pos, ComponentType.SERVO));
    }

    public void setZipLinerR (double pos) {
        zipLinerR.setPosition(clipValues(pos, ComponentType.SERVO));
    }


    //HELPER METHODS
    enum ComponentType {
        NONE,
        MOTOR,
        SERVO
    }

    public double clipValues(double initialValue, ComponentType type) {
        double finalval = 0;
        if (type == ComponentType.MOTOR)
            finalval = Range.clip(initialValue, MOTOR_MIN, MOTOR_MAX);
        if (type == ComponentType.SERVO)
            finalval = Range.clip(initialValue, SERVO_MIN, SERVO_MAX);
        return finalval;
    }


    //DEBUG
    public void basicTel() {
        telemetry.addData("01 frontLeftPos: ", frontLeft.getCurrentPosition());
        telemetry.addData("02 backLeftPos: ", backLeft.getCurrentPosition());
        telemetry.addData("03 LeftTarget: ", leftTarget);

        telemetry.addData("04 frontRightPos: ", frontRight.getCurrentPosition());
        telemetry.addData("05 backRightPos: ", backRight.getCurrentPosition());
        telemetry.addData("06 RightTarget: ", rightTarget);

        int tapeDirection = 0;
        if(armMotor1.getPower() > 0 && armMotor2.getPower() > 0){
            tapeDirection = 1;
        } else if (armMotor1.getPower() < 0 && armMotor2.getPower() < 0){
            tapeDirection = -1;
        }
        telemetry.addData("07 Tape Measure: ", tapeDirection);

        int pivotPos = 0;
        if(armPivot.getPower() < 0){
            pivotPos = 1;
        } else if(armPivot.getPower() > 0){
            pivotPos = -1;
        }
        telemetry.addData("08 Arm Pivot: ", pivotPos);

        String activeZipLine = "zipR";
        if(servoL){
            activeZipLine = "zipL";
        }
        telemetry.addData("09 Active Zip Line", activeZipLine);

        telemetry.addData("010 Propeller Encoders: ", prop.getCurrentPosition());
    }

    private int turn=0,
            targetPos;

    public boolean resetProp(){
        prop.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        int currentPos = prop.getCurrentPosition();
        if (turn==0) {
            if (targetPos==0) {
                targetPos = currentPos + (280-(currentPos % 280));
            }
            prop.setTargetPosition(targetPos);
            prop.setPower(.4);
            if (targetPos - currentPos <= 6) {
                resetPropellerEncoder();
                prop.setPower(0);
                turn = 1;
                return true;
            } else return false;
        } else return false;
    }

    public void resetPropellerEncoder(){
        prop.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        prop.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
    }

    public OpModeHelperClean(){}

    public void loop() {}

    @Override
    public void stop() {
        setMotorPower(0, 0);//brake the drive motors
        moveTapeMeasure(.8);//brake the measuring tape motors
        setArmPivot(0);//brake the pivot arm
    }

}

