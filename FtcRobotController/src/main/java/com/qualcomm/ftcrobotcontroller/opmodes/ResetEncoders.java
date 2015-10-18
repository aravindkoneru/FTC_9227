package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;


/**
 * Created by therat0981 on 10/18/15.
 */
public class ResetEncoders extends OpMode {
    DcMotor l1, r1, l2, r2;
    @Override
    public void init() {
        l1 = hardwareMap.dcMotor.get("l1");
        l2 = hardwareMap.dcMotor.get("l2");
        r1 = hardwareMap.dcMotor.get("r1");
        r2 = hardwareMap.dcMotor.get("r2");


        l1.setDirection(DcMotor.Direction.REVERSE);
        l2.setDirection(DcMotor.Direction.REVERSE);

        r1.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        r2.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        l1.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        l2.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);


        r1.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        r2.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        l1.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        l2.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
    }

    @Override
    public void loop() {
        r1.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        r2.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        l1.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        l2.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        //left is -
        //right goes -
    }

    @Override
    public void stop() {
        super.stop();
    }
}
