package frc.team364.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team364.robot.autons.*;
import frc.team364.robot.subsystems.*;

public class Robot extends TimedRobot {

    public static DriveSystem driveSystem;
    public static IntakeSystem intakeSystem;
    public static ClawSystem clawSystem;
    public static ArmSystem armSystem;
    public String gameData = "";

    public static OI oi;

    public static Command DriveStraight;


    public UsbCamera camera;

    /**
     * robotInit()
     * Note the setPeriod(0.05) function. This is a function with the
     * TimedRobot class that sets the robot loop period (50ms in this case).
     * This will allow the motion profiling code to run at a constant rate without
     * fluctuation. 
     */
	@Override
    public void robotInit() {
        setPeriod(0.02);
	    driveSystem = new DriveSystem();
        intakeSystem = new IntakeSystem();
        clawSystem = new ClawSystem();
        armSystem = new ArmSystem();
	    oi = new OI();
	    DriveStraight = new DriveStraight();
        camera = CameraServer.getInstance().startAutomaticCapture("Video", 0);
        camera.setResolution(640, 480);
        driveSystem.resetEncoders();
        driveSystem.resetHeading();
    }

    @Override
    public void disabledInit() { }

    @Override
    public void autonomousInit() {
        Scheduler.getInstance().removeAll();
	    gameData = DriverStation.getInstance().getGameSpecificMessage();
        if(oi.autoSelectorButton.get()) {
            if(gameData.charAt(1) == 'L') {
                DriveStraight.start();
            } else {
                DriveStraight.start();
            }
        } else {
            if(gameData.charAt(0) == 'L') {
                DriveStraight.start();
            } else {
                DriveStraight.start();
            }
        }
        driveSystem.resetHeading();
        driveSystem.resetEncoders();
    }

    @Override
    public void autonomousPeriodic() {
        putSmartDashVars();
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        Scheduler.getInstance().removeAll();
        driveSystem.leftRear.configOpenloopRamp(0, 0);
        driveSystem.rightRear.configOpenloopRamp(0, 0); 
    }

    @Override
    public void testInit() { 

    }

    @Override
    public void disabledPeriodic() {
        putSmartDashVars();
    }

    @Override
    public void teleopPeriodic() {
	    Scheduler.getInstance().run();
        putSmartDashVars();
    }

    @Override
    public void testPeriodic() { 

    }

    private void putSmartDashVars() {
        SmartDashboard.putNumber("Gyro Angle", driveSystem.getGyroAngle());
        SmartDashboard.putNumber("Left Encoder Counts", driveSystem.getLeftEncoderPosition());
        SmartDashboard.putNumber("Right Encoder Counts", driveSystem.getRightEncoderPosition());
        //SmartDashboard.putString("Current auto", gameData.charAt(0));
    }
}
