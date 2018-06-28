package frc.team364.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team364.robot.RobotMap;
import frc.team364.robot.commands.teleop.TeleopArmCommand;

public class ArmSystem extends Subsystem {

    private TalonSRX arm;
	private Encoder armEncoder;
	private PIDCalc pidArm;
	private double pidArmOutput;

    public ArmSystem() {
        arm = new TalonSRX(RobotMap.arm);
		pidArm = new PIDCalc(0.1, 0, 0, "Arm");
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
        return arm..getSelectedSensorPosition(0);
    }

    public void armStop() {
        arm.set(ControlMode.PercentOutput, 0);
    }

	// This should be all you need to set the position you want.
	// You may want to do some math to find out the counts per degree.
	public void moveArmToPosition(int counts){
		pidArmOutput = pidArm.calculateOutput(counts, getArmEncoderPosition());
		arm.set(ControlMode.PercentOutput, pidArmOutput);
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