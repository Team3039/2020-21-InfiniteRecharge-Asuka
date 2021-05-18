// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

  public Solenoid intakeTilt = new Solenoid(0);

  public TalonSRX intake = new TalonSRX(0);

  public void runIntake(){
    intake.set(ControlMode.PercentOutput, .9);
  }
  
  public void stopIntake(){
    intake.set(ControlMode.PercentOutput, 0);
  }

  public void extendIntake(){
    intakeTilt.set(true);
  }
  public void retractIntake(){
    intakeTilt.set(false);
  }
  
  /** Creates a new Intake. */
  public Intake() {
  }

  @Override
  public void periodic() {

    // This method will be called once per scheduler run
  }
}
