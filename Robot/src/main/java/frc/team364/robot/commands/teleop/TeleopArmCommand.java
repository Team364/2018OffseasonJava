package frc.team364.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.team364.robot.Robot;
import frc.team364.robot.subsystems.ArmSystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TeleopArmCommand extends Command {

    public ArmSystem armSystem = Robot.armSystem;
    public boolean controlsActive = false;
    public int switchCounter = 0;
    public int vaultCounter = 0;
          /**
         * 0 - nothing happening
         * 1 - switch
         * 2 - vault
         * 3 - other
         * 4 - other
         * 5 - dPad
         */
    private int armState;
    private boolean buttonActive;
    private boolean switchButtonLatch;
    private boolean vaultButtonLatch;

    /**
     * Command used for teleop control specific to the arm system
     */
    public TeleopArmCommand() {
        requires(Robot.armSystem);
  
        armState = 0;
        buttonActive = false;
        switchButtonLatch = false;
        vaultButtonLatch = false;
    }

    @Override
    protected void execute() {
       System.out.println("Arm State: " + armState);
       System.out.println("switchCounter " + switchCounter);
//System.out.println("POV active: " + POVactive());
       //System.out.println("Button active: " + buttonActive);
       //System.out.println("sButton active: " + switchButtonLatch);
       //System.out.println("vButton active: " + vaultButtonLatch);
              //System.out.println(armSystem.getPotVoltage());
        //A Button Switch Button
            if(Robot.oi.switchButton.get()){
                armState = 1;
                switchButtonLatch = true;
                buttonActive = true;
                System.out.println("switchButtonLatch is true");
                System.out.println("The A Button has been pressed");
                if(switchCounter < 1){
                switchCounter++;
                }
                }//A Button Switch Button

            
            if(switchButtonLatch = true){
            if(armState == 1){
                if(switchCounter == 1){
                    System.out.println("arm should move to a button pos");
                    armSystem.moveArmToPosition(3);//If changed update the auto command 3.12
                    }else{
                        armSystem.armStop();
                    }
                }
            
                if(armState == 1){
                if(armSystem.getPotVoltage() >= 3){
                    System.out.println("switchButtonShouldStop");
                    switchButtonLatch = false;
                    buttonActive = false;
                    armState = 0;
                    switchCounter = 0;
                    armSystem.armStop();
    }
}
            }//Switch Latch
    if(Robot.oi.vaultButton.get()){
        armState = 2;
        vaultButtonLatch = true;
        buttonActive = true;
        System.out.println("vaultButtonLatch is true");
        System.out.println("The X Button has been pressed");
        if(vaultCounter < 1){
        vaultCounter++;
        }
        }//Vault Button

    
    if(vaultButtonLatch = true){
    if(armState == 2){
       Robot.armSystem.armBackward();
        }
    
        if(armState == 2){
        if(armSystem.getPotVoltage() <= 0.8){
            System.out.println("vaultButtonShouldStop");
            vaultButtonLatch = false;
            buttonActive = false;
            armState = 0;
            vaultCounter = 0;
            armSystem.armStop();
}
        }
    }//Vault Latch

    if((POVactive())&&(buttonActive)){
        armState = 0;
    }
    
      if(Robot.oi.controller.getPOV() == 0){
          Robot.armSystem.armForward();
          armState = 5;
      }else if(Robot.oi.controller.getPOV() == 180){
          Robot.armSystem.armBackward();
          armState =5;
      }else{
          if(!buttonActive){
          armState = 0;
          }
            }

            if(armState == 0){
                if(armSystem.getPotVoltage() >= 4.2){
                    Robot.armSystem.powerArm(-0.08);
                    }
                    else if(armSystem.getPotVoltage() < 4.2){
                    Robot.armSystem.powerArm(0.1);
                    }
                     else if(armSystem.getPotVoltage() < 3){
                    Robot.armSystem.powerArm(0.2);
                    }
                }
        
    }
//execute

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

    





