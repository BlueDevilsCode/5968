package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
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

    private Servo posDiagServos;
    private Servo negDiagServos;

    private DcMotor elbow;
    private Servo rightClaw;
    private Servo leftClaw;
    private Servo wrist;

    @Override
    public void init() {
        frontRightMotor = hardwareMap.dcMotor.get("frMotor");
        frontLeftMotor = hardwareMap.dcMotor.get("flMotor");
        backRightMotor = hardwareMap.dcMotor.get("brMotor");
        backLeftMotor = hardwareMap.dcMotor.get("blMotor");

        posDiagServos = hardwareMap.servo.get("posServos");
        negDiagServos = hardwareMap.servo.get("negServos");

        elbow = hardwareMap.dcMotor.get("elbow");
        rightClaw = hardwareMap.servo.get("rClaw");
        leftClaw = hardwareMap.servo.get("lClaw");
        wrist = hardwareMap.servo.get("wrist");

        posDiagServos.setPosition(.5);
        negDiagServos.setPosition(.5);

        rightClaw.setPosition(.5);
        leftClaw.setPosition(.5);
        wrist.setPosition(.0);
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        if (gamepad1.right_stick_y < -.2) {
            posDiagServos.setPosition(.5);
            negDiagServos.setPosition(.5);

            frontRightMotor.setPower(.5 * gamepad1.right_stick_y);
            frontLeftMotor.setPower(.5 * gamepad1.right_stick_y);
            backRightMotor.setPower(.5 * gamepad1.right_stick_y);
            backLeftMotor.setPower(.5 * gamepad1.right_stick_y);
        } else if (gamepad1.right_stick_y > .2) {
            posDiagServos.setPosition(.5);
            negDiagServos.setPosition(.5);

            frontRightMotor.setPower(.5 * gamepad1.right_stick_y);
            frontLeftMotor.setPower(.5 * gamepad1.right_stick_y);
            backRightMotor.setPower(.5 * gamepad1.right_stick_y);
            backLeftMotor.setPower(.5 * gamepad1.right_stick_y);
        } else if (gamepad1.right_stick_x > .2) {
            posDiagServos.setPosition(.5 + (.5 * (2.0 / 3.0)) + 0.1);
            negDiagServos.setPosition(.5 - (.5 * (2.0 / 3.0)) - 0.1);

            frontRightMotor.setPower(-.5 * gamepad1.right_stick_x);
            frontLeftMotor.setPower(.5 * gamepad1.right_stick_x);
            backRightMotor.setPower(.5 * gamepad1.right_stick_x);
            backLeftMotor.setPower(-.5 * gamepad1.right_stick_x);
        } else if (gamepad1.right_stick_x < -.2) {
            posDiagServos.setPosition(.5 + (.5 * (2.0 / 3.0)) + 0.1);
            negDiagServos.setPosition(.5 - (.5 * (2.0 / 3.0)) - 0.1);

            frontRightMotor.setPower(-.5 * gamepad1.right_stick_x);
            frontLeftMotor.setPower(.5 * gamepad1.right_stick_x);
            backRightMotor.setPower(.5 * gamepad1.right_stick_x);
            backLeftMotor.setPower(-.5 * gamepad1.right_stick_x);
        } else {
            frontRightMotor.setPower(0);
            frontLeftMotor.setPower(0);
            backRightMotor.setPower(0);
            backLeftMotor.setPower(0);
        }

        if (gamepad1.left_trigger > 0) {
            wrist.setPosition(.0);
        }
        else if (gamepad1.right_trigger > 0) {
            wrist.setPosition(1);
        }

        if (gamepad1.a) {
            leftClaw.setPosition(1);
            rightClaw.setPosition(0);
        } else if (gamepad1.b) {
            leftClaw.setPosition(.1);
            rightClaw.setPosition(.9);
        } else if (gamepad1.y) {
            leftClaw.setPosition(.5);
            rightClaw.setPosition(.5);
        }

        if (gamepad1.dpad_up) {
            elbow.setPower(.4);
        } else if (gamepad1.dpad_down) {
            elbow.setPower(-.2);
        } else {
            elbow.setPower(0);
        }
    }
}