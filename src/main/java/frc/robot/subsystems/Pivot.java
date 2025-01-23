// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Pivot extends SubsystemBase {
  private final TalonFX m_pivot = new TalonFX(Constants.MotorConstants.IntakePivot, "canivore");
  




  public Pivot() {
    TalonFXConfiguration configs = new TalonFXConfiguration();
    configs.Slot0.kP = 2.4; // An error of 1 rotation results in 2.4 V output
    configs.Slot0.kI = 0; // No output for integrated error
    configs.Slot0.kD = 0.1; // A velocity of 1 rps results in 0.1 V output
    // Peak output of 8 V
    configs.Voltage.withPeakForwardVoltage(8)
      .withPeakReverseVoltage(-8);

    configs.Slot1.kP = 60; // An error of 1 rotation results in 60 A output
    configs.Slot1.kI = 0; // No output for integrated error
    configs.Slot1.kD = 6; // A velocity of 1 rps results in 6 A output
    // Peak output of 120 A
    configs.TorqueCurrent.withPeakForwardTorqueCurrent(40)
      .withPeakReverseTorqueCurrent(-40);

    /* Retry config apply up to 5 times, report if failure */
    StatusCode status = StatusCode.StatusCodeNotInitialized;
    for (int i = 0; i < 5; ++i) {
      status = m_pivot.getConfigurator().apply(configs);
      if (status.isOK()) break;
    }
    if (!status.isOK()) {
      System.out.println("Could not apply configs, error code: " + status.toString());
    }

    /* Make sure we start at 0 */
    m_pivot.setPosition(0);
  }
  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */



  //MoveToIntakePosition - Number state (constants)
  //MoveToCoral1 - Number state (constants)
  //MoveToCoral2and3 - Number state (constants)
  //MoveToCoral4 - Number state (constants)

  //MoveToAlgae - Number state (constants)
  //MoveToProcessor - Number state (constants)
  //MoveToBarge - Number state (constants)


  //Moving - Boolean? (within file)
  //Moving used in initialization of command

  //int CurrentMotorPos
  //String DesiredMotorPos - will call to a map in constants for motor position number
  public void moveMethod(int CurrentMotorPos, String DesiredMotorPos) {
    //methamphetamine
    
  }

}
