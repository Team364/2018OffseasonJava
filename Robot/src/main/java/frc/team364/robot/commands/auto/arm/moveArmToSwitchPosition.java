package frc.team364.robot.commands.auto.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.team364.robot.Robot;
import frc.team364.robot.subsystems.ArmSystem;

public class moveArmToSwitchPosition extends Command {

	

    /**
     * moveArmToSwitchPosition()
     * moves the arm to reach a desired positoin with the potentiometer and armPID
     * @param armVoltage voltage desired to be reached
     */
    public moveArmToSwitchPosition() {
        requires(Robot.armSystem);
        setTimeout(2);
    }

    @Override protected void initialize(){
		// No resetting needed here.
    }
    
    @Override
    protected void execute() {
        Robot.armSystem.moveArmToSwitchPosition();//Ensure this matches the teleop command value for move to switch
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