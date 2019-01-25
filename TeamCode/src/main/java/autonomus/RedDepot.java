package autonomus;


import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import hardware.Hardware;

@Autonomous(name = "RedDepot", group = "Pushbot")
public class RedDepot extends LinearOpMode {

public Hardware robot = new Hardware();

private GoldAlignDetector detector;

    @Override
    public void runOpMode() throws InterruptedException {


        robot.init(hardwareMap);

        waitForStart();

        initDetector();
        detectGold();
        //TODO 1000 ticks --> 22 centimetri
        //TODO 1000 ticks --> 55.8 inchi

    }

    //init all


    public void initDetector(){
        detector = new GoldAlignDetector();
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        detector.useDefaults();

        detector.alignSize = 100;
        detector.alignPosOffset = 100;
        detector.downscale = 0.4;

        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA;
        detector.maxAreaScorer.weight = 0.005;

        detector.ratioScorer.weight = 5;
        detector.ratioScorer.perfectRatio = 1.0;

        detector.enable();
    }

    //TODO AU MERS FUNCTIILE ASTEA
    //TODO SCHIMBA NR DE TICKS IN MAI MICI PUTIN

    public void detectGold(){
        sleep(2000);
        boolean detected = detector.getAligned();
        sleep(1000);
        detector.disable();

        if(detected){
            driveForward(2000, 0.4);
            sleep(2000);
            driveBackwards(4000, 0.4);
            sleep(2000);
            mecanumRight(4000, 0.4);
        }else{
            mecanumRight(2600, 0.4);
            detector.enable();
            sleep(2000);
            if(detector.getAligned()){
                driveForward(4000, 0.4);
            }else{
                mecanumRight(2600, 0.4);
                if(detector.getAligned()){
                    driveForward(4000, 0.4);

                }
            }
            }
    }


    //drive mecanum


    public void driveForward(int ticks, double speed){
        if(!opModeIsActive()) return;

        robot.backLeft.setDirection(DcMotor.Direction.FORWARD);
        robot.backRight.setDirection(DcMotor.Direction.REVERSE);
        robot.frontLeft.setDirection(DcMotor.Direction.FORWARD);
        robot.frontRight.setDirection(DcMotor.Direction.REVERSE);

        int newLeftTarget1 = 0;
        int newLeftTarget2 = 0;
        int newRightTarget1 = 0;
        int newRightTarget2 = 0;

        if(opModeIsActive()){

            newLeftTarget1 = robot.frontLeft.getCurrentPosition() + ticks;
            newRightTarget1 = robot.frontRight.getCurrentPosition() + ticks;
            newLeftTarget2 = robot.backLeft.getCurrentPosition() + ticks;
            newRightTarget2 = robot.backRight.getCurrentPosition() + ticks;

            robot.frontLeft.setTargetPosition(newLeftTarget1);
            robot.frontRight.setTargetPosition(newRightTarget1);
            robot.backLeft.setTargetPosition(newLeftTarget2);
            robot.backRight.setTargetPosition(newRightTarget2);

            robot.frontLeft.setPower(speed);
            robot.frontRight.setPower(speed);
            robot.backLeft.setPower(speed);
            robot.backRight.setPower(speed);

            while(opModeIsActive()
                    &&(robot.frontLeft.isBusy()
                    ||robot.frontRight.isBusy()
                    ||robot.backLeft.isBusy()
                    ||robot.backLeft.isBusy())
                    &&(robot.frontLeft.getCurrentPosition() <= newLeftTarget1
                    ||robot.frontRight.getCurrentPosition() <= newRightTarget1
                    || robot.backLeft.getCurrentPosition() <= newLeftTarget2
                    || robot.backRight.getCurrentPosition() <= newRightTarget1)){
            }

            robot.frontLeft.setPower(0);
            robot.frontRight.setPower(0);
            robot.backLeft.setPower(0);
            robot.backRight.setPower(0);

            robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        }
    }

    public void driveBackwards(int ticks, double speed){
        if(!opModeIsActive()) return;

        robot.backLeft.setDirection(DcMotor.Direction.REVERSE);
        robot.backRight.setDirection(DcMotor.Direction.FORWARD);
        robot.frontLeft.setDirection(DcMotor.Direction.REVERSE);
        robot.frontRight.setDirection(DcMotor.Direction.FORWARD);

        int newLeftTarget1 = 0;
        int newLeftTarget2 = 0;
        int newRightTarget1 = 0;
        int newRigthTarget2 = 0;

        if(opModeIsActive()){
            newLeftTarget1 = robot.frontLeft.getCurrentPosition() + ticks;
            newRightTarget1 = robot.frontRight.getCurrentPosition() + ticks;
            newLeftTarget2 = robot.backLeft.getCurrentPosition() + ticks;
            newRightTarget1 = robot.backRight.getCurrentPosition() + ticks;

            robot.frontLeft.setTargetPosition(newLeftTarget1);
            robot.frontRight.setTargetPosition(newRightTarget1);
            robot.backLeft.setTargetPosition(newLeftTarget2);
            robot.backRight.setTargetPosition(newRigthTarget2);

            robot.frontLeft.setPower(speed);
            robot.frontRight.setPower(speed);
            robot.backLeft.setPower(speed);
            robot.backRight.setPower(speed);

            while(opModeIsActive()
                    &&(robot.frontLeft.isBusy()
                    ||robot.frontRight.isBusy()
                    ||robot.backLeft.isBusy()
                    ||robot.backRight.isBusy())
                    &&(robot.frontLeft.getCurrentPosition() <= newLeftTarget1
                    ||robot.frontRight.getCurrentPosition() <= newRightTarget1
                    ||robot.backLeft.getCurrentPosition() <= newLeftTarget2
                    ||robot.backRight.getCurrentPosition() <= newRigthTarget2)){
            }

            robot.frontLeft.setPower(0);
            robot.frontRight.setPower(0);
            robot.backLeft.setPower(0);
            robot.backRight.setPower(0);


            robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


            robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }



    }


    //mecanum left or right


    public void mecanumLeft(int ticks, double speed){
        if(!opModeIsActive()) return;

        robot.backLeft.setDirection(DcMotor.Direction.FORWARD);
        robot.backRight.setDirection(DcMotor.Direction.FORWARD);
        robot.frontLeft.setDirection(DcMotor.Direction.REVERSE);
        robot.frontRight.setDirection(DcMotor.Direction.REVERSE);

        int newLeftTarget1 = 0;
        int newLeftTarget2 = 0;
        int newRightTarget1 = 0;
        int newRightTarget2 = 0;

        if(opModeIsActive()){
            newLeftTarget1 = robot.frontLeft.getCurrentPosition() + ticks;
            newRightTarget1 = robot.frontRight.getCurrentPosition() + ticks;
            newLeftTarget2 = robot.backLeft.getCurrentPosition() + ticks;
            newRightTarget2 = robot.backRight.getCurrentPosition() + ticks;

            robot.frontLeft.setTargetPosition(newLeftTarget1);
            robot.frontRight.setTargetPosition(newRightTarget1);
            robot.backLeft.setTargetPosition(newLeftTarget2);
            robot.backLeft.setTargetPosition(newRightTarget2);

            robot.frontLeft.setPower(speed);
            robot.frontRight.setPower(speed);
            robot.backLeft.setPower(speed);
            robot.backRight.setPower(speed);

            while(opModeIsActive()
                    && (robot.frontLeft.isBusy()
                    ||robot.frontRight.isBusy()
                    ||robot.backLeft.isBusy()
                    ||robot.backRight.isBusy())
                    &&(robot.frontLeft.getCurrentPosition() <= newLeftTarget1
                    ||robot.frontRight.getCurrentPosition() <= newRightTarget1
                    ||robot.backLeft.getCurrentPosition() <= newLeftTarget2
                    ||robot.backRight.getCurrentPosition() <= newRightTarget2)){


            }
            robot.frontLeft.setPower(0);
            robot.frontRight.setPower(0);
            robot.backLeft.setPower(0);
            robot.backRight.setPower(0);

            robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        }
    }

    public void mecanumRight(int ticks, double speed){
        if(!opModeIsActive()) return;
        robot.backLeft.setDirection(DcMotor.Direction.REVERSE);
        robot.backRight.setDirection(DcMotor.Direction.REVERSE);
        robot.frontLeft.setDirection(DcMotor.Direction.FORWARD);
        robot.frontRight.setDirection(DcMotor.Direction.FORWARD);

        int newLeftTarget1 = 0;
        int newLeftTarget2 = 0;
        int newRightTarget1 = 0;
        int newRightTarget2 = 0;

        if(opModeIsActive()){

            newLeftTarget1 = robot.frontLeft.getCurrentPosition() + ticks;
            newRightTarget1 = robot.frontRight.getCurrentPosition() + ticks;
            newLeftTarget2 = robot.backLeft.getCurrentPosition() + ticks;
            newRightTarget2 = robot.backRight.getCurrentPosition() + ticks;

            robot.frontLeft.setTargetPosition(newLeftTarget1);
            robot.frontRight.setTargetPosition(newRightTarget1);
            robot.backLeft.setTargetPosition(newLeftTarget2);
            robot.backRight.setTargetPosition(newRightTarget2);

            robot.frontLeft.setPower(speed);
            robot.frontRight.setPower(speed);
            robot.backLeft.setPower(speed);
            robot.backRight.setPower(speed);

            while(opModeIsActive()
                    &&(robot.frontLeft.isBusy()
                    ||robot.frontRight.isBusy()
                    ||robot.backLeft.isBusy()
                    ||robot.backRight.isBusy())
                    &&(robot.frontLeft.getCurrentPosition() <= newLeftTarget1
                    ||robot.frontRight.getCurrentPosition() <= newRightTarget1
                    ||robot.backLeft.getCurrentPosition() <= newLeftTarget2
                    ||robot.backLeft.getCurrentPosition() <= newRightTarget2)){
            }

            robot.frontLeft.setPower(0);
            robot.frontRight.setPower(0);
            robot.backLeft.setPower(0);
            robot.backRight.setPower(0);

            robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);




        }
    }


    //rotate mecanum


    public void rotateRight(int ticks, double speed){


        if (!opModeIsActive()) return;

        robot.backLeft.setDirection(DcMotor.Direction.REVERSE);
        robot.backRight.setDirection(DcMotor.Direction.REVERSE);
        robot.frontLeft.setDirection(DcMotor.Direction.REVERSE);
        robot.frontRight.setDirection(DcMotor.Direction.REVERSE);

        int newLeftTarget1 = 0;
        int newLeftTarget2 = 0;
        int newRightTarget1 = 0;
        int newRightTarget2 = 0;

        if (opModeIsActive()) {

            newLeftTarget1 = robot.frontLeft.getCurrentPosition() + ticks;
            newRightTarget1 = robot.frontRight.getCurrentPosition() + ticks;
            newLeftTarget2 = robot.backLeft.getCurrentPosition() + ticks;
            newRightTarget2 = robot.backRight.getCurrentPosition() + ticks;

            robot.frontLeft.setTargetPosition(newLeftTarget1);
            robot.frontRight.setTargetPosition(newRightTarget1);
            robot.backLeft.setTargetPosition(newLeftTarget2);
            robot.backRight.setTargetPosition(newRightTarget2);

            robot.frontLeft.setPower(speed);
            robot.frontRight.setPower(speed);
            robot.backLeft.setPower(speed);
            robot.backRight.setPower(speed);

            while (opModeIsActive()
                    && (robot.frontLeft.isBusy()
                    || robot.frontRight.isBusy()
                    || robot.backLeft.isBusy()
                    || robot.backRight.isBusy())
                    && (robot.frontLeft.getCurrentPosition() <= newLeftTarget1
                    || robot.frontRight.getCurrentPosition() <= newRightTarget1)
                    || robot.backLeft.getCurrentPosition() <= newLeftTarget2
                    || robot.backRight.getCurrentPosition() <= newRightTarget2) {

            }

            robot.frontLeft.setPower(0);
            robot.frontRight.setPower(0);
            robot.backLeft.setPower(0);
            robot.backRight.setPower(0);

            robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }
    }

    public void rotateLeft(int ticks, double speed){
        if (!opModeIsActive()) return;

        robot.backLeft.setDirection(DcMotor.Direction.FORWARD);
        robot.backRight.setDirection(DcMotor.Direction.FORWARD);
        robot.frontLeft.setDirection(DcMotor.Direction.FORWARD);
        robot.frontRight.setDirection(DcMotor.Direction.FORWARD);

        int newLeftTarget1 = 0;
        int newLeftTarget2 = 0;
        int newRightTarget1 = 0;
        int newRightTarget2 = 0;

        if (opModeIsActive()) {

            newLeftTarget1 = robot.frontLeft.getCurrentPosition() + ticks;
            newRightTarget1 = robot.frontRight.getCurrentPosition() + ticks;
            newLeftTarget2 = robot.backLeft.getCurrentPosition() + ticks;
            newRightTarget2 = robot.backRight.getCurrentPosition() + ticks;

            robot.frontLeft.setTargetPosition(newLeftTarget1);
            robot.frontRight.setTargetPosition(newRightTarget1);
            robot.backLeft.setTargetPosition(newLeftTarget2);
            robot.backRight.setTargetPosition(newRightTarget2);

            robot.frontLeft.setPower(speed);
            robot.frontRight.setPower(speed);
            robot.backLeft.setPower(speed);
            robot.backRight.setPower(speed);

            while (opModeIsActive()
                    && (robot.frontLeft.isBusy()
                    || robot.frontRight.isBusy()
                    || robot.backLeft.isBusy()
                    || robot.backRight.isBusy())
                    && (robot.frontLeft.getCurrentPosition() <= newLeftTarget1
                    || robot.frontRight.getCurrentPosition() <= newRightTarget1)
                    || robot.backLeft.getCurrentPosition() <= newLeftTarget2
                    || robot.backRight.getCurrentPosition() <= newRightTarget2) {

            }

            robot.frontLeft.setPower(0);
            robot.frontRight.setPower(0);
            robot.backLeft.setPower(0);
            robot.backRight.setPower(0);

            robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }
    }


    //i2c range sensor

    public void driveUntilWall(){
        robot.backLeft.setDirection(DcMotor.Direction.FORWARD);
        robot.backRight.setDirection(DcMotor.Direction.FORWARD);
        robot.frontLeft.setDirection(DcMotor.Direction.REVERSE);
        robot.frontRight.setDirection(DcMotor.Direction.REVERSE);

        robot.backLeft.setPower(0.5);
        robot.backRight.setPower(0.5);
        robot.frontLeft.setPower(0.5);
        robot.frontRight.setPower(0.5);


        while(opModeIsActive() && robot.rangeSensor.getDistance(DistanceUnit.CM)>=20){
            telemetry.addData("Distance until wall: ", robot.rangeSensor.getDistance(DistanceUnit.CM));
            telemetry.update();
        }


        robot.backLeft.setPower(0);
        robot.backRight.setPower(0);
        robot.frontLeft.setPower(0);
        robot.frontRight.setPower(0);

        robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}

