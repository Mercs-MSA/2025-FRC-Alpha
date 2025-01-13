package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class elevator extends SubsystemBase 
{
    private final PWMSparkMax elevatorMotor = new PWMSparkMax(0);
    elevatorMotor.setVelocity(); //initalize the motor to some value
    private int elevatorOffset = 10; //need to find an offset value
    //if some button(button1) is pressed, then set the motor to some value
    //the elevator goes up to a specific position

    //create two buttons that adjust the elevator position by an offset
    //if button2 pressed, move current elevator position by +offset
    //if button3 pressed, move current elevator position by -offset


}


// public void elevatorSetPosition () 
// {


// }

public void robotPeriodic() 
{
    //update the position of elevator motor
}


