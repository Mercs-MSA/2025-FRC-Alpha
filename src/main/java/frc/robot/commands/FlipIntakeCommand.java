// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.CoralIntake;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class FlipIntakeCommand extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final CoralIntake m_CoralIntake;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public FlipIntakeCommand(CoralIntake subsystem) {
    m_CoralIntake = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() 
  {
    m_CoralIntake.getIntakeState();
    m_CoralIntake.flipIntake();


  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_CoralIntake.endFlip();
    m_CoralIntake.flipMotorPos=m_CoralIntake.flipMotor.getPosition().getValueAsDouble();
  }
//
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(m_CoralIntake.getIntakeState()==1 || m_CoralIntake.getIntakeState()==2)
    {
      return true;
    }
    return false;
  }
}
