package frc.team364.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.team364.robot.Robot;
import frc.team364.robot.subsystems.ArmSystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TeleopArmCommand extends Command {

    public boolean auto = true;
    public boolean armStay = false;
    public double voltage = 0.008;
    public ArmSystem armSystem = Robot.armSystem;
    /**
     * Command used for teleop control specific to the arm system
     */
    public TeleopArmCommand() {
        requires(Robot.armSystem);
    }

    @Override
    protected void execute() {
        auto = false;
        SmartDashboard.putBoolean("ArmStay", armStay);
        if(!POVactive()){
            //TODO: Put armButtons for preset postions here
            if(Robot.oi.switchButton.get()){
                armSystem.moveArmToPosition(3.12);//If changed update the auto command
            }
            if(Robot.oi.backCubePickupButton.get()){
                armSystem.moveArmToPosition(4.6);
            }
            if(Robot.oi.vaultButton.get()){
                armSystem.moveArmToPosition(0.08);
            }
            if(Robot.oi.exchangeButton.get()){
                armSystem.moveArmToPosition(4.2);
            }
        }else{
        if(armSystem.withinDangerZone() && !(Robot.oi.controller.getPOV() == 180)){
            Robot.armSystem.powerArm(-0.04);

        } else{

        
        //If up on the directional pad is pressed then the arm will move forward
        
        if(Robot.oi.controller.getPOV() == 0) {
            Robot.armSystem.armForward();
            armStay = false;
            
        //If down on the directional pad is pressed then the arm will move backwards
        } else if(Robot.oi.controller.getPOV() == 180) {
            Robot.armSystem.armBackward();
            armStay = false;

        } else {
            if(!auto){
                /*voltage = armSystem.getPotVoltage();
                armSystem.keepArmVoltage(voltage);*/
                if(armSystem.getPotVoltage() >= 4.2){
                Robot.armSystem.powerArm(-0.08);
                } else if(armSystem.getPotVoltage() < 4.2){
                Robot.armSystem.powerArm(0.2);
                }
                
                //Robot.armSystem.armStop();

                armStay = true;
                
            }
        }
    }
    }
}

public boolean POVactive() {
    if(Robot.oi.controller.getPOV() == -1){
        return false;
    }else{
        return true;
    }
}
        
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.armSystem.armStop();
    }

}

    





