// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Beambreak;
import frc.robot.Constants.PivotConstants;
import edu.wpi.first.wpilibj2.command.Command;
/** An example command that uses an example subsystem. */
public class CommandElevelatorPos extends Command {
    private Elevator m_elevator;
  private double m_pos;
  /**
   
  
  * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public CommandElevelatorPos(Elevator subsystem) {
    addRequirements(subsystem);
    m_elevator = subsystem;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_pos = PivotConstants.pivotState.elevatorPosGet();
    m_elevator.moveMethod(m_pos, false);
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
    return false;
  }
}
