// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DigitalInput;

public class CoralIntake extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  public CoralIntake() {}
    private final TalonFX  flyWheelMotor= new TalonFX(Constants.OperatorConstants.MotorFWM_ID);
    private final TalonFX flipMotor = new TalonFX(Constants.OperatorConstants.MotorFM_ID);
    private final DigitalInput forwardLimitSwitch = new DigitalInput(Constants.OperatorConstants.FLMDIOPort); // <-- DIO port (on RoboRIO)
    private final DigitalInput backwardLimitSwitch = new DigitalInput(Constants.OperatorConstants.BLMDIOPort); // <-- DIO port (on RoboRIO)
    //COnfigure motors here----->


    private int intakeState= 1; 
    /*Represents where it is/which side it has flipped to, 1 represents "front" (algae intake side), 0 represents inbetween (in process of flipping),
    and -1 represents the "back" (non-algae intake side)*/ 
     


    public void IntakeCoral()
    {
      flyWheelMotor.set(1.0);      // flywheel motor run
    }
    public void OutakeCoral()
    {
        flyWheelMotor.set(-1.0); //temporary, need to figure out which way is reverse in terms of the way the motor is situated
      //flywheel motor run in reverse
    }
    public int IntakeState() //returns state of intake, forward, inbetween, backward.
    {
       
            if (forwardLimitSwitch.get()) 
            {
                intakeState = 1; // Forward position
            } else if (backwardLimitSwitch.get()) {
                intakeState = -1; // Backward position
            } else {
                intakeState = 0; // In-between
            }
            return intakeState;
        // check state with sensors or something, update state variable, return state variable

    } 
    public void flipIntake() //NEEDS TO STOP ONCE IT IS FULLY TO THE OTHER SIDE. Rewriting based on motor rotations. Look at gear ratio. put an arbitrary constant for now.
    {

      
    }


    /*{
        if(IntakeState()==1.0)
        {
            flipMotor.set(1);
        }
        else if(IntakeState()==-1.0)
        {
            flipMotor.set(-1.0);
        }
        else
        {
            flipIntake(); //Dangerous (recursive function)--find another way to do this
        }
      //flip intake either way- must know which way to flip and which way it is. calls intakeState.

    }*/
    public void stopIntake()
    {
        flyWheelMotor.set(0.0);

    }








  }

  /**
   * Example command factory method.
   *
   * @return a command
   */
  