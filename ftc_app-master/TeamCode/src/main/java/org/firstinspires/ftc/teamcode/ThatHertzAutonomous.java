package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by westfield_robotics on 12/6/2017.
 */

public class ThatHertzAutonomous extends LinearOpMode {

    private final double ppr = 1680;

    private enum STATE {ONPLATE, OFFPLATE, DONE}
    private enum GREYTHRESHHOLD {
        HIGH(), LOW();

        private double colorValue;
        GREYTHRESHHOLD(double colorValuePar) {
            this.colorValue = colorValuePar;
        }
    }

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

    STATE state = STATE.ONPLATE;

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

        posDiagServos.setPosition(.5);
        negDiagServos.setPosition(.5);

        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while(opModeIsActive()) {
            switch (state) {
                case ONPLATE:
                    if (colorSensor.argb() < GREYTHRESHHOLD.HIGH.colorValue || colorSensor.argb() > GREYTHRESHHOLD.LOW.colorValue) {
                        
                    }
                case OFFPLATE:

                    break;
                case DONE:

                    break;
            }
        }
    }
}
