/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotMap;

/**
 * The Intake delivers "Power Cells" to this subsystem to be transfered to the
 * Shooter. This subsystem also "indexes" said "Power Cells" for rapid shooting
 */
public class Hopper extends SubsystemBase {

  public VictorSPX agitator = new VictorSPX(RobotMap.HOPPER_AGITATOR);
  public TalonSRX beltFeed = new TalonSRX(RobotMap.INDEXER_BELTS);
  public TalonSRX wheelFeed = new TalonSRX(RobotMap.INDEXER_WHEEL);

  public DigitalInput topBeam = new DigitalInput(RobotMap.topBeam);
  public DigitalInput lowBeam = new DigitalInput(RobotMap.lowBeam);

  public boolean getTopBeam() {
    return !topBeam.get();
  }

  public boolean getLowBeam() {
    return !lowBeam.get();
  }

  
  //Main Control Method AKA what move the balls according to the sensor input
  public void index() {
    if (getTopBeam() && getLowBeam()) {
      stopFeed();
    }
    else if (getTopBeam() && !getLowBeam()) {
      runBeltFeed();
      agitateHopper();
      stopWheelFeed(); 
    }
    else if (!getTopBeam() && getLowBeam()) {
      feed();
    }
    else if (!getTopBeam() && !getLowBeam()) {
      feed();
    }
  }
  

  //Shuffles the Balls around
  public void agitateHopper() {
    agitator.set(ControlMode.PercentOutput, Constants.AGITATE_SPEED); 
  }

  //pushes balls into hopper
  public void runWheelFeed() {
    wheelFeed.set(ControlMode.PercentOutput, Constants.FEED_WHEEL_SPEED);
  }

  //pushes balls up hopper
  public void runBeltFeed() {
    beltFeed.set(ControlMode.PercentOutput, Constants.BELT_WHEEL_SPEED);
  }

  public void stopAgitator() {
    agitator.set(ControlMode.PercentOutput, 0); 
  }

  public void stopWheelFeed() {
    wheelFeed.set(ControlMode.PercentOutput, 0);
  }

  public void stopBeltFeed() {
    beltFeed.set(ControlMode.PercentOutput, 0); 
}

  //Constant Ball Feed w/o Indexing
  public void feed() {
    runWheelFeed();
    runBeltFeed();
    agitateHopper();
  }

  public void stopFeed() {
    stopWheelFeed();
    stopBeltFeed();
    stopAgitator();
  }

}
  
  