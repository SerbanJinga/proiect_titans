package mineral;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.detectors.roverrukus.HoughSilverDetector;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Hough Silver", group = "DogeCV")
public class HoughSfera extends OpMode {

    private HoughSilverDetector detector;

    @Override
    public void init() {
        telemetry.addData("Status", "HoughSfera");

        detector = new HoughSilverDetector();
        detector.downscale = 1;
        detector.useFixedDownscale = false;
        detector.sensitivity = 1.6;
        detector.minDistance = 60;
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        detector.useDefaults();

        detector.downscale = 0.4;

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
