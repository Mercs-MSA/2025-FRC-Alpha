// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import frc.robot.Constants.MotorConstants.AvailableState;
import frc.robot.Constants.PivotConstants;

import com.ctre.phoenix6.CANBus;

import com.ctre.phoenix6.CANBus;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.numbers.N3;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  public static class ElevatorConstants {
    public static final double L1 = 0.7;
    public static final double L2 = 5.45;
    public static final double L3 = 12.90;
    public static final double L4 = 25.75;
  };

  public static class MotorConstants {
    public static final int Flywheelintake = 20;
    public static final int IntakePivot = 2;
    public static final int ElevatorMain = 36;
    public static final int ElevatorFollower = 38;
    public static final int CoralIntakeRPM = 30;

    public static double elevatortemppos= 0;

    public static String state = "Move";

     public enum AvailableState {
      LEVEL1(10.0, 10.0),
      LEVEL2(20.0, 20.0),
      LEVEL3(30.0, 30.0),
      LEVEL4(40.0, 40.0);

      private double pivotPos;
      private double elevatorPos;

      private AvailableState(Double pivotPos, Double elevatorPos)
      {
        this.pivotPos = pivotPos;
        this.elevatorPos = elevatorPos;
     }

      public double pivotPosGet() {
        return this.pivotPos;
      };

      public double elevatorPosGet() {
        return this.elevatorPos;
      };
     }


    // public static final Map<AvailableState,Double> pivotMotorPositions = new HashMap<AvailableState,Double>() {{
    //   put(AvailableState.LEVEL1, 10.0);
    //   put(AvailableState.LEVEL2, 20.0);
    //   put(AvailableState.LEVEL3, 30.0);
    //   put(AvailableState.LEVEL4, 40.0);

    //   //Pivot motor position is dependent on elevator position,
    //   //instead could be a mode for pivot from operator side that checks if elevator position is ok
    //   // put(AvailableState.ALGAE, 50.0);
    //   // put("MoveToProcessor", 60.0);
    //   // put("MoveToBarge", 70.0);
    // }};

    //MoveToIntakePosition - Number state (constants)
    //MoveToCoral1 - Number state (constants)
    //MoveToCoral2and3 - Number state (constants)
    //MoveToCoral4 - Number state (constants)

    //MoveToAlgae - Number state (constants)
    //MoveToProcessor - Number state (constants)
    //MoveToBarge - Number state (constants)
  }



  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kOperatorControllerPort = 1;
  //   public static final int MotorFWM_ID = 0;
  // public static final int MotorFM_ID = 1;
  // public static final int FLMDIOPort = 0;
  // public static final double coralIntakingPos= 0; //will fix
  // public static final double coralOutakingPos=360; //will fix


  // public static final int BLMDIOPort = 1;


  }

  public static final class VisionConstants {
        public static final String limelightFrontName = "limelight-front";
        public static final String limelightBackName = "limelight-back";
        public static final Vector<N3> visionStdDevs = VecBuilder.fill(.7,.7,9999999);
    }
  public class PivotConstants
  {
      public static AvailableState pivotState = AvailableState.LEVEL1;
  }
  
  public static class ClawConstants {
    public static int clawMotorID = 21;
    public static int rangerID = 14;

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
