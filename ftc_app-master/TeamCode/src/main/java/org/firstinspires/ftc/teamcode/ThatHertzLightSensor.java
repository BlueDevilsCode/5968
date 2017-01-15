package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Sa'id on 12/9/2016.
 */

@Autonomous(name = "ThatHertzLSTest", group = "Testing")
public class ThatHertzLightSensor extends LinearOpMode {
    ElapsedTime runtime = new ElapsedTime();

    private LightSensor ls = null;

    @Override public void runOpMode() {
        ls = hardwareMap.lightSensor.get("l_s");

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            telemetry.addData("Light Sensor Raw", ls.getRawLightDetected());
            telemetry.addData("Light Sensor Level", ls.getLightDetected());
            telemetry.update();
        }
    }
}
