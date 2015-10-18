package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;


/**
 * Created by timmagoun on 10/4/15.
 */

<<<<<<< HEAD
    private DcMotor climberMotorLeft,
            climberMotorRight,
            armMotor;

    public ArmTester(){
    }
    @Override
    public void init(){
        climberMotorLeft = hardwareMap.dcMotor.get("m1");
        climberMotorLeft.setDirection(DcMotor.Direction.REVERSE);
        climberMotorRight = hardwareMap.dcMotor.get("m2");
        armMotor = hardwareMap.dcMotor.get("arm");
=======
//unstable code that has a double tap issue, but works by driving
//the arm using encoders.
public class armTester extends OpMode{

    public armTester(){

    }

    DcMotor leftMotor,
            rightMotor,
            arm;
    int pos = 0;
    final int inc = 10;

    public void init(){
        leftMotor = hardwareMap.dcMotor.get("m1");
        rightMotor = hardwareMap.dcMotor.get("m2");
        arm = hardwareMap.dcMotor.get("arm");

        leftMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
>>>>>>> 14a9e458c95bee7cb3a56fe3d9bc6a75d844e1af
    }

    public void loop(){
        leftMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

        float armPower = gamepad2.left_stick_y;

<<<<<<< HEAD
        float trigger = gamepad2.left_trigger - gamepad2.right_trigger;
        float arm = gamepad2.left_stick_y;

        armMotor.setPower(arm);

        if(trigger > 0){
            climberMotorLeft.setPower(.3);
            climberMotorRight.setPower(.3);
        } else if(trigger < 0){
            climberMotorLeft.setPower(-.3);
            climberMotorRight.setPower(-.3);
        }
        else
        {
            climberMotorLeft.setPower(0);
            climberMotorRight.setPower(0);
        }
    }
=======
        float extend = gamepad2.left_trigger - gamepad2.right_trigger;

        arm.setPower(armPower);

        if(extend > 0){
            pos+=inc;
            rightMotor.setTargetPosition(inc);
            leftMotor.setTargetPosition(inc);
            rightMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
            leftMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);


        } else if(extend < 0){
            pos-=inc;
            rightMotor.setTargetPosition(-inc);
            leftMotor.setTargetPosition(-inc);
            rightMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
            leftMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        }

        rightMotor.setPower(0.3);
        leftMotor.setPower(0.3);
    }

<<<<<<< HEAD
}
=======
>>>>>>> 14a9e458c95bee7cb3a56fe3d9bc6a75d844e1af
}
>>>>>>> 76717865609accd2ba054b240d8936c04f9c04de
