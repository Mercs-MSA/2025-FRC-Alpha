// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.MotorConstants.AvailableState;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Pivot;

/** Takes input elevator, pivot, and a constant state to move to (ex: Level 1, Level 2, etc) */
public class CommandToState extends Command {
  private Elevator m_elevator;
  private Pivot m_pivot;
  //input state to move to
  private AvailableState m_state;
  //whether elevator/pivot can move
  private boolean elevatorCanMove = false;
  private boolean pivotCanMove = false;
  //whether elevaotr/pivot have moved since intialization of command
  private boolean elevatorMoved = false;
  private boolean pivotMoved = false;

  /**
   * assigns parameters and requires subsystems
   *
   * @param elevator The elevator subsystem used by this command.
   * @param pivot The pivot subsystem used by this command.
   * @param state input available state to reference motor positions from constants
   */
  public CommandToState(Elevator elevator, Pivot pivot, AvailableState state) {
    addRequirements(elevator);
    addRequirements(pivot);

    m_elevator = elevator;
    m_pivot = pivot;

    m_state = state;
  }

  /**
   * Checks if elevator can move and not be at a point of interference with the pivot
   * 
   * @return boolean of if it can move
   */
  public boolean canElevatorMove() {
    if (m_pivot.getPosition() <= 2.75) {
      return false;
    } else if (m_pivot.getPosition() > 2.75) {
      return true;
    }
    return false;
  }

  /**
   * Checks if pivot can move and not be at a point of interference with the elevator
   * 
   * @return boolean of if it can move
   */
  public boolean canPivotMove() {
    if (m_elevator.getPosition() <= 0.5 || (m_state.pivotPosGet() >= 2.9 && m_pivot.getPosition() > 2.75)) {
      return true;
    } else if (m_elevator.getPosition() > 0.5) {
      return false;
    }
    return false;
  }
  
  /**
   * @return Checks if elevator has finished movement (with margin of error)
   */
  public boolean elevatorFinished() {
    return (((m_state.elevatorPosGet() - 0.25) < m_elevator.getPosition()) && (m_elevator.getPosition() < (m_state.elevatorPosGet() + 0.25)));
  }

 /**
   * @return Checks if pivot has finished movement (with margin of error)
   */
  public boolean pivotFinished() {
    return (((m_state.pivotPosGet() - 0.05) < m_pivot.getPosition()) && (m_pivot.getPosition() < (m_state.pivotPosGet() + 0.05)));
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    elevatorCanMove = canElevatorMove();
    pivotCanMove = canPivotMove();

    elevatorMoved = false;
    pivotMoved = false;

    m_elevator.moveMethod(m_state.elevatorPosGet(), elevatorCanMove);
    m_pivot.moveMethod(m_state.pivotPosGet(), pivotCanMove);
  }
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Checks if elevator is in a state for pivot to move and only sends movemethon once using the elevatorMoved boolean
    if (((elevatorMoved == false) && (elevatorFinished() || m_state.pivotPosGet() >= 5) && canPivotMove()) || ((elevatorMoved == false) && m_state.pivotPosGet() == 5.0)) {
      elevatorMoved = true;
      m_pivot.moveMethod(m_state.pivotPosGet(), true);
    }

    //Checks if pivot is in a state for elevator to move and only sends movemethon once using the pivotMoved boolean
    if ((pivotMoved == false) && pivotFinished() && canElevatorMove()) {
      pivotMoved = true;
      m_elevator.moveMethod(m_state.elevatorPosGet(), true);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (elevatorFinished() && pivotFinished()) {
      return true;
    }
    return false;
  }
}
