// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Intake extends SubsystemBase {

  public Solenoid intakeTilt = new Solenoid(RobotMap.intakeTilt);

  public TalonSRX intake = new TalonSRX(RobotMap.intake);

  /** Creates a new Intake. */
  public Intake() {
  }

  public void setIntakeSpeed(double intakeSpeed) {
    intake.set(ControlMode.PercentOutput, intakeSpeed);
  }
  
  public void stopIntake(){
    intake.set(ControlMode.PercentOutput, 0);
  }

  public void actuateIntake(boolean isExtended) {
    intakeTilt.set(isExtended);
  }

  @Override
  public void periodic() {

    // This method will be called once per scheduler run
  }
}
