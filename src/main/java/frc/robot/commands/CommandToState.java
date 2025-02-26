// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.MotorConstants.AvailableState;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Pivot;

/** An example command that uses an example subsystem. */
public class CommandToState extends Command {
  private Elevator m_elevator;
  private Pivot m_pivot;
  private AvailableState m_state;

  private boolean elevatorCanMove = false;
  private boolean pivotCanMove = false;

  private boolean elevatorMoved = false;
  private boolean pivotMoved = false;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public CommandToState(Elevator elevator, Pivot pivot, AvailableState state) {
    addRequirements(elevator);
    addRequirements(pivot);
    m_elevator = elevator;
    m_pivot = pivot;
    m_state = state;
  }


  public boolean canElevatorMove() {
    if (m_pivot.getPosition() <= 2.75) {
      return false;
    } else if (m_pivot.getPosition() > 2.75) {
      return true;
    }
    return false;
  }


  public boolean canPivotMove() {
    if (m_elevator.getPosition() <= 0.5 || (m_state.pivotPosGet() >= 2.9 && m_pivot.getPosition() > 2.75)) {
      return true;
    } else if (m_elevator.getPosition() > 0.5) {
      return false;
    }
    return false;
  }
  

  public boolean elevatorFinished() {
    return (((m_state.elevatorPosGet() - 0.25) < m_elevator.getPosition()) && (m_elevator.getPosition() < (m_state.elevatorPosGet() + 0.25)));
  }


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
    // System.out.println("canElevatorMove: " + canElevatorMove());
    // System.out.println("ElevatorPos: " + m_elevator.getPosition());
    // System.out.println("canPivotMove: " + canPivotMove());
    // System.out.println("PivotPos: " + m_pivot.getPosition());
  }
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (((elevatorMoved == false) && (elevatorFinished() || m_state.pivotPosGet() >= 5) && canPivotMove()) || ((elevatorMoved == false) && m_state.pivotPosGet() == 5.0)) {
      elevatorMoved = true;
      m_pivot.moveMethod(m_state.pivotPosGet(), true);
    }
    if ((pivotMoved == false) && pivotFinished() && canElevatorMove()) {
      pivotMoved = true;
      // System.out.println("Yooooooooookjnjergnrij");
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
