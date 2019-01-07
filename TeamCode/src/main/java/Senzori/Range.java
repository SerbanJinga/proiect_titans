package Senzori;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware;
@TeleOp(name = "Senzor_dist", group = "Senzori")
public class Range extends LinearOpMode {
    public Hardware robot = new Hardware();

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

        robot.init(hardwareMap);

        while(opModeIsActive())
        {
            telemetry.addData("raw ultrasonic", robot.rangeSensor.rawUltrasonic());
            telemetry.addData("raw optical", robot.rangeSensor.rawOptical());
            telemetry.addData("cm optical", "%.2f cm", robot.rangeSensor.cmOptical());
            telemetry.addData("cm", "%.2f cm", robot.rangeSensor.getDistance(DistanceUnit.CM));
            telemetry.update();
        }
    }
}
