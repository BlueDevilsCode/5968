package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by westfield_robotics on 12/6/2017.
 */

@Autonomous(name = "ThatHertzAutonomous", group = "Autonomous")
public class ThatHertzAutonomous extends LinearOpMode {

    private enum STATE {LOWERCLAWS, MOVEOFFPLATE, STRAFERIGHT, MOVEFORWARD, DONE}

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

    private ColorSensor colorSensor;
    private DeviceInterfaceModule cdim;

    private TouchSensor touchSensor;

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

        colorSensor = hardwareMap.colorSensor.get("color");
        cdim = hardwareMap.deviceInterfaceModule.get("cdim");
        cdim.setDigitalChannelMode(5, DigitalChannel.Mode.OUTPUT);
        cdim.setDigitalChannelState(5, true);

        touchSensor = hardwareMap.touchSensor.get("touch");

        posDiagServos.setPosition(.5);
        negDiagServos.setPosition(.5);

        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);

        double initPos = 0;

        STATE state = STATE.LOWERCLAWS;

        waitForStart();

        while (opModeIsActive()) {
            switch (state) {
                case LOWERCLAWS:
                    if (touchSensor.isPressed()) {
                        wrist.setPower(0);
                        state = STATE.MOVEOFFPLATE;
                        break;
                    }
                    wrist.setPower(1);
                    break;
                case MOVEOFFPLATE:
                    if (colorSensor.blue() < blueLowThreshhold) {
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
                    if (frontLeftMotor.getCurrentPosition() >= initPos + 805) { //could be a problem: motor running backwards (negative), probably not though
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
                case MOVEFORWARD: //this could be problematic, apparently we're already like right in front of the scoring region
                    if (frontLeftMotor.getCurrentPosition() >= initPos + 1075) { //was giving me flak about not having initPos initialized while the other usage was not: could be a problem down the line...
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
