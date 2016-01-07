package com.qualcomm.ftcrobotcontroller.opmodes;

public class MainTeleOp extends OpModeHelperClean {

    public MainTeleOp() {

    }
    //TODO: check wiring

    //TODO: ITS ALWAYS A HARDWARE PROBLEM
    //TODO: ITS ALWAYS A HARDWARE PROBLEM
    //TODO: ITS ALWAYS A HARDWARE PROBLEM
    //TODO: ITS ALWAYS A HARDWARE PROBLEM
    //TODO: ITS ALWAYS A HARDWARE PROBLEM
    //TODO: ITS ALWAYS A HARDWARE PROBLEM
    //TODO: ITS ALWAYS A HARDWARE PROBLEM
    //TODO: ITS ALWAYS A HARDWARE PROBLEM
    //TODO: ITS ALWAYS A HARDWARE PROBLEM
    //TODO: ITS ALWAYS A HARDWARE PROBLEM

    @Override
    public void loop() {
        basicTel();

        //DRIVER CONTROLS
        //manual drive
        if (gamepad1.right_bumper && gamepad1.left_bumper) {
            manualDrive(true);
        } else {
            manualDrive(false);//move robot using joysticks
        }
        //constant drive
        if (gamepad1.a) {
            setMotorPower(-1, -1);
        } else if (gamepad1.y) {
            setMotorPower(1, 1);
        }
        //propeller
        if(gamepad1.left_bumper){
            movePropeller(1);
        } else if(gamepad1.right_bumper){
            movePropeller(-1);
        } else{
            movePropeller(0);
        }

        //OPERATOR CONTROLS
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
            moveTapeMeasure(.2);
        } else if (gamepad2.a) {
            moveTapeMeasure(-.2);
        } else if (gamepad2.x) {
            moveTapeMeasure(-.8);
        } else {
            moveTapeMeasure(0);
        }

    }
}