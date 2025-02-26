// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;

import com.ctre.phoenix6.swerve.SwerveRequest;

import au.grapplerobotics.LaserCan;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.MotorConstants.AvailableState;
import frc.robot.Constants.MotorConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.CommandCoral;
import frc.robot.commands.CommandElevelatorPos;
import frc.robot.commands.CommandFreePivot;
import frc.robot.commands.CommandCancel;
import frc.robot.commands.CommandCollectCoral;
import frc.robot.commands.CommandScoreCoral;
import frc.robot.commands.CommandToState;
import frc.robot.commands.CommandSetState;
// import frc.robot.commands.CommandPivotPosOpposite;
import frc.robot.commands.CommandStopCoral;
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
    .withDeadband(MaxSpeed * 0.05).withRotationalDeadband(MaxAngularRate * 0.05); // 5% deadzone
  
  private final Coral m_coral = new Coral();
  public final Elevator m_Elevator = new Elevator();
  private final Pivot m_Pivot = new Pivot();
  private final Claw m_claw =new Claw();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_operatorController = new CommandXboxController(OperatorConstants.kOperatorControllerPort);
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);
  
  public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    
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

  public void laserDetector() {
    LaserCan.Measurement measurement = Robot.laser.getMeasurement();
    System.out.println(measurement);
    if (measurement != null && measurement.status == LaserCan.LASERCAN_STATUS_VALID_MEASUREMENT && measurement.distance_mm <= 100) {
      System.out.println(measurement.distance_mm);
      MotorConstants.laserDetect = true;
    } else {
      //System.out.println("coral not here");
      MotorConstants.laserDetect = false;
    }
  }

  
  private void configureBindings() {
    // m_driverController.a().onTrue(new IntakeCommand(m_CoralIntake));
    // m_driverController.b().onTrue(new OuttakeCommand(m_CoralIntake));
    // m_driverController.x().onTrue(new FlipIntakeCommand(m_CoralIntake));


   // m_driverController.pov(0).whileTrue(new CommandElevelatorPos(m_Elevator, 0.8));
   // m_driverController.pov(180).whileTrue(new CommandElevelatorPos(m_Elevator, 0.8));
    // m_driverController.x().onTrue(new CommandPivotPosOpposite(m_Pivot, 0.25));
    // m_driverController.y().onTrue(new CommandPivotPosOpposite(m_Pivot, -0.25));
    // m_driverController.a().onTrue(new CommandScoreCoral(m_claw));
    // m_driverController.b().onTrue(new CommandStopCoral(m_claw));




    m_operatorController.pov(180).onTrue(new CommandToState(m_Elevator, m_Pivot, AvailableState.LEVEL1));
    m_operatorController.pov(270).onTrue(new CommandToState(m_Elevator, m_Pivot, AvailableState.LEVEL2));
    m_operatorController.pov(0).onTrue(new CommandToState(m_Elevator, m_Pivot, AvailableState.LEVEL3));
    m_operatorController.pov(90).onTrue(new CommandToState(m_Elevator, m_Pivot, AvailableState.LEVEL4));

    m_operatorController.y().onTrue(new CommandToState(m_Elevator, m_Pivot, AvailableState.LEVEL2ALGAE));
    m_operatorController.b().onTrue(new CommandToState(m_Elevator, m_Pivot, AvailableState.LEVEL1));
    m_operatorController.a().onTrue(new CommandToState(m_Elevator, m_Pivot, AvailableState.COOP));
    m_operatorController.x().onTrue(new CommandToState(m_Elevator, m_Pivot, AvailableState.BARGE));



    m_operatorController.leftBumper().whileTrue(new CommandToState(m_Elevator, m_Pivot, AvailableState.LEVEL2ALGAE));
    m_operatorController.rightBumper().whileTrue(new CommandToState(m_Elevator, m_Pivot, AvailableState.LEVEL3ALGAE));

    m_driverController.leftTrigger(0.8).onTrue(new CommandScoreCoral(m_claw, false));
    m_driverController.rightTrigger(0.8).onTrue(new CommandStopCoral(m_claw));
    m_driverController.leftBumper().whileTrue(new CommandScoreCoral(m_claw, false));
    m_driverController.rightBumper().whileTrue(new CommandScoreCoral(m_claw, true));



    
    // m_driverController.a().onTrue(new CommandScoreCoral(m_claw));
    // m_driverController.b().onTrue(new CommandStopCoral(m_claw));


    // Deadzone is 5%

    drivetrain.setDefaultCommand(
      // Drivetrain will execute this command periodically
      drivetrain.applyRequest(() ->
          drive.withVelocityX(-m_driverController.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
              .withVelocityY(-m_driverController.getLeftX() * MaxSpeed) // Drive left with negative X (left)
              .withRotationalRate(-m_driverController.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
      )
  );

    

  m_driverController.y().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric())); //Seed
    // m_driverController.x().whileTrue(new CommandPivotPos(m_Pivot, 5.0));
    // m_driverController.x().whileFalse(new CommandPivotPos(m_Pivot, 0.0));

    
    // m_driverController.y().whileTrue(new CommandCoral(m_coral, -1));
    // m_driverController.b().whileTrue(new CommandCoral(m_coral, 1));    
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
