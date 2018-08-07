package frc.team364.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {

    /**
    * leftStick
    * Logitech Attack 3 Joystick used for the left side of the drive train - drive system
    */
    public Joystick leftStick;
    /**
     * rightStick
     * Logitech Attack 3 Joystick used for the right side of the drive train - drive system
     */
    public Joystick rightStick;
    /**
     * controller
     * logitech f310 controller used for the arm and claw -- arm system, claw system, intake system
     */
    public Joystick controller;


    //public double leftPower; Not sure what these two actually do
    //public double rightPower;

    /**
     * shiftLow
     * Trigger used to shift the transmission into low gear - leftStick - drive system
     */
    public JoystickButton shiftLow;
    /**
     * shiftHigh
     * Trigger used to shift the transmission into high gear - rightStick - drive system
     */
    public JoystickButton shiftHigh;

    /**
     * pinchButton
     * Button used to open and close the claw - controller - claw system
     */
    public JoystickButton pinchButton;
    /**
     * intakeButton
     * Trigger used to run the intake forward - controller - intake system
     */
    public JoystickButton intakeButton;

    /**
     * outtakeButton
     * Trigger used to run the intake in reverse - controller - intake system
     */
    public JoystickButton outtakeButton;

    public OI() {
        //Physical control objects

 
        leftStick = new Joystick(0);
        /**
         * rightStick
         */
        rightStick = new Joystick(1);
        controller = new Joystick(2);

        //Drive System Joystick Buttons
        shiftLow = new JoystickButton(leftStick, 1);
        shiftHigh = new JoystickButton(rightStick, 1);

        //Claw System JoyStick Buttons
        //TODO: Add Buttons for preset arm positions
        pinchButton = new JoystickButton(controller, 5);
    }
}
