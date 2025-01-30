// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.CANBus;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class ClawConstants {
    public static int clawMotorID = 0;
    public static int rangerID = 0;

    public enum clawState {
      CORAL_INTAKING_STATE,
      CORAL_SCORING_STATE,
      ALGAE_INTAKING_STATE,
      ALGAE_SCORING_STATE,
      CORAL_HOLDING_STATE,
      ALGAE_HOLDING_STATE
    }

    public static double timeforclawtorun = 0.2;
    public static double clawVoltage = 6;
  }
}
