package frc.team364.robot.autons;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team364.robot.commands.auto.arm.ArmMoveToPosition;
import frc.team364.robot.commands.auto.arm.MoveArmDown;
import frc.team364.robot.commands.auto.arm.moveArmToSwitchPosition;
import frc.team364.robot.commands.auto.drive.*;
import frc.team364.robot.commands.auto.intake.OuttakeCube;

/**
 * Auto file - Objective - Score in the Left Switch
 */
public class LeftSwitch extends CommandGroup {
    /**
     * Objective - Score in the LeftSwitch
     *<p>1 Drive Foward
     */
    public LeftSwitch() {
        
        //TODO: make sure that the potentimeter is at a certain value
        //TODO: TEST THESE VALUES FOR DRIVE AND TURN
        //addSequential(new moveArmToSwitchPosition()); uncomment this once this line of code is determined to work
        addSequential(new DriveStraightForCounts(1500, false, false));//1
        addSequential(new TurnToHeading(-23));//2
        addSequential(new DriveStraightForCounts(5000, false, false));//3
        addSequential(new TurnToHeading(26));//4
        addParallel(new MoveArmDown());
        addSequential(new DriveStraightForCountsQuick(2000, false, false));//5
        addSequential(new OuttakeCube());
        addSequential(new DriveStraightForCounts(1500, true, false));//7
    
    }
}