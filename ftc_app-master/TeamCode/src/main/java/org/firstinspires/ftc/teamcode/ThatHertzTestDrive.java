package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Sa'id on 10/14/2017.
 */

@TeleOp(name = "ThatHertzTestDrive", group = "Tests")
public class ThatHertzTestDrive extends OpMode {

    private DcMotor frontRightMotor;
    private DcMotor frontLeftMotor;
    private DcMotor backRightMotor;
    private DcMotor backLeftMotor;

    private Servo rightServos;
    private Servo leftServos;

    private Servo rightClaw;
    private Servo leftClaw;
    private Servo wrist;

    @Override
    public void init() {
        frontRightMotor = hardwareMap.dcMotor.get("frMotor");
        frontLeftMotor = hardwareMap.dcMotor.get("flMotor");
        backRightMotor = hardwareMap.dcMotor.get("brMotor");
        backLeftMotor = hardwareMap.dcMotor.get("blMotor");

        rightServos = hardwareMap.servo.get("rServos");
        leftServos = hardwareMap.servo.get("lServos");

        rightClaw = hardwareMap.servo.get("rClaw");
        leftClaw = hardwareMap.servo.get("lClaw");
        wrist = hardwareMap.servo.get("wrist");

        rightServos.setPosition(.5);
        leftServos.setPosition(.5);

        rightClaw.setPosition(.5);
        leftClaw.setPosition(.5);
        wrist.setPosition(0);

        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        if (gamepad1.right_stick_y < 0) {
            rightServos.setPosition(.5);
            leftServos.setPosition(.5);

            frontRightMotor.setPower(-.5 * gamepad1.right_stick_y);
            frontLeftMotor.setPower(-.5 * gamepad1.right_stick_y);
            backRightMotor.setPower(-.5 * gamepad1.right_stick_y);
            backLeftMotor.setPower(-.5 * gamepad1.right_stick_y);
        } else if (gamepad1.right_stick_y > .3) {
            rightServos.setPosition(.5);
            leftServos.setPosition(.5);

            frontRightMotor.setPower(-.5 * gamepad1.right_stick_y);
            frontLeftMotor.setPower(-.5 * gamepad1.right_stick_y);
            backRightMotor.setPower(-.5 * gamepad1.right_stick_y);
            backLeftMotor.setPower(-.5 * gamepad1.right_stick_y);
        } else if (gamepad1.right_stick_x > .3) {
            rightServos.setPosition(.5 - (.5 * (2.0/3.0)) - 0.1);
            leftServos.setPosition(.5 + (.5 * (2.0/3.0)) + 0.1);

            frontRightMotor.setPower(.5 * gamepad1.right_stick_x);
            frontLeftMotor.setPower(-.5 * gamepad1.right_stick_x);
            backRightMotor.setPower(.5 * gamepad1.right_stick_x);
            backLeftMotor.setPower(-.5 * gamepad1.right_stick_x);
        } else if (gamepad1.right_stick_x < -.3) {
            rightServos.setPosition(.5 - (.5 * (2.0/3.0)) - 0.1);
            leftServos.setPosition(.5 + (.5 * (2.0/3.0)) + 0.1);

            frontRightMotor.setPower(.5 * gamepad1.right_stick_x);
            frontLeftMotor.setPower(-.5 * gamepad1.right_stick_x);
            backRightMotor.setPower(-.5 * gamepad1.right_stick_x);
            backLeftMotor.setPower(.5 * gamepad1.right_stick_x);
        } else if(gamepad1.right_trigger > 0) {
            frontRightMotor.setPower(.5);
            frontLeftMotor.setPower(-.5);
            backRightMotor.setPower(.5);
            backLeftMotor.setPower(-.5);
        } else if(gamepad1.left_trigger > 0) {
            frontRightMotor.setPower(-.5);
            frontLeftMotor.setPower(.5);
            backRightMotor.setPower(-.5);
            backLeftMotor.setPower(.5);
        } else {
            frontRightMotor.setPower(0);
            frontLeftMotor.setPower(0);
            backRightMotor.setPower(0);
            backLeftMotor.setPower(0);
        }
    }
}