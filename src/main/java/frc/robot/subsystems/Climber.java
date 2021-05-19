// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {

  public Solenoid release = new Solenoid(0);
  public TalonSRX climberLeft = new TalonSRX(1);
  public TalonSRX climberRight = new TalonSRX(2);

  public void releaseClimber(){
    release.set(false);
  }
  public void runWinches(){

  }
  public void stopWinches(){

  }
  //Change the Solenoid channel
  /** Creates a new Climb. */
  
  public Climber() {
    release.set(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
