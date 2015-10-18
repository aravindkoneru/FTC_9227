package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

//NEWSTUFF
/**
 * Created by aravindkoneru on 10/4/15.
 */
public class ArmTesterEncoder extends OpMode{

    public ArmTesterEncoder(){
    }

    private DcMotor climberMotorLeft,
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

        float extend = gamepad2.left_trigger - gamepad2.right_trigger;
        float arm = gamepad2.left_stick_y;

        int targetPos = climberMotorLeft.getCurrentPosition();
        armMotor.setPower(arm);

        if(extend > 0){
            targetPos+=encoderStep;
        } else if(extend < 0){
            targetPos-=encoderStep;
        }

        climberMotorRight.setTargetPosition(targetPos);
        climberMotorLeft.setTargetPosition(targetPos);

    }
}
