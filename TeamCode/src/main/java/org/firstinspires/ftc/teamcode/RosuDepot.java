package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import mineral.DetectareMineral;

@Autonomous(name = "RosuDepot", group = "Pushbot")
public class RosuDepot extends LinearOpMode {


    public DetectareMineral mineral;
    public Hardware robot = new Hardware();
    protected ElapsedTime timer = new ElapsedTime();

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private static final String LICENSE_KEY =
            "AVURij3/////AAAAGWusRd1rFkGagFOB+18PAqEfWIuxX6zFovEtmuPxW4RAP96Bmsn5uiTeFl/FRiP8Zcwp9podgVtd7GLTaMzz3zNIMf02Pjk9SwnovU5aYFXEdLK6fuVnqGHoSwn09fYF0aZ+zJ4wpidGnE6zcm+D4A6nbqjKqIdvy7kEBVC6nv/iN61N6q+Qt2Vw99cAwDcjSs7JgPwZBjl5oBMO89JBmpf+OgMzwFd/KFo1GqD4eL8PXEZd8Yvs6aB9IMeBuNTRkruXc7fVcUPqab8ysWZjlhO2F6jZ9pV84uQmoTFlMf3wY8U8wg4jfIhWTbcWpL6XII1RDIz+XRVx0IHJ8nZLyxbcxqlGEGYbR33z1bqw4O+4";

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    private GoldAlignDetector detector;

    public char tfod_cit;


    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        waitForStart();


        telemetry.addData("Status", "DogeCV - Gold Align");

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

        mecanumDreapta(100, 1);
        mecanumDreapta(1600, 1);
        sleep(500);

        mersInFata(2500, 0.3);
        sleep(100);


        if(detectareGalben())
        {
            telemetry.addData("Ma sa lui tibi e femeie neserioasa", detector.getAligned());
            telemetry.addData("X Pos", detector.getXPosition());
        }
        if(!detectareGalben())
        {
            telemetry.addData("Nu", detector.getAligned());
            telemetry.addData("X Pos", detector.getXPosition());
        }



    }

    public void mersInSpate(int ticks, double speed){
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
                //LOOP NEBUN
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

    public void mersInFata(int ticks, double speed){
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
                //LOOP NEBUN
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

    public void mecanumStanga(int ticks, double speed){

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
                //LOOP NEBUN


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

    public void mecanumDreapta(int ticks, double speed){
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
                //LOOP NEBUN
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

    public void rotireSpreStanga(int ticks, double speed) {

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

                //LOOOOOOOP
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

    public void rotireSpreDreapta(int ticks, double speed) {

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

                //LOOOOOOOP
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

    public void oprireMecanum(){
        robot.frontLeft.setPower(0);
        robot.frontRight.setPower(0);
        robot.backLeft.setPower(0);
        robot.backRight.setPower(0);

    }

    public void detectareDistanta(){
//        if(robot.rangeFinder.getDistance(DistanceUnit.CM) <= 30){
//            oprireMecanum();
////            rotireSpreDreapta(2000, 0.2);
//
//

//
    }



    public boolean detectareGalben()
    {
        boolean valid = true;
        if(detector.getAligned())
        {
            valid = true;
        }
        if(!detector.getAligned()) {
            valid = false;
        }


        return valid;

    }


}



