// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {

  public TalonSRX climberLeft = new TalonSRX(1);
  public TalonSRX climberRight = new TalonSRX(2);

  public Solenoid release = new Solenoid(0);

  public Climber() {
    climberLeft.setNeutralMode(NeutralMode.Brake);
    climberRight.setNeutralMode(NeutralMode.Brake);

    climberRight.follow(climberLeft);
  }

  public void releaseClimber() {
    release.set(true);
  }

  public void runWinches(double power) {
    climberLeft.set(ControlMode.PercentOutput, power);
  }

  public void stopWinches() {
    climberLeft.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void periodic() {
  }
}
