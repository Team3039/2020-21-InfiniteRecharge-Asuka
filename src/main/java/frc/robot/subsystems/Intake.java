/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

/**
 * The Intake Class controls the front facing device used to collect "Power Cells"
 * from the floor and transfer them to the Indexer
 */
public class Intake extends SubsystemBase {

  public VictorSPX intake = new VictorSPX(RobotMap.intake);
  
  public Solenoid intakeTilt = new Solenoid(PneumaticsModuleType.CTREPCM, RobotMap.intakeTilt);

  public Intake() {
    intake.setNeutralMode(NeutralMode.Coast);

    intake.setStatusFramePeriod(StatusFrame.Status_1_General, 255);
  }

  public void acuateIntake(boolean lowerIntake){
    intakeTilt.set(lowerIntake);
  }

  public void setIntakeSpeed(double percentOutput) {
    intake.set(ControlMode.PercentOutput, percentOutput);
  }

  @Override
  public void periodic() {
    }
}
