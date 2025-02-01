// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.subsystems;

// import com.ctre.phoenix6.hardware.TalonFX;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.Constants;
// import edu.wpi.first.wpilibj.DigitalInput;

// public class CoralIntake extends SubsystemBase {
//   /** Creates a new ExampleSubsystem. */
//   public CoralIntake() {}
//     private final TalonFX  flyWheelMotor= new TalonFX(Constants.OperatorConstants.MotorFWM_ID);
//     public final TalonFX flipMotor = new TalonFX(Constants.OperatorConstants.MotorFM_ID);
//     private final DigitalInput forwardLimitSwitch = new DigitalInput(Constants.OperatorConstants.FLMDIOPort); // <-- DIO port (on RoboRIO)
//     private final DigitalInput backwardLimitSwitch = new DigitalInput(Constants.OperatorConstants.BLMDIOPort); // <-- DIO port (on RoboRIO)
//     //COnfigure motors here----->
//     private double frontPos=300; //arbitrary, will fix based on gear ratios etc.
//     private double backPos=-300; //arbitrary, will fix based on gear ratios etc.
//     public double flipMotorPos=flipMotor.getPosition().getValueAsDouble();
//         //private int intakeState= 1;  //doing this a different way
//     /*Represents where it is/which side it has flipped to, 1 represents "front" (algae intake side), 0 represents inbetween (in process of flipping),
//     and -1 represents the "back" (non-algae intake side)*/ 
     


//     public void IntakeCoral()
//     {
//       flyWheelMotor.set(1.0);      // flywheel motor run
//     }
//     public void OutakeCoral()
//     {
//         flyWheelMotor.set(-1.0); //temporary, need to figure out which way is reverse in terms of the way the motor is situated
//       //flywheel motor run in reverse
//     }
//     public void flipIntake() //returns state of intake, forward, inbetween, backward.
//     {  
          
//         if(getIntakeState()==1)
//         {
//             flipMotor.set(1.0);
//         }
//         else if(getIntakeState()==2)
//         {
//             flipMotor.set(-1.0);
//         }
//         else
//         {
//             flipIntake(); //Dangerous (recursive function)--find another way to do this
//         }
//       //flip intake either way- must know which way to flip and which way it is. calls intakeState.

         

//     } 
//     public int getIntakeState() //NEEDS TO STOP ONCE IT IS FULLY TO THE OTHER SIDE. Rewriting based on motor rotations. Look at gear ratio, learn PID. put an arbitrary constant for now.
//     {
//         if (Math.abs(flipMotorPos-frontPos)<=0.5)
//         {
//           return 1; //represents state 1; front
//         }
        
//         else if(Math.abs(flipMotorPos-backPos)<=0.5)
//         {
//           return 2; //represents state 2; back
//         }
//         return 0; //represents state 0; inbetween/traveling to a state
    
//     }
//     public void dislodgeAlgae()
//     {
//       flipMotor.setPosition(60);//arbitrary angle, will research

//       //tilt up by a few degrees, go back to where it was 

//     }
//     public void endFlip()
//     {
//       flipMotor.stopMotor();
//     }
  


//     public void stopIntake()
//     {
//         flyWheelMotor.stopMotor();

//     }
//     public void stopOuttake()
//     {
//         flyWheelMotor.stopMotor();

//     }
//     public void goToIntakingPos()
//     {
//       flipMotor.setPosition(Constants.OperatorConstants.coralIntakingPos);
//     }
//     public void goToOuttakingPos(Constants.OperatorConstants.coralOutakingPos);








//   }

//   /**
//    * Example command factory method.
//    *
//    * @return a command
//    */
  