/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import static frc.robot.Constants.SHOOTER_OUTPUT_TO_ENCODER_RATIO;
import static frc.robot.Constants.TICKS_PER_ROTATION;
import static frc.robot.Constants.kD_SHOOTER;
import static frc.robot.Constants.kF_SHOOTER;
import static frc.robot.Constants.kIZone_SHOOTER;
import static frc.robot.Constants.kI_SHOOTER;
import static frc.robot.Constants.kP_SHOOTER;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.RobotMap;

/**
 * The Shooter launches "Power Cells" from the robot to the "Power Port"
 */
public class Shooter extends SubsystemBase {

    public TalonFX shooterA = new TalonFX(RobotMap.shooterA);
    public TalonFX shooterB = new TalonFX(RobotMap.shooterB);
    public VictorSPX topWheel = new VictorSPX(RobotMap.topWheel);

    public boolean isFar = false;

    public Shooter() {
        shooterA.configFactoryDefault();
        shooterB.configFactoryDefault();
        shooterA.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

        shooterA.setInverted(true);
        shooterA.setSensorPhase(true);

        shooterB.setInverted(false);
        shooterB.setSensorPhase(true);

        shooterA.setNeutralMode(NeutralMode.Coast);
        shooterB.setNeutralMode(NeutralMode.Coast);
        topWheel.setNeutralMode(NeutralMode.Coast);

        shooterA.config_kP(0, kP_SHOOTER);
        shooterA.config_kI(0, kI_SHOOTER);
        shooterA.config_kD(0, kD_SHOOTER);
        shooterA.config_kF(0, kF_SHOOTER);
        shooterA.config_IntegralZone(0, kIZone_SHOOTER);

        shooterA.clearStickyFaults();
        shooterB.clearStickyFaults();

        shooterB.follow(shooterA);
        
        topWheel.config_kP(0, kP_SHOOTER);
        topWheel.config_kI(0, kI_SHOOTER);
        topWheel.config_kD(0, kD_SHOOTER);
        topWheel.config_kF(0, kF_SHOOTER);
        topWheel.config_IntegralZone(0, kIZone_SHOOTER);

        topWheel.setInverted(true);
    }

    public void setShooterSpeed(double speed) {
        shooterA.set(ControlMode.PercentOutput, speed);
    }

    public double calculateDesiredOutput(double x) {
        double a = 36;
        double b = -468.2;
        double c = 5900;
        double rpmGenerated = ((a * (Math.pow(x,2))) + (b * x) + c); 
        double clampedRPM = MathUtil.clamp(rpmGenerated, 4000, 8000);
        return clampedRPM;
    }

    public void resetShooterPosition() {
        shooterA.setSelectedSensorPosition(0);
    }

    public double getShooterRotations() {
        return shooterA.getSelectedSensorPosition() / SHOOTER_OUTPUT_TO_ENCODER_RATIO / TICKS_PER_ROTATION;
    }

    public double getShooterRPM() {
        return shooterA.getSelectedSensorVelocity() / SHOOTER_OUTPUT_TO_ENCODER_RATIO / TICKS_PER_ROTATION * 10.0 * 60.0;
    }

    public void setShooterRPM(double rpm) {
        shooterA.set(ControlMode.Velocity, shooterRPMToNativeUnits(rpm));
    }

    public void setTopWheel(double speed) {
        topWheel.set(ControlMode.PercentOutput, speed);
    }

    public void setTopWheelAuto() {
        if (RobotContainer.hood.isFar) {
            topWheel.set(ControlMode.PercentOutput, .85);
        }
        else {
            topWheel.set(ControlMode.PercentOutput, Constants.TOP_WHEEL_INIT_OUTPUT);
        }
    }

    public double calculateDesiredHoodPosition(double x) {
        double a = 1.49787;
        double b = 0.537159;
        double c = -7.36445;
        double d = 12.3024;
        double f = -27.0558;
        double g = 28.2696;
        double h = -11.8597;
        double i = 1.79898;

        double desiredPosition = 
            (a*Math.pow(x, b)) + 
            (c*Math.pow(x, (b-1))) + 
            (d*Math.pow(x, (b-2))) + 
            (f*Math.pow(x, (b-3))) + 
            (g*Math.pow(x, (b-4))) + 
            (h*Math.pow(x, (b-5))) + 
            (i*Math.pow(x, (b-6)));
        
        return desiredPosition;
    }

    public double shooterRPMToNativeUnits(double rpm) {
        return rpm * SHOOTER_OUTPUT_TO_ENCODER_RATIO * TICKS_PER_ROTATION / 10.0 / 60.0;
    }

    public double degreesToTicks(double degrees) {
        double updatedDegrees = MathUtil.clamp(degrees, 100, 145);
        return (updatedDegrees - Constants.HOOD_MINIMUM_VALUE) / Constants.HOOD_RANGE;
      }

    public double ticksToDegrees(double ticks) {
        double updatedTicks = MathUtil.clamp(ticks, 0, 1);
        return (updatedTicks * Constants.HOOD_RANGE) + Constants.HOOD_MINIMUM_VALUE;
    }

    public void setIsFar(boolean isFar){
        this.isFar = isFar;
    }

    public boolean isFar(){
        return isFar;
    }

    @Override
    public void periodic() {
        // if (getShooterRPM() >= 5100) {
        //     isFar = true;
        // }
        // else {
        //     isFar = false;
        // }
    }
}