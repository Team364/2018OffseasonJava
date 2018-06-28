package frc.team364.robot.commands.auto.drive;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

import java.io.File;

import edu.wpi.first.wpilibj.command.Command;
import frc.team364.robot.Robot;

public class FollowPath extends Command {

    private EncoderFollower left;
    private EncoderFollower right;

    private double leftCalculatedOutput;
    private double rightCalculatedOutput;
    private double heading;
    private double desiredHeading;
    private double angleDifference;
    private double turn;
    
    private File leftFile;
    private File rightFile;
    private Trajectory leftTraj;
    private Trajectory rightTraj;    

    public FollowPath(String leftFileName, String rightFileName) {
        leftFile = new File(leftFileName);
        rightFile = new File(rightFileName);
        requires(Robot.driveSystem);
    }

    @Override
    protected void initialize() {
        leftTraj = Pathfinder.readFromCSV(leftFile);
        rightTraj = Pathfinder.readFromCSV(rightFile);
        left = new EncoderFollower(leftTraj);
        right = new EncoderFollower(rightTraj);
        left.configureEncoder(0, 4096, 0.1524);
        right.configureEncoder(0, 4096, 0.1524);
        left.configurePIDVA(0.25, 0, 0, 1/6, 0);
        right.configurePIDVA(0.25, 0, 0, 1/6, 0);
        Robot.driveSystem.resetEncoders();
        Robot.driveSystem.resetHeading();
    }

    @Override
    protected void execute() {
        leftCalculatedOutput = left.calculate(Robot.driveSystem.getLeftEncoderPosition());
        rightCalculatedOutput = right.calculate(Robot.driveSystem.getRightEncoderPosition());
        
        heading = Robot.driveSystem.getGyroAngle();
        desiredHeading = Pathfinder.r2d(left.getHeading());

        angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - heading);
        turn = 0.8 * (-1.0/80.0) * angleDifference;

        Robot.driveSystem.setLeftDrivePower(leftCalculatedOutput + turn);
        Robot.driveSystem.setRightDrivePower(rightCalculatedOutput - turn);
    }

	@Override
	protected boolean isFinished() {
		return left.isFinished() && right.isFinished();
    }
    
    @Override
    protected void end() {

    }

}