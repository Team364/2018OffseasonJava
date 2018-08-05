package frc.team364.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {

    public Joystick leftStick;
    public Joystick rightStick;
    public Joystick controller;

    public double leftPower;
    public double rightPower;

    public JoystickButton shiftLow;
    public JoystickButton shiftHigh;

    public JoystickButton pinchButton;
    public JoystickButton intakeButton;
    public JoystickButton outtakeButton;
    public JoystickButton armButton;

    public JoystickButton autoSelectorButton;

    public OI() {

        leftStick = new Joystick(0);
        rightStick = new Joystick(1);
        controller = new Joystick(2);

        shiftLow = new JoystickButton(leftStick, 1);
        shiftHigh = new JoystickButton(rightStick, 1);

        pinchButton = new JoystickButton(controller, 5);

        autoSelectorButton = new JoystickButton(controller, 10);
    }
}
