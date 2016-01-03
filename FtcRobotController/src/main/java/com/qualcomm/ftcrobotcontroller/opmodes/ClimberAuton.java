package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by aravindkoneru on 11/15/15.
 */
public class ClimberAuton extends OpModeHelperClean {

    enum RunState{
        RESET,
        FIRST,
        FRESET,
        SECOND,
        SRESET,
        THRID,
        TRESET,
        FOURTH,
        FIFTH,
        LAST
    };

    double x = 0;

    RunState rs = RunState.RESET;

    public void loop(){

        switch (rs){

            case RESET: {

                rs = RunState.FIRST;
                break;
            }
            case FIRST: {
                if (runStraight(-74, false)) {
                    rs = RunState.FRESET;
                }
                break;
            }

            case FRESET:{
                if(resetDriveEncoders()){
                    rs = RunState.SECOND;
                }
                break;
            }

            case THRID:{
                if(x < 100){
                    x = 0;
                    setArmPivot(0);
                    rs = RunState.TRESET;
                }
                setArmPivot(-.2);
                x++;
                break;
            }

            case TRESET:{
                if (resetDriveEncoders()){
                    rs = RunState.FOURTH;
                }
                break;
            }

            case FOURTH:{
                if (runStraight(-10, false)) {
                    rs = RunState.FIFTH;
                }
                break;
            }

            case FIFTH:{
                if(x < 40){
                    setArmPivot(0);
                    rs = RunState.LAST;
                }
                setArmPivot(.2);
                x++;
                break;
            }

            case LAST:{
                stop();
            }



        }

    }


}
