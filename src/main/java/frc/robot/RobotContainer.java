// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.MotorConstants.AvailableState;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.CommandCoral;
import frc.robot.commands.CommandElevelatorPos;
import frc.robot.commands.CommandPivotPos;
import frc.robot.commands.CommandSetState;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Coral;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Pivot;



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


  
  private final Coral m_coral = new Coral();
  private final Elevator m_Elevator = new Elevator();
  private final Pivot m_Pivot = new Pivot();

  // Replace with CommandPS4Controller or CommandJoystick if needed
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
  private void configureBindings() {
    // m_driverController.a().onTrue(new IntakeCommand(m_CoralIntake));
    // m_driverController.b().onTrue(new OuttakeCommand(m_CoralIntake));
    // m_driverController.x().onTrue(new FlipIntakeCommand(m_CoralIntake));



    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    // new Trigger(m_coral::exampleCondition)
    //     .onTrue(new ExampleCommand(m_coral));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    System.out.println(m_driverController.getLeftX());

    m_driverController.x().whileTrue(new CommandPivotPos(m_Pivot, m_driverController.getLeftY()));

    // m_driverController.y().whileTrue(new CommandCoral(m_coral, -1));
    // m_driverController.b().whileTrue(new CommandCoral(m_coral, 1));
    // m_driverController.a().onTrue(new SequentialCommandGroup(
    //   new CommandSetState(AvailableState.LEVEL2),
    //   new CommandPivotPos(m_Pivot),
    //   new CommandElevelatorPos(m_Elevator)
    // ));

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
