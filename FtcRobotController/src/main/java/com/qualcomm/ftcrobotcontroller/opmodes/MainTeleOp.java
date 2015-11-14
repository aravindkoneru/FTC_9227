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
        if(gamepad2.y){
            setZiplinePosition(true);
        }

        if(gamepad2.a){
            setZiplinePosition(false);
        }

        //handle arm pivot
        if(gamepad2.left_bumper){
            setArmPivot(-.2);
        }else if(gamepad2.right_bumper){
            setArmPivot(.2);
        } else{
            setArmPivot(0);
        }

        //handle the tape measure
        if(gamepad2.left_trigger > 0) {
            moveTapeMeasure(.2);
        } else if(gamepad2.right_trigger > 0){
            moveTapeMeasure(-.2);
        } else{
            moveTapeMeasure(0);
        }
    }

}
