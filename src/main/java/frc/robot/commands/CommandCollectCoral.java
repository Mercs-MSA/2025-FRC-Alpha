package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Claw;
import edu.wpi.first.wpilibj.Timer;


public class CommandCollectCoral extends Command {
    public final Claw m_claw;
    public CommandCollectCoral(Claw subsystem) {
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
    public void end(boolean interrupted) {

        m_claw.flywheel.setVoltage(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (m_claw.getDistance() < .02)
        {
            Timer time=new Timer();
            time.start();
            if(time.hasElapsed(Constants.ClawConstants.timeforclawtorun))
            {
                return true;

            }

        }
        return false;
    }
}
