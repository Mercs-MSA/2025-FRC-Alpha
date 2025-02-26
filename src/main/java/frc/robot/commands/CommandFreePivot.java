// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants.MotorConstants.AvailableState;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Pivot;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class CommandFreePivot extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Pivot m_pivot;
  private double m_position;
  private boolean m_able = false;
  private double m_change = 0;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public CommandFreePivot(Pivot pivot, double change) {
    addRequirements(pivot);
    m_pivot = pivot;
    m_change = change;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_position = m_pivot.getPosition();
    System.out.println("Initialized");
    if (m_pivot.getPosition() >= 15) {
      m_able = true;
    } else {
      m_able = false;
    }
  }

  @Override
  public void execute() {
    m_pivot.moveMethod(m_position + m_change, (m_position <= 22.5));
    m_position += m_change;
    System.out.println("Moved!");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
