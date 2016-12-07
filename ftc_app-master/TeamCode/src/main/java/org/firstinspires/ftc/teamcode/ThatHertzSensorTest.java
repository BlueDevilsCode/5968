package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.hardware.LegacyModule;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.text.DecimalFormat;

/**
 * Created by Sa'id on 11/19/2016.
 */

@Autonomous(name = "ThatHertz Sensor Test", group = "Test")
public class ThatHertzSensorTest extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();

    //for legacy sensors
    private LegacyModule legacy = null;

    //for light sensor
    private LightSensor backLightSensor = null;
    private LightSensor frontLightSensor = null;

    //for ultrasonic sensors
    private UltrasonicSensor ultrasonicLeft = null;
    private UltrasonicSensor ultrasonicRight = null;
    private double ultrasonicDifference = 0.0;

    //for I2C sensors
    private DeviceInterfaceModule dim = null;

    //for color sensor
    private ColorSensor color = null;
    static final int LED_CHANNEL = 5;
    float hsvValues[] = {0F,0F,0F};

    //for wheels
    private DcMotor frontLeftMotor = null;
    private DcMotor frontRightMotor = null;
    private DcMotor backLeftMotor = null;
    private DcMotor backRightMotor = null;

    @Override
    public void init() {
        //for ultrasonic sensors
        ultrasonicLeft = hardwareMap.ultrasonicSensor.get("ultrasonic_l");
        ultrasonicRight = hardwareMap.ultrasonicSensor.get("ultrasonic_r");
        legacy = hardwareMap.legacyModule.get("legacy");
        legacy.enable9v(4, true); //these two ports must have 9 volts
        legacy.enable9v(5, true); //to make sure that ultrasonics work

        //for DIM
        dim = hardwareMap.deviceInterfaceModule.get("d_i_m");

        //for color sensor
        color = hardwareMap.colorSensor.get("color");
        dim.setDigitalChannelMode(LED_CHANNEL, DigitalChannelController.Mode.OUTPUT);

        //for light sensors
        backLightSensor = hardwareMap.lightSensor.get("b_light");
        frontLightSensor = hardwareMap.lightSensor.get("f_light");

        //for wheels
        frontLeftMotor = hardwareMap.dcMotor.get("f_l_m");
        frontRightMotor = hardwareMap.dcMotor.get("f_r_m");
        backLeftMotor = hardwareMap.dcMotor.get("b_l_m");
        backRightMotor = hardwareMap.dcMotor.get("b_r_m");
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
    }


    @Override
    public void start() {runtime.reset();}


    @Override
    public void loop() {
//        boolean notForward = true;
//        boolean notTurned = true;
//        boolean notNearBeacon = true;

//        if (notForward) {
//            ultrasonicDifference = ultrasonicRight.getUltrasonicLevel() - ultrasonicLeft.getUltrasonicLevel();
//            if (Math.abs(ultrasonicDifference) < 5 && ultrasonicRight.getUltrasonicLevel() < 61) {
//                frontLeftMotor.setPower(.1);
//                frontRightMotor.setPower(.1);
//                backRightMotor.setPower(.1);
//                backLeftMotor.setPower(.1);
//            } else if (ultrasonicDifference >= 5) {
//                backLeftMotor.setPower(.05);
//                frontLeftMotor.setPower(.05);
//                frontRightMotor.setPower(-.05);
//                backRightMotor.setPower(-.05);
//            } else if (ultrasonicDifference <= -5) {
//                backLeftMotor.setPower(-.05);
//                frontLeftMotor.setPower(-.05);
//                frontRightMotor.setPower(.05);
//                backRightMotor.setPower(.05);
//            } else {
//                backLeftMotor.setPower(0);
//                frontLeftMotor.setPower(0);
//                frontRightMotor.setPower(-0);
//                backRightMotor.setPower(-0);
//                notForward = false;
//            }
//        }

//       if(notTurned) {
//
//      }

        telemetry.addData("Back Light: Raw", backLightSensor.getRawLightDetected() + "");
        telemetry.addData("Back Light: Normal", backLightSensor.getLightDetected() + "");
        telemetry.addData("Front Light: Raw", frontLightSensor.getRawLightDetected() + "");
        telemetry.addData("Front Light: Normal", frontLightSensor.getLightDetected() + "");
        telemetry.addData("Color: Clear", color.alpha() + "");
        telemetry.addData("Color: Red  ", color.red() + "");
        telemetry.addData("Color: Green", color.green() + "");
        telemetry.addData("Color: Blue ", color.blue() + "");
        telemetry.addData("Ultrasonic Sensor Left", ultrasonicLeft.getUltrasonicLevel() + "");
        telemetry.addData("Ultrasonic Sensor Right", ultrasonicRight.getUltrasonicLevel() + "");
        telemetry.addData("Ultrasonic Difference", ultrasonicDifference + "");
    }
}