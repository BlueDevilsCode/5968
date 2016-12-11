package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.hardware.LegacyModule;
import com.qualcomm.robotcore.hardware.Servo;
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
    private double GRAY_COLOR_CONSTANT_FRONT = 0.0;
    private double WHITE_COLOR_CONSTANT_FRONT = 0.0;
    private double GRAY_COLOR_CONSTANT_BACK = 0.0;
    private double WHITE_COLOR_CONSTANT_BACK = 0.0;

    //for ultrasonic sensors
    private UltrasonicSensor ultrasonicLeft = null;
//    private UltrasonicSensor ultrasonicRight = null;
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

    private Servo servoRight = null;
    private Servo servoLeft = null;

    //for autonomous loop
    private boolean foundBack = false;
    private boolean foundFront = false;
    private boolean hitBeaconOne = false;
    private boolean adjust = false;

    @Override
    public void init() {
        //for ultrasonic sensors
        ultrasonicLeft = hardwareMap.ultrasonicSensor.get("ultrasonic_l");
//        ultrasonicRight = hardwareMap.ultrasonicSensor.get("ultrasonic_r");
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
        ultrasonicLeft = hardwareMap.ultrasonicSensor.get("ultrasonic_l");
//        ultrasonicRight = hardwareMap.ultrasonicSensor.get("ultrasonic_r");
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

        //for servos
        servoRight = hardwareMap.servo.get("servoRight");
        servoLeft = hardwareMap.servo.get("servoLeft");

        GRAY_COLOR_CONSTANT_FRONT = frontLightSensor.getRawLightDetected();
        WHITE_COLOR_CONSTANT_FRONT = GRAY_COLOR_CONSTANT_FRONT + .15;
        GRAY_COLOR_CONSTANT_BACK = backLightSensor.getRawLightDetected();
        WHITE_COLOR_CONSTANT_BACK = GRAY_COLOR_CONSTANT_BACK + .15;

    }


    @Override
    public void start() {runtime.reset();}

    public boolean findStripeFront() {
        if(frontLightSensor.getRawLightDetected() >= WHITE_COLOR_CONSTANT_FRONT) {
            frontRightMotor.setPower(0);
            backRightMotor.setPower(0);
            backLeftMotor.setPower(0);
            backRightMotor.setPower(0);
            return true;
        }
        else {
            backLeftMotor.setPower(-.07);
            backRightMotor.setPower(-.07);
            frontLeftMotor.setPower(-.07);
            frontRightMotor.setPower(-.07);
        }
        return false;
    }
    public boolean findStripeFrontAdjust(){
        if(frontLightSensor.getRawLightDetected() >= WHITE_COLOR_CONSTANT_FRONT){
            backLeftMotor.setPower(0);
            backRightMotor.setPower(0);
            frontLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            return true;
        }
        else{
            backLeftMotor.setPower(.1);
            backRightMotor.setPower(.1);
            frontLeftMotor.setPower(.1);
            frontRightMotor.setPower(.1);
        }
        return false;

    }

    public boolean findStripeBack() {
        if(backLightSensor.getRawLightDetected() >= WHITE_COLOR_CONSTANT_BACK) {
            backLeftMotor.setPower(0);
            backRightMotor.setPower(0);
            frontLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            return true;
        }
        else {
            frontRightMotor.setPower(.13);
            backRightMotor.setPower(.13);
            backLeftMotor.setPower(-.13);
            frontLeftMotor.setPower(-.13);
        }
        return false;
    }

    public boolean hitBeacon(){
        if(ultrasonicLeft.getUltrasonicLevel() > 18) {
            frontRightMotor.setPower(-.15);
            frontLeftMotor.setPower(-.15);
            backLeftMotor.setPower(-.15);
            backRightMotor.setPower(-.15);
            servoRight.setPosition(0.0);
            servoLeft.setPosition(0.2);
        }
        if(ultrasonicLeft.getUltrasonicLevel() <= 18) {
            frontRightMotor.setPower(0);
            frontLeftMotor.setPower(0);
            backLeftMotor.setPower(0);
            backLeftMotor.setPower(0);
            
            servoRight.setPosition(0.2);
            servoLeft.setPosition(0.0);
            return true;
        }
        return false;
    }


    @Override
    public void loop() {
        if(!foundFront) {
            foundFront = findStripeFront();
        }
        if (foundFront && !adjust){
            adjust = findStripeFrontAdjust();
        }
        if(adjust && !foundBack) {
            foundBack = findStripeBack();
        }
        if(foundFront && foundBack && !hitBeaconOne) {
            hitBeaconOne = hitBeacon();
        }

        telemetry.addData("Back Light: Raw", backLightSensor.getRawLightDetected() + "");
        telemetry.addData("Back Light: Normal", backLightSensor.getLightDetected() + "");
        telemetry.addData("Front Light: Raw", frontLightSensor.getRawLightDetected() + "");
        telemetry.addData("Front Light: Normal", frontLightSensor.getLightDetected() + "");
        telemetry.addData("Color: Clear", color.alpha() + "");
        telemetry.addData("Color: Red  ", color.red() + "");
        telemetry.addData("Color: Green", color.green() + "");
        telemetry.addData("Color: Blue ", color.blue() + "");
        telemetry.addData("Ultrasonic Sensor Left", ultrasonicLeft.getUltrasonicLevel() + "");
//        telemetry.addData("Ultrasonic Sensor Right", ultrasonicRight.getUltrasonicLevel() + "");
        telemetry.addData("Ultrasonic Difference", ultrasonicDifference + "");
    }
}