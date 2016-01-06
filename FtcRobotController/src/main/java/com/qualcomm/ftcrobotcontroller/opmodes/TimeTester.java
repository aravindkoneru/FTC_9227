package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by therat0981 on 12/30/15.
 */
public class TimeTester extends OpModeHelperClean {

    public TimeTester(){}

    public void loop(){
        telemetry.addData("Time: ", getRuntime());
    }
}
