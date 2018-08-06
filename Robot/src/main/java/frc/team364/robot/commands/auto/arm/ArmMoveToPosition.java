package frc.team364.robot.commands.auto.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.team364.robot.Robot;

public class ArmMoveToPosition extends Command {

	private int voltage;

    public ArmMoveToPosition(int armVoltage) {
        requires(Robot.armSystem);
        voltage = armVoltage;
        setTimeout(2);
    }

    @Override protected void initialize(){
		// No resetting needed here.
    }
    
    @Override
    protected void execute() {
        Robot.armSystem.moveArmToPosition(voltage); // This is the only line of code you need.
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