package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by therat0981 on 10/31/15.
 */
public class GoFwrd extends OpModeHelper {
    public GoFwrd(){

    }
    @Override
    public void init() {
        init();

    }

    @Override
    public void loop() {
        updateController();
        driveUsingSticks();
        telemetry();

    }

    @Override
    public void stop() {

    }
}
