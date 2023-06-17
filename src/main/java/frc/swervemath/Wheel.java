package frc.swervemath;

import frc.swervemath.Hardware.WheelHardware;
import frc.swervemath.math.Vector2;

//Probably should be refactored as an interface to reduce overhead
public class Wheel { 
    public final WheelHardware hardware;
    public final Vector2 pos;

    public void setAngleAndSpeed(float angle, float speed){
        hardware.setSetpoint(angle);
        hardware.setSpeed(speed);
    }
    public Wheel(WheelHardware hardware, Vector2 pos){
        this.hardware = hardware;
        this.pos = pos;
    }
}
