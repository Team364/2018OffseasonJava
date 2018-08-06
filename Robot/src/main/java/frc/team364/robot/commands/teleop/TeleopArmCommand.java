package frc.team364.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.team364.robot.Robot;

public class TeleopArmCommand extends Command {

    public TeleopArmCommand() {
        requires(Robot.armSystem);
    }

    @Override
    protected void execute() {
        //If up on the directional pad is pressed then the arm will move forward
        if(Robot.oi.controller.getPOV() == 0) {
            Robot.armSystem.forward();
        //If down on the directional pad is pressed then the arm will move backwards
        } else if(Robot.oi.controller.getPOV() == 180) {
            Robot.armSystem.backward();
        } else {
            Robot.armSystem.armStop();
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

    





