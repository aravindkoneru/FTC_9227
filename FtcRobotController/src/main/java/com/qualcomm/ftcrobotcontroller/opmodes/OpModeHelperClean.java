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
            armPivot;

    //zipliner servo
    Servo zipLinerR,
            ziplinerL,
            shitter;


    //target encoder position
    private int rightTarget,
            leftTarget;

    //SERVO CONSTANTS
    private final double
            SERVO_MAX = 1,
            SERVO_MIN = - 1,
            SERVO_NEUTRAL = 9.0 / 17;      //Stops the continuous servo

    //MOTOR RANGES
    private final double
            MOTOR_MAX = 1,
            MOTOR_MIN = - 1;

    //ENCODER CONSTANTS TODO: Calibrate all of these values
    private final double
            CIRCUMFERENCE_INCHES = 39.5,
            TICKS_PER_ROTATION = 3193,
            TICKS_PER_INCH = TICKS_PER_ROTATION / CIRCUMFERENCE_INCHES,
            TOLERANCE = 10;

    //ROBOT DIMENSIONS
    private final double   //TODO: Measure these distances for 9927
            ROBOT_WIDTH = 15.5,           // Width between centerline of wheels
            ROBOT_WHEEL_DISTANCE = 14;  // Distance between axles

    private boolean servoR = false,
            servoL = false;

    public OpModeHelperClean() {

    }


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

        zipLinerR = hardwareMap.servo.get("zipR");
        ziplinerL = hardwareMap.servo.get("zipL");

        // shitter = hardwareMap.servo.get("dump");


        setDirection(); //ensures the proper motor directions

        resetEncoders(); //ensures that the encoders have reset

        //dropShit(0);//make the shitter servo netural
    }


    //sets the proper direction for the motors
    public void setDirection() {
        //drive motors
        if (frontLeft.getDirection() == DcMotor.Direction.REVERSE) { //Front Left = Front Right
            frontLeft.setDirection(DcMotor.Direction.FORWARD);
        }
        if (backLeft.getDirection() == DcMotor.Direction.FORWARD) {
            backLeft.setDirection(DcMotor.Direction.REVERSE);
        }

        if (frontRight.getDirection() == DcMotor.Direction.REVERSE) {
            frontRight.setDirection(DcMotor.Direction.FORWARD);
        }

        if (backRight.getDirection() == DcMotor.Direction.FORWARD) {
            backRight.setDirection(DcMotor.Direction.REVERSE);
        }

        //arm motor
        if(armMotor1.getDirection() == DcMotor.Direction.FORWARD){
            armMotor1.setDirection(DcMotor.Direction.REVERSE);
        }
    }




    //reset drive encoders and return true when everything is at 0
    public boolean resetEncoders() {
        frontLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        backLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        frontRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        backRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        return (frontLeft.getCurrentPosition() == 0 &&
                backLeft.getCurrentPosition() == 0 &&
                frontRight.getCurrentPosition() == 0 &&
                backRight.getCurrentPosition() == 0);

    }

    //sets drive motors to encoder mode
    public void setToEncoderMode() {

        frontLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        frontRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
    }

    //sets drive motors to run without encoders and use power
    public void setToWOEncoderMode() {
        frontLeft.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        backLeft.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

        frontRight.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        backRight.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
    }




    //encoder drive to go straight
    public boolean runStraight(double distance_in_inches, boolean speed) {//Sets values for driving straight, and indicates completion
        leftTarget = (int) (distance_in_inches * TICKS_PER_INCH);
        rightTarget =  leftTarget;
        setTargetValueMotor();

        if(speed) {
            setMotorPower(.8, .8);//TODO: Stalling factor that Libby brought up; check for adequate power
        } else{
            setMotorPower(.2,.2);
        }


        if (hasReached()) {
            setMotorPower(0, 0);
            return true;//done traveling
        }
        return false;
    }

    //TODO: Run tests to determine the relationship between degrees turned and ticks
    public boolean setTargetValueTurn(double degrees) {

        int encoderTarget = (int) (degrees/360*Math.PI*ROBOT_WIDTH*TICKS_PER_INCH);     //theta/360*PI*D
        leftTarget = encoderTarget;
        rightTarget = -encoderTarget;

        setTargetValueMotor();
        setMotorPower(.4, .4);//TODO: Stalling factor that Libby brought up; check for adequate power

        if (hasReached()) {
            setMotorPower(0, 0);
            return true;//done traveling
        }
        return false;
    }

    //sets the encoder target position of drive motors
    public void setTargetValueMotor() {
        frontLeft.setTargetPosition(leftTarget);
        backLeft.setTargetPosition(leftTarget);

        frontRight.setTargetPosition(rightTarget);
        backRight.setTargetPosition(rightTarget);
    }

    //checks to see if the encoders are at the target within reason
    public boolean hasReached() {
        return (Math.abs(frontLeft.getCurrentPosition() - leftTarget) <= TOLERANCE &&
                Math.abs(backLeft.getCurrentPosition() - leftTarget) <= TOLERANCE &&
                Math.abs(frontRight.getCurrentPosition() - rightTarget) <= TOLERANCE &&
                Math.abs(backRight.getCurrentPosition() - rightTarget) <= TOLERANCE);
    }

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

    //Should only be used for Autons
    public void setZipLinerL (double pos)
    {
        ziplinerL.setPosition(clipValues(pos, ComponentType.SERVO));
    }

    //Should only be used for Autons
    public void setZipLinerR (double pos)
    {
        zipLinerR.setPosition(clipValues(pos,ComponentType.SERVO ));
    }




    //TODO: Calibrate this motor for the arm
    public void setArmPivot(double power) {
        armPivot.setPower(clipValues(power, ComponentType.MOTOR));
    }




    //if true, then do turtle mode, otherwise, drive normally
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
        backRight.setPower(-rightPower);
    }


    public void moveTapeMeasure(double power){
        armMotor2.setPower(power);
        armMotor1.setPower(power);
    }


    public void loop() {
    }




    enum ComponentType {//helps with clipValues
        NONE,
        MOTOR,
        SERVO
    }

    //clips values so that they are within the range of the different compoenents
    public double clipValues(double initialValue, ComponentType type) {
        double finalval = 0;
        if (type == ComponentType.MOTOR)
            finalval = Range.clip(initialValue, MOTOR_MIN, MOTOR_MAX);
        if (type == ComponentType.SERVO)
            finalval = Range.clip(initialValue, SERVO_MIN, SERVO_MAX);
        return finalval;
    }

    public void dropShit(double flag){
        if(flag == 1)
            shitter.setPosition(1);

        if(flag == -1)
            shitter.setPosition(0);

        if(flag == 0)
            shitter.setPosition(SERVO_NEUTRAL);
    }

    //simple debugging and info
    public void basicTel() {
        telemetry.addData("00 tester: ", frontLeft.getConnectionInfo());

        telemetry.addData("LeftTarget", leftTarget);
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

        //telemetry.addData("10 Shitter", shitter.getPosition());
    }

    public boolean encoderDrive(int target){
        leftTarget = target;
        rightTarget = target;
        targetOne();
        setMotorPower(.4, .4);
        if (hasReached()) {
            setMotorPower(0, 0);
            return true;//done traveling
        }
        return false;
    }




    public void targetOne(){
        frontLeft.setTargetPosition(leftTarget);
        frontRight.setTargetPosition(rightTarget);
    }


    public boolean tankEncoders(int position){
        leftTarget = position;
        rightTarget = position;

        targetOne();
        setMotorPower(.4,.4);

        if(hasReached()){
            setMotorPower(0,0);
            return true;
        }
        return false;
    }

    public boolean oneReach(){
        return (Math.abs(frontLeft.getCurrentPosition() - leftTarget) < 30 &&
                Math.abs(frontRight.getCurrentPosition() - rightTarget) < 30);
    }

    public void stop(){
        setMotorPower(0, 0);//brake the drive motors
        moveTapeMeasure(.8);//brake the measuring tape motors
        setArmPivot(0);//brake the pivot arm
    }

}
