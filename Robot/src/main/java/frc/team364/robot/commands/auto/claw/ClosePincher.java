package frc.team364.robot.commands.auto.claw;

import edu.wpi.first.wpilibj.command.Command;
import frc.team364.robot.Robot;


public class ClosePincher extends Command {

    public ClosePincher() {
        requires(Robot.clawSystem);
        setTimeout(0.1);
    }

    @Override
    protected void initialize() {
        Robot.clawSystem.pincherOff();
    }

    @Override
    protected void execute() {
        Robot.clawSystem.openPincher();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Robot.clawSystem.pincherOff();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}