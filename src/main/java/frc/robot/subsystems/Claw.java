package frc.robot.subsystems;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.configs.CANrangeConfiguration;
import com.ctre.phoenix6.hardware.CANrange;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.OperatorConstants;


public class Claw extends SubsystemBase {
    //Voltage motor initialized and configured here
    private CANBus fake = new CANBus("rio");
    private CANrange ranger = new CANrange(Constants.ClawConstants.rangerID, fake.getName());
    public TalonFX flywheel = new TalonFX(Constants.ClawConstants.clawMotorID, fake.getName());

    CANrangeConfiguration configs = new CANrangeConfiguration();

    //Enum here to keep track of state
    public static enum CLAW_STATES {
        STOPPED_STATE,
        SCORING_CORAL_STATE,
        INTAKING_ALGAE_STATE,
        SCORING_ALGAE_STATE,
    }

    private static CLAW_STATES currentClawState = CLAW_STATES.STOPPED_STATE;

    public Claw() {
        ranger.getConfigurator().apply(configs);
    }

    //Set voltage function
    public void setVoltage(double volts) {
        flywheel.setVoltage(volts);
    }

    //Get voltage function
    public double getVoltage() {
        return flywheel.getMotorVoltage().getValueAsDouble();
    }

    public double getDistance() {
        return ranger.getDistance().getValueAsDouble();
    }

    @Override
    public void periodic() {
        //output current state
        SmartDashboard.putNumber("Flywheel voltage: ", getVoltage());
    }
}
