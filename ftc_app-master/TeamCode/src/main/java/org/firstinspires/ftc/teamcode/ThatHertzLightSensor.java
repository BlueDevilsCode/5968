package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Sa'id on 12/9/2016.
 */

@Autonomous(name = "ThatHertzLSTest", group = "Testing")
public class ThatHertzLightSensor extends OpMode {
    ElapsedTime runtime = new ElapsedTime();

    private LightSensor ls = null;

    @Override
    public void init() {
        ls = hardwareMap.lightSensor.get("l_s");
    }

    @Override
    public void start() {runtime.reset();}

    @Override
    public void loop() {
        telemetry.addData("Light Sensor Raw", ls.getRawLightDetected() + "");
        telemetry.addData("Light Sensor Level", ls.getLightDetected());
    }
}
