package mineral;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "DetectareMineral", group = "DogeCV")

public class DetectareMineral extends OpMode {

    private GoldAlignDetector detector;

    @Override
    public void init() {

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




    }

    @Override
    public void init_loop()
    {

    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {
        telemetry.addData("IsAligned", detector.getAligned());
        telemetry.addData("X Pos", detector.getXPosition());
    }

    @Override
    public void stop() {
        detector.disable();
    }
}
