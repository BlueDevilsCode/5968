package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Sa'id on 12/10/2016.
 */
public class ThatHertzColorSensor extends OpMode {
    ElapsedTime runtime = new ElapsedTime();

    private ColorSensor color = null;
    private DeviceInterfaceModule dim = null;
    private static final int LED_CHANNEL = 5;

    @Override
    public void init() {
        color = hardwareMap.colorSensor.get("color");
        dim = hardwareMap.deviceInterfaceModule.get("d_i_m");

        dim.setDigitalChannelMode(LED_CHANNEL, DigitalChannelController.Mode.OUTPUT);
    }

    @Override
    public void start() {runtime.reset();}

    @Override
    public void loop() {
        telemetry.addData("ARGB", "" + color.argb());
        telemetry.addData("Green", "" + color.green());
        telemetry.addData("Blue", "" + color.blue());
        telemetry.addData("Red", "" + color.red());
        telemetry.addData("Clear/Alpha", "" + color.alpha());
    }
}
