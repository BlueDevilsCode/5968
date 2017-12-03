package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by westfield_robotics on 11/12/2017.
 */

public class ThatHertzTCTeleOp extends OpMode {
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
        wrist.setPosition(.5);
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

            frontRightMotor.setPower(.5 * gamepad1.right_stick_y -.1);
            frontLeftMotor.setPower(.5 * gamepad1.right_stick_y -.1);
            backRightMotor.setPower(.5 * gamepad1.right_stick_y-.1);
            backLeftMotor.setPower(.5 * gamepad1.right_stick_y-.1);
        } else if (gamepad1.right_stick_y > .2) {
            posDiagServos.setPosition(.5);
            negDiagServos.setPosition(.5);

            frontRightMotor.setPower(.5 * gamepad1.right_stick_y-.1);
            frontLeftMotor.setPower(.5 * gamepad1.right_stick_y-.1);
            backRightMotor.setPower(.5 * gamepad1.right_stick_y-.1);
            backLeftMotor.setPower(.5 * gamepad1.right_stick_y-.1);
        } else if (gamepad1.right_stick_x > .2) {
            posDiagServos.setPosition(.5 + (.5 * (2.0 / 3.0)) + 0.1);
            negDiagServos.setPosition(.5 - (.5 * (2.0 / 3.0)) - 0.1);

            frontRightMotor.setPower(-.5 * gamepad1.right_stick_x+.1);
            frontLeftMotor.setPower(.5 * gamepad1.right_stick_x-.1);
            backRightMotor.setPower(.5 * gamepad1.right_stick_x-.1);
            backLeftMotor.setPower(-.5 * gamepad1.right_stick_x+.1);
        } else if (gamepad1.right_stick_x < -.2) {
            posDiagServos.setPosition(.5 + (.5 * (2.0 / 3.0)) + 0.1);
            negDiagServos.setPosition(.5 - (.5 * (2.0 / 3.0)) - 0.1);

            frontRightMotor.setPower(-.5 * gamepad1.right_stick_x +.1);
            frontLeftMotor.setPower(.5 * gamepad1.right_stick_x -.1);
            backRightMotor.setPower(.5 * gamepad1.right_stick_x -.1);
            backLeftMotor.setPower(-.5 * gamepad1.right_stick_x +.1);
        }
        else if (gamepad1.right_bumper){
            frontRightMotor.setPower(-.5 * gamepad1.right_stick_x +.1);
            frontLeftMotor.setPower(.5 * gamepad1.right_stick_x -.1);
            backRightMotor.setPower(-.5 * gamepad1.right_stick_x +.1);
            backLeftMotor.setPower(.5 * gamepad1.right_stick_x -.1);
        }
        else if (gamepad1.left_bumper){
            frontRightMotor.setPower(.5 * gamepad1.right_stick_x -.1);
            frontLeftMotor.setPower(-.5 * gamepad1.right_stick_x +.1);
            backRightMotor.setPower(.5 * gamepad1.right_stick_x -.1);
            backLeftMotor.setPower(-.5 * gamepad1.right_stick_x +.1);
        }
        else {
            frontRightMotor.setPower(0);
            frontLeftMotor.setPower(0);
            backRightMotor.setPower(0);
            backLeftMotor.setPower(0);
        }

        if (gamepad2.right_stick_y > 0.2) {
            wrist.setPosition(.0);
        }
        else if (gamepad2.right_stick_y < -0.2){
            wrist.setPosition(1);
        }
        if (gamepad2.left_stick_x > 0.2){
            leftClaw.setPosition(1);
            rightClaw.setPosition(0);
        } else if (gamepad2.left_stick_x < -0.2) {
            leftClaw.setPosition(.1);
            rightClaw.setPosition(.9);
        } else{
            leftClaw.setPosition(.5);
            rightClaw.setPosition(.5);
        }

        if (gamepad1.dpad_up) {
            elbow.setPower(.4);
        } else if (gamepad1.dpad_down) {
            elbow.setPower(-.3);
        } else {
            elbow.setPower(0);
        }
    }

}
