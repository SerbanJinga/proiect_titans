package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.detectors.roverrukus.SilverDetector;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Hardware {
    public DcMotor backLeft = null;
    public DcMotor backRight = null;
    public DcMotor frontLeft = null;
    public DcMotor frontRight = null;
    public ModernRoboticsI2cRangeSensor rangeSensor = null;
    //    public DcMotor motorCremaliera = null;




    HardwareMap Map = null;

    public Hardware(){}

    public void init(HardwareMap ahwMap){
        Map = ahwMap;

        backLeft = Map.get(DcMotor.class, "bL");
        backRight = Map.get(DcMotor.class, "bR");
        frontLeft = Map.get(DcMotor.class, "fL");
        frontRight = Map.get(DcMotor.class, "fR");
        rangeSensor = Map.get(ModernRoboticsI2cRangeSensor.class, "rS");
        //motorCremaliera = Map.get(DcMotor.class, "mC");

        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(0);

        //motorCremaliera.setPower(0);

        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
//        motorCremaliera.setDirection(DcMotor.Direction.FORWARD);

        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motorCremaliera.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //motorCremaliera.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



    }
}

