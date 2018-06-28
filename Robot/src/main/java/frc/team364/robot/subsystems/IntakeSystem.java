package frc.team364.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team364.robot.RobotMap;
import frc.team364.robot.commands.teleop.TeleopIntakeCommand;

public class IntakeSystem extends Subsystem {

    private TalonSRX leftIntake;
    private TalonSRX rightIntake;

    public IntakeSystem() {
        leftIntake = new TalonSRX(RobotMap.intakeLeft);
        rightIntake = new TalonSRX(RobotMap.intakeRight);
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new TeleopIntakeCommand());
    }

    public void intake() {
        leftIntake.set(ControlMode.PercentOutput, 1);
        rightIntake.set(ControlMode.PercentOutput, 1);
    }

    public void outtake() {
        leftIntake.set(ControlMode.PercentOutput, -1);
        rightIntake.set(ControlMode.PercentOutput, -1);
    }

    public void intakeStop() {
        leftIntake.set(ControlMode.PercentOutput, 0);
        rightIntake.set(ControlMode.PercentOutput, 0);
    }

}