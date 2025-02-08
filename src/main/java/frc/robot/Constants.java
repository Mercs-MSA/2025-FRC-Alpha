// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.math.controller.ElevatorFeedforward;
//import frc.robot.Constants.MotorConstants.AvailableState; //both referencing something inside itself, the constants file?
//import frc.robot.Constants.PivotConstants;

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

  

  public static class MotorConstants { //commenting out the following ints for now as they appear to have no implementation
    
    //motor ports
    public static final int FlywheelAlgaeID=0;
    public static final int FlywheelCoralID=1;
    public static final int ElevMainID=2;
    public static final int ElevFollowerID=3;
    public static final int pivotMotorID=4;

    //current states collected
    public static String[] state=new String[4]; //elev,pivot,intake/claw,algae intake
    public static int elevIndex=0;
    public static int pivotIndex=1;
    public static int clawIndex=2;
    public static int algaeIntakeIndex=3;

    //states elvator
    public static String[] elevatorState=null;
    public static HashMap <String,Double> elevatorStateAndValues=new HashMap<>();
    public static void setValuesElev()
    {
    elevatorStateAndValues.put("restPos",10.0); //arbitrary values, will change with testing
    elevatorStateAndValues.put("processor",15.0); // I dont know if this is necessary, but it cant hurt
    elevatorStateAndValues.put("coralIntake",20.0);
    elevatorStateAndValues.put("L1",30.0);
    elevatorStateAndValues.put("L2",40.0);
    elevatorStateAndValues.put("L3",50.0);
    elevatorStateAndValues.put("L4",60.0);
    elevatorStateAndValues.put("Barge",70.0);
    elevatorStateAndValues.put("resting",0.0);
    }
    //states pivot
    public static String pivotState=null;
    public static HashMap <String,Double> pivotStateAndValues=new HashMap<>();
    public static void setValuesPivot()
    {
    pivotStateAndValues.put("coralIntaking",10.0); //arbitrary, will change
    pivotStateAndValues.put("coralOuttake",15.0); 
    pivotStateAndValues.put("barge",40.0);
    pivotStateAndValues.put("algaeIntake",50.0);
    pivotStateAndValues.put("processor",20.0);
    pivotStateAndValues.put("L4",40.0);
    pivotStateAndValues.put("resting",0.0);
    }
    //states coral intake/claw
    public static String clawState=null;
    public static HashMap <String,Double> clawStateAndValues=new HashMap<>();
    public static void setValuesClaw()
    {
    elevatorStateAndValues.put("clawRunning",1.0); //on or off
    elevatorStateAndValues.put("clawOff",0.0); 
    
    }
    //states algae intake
    public static String algaeIntakeState=null;
    public static HashMap <String,Double> algaeIntakeStateAndValues=new HashMap<>();
    public static void setValuesAlgaeIntake()
    {
    elevatorStateAndValues.put("algaeIntakeRunning",1.0); //on or off
    elevatorStateAndValues.put("algaeIntakeOff",0.0); 
    
    } 

    //just changes state in array
    public static void modifyState(String changestate, int index)
    {
      state[index]=changestate;
    }


   

    
    // public static final int Flywheelintake = 1;
    // public static final int IntakePivot = 2;
    // public static final int ElevatorMain = 3;
    // public static final int ElevatorFollower = 4;
    // public static final int CoralIntakeRPM = 10;

    //public static String state = "Move"; //seems redundant or uneccesary with the enums

    //  public enum AvailableState { ELIMINATING FOR NOW, AS THEY SEEM UNNECESSARY AND CONFUSING
    //   LEVEL1(10.0, 10.0),
    //   LEVEL2(20.0, 20.0),
    //   LEVEL3(30.0, 30.0),
    //   LEVEL4(40.0, 40.0);

    //   private double pivotPos;
    //   private double elevatorPos;

    //   private AvailableState(Double pivotPos, Double elevatorPos)
    //   {
    //     this.pivotPos = pivotPos;
    //     this.elevatorPos = elevatorPos;
    //  }

    //   public double pivotPosGet() {
    //     return this.pivotPos;
    //   };

    //   public double elevatorPosGet() {
    //     return this.elevatorPos;
    //   };
    //  }


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


   //anything about sensors --->

   public final class sensorConstants
   {
     public static final int CANrangeID=6;

   }





  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
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
  // public class PivotConstants
  // {
  //     public static AvailableState pivotState = AvailableState.LEVEL1;
  // }

  // public static class ClawConstants {
  //   public static int clawMotorID = 0;
  //   public static int rangerID = 0;

  //   public enum clawState {
  //     CORAL_INTAKING_STATE,
  //     CORAL_SCORING_STATE,
  //     ALGAE_INTAKING_STATE,
  //     ALGAE_SCORING_STATE,
  //     CORAL_HOLDING_STATE,
  //     ALGAE_HOLDING_STATE
  //   }

  //   public static double timeforclawtorun = 0.2;
  //   public static double clawVoltage = 6;
  }
