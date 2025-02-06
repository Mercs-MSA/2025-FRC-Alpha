package frc.robot.subsystems;

import java.util.function.BiConsumer;

import edu.wpi.first.wpilibj.AsynchronousInterrupt;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class Beambreak extends SubsystemBase{
    private static DigitalInput m_beamBreak = new DigitalInput(0);

    private boolean broken = false;

    private String breakName = "Beambreak";

    BiConsumer<Boolean, Boolean> callback = (risingEdge, fallingEdge) -> {
        if (risingEdge){
            this.broken = true;
            // RobotContainer.stopEverything();
        }
        if (fallingEdge){
            this.broken = false;
            //RobotContainer.stopEverything();
            // RobotContainer.prepShooter();
        }
    };

    private AsynchronousInterrupt asynchronousInterrupt = new AsynchronousInterrupt(m_beamBreak, callback);
    

    public Beambreak(int channel, boolean startsBroken, String name) {
        m_beamBreak = new DigitalInput(channel);
        broken = startsBroken;
        breakName = name;
    }

    public static boolean checkBreak() {
        return m_beamBreak.get();
    }

    @Override
    public void periodic(){
        broken = m_beamBreak.get();
        SmartDashboard.putBoolean(breakName, checkBreak());
    }
}