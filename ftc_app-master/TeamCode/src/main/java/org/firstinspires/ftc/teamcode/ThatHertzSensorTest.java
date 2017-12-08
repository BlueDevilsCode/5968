package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by Sa'id on 12/8/2017.
 */

@Autonomous(name = "ThatHertzSensorTest", group = "Test")
public class ThatHertzSensorTest extends LinearOpMode {

    private ColorSensor colorSensor;
    private DeviceInterfaceModule cdim;

    private TouchSensor touchSensor;

    @Override
    public void runOpMode() {
        colorSensor = hardwareMap.colorSensor.get("color");
        cdim = hardwareMap.deviceInterfaceModule.get("cdim");
        cdim.setDigitalChannelMode(5, DigitalChannel.Mode.OUTPUT);
        cdim.setDigitalChannelState(5, true);

        touchSensor = hardwareMap.touchSensor.get("touch");

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Red", colorSensor.red());
            telemetry.addData("Green", colorSensor.green());
            telemetry.addData("Blue", colorSensor.blue());
            telemetry.addData("Touch Sensor Pressed", touchSensor.isPressed());
            telemetry.update();
        }
    }
}
