package teleOp;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import hardware.Hardware;

@TeleOp(name = "Drive", group = "Pushbot")
public class Drive extends LinearOpMode {
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

            displayTelemetry(robot.frontLeft.getCurrentPosition(), robot.frontRight.getCurrentPosition()
                    ,robot.backLeft.getCurrentPosition(), robot.backRight.getCurrentPosition());
            mecanumDrive(gamepad1.left_stick_x, (gamepad1.left_trigger - gamepad1.right_trigger)
                    , -gamepad1.right_stick_x);
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

    public void mecanumDrive(double x, double y, double rotation) {

        double wheelSpeeds[] = new double[4];

        wheelSpeeds[0] = -x + y + rotation;
        wheelSpeeds[1] = x + y - rotation;
        wheelSpeeds[2] = x + y + rotation;
        wheelSpeeds[3] = -x + y - rotation;

        normalizeMecanum(wheelSpeeds);

        robot.frontLeft.setPower(wheelSpeeds[0]);
        robot.frontRight.setPower(wheelSpeeds[1]);
        robot.backLeft.setPower(wheelSpeeds[2]);
        robot.backRight.setPower(wheelSpeeds[3]);
    }

    private void normalizeMecanum(double[] wheelSpeeds) {

        double maxMagnitude = Math.abs(wheelSpeeds[0]);

        for (int i = 1; i < wheelSpeeds.length; ++i) {

            double magnitude = Math.abs(wheelSpeeds[i]);

            if (magnitude > maxMagnitude) {

                maxMagnitude = magnitude;
            }
        }

        if (maxMagnitude > 1.0) {

            for (int i = 0; i < wheelSpeeds.length; ++i) {

                wheelSpeeds[i] /= maxMagnitude;
            }
        }
    }

}


