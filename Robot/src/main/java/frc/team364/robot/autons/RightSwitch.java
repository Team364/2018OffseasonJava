package frc.team364.robot.autons;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team364.robot.commands.auto.arm.MoveArmDown;
import frc.team364.robot.commands.auto.arm.moveArmToSwitchPosition;
import frc.team364.robot.commands.auto.drive.*;
import frc.team364.robot.commands.auto.intake.OuttakeCube;

/**
 * Auto file - Objective - Score in the Right Switch
 */
public class RightSwitch extends CommandGroup {
    /**
     * Objective - Score in the Right Switch
     *<p>1 Drive Foward
     */
    public RightSwitch() {
       
        //TODO: make sure that the potentimeter is at a certain value
        //TODO: TEST THESE VALUES FOR DRIVE AND TURN
        //addSequential(new moveArmToSwitchPosition()); uncomment this once this line of code is determined to work
        addSequential(new DriveStraightForCounts(1500, false, false));//1
        addSequential(new TurnToHeading(15));//2
        addSequential(new DriveStraightForCounts(4000, false, false));//3
        addSequential(new TurnToHeading(-15));//4
        addSequential(new MoveArmDown());
        addSequential(new DriveStraightForCountsQuick(1700, false, false));//6
        addSequential(new OuttakeCube());
        addSequential(new DriveStraightForCounts(1500, true, false));//10
    
    }
}