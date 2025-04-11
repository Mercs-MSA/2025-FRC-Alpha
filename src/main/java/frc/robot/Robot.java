// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.generated.TunerConstants;
import frc.robot.generated.TunerConstants.TunerSwerveDrivetrain;
import frc.robot.subsystems.CommandSwerveDrivetrain;

import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.swerve.SwerveRequest;

import au.grapplerobotics.CanBridge;
import au.grapplerobotics.ConfigurationFailedException;
import au.grapplerobotics.LaserCan;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private final RobotContainer m_robotContainer;
  //private final ADIS16470_IMU m_gyro = new ADIS16470_IMU(); // change this

  private final ADIS16470_IMU m_gyro = new ADIS16470_IMU(); // change this

  private double MaxSpeed = TunerConstants.kSpeedAt12Volts.magnitude();
  private double MaxAngularRate = Units.rotationsPerMinuteToRadiansPerSecond(45.0);

  private CommandXboxController  m_driverController = new CommandXboxController(0);
  public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();


  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
    .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1); // 10% deadzone

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  public Robot() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    boolean doRejectUpdate = false;
    double[] arr = {m_robotContainer.drivetrain.getState().Pose.getRotation().getDegrees(),  m_robotContainer.drivetrain.getState().Pose.getX(), m_robotContainer.drivetrain.getState().Pose.getY()};
    LimelightHelpers.SetRobotOrientation("limelight-alpha", m_robotContainer.drivetrain.getState().Pose.getRotation().getDegrees(), 0, 0, 0, 0, 0);
    LimelightHelpers.PoseEstimate mt2 = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelight-alpha");
    if(Math.abs(m_gyro.getRate()) > 720) // if our angular velocity is greater than 720 degrees per second, ignore vision updates
    {
      doRejectUpdate = true;
    }
    if(mt2.tagCount == 0)
    {
      doRejectUpdate = true;
     
    }
    if(!doRejectUpdate)
    {
      m_robotContainer.drivetrain.setVisionMeasurementStdDevs(VecBuilder.fill(.7,.7,9999999));
      m_robotContainer.drivetrain.addVisionMeasurement(
          mt2.pose,
          Utils.fpgaToCurrentTime(mt2.timestampSeconds)); 
      System.out.println("Limelight Updated");
      System.out.println(arr);
      System.out.println("to" + mt2.pose.getX() +" " + mt2.pose.getY() + "" + mt2.pose.getRotation().getDegrees());


    }


    
    SmartDashboard.putNumberArray("Odom Pose", arr);

    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    
    SmartDashboard.putNumber("elevator command", m_robotContainer.m_Elevator.m_elevator_command);
    SmartDashboard.putNumber("elevator position", m_robotContainer.m_Elevator.getPosition());
    SmartDashboard.putNumber("elevator voltage", m_robotContainer.m_Elevator.getVoltage());

    SmartDashboard.putNumber("pivot desired position", m_robotContainer.m_Pivot.desiredPosition);
    SmartDashboard.putNumber("pivot position", m_robotContainer.m_Pivot.getPosition());
    SmartDashboard.putNumber("pivot voltage", m_robotContainer.m_Pivot.getVoltage());
    SmartDashboard.putNumber("operator right trig", m_robotContainer.m_operatorController.getRightTriggerAxis());
    SmartDashboard.putNumber("operator left trig", m_robotContainer.m_operatorController.getLeftTriggerAxis());

 


    

    // SmartDashboard.putBoolean("Test Laser Delete", m_robotContainer.laserDetect);

    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  //@Override
 /*  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();
  @Override
  public void autonomousInit() {
    // m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // // schedule the autonomous command (example)
    // if (m_autonomousCommand != null) {
    //   m_autonomousCommand.schedule();
    // }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }


  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    m_robotContainer.drivetrainControl();
  }
    

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
