package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VelocityVoltage;

import frc.robot.Constants;

public class Coral extends SubsystemBase {
    private final TalonFX m_main = new TalonFX(Constants.MotorConstants.Flywheelintake, "rio");
    private final VelocityVoltage m_VelocityVoltage = new VelocityVoltage(0).withSlot(0);
    private final VelocityTorqueCurrentFOC m_VelocityTorque = new VelocityTorqueCurrentFOC(0).withSlot(0);
    private final NeutralOut m_brake = new NeutralOut();

    public Coral() {
         TalonFXConfiguration configs = new TalonFXConfiguration();

    /* Voltage-based velocity requires a velocity feed forward to account for the back-emf of the motor */
    configs.Slot0.kS = 0.1; // To account for friction, add 0.1 V of static feedforward
    configs.Slot0.kV = 0.12; // Kraken X60 is a 500 kV motor, 500 rpm per V = 8.333 rps per V, 1/8.33 = 0.12 volts / rotation per second
    configs.Slot0.kP = 0.11; // An error of 1 rotation per second results in 0.11 V output
    configs.Slot0.kI = 0; // No output for integrated error
    configs.Slot0.kD = 0; // No output for error derivative
    // Peak output of 8 volts
    configs.Voltage.withPeakForwardVoltage(8.0)
      .withPeakReverseVoltage(8.0);

    /* Torque-based velocity does not require a velocity feed forward, as torque will accelerate the rotor up to the desired velocity by itself */
    configs.Slot1.kS = 2.5; // To account for friction, add 2.5 A of static feedforward
    configs.Slot1.kP = 5; // An error of 1 rotation per second results in 5 A output
    configs.Slot1.kI = 0; // No output for integrated error
    configs.Slot1.kD = 0; // No output for error derivative
    // Peak output of 40 A
    configs.TorqueCurrent.withPeakForwardTorqueCurrent(40.0)
      .withPeakReverseTorqueCurrent(-40.0);

    /* Retry config apply up to 5 times, report if failure */
    StatusCode status = StatusCode.StatusCodeNotInitialized;
    for (int i = 0; i < 5; ++i) {
      status = m_main.getConfigurator().apply(configs);
      if (status.isOK()) break;
    }
    if (!status.isOK()) {
        System.out.println("Could not apply configs, error code: " + status.toString());
    }
    }


    public void CoralIn(boolean onoff, int state) {
        int rpm;
        if (onoff == true) {
            rpm = Constants.MotorConstants.CoralIntakeRPM;
        } else {
            rpm = 0;
        }
        m_main.setControl(m_VelocityVoltage.withVelocity(rpm*state));
    }

    public void CoralBrake() {
        m_main.setControl(m_brake);
    }
}
