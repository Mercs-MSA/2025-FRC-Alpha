// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.math.controller.ElevatorFeedforward;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class MotorConstants {
    public static final int Flywheelintake = 1;
    public static final int IntakePivot = 2;
    public static final int CoralIntakeRPM = 10;

    public static final Map<String,Double> pivotMotorPositions = new HashMap<String,Double>() {{
      put("MoveToIntakePosition", 10.0);
      put("MoveToCoral1", 20.0);
      put("MoveToCoral2and3", 30.0);
      put("MoveToCoral4", 40.0);

      put("MoveToAlgae", 50.0);
      put("MoveToProcessor", 60.0);
      put("MoveToBarge", 70.0);
    }};

    //MoveToIntakePosition - Number state (constants)
    //MoveToCoral1 - Number state (constants)
    //MoveToCoral2and3 - Number state (constants)
    //MoveToCoral4 - Number state (constants)

    //MoveToAlgae - Number state (constants)
    //MoveToProcessor - Number state (constants)
    //MoveToBarge - Number state (constants)

    public Double returnKey(String key) {
      return (double)pivotMotorPositions.get(key);
    };

    
  }



  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
}
