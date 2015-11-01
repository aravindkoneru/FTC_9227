package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Tim on 11/1/2015.
 */
public class TurnTester extends OpHelperClean {

    public TurnTester()
    {

    }

    @Override
    public void loop()
    {
        setToEncoderMode();
        setTargetValueTurn(45);
    }


}
