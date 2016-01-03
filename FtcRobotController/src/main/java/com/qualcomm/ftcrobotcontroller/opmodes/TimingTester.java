package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Tim on 12/30/2015.
 */
public class TimingTester extends OpModeHelperClean {
    private double time = 0;

    public TimingTester(){}

    @Override
    public void loop(){

        telemetry.addData("Time: ", time);

        time=getRuntime();
    }
}