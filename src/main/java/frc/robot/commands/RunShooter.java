// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class RunShooter extends CommandBase {
  /** Creates a new RunShooter. */
  double rpm;
  public RunShooter(double rpm) {
    this.rpm = rpm;
    addRequirements(RobotContainer.shooter);
    addRequirements(RobotContainer.turret);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.shooter.setShooterRPM(rpm);
    RobotContainer.turret.setTrackingMode();
    RobotContainer.turret.trackTarget();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.shooter.setShooterRPM(0);
    RobotContainer.turret.setDriverCamMode();
    RobotContainer.turret.setTurretPosition(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
