package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.commands.ActuateClimb;
import frc.robot.commands.ActuateHood;
import frc.robot.commands.ActuateHoodManual;
import frc.robot.commands.ActuateIntake;
import frc.robot.commands.SetClimbArmSpeed;
import frc.robot.commands.SetClimberASpeed;
import frc.robot.commands.SetClimberBSpeed;
import frc.robot.commands.SetHopperIdleMode;
import frc.robot.commands.SetHopperIntakingMode;
import frc.robot.commands.SetHopperUnjamMode;
import frc.robot.commands.SetIntakeSpeed;
import frc.robot.commands.SetShooterSpeed;
import frc.robot.commands.SetTurretClimbMode;
import frc.robot.commands.SetTurretDriverMode;
import frc.robot.commands.SetTurretJoystickMode;
import frc.robot.commands.SetTurretTrackMode;
import frc.robot.commands.sequences.FeedCells;
import frc.robot.commands.sequences.IndexCells;
import frc.robot.commands.sequences.IntakeCells;
import frc.robot.commands.sequences.ResetHopper;
import frc.robot.commands.sequences.ResetShooter;
import frc.robot.commands.sequences.ShootFarShot;
import frc.robot.commands.sequences.ShootMidShot;
import frc.robot.commands.sequences.ShootNearShot;
import frc.robot.controllers.PS4Gamepad;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;

public class RobotContainer {

  public final static Drive drive = Drive.getInstance();
  public final static Intake intake = new Intake();
  public final static Turret turret = new Turret();
  public final static Hopper hopper = new Hopper();
  public final static Shooter shooter = new Shooter();
  public final static Climber climber = new Climber();
  public final static Hood hood = new Hood();

  public static PS4Gamepad driverPad = new PS4Gamepad(RobotMap.DRIVER_JOYSTICK_1_USB_ID);
  public static PS4Gamepad operatorPad = new PS4Gamepad(RobotMap.OPERATOR_JOYSTICK_1_USB_ID);

  public static Timer timer = new Timer();

  Button driverTriangle = driverPad.getButtonTriangle();
  Button driverSquare = driverPad.getButtonSquare();
  Button driverCircle = driverPad.getButtonCircle();
  Button driverX = driverPad.getButtonX();
  Button driverShare = driverPad.getShareButton();
  Button driverOptions = driverPad.getOptionsButton();
  Button driverPadButton = driverPad.getButtonPad();
  Button driverL1 = driverPad.getL1();
  Button driverL2 = driverPad.getL2();
  Button driverL3 = driverPad.getL3();
  Button driverR1 = driverPad.getR1();
  Button driverR2 = driverPad.getR2();
  Button driverR3 = driverPad.getR3();
  Button startButton = driverPad.getStartButton();
  Button driverDPadUp = driverPad.getDPadUp();
  Button driverDPadDown = driverPad.getDPadDown();
  Button driverDPadLeft = driverPad.getDPadLeft();
  Button driverDPadRight = driverPad.getDPadRight();

  Button operatorTriangle = operatorPad.getButtonTriangle();
  Button operatorSquare = operatorPad.getButtonSquare();
  Button operatorCircle = operatorPad.getButtonCircle();
  Button operatorX = operatorPad.getButtonX();
  Button operatorShare = operatorPad.getShareButton();
  Button operatorOptions = operatorPad.getOptionsButton();
  Button operatorPadButton = operatorPad.getButtonPad();
  Button operatorL1 = operatorPad.getL1();
  Button operatorL2 = operatorPad.getL2();
  Button operatorR1 = operatorPad.getR1();
  Button operatorR2 = operatorPad.getR2();
  Button operatorDPadUp = operatorPad.getDPadUp();
  Button operatorDPadDown = operatorPad.getDPadDown();
  Button operatorDPadLeft = operatorPad.getDPadLeft();
  Button operatorDPadRight = operatorPad.getDPadRight();


  public RobotContainer() {
    configureButtonBindings();
  }

  // Put Button Bindings Here
  private void configureButtonBindings() {

    driverPadButton.whenPressed(new SetTurretJoystickMode());

    // driverR1.whileHeld(new SetClimbArmSpeed(.9));

    // driverL2.whileHeld(new SetClimberASpeed(.2));
    // driverR2.whileHeld(new SetClimberBSpeed(.2));

    driverR1.whileHeld(
      () -> {
        Drive.getInstance().setQuickTurn(true);;
      }
    );

    driverR1.whenReleased(
      () -> {
        Drive.getInstance().setQuickTurn(false);
      }
    );

    operatorL1.whileHeld(new SetIntakeSpeed(.99));
    operatorL1.whileHeld(new SetHopperIntakingMode());
    operatorL1.whenReleased(new ResetHopper());
    operatorX.whenPressed(new ShootFarShot());
    operatorR1.whileHeld(new FeedCells());
    operatorR1.whenReleased(new ResetHopper());
    operatorR1.whenReleased(new ResetShooter());
    // operatorOptions.whileHeld(new SetTurretTrackMode());
    operatorDPadUp.whenPressed(new ActuateIntake(true));
    operatorDPadDown.whenPressed(new ActuateIntake(false));
    operatorL2.whileHeld(new SetIntakeSpeed(-.99));
    operatorL2.whileHeld(new SetHopperUnjamMode());
    operatorL2.whenReleased(new SetIntakeSpeed(0));
    operatorL2.whenReleased(new ResetHopper());
    // driverPadButton.whileHeld(new SetClimbArmSpeed(0.5));
    // driverShare.whileHeld(new SetClimbArmSpeed(-0.3));
    operatorOptions.whenPressed(new SetTurretTrackMode());
    operatorShare.whenPressed(new SetTurretDriverMode());
    operatorPadButton.whenPressed(new SetTurretClimbMode());
    // driverTriangle.whenPressed(new ActuateHoodManual(true));
    // driverTriangle.whenReleased(new ActuateHoodManual(false));
    operatorTriangle.toggleWhenPressed(new ActuateClimb());
    // operatorDPadLeft.whileHeld(new SetClimbArmSpeed(-.2));
    // operatorDPadRight.whileHeld(new SetClimbArmSpeed(.2));
  }

  //Get Controller Objects
  public static PS4Gamepad getDriver() {
    return driverPad;
  }

  public static PS4Gamepad getOperator() {
    return operatorPad;
  }
}