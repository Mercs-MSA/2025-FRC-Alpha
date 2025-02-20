// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Pivot;

public class CommandGroups extends SubsystemBase {
  Elevator m_elevator=new Elevator(); //creating new objects of all subsyystems in the constructor, as its methods call their methods
  Claw m_claw=new Claw();
  Pivot m_pivot=new Pivot();
  /** Creates a new ExampleSubsystem. */
  public CommandGroups() {
   
  }

  /**
   * Example command factory method.
   *
   * @return a command
   */
  public Command exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
  public void scoreL1()
  {
      if(Constants.MotorConstants.state[Constants.MotorConstants.elevIndex].equals("L1"));
      {
        m_elevator.MoveMethodL1();

      }
      if()

    //if(Constants.MotorConstants.state[Constants.MotorConstants])
    //if elevator is not at L1:
    //m_elevator.moveMethod() pass in desired motor pos when tied to a button
    //if state/elevatorpos==Level 1
    // call m_pivot.moveMethod() pass in desired pivot pos when tied to a button
    // else: recursive; call score L1 again
    //if state/pivotpos==scoring coral pos
    // tell the "claw" to outtake the coral
    // else: call score L1 again
}
public void scoreL2()
{ 
  //same but for L2

}
public void scoreL3()
{
  //same but for L3
}
public void scoreL4()
{
  //same but for L4
}
public void collectCoral()
{
  //if elevator is not at position for collecting coral (L1?); 
  // set elevator to coral collecting position(L1?)
  //if elevator is at coral collecting position
  //claw set voltage/turn on flywheels. (collect coral)
  //else: call collect coral again

}
public void collectAlgaeL2()
{
  //if elevator is not at L2;
  // elevator.moveMethod desired pos=L2
  //if elevator is at L2:
  // run algae flywheels
  //else: call collect algae again
}
public void collectAlgaeL3()
{
  //same as L2

}
public void scoreALgaeInBarge()
{
  //if elevator is not at algae barge position:
  // elevator.mov method desired pos=(algae barge scoring position?)
  //if elevator is at algae barge position:
  // set pivot to algae barge position
  //else: call scoreAlgaeInBarge again
  //if pivot is at algae barge position:
  //outake algae with algae flywheels
  //else: call score AlgaeInBarge
}
}