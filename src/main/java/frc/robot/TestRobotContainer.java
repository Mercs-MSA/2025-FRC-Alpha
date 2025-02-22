package frc.robot;

import com.ctre.phoenix6.swerve.SwerveModuleConstants;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.CommandCollectCoral;
import frc.robot.commands.CommandScoreCoral;
import frc.robot.commands.TestCommand;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.OneMotorSystem;
import frc.robot.subsystems.TestDriveSubsystem;

public class TestRobotContainer {
    OneMotorSystem testSystem = new OneMotorSystem();



    
    TestDriveSubsystem drive = new TestDriveSubsystem(TunerConstants.DrivetrainConstants, TunerConstants.FrontLeft, TunerConstants.FrontRight, TunerConstants.BackLeft, TunerConstants.BackRight);

    
    
    public TestRobotContainer(){
        NamedCommands.registerCommand("Test Command", command);
        NamedCommands.registerCommand("CommandCollectCoral", collectCommand);
        autoCommand = new PathPlannerAuto("New Auto");  
    }
    Command command = new TestCommand(testSystem);
    Command collectCommand = new CommandCollectCoral();
    Command score = new CommandScoreCoral();
    Command autoCommand;


    public Command getTeleOpCommand(){
        return command;
    }

    public Command getAutoCommand(){
    
        
        
        return autoCommand;
    }
}
