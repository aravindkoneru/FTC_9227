package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by aravindkoneru on 10/31/15.
 */
public class armpivottester extends OpHelperClean{

    public void loop(){

        if(gamepad1.left_bumper){
            setArmPivot(-.4);
        }

        if(gamepad1.right_bumper){
            setArmPivot(.4);
        }

    }

}
