package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.swervemath.math.Vector2;

public class ArticulatedArm extends SubsystemBase {
    public final TalonFX firstStageMotor = new TalonFX(Constants.ArmConstants.firstStageMotorCANid);
    public final CANSparkMax secondStageMotor1 = new CANSparkMax(Constants.ArmConstants.secondStageMotorCANid1, MotorType.kBrushless);
    public final CANSparkMax secondStageMotor2 = new CANSparkMax(Constants.ArmConstants.secondStageMotorCANid2, MotorType.kBrushless);
    public final CANSparkMax intakeWristMotor = new CANSparkMax(Constants.ArmConstants.intakeWristMotorCANid, MotorType.kBrushless);

    //in in-lbs
    public double getTorqueFirstStage(double firstStageRot, double secondStageRot, double intakeRot){
        secondStageRot += 180;

        Vector2 totalCenterOfMass = new Vector2(0, 0);
        float totalMass = 0;

        totalCenterOfMass = Vector2.add(totalCenterOfMass, new Vector2((float)Math.sin(firstStageRot * 180 / Math.PI) * Constants.ArmConstants.firstStageCenterOfMass, 
                                                                       (float)Math.cos(firstStageRot * 180 / Math.PI) * Constants.ArmConstants.firstStageCenterOfMass));
        totalMass += Constants.ArmConstants.firstStageMass;

        Vector2 pivotPoint = new Vector2((float)Math.sin(firstStageRot * 180 / Math.PI) * Constants.ArmConstants.firstStageNextPivot, 
                                         (float)Math.cos(firstStageRot * 180 / Math.PI) * Constants.ArmConstants.firstStageNextPivot);
        Vector2 transformedCenterOfMass = Vector2.add(pivotPoint, new Vector2((float)Math.sin(secondStageRot * 180 / Math.PI) * Constants.ArmConstants.secondStageCenterOfMass, 
                                                                              (float)Math.cos(secondStageRot * 180 / Math.PI) * Constants.ArmConstants.secondStageCenterOfMass));
        totalCenterOfMass = Vector2.add(totalCenterOfMass, transformedCenterOfMass);
        totalMass += Constants.ArmConstants.secondStageMass;

        pivotPoint = Vector2.add(pivotPoint, new Vector2((float)Math.sin(secondStageRot * 180 / Math.PI) * Constants.ArmConstants.secondStageNextPivot, 
                                                         (float)Math.cos(secondStageRot * 180 / Math.PI) * Constants.ArmConstants.secondStageNextPivot));

        transformedCenterOfMass = Vector2.add(pivotPoint, new Vector2((float)Math.sin(intakeRot * 180 / Math.PI) * Constants.ArmConstants.intakeCenterOfMass, 
                                                                      (float)Math.cos(intakeRot * 180 / Math.PI) * Constants.ArmConstants.intakeCenterOfMass));
        totalCenterOfMass = Vector2.add(totalCenterOfMass, transformedCenterOfMass);
        totalMass += Constants.ArmConstants.intakeMass;

        Vector2 centerOfMass = Vector2.divide(totalCenterOfMass, totalMass);
        return totalMass * Math.sin(Math.atan2(centerOfMass.y, centerOfMass.x)) * centerOfMass.magnitude();
    }

    public double getTorqueSecondStage(double secondStageRot, double intakeRot){
        secondStageRot += 180;

        Vector2 totalCenterOfMass = new Vector2(0, 0);
        float totalMass = 0;

        Vector2 transformedCenterOfMass = new Vector2((float)Math.sin(secondStageRot * 180 / Math.PI) * Constants.ArmConstants.secondStageCenterOfMass, 
                                                      (float)Math.cos(secondStageRot * 180 / Math.PI) * Constants.ArmConstants.secondStageCenterOfMass);
        totalCenterOfMass = Vector2.add(totalCenterOfMass, transformedCenterOfMass);
        totalMass += Constants.ArmConstants.secondStageMass;

        Vector2 pivotPoint = new Vector2((float)Math.sin(secondStageRot * 180 / Math.PI) * Constants.ArmConstants.secondStageNextPivot, 
                                         (float)Math.cos(secondStageRot * 180 / Math.PI) * Constants.ArmConstants.secondStageNextPivot);

        transformedCenterOfMass = Vector2.add(pivotPoint, new Vector2((float)Math.sin(intakeRot * 180 / Math.PI) * Constants.ArmConstants.intakeCenterOfMass, 
                                                                      (float)Math.cos(intakeRot * 180 / Math.PI) * Constants.ArmConstants.intakeCenterOfMass));
        totalCenterOfMass = Vector2.add(totalCenterOfMass, transformedCenterOfMass);
        totalMass += Constants.ArmConstants.intakeMass;

        Vector2 centerOfMass = Vector2.divide(totalCenterOfMass, totalMass);
        return totalMass * Math.sin(Math.atan2(centerOfMass.y, centerOfMass.x)) * centerOfMass.magnitude();
    }
    
    public double getIntakeTorque(double intakeRot){
        Vector2 totalCenterOfMass = new Vector2(0, 0);
        float totalMass = 0;

        Vector2 transformedCenterOfMass = new Vector2((float)Math.sin(intakeRot * 180 / Math.PI) * Constants.ArmConstants.intakeCenterOfMass, 
                                                      (float)Math.cos(intakeRot * 180 / Math.PI) * Constants.ArmConstants.intakeCenterOfMass);
        totalCenterOfMass = Vector2.add(totalCenterOfMass, transformedCenterOfMass);
        totalMass += Constants.ArmConstants.intakeMass;

        Vector2 centerOfMass = Vector2.divide(totalCenterOfMass, totalMass);
        return totalMass * Math.sin(Math.atan2(centerOfMass.y, centerOfMass.x)) * centerOfMass.magnitude();
    }
}
