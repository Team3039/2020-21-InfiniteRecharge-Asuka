// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Climber extends SubsystemBase {
  /** Creates a new Climber. */

  public TalonSRX climberA = new TalonSRX(RobotMap.climberA);
  public TalonSRX climberB  = new TalonSRX(RobotMap.climberB);

  public Solenoid release = new Solenoid(RobotMap.climbRelease);

  public Climber() {
    climberB.follow(climberA);
    climberA.setNeutralMode(NeutralMode.Brake);
    setRelease(true);
  }

  public void setClimberSpeed(double percentOutput) {
    climberA.set(ControlMode.PercentOutput, percentOutput * -1);
  } 

  public void setRelease(boolean isReleased) {
    release.set(isReleased);
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
