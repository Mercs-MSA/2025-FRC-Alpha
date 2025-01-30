package frc.robot.commands;
import frc.robot.subsystems.OneMotorSystem;

import java.time.Clock;

import edu.wpi.first.wpilibj2.command.Command;

public class testCommand extends Command {
    OneMotorSystem oneMotor;
    long time;

    public testCommand(OneMotorSystem oneMotor){
        this.oneMotor = oneMotor;
    }



    

    @Override
    public void initialize() {
        time = System.currentTimeMillis();
        
    }

    @Override
    public void execute() {
        oneMotor.setVoltage(2);
    }

    @Override
    public void end(boolean interrupted) {
        oneMotor.setVoltage(0);
    }

    @Override
    public boolean isFinished() {
        if((System.currentTimeMillis() - time) > 3000){
            return true;

        }else{
            return false;
        }
    }
}
