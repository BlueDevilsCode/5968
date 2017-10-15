package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Sa'id on 10/14/2017.
 */

@TeleOp(name = "ThatHertzSwerveServoTest")
public class ThatHertzSwerveServoTest extends OpMode{
    private Servo frontRightServo;
    private Servo frontLeftServo;
    private Servo backRightServo;
    private Servo backLeftServo;

    @Override
    public void init() {
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
        try {
            frontRightServo.setPosition(90);
            Thread.sleep(1000);
            frontLeftServo.setPosition(90);
            Thread.sleep(1000);
            backRightServo.setPosition(90);
            Thread.sleep(1000);
            backLeftServo.setPosition(90);
            Thread.sleep(1000);

            frontRightServo.setPosition(180);
            Thread.sleep(1000);
            frontLeftServo.setPosition(180);
            Thread.sleep(1000);
            backRightServo.setPosition(180);
            Thread.sleep(1000);
            backLeftServo.setPosition(180);
            Thread.sleep(1000);

            frontRightServo.setPosition(0);
            Thread.sleep(1000);
            frontLeftServo.setPosition(0);
            Thread.sleep(1000);
            backRightServo.setPosition(0);
            Thread.sleep(1000);
            backLeftServo.setPosition(0);
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
    }
}
