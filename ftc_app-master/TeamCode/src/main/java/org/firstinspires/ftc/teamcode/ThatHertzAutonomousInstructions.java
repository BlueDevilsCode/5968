package org.firstinspires.ftc.teamcode;

/**
 * Created by Sa'id on 2/4/2017.
 */

import org.firstinspires.ftc.teamcode.ThatHertzRobot;

public class ThatHertzAutonomousInstructions {


    public boolean findStripeFront() {
        if (ThatHertzRobot.frontLightSensor.getRawLightDetected() >= ) {
            ThatHertzRobot.frontRightMotor.setPower(0);
            ThatHertzRobot.backRightMotor.setPower(0);
            ThatHertzRobot.backLeftMotor.setPower(0);
            ThatHertzRobot.backRightMotor.setPower(0);
            return true;
        } else {
            ThatHertzRobot.backLeftMotor.setPower(-.07);
            ThatHertzRobot.backRightMotor.setPower(-.07);
            ThatHertzRobot.frontLeftMotor.setPower(-.07);
            ThatHertzRobot.frontRightMotor.setPower(-.07);
        }
        return false;
    }

    public boolean findStripeBack() {
        if (ThatHertzRobot.backLightSensor.getRawLightDetected() >=) {
            ThatHertzRobot.backLeftMotor.setPower(0);
            ThatHertzRobot.backRightMotor.setPower(0);
            ThatHertzRobot.frontLeftMotor.setPower(0);
            ThatHertzRobot.frontRightMotor.setPower(0);
            return true;
        } else {
            ThatHertzRobot.frontRightMotor.setPower(.13);
            ThatHertzRobot.backRightMotor.setPower(.13);
            ThatHertzRobot.backLeftMotor.setPower(-.13);
            ThatHertzRobot.frontLeftMotor.setPower(-.13);
        }
        return false;
    }

    public boolean findColor() {
        if(ThatHertzRobot.color.red() + 100 < ThatHertzRobot.color.blue()) {
//            servoRight.setPosition(.75);
//            servoLeft.setPosition(0);
        }
        else {
//            servoLeft.setPosition(.75);
//            servoRight.setPosition(0);
        }
        return true;
    }

    public boolean hitBeacon() {

        if (ultrasonicLeft.getUltrasonicLevel() > 18) {
            ThatHertzRobot.frontRightMotor.setPower(-.1);
            ThatHertzRobot.frontLeftMotor.setPower(-.1);
            ThatHertzRobot.backLeftMotor.setPower(-.1);
            ThatHertzRobot.backRightMotor.setPower(-.1);
            servoRight.setPosition(1);
            servoLeft.setPosition(0.8);
        }
        if (ultrasonicLeft.getUltrasonicLevel() <= 18) {
            ThatHertzRobot.frontRightMotor.setPower(0);
            ThatHertzRobot.frontLeftMotor.setPower(0);
            ThatHertzRobot.backLeftMotor.setPower(0);
            ThatHertzRobot.backLeftMotor.setPower(0);
            return true;
        }
        return false;
    }

    public boolean hitBall() {
        if() {
            ThatHertzRobot.backLeftMotor.setPower(.3);
            ThatHertzRobot.backRightMotor.setPower(.3);
            ThatHertzRobot.frontLeftMotor.setPower(.3);
            ThatHertzRobot.frontRightMotor.setPower(.3);
        }
        if() {
            ThatHertzRobot.backLeftMotor.setPower(0);
            ThatHertzRobot.backRightMotor.setPower(0);
            ThatHertzRobot.frontLeftMotor.setPower(0);
            ThatHertzRobot.frontRightMotor.setPower(0);
            return true;
        }

        return false;
    }
}
