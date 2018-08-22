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

    /**
     * Button used to move arm to position using potentiometer voltage
     */
    public JoystickButton switchButton;

    /**
     * Button used to move arm to backwards for picking up cubes from behind position using potentiometer voltage
     */
    public JoystickButton backCubePickupButton;
    

      /**
     * Button used to move arm to the full foward position - lowest possible on potentiometer - using potentiometer voltage
     */
    public JoystickButton vaultButton;

    /**
     * Button used to move the arm to a postion that can accept cubes directly from the exchange
     */
    public JoystickButton exchangeButton;
    public JoystickButton resetButton;

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
        
        pinchButton = new JoystickButton(controller, 5);

        //Arm Buttons
        switchButton = new JoystickButton(controller, 1);
        backCubePickupButton = new JoystickButton(controller, 2);
        vaultButton = new JoystickButton(controller, 3);
        exchangeButton = new JoystickButton(controller, 4);
        resetButton = new JoystickButton(controller, 7);

    }
}
