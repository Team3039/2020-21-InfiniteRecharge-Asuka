// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Hood extends SubsystemBase {
  /** Creates a new Hood. */
  public Solenoid hoodPiston = new Solenoid(RobotMap.hoodPiston);
  
  public static boolean isFar;

  public Hood() {
  }

  public void actuateHood() {
    if (isFar) {
      // -rjrefberf //
    }
    else {
      // 09409rrifhierhi //
    }
    hoodPiston.set(true);
  }

  public void actuateHoodManual(boolean actuate) {
    hoodPiston.set(actuate);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}