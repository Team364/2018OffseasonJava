package frc.team364.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team364.robot.RobotMap;
import frc.team364.robot.commands.teleop.TeleopArmCommand;
import edu.wpi.first.wpilibj.AnalogInput;

public class ArmSystem extends Subsystem {

    private TalonSRX arm;
	private PIDCalc pidArm;
	private double pidArmOutput;
    private AnalogInput pot;

    public ArmSystem() {
        arm = new TalonSRX(RobotMap.arm);
		pidArm = new PIDCalc(0.1, 0, 0, "Arm");
        pot = new AnalogInput(0);
    }

    
    protected void initDefaultCommand() {
        setDefaultCommand(new TeleopArmCommand());
    }
	
	public void resetEncoder() {
		arm.reset();
	}

    public void forward() {
        arm.set(ControlMode.PercentOutput, 1);
    }

    public void backward(){
        arm.set(ControlMode.PercentOutput, -1);
    }

    public int getArmEncoderPosition() {
        return arm.getSelectedSensorPosition(0);
    }

    public void armStop() {
        arm.set(ControlMode.PercentOutput, 0);
    }

	// This should be all you need to set the position you want.
	// You may want to do some math to find out the counts per degree.
	public void moveArmToPosition(double voltage){
		pidArmOutput = pidArm.calculateOutput(voltage, getPotVoltage());
		arm.set(ControlMode.PercentOutput, pidArmOutput);
    }

      public double getPotVoltage(){
        return  pot.getVoltage();
    }

	/* Lets take this stuff out for now.
	public void ForwardForTime(int armTime){
        arm.set(ControlMode.PercentOutput, 1);
    }
      public void BackwardForTime(int armTime){
        arm.set(ControlMode.PercentOutput, -1);
    }
	*/
}