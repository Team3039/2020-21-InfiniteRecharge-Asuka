// /*----------------------------------------------------------------------------*/
// /* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
// /* Open Source Software - may be modified and shared by FRC teams. The code   */
// /* must be accompanied by the FIRST BSD license file in the root directory of */
// /* the project.                                                               */
// /*----------------------------------------------------------------------------*/

// package frc.robot.subsystems;

// import com.ctre.phoenix.motorcontrol.ControlMode;
// import com.ctre.phoenix.motorcontrol.NeutralMode;
// import com.ctre.phoenix.motorcontrol.can.TalonSRX;

// import edu.wpi.first.wpilibj.Solenoid;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.RobotMap;

// public class Climber extends SubsystemBase {
  
//   public TalonSRX climberA = new TalonSRX(RobotMap.climberA);
//   public TalonSRX climberB = new TalonSRX(RobotMap.climberB);

//   public Solenoid climbRelease = new Solenoid(RobotMap.climbRelease);

//   public Climber() {
//     climberA.setInverted(true);
//     climberB.setInverted(true);
//     climberB.follow(climberA);

//     climberA.setNeutralMode(NeutralMode.Brake);
//     climberB.setNeutralMode(NeutralMode.Brake);
//   }
  
//   public enum ClimberControlMode {
//     IDLE,
//     CLIMB
//   }

//   public final static Climber INSTANCE = new Climber();

//   public ClimberControlMode climberControlMode = ClimberControlMode.IDLE;

//   public static Climber getInstance() {
//     return INSTANCE;
//   }

//   public ClimberControlMode getControlMode() {
//     return climberControlMode;
//   }

//   public void setControlMode(ClimberControlMode controlMode) {
//     this.climberControlMode = controlMode;
//   }

//   public void actuateClimb() {
//       climbRelease.set(true);
//   }

//   public void setClimberSpeed(double power){
//     climberA.set(ControlMode.PercentOutput, Math.abs(power));;
//   }

//   public void stop() {
//     climberA.set(ControlMode.PercentOutput, 0);
//   }

//   @Override
//   public void periodic() {
//     synchronized (Climber.this) {
//       switch(getControlMode()) {
//         case CLIMB:
//           actuateClimb();          
//           break;
//         default:
//           setClimberSpeed(0);
//           actuateClimb();
//           break;
//       }
//     }
//   }
// }
