package frc.team364.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.team364.robot.Robot;

public class TeleopArmCommand extends Command {

    public double armScalePosition = 2;
    public double armSwitchPosition = 1;

    public TeleopArmCommand() {
        requires(Robot.armSystem);
    }

	// TODO: Implement position control and state controller.
    @Override
    protected void execute() {
        if(Robot.oi.controller.getPOV() == 0) {
            Robot.armSystem.forward();
        } else if(Robot.oi.controller.getPOV() == 180) {
            Robot.armSystem.backward();
        } else {
            Robot.armSystem.armStop();
        }
        if(Robot.oi.controller.armScaleButton.get()){
            Robot.armSystem.moveArmToPosition(armScalePosition);
        }
        else if(Robot.oi.controller.armSwitchButton.get()){
            Robot.armSystem.moveArmToPosition(armSwitchPosition);
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

    





