package frc.team364.robot.autons;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team364.robot.commands.auto.drive.*;

public class DriveStraight extends CommandGroup {

    public DriveStraight() {
       
        addSequential(new DriveStraightForCounts(15000, false, true)); // Drive to scale
    
    }
}