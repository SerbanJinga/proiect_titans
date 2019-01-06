package mineral;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.SilverDetector;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.opencv.core.Size;


@TeleOp(name = "Sfera", group = "DogeCV")
public class Sfera extends OpMode {

    private SilverDetector detector;
    @Override
    public void init() {

        telemetry.addData("Status", "Silver Mineral");

        detector = new SilverDetector();
        detector.setAdjustedSize(new Size(480, 270));
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        detector.useDefaults();

        detector.downscale = 0.4;

        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA;
        detector.maxAreaScorer.weight = 0.005;

        detector.ratioScorer.weight = 5;
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

    }

    @Override
    public void stop() {
        detector.disable();
    }
}
