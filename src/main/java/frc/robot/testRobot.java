package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PS4Controller.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.testCommand;
import frc.robot.subsystems.OneMotorSystem;


public class testRobot extends TimedRobot{
    private final OneMotorSystem oneMotorSystem = new OneMotorSystem();
    private final TestRobotContainer container = new TestRobotContainer();
    
    private final XboxController controller = new XboxController(OperatorConstants.kDriverControllerPort);

    private final Command command = container.getTeleOpCommand();

    @Override
    public void teleopPeriodic() {
        if(controller.getAButtonPressed()){
            command.schedule();
        }
    }

    public testRobot(){
        TestRobotContainer robotContainer = new TestRobotContainer();

    }


    
}
