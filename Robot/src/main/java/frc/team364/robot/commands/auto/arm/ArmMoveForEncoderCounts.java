package frc.team364.robot.commands.auto.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.team364.robot.Robot;

public class ArmMoveForEncoderCounts extends Command {

private boolean backwards;


    public ArmMoveForEncoderCounts(int armCounts, boolean armBackwards) {
        requires(Robot.armSystem);
        backwards = armBackwards;
        armCounts = armCounts
        setTimeout(5);

    }
    /*    public void driveStraightToEncoderCounts(int counts, boolean backwards) {
        if(backwards) {
            pidOutputArm = pidArm.calculateOutput(counts, -getLeftEncoderPosition());
        } else {
            pidOutputLeft = pidArm.calculateOutput(counts, getLeftEncoderPosition());
        }
    }*/
    @Override protected void initialize(){
        Robot.armSystem.resetEncoders();
    }
    
    @Override
    protected void execute() {
        Robot.armSystem.ArmMoveForEncoderCounts(armBackwards);
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