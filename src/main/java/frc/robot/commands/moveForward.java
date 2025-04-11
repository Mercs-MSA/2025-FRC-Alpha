package frc.robot.commands;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class  moveForward extends Command {
    private TalonFX drive;
    

    int time = 0;
    
        @Override
        public void initialize() {
            drive.setVoltage(1);
            System.out.println("Started");
        }
        @Override
        public boolean isFinished() {
            return time > 100;      }
        @Override
        public void execute() {
            time = time + 20;
        }
    
        public moveForward(TalonFX drive){
            this.drive = drive;
        }

        @Override
        public void end(boolean interrupted) {
            drive.setVoltage(0);
            System.out.println("Ended");
        }
}
