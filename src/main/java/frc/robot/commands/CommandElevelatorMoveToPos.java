// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.Constants.PivotConstants;
import frc.robot.subsystems.Elevator;
/** An example command that uses an example subsystem. */
public class CommandElevelatorMoveToPos extends Command {
  private Elevator m_elevator;
  private double m_goToPos;
  private double m_currentPos;
  /**
   
  
  * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public CommandElevelatorMoveToPos(Elevator subsystem, double goToPos) {
    addRequirements(subsystem);
    m_elevator = subsystem;
    m_goToPos = goToPos;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_currentPos = m_elevator.getPosition();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //m_pos = m_elevator.getPosition() + m_add;
    m_elevator.moveMethod(m_goToPos, true);
    m_currentPos = m_elevator.getPosition();
    System.out.println("This is running");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if (m_goToPos == ElevatorConstants.L1) {
      m_elevator.setVoltageToZero();
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (Math.abs(m_currentPos-m_goToPos) <= 0.2) ? true : false;
  }
}