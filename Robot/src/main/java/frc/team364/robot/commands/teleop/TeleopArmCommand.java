package frc.team364.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.team364.robot.Robot;

public class TeleopArmCommand extends Command {

    public TeleopArmCommand() {
        requires(Robot.armSystem);
    }


     @Override
    protected void execute() {
            if(Robot.oi.operationStation.getRawAxis(1) < -0.5) {
                Robot.armSystem.forward();
            } else if(Robot.oi.operationStation.getRawAxis(1) > 0.5) {
                Robot.armSystem.backward();
            } else {
                Robot.armSystem.armStop();
            }
    

            }//end of execute
        
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.armSystem.armStop();
    }

}

    





