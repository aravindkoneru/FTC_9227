package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;


public class EncoderTester extends OpMode {
    //gyroTest gt;

    DcMotor l1, r1, l2, r2;

    final double ticksPerRotation = 1200;
    final double inchesPerRotation = 4 * Math.PI;
    double distance = 20;
    double rotations = ticksPerRotation*(distance/inchesPerRotation);
    final double tolerance = 100;

    public EncoderTester() {

    }

    public void init() {
        l1 = hardwareMap.dcMotor.get("l1");
        l2 = hardwareMap.dcMotor.get("l2");
        r1 = hardwareMap.dcMotor.get("r1");
        r2 = hardwareMap.dcMotor.get("r2");

        l1.setDirection(DcMotor.Direction.REVERSE);
        l2.setDirection(DcMotor.Direction.REVERSE);

        setReset();
        setToRun();

        SetPowerLeft(.10);
        SetPowerRight(.10);
    }

    public void setToRun(){
        r1.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        r2.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        l1.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        l2.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
    }

    public void setReset(){
        r1.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        r2.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        l1.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        l2.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
    }

    public void SetPowerRight(double Power){
        r1.setPower(Power);
        r2.setPower(Power);

    }
    public void SetPowerLeft(double Power){
        l1.setPower(Power);
        l2.setPower(Power);
    }

    public int getAvgRight(){
        int avgR = r1.getCurrentPosition() + r2.getCurrentPosition();
        avgR /= 2;
        return (int)avgR;
    }

    public int getAvgLeft(){
        int avgL = l1.getCurrentPosition() + l2.getCurrentPosition();
        avgL /= 2;
        return avgL;
    }


    public void loop() {

        r1.setTargetPosition((int) (-rotations));
        r2.setTargetPosition((int) (-rotations));
        l1.setTargetPosition((int) (-rotations));
        l2.setTargetPosition((int) (-rotations));

        if(Math.abs(getAvgLeft()-rotations) <= tolerance){
            stop();
        }

        else if(Math.abs(getAvgRight() - rotations) <= tolerance){
            stop();
        }

        telemetry.addData("Right1 Encoder: ", r1.getCurrentPosition());
        telemetry.addData("Right2 Encoder: ", r2.getCurrentPosition());
        telemetry.addData("Left1 Encoder: ", l1.getCurrentPosition());
        telemetry.addData("Left2 Encoder: ", l2.getCurrentPosition());
    }

    public void stop() {
        SetPowerLeft(0);
        SetPowerRight(0);

    }
}