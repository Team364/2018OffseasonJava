package frc.team364.robot.autons;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team364.robot.commands.auto.drive.*;

/**
 * Auto file - Objective - Cross the line
 */
public class DriveStraight extends CommandGroup {
    /**
     * Objective - Cross the line
     *<p>1 Drive Foward
     */
    public DriveStraight() {
       
        addSequential(new DriveStraightForCounts(7000, false, true)); //1
        addSequential(new StopDriveMotors());
    
    }
}