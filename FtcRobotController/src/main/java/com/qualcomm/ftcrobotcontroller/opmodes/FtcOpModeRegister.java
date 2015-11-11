package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegister;

/**
 * Register Op Modes
 */
public class FtcOpModeRegister implements OpModeRegister {

  public void register(OpModeManager manager) {

      //null op

      //main
      manager.register("MainTeleOp", MainTeleOp.class);

      //autons/encoder stuff
      manager.register("blue auton", blueAuton.class);
      manager.register("red auton", redAuton.class);
  }
}
