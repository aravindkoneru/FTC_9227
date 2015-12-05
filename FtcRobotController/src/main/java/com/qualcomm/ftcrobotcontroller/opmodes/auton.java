
package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Tim on 10/25/2015.
 * copied by ruchir
 */
public class auton extends OpModeHelperClean{


    enum RunState{
        RESET_STATE,
        FIRST_STATE,
        FIRST_RESET,
        SECOND_STATE,
        SECOND_RESET,
        THIRD_STATE,
        THIRD_RESET,
        FOURTH_STATE,
        LAST_STATE,
        FOURTH_RESET,
        FIFTH_STATE,
        FIFTH_RESET,
        SIXTH_RESET,
        SIXTH_STATE,
        STOP_STATE
    }
    double x = 0;

    private RunState rs = RunState.RESET_STATE;
    public auton() {}


    @Override
    public void loop() {


        basicTel();
        setToEncoderMode();
        switch(rs) {
            case RESET_STATE:
            {
                if(resetEncoders())
                {
                    rs=RunState.FIRST_STATE;
                }
                break;
            }
            case FIRST_STATE: {
                if (runStraight(-10, false)) {
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
            case SECOND_STATE: {
                if (encoderDrive(-1275)) {
                    rs = RunState.SECOND_RESET;
                }
                break;
            }

            case SECOND_RESET:
            {
                if(resetEncoders()){
                    rs = RunState.THIRD_STATE;
                }
                break;
            }
            case THIRD_STATE:
            {
                if(encoderDrive(-1865)){
                    rs = RunState.THIRD_RESET;
                }
                break;


            }
            case THIRD_RESET: {
                if (resetEncoders()) {
                    rs = RunState.FOURTH_STATE;
                }
                break;
            }
            case FOURTH_STATE:
            {
                if(encoderDrive(-2900)){
                    rs = RunState.FOURTH_RESET;
                }
                break;

            }
            case FOURTH_RESET:
            {
                if(resetEncoders()){
                    rs = RunState.FIFTH_STATE;
                }
                break;
            }
            case FIFTH_STATE:
            {
                if(encoderDrive(-1181)) {
                    rs = RunState.FIFTH_RESET;
                }
                break;
            }
            case FIFTH_RESET:
            {
                if(resetEncoders()){
                    rs = RunState.SIXTH_STATE;
                }
                break;

            }
            case SIXTH_STATE: {
                if (encoderDrive(-5000)) {
                    rs = RunState.STOP_STATE;
                }
                break;
            }
            case STOP_STATE:
            {
                stop();
                break;
            }
        }
    }
}
