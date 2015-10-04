package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;


/**
 * Created by timmagoun on 10/4/15.
 */
public class ArmTester extends OpMode{

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
    }

    public void loop(){

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
}
