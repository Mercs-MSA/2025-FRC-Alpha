// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
//import frc.robot.Constants.MotorConstants.AvailableState;
import frc.robot.Constants.MotorConstants;
//import frc.robot.Constants.PivotConstants;

public class Elevator extends SubsystemBase {
  private final TalonFX m_elevmain = new TalonFX(Constants.MotorConstants.ElevMainID, "canivore"); //keep this, necessary for motor synchronization. need to runcomment motor id
  private final TalonFX m_elevfollower = new TalonFX(Constants.MotorConstants.ElevFollowerID, "canivore");
  private final PositionVoltage elevatorVoltage = new PositionVoltage(0); //is this for resetting at power on?
  private double tolerance=1.0;

  public Elevator() {
    m_elevfollower.setControl(new Follower(MotorConstants.ElevFollowerID, false));
    TalonFXConfiguration configs = new TalonFXConfiguration(); //configs are necessary, keep these. Move PID values to constants
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
    StatusCode status1 = StatusCode.StatusCodeNotInitialized; //this all just reports status, tries to apply configs, if they cant be applied the status is reported
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
    Constants.MotorConstants.modifyState("resting", Constants.MotorConstants.elevIndex);
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


   public void moveMethodL1() //need to interpret this abl does not actually check anything. It is likely also not necessary when
   //tying it to the command groups, as it checks the necessary conditions. will change conditions based on a variable changed when this is successful
   {  
 
      m_elevmain.setControl(elevatorVoltage.withPosition(Constants.MotorConstants.elevatorStateAndValues.get("L1")));
      if(Math.abs(m_elevmain.getPosition().getValueAsDouble()-Constants.MotorConstants.elevatorStateAndValues.get("L1"))<tolerance)
      {
        Constants.MotorConstants.modifyState("L1", Constants.MotorConstants.elevIndex);
        m_elevmain.set(0);
        m_elevfollower.set(0);

      }
      //pivotMotor.setControl(pivotVoltage.withPosition(Constants.MotorConstants.pivotMotorPositions.get(DesiredMotorPos (AvailableState type) )));
    

  }
  public void moveMethodL2()
  {
    m_elevmain.setControl(elevatorVoltage.withPosition(Constants.MotorConstants.elevatorStateAndValues.get("L2")));
    if(Math.abs(m_elevmain.getPosition().getValueAsDouble()-Constants.MotorConstants.elevatorStateAndValues.get("2"))<tolerance)
    {
      Constants.MotorConstants.modifyState("L2", Constants.MotorConstants.elevIndex);
      m_elevmain.set(0);
      m_elevfollower.set(0);
    }
 
  }
  public void moveMethodL3()
  {
    m_elevmain.setControl(elevatorVoltage.withPosition(Constants.MotorConstants.elevatorStateAndValues.get("L3")));
    if(Math.abs(m_elevmain.getPosition().getValueAsDouble()-Constants.MotorConstants.elevatorStateAndValues.get("L3"))<tolerance)
    {
      Constants.MotorConstants.modifyState("L3", Constants.MotorConstants.elevIndex);
      m_elevmain.set(0);
      m_elevfollower.set(0);
    }

  }
  public void moveMethodL4()
  {
    m_elevmain.setControl(elevatorVoltage.withPosition(Constants.MotorConstants.elevatorStateAndValues.get("L4")));
    if(Math.abs(m_elevmain.getPosition().getValueAsDouble()-Constants.MotorConstants.elevatorStateAndValues.get("L4"))<tolerance)
    {
      Constants.MotorConstants.modifyState("L4", Constants.MotorConstants.elevIndex);
      m_elevmain.set(0);
      m_elevfollower.set(0);
    }

  }
  public void moveMethodBarge()
  {
    m_elevmain.setControl(elevatorVoltage.withPosition(Constants.MotorConstants.elevatorStateAndValues.get("barge")));
    if(Math.abs(m_elevmain.getPosition().getValueAsDouble()-Constants.MotorConstants.elevatorStateAndValues.get("barge"))<tolerance)
    {
      Constants.MotorConstants.modifyState("barge", Constants.MotorConstants.elevIndex);
      m_elevmain.set(0);
      m_elevfollower.set(0);
    }

  }
  public void moveMethodCollectCoral()
  {
    m_elevmain.setControl(elevatorVoltage.withPosition(Constants.MotorConstants.elevatorStateAndValues.get("coralIntake")));
    if(Math.abs(m_elevmain.getPosition().getValueAsDouble()-Constants.MotorConstants.elevatorStateAndValues.get("coralIntake"))<tolerance)
    {
      Constants.MotorConstants.modifyState("coralIntake", Constants.MotorConstants.elevIndex);
      m_elevmain.set(0);
      m_elevfollower.set(0);
    }

  }
  public void moveMethodProcessor()
  {
    m_elevmain.setControl(elevatorVoltage.withPosition(Constants.MotorConstants.elevatorStateAndValues.get("processor")));
    if(Math.abs(m_elevmain.getPosition().getValueAsDouble()-Constants.MotorConstants.elevatorStateAndValues.get("processor"))<tolerance)
    {
      Constants.MotorConstants.modifyState("processor", Constants.MotorConstants.elevIndex);
      m_elevmain.set(0);
      m_elevfollower.set(0);
    }

  }
}