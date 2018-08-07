package frc.team364.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team364.robot.RobotMap;
import frc.team364.robot.commands.teleop.TeleopClawCommand;

public class ClawSystem extends Subsystem {

    private DoubleSolenoid pincher;

    /**
     * ClawSystem()
     * used to open and close pincher for grabbing power cubes
     */
    public ClawSystem() {
        pincher = new DoubleSolenoid(RobotMap.pinchPistonPort1, RobotMap.pinchPistonPort2);
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new TeleopClawCommand());
    }
    /**
     * openPincher()
     * opens pincher by setting the solenoid for the piston to forward
     */
    public void openPincher() {
        pincher.set(DoubleSolenoid.Value.kForward);
    }

    /**
     * closePincher()
     * closes the pincher by setting the solenoid for the piston to reverse
     */
    public void closePincher() {
        pincher.set(DoubleSolenoid.Value.kReverse);
    }

    /**
     * pincherOff()
     * sets the picher solenoid to off
     */
    public void pincherOff() {
        pincher.set(DoubleSolenoid.Value.kOff);
    }

}