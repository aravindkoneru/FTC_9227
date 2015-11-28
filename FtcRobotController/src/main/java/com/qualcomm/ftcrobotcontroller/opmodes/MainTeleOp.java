package com.qualcomm.ftcrobotcontroller.opmodes;

public class MainTeleOp extends OpModeHelperClean {

    public MainTeleOp() {

    }

    //Right now, all operator stuff is gamepad2 and driving is gamepad1
    //TODO: check wiring


    @Override
    public void loop() {
        basicTel();

        if (gamepad1.right_bumper && gamepad1.left_bumper) {
            manualDrive(true);
        } else {
            manualDrive(false);//move robot using joysticks
        }

        if (gamepad1.a) {
            setMotorPower(-1, -1);
        } else if (gamepad1.y) {
            setMotorPower(1, 1);
        }

        //Handle zipliner positions
        if (gamepad2.left_bumper) {
            activateLeft(false);
        } else if (gamepad2.right_bumper) {//brings it down
            activateRight(true);
        }

        if (gamepad2.left_trigger > 0) {
            activateLeft(true);
        } else if (gamepad2.right_trigger > 0) {
            activateRight(false);
        }

        //handle arm pivot
        if (gamepad2.dpad_down) {
            setArmPivot(-.2);
        } else if (gamepad2.dpad_up) {
            setArmPivot(.2);
        } else if (gamepad2.dpad_right) {
            setArmPivot(.8);
        } else {
            setArmPivot(0);
        }

        //handle the tape measure
        if (gamepad2.y) {
            moveTapeMeasure(-.2);
        } else if (gamepad2.a) {
            moveTapeMeasure(.2);
        } else if (gamepad2.x) {
            moveTapeMeasure(.8);
        } else {
            moveTapeMeasure(0);
        }

    }
}