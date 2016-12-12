package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.hardware.LegacyModule;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Sa'id on 11/19/2016.
 */

@Autonomous(name = "[5968] ThatHertz Autonomous BLUE", group = "Match-Ready")
public class ThatHertzAutonomousBLUE extends LinearOpMode {
    ElapsedTime runtime = new ElapsedTime();

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
    static final int HELPER_LED_CHANNEL = 4;
    float hsvValues[] = {0F, 0F, 0F};

    //for wheels
    private DcMotor frontLeftMotor = null;
    private DcMotor frontRightMotor = null;
    private DcMotor backLeftMotor = null;
    private DcMotor backRightMotor = null;

    private Servo servoRight = null;
    private Servo servoLeft = null;
    private boolean servosFixed = false;

    //for autonomous loop
    private boolean foundBack = false;
    private boolean foundFront = false;
    private boolean hitBeaconOne = false;
    private boolean choseColor = false;
    private boolean ballHit = false;
    private boolean forward = false;
    private boolean jerked = false;

    public boolean moveForwardFast() {
        if(runtime.time() < .5) {
            frontLeftMotor.setPower(-.5);
            frontRightMotor.setPower(-.5);
            backRightMotor.setPower(-.5);
            backLeftMotor.setPower(-.5);
        } else {
            return true;
        }
        return false;
    }

    public boolean findStripeFront() {
        if (frontLightSensor.getRawLightDetected() >= WHITE_COLOR_CONSTANT_FRONT) {
            frontRightMotor.setPower(0);
            backRightMotor.setPower(0);
            backLeftMotor.setPower(0);
            backRightMotor.setPower(0);

            return true;
        } else {
            backLeftMotor.setPower(-.1);
            backRightMotor.setPower(-.1);
            frontLeftMotor.setPower(-.1);
            frontRightMotor.setPower(-.1);
        }
        return false;
    }

    public boolean findStripeBack() {
        if (backLightSensor.getRawLightDetected() >= WHITE_COLOR_CONSTANT_BACK) {
            backLeftMotor.setPower(0);
            backRightMotor.setPower(0);
            frontLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            return true;
        } else {
            frontRightMotor.setPower(-.13);
            backRightMotor.setPower(-.13);
            backLeftMotor.setPower(.13);
            frontLeftMotor.setPower(.13);
        }
        return false;
    }

    public boolean findColor() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {}

        if(color.blue() < color.red()) {
            servoRight.setPosition(1);
            servoLeft.setPosition(.75);
        }
        else {
            servoLeft.setPosition(0);
            servoRight.setPosition(.75);
        }
        return true;
    }

    public boolean hitBeacon() {

        if (ultrasonicLeft.getUltrasonicLevel() > 18) {
            frontRightMotor.setPower(-.1);
            frontLeftMotor.setPower(-.1);
            backLeftMotor.setPower(-.1);
            backRightMotor.setPower(-.1);
            servoRight.setPosition(1);
            servoLeft.setPosition(0.8);
        }
        if (ultrasonicLeft.getUltrasonicLevel() <= 18) {
            frontRightMotor.setPower(0);
            frontLeftMotor.setPower(0);
            backLeftMotor.setPower(0);
            backLeftMotor.setPower(0);
            return true;
        }
        return false;
    }

    public boolean hitBall() {
        if(ultrasonicLeft.getUltrasonicLevel() < 122) {
            backLeftMotor.setPower(.3);
            backRightMotor.setPower(.3);
            frontLeftMotor.setPower(.3);
            frontRightMotor.setPower(.3);
        }
        if(ultrasonicLeft.getUltrasonicLevel() >= 122) {
            backLeftMotor.setPower(0);
            backRightMotor.setPower(0);
            frontLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            return true;
        }

        return false;
    }

    public void runOpMode() {
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
        dim.setDigitalChannelMode(HELPER_LED_CHANNEL, DigitalChannelController.Mode.OUTPUT);

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
        dim.setDigitalChannelState(LED_CHANNEL, true);
        dim.setDigitalChannelState(HELPER_LED_CHANNEL, true);

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
        servoRight = hardwareMap.servo.get("r_b_s");
        servoLeft = hardwareMap.servo.get("l_b_s");


        GRAY_COLOR_CONSTANT_FRONT = frontLightSensor.getRawLightDetected();
        WHITE_COLOR_CONSTANT_FRONT = GRAY_COLOR_CONSTANT_FRONT + .15;
        GRAY_COLOR_CONSTANT_BACK = backLightSensor.getRawLightDetected();
        WHITE_COLOR_CONSTANT_BACK = GRAY_COLOR_CONSTANT_BACK + .15;

        waitForStart();

        runtime.reset();

        while(opModeIsActive()) {
            if(!servosFixed) {
                servoLeft.setPosition(0);
                servoRight.setPosition(1);
                servosFixed = true;
            }
            if(!forward) {
                forward = moveForwardFast();
            }
            if (forward && !foundFront) {
                foundFront = findStripeFront();
            }
//            if(forward && foundFront && !jerked) {r
//                frontRightMotor.setPower(.1);
//                backRightMotor.setPower(.1);
//                backLeftMotor.setPower(.1);
//                backRightMotor.setPower(.1);
//
//                try {
//                    Thread.sleep(25);
//                } catch(Exception e) {
//
//                }
//                jerked = true;
//            }
            if (forward && foundFront && !choseColor) {
                choseColor = findColor();
            }
            if(forward && foundFront && choseColor && !foundBack) {
                foundBack = findStripeBack();
            }
            if (forward && foundFront && foundBack && choseColor && !hitBeaconOne) {
                hitBeaconOne = hitBeacon();
            }
            if(forward && foundFront && foundBack && choseColor && hitBeaconOne && !hitBall()) {
                ballHit = hitBall();
            }

            telemetry.addData("Back Light: Raw", backLightSensor.getRawLightDetected());
            telemetry.addData("Back Light: Normal", backLightSensor.getLightDetected());
            telemetry.addData("Front Light: Raw", frontLightSensor.getRawLightDetected());
            telemetry.addData("Front Light: Normal", frontLightSensor.getLightDetected());
            telemetry.addData("Color: Clear", color.alpha());
            telemetry.addData("Color: Red  ", color.red());
            telemetry.addData("Color: Green", color.green());
            telemetry.addData("Color: Blue ", color.blue());
            telemetry.addData("Ultrasonic Sensor Left", ultrasonicLeft.getUltrasonicLevel() + "");
    //        telemetry.addData("Ultrasonic Sensor Right", ultrasonicRight.getUltrasonicLevel() + "");
            telemetry.addData("Ultrasonic Difference", ultrasonicDifference + "");
            telemetry.update();
        }
    }
}
