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

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.RobotMap;

/**
 * The Intake delivers "Power Cells" to this subsystem to be transfered to the
 * Shooter. This subsystem also "indexes" said "Power Cells" for rapid shooting
 */
public class Hopper extends SubsystemBase {

  public TalonSRX kickerWheel = new TalonSRX(RobotMap.kickerWheelAndAgitator);
  public TalonSRX backFeederBelt = new TalonSRX(RobotMap.backFeederBelt);

  public DigitalInput topBeam = new DigitalInput(RobotMap.topBeam);
  public DigitalInput lowBeam = new DigitalInput(RobotMap.lowBeam);

  public Hopper() {
    backFeederBelt.setInverted(false);

    backFeederBelt.setNeutralMode(NeutralMode.Brake);
    kickerWheel.setNeutralMode(NeutralMode.Brake);
  }

  public enum HopperControlMode {
    IDLE,
    INTAKING,
    FEEDING,
    UNJAMMING
  }

  public HopperControlMode hopperControlMode = HopperControlMode.IDLE;

  public synchronized HopperControlMode getControlMode() {
    return hopperControlMode;
}

  public synchronized void setControlMode(HopperControlMode controlMode) {
    this.hopperControlMode = controlMode;
  }

  public boolean getTopBeam() {
    return !topBeam.get();
  }

  public boolean getLowBeam() {
    return !lowBeam.get();
  }

  public void setBackBeltSpeed(double percentOutput) {
    backFeederBelt.set(ControlMode.PercentOutput, percentOutput);
  }

  public void setKickerSpeed(double percentOutput) {
    kickerWheel.set(ControlMode.PercentOutput, percentOutput);
  }

  public void setHopperSpeed(double kickerSpeed, double backBeltSpeed, double frontBeltWheelSpeed) {
    kickerWheel.set(ControlMode.PercentOutput, kickerSpeed);
    backFeederBelt.set(ControlMode.PercentOutput, backBeltSpeed);
  }

  public void stopSystems() {
    kickerWheel.set(ControlMode.PercentOutput, 0);
    backFeederBelt.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Top Beam", getTopBeam());
    SmartDashboard.putBoolean("Low Beam", getLowBeam());
    synchronized (Hopper.this) {
      switch (getControlMode()) {
        case IDLE:
          stopSystems();
          break;
        case INTAKING:
          if (!getTopBeam() && !getLowBeam()) {
            setHopperSpeed(.55, .6, .6);
          }
          else if (getTopBeam() && !getLowBeam()) {
            setKickerSpeed(.55);
            setBackBeltSpeed(0);
          }
          else if (getTopBeam() && getLowBeam()){
            setHopperSpeed(0, 0, 0);
          }
          else if (!getTopBeam() && getLowBeam()) {
            setHopperSpeed(.55, .6, .6);
          }
          break;
        case FEEDING:
            setHopperSpeed(.7, .4, .35);
          break;
        case UNJAMMING:
          setHopperSpeed(-.4, -.75, -.75);
          break;
      }
    }
  }
}
