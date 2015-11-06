package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Ruchir on 11/06/2015.
 */
public class PkZone extends OpHelperClean {


    enum RunState {
        RESET_STATE,
        FIRST_STATE,
        FIRST_RESET,
        SECOND_STATE,
        SECOND_RESET,
        LAST_STATE,
        STOP_STATE
    }

    private RunState rs = RunState.RESET_STATE;

    public PkZone() {
    }


    @Override
    public void loop() {
        basicTel();
        setToEncoderMode();
        switch (rs) {
            case RESET_STATE: {
                resetEncoders();
                rs = RunState.FIRST_STATE;
                break;
            }
            case FIRST_STATE:{
                if(runStraight(40)){
                    rs = RunState.FIRST_RESET;
                }
                break;
            }
            case FIRST_RESET:{
                if(resetEncoders()){
                    rs = RunState.SECOND_STATE;
                }
                break;
            }
            case SECOND_STATE:{
                if(setTargetValueTurn(150)){
                    rs = RunState.SECOND_RESET;
                }
                break;
            }
            case SECOND_RESET:{
                if(resetEncoders()){
                    rs = RunState.LAST_STATE;
                }
                break;
            }
            case LAST_STATE:{
                if(runStraight(68)){
                    rs = RunState.STOP_STATE;
                }
                break;
            }
            case STOP_STATE:{
                stop();
                break;
            }
        }
    }
}