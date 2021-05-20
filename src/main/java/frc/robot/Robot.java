package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.geometry.Pose2d;
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
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Turret.TurretControlMode;

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

  @Override
  public void robotInit() {

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

    UsbCamera usbCamera = CameraServer.getInstance().startAutomaticCapture();
    usbCamera.setVideoMode(VideoMode.PixelFormat.kYUYV, 320, 180, 60);
    servoPose = 0.5;
  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("SERVO POSE", RobotContainer.shooter.getServoPose());
    SmartDashboard.putNumber("TURRET POSE", RobotContainer.turret.getCurrentPosition());

    //Gather Vision Info
    targetValid = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    targetX = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    targetY = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    targetArea = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
    CommandScheduler.getInstance().run();

    if (targetArea <= Constants.LIMELIGHT_IS_FAR_AREA) {
      Far = true;
      RPM = 6500;
    }
    else {
      Far = false;
      RPM = 5000;
    }

    if (targetArea == 0) {
      calculatedHoodPose = 1;
    }
    else {
      calculatedHoodPose = RobotContainer.shooter.calculateDesiredHoodPosition(targetArea);
    }
    
    SmartDashboard.putNumber("Calculated Output", calculatedHoodPose);
  }

  @Override
  public void disabledInit() {
    drive.resetOdometry(new Pose2d());
    RobotContainer.turret.setControlMode(TurretControlMode.DRIVER);
  }

  @Override
  public void disabledPeriodic() {
    SmartDashboard.putString("Selected Auto: ", autonTaskChooser.getSelected().toString());
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    drive.setControlMode(Drive.DriveControlMode.PATH_FOLLOWING);
    drive.resetOdometry(new Pose2d());

    m_autonomousCommand = new AutoBouncePath();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    drive.setControlMode(Drive.DriveControlMode.JOYSTICK);
    RobotContainer.turret.setControlMode(TurretControlMode.DRIVER);
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {

  }

  public static double getTime(){
    return Timer.getFPGATimestamp();
  }
}
