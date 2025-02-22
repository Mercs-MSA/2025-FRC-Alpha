// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Pivot;
import frc.robot.Constants;
import frc.robot.Constants.MotorConstants;
import frc.robot.Constants.MotorConstants.AvailableState;
import frc.robot.Constants.PivotConstants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Timer;

/** An example command that uses an example subsystem. */
public class CommandScoreState extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Elevator m_elevator;
  private final Pivot m_pivot;
  private final AvailableState m_teststate = AvailableState.LEVEL2;
  private double starttime = 0.0;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public CommandScoreState(Elevator elevator, Pivot pivot) {
    addRequirements(elevator);
    addRequirements(pivot);
    m_elevator = elevator;
    m_pivot = pivot;
    
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    starttime = Timer.getFPGATimestamp();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
   // if (PivotConstants.pivotState.pivotPosGet() < 2.8) {
      if (m_pivot.getPositionPivot() < 2.8) {
        m_pivot.moveMethod(m_teststate.pivotPosGet());  //~5
      } else {
        m_pivot.moveMethod(m_teststate.pivotPosGet());  //  ~5
        m_elevator.moveMethod(m_teststate.elevatorPosGet(), true);  //~5
      }
      if (m_pivot.getPositionPivot() > 4.5) {
        System.out.println(Timer.getFPGATimestamp() - starttime);
      }
    }
  

  @Override
  public void end(boolean interrupted) {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (m_pivot.getPositionPivot() > 4.75) {
      return true;
    }
    return false;
  }
}
