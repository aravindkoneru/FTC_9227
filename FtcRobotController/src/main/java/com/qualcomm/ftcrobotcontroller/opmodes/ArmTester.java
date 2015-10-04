package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;


/**
 * Created by aravindkoneru on 10/4/15.
 */
public class ArmTester extends OpMode{

    public ArmTester(){

    }

    DcMotor climberMotorLeft,
            climberMotorRight,
            armMotor;

    final int encoderStep = 10;

    @Override
    public void init(){
        climberMotorLeft = hardwareMap.dcMotor.get("m1");
        climberMotorRight = hardwareMap.dcMotor.get("m2");
        armMotor = hardwareMap.dcMotor.get("arm");

        climberMotorRight.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        climberMotorLeft.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

        climberMotorLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        climberMotorRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);


    }

    public void loop(){

        float left = gamepad2.right_stick_y;
        float arm = gamepad2.left_stick_y;

        int targetPos = climberMotorLeft.getCurrentPosition();
        armMotor.setPower(arm);

        if(left > 0){
            climberMotorLeft.setTargetPosition(targetPos + encoderStep);
            climberMotorRight.setTargetPosition(targetPos + encoderStep);
        } else if(left < 0){
            climberMotorRight.setTargetPosition(targetPos - encoderStep);
            climberMotorLeft.setTargetPosition(targetPos - encoderStep);

        }

    }


}
