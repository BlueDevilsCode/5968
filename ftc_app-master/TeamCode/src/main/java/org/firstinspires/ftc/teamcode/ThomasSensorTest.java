
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.LegacyModule;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "[5968] ThomasSensorTest", group = "TeleOpTest")
public class ThomasSensorTest extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();

    //for wheels
    private DcMotor frontLeftMotor = null;
    private DcMotor frontRightMotor = null;
    private DcMotor backLeftMotor = null;
    private DcMotor backRightMotor = null;

    //for collection sweeper
    private DcMotor collection = null;

    //for beacon scoring
    private Servo rightBeaconServo = null;
    private Servo leftBeaconServo = null;

     private LegacyModule legacy = null;

    //for light sensor
    private LightSensor backLightSensor = null;
    private LightSensor frontLightSensor = null;

    //for ultrasonic sensors
    private UltrasonicSensor ultrasonicLeft = null;
    //    private UltrasonicSensor ultrasonicRight = null;
    private double ultrasonicDifference = 0.0;

    //for I2C sensors
    private DeviceInterfaceModule dim = null;
    //code to run on init
    @Override
    public void init() {
        telemetry.addData("Status", "Initializing");

        //for wheels
        frontLeftMotor = hardwareMap.dcMotor.get("f_l_m");
        frontRightMotor = hardwareMap.dcMotor.get("f_r_m");
        backLeftMotor = hardwareMap.dcMotor.get("b_l_m");
        backRightMotor = hardwareMap.dcMotor.get("b_r_m");

        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);

        //for collection sweeper
        collection = hardwareMap.dcMotor.get("collection");

        //for beacon scoring
        rightBeaconServo = hardwareMap.servo.get("r_b_s");
        leftBeaconServo = hardwareMap.servo.get("l_b_s");

        rightBeaconServo.setPosition(0); //probably will need changing (especially left)
        leftBeaconServo.setPosition(0);

        telemetry.addData("Status", "Initialized");
    }


    //code to run once start is pressed (instantaneous)
    @Override
    public void start() {runtime.reset();}


    //Main code that will run during play
    @Override
    public void loop() {
        telemetry.addData("Status", "Running: " + runtime.toString());

        //MOVING
        if(gamepad1.dpad_left) {
            //strafe left
            frontLeftMotor.setPower(-.4);
            backRightMotor.setPower(-.4);
            frontRightMotor.setPower(.4);
            backLeftMotor.setPower(.4);
        } else if(gamepad1.dpad_right) {
            //strafe right
            frontLeftMotor.setPower(.4);
            backRightMotor.setPower(.4);
            frontRightMotor.setPower(-.4);
            backLeftMotor.setPower(-.4);
        } else {

            if (gamepad1.left_bumper) { //for accurate movement
                frontLeftMotor.setPower(.2);
                backLeftMotor.setPower(.2);
                frontRightMotor.setPower(.2);
                backRightMotor.setPower(.2);
            }
            else if (gamepad1.right_bumper) {
                frontLeftMotor.setPower(-.2);
                backLeftMotor.setPower(-.2);
                frontRightMotor.setPower(-.2);
                backRightMotor.setPower(-.2);

            }
            else if (gamepad1.left_trigger > 0) {
                frontLeftMotor.setPower(-.2);
                backLeftMotor.setPower(-.2);
                frontRightMotor.setPower(.2);
                backRightMotor.setPower(.2);

            }
            else if(gamepad1.right_trigger > 0){
                frontLeftMotor.setPower(.2);
                backLeftMotor.setPower(.2);
                frontRightMotor.setPower(-.2);
                backRightMotor.setPower(-.2);
            }
            else {
                frontLeftMotor.setPower(-gamepad1.left_stick_y);
                backLeftMotor.setPower(-gamepad1.left_stick_y);
                frontRightMotor.setPower(-gamepad1.right_stick_y);
                backRightMotor.setPower(-gamepad1.right_stick_y);
            }
        }

        //COLLECTION
        if(gamepad1.a) {
            collection.setPower(-.75);
        } else if(gamepad1.b) {
            collection.setPower(.75);
        } else {
            collection.setPower(0);
        }

        //BEACONS
//        if(gamepad1.left_stick_button && (leftBeaconServo.getPosition() != .75 || leftBeaconServo.getPosition() != 0)) {
//            leftBeaconServo.setPosition(Math.abs(leftBeaconServo.getPosition() - .75));
//        } else if(gamepad1.right_stick_button && (rightBeaconServo.getPosition() != .75 || rightBeaconServo.getPosition() != 0)) {
//            rightBeaconServo.setPosition(Math.abs(rightBeaconServo.getPosition() - .75));
//        }
        if(gamepad1.right_stick_button) {
            rightBeaconServo.setPosition(.75);
        } else if(gamepad1.left_stick_button) {
            leftBeaconServo.setPosition(.75);
        }
        if(gamepad1.right_stick_button && rightBeaconServo.getPosition() == .75) {
            rightBeaconServo.setPosition(0);
        } else if(gamepad1.left_stick_button && leftBeaconServo.getPosition() == .75) {
            leftBeaconServo.setPosition(0);
        }
        telemetry.addData("FLM Power", frontLeftMotor.getPower());
        telemetry.addData("FRM Power", frontRightMotor.getPower());
        telemetry.addData("BLM Power", backLeftMotor.getPower());
        telemetry.addData("BRM Power", backRightMotor.getPower());
        telemetry.addData("Collection Power", collection.getPower());
        telemetry.addData("LS Position", leftBeaconServo.getPosition());
        telemetry.addData("RS Position", rightBeaconServo.getPosition());
        telemetry.addData("Back Light: Raw", backLightSensor.getRawLightDetected() + "");
        telemetry.addData("Back Light: Normal", backLightSensor.getLightDetected() + "");
        telemetry.addData("Front Light: Raw", frontLightSensor.getRawLightDetected() + "");
        telemetry.addData("Front Light: Normal", frontLightSensor.getLightDetected() + "");
//        telemetry.addData("Color: Clear", color.alpha() + "");
//        telemetry.addData("Color: Red  ", color.red() + "");
//        telemetry.addData("Color: Green", color.green() + "");
//        telemetry.addData("Color: Blue ", color.blue() + "");
        telemetry.addData("Ultrasonic Sensor Left", ultrasonicLeft.getUltrasonicLevel() + "");
//        telemetry.addData("Ultrasonic Sensor Right", ultrasonicRight.getUltrasonicLevel() + "");
        telemetry.addData("Ultrasonic Difference", ultrasonicDifference + "");
    }
}