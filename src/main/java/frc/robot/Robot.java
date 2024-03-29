/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoMode;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.auto.routines.AutoBouncePath;
import frc.robot.auto.routines.AutoDoNothing;
import frc.robot.auto.routines.AutoHyperPath;
import frc.robot.auto.routines.AutoRendezvousTrench10Ball;
import frc.robot.auto.routines.AutoSafe;
import frc.robot.auto.routines.AutoSlalomPath;
import frc.robot.auto.routines.AutoTrench8Ball;
import frc.robot.auto.routines.AutoTrenchSteal;
// import frc.robot.auto.routines.TestA;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Turret.TurretControlMode;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public Command m_autonomousCommand;
  private SendableChooser<Command> autonTaskChooser;
  public static double servoPose;


  RobotContainer m_robotContainer;
  private Drive drive = Drive.getInstance();

     //Vision Information
     public static double targetValid; //Whether the limelight has any valid targets (0 or 1)
     public static double targetX; //Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees)
     public static double targetY; //Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
     public static double targetArea; //Target Area (0% of image to 100% of image)

     public static double calculatedHoodPose;
     public static boolean Far;
     public static double RPM;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
    drive.resetOdometry(new Pose2d());
 
    autonTaskChooser = new SendableChooser<>();

    autonTaskChooser.setDefaultOption("Do Nothing", new AutoDoNothing());

    autonTaskChooser.addOption("Trench 8 Ball Auto", new AutoTrench8Ball());
    autonTaskChooser.addOption("Trench Steal 5 Ball Auto", new AutoTrenchSteal());

    autonTaskChooser.addOption("Rendezvous/Trench 10 Ball Auto", new AutoRendezvousTrench10Ball());
    autonTaskChooser.addOption("Safe 3 Ball Auto", new AutoSafe());

    autonTaskChooser.addOption("Bounce Path Auto", new AutoBouncePath());
    autonTaskChooser.addOption("Slalom Path Auto", new AutoSlalomPath());
    autonTaskChooser.addOption("Hyper Path Auto", new AutoHyperPath());

    SmartDashboard.putData("Autonomous", autonTaskChooser);

    // UsbCamera usbCamera = CameraServer.startAutomaticCapture();
    // usbCamera.setVideoMode(VideoMode.PixelFormat.kYUYV, 320, 180, 60);
    servoPose = 0.5;

    SmartDashboard.putBoolean("isFar", RobotContainer.hood.isFar);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods. This must be called from the robot's periodic
    // block in order for anything in 
    //  Command-based framework to work.
    //Gather Vision Info
    targetValid = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    targetX = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    targetY = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    targetArea = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
    CommandScheduler.getInstance().run();

    // if (targetArea <= Constants.LIMELIGHT_IS_FAR_AREA) {
    //   Far = true;
    //   RPM = 6500;
    // }
    // else {
    //   Far = false;
    //   RPM = 5000;
    // }

    // if (targetArea == 0) {
    //   calculatedHoodPose = 1;
    // }
    // else {
    //   calculatedHoodPose = RobotContainer.shooter.calculateDesiredHoodPosition(targetArea);
    // }

    if (targetArea >= Constants.HOOD_THRESHOLD) {
      RobotContainer.hood.isFar = true;
    }
    else {
      RobotContainer.hood.isFar = false;
    }
    
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
    drive.resetOdometry(new Pose2d());
    // RobotContainer.turret.setControlMode(TurretControlMode.DRIVER);
  }

  @Override
  public void disabledPeriodic() {
    // SmartDashboard.putString("Selected Auto: ", autonTaskChooser.getSelected().toString());
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    drive.setControlMode(Drive.DriveControlMode.PATH_FOLLOWING);
    drive.resetOdometry(new Pose2d());

    m_autonomousCommand = new AutoBouncePath();


    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {

  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    drive.setControlMode(Drive.DriveControlMode.JOYSTICK);
    RobotContainer.turret.setControlMode(TurretControlMode.DRIVER);
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  public static double getTime(){
    return Timer.getFPGATimestamp();
  }
}
