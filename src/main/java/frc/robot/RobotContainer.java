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
import frc.robot.commands.CommandScoreCoral;
import frc.robot.commands.CommandToState;
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

  private double MaxSpeed = TunerConstants.kSpeedAt12Volts.magnitude();
  private double MaxAngularRate = Units.rotationsPerMinuteToRadiansPerSecond(45.0);
  
  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
    .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1); // 10% deadzone
  
  public final Coral m_coral = new Coral();
  public final Elevator m_Elevator = new Elevator();
  public final Pivot m_Pivot = new Pivot();
  private final Claw m_claw =new Claw();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_operatorController = new CommandXboxController(OperatorConstants.kOperatorControllerPort);
  private final CommandXboxController m_driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);
  
  public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

  private final SequentialCommandGroup l4RetractCommandGroup = new SequentialCommandGroup(      
        new CommandPivotPos(m_Pivot, Constants.PivotConstants.L1),
        new CommandElevelatorMoveToPos(m_Elevator, ElevatorConstants.L3),
        new CommandPivotPos(m_Pivot, Constants.PivotConstants.TRANSFER_POSITION),
        new CommandElevelatorMoveToPos(m_Elevator, ElevatorConstants.L1),
        new CommandPivotPos(m_Pivot, Constants.PivotConstants.L1)
  );

  private final SequentialCommandGroup defaultRetractCommandGroup = new SequentialCommandGroup(    
    new CommandPivotPos(m_Pivot, Constants.PivotConstants.TRANSFER_POSITION),
    new CommandElevelatorMoveToPos(m_Elevator, ElevatorConstants.L1),
    new CommandPivotPos(m_Pivot, Constants.PivotConstants.L1)
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

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */


 
 
  
  private void configureBindings() {


    //4 commands send elevator state to a buffer variable to be later called by driver


    // m_operatorController.pov(180).onTrue(new CommandSetToState(Constants.MotorConstants.AvailableState.LEVEL1));
    // m_operatorController.pov(270).onTrue(new CommandSetToState(Constants.MotorConstants.AvailableState.LEVEL2));
    // m_operatorController.pov(0).onTrue(new CommandSetToState(Constants.MotorConstants.AvailableState.LEVEL3));
    // m_operatorController.pov(90).onTrue(new CommandSetToState(Constants.MotorConstants.AvailableState.LEVEL4));

    //Resets elevator and pivot to bottom by control of operator
    //m_operatorController.b().onTrue(new CommandToState(m_Elevator, m_Pivot, Constants.MotorConstants.AvailableState.LEVEL1));
    
    m_operatorController.a().onTrue(new SequentialCommandGroup(
      new CommandIntakeFlywheels(m_claw),
      new CommandIntakeCoral(m_claw)
    ));

    //Left trigger starts into. Right trigger stops, should normally need to be used to stop it after scoring
    //m_operatorController.leftTrigger(0.8).whileTrue(new CommandScoreCoral(m_claw));

    //For future use with Algae
    // m_operatorController.leftBumper().whileTrue(new CommandScoreCoral(m_claw, false));
    // m_operatorController.rightBumper().whileTrue(new CommandScoreCoral(m_claw, true));




    //m_operatorController.a().whileTrue(new CommandIntakeFlywheels(m_claw, IntakeAction.INTAKE)); //intakes
    m_operatorController.y().whileTrue(new CommandScoreCoral(m_claw, true));
    m_operatorController.x().whileTrue(new CommandScoreCoral(m_claw, false));
   
    m_operatorController.pov(0).onTrue(new SequentialCommandGroup(
      new CommandPivotPos(m_Pivot, Constants.PivotConstants.TRANSFER_POSITION),
      new CommandElevelatorMoveToPos(m_Elevator, ElevatorConstants.L4),
      new CommandPivotPos(m_Pivot, Constants.PivotConstants.L4)

    ));

    m_operatorController.pov(270).onTrue(new SequentialCommandGroup(
      new CommandPivotPos(m_Pivot, Constants.PivotConstants.TRANSFER_POSITION),
      new CommandElevelatorMoveToPos(m_Elevator, ElevatorConstants.L2),
      new CommandPivotPos(m_Pivot, Constants.PivotConstants.L4)

    ));

    m_operatorController.pov(90).onTrue(new SequentialCommandGroup(
      new CommandPivotPos(m_Pivot, Constants.PivotConstants.TRANSFER_POSITION),
      new CommandElevelatorMoveToPos(m_Elevator, ElevatorConstants.L3),
      new CommandPivotPos(m_Pivot, Constants.PivotConstants.L4)

    ));
    m_operatorController.pov(180).onTrue(new SequentialCommandGroup(
      new CommandPivotPos(m_Pivot, Constants.PivotConstants.TRANSFER_POSITION),
      new CommandElevelatorMoveToPos(m_Elevator, ElevatorConstants.L1),
      new CommandPivotPos(m_Pivot, Constants.PivotConstants.L1)

    ));
    
    m_operatorController.pov(180).onTrue(new SequentialCommandGroup(
      //if elevator in level 4, run l4RetractCommandGroup, else run defaultRectractCommandGroup
      Math.abs(m_Elevator.getPosition() - ElevatorConstants.L4) <= 0.25 ? l4RetractCommandGroup : defaultRetractCommandGroup
    ));
    
    
  
  //m_operatorController.pov(90).onTrue(new CommandPivotPos(m_Pivot, PivotConstants.L1));
  
   // m_operatorController.a().onTrue(new CommandPivotPos(m_Pivot, Constants.PivotConstants.L2THRUL3,true));



    // Deadzone is 10%
    drivetrain.setDefaultCommand(
      // Drivetrain will execute this command periodically
      drivetrain.applyRequest(() ->
          drive.withVelocityX(-m_driverController.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
              .withVelocityY(-m_driverController.getLeftX() * MaxSpeed) // Drive left with negative X (left)
              .withRotationalRate(-m_driverController.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
      )
  );

  
  m_driverController.x().onTrue(new CommandToPos(drivetrain,
   new Pose2d(drivetrain.getState().Pose.getX() + 1,
   drivetrain.getState().Pose.getY(),
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
