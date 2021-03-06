package org.firstinspires.ftc.teamcode.old;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by westfield_robotics on 11/12/2017.
 */

@Disabled
@TeleOp(name = "ThatHertzTCTeleOp", group = "TeleOp")
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
    private CRServo wrist;

    private double elbowPower = 0.3;
    private double pastPos;
    private double currV;
    private double timeInit;

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
        wrist = hardwareMap.crservo.get("wrist");

        posDiagServos.setPosition(.5);
        negDiagServos.setPosition(.5);

        rightClaw.setPosition(.5);
        leftClaw.setPosition(.5);
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        //MOVEMENT CONTROLS | GAMEPAD1\\
        if (gamepad1.right_stick_y < -.2) {
            posDiagServos.setPosition(.5);
            negDiagServos.setPosition(.5);

            frontRightMotor.setPower(-.5 * gamepad1.right_stick_y);
            frontLeftMotor.setPower(-.5 * gamepad1.right_stick_y);
            backRightMotor.setPower(-.5 * gamepad1.right_stick_y);
            backLeftMotor.setPower(-.5 * gamepad1.right_stick_y);
        } else if (gamepad1.right_stick_y > .2) {
            posDiagServos.setPosition(.5);
            negDiagServos.setPosition(.5);

            frontRightMotor.setPower(-.5 * gamepad1.right_stick_y);
            frontLeftMotor.setPower(-.5 * gamepad1.right_stick_y);
            backRightMotor.setPower(-.5 * gamepad1.right_stick_y);
            backLeftMotor.setPower(-.5 * gamepad1.right_stick_y);
        } else if (gamepad1.right_stick_x > .2) {
            posDiagServos.setPosition(.5 + (.5 * (2.0 / 3.0)));
            negDiagServos.setPosition(.5 - (.5 * (2.0 / 3.0)));

            frontRightMotor.setPower(-.5 * gamepad1.right_stick_x);
            frontLeftMotor.setPower(.5 * gamepad1.right_stick_x);
            backRightMotor.setPower(.5 * gamepad1.right_stick_x);
            backLeftMotor.setPower(-.5 * gamepad1.right_stick_x);
        } else if (gamepad1.right_stick_x < -.2) {
            posDiagServos.setPosition(.5 + (.5 * (2.0 / 3.0)));
            negDiagServos.setPosition(.5 - (.5 * (2.0 / 3.0)));

            frontRightMotor.setPower(-.5 * gamepad1.right_stick_x);
            frontLeftMotor.setPower(.5 * gamepad1.right_stick_x);
            backRightMotor.setPower(.5 * gamepad1.right_stick_x);
            backLeftMotor.setPower(-.5 * gamepad1.right_stick_x);
        }
        else if (gamepad1.right_bumper){
            posDiagServos.setPosition(.5);
            negDiagServos.setPosition(.5);

            frontRightMotor.setPower(-.5);
            frontLeftMotor.setPower(.5);
            backRightMotor.setPower(-.5);
            backLeftMotor.setPower(.5);
        }
        else if (gamepad1.left_bumper){
            posDiagServos.setPosition(.5);
            negDiagServos.setPosition(.5);

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

        //ARM CONTROLS | GAMEPAD2\\
        if (gamepad2.right_trigger > 0) {
            wrist.setPower(1);
        }
        else if (gamepad2.left_trigger > 0) {
            wrist.setPower(-1);
        }
        else {
            wrist.setPower(0);
        }

        if (gamepad2.a) {
            leftClaw.setPosition(1);
            rightClaw.setPosition(0);
        } else if (gamepad2.b) {
            leftClaw.setPosition(.3);
            rightClaw.setPosition(.7);
        } else if (gamepad2.y) {
            leftClaw.setPosition(.5);
            rightClaw.setPosition(.5);
        }

        if (gamepad2.right_stick_y < -0.1) {
            double goalV = 280;
            pastPos = elbow.getCurrentPosition();
            elbow.setPower(elbowPower);
            timeInit = System.nanoTime();
            currV = (elbow.getCurrentPosition() - pastPos) / ((System.nanoTime() - timeInit) * Math.pow(10, -9));
            if (currV < goalV) {
                elbowPower += 0.05;
            } else if (currV > goalV) {
                elbowPower -= 0.05;
            }
        } else if (gamepad2.right_stick_y > 0.1) {
            double goalV = -280;
            pastPos = elbow.getCurrentPosition();
            elbow.setPower(-elbowPower);
            timeInit = System.nanoTime();
            currV = (elbow.getCurrentPosition() - pastPos) / ((System.nanoTime() - timeInit) * Math.pow(10, -9));
            if (currV > goalV) {
                elbowPower += 0.05;
            } else if (currV < goalV) {
                elbowPower -= 0.05;
            }
        } else {
            elbow.setPower(0);
        }

        telemetry.addData("elbowPower", elbow.getPower());
        telemetry.addData("currTime", System.nanoTime() * Math.pow(10, -9));
        telemetry.addData("dTime", (System.nanoTime() * Math.pow(10, -9)) - (timeInit * Math.pow(10, -9)));
        telemetry.addData("pastPos", pastPos);
        telemetry.addData("currPos", elbow.getCurrentPosition());
        telemetry.addData("dPos", elbow.getCurrentPosition() - pastPos);
        telemetry.addData("currV", currV);
        telemetry.update();
    }

}
