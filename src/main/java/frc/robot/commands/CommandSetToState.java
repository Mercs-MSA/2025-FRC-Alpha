// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants.MotorConstants;
import frc.robot.Constants.MotorConstants.AvailableState;
import edu.wpi.first.wpilibj2.command.Command;

/** Takes input AvailableState enum and sends it to the constant
 *  buffer variable toState to be used in CommandToState by driver
*/
public class CommandSetToState extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private AvailableState m_cancelstate;

  /**
   * Takes input AvailableStatee enum from constants
   *
   * @param cancelstate Input AvailableState enum
   */
  public CommandSetToState(AvailableState cancelstate) {
    m_cancelstate = cancelstate;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //updates buffer variable in Constants from the operator so the driver can use it
    MotorConstants.toState = m_cancelstate;
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
