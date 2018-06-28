package frc.team364.robot.commands.auto.claw;

import edu.wpi.first.wpilibj.command.Command;
import frc.team364.robot.Robot;


public class FlipClawUp extends Command {

    public FlipClawUp() {
        requires(Robot.clawSystem);
        setTimeout(0.1);
    }

    @Override
    protected void initialize() {
        Robot.clawSystem.clawOff();
    }

    @Override
    protected void execute() {
        Robot.clawSystem.flipClawDown();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Robot.clawSystem.clawOff();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}