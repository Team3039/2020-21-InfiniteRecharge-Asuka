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
import frc.robot.RobotMap;

/**
 * The Shooter launches "Power Cells" from the robot to the "Power Port"
 */
public class Shooter extends SubsystemBase {

    public TalonFX shooterA = new TalonFX(RobotMap.shooterA);
    public TalonFX shooterB = new TalonFX(RobotMap.shooterB);
    public VictorSPX topWheel = new VictorSPX(RobotMap.climberC);

    public Servo hoodServoA = new Servo(RobotMap.hoodServo);
    public Servo hoodServoB = new Servo(RobotMap.hoodServoB);
    
    public boolean isFar = false;

    public Shooter() {
        shooterA.configFactoryDefault();
        shooterB.configFactoryDefault();
        shooterA.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

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

    public double shooterRPMToNativeUnits(double rpm) {
        return rpm * SHOOTER_OUTPUT_TO_ENCODER_RATIO * TICKS_PER_ROTATION / 10.0 / 60.0;
    }

    @Override
    public void periodic() {
    }
}