// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DigitalInput;


import static edu.wpi.first.units.Units.*;


import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;


import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;




public class YajatAlgae extends SubsystemBase {
   public TalonFX rollerMotor = new TalonFX(0);
    public TalonFX armMotor = new TalonFX(1);
    private VelocityVoltage m_velocityVoltage = new VelocityVoltage(0).withSlot(0);
    private VelocityTorqueCurrentFOC m_velocityTorque = new VelocityTorqueCurrentFOC(0).withSlot(1);
    // public DigitalInput frontLimitSwitch = new DigitalInput(0);
    // public DigitalInput backLimitSwitch = new DigitalInput(0);


   public YajatAlgae() {
   //Configuration for both TalonFX Motors
   configArmMotor();
   configRollerMotor();
   
 }

public void configRollerMotor() {
  TalonFXConfiguration configs = new TalonFXConfiguration();

  /* Voltage-based velocity requires a velocity feed forward to account for the back-emf of the motor */
  configs.Slot0.kS = 0.1; // To account for friction, add 0.1 V of static feedforward
  configs.Slot0.kV = 0.12; // Kraken X60 is a 500 kV motor, 500 rpm per V = 8.333 rps per V, 1/8.33 = 0.12 volts / rotation per second
  configs.Slot0.kP = 0.11; // An error of 1 rotation per second results in 0.11 V output
  configs.Slot0.kI = 0; // No output for integrated error
  configs.Slot0.kD = 0; // No output for error derivative
  // Peak output of 8 volts
  configs.Voltage.withPeakForwardVoltage(Volts.of(8))
    .withPeakReverseVoltage(Volts.of(-8));

  /* Torque-based velocity does not require a velocity feed forward, as torque will accelerate the rotor up to the desired velocity by itself */
  configs.Slot1.kS = 2.5; // To account for friction, add 2.5 A of static feedforward
  configs.Slot1.kP = 5; // An error of 1 rotation per second results in 5 A output
  configs.Slot1.kI = 0; // No output for integrated error
  configs.Slot1.kD = 0; // No output for error derivative
  // Peak output of 40 A
  configs.TorqueCurrent.withPeakForwardTorqueCurrent(Amps.of(40))
    .withPeakReverseTorqueCurrent(Amps.of(-40));

  /* Retry config apply up to 5 times, report if failure */
  StatusCode status = StatusCode.StatusCodeNotInitialized;
  for (int i = 0; i < 5; ++i) {
    status = rollerMotor.getConfigurator().apply(configs);
    if (status.isOK()) break;
  }
  if (!status.isOK()) {
    System.out.println("Could not apply configs, error code: " + status.toString());
  }

  rollerMotor.setControl(new Follower(rollerMotor.getDeviceID(), false));
}

public void configArmMotor() {
  TalonFXConfiguration configs = new TalonFXConfiguration();
    configs.Slot0.kP = 2.4; // An error of 1 rotation results in 2.4 V output
    configs.Slot0.kI = 0; // No output for integrated error
    configs.Slot0.kD = 0.1; // A velocity of 1 rps results in 0.1 V output
    // Peak output of 8 V
    configs.Voltage.withPeakForwardVoltage(Volts.of(8))
      .withPeakReverseVoltage(Volts.of(-8));

    configs.Slot1.kP = 60; // An error of 1 rotation results in 60 A output
    configs.Slot1.kI = 0; // No output for integrated error
    configs.Slot1.kD = 6; // A velocity of 1 rps results in 6 A output
    // Peak output of 120 A
    configs.TorqueCurrent.withPeakForwardTorqueCurrent(Amps.of(120))
      .withPeakReverseTorqueCurrent(Amps.of(-120));

    /* Retry config apply up to 5 times, report if failure */
    StatusCode status = StatusCode.StatusCodeNotInitialized;
    for (int i = 0; i < 5; ++i) {
      status = armMotor.getConfigurator().apply(configs);
      if (status.isOK()) break;
    }
    if (!status.isOK()) {
      System.out.println("Could not apply configs, error code: " + status.toString());
    }

    /* Make sure we start at 0 */
    armMotor.setPosition(0);
}

public void algaeRoller(boolean click){
  int rpm;
  if (click == true){
    rpm = 10;
  } else{
    rpm = 0;
  }
  rollerMotor.setControl(m_velocityVoltage.withVelocity(rpm));
  rollerMotor.setControl(m_velocityTorque.withVelocity(rpm));
}



 
 /*
  * Example command factory method.
  *
  * @return a command
  */
 public Command exampleMethodCommand() {
   // Inline construction of command goes here.
   // Subsystem::RunOnce implicitly requires `this` subsystem.
   return runOnce(
       () -> {
         /* one-time action goes here */
       });
 }


 /**
  * An example method querying a boolean state of the subsystem (for example, a digital sensor).
  *
  * @return value of some boolean subsystem state, such as a digital sensor.
  */
 public boolean exampleCondition() {
   // Query some boolean state, such as a digital sensor.
   return false;
 }


 @Override
 public void periodic() {
   // This method will be called once per scheduler run
   
 }


 @Override
 public void simulationPeriodic() {
   // This method will be called once per scheduler run during simulation
 }
}

