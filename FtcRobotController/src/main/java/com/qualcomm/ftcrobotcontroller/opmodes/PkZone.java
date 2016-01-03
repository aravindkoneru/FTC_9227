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

                rs = RunState.FIRST_STATE;
                break;
            }
            case FIRST_STATE: {
                //go forward 23 inches
                if (runStraight(-23, false)) {
                    rs = RunState.FIRST_RESET;
                }
                break;
            }
            case FIRST_RESET:
            {
                //reset encoders
                resetDriveEncoders();
                rs = RunState.SECOND_STATE;
            }
            case SECOND_STATE:
            {
                //turn 45 degrees to face the climber basket
                if (setTargetValueTurn(45)) {
                    rs = RunState.SECOND_RESET;
                }
                break;
            }
            case SECOND_RESET:
            {
                //reset encdoers
                resetDriveEncoders();
                rs = RunState.THIRD_STATE;
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
            {//reset encdoers
                resetDriveEncoders();
                rs = RunState.FOURTH_STATE;
            }
            case FOURTH_STATE:
            {//align with basket
                if(setTargetValueTurn(45)){
                    rs = RunState.FOURTH_RESET;
                }
                break;
            }
            case FOURTH_RESET:
            {//reset
                resetDriveEncoders();
                rs = RunState.FIFTH_STATE;
            }
            case FIFTH_STATE:
            {//raise arm
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
                /*moveTapeMeasure(.1);
                if(TimeDurationForTapeMeasure > 100){
                    moveTapeMeasure(0);
                    TimeDurationForTapeMeasure =0;
                    rs = RunState.SEVENTH_STATE;
                }
                TimeDurationForTapeMeasure++;
                break;*/
                //moveTapeMeasureWithEncoders(6);
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

            case EIGHT_STATE:
            {
                moveTapeMeasure(-.1);

                if(TimeDurationForTapeMeasure > 100){
                    moveTapeMeasure(0);
                    rs = RunState.LAST_STATE;
                }
                TimeDurationForTapeMeasure++;

                break;
            }
            case LAST_STATE:
            {
                resetDriveEncoders();
                stop();
            }
            /*
            this is the code if we are using encoder with our tape measure
            case SIXTH_STATE:
            {
                moveTapeMeasureWithEncoders(6);
            }
            case EIGHT_STATE:
            {
                moveTapeMeasureWithEncoder(-6);
            }
            this is the code if we are using  encoders on our arm pivot thing
            case SEVENTH_STATE:
            {
            }
            */
            /*
             */
        }
    }
}

