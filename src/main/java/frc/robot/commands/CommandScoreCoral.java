package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Claw;

public class CommandScoreCoral extends Command {
    public final Claw m_claw;
    public CommandScoreCoral(Claw subsystem) {
        addRequirements(subsystem);
        m_claw = subsystem;
    }
    
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_claw.setVoltage(6);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (m_claw.getDistance() > .02)
            return true;
        return false;
    }
}
