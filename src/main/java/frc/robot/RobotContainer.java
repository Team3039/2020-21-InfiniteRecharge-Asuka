// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.commands.Climb;
import frc.robot.commands.FeedCells;
import frc.robot.commands.ReleaseClimber;
import frc.robot.commands.RunIntake;
import frc.robot.commands.RunShooter;
import frc.robot.commands.TurretFlip;
import frc.robot.commands.TestLimelight;
import frc.robot.controllers.PS4Gamepad;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  public static Drivetrain drivetrain = new Drivetrain();
  public static Intake intake = new Intake();
  public static Climber climber = new Climber();
  public static Shooter shooter = new Shooter();
  public static Hopper hopper = new Hopper();
  public static Turret turret = new Turret();

  public static PS4Gamepad driverPad = new PS4Gamepad(0);
  public static PS4Gamepad operatorPad = new PS4Gamepad(1);

  Button driverCircle = driverPad.getButtonCircle();
  Button driverTriangle = driverPad.getButtonTriangle();
  Button driverPadButton = driverPad.getButtonPad();
  Button driverR2 = driverPad.getR2();
  Button driverX = driverPad.getButtonX();
  Button driverSquare = driverPad.getButtonSquare();

  Button operatorCircle = operatorPad.getButtonCircle();
  Button operatorSquare = operatorPad.getButtonSquare();
  Button operatorPadButton = operatorPad.getButtonPad();
  Button operatorX = operatorPad.getButtonX();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    operatorSquare.toggleWhenPressed(new RunIntake()); //Standard Intake Command, Runs Hopper/Indexer as well
    operatorCircle.toggleWhenPressed(new RunShooter(5000)); //Turns Shooter on
    operatorX.whileHeld(new FeedCells());
    operatorPadButton.toggleWhenPressed(new TestLimelight());

    driverPadButton.whenPressed(new ReleaseClimber()); //Release Pneumatic on Climber
    driverR2.whileHeld(new Climb()); //Ratchet Climbers
    driverTriangle.toggleWhenPressed(new TurretFlip(180));
    
    // Uncomment these when you need 1 controller to both drive and shoot (delete the "//")

    driverSquare.toggleWhenPressed(new RunIntake()); //Standard Intake Command, Runs Hopper/Indexer as well
    driverCircle.toggleWhenPressed(new RunShooter(5000)); //Turns Shooter on
    driverX.whileHeld(new FeedCells());
  }

  //TODO: Add Auto lol
  public Command getAutonomousCommand() {
    // return m_autoCommand; 
    return null;
  }
}
