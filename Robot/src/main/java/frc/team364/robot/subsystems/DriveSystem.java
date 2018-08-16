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

    /**
     * DriveSystem()
     * Drive train used for moving about the field
     */
    public DriveSystem() {
        
        // Initialize TalonSRX objects
        leftFront = new TalonSRX(RobotMap.leftFrontDrive);
        leftRear = new TalonSRX(RobotMap.leftRearDrive);
        rightFront = new TalonSRX(RobotMap.rightFrontDrive);
        rightRear = new TalonSRX(RobotMap.rightRearDrive);

        // Initialize DoubleSolenoid shifter object
        shifter = new DoubleSolenoid(RobotMap.shifterPort1, RobotMap.shifterPort2);
        
	    // Set the front drive motors to follow the rear
        leftRear.follow(leftFront);
        rightRear.follow(rightFront);

	    // Config PF on left side
        leftFront.config_kP(0, 0.25, 100);
        leftFront.config_kF(0, 1, 100);

	    // Config PF on right side
        rightFront.config_kP(0, 0.25, 100);
        rightFront.config_kF(0, 1, 100);

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
        leftFront.set(ControlMode.PercentOutput, left);
        rightFront.set(ControlMode.PercentOutput, right);//negate in case of backwards
    }

    /**
     * stop()
     * Stops the drive motors
     * Use this in auto to stop the drivetrain inbetween commands
     */ 
    public void stop() {
        leftFront.set(ControlMode.PercentOutput, 0);
        rightFront.set(ControlMode.PercentOutput, 0);
    }

    /**
     * getLeftEncoderPosition()
     * @return returns the left encoder position in counts
     */ 
    public int getLeftEncoderPosition() {
        return leftFront.getSelectedSensorPosition(0);
    }

    /**
     * getRightEncoderPosition()
     * @return returns the right encoder position in counts
     */ 
    public int getRightEncoderPosition() {
        return rightFront.getSelectedSensorPosition(0);//Negate if the right side gets negated
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
     * @param backwards indicate whether the robot is to move backwards
     * @param useGyro indicate whether the robot is to use gyro to correct path
     */ 
    public void driveStraightToEncoderCounts(int counts, boolean backwards, boolean useGyro) {
        if(backwards) {
            pidOutputLeft = pidLeft.calculateOutput(counts, -getLeftEncoderPosition());
            pidOutputRight = pidRight.calculateOutput(counts, -getRightEncoderPosition());
            pidOutputNavX = pidNavX.calculateOutput(0, getGyroAngle());
            if(useGyro) {
                leftFront.set(ControlMode.PercentOutput, -pidOutputLeft + pidOutputNavX);
                rightFront.set(ControlMode.PercentOutput, pidOutputRight + pidOutputNavX);
            } else {
                leftFront.set(ControlMode.PercentOutput, -pidOutputLeft);
                rightFront.set(ControlMode.PercentOutput, pidOutputRight);
            }
        } else {
            pidOutputLeft = pidLeft.calculateOutput(counts, getLeftEncoderPosition());
            pidOutputRight = pidRight.calculateOutput(counts, getRightEncoderPosition());
            pidOutputNavX = pidNavX.calculateOutput(0, getGyroAngle());
            if(useGyro) {
                leftFront.set(ControlMode.PercentOutput, pidOutputLeft + pidOutputNavX);
                rightFront.set(ControlMode.PercentOutput, -pidOutputRight + pidOutputNavX);
            } else {
                leftFront.set(ControlMode.PercentOutput, pidOutputLeft);
                rightFront.set(ControlMode.PercentOutput, -pidOutputRight);
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

        double leftFrontPos = leftFront.getSelectedSensorPosition(0);
        if(Math.abs(leftFrontPos) >= (counts - 100)) {
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
        //Remove dampening on the pidOutputNavX to increase turn speed
        //Possibly add navX.zeroYaw(); to see if that corrects error
        pidOutputNavX = pidNavX.calculateOutput(heading, navX.getYaw());
        leftFront.set(ControlMode.PercentOutput, pidOutputNavX * 0.6);
        rightFront.set(ControlMode.PercentOutput, pidOutputNavX * 0.6);
    }

    /**
     * reachedHeading()
     * Determines if the drivetrain has reached the target heading
     * @param heading heading to be reached
     * @return returns true if the robot is within 2 degrees of wanted heading
     */ 
    public boolean reachedHeading(double heading) {
        //Possibly edit this to see what error we get for turning to particular angles
        //TODO: Tweek these values and see what occurs in auto
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
     * resetEncoders()
     * This resets the encoders on the Talons to zero
    */
    public void resetEncoders() {
        leftFront.setSelectedSensorPosition(0, 0, 0);
        rightFront.setSelectedSensorPosition(0, 0, 0);
    }

}
