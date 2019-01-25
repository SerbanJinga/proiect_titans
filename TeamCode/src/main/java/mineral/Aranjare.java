package mineral;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.SamplingOrderDetector;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Aranjare")
public class Aranjare extends OpMode {


    private SamplingOrderDetector detector;
    @Override
    public void init() {

        telemetry.addData("Status", "Order");

        detector = new SamplingOrderDetector();
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        detector.useDefaults();

        detector.downscale = 0.4;
        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA;
        detector.maxAreaScorer.weight = 0.001;

        detector.ratioScorer.weight = 15;
        detector.ratioScorer.perfectRatio = 1.0;

        detector.enable();

        detector.getCurrentOrder().compareTo(SamplingOrderDetector.GoldLocation.LEFT);
    }


    @Override
    public void init_loop() {

    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {
        telemetry.addData("Current Order", detector.getCurrentOrder().toString());
        telemetry.addData("List Order", detector.getCurrentOrder().toString());


        if (detector.getCurrentOrder().compareTo(SamplingOrderDetector.GoldLocation.LEFT) == 0) {
            telemetry.addData("LEFTUM", "Stanga");
            telemetry.update();
        } else if (detector.getCurrentOrder().compareTo(SamplingOrderDetector.GoldLocation.LEFT) == 1) {
            telemetry.addData("RIGHTUM", "Dreapta");
            telemetry.update();
        } else if (detector.getCurrentOrder().compareTo(SamplingOrderDetector.GoldLocation.LEFT) == 2) {
            telemetry.addData("CENTRUM", "Centru");
            telemetry.update();
        } else {
            telemetry.addData("Sunteti Prosti", "Nu vad nimic ba");
            telemetry.update();
        }

    }

    @Override
    public void stop() {
        detector.disable();
    }
}
