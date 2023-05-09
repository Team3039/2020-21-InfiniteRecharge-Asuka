// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Hopper.HopperControlMode;

public class SetIntake extends CommandBase {
  /** Creates a new SetIntake. */
  public SetIntake() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.intake.acuateIntake(true);
    RobotContainer.intake.setIntakeSpeed(.8);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.hopper.setControlMode(HopperControlMode.INTAKING);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.intake.acuateIntake(false);
    RobotContainer.intake.setIntakeSpeed(0);
    RobotContainer.hopper.setControlMode(HopperControlMode.IDLE);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
