package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Tim on 10/25/2015.
 * copied by ruchir
 */
public class redAuton extends OpHelperClean{


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
        FIFTH_RESET,
        FIFTH_STATE,
        SIXTH_STATE,
        STOP_STATE
    }

    double x = 0;

    private RunState rs = RunState.RESET_STATE;
    public redAuton() {}


    @Override
    public void loop() {


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

                if(runStraight(-35, false) )//&& debug)
                {
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
                if(setTargetValueTurn(-115))
                {
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
                if(runStraight(-45, false)){
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
                if(setTargetValueTurn(-50)){
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
                setZiplinePosition(false);
                rs = RunState.SIXTH_STATE;
                break;
            }

            case SIXTH_STATE:
            {
                setArmPivot(-.2);
                if(x > 100){
                    setArmPivot(0);
                    rs = RunState.LAST_STATE;
                }
                x++;
                break;

            }
            case LAST_STATE:
            {
                if(runStraight(-75, true))
                {
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
