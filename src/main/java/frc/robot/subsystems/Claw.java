package frc.robot.subsystems;

import java.io.Flushable;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.configs.CANrangeConfiguration;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicExpoVoltage;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.CANrange;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.OperatorConstants;
import au.grapplerobotics.CanBridge;
import au.grapplerobotics.ConfigurationFailedException;
import au.grapplerobotics.LaserCan;


public class Claw extends SubsystemBase {
    //Voltage motor initialized and configured here
    private CANBus fake = new CANBus("rio");
    private CANrange ranger = new CANrange(Constants.SensorConstants.rangerID, fake.getName());
    public TalonFX flywheel = new TalonFX(Constants.MotorConstants.Flywheelintake, fake.getName());
    public static final LaserCan laser = new LaserCan(Constants.SensorConstants.rangerID);
    private final MotionMagicExpoVoltage clawVoltage = new MotionMagicExpoVoltage(0);

    private static double perfectCoralPosition;



    //Enum here to keep track of state
    public static enum CLAW_STATES {
        STOPPED_STATE,
        SCORING_CORAL_STATE,
        INTAKING_ALGAE_STATE,
        SCORING_ALGAE_STATE,
    }

    private static CLAW_STATES currentClawState = CLAW_STATES.STOPPED_STATE;

    public Claw() {
        try {
            laser.setRangingMode(LaserCan.RangingMode.SHORT);
            laser.setRegionOfInterest(new LaserCan.RegionOfInterest(8, 8, 16, 16));
            laser.setTimingBudget(LaserCan.TimingBudget.TIMING_BUDGET_33MS);
          } catch (ConfigurationFailedException e) {
            System.out.println("Configuration failed! " + e);
          }
          CanBridge.runTCP();
    
    
        //ranger.getConfigurator().apply(configs);
        TalonFXConfiguration configs = new TalonFXConfiguration();
        MotionMagicConfigs motionMagicCon = configs.MotionMagic;
        configs.Slot0.kP = 5; // An error of 1 rotation results in 2.4 V output
        configs.Slot0.kI = 0.5; // No output for integrated error
        configs.Slot0.kD = 0.1; // A velocity of 1 rps results in 0.1 V output
        configs.Slot0.kG = 0;
        // Peak output of 8 V
        configs.Voltage.withPeakForwardVoltage(8)
        .withPeakReverseVoltage(-8);

        motionMagicCon.MotionMagicCruiseVelocity = 30;
        motionMagicCon.MotionMagicAcceleration = 10;
        motionMagicCon.MotionMagicJerk = 0;

        flywheel.setNeutralMode(NeutralModeValue.Brake);
    }

    //Set voltage function
    public void setVoltage(double volts) {
        flywheel.setVoltage(volts);
    }

    //Get voltage function
    public double getVoltage() {
        return flywheel.getMotorVoltage().getValueAsDouble();
    }

    public void setPerfectPosition() {
        perfectCoralPosition = flywheel.getPosition().getValueAsDouble() - 2.25;
    }

    public void runMotorToPerfectPosition() {
        //get gear ratio: 2:1, get distance: 6.25inches , get circumfrence of wheel: 2 inches,
        flywheel.setControl(clawVoltage.withPosition(perfectCoralPosition));

    }
    public boolean isAtPerfectPosition()
    {
        if((Math.abs(flywheel.getPosition().getValueAsDouble()-perfectCoralPosition)<.1))
        {
            return true;
        }
        return false;

    }
    
    public  boolean isCoralInIntake() {
        LaserCan.Measurement measurement = laser.getMeasurement();
            //only returns true for distances equal to or less than 100mm/10cm so it shouldnt accidently be triggered
            if (measurement != null && measurement.status == LaserCan.LASERCAN_STATUS_VALID_MEASUREMENT && measurement.distance_mm <= 100) {
              
              
              return true;
            } else {
              return false;
            }
    }

    // public boolean isCoralAtPerfectPosition() {
       
         
    // }
    

    @Override
    public void periodic() {
        //output current state
        SmartDashboard.putNumber("Flywheel voltage: ", getVoltage());
        SmartDashboard.putNumber("Distance to object (mm)", laser.getMeasurement().distance_mm);
        SmartDashboard.putNumber("status", laser.getMeasurement().status);
        SmartDashboard.putBoolean("coralIn", isCoralInIntake());

    }
}
