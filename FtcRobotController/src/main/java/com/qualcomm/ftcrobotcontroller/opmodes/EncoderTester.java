package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;


public class EncoderTester extends OpMode {
    //gyroTest gt;

    DcMotor l1, r1, l2, r2;

    final double TICKS_PER_ROTATION = 1200/1.05;
    final double INCHES_PER_ROTATION = 4 * Math.PI;
    double distance = 20;
    double rotations = TICKS_PER_ROTATION*(distance/INCHES_PER_ROTATION);
    final double TOLERANCE = 500;
    boolean stopped = false;

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

        setPowerLeft(.40);
        setPowerRight(.40);
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

    public void setPowerRight(double Power){
        r1.setPower(Power);
        r2.setPower(Power);
    }
    public void setPowerLeft(double Power){
        l1.setPower(Power);
        l2.setPower(Power);
    }

    public double getAvgRight(){
        double avgR = r1.getCurrentPosition() + r2.getCurrentPosition();
        avgR /= 2;
        return (int)avgR;
    }

    public double getAvgLeft(){
        double avgL = l1.getCurrentPosition() + l2.getCurrentPosition();
        avgL /= 2;
        return avgL;
    }


    public void loop() {

        //setToRun();

        if(Math.abs(Math.abs(getAvgLeft()) - rotations) <= TOLERANCE){
            setReset();
            stopped = true;
            stop();
        }

        else if(Math.abs(Math.abs(getAvgRight()) - rotations) <= TOLERANCE){
            setReset();
            stopped = true;
            stop();
        }

        if(!stopped) {
            r1.setTargetPosition((int) (-rotations));
            r2.setTargetPosition((int) (-rotations));
            l1.setTargetPosition((int) (-rotations));
            l2.setTargetPosition((int) (-rotations));
        }
        else{
            setPowerLeft(0);
            setPowerRight(0);
        }

        telemetry.addData("value of Tolerance Left", (Math.abs(Math.abs(getAvgLeft()) - rotations)));
        telemetry.addData("value of Tolerance Right", (Math.abs(Math.abs(getAvgRight()) - rotations)));
        telemetry.addData("Right1 Encoder: ", r1.getCurrentPosition());
        telemetry.addData("Right2 Encoder: ", r2.getCurrentPosition());
        telemetry.addData("Left1 Encoder: ", l1.getCurrentPosition());
        telemetry.addData("Left2 Encoder: ", l2.getCurrentPosition());
    }

    public void stop() {

        //setReset();

        l1.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        l2.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        r1.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        r2.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

        setPowerLeft(0);
        setPowerRight(0);

    }
}