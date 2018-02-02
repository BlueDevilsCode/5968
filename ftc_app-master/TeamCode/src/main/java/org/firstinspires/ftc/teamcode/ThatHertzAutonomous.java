package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Sa'id on 1/21/2018.
 */

@Autonomous(name = "ThatHertzAutonomous", group = "Autonomous")
public class ThatHertzAutonomous extends LinearOpMode {
    private enum STATE {KNOCKJEWEL, MOVEOFF, MOVERIGHT, MOVEFORWARD, DRIVECOLLECTOR}

    private DcMotor frontRightMotor;
    private DcMotor frontLeftMotor;
    private DcMotor backRightMotor;
    private DcMotor backLeftMotor;

    private Servo frontRightServo;
    private Servo backRightServo;
    private Servo frontLeftServo;
    private Servo backLeftServo;

    private DcMotor collector;

    private Servo jewelFlipper;

    private ColorSensor colorSensor;
    private ColorSensor jewelColorSensor;
    private DeviceInterfaceModule cdim;

    private STATE state;

    private double motorPos;

    @Override
    public void runOpMode() throws InterruptedException {
        frontRightMotor = hardwareMap.dcMotor.get("frMotor");
        frontLeftMotor = hardwareMap.dcMotor.get("flMotor");
        backRightMotor = hardwareMap.dcMotor.get("brMotor");
        backLeftMotor = hardwareMap.dcMotor.get("blMotor");

        frontRightServo = hardwareMap.servo.get("frServo");
        backRightServo = hardwareMap.servo.get("brServo");
        frontLeftServo = hardwareMap.servo.get("flServo");
        backLeftServo = hardwareMap.servo.get("blServo");

        frontRightServo.setPosition(0.5);
        backRightServo.setPosition(0.5);
        frontLeftServo.setPosition(0.5);
        backLeftServo.setPosition(0.5);

        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);

        collector = hardwareMap.dcMotor.get("collector");

        jewelFlipper.setPosition(0);

        colorSensor = hardwareMap.colorSensor.get("color");
        jewelColorSensor = hardwareMap.colorSensor.get("jColor");
        cdim = hardwareMap.deviceInterfaceModule.get("cdim");

        waitForStart();

        double blue = colorSensor.blue();

        while(opModeIsActive()) {
            switch (state) {
                case KNOCKJEWEL:
                    jewelFlipper.setPosition(1);

                    if (jewelFlipper.getPosition() == 1) {
                        if (jewelColorSensor.blue() > jewelColorSensor.red()) {
                            if (backRightMotor.getCurrentPosition() < 180) {
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
                            jewelFlipper.setPosition(0);
                            if (backRightMotor.getCurrentPosition() < 360) {
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
                            } else {
                                if (backRightMotor.getCurrentPosition() < 180) {
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
                                jewelFlipper.setPosition(0);
                                if (backRightMotor.getCurrentPosition() < 360) {
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
                        }
                    }
                    break;
                case MOVEOFF:
                    if (colorSensor.blue() >= (blue * .75) ) {
                        frontRightServo.setPosition(.5 - (.5 * (1.0 / 2.0)));
                        backRightServo.setPosition(.5 + (.5 * (1.0 / 2.0)));
                        frontLeftServo.setPosition(.5 + (.5 * (1.0 / 2.0)));
                        backLeftServo.setPosition(.5 - (.5 * (1.0 / 2.0)));

                        frontRightMotor.setPower(-.5);
                        frontLeftMotor.setPower(.5);
                        backRightMotor.setPower(.5);
                        backLeftMotor.setPower(-.5);
                    }
                    motorPos = backRightMotor.getCurrentPosition();
                case MOVERIGHT:
                    if (frontRightMotor.getCurrentPosition() < motorPos + 840) {
                        frontRightServo.setPosition(.5 - (.5 * (1.0 / 2.0)));
                        backRightServo.setPosition(.5 + (.5 * (1.0 / 2.0)));
                        frontLeftServo.setPosition(.5 + (.5 * (1.0 / 2.0)));
                        backLeftServo.setPosition(.5 - (.5 * (1.0 / 2.0)));

                        frontRightMotor.setPower(-.5);
                        frontLeftMotor.setPower(.5);
                        backRightMotor.setPower(.5);
                        backLeftMotor.setPower(-.5);
                    } else {
                        frontRightServo.setPosition(.5);
                        backRightServo.setPosition(.5);
                        frontLeftServo.setPosition(.5);
                        backLeftServo.setPosition(.5);

                        frontRightMotor.setPower(0);
                        frontLeftMotor.setPower(0);
                        backRightMotor.setPower(0);
                        backLeftMotor.setPower(0);
                    }
                    break;
                case MOVEFORWARD:
                    if (frontRightMotor.getCurrentPosition() < motorPos + 1110) {
                        frontRightServo.setPosition(0.5);
                        backRightServo.setPosition(0.5);
                        frontLeftServo.setPosition(0.5);
                        backLeftServo.setPosition(0.5);

                        frontRightMotor.setPower(.5);
                        frontLeftMotor.setPower(.5);
                        backRightMotor.setPower(.5);
                        backLeftMotor.setPower(.5);
                    }
                    frontRightServo.setPosition(0.5);
                    backRightServo.setPosition(0.5);
                    frontLeftServo.setPosition(0.5);
                    backLeftServo.setPosition(0.5);

                    frontRightMotor.setPower(0);
                    frontLeftMotor.setPower(0);
                    backRightMotor.setPower(0);
                    backLeftMotor.setPower(0);
                    break;
                case DRIVECOLLECTOR:
                    collector.setPower(-1);
                    break;
            }
        }
    }
}
