package frc.robot.commands;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.Constants.MotorConstants;
import frc.robot.subsystems.Claw;

public class CommandScoreCoral extends Command {

    public final Claw m_claw;
    public boolean m_startstate=false;
    public boolean m_backwards;

    public CommandScoreCoral(Claw subsystem, boolean backwards) {
        addRequirements(subsystem);
        m_claw = subsystem;
        m_backwards = backwards;
    }
    
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        if(!(m_claw.isCoralInIntake())) {
            m_startstate = true;
        } 
        if (m_backwards == false) {
            m_claw.setVoltage(-3);
        } else if (m_backwards == true) {
            m_claw.setVoltage(3);
        }
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_claw.setVoltage(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return m_claw.isCoralInIntake();

    }
}
