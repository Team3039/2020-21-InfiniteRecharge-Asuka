/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

/**
 * The Intake Class controls the front facing device used to collect "Power Cells"
 * from the floor and transfer them to the Indexer
 */
public class Intake extends SubsystemBase {

  public VictorSPX intake = new VictorSPX(RobotMap.intake);
  
  public Solenoid intakeTilt = new Solenoid(RobotMap.intakeTilt);

  public Intake() {
    intake.setNeutralMode(NeutralMode.Coast);
  }

  public void acuateIntake(boolean lowerIntake){
    if (lowerIntake){
      intakeTilt.set(true);
    }
    else {
      intakeTilt.set(false);
    }
  }

  public void setIntakeSpeed(double percentOutput) {
    intake.set(ControlMode.PercentOutput, percentOutput);
  }

  @Override
  public void periodic() {
    }
}
