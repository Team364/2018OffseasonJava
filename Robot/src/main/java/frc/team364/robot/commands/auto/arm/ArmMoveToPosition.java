package frc.team364.robot.commands.auto.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.team364.robot.Robot;

public class ArmMoveToPosition extends Command {

	private int counts; // <--

    public ArmMoveToPosition(int armCounts) {
        requires(Robot.armSystem);
        counts = armCounts; // -->
        setTimeout(5); // Could maybe reduce this timeout a little.
    }

    @Override protected void initialize(){
		// No resetting needed here.
    }
    
    @Override
    protected void execute() {
        Robot.armSystem.moveArmToPositioin(counts); // This is the only line of code you need.
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