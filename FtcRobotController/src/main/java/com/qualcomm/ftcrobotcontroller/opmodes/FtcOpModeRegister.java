package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegister;

/**
 * Register Op Modes
 */
public class FtcOpModeRegister implements OpModeRegister {

  public void register(OpModeManager manager) {

      //autons/encoder stuff

      manager.register("MainTeleop", MainTeleOp.class);
      manager.register("pkzone", PkZone.class);
      manager.register("auton",auton.class);
      manager.register("GoStraight",GoStraight.class);
      //manager.register("BlueAuton", BlueBlueRampAuton.class);
      //manager.register("RedAuton", RedBlueRampAuton.class);
  }
}
