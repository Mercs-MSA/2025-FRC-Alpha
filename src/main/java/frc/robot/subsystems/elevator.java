package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class elevator extends SubsystemBase 
{
    private final PWMSparkMax elevatorMotor = new PWMSparkMax(0);
    elevatorMotor.setVelocity(); //initalize the motor to some value
    private int elevatorOffset; //need to find an offset value
    //if some button(button1) is pressed, then set the motor to some value
    //the elevator goes up to a specific position
    //if button1 pressed, go to l1 pos
    //if button2 pressed, go to l2 pos
    //if button3 pressed, go to l3 pos
    //if button4 pressed, go to l4 pos

    //create two buttons that adjust the elevator position by an offset
    //if button5 pressed, move current elevator position by +offset
    //if button6 pressed, move current elevator position by -offset


}


// public void elevatorSetPosition () 
// {


// }

public void robotPeriodic() 
{
    //update the position of elevator motor
}


