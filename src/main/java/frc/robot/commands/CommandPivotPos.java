// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Pivot;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Constants.MotorConstants;
import frc.robot.Constants.PivotConstants;
import frc.robot.Constants.MotorConstants.AvailableState;

/** An example command that uses an example subsystem. */
public class CommandPivotPos extends Command {
  private Pivot m_Pivot;
  private double m_pos;
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public CommandPivotPos(Pivot subsystem, Double test) {
    addRequirements(subsystem);
    m_Pivot = subsystem;
    m_pos = test;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //m_pos = PivotConstants.pivotState.pivotPosGet();
    m_Pivot.moveMethod(m_pos);
  }
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
