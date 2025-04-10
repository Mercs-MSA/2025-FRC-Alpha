// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;

import com.ctre.phoenix6.swerve.SwerveRequest;

import au.grapplerobotics.LaserCan;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.ElevatorConstants;
//import frc.robot.Constants.MotorConstants.AvailableState;
import frc.robot.Constants.MotorConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.PivotConstants;
import frc.robot.commands.CommandCoral;
import frc.robot.commands.CommandElevelatorMoveToPos;
import frc.robot.commands.CommandElevelatorPos;
import frc.robot.commands.CommandIntakeCoral;
import frc.robot.commands.CommandIntakeFlywheels;
// import frc.robot.commands.CommandFreePivot;
import frc.robot.commands.CommandPivotPos;
import frc.robot.commands.CommandMoveFlywheels;
import frc.robot.commands.CommandToState;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.CommandSetToState;
// import frc.robot.commands.CommandPivotPosOpposite;
import frc.robot.commands.CommandStopCoral;
import frc.robot.commands.CommandToPos;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Coral;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Claw;
import frc.robot.generated.TunerConstants;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  // private final CoralIntake m_CoralIntake = new CoralIntake();
  // private final IntakeCommand m_IntakeCommand= new IntakeCommand(m_CoralIntake);

  private double MaxSpeed = TunerConstants.kSpeedAt12Volts.magnitude() / 5;
  private double MaxAngularRate = Units.rotationsPerMinuteToRadiansPerSecond(10);
  private final double MAX_FLYWHEEL_VOLTAGE = 6.0;
  private double actualVelX = 0, actualVelY = 0, actualRot = 0;

  
  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
    .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1); // 10% deadzone
  
  public final Coral m_coral = new Coral();
  public final Elevator m_Elevator = new Elevator();
  public final Pivot m_Pivot = new Pivot();
  private final Claw m_claw =new Claw();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  public final CommandXboxController m_operatorController = new CommandXboxController(OperatorConstants.kOperatorControllerPort);
  private final CommandXboxController m_driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);
  
  public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

  private final SequentialCommandGroup l4RetractSequentialCommandGroup = new SequentialCommandGroup(      
        new CommandPivotPos(m_Pivot, Constants.PivotConstants.L4_TRANSFER),
        new CommandElevelatorMoveToPos(m_Elevator, ElevatorConstants.L3),
        new CommandPivotPos(m_Pivot, Constants.PivotConstants.TRANSFER_POSITION),
        new CommandElevelatorMoveToPos(m_Elevator, ElevatorConstants.L1),
        new CommandPivotPos(m_Pivot, Constants.PivotConstants.L1)
  );

  private final SequentialCommandGroup SCORE_CORAL_SEQUENTIAL_COMMAND_GROUP = new SequentialCommandGroup(
    new CommandIntakeFlywheels(m_claw),
    new CommandIntakeCoral(m_claw)
  );

  private final SequentialCommandGroup ELEVATOR_AND_PIVOT_TO_L1_CORAL_SEQUENTIAL_COMMAND_GROUP = new SequentialCommandGroup(    
    new CommandPivotPos(m_Pivot, Constants.PivotConstants.TRANSFER_POSITION),
    new CommandElevelatorMoveToPos(m_Elevator, ElevatorConstants.L1),
    new CommandPivotPos(m_Pivot, Constants.PivotConstants.L1)
  );

  private final SequentialCommandGroup ELEVATOR_AND_PIVOT_TO_L2_CORAL_SEQUENTIAL_COMMAND_GROUP = new SequentialCommandGroup(    
    new CommandPivotPos(m_Pivot, Constants.PivotConstants.TRANSFER_POSITION),
    new CommandElevelatorMoveToPos(m_Elevator, ElevatorConstants.L2),
    new CommandPivotPos(m_Pivot, Constants.PivotConstants.L2)
  );

  private final SequentialCommandGroup ELEVATOR_AND_PIVOT_TO_L3_CORAL_SEQUENTIAL_COMMAND_GROUP = new SequentialCommandGroup(    
    new CommandPivotPos(m_Pivot, Constants.PivotConstants.TRANSFER_POSITION),
    new CommandElevelatorMoveToPos(m_Elevator, ElevatorConstants.L3),
    new CommandPivotPos(m_Pivot, Constants.PivotConstants.L3)
  );

  private final SequentialCommandGroup ELEVATOR_AND_PIVOT_TO_L4_CORAL_SEQUENTIAL_COMMAND_GROUP = new SequentialCommandGroup(
    new CommandPivotPos(m_Pivot, Constants.PivotConstants.TRANSFER_POSITION),
    new CommandElevelatorMoveToPos(m_Elevator, ElevatorConstants.L4),
    new CommandPivotPos(m_Pivot, Constants.PivotConstants.L4)
  );


  private final SequentialCommandGroup ELEVATOR_AND_PIVOT_TO_L1_ALGAE_SEQUENTIAL_COMMAND_GROUP = new SequentialCommandGroup(    
    new CommandPivotPos(m_Pivot, Constants.PivotConstants.ALGAE),
    new CommandElevelatorMoveToPos(m_Elevator, ElevatorConstants.ALGAE_L1)
  );

  private final SequentialCommandGroup ELEVATOR_AND_PIVOT_TO_L2_ALGAE_SEQUENTIAL_COMMAND_GROUP = new SequentialCommandGroup(    
    new CommandPivotPos(m_Pivot, Constants.PivotConstants.ALGAE),
    new CommandElevelatorMoveToPos(m_Elevator, ElevatorConstants.ALGAE_L2)
  );

  private final SequentialCommandGroup ELEVATOR_AND_PIVOT_TO_L3_ALGAE_SEQUENTIAL_COMMAND_GROUP = new SequentialCommandGroup(    
    new CommandPivotPos(m_Pivot, Constants.PivotConstants.ALGAE),
    new CommandElevelatorMoveToPos(m_Elevator, ElevatorConstants.ALGAE_L3)
  );


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    

    
  }

  public enum IntakeAction {
    INTAKE,
    OUTTAKE
  }

  public void drivetrainControl(){
    double targetVelX = -m_driverController.getLeftY()  * MaxSpeed;
    double targetVelY = -m_driverController.getLeftX()  * MaxSpeed;
    double targetRot = -m_driverController.getRightX() * MaxAngularRate;
    double lateralSpeedIncrease = .011;    
    double rotationalSpeedIncrease = Units.rotationsPerMinuteToRadiansPerSecond(5);
    
    if(actualVelX < .2 * MaxSpeed && actualVelX > -.2 * MaxSpeed){
      actualVelX = 0;
    }
    if(actualVelY < .2 * MaxSpeed && actualVelY > -.2 * MaxSpeed){
      actualVelY = 0;
    }
    if(actualRot < .2 * MaxAngularRate && actualRot > -.2 * MaxAngularRate){
      actualRot = 0;
    }
    
    System.out.println("Target" + targetVelX + " " + targetVelY + " " + targetRot);
    System.out.println("Actual before Incrementing" + actualVelX + " " + actualVelY + " " + actualRot);
    
    if(!(actualVelX <= targetVelX + .4 && actualVelX >= targetVelX - .4)){
      System.out.println(" X is incrementing");
    if(actualVelX > targetVelX)
      {
        actualVelX -= lateralSpeedIncrease;
      } else if(actualVelX < targetVelX){
        actualVelX += lateralSpeedIncrease;
      }
    }
    if(!(actualVelY <= targetVelY + .4 && actualVelY >= targetVelY - .4)){
      System.out.println(" Y is incrementing");
    if(actualVelY > targetVelY ){
      actualVelY -= lateralSpeedIncrease;
    } else if(actualVelX < targetVelX){
      actualVelY += lateralSpeedIncrease;
    }}
    if(!(actualRot <= actualRot + 2 && actualRot >= actualRot- 2)){
      System.out.println(" Rot is incrementing");
    if (actualRot > targetRot) {
      actualRot -= rotationalSpeedIncrease;
    } else if (actualRot < targetRot) {
      actualRot += rotationalSpeedIncrease;
    }
  }


  

    System.out.println("Target" + targetVelX + " " + targetVelY + " " + targetRot);
    System.out.println("Actual" + actualVelX + " " + actualVelY + " " + actualRot);

    double[] arr = {targetVelX, targetVelY, targetRot, actualVelX, actualVelY, actualRot};

    SmartDashboard.putNumberArray("Values", arr);


  

    drivetrain.setDefaultCommand(
      // Drivetrain will execute this command periodically
      drivetrain.applyRequest(() ->
          drive.withVelocityX(actualVelX)
              .withVelocityY(actualVelY) 
              .withRotationalRate(actualRot) 
      )
  );
  };


  private void configureBindings() {

    /* -------------------- OPERATOR CONTROLS -------------------- */
    
    m_operatorController.y().onTrue(SCORE_CORAL_SEQUENTIAL_COMMAND_GROUP); // y
    
    m_operatorController.leftTrigger().whileTrue(new CommandMoveFlywheels(m_claw,6.0 /*m_operatorController.getRightTriggerAxis() * MAX_FLYWHEEL_VOLTAGE*/)); // left trigger
    m_operatorController.rightTrigger().whileTrue(new CommandMoveFlywheels(m_claw,-6.0 /*m_operatorController.getRightTriggerAxis() * -MAX_FLYWHEEL_VOLTAGE*/)); // right trigger

    m_operatorController.pov(180).onTrue(ELEVATOR_AND_PIVOT_TO_L1_CORAL_SEQUENTIAL_COMMAND_GROUP); // down dpad
    m_operatorController.pov(270).onTrue(ELEVATOR_AND_PIVOT_TO_L2_CORAL_SEQUENTIAL_COMMAND_GROUP); // left dpad
    m_operatorController.pov(90).onTrue(ELEVATOR_AND_PIVOT_TO_L3_CORAL_SEQUENTIAL_COMMAND_GROUP); // right dpad
    m_operatorController.pov(0).onTrue(ELEVATOR_AND_PIVOT_TO_L4_CORAL_SEQUENTIAL_COMMAND_GROUP); // up dpad

    m_operatorController.pov(180).and(m_operatorController.a()).onTrue(ELEVATOR_AND_PIVOT_TO_L1_ALGAE_SEQUENTIAL_COMMAND_GROUP); // down dpad + a
    m_operatorController.pov(270).and(m_operatorController.a()).onTrue(ELEVATOR_AND_PIVOT_TO_L2_ALGAE_SEQUENTIAL_COMMAND_GROUP); // left dpad + a
    m_operatorController.pov(90).and(m_operatorController.a()).onTrue(ELEVATOR_AND_PIVOT_TO_L3_ALGAE_SEQUENTIAL_COMMAND_GROUP); // right dpad + a
    

    /* -------------------- DRIVER CONTROLS -------------------- */
    
    // Deadzone is 10%
    // drivetrain.setDefaultCommand(
    //   // Drivetrain will execute this command periodically
    //   drivetrain.applyRequest(() ->
    //       drive.withVelocityX(-m_driverController.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
    //           .withVelocityY(-m_driverController.getLeftX() * MaxSpeed) // Drive left with negative X (left)
    //           .withRotationalRate(-m_driverController.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
    //   )
    // );

    
    

  
    m_driverController.x().onTrue(new CommandToPos(drivetrain,
    new Pose2d(2,2,
    drivetrain.getState().Pose.getRotation()),
    false));

      
      //Seeds robot for field centric. To seed, face the robot facing the direction opposite of the driver stations then hit Y
      m_driverController.y().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric())); //Seed  
  }

public void getAutonomousCommand() {

}

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */


  // public Command getAutonomousCommand() {
  //  An example command will be run in autonomous
  //  return Autos.exampleAuto(m_exampleSubsystem);
  // }
}
