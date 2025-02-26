// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicExpoVoltage;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.MotorConstants.AvailableState;
import frc.robot.Constants.MotorConstants;
import frc.robot.Constants.PivotConstants;

public class Elevator extends SubsystemBase {
  private final TalonFX m_elevmain = new TalonFX(Constants.MotorConstants.ElevatorMain, "rio");
  private final TalonFX m_elevfollower = new TalonFX(Constants.MotorConstants.ElevatorFollower, "rio");
  private final PositionVoltage elevatorVoltage = new PositionVoltage(0);
  private final MotionMagicExpoVoltage elevatorMotionMagicVoltage = new MotionMagicExpoVoltage(0);

  public Elevator() {
    m_elevfollower.setControl(new Follower(MotorConstants.ElevatorMain, false));
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

    configs.MotorOutput.NeutralMode = NeutralModeValue.Brake;

    /* Retry config apply up to 5 times, report if failure */
    StatusCode status1 = StatusCode.StatusCodeNotInitialized;
    for (int i = 0; i < 5; ++i) {
      status1 = m_elevmain.getConfigurator().apply(configs);
      if (status1.isOK()) break;
    }
    if (!status1.isOK()) {
      System.out.println("Could not apply configs, error code: " + status1.toString());
    }

    StatusCode status2 = StatusCode.StatusCodeNotInitialized;
    for (int i = 0; i < 5; ++i) {
      status2 = m_elevfollower.getConfigurator().apply(configs);
      if (status2.isOK()) break;
    }
    if (!status2.isOK()) {
      System.out.println("Could not apply configs, error code: " + status2.toString());
    }

    /* Make sure we start at 0 */
    m_elevmain.setPosition(0);
    m_elevfollower.setPosition(0);
  }



  /**
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


   public void moveMethod(Double DesiredMotorPos, boolean Able) {
    if (Able == true) {
      m_elevmain.setControl(elevatorMotionMagicVoltage.withPosition(DesiredMotorPos));
      //pivotMotor.setControl(pivotVoltage.withPosition(Constants.MotorConstants.pivotMotorPositions.get(DesiredMotorPos (AvailableState type) )));
    }
  }

  @Override
  public void periodic() {
    //System.out.println("Elevator pos:"+ getPosition());
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */    
    public double getPosition() {
      return (m_elevmain.getPosition().getValueAsDouble());
    }
  }