// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.RobotMap;

public class Drivetrain extends SubsystemBase {

  TalonFX leftFrontDrive = new TalonFX(RobotMap.leftFrontDrive); 
  TalonFX leftRearDrive = new TalonFX(RobotMap.leftRearDrive);
  TalonFX rightFrontDrive = new TalonFX(RobotMap.rightFrontDrive);
  TalonFX rightRearDrive = new TalonFX(RobotMap.rightRearDrive); 
  
  /** Creates a new Drive. */
  public Drivetrain() {
    leftRearDrive.follow(leftFrontDrive);
    rightRearDrive.follow(rightFrontDrive); 
  }
  
  public void driveWithJoysticks() {
    double y = RobotContainer.driverPad.getLeftYAxis() * Constants.DRIVER_Y;
    double rot = RobotContainer.driverPad.getRightXAxis() * Constants.DRIVER_ROT * -1;

    double leftOutput = rot + y;
    double rightOutput = rot - y; 
    
    leftFrontDrive.set(ControlMode.PercentOutput, leftOutput);
    rightFrontDrive.set(ControlMode.PercentOutput, rightOutput); 
  }

  @Override
  public void periodic() {
    driveWithJoysticks();
  }
}
