/*
 *  George and Keanu:
 *  This is the TeleopDriveCommand. This runs whenever there isn't another command
 *  running that requries the DriveSystem class. In Execute, the drive motors are
 *  given an output from the joysticks using a variable in the OI (Operator Interface) class.
 *  The shifters are also set using the triggers from each joystick.
 */

package frc.team364.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.team364.robot.Robot;

public class TeleopDriveCommand extends Command {

    public TeleopDriveCommand() {
        requires(Robot.driveSystem);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void end() {
        // This will probably never be called.
        Robot.driveSystem.stop();
    }

    @Override
    protected void execute() {
        Robot.driveSystem.tankDrive(Robot.oi.leftStick.getRawAxis(1), Robot.oi.rightStick.getRawAxis(1));
        if(Robot.oi.shiftHigh.get()) {
            Robot.driveSystem.shiftHigh();
        } else if(Robot.oi.shiftLow.get()) {
            Robot.driveSystem.shiftLow();
        } else {
            Robot.driveSystem.noShiftInput();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
