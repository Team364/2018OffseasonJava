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

    /**
     * DriveSystem()
     * Drive train used for moving about the field
     */
    public static DriveSystem driveSystem;
    /**
     * IntakeSystem()
     * used to intake cubes into claw for transport and outtake cubes for scoring
     */
    public static IntakeSystem intakeSystem;
    /**
     * ClawSystem()
     * used to open and close pincher for grabbing power cubes
     */
    public static ClawSystem clawSystem;
    /**
     * ArmSystem()
     * used to rotate the arm about a pivot on the y-axis
     */
    public static ArmSystem armSystem;
    /**
     * Game Data for Alliance Switch, Scale, and Opposition Swtich
     */
    public String gameData = "";
    /**
     * Control initialization
     */
    public static OI oi;

    /**
     * DriveStraightAuto File
     */
    public static Command DriveStraight;

    /**
     * RightSwitchAuto File
     */
    public static Command RightSwitch;

    /**
     * LeftSwitchAuto File
     */
    public static Command LeftSwitch;

    /**
     * Driver Visual Camera
     */
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
        LeftSwitch = new LeftSwitch();
        RightSwitch = new RightSwitch();
        camera = CameraServer.getInstance().startAutomaticCapture("Video", 0);
        camera.setResolution(320, 240);
        driveSystem.resetEncoders();
        driveSystem.resetHeading();
    }

    @Override
    public void disabledInit() { }

    @Override
    public void autonomousInit() {
        Scheduler.getInstance().removeAll();
	    gameData = DriverStation.getInstance().getGameSpecificMessage();
            if(gameData.charAt(0) == 'L') {
                LeftSwitch.start();//DriveStraight.start()
            } else {
                RightSwitch.start();//DriveStraight.start()
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
        SmartDashboard.putNumber("Pot Voltage", armSystem.getPotVoltage());
        //SmartDashboard.putString("Current auto", gameData.charAt(0));
    }
}
