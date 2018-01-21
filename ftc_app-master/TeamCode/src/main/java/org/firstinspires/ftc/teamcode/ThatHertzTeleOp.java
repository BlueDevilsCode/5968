package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Sa'id on 1/14/2018.
 */

@TeleOp(name = "ThatHertzTeleOp", group = "TeleOp")
public class ThatHertzTeleOp extends OpMode {

    private DcMotor frontRightMotor;
    private DcMotor frontLeftMotor;
    private DcMotor backRightMotor;
    private DcMotor backLeftMotor;

    private Servo frontRightServo;
    private Servo backRightServo;
    private Servo frontLeftServo;
    private Servo backLeftServo;

    private DcMotor collector;
    private DcMotor leftSlide;
    private DcMotor rightSlide;
    private DcMotor rotator;

    public void init() {
        frontRightMotor = hardwareMap.dcMotor.get("frMotor");
        frontLeftMotor = hardwareMap.dcMotor.get("flMotor");
        backRightMotor = hardwareMap.dcMotor.get("brMotor");
        backLeftMotor = hardwareMap.dcMotor.get("blMotor");

        frontRightServo = hardwareMap.servo.get("frServo");
        backRightServo = hardwareMap.servo.get("brServo");
        frontLeftServo = hardwareMap.servo.get("flServo");
        backLeftServo = hardwareMap.servo.get("blServo");

        collector = hardwareMap.dcMotor.get("collector");
        leftSlide = hardwareMap.dcMotor.get("lSlide");
        rightSlide = hardwareMap.dcMotor.get("rSlide");
        rotator = hardwareMap.dcMotor.get("rotator");

        frontRightServo.setPosition(0.5);
        backRightServo.setPosition(0.5);
        frontLeftServo.setPosition(0.5);

        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void loop() {
        if (gamepad1.right_stick_y < -.2) {
            frontRightServo.setPosition(0.5);
            backRightServo.setPosition(0.5);
            frontLeftServo.setPosition(0.5);
            backLeftServo.setPosition(0.5);

            frontRightMotor.setPower(-.5 * gamepad1.right_stick_y);
            frontLeftMotor.setPower(-.5 * gamepad1.right_stick_y);
            backRightMotor.setPower(-.5 * gamepad1.right_stick_y);
            backLeftMotor.setPower(-.5 * gamepad1.right_stick_y);
        } else if (gamepad1.right_stick_y > .2) {
            frontRightServo.setPosition(0.5);
            backRightServo.setPosition(0.5);
            frontLeftServo.setPosition(0.5);
            backLeftServo.setPosition(0.5);

            frontRightMotor.setPower(-.5 * gamepad1.right_stick_y);
            frontLeftMotor.setPower(-.5 * gamepad1.right_stick_y);
            backRightMotor.setPower(-.5 * gamepad1.right_stick_y);
            backLeftMotor.setPower(-.5 * gamepad1.right_stick_y);
        } else if (gamepad1.right_stick_x > .2) {
            frontRightServo.setPosition(.5 - (.5 * (1.0 / 2.0)));
            backRightServo.setPosition(.5 + (.5 * (1.0 / 2.0)));
            frontLeftServo.setPosition(.5 + (.5 * (1.0 / 2.0)));
            backLeftServo.setPosition(.5 - (.5 * (1.0 / 2.0)));

            frontRightMotor.setPower(-.5 * gamepad1.right_stick_x);
            frontLeftMotor.setPower(.5 * gamepad1.right_stick_x);
            backRightMotor.setPower(.5 * gamepad1.right_stick_x);
            backLeftMotor.setPower(-.5 * gamepad1.right_stick_x);
        } else if (gamepad1.right_stick_x < -.2) {
            frontRightServo.setPosition(.5 - (.5 * (1.0 / 2.0)));
            backRightServo.setPosition(.5 + (.5 * (1.0 / 2.0)));
            frontLeftServo.setPosition(.5 + (.5 * (1.0 / 2.0)));
            backLeftServo.setPosition(.5 - (.5 * (1.0 / 2.0)));

            frontRightMotor.setPower(-.5 * gamepad1.right_stick_x);
            frontLeftMotor.setPower(.5 * gamepad1.right_stick_x);
            backRightMotor.setPower(.5 * gamepad1.right_stick_x);
            backLeftMotor.setPower(-.5 * gamepad1.right_stick_x);
        }
        else if (gamepad1.right_bumper){
            frontRightServo.setPosition(0.5);
            backRightServo.setPosition(0.5);
            frontLeftServo.setPosition(0.5);
            backLeftServo.setPosition(0.5);

            frontRightMotor.setPower(-.5);
            frontLeftMotor.setPower(.5);
            backRightMotor.setPower(-.5);
            backLeftMotor.setPower(.5);
        }
        else if (gamepad1.left_bumper){
            frontRightServo.setPosition(0.5);
            backRightServo.setPosition(0.5);
            frontLeftServo.setPosition(0.5);
            backLeftServo.setPosition(0.5);

            frontRightMotor.setPower(.5);
            frontLeftMotor.setPower(-.5);
            backRightMotor.setPower(.5);
            backLeftMotor.setPower(-.5);
        } else {
            frontRightMotor.setPower(0);
            frontLeftMotor.setPower(0);
            backRightMotor.setPower(0);
            backLeftMotor.setPower(0);
        }

        if(gamepad2.dpad_up) {
            rightSlide.setPower(-.75);
            leftSlide.setPower(.75);
        } else if (gamepad2.dpad_down) {
            rightSlide.setPower(.75);
            leftSlide.setPower(-.75);
        } else {
            rightSlide.setPower(0);
            leftSlide.setPower(0);
        }

        if (gamepad2.right_stick_y < -.2) {
            rotator.setPower(-1);
        } else if (gamepad2.right_stick_y > .2) {
            rotator.setPower(1);
        } else {
            rotator.setPower(0);
        }

        if (gamepad2.a) {
            collector.setPower(1);
        } else {
            collector.setPower(0);
        }
    }
}
