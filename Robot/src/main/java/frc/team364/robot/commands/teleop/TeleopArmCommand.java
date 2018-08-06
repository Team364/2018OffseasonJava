package frc.team364.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.team364.robot.Robot;

public class TeleopArmCommand extends Command {

    public double switchPlace = 1;
    public double scalePlace = 2;


    public TeleopArmCommand() {
        requires(Robot.armSystem);
    }

	
    @Override
    protected void execute() {
        if(Robot.oi.controller.getPOV() == 0) {
            Robot.armSystem.forward();
        } else if(Robot.oi.controller.getPOV() == 180) {
            Robot.armSystem.backward();
        } else {
            Robot.armSystem.armStop();
        }
        if(Robot.oi.switchButton.get())
            {
                Robot.armSystem.moveArmToPosition(switchPlace);
            }
            else if(Robot.oi.scaleButton.get())
            {
                Robot.armSystem.moveArmToPosition(scalePlace);
            }
    }
        
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.armSystem.armStop();
    }

}

    





