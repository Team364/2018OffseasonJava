package frc.team364.robot.commands.auto.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.team364.robot.Robot;

public class MoveArmDown extends Command {


    /**
     * ArmMoveToPosition()
     * moves the arm to reach a desired positoin with the potentiometer and armPID
     * @param armVoltage voltage desired to be reached
     */
    public MoveArmDown() {
        requires(Robot.armSystem);
        setTimeout(0.5);
    }

    @Override protected void initialize(){
		// No resetting needed here.
    }
    
    @Override
    protected void execute() {
        if(Robot.armSystem.getPotVoltage() >= 3.2){
        Robot.armSystem.armBackward(); // This is the only line of code you need.
        }
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