package frc.team364.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team364.robot.RobotMap;
import frc.team364.robot.PIDCalc;
import frc.team364.robot.commands.teleop.TeleopArmCommand;
import edu.wpi.first.wpilibj.AnalogInput;

public class ArmSystem extends Subsystem {

    private TalonSRX arm;
	private PIDCalc pidArm;
	private double pidArmOutput;
    private AnalogInput pot;

    public ArmSystem() {
        arm = new TalonSRX(RobotMap.arm);
		pidArm = new PIDCalc(0.1, 0, 0, 0, "Arm");
        pot = new AnalogInput(0);
    }

    
    protected void initDefaultCommand() {
        setDefaultCommand(new TeleopArmCommand());
    }
    /**
     * armForward()
     * moves the arm forward
     */
    public void armForward() {
        arm.set(ControlMode.PercentOutput, 1);
    }
    /**
     * armBackward()
     * moves the arm backward
     */
    public void armBackward(){
        arm.set(ControlMode.PercentOutput, -1);
    }

    /**
     * armStop()
     * stops the arm
     */
    public void armStop() {
        arm.set(ControlMode.PercentOutput, 0);
    }

	// This should be all you need to set the position you want.
    // You may want to do some math to find out the counts per degree.
    /**
     * moveArmToPosition()
     * moves the arm to reach a desired potentiometer voltage
     */
	public void moveArmToPosition(double voltage){
		pidArmOutput = pidArm.calculateOutput(voltage, getPotVoltage());
		arm.set(ControlMode.PercentOutput, pidArmOutput);
    }
    /**
     * getPotVoltage()
     * returns the current voltage reading of the potentiometer
     */
      public double getPotVoltage(){
        return  pot.getVoltage();
    }

}