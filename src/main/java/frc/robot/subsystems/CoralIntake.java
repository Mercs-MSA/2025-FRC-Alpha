// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
public class CoralIntake extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  public CoralIntake() {
    private final TalonFX  flyWheelMotor= new TalonFX(MotorOne_ID);
    private final TalonFX flipMotor = new TalonFX(MotorTwo_ID);


    public void IntakeCoral()
    {
      flyWheelMotor.setControl()
      // flywheel motor run
    }
    public void OutakeCoral()
    {
      //flywheel motor run in reverse
    }
    public void flipIntake()

    {
      //flip intake either way- must know which way to flip and which way it is

    }








  }

  /**
   * Example command factory method.
   *
   * @return a command
   */
  