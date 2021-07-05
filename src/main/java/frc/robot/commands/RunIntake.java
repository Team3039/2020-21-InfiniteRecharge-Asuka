// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class RunIntake extends CommandBase {
  /** Creates a new RunIntake. */
  public RunIntake() {
    addRequirements(RobotContainer.hopper);
    addRequirements(RobotContainer.intake);
  }

    // Use addRequirements() here to declare subsystem dependencies.

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {   
     RobotContainer.intake.actuateIntake(true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
     RobotContainer.intake.setIntakeSpeed();
     RobotContainer.hopper.index();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.intake.actuateIntake(false);
    RobotContainer.intake.stopIntake();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
