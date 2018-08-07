/*
 * George and Keandre:
 * This is the PID calculation class. It calculates a PIDF output for
 * a motor using a setpoint and actual values.
 * Create an object for each subsystem that needs to use PID and run resetPID() and then
 * calculateOutput().
 */

package frc.team364.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDCalc {

    private String pidName;
    private double kP = 0;
    private double kI = 0;
    private double kD = 0;
    private double kF = 0;
    private double derivative = 0;
    private double integral = 0;
    private double prev_error = 0;
    private double error = 0;
    private double result = 0;
    /**
     * PIDCalc()
     * sets the PID and F terms for a PIDF conotroller for a particluar value
     * @param pTerm Porportional Gain
     * @param iTerm Intergal Gain
     * @param dTerm Derivative Gain
     * @param fTerm Filter Time Constant
     */
    public PIDCalc(double pTerm, double iTerm, double dTerm, double fTerm, String name) {
        setPIDParameters(pTerm, iTerm, dTerm, fTerm);
        pidName = name;
    }
    /**
     * calculateOutput()
     * calculates the output of a PIDF controller using the pre-determined values for PIDF. This means that the setpoint is trying to be reached.
     * @param setpoint Desired Value
     * @param value Value at which a sensor is currently at
     * @return 1 or -1 depending on the result of the calculation
     */
    public double calculateOutput(double setpoint, double actual) {
        error = setpoint - actual;
        integral += (error * 0.02);
        derivative = (error - prev_error) / 0.02;
        result = kF + (kP * error) + (kI * integral) + (kD * derivative);
        if(result > 1) {
            result = 1;
        } else if(result < -1) {
            result = -1;
        }
        smartDashVars();
        prev_error = error;
        return result;
    }
    /**
     * resetPID()
     * sets the values for PID and F to zero
     */
    public void resetPID() {
        derivative = 0;
        integral = 0;
        prev_error = 0;
        error = 0;
    }

    /**
     * setPIDParameters()
     * sets the PIDF parameters from PIDCalc to variables which are used in the operation. Only to be used in PIDCalc file
     * @param pTerm Porportional Gain for a given PIDF controller
     * @param iTerm Intergal Gain for a given PIDF controller
     * @param dTerm Derivative Gain for a given PIDF controller
     * @param fTerm Filter Time Constant for a given PIDF controller
     */
    public void setPIDParameters(double pTerm, double iTerm, double dTerm, double fTerm) {
        kP = pTerm;
        kI = iTerm;
        kD = dTerm;
        kF = fTerm;
    }

    private void smartDashVars() {
        SmartDashboard.putNumber(pidName + "Error", error);
        SmartDashboard.putNumber(pidName + "Prev Error", prev_error);
        SmartDashboard.putNumber(pidName + "Integral", integral);
        SmartDashboard.putNumber(pidName + "Derivative", derivative);
        SmartDashboard.putNumber(pidName + "Result", result);
        SmartDashboard.putNumber(pidName + "kP", kP);
    }

}
