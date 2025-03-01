
package frc.robot.commands;

import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class CommandToPos extends Command {
  private final CommandSwerveDrivetrain drivetrain;
  private Pose2d target;
  boolean invertRed;
  SwerveRequest.FieldCentric driveRequest = new SwerveRequest.FieldCentric();
   
  // tune PID later
  private final PIDController thetaController =
    // new ProfiledPIDController(6, 1.0, 0, new TrapezoidProfile.Constraints(Math.PI, Math.PI));
    new PIDController(2, 0, 0);
  private final PIDController xVelController =
    // new ProfiledPIDController(2.5, 0, 0, new TrapezoidProfile.Constraints(Constants.DriveToPoseConstants.linearMetersMaxVel, Constants.DriveToPoseConstants.linearMetersMaxAccel));
    new PIDController(2.5, 0, 0);
  private final PIDController yVelController =
    new PIDController(2.5, 0, 0);
    // new ProfiledPIDController(2.5, 0, 0, new TrapezoidProfile.Constraints(Constants.DriveToPoseConstants.linearMetersMaxVel, Constants.DriveToPoseConstants.linearMetersMaxAccel));

  

  public CommandToPos(CommandSwerveDrivetrain swerve, Pose2d targetPose, boolean invertRed) {
    this.drivetrain = swerve;

    // thetaController.setTolerance(Units.degreesToRadians(Constants.DriveToPoseConstants.angularDegreesTolerance));
    // xVelController.setTolerance(Constants.DriveToPoseConstants.linearMetersTolerance); Fizx these numbers
    // yVelController.setTolerance(Constants.DriveToPoseConstants.linearMetersTolerance);
    
    thetaController.enableContinuousInput(-Math.PI, Math.PI);

    SmartDashboard.putData("driveToPosTheta_Pid", thetaController);
    SmartDashboard.putData("driveToPosX_Pid", xVelController);
    SmartDashboard.putData("driveToPosY_Pid", yVelController);

    SmartDashboard.putNumber("driveToPosX_Tgt", -1);
    SmartDashboard.putNumber("driveToPosY_Tgt", -1);
    SmartDashboard.putNumber("driveToPosTheta_Tgt", -1);
    this.target = targetPose;
    this.invertRed = invertRed;

    addRequirements(swerve);
  }

  @Override
  public void initialize() {    
    var currPose = drivetrain.getState().Pose;
    // thetaController.reset(currPose.getRotation().getRadians());
    // xVelController.reset(currPose.getX());
    // yVelController.reset(currPose.getY());
    thetaController.reset();
    xVelController.reset();
    yVelController.reset();

    if (target == null) {
      return;
    }
    System.out.println(target);
  }

  @Override
  public void execute() {
    if (target == null) {
      return;
    }
    

    

    double targetX = target.getX();
    double targetY = target.getY();
    double targetTheta = target.getRotation().getRadians();
    // if (this.invertRed) {
    //   var alliance = DriverStation.getAlliance();
    //   if (alliance.isPresent() && alliance.get() == DriverStation.Alliance.Red) {
    //     targetX = Constants.FieldConstants.fieldLengthMeters - targetX;
    //     targetY = Constants.FieldConstants.fieldWidthMeters - targetY;
    //     targetTheta = targetTheta + Math.PI;
    //   } fix this later with constants
    // }

    var currPose = drivetrain.getState().Pose;
    SmartDashboard.putNumber("currPoseX", currPose.getX());
    SmartDashboard.putNumber("currPoseY", currPose.getY());
    final double thetaVelocity = //thetaController.atGoal() ? 0.0 :
        thetaController.calculate(
            currPose.getRotation().getRadians(), targetTheta);
    final double xVelocity = //xVelController.atSetpoint() ? 0.0 :
        xVelController.calculate(
            currPose.getX(), targetX
        );
    final double yVelocity = //yVelController.atSetpoint() ? 0.0 :
        yVelController.calculate(
            currPose.getY(), targetY
        );

    SmartDashboard.putNumber("driveToPosX_Vel", xVelocity);
    SmartDashboard.putNumber("driveToPosY_Vel", yVelocity);
    SmartDashboard.putNumber("driveToPosTheta_Vel", thetaVelocity);

    SmartDashboard.putNumber("driveToPosX_Tgt", targetX);
    SmartDashboard.putNumber("driveToPosY_Tgt", targetY);
    SmartDashboard.putNumber("driveToPosTheta_Tgt", target.getRotation().getDegrees());

    drivetrain.setControl(
        driveRequest
        .withVelocityX(xVelocity * (DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get() == Alliance.Red ? -1 : 1))
        .withVelocityY(yVelocity * (DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get() == Alliance.Red ? -1 : 1))
        .withRotationalRate(thetaVelocity)
    );
}
  public boolean atGoal() {
    return thetaController.atSetpoint() && xVelController.atSetpoint() && yVelController.atSetpoint();
    // return thetaController.atGoal() && xVelController.atGoal() && yVelController.atGoal();
  }

  @Override
  public void end(boolean interrupted) {
    SmartDashboard.putNumber("driveToPosX_Vel", 0);
    SmartDashboard.putNumber("driveToPosY_Vel", 0);
    SmartDashboard.putNumber("driveToPosTheta_Vel", 0);
  }

  @Override
  public boolean isFinished(){
    // return atGoal();
    return false;
  }
}
