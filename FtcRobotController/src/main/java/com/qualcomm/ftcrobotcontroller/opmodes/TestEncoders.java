package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Tim on 10/25/2015.
 */
public class TestEncoders extends OpHelperClean{

    boolean debug;

    enum RunState{
        RESET_STATE,
        FIRST_STATE,
        FIRST_RESET,
        SECOND_STATE,
        THIRD_STATE,
        FOURTH_STATE,
        LAST_STATE
    }

    private RunState rs = RunState.RESET_STATE;
    public TestEncoders() {}


    @Override
    public void loop() {
        if(gamepad1.a){
            debug=true;
        }



        basicTel();
        setToEncoderMode();
        switch(rs) {
            case RESET_STATE:
            {
                resetEncoders();
                rs=RunState.FIRST_STATE;
                break;
            }
            case FIRST_STATE:
            {
                //setTargetValueMotor(10,10);

                if(runStraight(10) )//&& debug)
                {
                    //resetEncoders();
                    //debug = false;
                    rs = RunState.FIRST_RESET;
                }
                break;
            }
            case FIRST_RESET: {

                if(resetEncoders()){
                    rs = RunState.SECOND_STATE;
                }
                break;
            }
            case SECOND_STATE:
            {
                //setTargetValueMotor(5,5);

                if(runStraight(12))// && debug)
                {
                //    resetEncoders();
                    rs = RunState.LAST_STATE;
                }
                break;
            }

            case LAST_STATE:
            {
                telemetry.addData("END", 10);
            }


        }
    }
}
