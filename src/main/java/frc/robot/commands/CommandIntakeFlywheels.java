// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.RobotContainer;
import frc.robot.RobotContainer.IntakeAction;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class CommandIntakeFlywheels extends Command {
  //@SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Claw m_claw;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public CommandIntakeFlywheels(Claw claw) {
    m_claw = claw;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(claw);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() 
  {
    m_claw.setVoltage(-2.5);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    //else if (m_IntakeAction == IntakeAction.OUTTAKE) {
    //   if (RobotContainer.isCoralInIntake())
    //     m_claw.setVoltage(0);
    // }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_claw.setVoltage(0);
    if (interrupted) {
      
      m_claw.setVoltage(0);
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_claw.isCoralInIntake();
  }
}
