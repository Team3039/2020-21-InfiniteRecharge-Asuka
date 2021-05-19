// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class Drive extends SubsystemBase {

  TalonFX leftFrontDrive = new TalonFX(4); 
  TalonFX leftRearDrive = new TalonFX(5);
  TalonFX rightFrontDrive = new TalonFX(12);
  TalonFX rightRearDrive = new TalonFX(13); 
  
  /** Creates a new Drive. */
  public Drive() {
    leftRearDrive.follow(leftFrontDrive);
    rightRearDrive.follow(rightFrontDrive); 
  }
  
  public void driveWithJoysticks() {
    double y = RobotContainer.driverPad.getLeftYAxis() * Constants.DRIVER_Y;
    double rot = RobotContainer.driverPad.getRightXAxis() * Constants.DRIVER_ROT;

    double leftOutput = y + rot;
    double rightOutput = y - rot; 
    
    leftFrontDrive.set(ControlMode.PercentOutput, leftOutput);
    rightFrontDrive.set(ControlMode.PercentOutput, rightOutput); 
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
