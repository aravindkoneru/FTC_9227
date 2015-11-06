package com.qualcomm.ftcrobotcontroller.opmodes;

public class MainTeleOp extends OpHelperClean {

    public MainTeleOp(){

    }

    //operator = gamepad2; driver = gamepad1

    //TODO: Check all of these to make sure they work

    @Override
    public void loop() {
        basicTel();

        if(gamepad1.right_bumper && gamepad1.left_bumper){
            manualDrive(true);
        } else{
            manualDrive(false);//move robot using joysticks
        }

        //Handle zipliner positions
        if(gamepad2.left_bumper){
            setZiplinePosition(true);
        } else if(gamepad2.right_bumper){
            setZiplinePosition(false);
        }

        //handle arm pivot
        if(gamepad2.dpad_up){
            setArmPivot(-.2);
        }else if(gamepad2.dpad_down){
            setArmPivot(.2);
        } else{
            setArmPivot(0);
        }

        //handle the tape measure
        if(gamepad2.y) {
            moveTapeMeasure(.2);
        } else if(gamepad2.a){
            moveTapeMeasure(-.2);
        } else{
            moveTapeMeasure(0);
        }
    }

}
