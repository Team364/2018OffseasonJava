/*
 * George and Keandre:
 * This is the DriveSystem class. It holds objects for all of the motor controllers,
 * shift pistons, PID, and the navX. It also has functions for running DriveToDistance and
 * TurnToHeading.
 */

package frc.team364.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team364.robot.PIDCalc;
import frc.team364.robot.RobotMap;
import frc.team364.robot.commands.teleop.TeleopDriveCommand;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;


/**
 * @author Landon Haugh
 * @version v1.0
 */ 
public class DriveSystem extends Subsystem {

    public TalonSRX leftFront;
    public TalonSRX leftRear;
    public TalonSRX rightFront;
    public TalonSRX rightRear;
    public DoubleSolenoid shifter;
    public AHRS navX;
    public PIDCalc pidNavX;
    public PIDCalc pidLeft;
    public PIDCalc pidRight;
    public double pidOutputNavX;
    public double pidOutputLeft;
    public double pidOutputRight;


    public DriveSystem() {
        
        // Initialize TalonSRX objects
        leftFront = new TalonSRX(RobotMap.leftFrontDrive);
        leftRear = new TalonSRX(RobotMap.leftRearDrive);
        rightFront = new TalonSRX(RobotMap.rightFrontDrive);
        rightRear = new TalonSRX(RobotMap.rightRearDrive);

        // Initialize DoubleSolenoid shifter object
        shifter = new DoubleSolenoid(RobotMap.shifterPort1, RobotMap.shifterPort2);
        
	    // Set the front drive motors to follow the rear
        leftFront.follow(leftRear);
        rightFront.follow(rightRear);

	    // Config PF on left side
        leftRear.config_kP(0, 0.25, 100);
        leftRear.config_kF(0, 1, 100);

	    // Config PF on right side
        rightRear.config_kP(0, 0.25, 100);
        rightRear.config_kF(0, 1, 100);

	    // Init the navX, Pathfinder, and PIDCalc
        navX = new AHRS(SPI.Port.kMXP);
        pidNavX = new PIDCalc(0.00005, 0.01, 50, 0, "NavX");
        pidLeft = new PIDCalc(0.0005, 0, 0, 0, "Left");
        pidRight = new PIDCalc(0.0005, 0, 0, 0, "Right");
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new TeleopDriveCommand());
    }

    /**
     * tankDrive()
     * Sets manual control of the drivetrain for teleop
     * @param left sets the left drive power
     * @param right sets the right drive power
     */
    public void tankDrive(double left, double right) {
        leftRear.set(ControlMode.PercentOutput, left);
        rightRear.set(ControlMode.PercentOutput, right);//negate in case of backwards
    }

    /**
     * stop()
     * Stops the drive motors
     * Use this in auto to stop the drivetrain inbetween commands
     */ 
    public void stop() {
        leftRear.set(ControlMode.PercentOutput, 0);
        rightRear.set(ControlMode.PercentOutput, 0);
    }

    /**
     * getLeftEncoderPosition()
     * @return returns the left encoder position in counts
     */ 
    public int getLeftEncoderPosition() {
        return leftRear.getSelectedSensorPosition(0);
    }

    /**
     * getRightEncoderPosition()
     * @return returns the right encoder position in counts
     */ 
    public int getRightEncoderPosition() {
        return rightRear.getSelectedSensorPosition(0);//Negate if the right side gets negated
    }

    /**
     * getGyroAngle()
     * @return returns the navX angle (yaw)
     */ 
    public double getGyroAngle() {
        return navX.getYaw();
    }

    /**
     * driveStraightToEcnoderCounts()
     * Uses the TalonSRX PID to drive to a certain number of counts
     * @param counts specify encoder counts to drive to
     */ 
    public void driveStraightToEncoderCounts(int counts, boolean backwards, boolean useGyro) {
        if(backwards) {
            pidOutputLeft = pidLeft.calculateOutput(counts, -getLeftEncoderPosition());
            pidOutputRight = pidRight.calculateOutput(counts, -getRightEncoderPosition());
            pidOutputNavX = pidNavX.calculateOutput(0, getGyroAngle());
            if(useGyro) {
                leftRear.set(ControlMode.PercentOutput, -pidOutputLeft + pidOutputNavX);
                rightRear.set(ControlMode.PercentOutput, pidOutputRight + pidOutputNavX);
            } else {
                leftRear.set(ControlMode.PercentOutput, -pidOutputLeft);
                rightRear.set(ControlMode.PercentOutput, pidOutputRight);
            }
        } else {
            pidOutputLeft = pidLeft.calculateOutput(counts, getLeftEncoderPosition());
            pidOutputRight = pidRight.calculateOutput(counts, getRightEncoderPosition());
            pidOutputNavX = pidNavX.calculateOutput(0, getGyroAngle());
            if(useGyro) {
                leftRear.set(ControlMode.PercentOutput, pidOutputLeft + pidOutputNavX);
                rightRear.set(ControlMode.PercentOutput, -pidOutputRight + pidOutputNavX);
            } else {
                leftRear.set(ControlMode.PercentOutput, pidOutputLeft);
                rightRear.set(ControlMode.PercentOutput, -pidOutputRight);
            }
        }
    }

    /**
     * withinEncoderRange()
     * Checks if the drivetrain has reached the target counts
     * @param counts counts to reach
     * @return returns true if counts is within 10 of wanted counts
     */ 
    public boolean withinEncoderCountRange(int counts) {

        double leftRearPos = leftRear.getSelectedSensorPosition(0);
        if(Math.abs(leftRearPos) >= (counts - 100)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * resetHeading()
     * Resets navX gyro heading
     */ 
    public void resetHeading() {
        navX.reset();
    }

    /**
     * turnToHeading()
     * Turns the robot to a specified heading using PIDCalc and the navX
     * @param heading heading to turn to
     */ 
    public void turnToHeading(double heading) {
        pidOutputNavX = pidNavX.calculateOutput(heading, navX.getYaw());
        leftRear.set(ControlMode.PercentOutput, pidOutputNavX * 0.6);
        rightRear.set(ControlMode.PercentOutput, pidOutputNavX * 0.6);
    }

    /**
     * reachedHeading()
     * Determines if the drivetrain has reached the target heading
     * @param heading heading to be reached
     * @return returns true if the robot is within 2 degrees of wanted heading
     */ 
    public boolean reachedHeading(double heading) {
        if(navX.getYaw() <= (heading + 5) && navX.getYaw() >= (heading - 5)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * shiftHigh()
     * Shifts the drivetrain into high gear
     */ 
    public void shiftHigh() {
        shifter.set(DoubleSolenoid.Value.kForward);
    }

    /**
     * shiftLow()
     * Shifts the drivetrain into low gear
     */ 
    public void shiftLow() {
        shifter.set(DoubleSolenoid.Value.kReverse);
    }

    /**
     * noShiftInput()
     * Leaves the shifters where they're at
     */ 
    public void noShiftInput() {
        shifter.set(DoubleSolenoid.Value.kOff);
    }

    /**
     * configTrajectory()
     * Creates a trajectory based on waypoints specified
     * Takes a long time to run, use a motion profile generator and load files from roboRIO instead
     * @param points waypoints to follow
     * @return returns a TankModifier based off our drivetrain
     */ 
    public TankModifier configTrajectory(Waypoint[] points) {
        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 1.7, 2.0, 60.0);
        Trajectory trajectory = Pathfinder.generate(points, config);
        // 2.16 feet in meters = 0.658368
        TankModifier modifier = new TankModifier(trajectory).modify(0.658368);
        return modifier;
    }

    public void resetEncoders() {
        leftRear.setSelectedSensorPosition(0, 0, 0);
        rightRear.setSelectedSensorPosition(0, 0, 0);
    }

}
