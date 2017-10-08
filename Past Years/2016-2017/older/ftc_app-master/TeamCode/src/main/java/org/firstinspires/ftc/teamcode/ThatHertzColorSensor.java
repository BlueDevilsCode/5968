package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;



/**
 * Created by Sa'id on 12/10/2016.
 */
@Autonomous(name = "ThatHertzColorSensor", group = "Testing")
public class ThatHertzColorSensor extends LinearOpMode {

    private ColorSensor color = null;
    private DeviceInterfaceModule dim = null;
    private static final int LED_CHANNEL = 5;
    private boolean bLEDOn = true;
    private float hsvValues[] = {0F,0F,0F};

    public void runOpMode() {

        color = hardwareMap.colorSensor.get("color");
        dim = hardwareMap.deviceInterfaceModule.get("d_i_m");

        dim.setDigitalChannelMode(LED_CHANNEL, DigitalChannelController.Mode.OUTPUT);
        dim.setDigitalChannelState(LED_CHANNEL, bLEDOn);

        waitForStart();

        while(opModeIsActive()) {
            if (color.red() < 1) {
                dim.setDigitalChannelState(LED_CHANNEL, true);
            } else {
                dim.setDigitalChannelState(LED_CHANNEL, false);
            }
            Color.RGBToHSV((color.red() * 255) / 800, (color.green() * 255) / 800, (color.blue() * 255) / 800, hsvValues);


            telemetry.addData("ARGB", color.argb());
            telemetry.addData("Green", color.green());
            telemetry.addData("Blue", color.blue());
            telemetry.addData("Red", color.red());
            telemetry.addData("Clear/Alpha", color.alpha());
            telemetry.addData("TEST1", hsvValues[0]);
            telemetry.addData("TEST2", hsvValues[1]);
            telemetry.addData("TEST3", hsvValues[2]);
            telemetry.update();

        }
    }
}
