package com.qualcomm.ftcrobotcontroller.opmodes;

public class MainTeleOp extends OpHelperClean {

    public MainTeleOp(){

    }

    //TODO: Talk to drive team about controller prefs/who controls what
    //Right now, all operator stuff is gamepad2 and driving is gamepad1

    @Override
    public void loop() {
        //basicTel();
        manualDrive();//move robot using joysticks

        //Handle zipliner positions
        if(gamepad2.y){
            setZiplinePosition(true);
        }

        if(gamepad2.a){
            setZiplinePosition(false);
        }
//
//        //TODO: Check if this will work
//        //handle arm pivot
//        if(gamepad2.left_bumper){
//            setArmPivot(-.4);
//        }
//
//        if(gamepad2.right_bumper){
//            setArmPivot(.4);
//        }
//
//        //TODO: Tape measure code
    }

    @Override
    public void stop() {

    }

}
