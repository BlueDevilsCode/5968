package org.firstinspires.ftc.teamcode.old;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by westfield_robotics on 12/16/2017.
 */

@Disabled
@TeleOp(name = "ThatHertzArmTest", group = "Test")
public class ThatHertzArmTest extends OpMode {

    private DcMotor elbow;
    private Servo rightClaw;
    private Servo leftClaw;
    private CRServo wrist;

    private double elbowPower = 0.3;
    private double pastPos;
    private double currV;
    private double timeInit;

    public enum ALG {DELTAPOS, SCALING}
    public ALG currAlg = ALG.DELTAPOS;

    @Override
    public void init() {
        elbow = hardwareMap.dcMotor.get("elbow");
        rightClaw = hardwareMap.servo.get("rClaw");
        leftClaw = hardwareMap.servo.get("lClaw");
        wrist = hardwareMap.crservo.get("wrist");

        rightClaw.setPosition(.5);
        leftClaw.setPosition(.5);
    }

    @Override
    public void loop() {
        switch (currAlg) {
            case DELTAPOS:
                if (gamepad2.right_stick_y < -0.1) {
                    double goalV = 450;
                    pastPos = clipPosition(elbow.getCurrentPosition());
                    elbow.setPower(elbowPower);
                    timeInit = System.nanoTime();
                    currV = (clipPosition(elbow.getCurrentPosition()) - pastPos) / ((System.nanoTime() - timeInit) * Math.pow(10, -9));
                    if (currV < goalV) {
                        elbowPower += 0.02;
                    } else if (currV > goalV) {
                        elbowPower -= 0.02;
                    }
                } else if (gamepad2.right_stick_y > 0.1) {
                    double goalV = -450;
                    pastPos = clipPosition(elbow.getCurrentPosition());
                    elbow.setPower(-elbowPower);
                    timeInit = System.nanoTime();
                    currV = (clipPosition(elbow.getCurrentPosition()) - pastPos) / ((System.nanoTime() - timeInit) * Math.pow(10, -9));
                    if (currV > goalV) {
                        elbowPower += .02;
                    } else if (currV < goalV) {
                        elbowPower -= 0.02;
                    }
                } else {
                    elbow.setPower(0);
                }
                if (gamepad2.a) {
                    currAlg = ALG.SCALING;
                }
                break;
            case SCALING:
                if (gamepad2.right_stick_y < -.1) {
                    double scalingFactor = (1680 - clipPosition(elbow.getCurrentPosition())) / (1680);
                    if (scalingFactor <= (2/10)) {
                        elbow.setPower(.2);
                    } else {
                        elbow.setPower(Math.abs(scalingFactor) * 1);
                    }
                    elbow.setPower(Math.abs(scalingFactor) * .6);
                } else if (gamepad2.right_stick_y > .1) {
                    double scalingFactor = Math.abs(clipPosition(elbow.getCurrentPosition()) / 1680);
                    if (scalingFactor <= (2/10)) {
                        elbow.setPower(-.2);
                    } else {
                        elbow.setPower(scalingFactor * -1);
                    }
                }
                else {
                    elbow.setPower(0);
                }
                if (gamepad2.a) {
                    currAlg = ALG.DELTAPOS;
                }
                break;
        }
        telemetry.addData("elbowPower", elbow.getPower());
        telemetry.addData("currTime", System.nanoTime() * Math.pow(10, -9));
        telemetry.addData("dTime", (System.nanoTime() * Math.pow(10, -9)) - (timeInit * Math.pow(10, -9)));
        telemetry.addData("pastPos", pastPos);
        telemetry.addData("currPos", elbow.getCurrentPosition());
        telemetry.addData("dPos", elbow.getCurrentPosition() - pastPos);
        telemetry.addData("currV", currV);
        telemetry.addData("currPower", elbow.getPower());
        telemetry.update();
    }

    public static int clipPosition(int currPos)
    {
        if (currPos < 0) {
            return 0;
        } else if(currPos > 1680) {
            return 1680;
        } else {
            return currPos;
        }
    }
}