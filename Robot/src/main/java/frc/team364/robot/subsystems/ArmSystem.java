package frc.team364.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team364.robot.RobotMap;
import frc.team364.robot.commands.teleop.TeleopArmCommand;

public class ArmSystem extends Subsystem {

    private TalonSRX arm;

    public ArmSystem() {
        arm = new TalonSRX(RobotMap.arm);

    }

    
    protected void initDefaultCommand() {
        setDefaultCommand(new TeleopArmCommand());
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
        public void ArmMoveForEncoderCounts(boolean armBackwards){

        }

        public void ForwardForTime(int armTime){
        arm.set(ControlMode.PercentOutput, 1);

    }
      public void BackwardForTime(int armTime){
        arm.set(ControlMode.PercentOutput, -1);

    }
}