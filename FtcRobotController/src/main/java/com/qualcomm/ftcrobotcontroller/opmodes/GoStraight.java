
package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Tim on 10/25/2015.
 * copied by ruchir
 */
public class GoStraight extends OpModeHelperClean{


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
    public GoStraight() {}


    @Override
    public void loop() {


        basicTel();
        setToEncoderMode();
        switch(rs) {
            case RESET_STATE:
            {
                if(resetEncoders())
                {
                    rs=RunState.SECOND_STATE;
                }
                break;
            }
            case FIRST_STATE: {
                if (runStraight(10, false)) {
                    rs = RunState.STOP_STATE;
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
                if(setTargetValueTurn(90)){
                    rs = RunState.STOP_STATE;
                }
            }
            case STOP_STATE:
            {
                stop();
                break;
            }
        }
    }
}
