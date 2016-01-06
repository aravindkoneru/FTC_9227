package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorController;

import java.sql.Time;

/**
 * The starting position for the code is MM's starting pos.
 * the code is supposed to go to the climber's basket and drop the climbers.
 * Arvin, sicne you weren't there, idk if anyone told you how we plan on dropping the climbers, its kind of hard to explain by text
 * so ill tell u on thursady, but the code should make the robot extend the tape measures, then retract them.
 */
public class PkZone extends OpModeHelperClean{


    //establish run states for auton
    enum RunState{
        RESET_STATE,
        FIRST_STATE,
        FIRST_RESET,
        SECOND_STATE,
        SECOND_RESET,
        THIRD_STATE,
        THIRD_RESET,
        FOURTH_STATE,
        FOURTH_RESET,
        FIFTH_STATE,
        FIFTH_RESET,
        SIXTH_STATE,
        SEVENTH_STATE,
        SEVENTH_RESET,
        EIGHT_STATE,
        LAST_STATE
    }

    private RunState rs = RunState.RESET_STATE;

    public PkZone() {}
    double TimeDurationForArm;
    double TimeDurationForTapeMeasure;

    @Override
    public void loop() {
        basicTel();
        setToEncoderMode();


        switch(rs) {
            case RESET_STATE: {
                if(resetEncoders())
                    rs = RunState.FIRST_STATE;
                break;
            }
            case FIRST_STATE: {
                //go forward 23 inches
                if (runStraight(-14.5, false)) {
                    rs = RunState.FIRST_RESET;
                }
                break;
            }
            case FIRST_RESET:
            {
                //reset encoders
                if(resetEncoders())
                    rs = RunState.SECOND_STATE;
                break;
            }
            case SECOND_STATE:
            {
                //turn 45 degrees to face the climber basket
                if (setTargetValueTurn(45)){
                    rs = RunState.SECOND_RESET;
                }
                break;
            }
            case SECOND_RESET:
            {
                //reset encdoers
                if(resetEncoders())
                    rs = RunState.THIRD_STATE;
                break;
            }
            case THIRD_STATE:
            {
                //go foward to the basket
                if (runStraight(-84, false)) {
                    rs = RunState.THIRD_RESET;
                }
                break;
            }
            case THIRD_RESET:
            {
            //reset encdoers
                if(resetEncoders())
                    rs = RunState.FOURTH_STATE;//TODO First STOP
                break;
            }
            case FOURTH_STATE:
            {
            //align with basket
                if(setTargetValueTurn(45)){
                    rs = RunState.FOURTH_RESET;
                }
                break;
            }
            case FOURTH_RESET:
            {
            //reset
                if(resetEncoders())
                    rs = RunState.LAST_STATE;
                break;
            }
            case FIFTH_STATE:
            {
            //raise arm
                setArmPivot(-.2);
                if(TimeDurationForArm > 150){
                    setArmPivot(0);
                    rs = RunState.SIXTH_STATE;
                }
                TimeDurationForArm++;
                break;
            }

            //if we are not using ENCODERS
            case SIXTH_STATE:
            {
                //extend tape masures
//                if(moveTapeMeasureENC(6))
//                {
//                    rs = RunState.SEVENTH_STATE;
//                }
                break;
            }

            case SEVENTH_STATE:
            {
                //setting down arm
                setArmPivot(-.1);

                if(TimeDurationForArm > 150){
                    setArmPivot(0);
                    rs = RunState.EIGHT_STATE;
                }
                TimeDurationForArm++;
                break;
            }

            case SEVENTH_RESET:
            {
                if(resetEncoders()){
                    rs = RunState.EIGHT_STATE;
                }
                break;
            }
            case EIGHT_STATE:
            {
//                if(moveTapeMeasureENC(-6)){
//                    rs = RunState.LAST_STATE;
//                }
                break;
            }
            case LAST_STATE:
            {
                resetEncoders();
                stop();
            }
        }
    }
}

