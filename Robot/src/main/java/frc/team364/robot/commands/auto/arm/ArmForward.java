package frc.team364.robot.commands.auto.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.team364.robot.Robot;

public class ArmForward extends Command {

private int time;


    public ArmForward(int armTime) {
        requires(Robot.armSystem);
        time = armTime;
        setTimeout(armTime);

    }

    
    @Override
    protected void execute() {
        Robot.armSystem.ForwardForTime(time);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Robot.armSystem.armStop();
    }

    
    @Override
    protected void interrupted() {
        super.interrupted();
    }


}