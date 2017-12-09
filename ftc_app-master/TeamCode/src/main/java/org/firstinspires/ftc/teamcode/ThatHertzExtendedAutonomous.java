package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by Sa'id on 12/9/2017.
 */

@Autonomous(name = "ThatHertzExtendedAutonomous", group = "Autonomous")
@Disabled
public class ThatHertzExtendedAutonomous extends LinearOpMode {

    private enum STATE {LOWERKNOCKER, BLUEFOUND, REDFOUND, LOWERARM, MOVEOFFPLATE, STRAFERIGHT, MOVEFORWARD, DONE}
    private final double blueLowThreshhold = 0.0; //placeholder

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

    private Servo jewelKnocker;

    private ColorSensorMultiplexer multiplexer;
    private DeviceInterfaceModule cdim;

    private TouchSensor touchSensor;

    private double initPos;
    private int[] ports = {0, 3};

    @Override
    public void runOpMode() {

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

        jewelKnocker = hardwareMap.servo.get("jewel");

        multiplexer = new ColorSensorMultiplexer(hardwareMap, "multiplexer", "color", ports, 48, ColorSensorMultiplexer.GAIN_16X);
        cdim = hardwareMap.deviceInterfaceModule.get("cdim");
        cdim.setDigitalChannelMode(5, DigitalChannel.Mode.OUTPUT);
        cdim.setDigitalChannelState(5, true);
        cdim.setDigitalChannelMode(4, DigitalChannel.Mode.OUTPUT);
        cdim.setDigitalChannelState(4, true);

        touchSensor = hardwareMap.touchSensor.get("touch");

        posDiagServos.setPosition(.5);
        negDiagServos.setPosition(.5);
        jewelKnocker.setPosition(0);

        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);

        STATE state = STATE.LOWERARM;

        waitForStart();

        multiplexer.startPolling();

        while (opModeIsActive()) {
            switch (state) {
                case LOWERKNOCKER:
                    jewelKnocker.setPosition(1);
                    if (multiplexer.getCRGB(0)[1] < multiplexer.getCRGB(0)[3]) {
                        state = STATE.BLUEFOUND;
                    } else {
                        state = STATE.REDFOUND;
                    }
                    break;
                case BLUEFOUND:
                    if (Math.abs(frontLeftMotor.getCurrentPosition()) > 135) {
                        frontRightMotor.setPower(0);
                        frontLeftMotor.setPower(0);
                        backRightMotor.setPower(0);
                        backLeftMotor.setPower(0);
                        jewelKnocker.setPosition(0);
                        state = STATE.LOWERARM;
                        break;
                    }
                    frontRightMotor.setPower(-.3);
                    frontLeftMotor.setPower(-.3);
                    backRightMotor.setPower(-.3);
                    backLeftMotor.setPower(-.3);
                    break;
                case REDFOUND:
                    if (Math.abs(frontLeftMotor.getCurrentPosition()) > 135) {
                        frontRightMotor.setPower(0);
                        frontLeftMotor.setPower(0);
                        backRightMotor.setPower(0);
                        backLeftMotor.setPower(0);
                        jewelKnocker.setPosition(0);
                        state = STATE.LOWERARM;
                        break;
                    }
                    frontRightMotor.setPower(.3);
                    frontLeftMotor.setPower(.3);
                    backRightMotor.setPower(.3);
                    backLeftMotor.setPower(.3);
                    break;
                case LOWERARM:
                    if (touchSensor.isPressed()) {
                        wrist.setPower(0);
                        state = STATE.MOVEOFFPLATE;
                        break;
                    }
                    wrist.setPower(1);
                    break;
                case MOVEOFFPLATE:
                    if (multiplexer.getCRGB(3)[3] < blueLowThreshhold) {
                        frontRightMotor.setPower(0);
                        frontLeftMotor.setPower(0);
                        backRightMotor.setPower(0);
                        backLeftMotor.setPower(0);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {}
                        initPos = frontLeftMotor.getCurrentPosition();
                        state = STATE.STRAFERIGHT;
                        break;
                    }
                    posDiagServos.setPosition(.5);
                    negDiagServos.setPosition(.5);

                    frontRightMotor.setPower(.3);
                    frontLeftMotor.setPower(.3);
                    backRightMotor.setPower(.3);
                    backLeftMotor.setPower(.3);
                    break;
                case STRAFERIGHT:
                    if (frontLeftMotor.getCurrentPosition() >= initPos + 805) {
                        frontRightMotor.setPower(0);
                        frontLeftMotor.setPower(0);
                        backRightMotor.setPower(0);
                        backLeftMotor.setPower(0);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {}
                        state = STATE.MOVEFORWARD;
                        break;
                    }
                    posDiagServos.setPosition(.5 + (.5 * (2.0 / 3.0)));
                    negDiagServos.setPosition(.5 - (.5 * (2.0 / 3.0)));

                    frontRightMotor.setPower(-.3);
                    frontLeftMotor.setPower(.3);
                    backRightMotor.setPower(.3);
                    backLeftMotor.setPower(-.3);
                    break;
                case MOVEFORWARD:
                    if (frontLeftMotor.getCurrentPosition() >= initPos +  1610) {
                        state = STATE.DONE;
                        break;
                    }
                    posDiagServos.setPosition(.5);
                    negDiagServos.setPosition(.5);

                    frontRightMotor.setPower(.3);
                    frontLeftMotor.setPower(.3);
                    backRightMotor.setPower(.3);
                    backLeftMotor.setPower(.3);
                    break;
                case DONE:
                    frontRightMotor.setPower(0);
                    frontLeftMotor.setPower(0);
                    backRightMotor.setPower(0);
                    backLeftMotor.setPower(0);
                    break;
            }
        }
    }
}
