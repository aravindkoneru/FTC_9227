package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by aravindkoneru on 9/2/15.
 */
public class CheesyDrive extends OpMode {

    //@TODO: Need to add another motor on the left and the right

    public CheesyDrive(){

    }

    DcMotor rightMotor1,
            rightMotor2;
    DcMotor leftMotor1,
            leftMotor2;

    final double SKIM_GAIN = 1; //used in skim() and will need to be adjusted based on driver
    final double TURN_GAIN = 1; //used to calcualte turn and will need to be adjusted based on driver

    @Override
    public void init(){

        leftMotor1 = hardwareMap.dcMotor.get("l1");
        leftMotor2 = hardwareMap.dcMotor.get("l2");

        rightMotor1 = hardwareMap.dcMotor.get("r1");
        rightMotor2 = hardwareMap.dcMotor.get("r2");

        leftMotor1.setDirection(DcMotor.Direction.REVERSE);
        leftMotor2.setDirection(DcMotor.Direction.REVERSE);
    }

    public void setPowerRight(double value){
        rightMotor1.setPower(value);
        rightMotor2.setPower(value);
    }

    public void setPowerLeft(double value){
        leftMotor2.setPower(value);
        leftMotor1.setPower(value);
    }

    @Override
    public void loop(){

        //get values from joystick
        double throttle = (double)gamepad1.left_stick_y;
        double turn = (double)gamepad1.right_stick_x * TURN_GAIN * throttle;

        //calculate stock values (no skim and might be out of range)
        double noSkimLeft = throttle + turn;
        double noSkimRight = throttle - turn;

        //calculate values post skim (sent to motors)
        double left = noSkimLeft + skim(noSkimRight);
        double right = noSkimRight + skim(noSkimLeft);

        //set motor power
        setPowerLeft(left);
        setPowerRight(right);
    }


    //removes excess and adds it to the other side
    public double skim(double someVal){
        if (someVal > 1.0)
            return -((someVal - 1.0) * SKIM_GAIN);
        else if (someVal < -1.0)
            return -((someVal + 1.0) * SKIM_GAIN);
        return 0;
    }

    @Override
    public void stop(){

    }


}
