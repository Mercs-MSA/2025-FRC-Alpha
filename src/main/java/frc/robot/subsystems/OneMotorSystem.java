package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class OneMotorSystem extends SubsystemBase{
    TalonFX motor = new TalonFX(0 ); 

    public void setVoltage(double volts){
        motor.setVoltage(volts);
    }

    public double getVoltage(){
        return motor.getMotorVoltage().getValueAsDouble();
    }

    // setMotorVoltage
}