package frc.robot.subsystems;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.OperatorConstants;


public class Claw extends SubsystemBase {
    //Voltage motor initialized and configured here
    private CANBus fake = new CANBus("temp");
    private TalonFX flywheel = new TalonFX(Constants.OperatorConstants.clawMotorID, fake.getName());
    //Enum here to keep track of state
    public static enum CLAW_STATES {
        STOPPED_STATE,
        SCORING_CORAL_STATE,
        INTAKING_ALGAE_STATE,
        SCORING_ALGAE_STATE,
    }

    private static CLAW_STATES currentClawState = CLAW_STATES.STOPPED_STATE;

    public Claw() {}

    //Set voltage function
    public void setVoltage(double volts) {
        flywheel.setVoltage(volts);
    }

    //Get voltage function
    public double getVoltage() {
        return flywheel.getMotorVoltage().getValueAsDouble();
    }

    @Override
    public void periodic() {
        //output current state
        SmartDashboard.putNumber("Flywheel voltage: ", getVoltage());
    }
}
