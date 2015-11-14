package com.qualcomm.ftcrobotcontroller.opmodes;

public class MainTeleOp extends OpModeHelperClean {

    public MainTeleOp(){

    }

    //TODO: Talk to drive team about controller prefs/who controls what
    //Right now, all operator stuff is gamepad2 and driving is gamepad1
    //TODO: check wiring


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
        } else if(gamepad2.right_bumper){//brings it down
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
