package frc.robot;

public class RobotMap {
//HID
  public final static int DRIVER_JOYSTICK_1_USB_ID = 0;
  public final static int OPERATOR_JOYSTICK_1_USB_ID = 1;

//CAN
  //Drive
  public static int leftFrontDrive = 4;
  public static int rightFrontDrive = 12; 
  public static int leftRearDrive = 5;
  public static int rightRearDrive = 13; 
  
  //intake/shooter
  public static int shooterA = 6;
  public static int shooterB = 7;
  public final static int topWheel = 8;

  public static int turret = 2;
  public static int intake = 0;

  //Indexer
  public final static int indexerBelts = 9;
  public final static int kickerWheelandAgitator = 10;

  //Climber
  public final static int climberA = 1;
  public final static int climberB = 11;


//SOLENOID
//0 - 3
  public final static int intakeTilt = 3;
  public final static int climbRelease = 1;

//PWM

//DIO	
  public final static int turretSwitch = 0;
  public final static int topBeam = 1;
  public final static int lowBeam = 2;
  public final static int hoodSensor = 3;

//AIO
}