package frc.robot;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.CommandCollectCoral;
import frc.robot.commands.CommandScoreCoral;
import frc.robot.commands.testCommand;
import frc.robot.subsystems.OneMotorSystem;

public class TestRobotContainer {
    OneMotorSystem testSystem = new OneMotorSystem();
    
    
    public TestRobotContainer(){
        NamedCommands.registerCommand("Test Command", command);
        NamedCommands.registerCommand("CommandCollectCoral", collectCommand);
        autoCommand = new PathPlannerAuto("New Auto");  
    }
    Command command = new testCommand(testSystem);
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
