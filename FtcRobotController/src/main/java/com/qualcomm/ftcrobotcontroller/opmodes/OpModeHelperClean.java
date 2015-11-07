package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by aravindkoneru on 10/28/15.
 */
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
    Servo zipLiner;


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
            CIRCUMFERENCE_INCHES = 4 * Math.PI,
            TICKS_PER_ROTATION = 1200 / 1.05,
            TICKS_PER_INCH = TICKS_PER_ROTATION / CIRCUMFERENCE_INCHES,
            TOLERANCE = 10;

    //ROBOT DIMENSIONS
    private final double   //TODO: Measure these distances for 9927
            ROBOT_WIDTH = 15.5,           // Width between centerline of wheels
            ROBOT_WHEEL_DISTANCE = 14;  // Distance between axles

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

        zipLiner = hardwareMap.servo.get("zip");


        setDirection(); //ensures the proper motor directions

        resetEncoders(); //ensures that the encoders have reset
    }

    public void moveTapeMeasure(double power){
        armMotor2.setPower(power);
        armMotor1.setPower(power);
    }

    //sets the proper direction for the motors
    public void setDirection() {
        //drive motors
        if (frontLeft.getDirection() == DcMotor.Direction.REVERSE) {
            frontLeft.setDirection(DcMotor.Direction.FORWARD);
        }
        if (backLeft.getDirection() == DcMotor.Direction.REVERSE) {
            backLeft.setDirection(DcMotor.Direction.FORWARD);
        }

        if (frontRight.getDirection() == DcMotor.Direction.FORWARD) {
            frontRight.setDirection(DcMotor.Direction.REVERSE);
        }

        if (backRight.getDirection() == DcMotor.Direction.FORWARD) {
            backRight.setDirection(DcMotor.Direction.REVERSE);
        }

        //arm motor
        if(armMotor1.getDirection() == DcMotor.Direction.FORWARD){
            armMotor1.setDirection(DcMotor.Direction.REVERSE);
        }

        //TODO configure arm motor direction
    }

    //reset drive encoders and return true when everything is at 0
    public boolean resetEncoders() {
        frontLeft.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        backLeft.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

        frontRight.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        backRight.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

        return (frontLeft.getCurrentPosition() == 0 &&
                backLeft.getCurrentPosition() == 0 &&
                frontRight.getCurrentPosition() == 0 &&
                backRight.getCurrentPosition() == 0);

    }

    //TODO: Implement cheesy drive or special drive code?
    public void setMotorPower(double leftPower, double rightPower) {//only accepts clipped values
        clipValues(leftPower, ComponentType.MOTOR);
        clipValues(rightPower, ComponentType.MOTOR);

        frontLeft.setPower(leftPower);
        backLeft.setPower(leftPower);

        frontRight.setPower(rightPower);
        backRight.setPower(rightPower);
    }

    //sets drive motors to encoder mode
    public void setToEncoderMode() {

        frontLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        backLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

        frontRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        backRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
    }

    //sets drive motors to run without encoders and use power
    public void setToWOEncoderMode() {
        frontLeft.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        backLeft.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

        frontRight.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        backRight.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
    }

    //encoder drive to go straight
    public boolean runStraight(double distance_in_inches, boolean speed) {//Sets values for driving straight, and indicates completion
        leftTarget = (int) (distance_in_inches * TICKS_PER_INCH);
        rightTarget = leftTarget;
        setTargetValueMotor();

        if(speed){
            setMotorPower(.8, .8);//TODO: Stalling factor that Libby brought up; check for adequate power
        } else{
            setMotorPower(.4,.4);
        }


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

    //simple debugging and info
    public void basicTel() {
        telemetry.addData("frontLeftPos: ", frontLeft.getCurrentPosition());
        telemetry.addData("backLeftPos: ", backLeft.getCurrentPosition());
        telemetry.addData("LeftTarget: ", leftTarget);

        telemetry.addData("frontRightPos: ", frontRight.getCurrentPosition());
        telemetry.addData("backRightPos: ", backRight.getCurrentPosition());
        telemetry.addData("RightTarget: ", rightTarget);

    }

    enum ComponentType {         //helps with clipValues
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


    //true = down; false = up
    public void setZiplinePosition(boolean down) {//slider values
        if (down) {
            zipLiner.setPosition(.9);
        } else {
            zipLiner.setPosition(.2);
        }
    }

    //TODO: Calibrate this motor for the arm
    public void setArmPivot(double power) {
        armPivot.setPower(clipValues(power, ComponentType.MOTOR));
    }

    public void setZipLiner(double pos)
    {
        zipLiner.setPosition(clipValues(pos,ComponentType.SERVO ));
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


    public void loop() {
    }


    public void stop(){

        setMotorPower(0,0);//brake the drive motors
        moveTapeMeasure(0);//brake the measuring tape motors
        setZiplinePosition(true);//bring the zipliner back up
        setArmPivot(0);//brake the pivot arm

    }

}