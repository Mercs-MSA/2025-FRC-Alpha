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


public class Claw extends SubsystemBase {
    //Voltage motor initialized and configured here
    private CANBus fake = new CANBus("rio");
    //private CANrange ranger = new CANrange(Constants.ClawConstants.rangerID, fake.getName());
    public TalonFX flywheel = new TalonFX(Constants.MotorConstants.Flywheelintake, fake.getName());
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
        perfectCoralPosition = flywheel.getPosition().getValueAsDouble() - 2;
    }

    public void runMotorToPerfectPosition() {
        //get gear ratio: 2:1, get distance: 6.25inches , get circumfrence of wheel: 2 inches,
        flywheel.setControl(clawVoltage.withPosition(perfectCoralPosition));
    }

    public boolean isCoralAtPerfectPosition() {
        return (Math.abs(perfectCoralPosition - flywheel.getPosition().getValueAsDouble()) <= 0.1);
    }
    

    @Override
    public void periodic() {
        //output current state
        SmartDashboard.putNumber("Flywheel voltage: ", getVoltage());
    }
}
