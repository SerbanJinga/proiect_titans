package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="TeleghidatNou", group="Pushbot")
public class Teleghidat extends LinearOpMode {

    Hardware robot = new Hardware();

    @Override
    public void runOpMode() {


        robot.init(hardwareMap);


        telemetry.log().clear();
        telemetry.addData("Ready!", "Press PLAY!");
        telemetry.update();


        double frontLeftDrive, frontRightDrive, backRightDrive, backLeftDrive = 0;


        telemetry.addData("left_X", gamepad1.left_stick_x);
        telemetry.addData("right_X", gamepad1.right_stick_x);
        telemetry.addData("left_Y", gamepad1.left_stick_y);
        telemetry.addData("right_Y", gamepad1.right_stick_y);

        waitForStart();


        while (opModeIsActive()) {

//            frontRightDrive = gamepad1.left_stick_x - gamepad1.left_stick_y - gamepad1.right_stick_y;
//            frontLeftDrive  = gamepad1.left_stick_x + gamepad1.left_stick_y + gamepad1.right_stick_y;
//            backLeftDrive = gamepad1.left_stick_x - gamepad1.left_stick_y + gamepad1.right_stick_y;
//            backRightDrive = gamepad1.left_stick_x + gamepad1.left_stick_y - gamepad1.right_stick_y;

//            if(gamepad1.right_bumper || gamepad1.left_bumper) {
//                robot.frontRight.setPower(frontRightDrive * 0.3);
//                robot.frontLeft.setPower(frontLeftDrive * 0.3);
//                robot.backRight.setPower(backRightDrive * 0.3);
//                robot.backLeft.setPower(backLeftDrive * 0.3);
//            } else {
//                robot.frontRight.setPower(frontRightDrive);
//                robot.frontLeft.setPower(frontLeftDrive);
//                robot.backRight.setPower(backRightDrive);
//                robot.backLeft.setPower(backLeftDrive);
//            }
            double Speed = -gamepad1.left_stick_y;
            double Turn = gamepad1.left_stick_x;
            double Strafe = gamepad1.right_stick_x;

            if (gamepad1.right_bumper || gamepad1.left_bumper) {
                frontLeftDrive = -(+Speed + Turn - Strafe) * 0.2;
                frontRightDrive = -(+Speed - Turn + Strafe) * 0.2;
                backLeftDrive = -(+Speed + Turn + Strafe) * 0.2;
                backRightDrive = -(+Speed - Turn - Strafe) * 0.2;
            } else {
                frontLeftDrive = -(+Speed +Turn -Strafe);
                frontRightDrive = -(+Speed -Turn +Strafe);
                backLeftDrive = -(+Speed +Turn +Strafe);
                backRightDrive = -(+Speed -Turn -Strafe);
            }
            robot.frontRight.setPower(frontRightDrive);
            robot.frontLeft.setPower(frontLeftDrive);
            robot.backRight.setPower(backRightDrive);
            robot.backLeft.setPower(backLeftDrive);
        }
    }

    public void displayTelemetry(double fLD, double fRD, double bLD, double bRD) {
        telemetry.log().clear();
        telemetry.addData("frontLeft: ", fLD);
        telemetry.addData("frontRight: ", fRD);
        telemetry.addData("backLeft: ", bLD);
        telemetry.addData("backRight: ", bRD);
        telemetry.update();
    }
}