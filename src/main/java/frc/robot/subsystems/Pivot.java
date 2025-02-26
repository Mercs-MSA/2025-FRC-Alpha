// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicExpoVoltage;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.MotorConstants.AvailableState;
import frc.robot.Constants.PivotConstants;

public class Pivot extends SubsystemBase {
  private final TalonFX pivotMotor = new TalonFX(Constants.MotorConstants.IntakePivot, "rio");
  
  private final PositionVoltage pivotVoltage = new PositionVoltage(0);
  private final MotionMagicExpoVoltage pivotMotionMagicVoltage = new MotionMagicExpoVoltage(0);

  private boolean isMoving = false;



  public Pivot() {
    TalonFXConfiguration configs = new TalonFXConfiguration();
    MotionMagicConfigs motionMagicCon = configs.MotionMagic;
    configs.Slot0.kP = 10; // An error of 1 rotation results in 2.4 V output
    configs.Slot0.kI = 0; // No output for integrated error
    configs.Slot0.kD = 0.1; // A velocity of 1 rps results in 0.1 V output
    // Peak output of 8 V
    configs.Voltage.withPeakForwardVoltage(8)
      .withPeakReverseVoltage(-8);

    motionMagicCon.MotionMagicCruiseVelocity = 9;
    motionMagicCon.MotionMagicAcceleration = 5;
    motionMagicCon.MotionMagicJerk = 0;

    // configs.MotorOutput.NeutralMode = NeutralModeValue.Brake;

    /* Retry config apply up to 5 times, report if failure */
    StatusCode status = StatusCode.StatusCodeNotInitialized;
    for (int i = 0; i < 5; ++i) {
      status = pivotMotor.getConfigurator().apply(configs);
      if (status.isOK()) break;
    }
    if (!status.isOK()) {
      System.out.println("Could not apply configs, error code: " + status.toString());
    }

    /* Make sure we start at 0 */
    pivotMotor.setPosition(0);
    PivotConstants.pivotState = AvailableState.LEVEL1;
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
  @Override
  public void periodic() {
    
  }


  public void moveMethod(Double DesiredMotorPos, boolean Able) {
    if (Able) {
    pivotMotor.setControl(pivotMotionMagicVoltage.withPosition(DesiredMotorPos));
    }
  }

  public void motorArrived() {
    PivotConstants.pivotState = AvailableState.LEVEL2;
  }

    /**
     * Gets the current real position of the pivot motor.
     *
     * @return Value of position is returned as a double
     */
  public double getPosition() {
    return (pivotMotor.getPosition().getValueAsDouble());
  }

  // private boolean isMovingCheck() {
  //   return isMoving;
  // };

  // private void isMovingSet(Boolean update) {
  //   isMoving = update;
  // };
}
