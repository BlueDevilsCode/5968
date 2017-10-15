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

    private Servo frontRightServo;
    private Servo frontLeftServo;
    private Servo backRightServo;
    private Servo backLeftServo;

    @Override
    public void init() {
        frontRightMotor = hardwareMap.dcMotor.get("frMotor");
        frontLeftMotor = hardwareMap.dcMotor.get("flMotor");
        backRightMotor = hardwareMap.dcMotor.get("brMotor");
        backLeftMotor = hardwareMap.dcMotor.get("blMotor");

        frontRightServo = hardwareMap.servo.get("frServo");
        frontLeftServo = hardwareMap.servo.get("flServo");
        backRightServo = hardwareMap.servo.get("brServo");
        backLeftServo = hardwareMap.servo.get("blServo");

        frontRightServo.setPosition(0);
        frontLeftServo.setPosition(0);
        backRightServo.setPosition(0);
        backLeftServo.setPosition(0);
    }

    @Override
    public void loop() {
        if (gamepad1.right_stick_x >= 0) {
            frontRightServo.setPosition(90);
            frontLeftServo.setPosition(90);
            backRightServo.setPosition(90);
            backLeftServo.setPosition(90);

            frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);

            frontRightMotor.setPower(.5);
            frontLeftMotor.setPower(.5);
            backRightMotor.setPower(.5);
            backLeftMotor.setPower(.5);
        }

        if (gamepad1.right_stick_x <= 0) {
            frontRightServo.setPosition(90);
            frontLeftServo.setPosition(90);
            backRightServo.setPosition(90);
            backLeftServo.setPosition(90);

            frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

            frontRightMotor.setPower(.5);
            frontLeftMotor.setPower(.5);
            backRightMotor.setPower(.5);
            backLeftMotor.setPower(.5);
        }

        if (gamepad1.right_stick_y <= 0) {
            frontRightServo.setPosition(0);
            frontLeftServo.setPosition(0);
            backRightServo.setPosition(0);
            backLeftServo.setPosition(0);

            frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

            frontRightMotor.setPower(.5);
            frontLeftMotor.setPower(.5);
            backRightMotor.setPower(.5);
            backLeftMotor.setPower(.5);
        }

        if (gamepad1.right_stick_y >= 0) {
            frontRightServo.setPosition(90);
            frontLeftServo.setPosition(90);
            backRightServo.setPosition(90);
            backLeftServo.setPosition(90);

            frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);

            frontRightMotor.setPower(.5);
            frontLeftMotor.setPower(.5);
            backRightMotor.setPower(.5);
            backLeftMotor.setPower(.5);
        }
    }
}