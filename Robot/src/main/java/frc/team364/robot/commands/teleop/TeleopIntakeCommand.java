/*
 * George and Keanu:
 * This is the default intake command and will run during teleop since
 * it is the default command of IntakeSystem. Hopefully you're picking up
 * on how this works now.
 */

package frc.team364.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.team364.robot.Robot;



public class TeleopIntakeCommand extends Command {

    public TeleopIntakeCommand() {
        requires(Robot.intakeSystem);
    }

    @Override
    protected void execute() {
       
        // Run the intake/outtake on button press.
        // No toggle.
        if(Robot.oi.controller.getRawAxis(2) > 0.5) {
            Robot.intakeSystem.intake();
            System.out.println("Intake is running");
        } else if(Robot.oi.controller.getRawAxis(3) > 0.2) {
            Robot.intakeSystem.outtakeForPressure();
            System.out.println("Outtake is running");
        } else {
            Robot.intakeSystem.intakeStop();
        }

    }


    

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.intakeSystem.intakeStop();
    }


}