package frc.robot;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;

class ElasticTesting extends TimedRobot {
    class SendString implements Sendable{
        String value;

        SendString(String value){
            this.value = value;

        }
        @Override
        public void initSendable(SendableBuilder builder) {
            builder.setSmartDashboardType("value");
            builder.addStringProperty("A", this::get, null);        
        }

        public String get(){
            return value;
        }
    }
    SendableChooser<String> sendableChooser  = new SendableChooser<>();

    @Override
    public void teleopInit() {
        System.out.print("LLLL");
        
        SmartDashboard.putData("A",new SendString("TESTSTSTSTS"));

        
        sendableChooser.addOption("UNO", "A");
        sendableChooser.addOption("DOS", "B");
        SmartDashboard.putData(sendableChooser);

    }


    @Override
    public void teleopPeriodic() {
        
        String option = sendableChooser.getSelected();

        System.out.println(option);

    }

    
    
}