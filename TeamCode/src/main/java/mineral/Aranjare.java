package mineral;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.SamplingOrderDetector;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

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

    }

    @Override
    public void stop() {
        detector.disable();
    }
}
