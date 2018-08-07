package frc.team364.robot.commands.auto.claw;

import edu.wpi.first.wpilibj.command.Command;
import frc.team364.robot.Robot;

//I am assuming that the forward and reverse wires are backwards
//TODO: Ensure the wires are correct and change this to execute open pincher
public class OpenPincher extends Command {

    /**
     * OpenPincher()
     * opnes pincher
     */
    public OpenPincher() {
        requires(Robot.clawSystem);
        setTimeout(0.1);
    }

    @Override
    protected void initialize() {
        Robot.clawSystem.pincherOff();
    }

    @Override
    protected void execute() {
        Robot.clawSystem.closePincher();
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