package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.ThatHertzRobot;
import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;


/**
 * Created by Sa'id on 11/19/2016.
 */

@Autonomous(name = "[5968] ThatHertz Autonomous RED", group = "Match-Ready")
public class ThatHertzAutonomousRED extends LinearOpMode {

    private boolean servosFixed = false;

    //for autonomous loop
    private boolean foundBack = false;
    private boolean foundFront = false;
    private boolean hitBeaconOne = false;
    private boolean choseColor = false;
    private boolean ballHit = false;

    private String className = "org.firstinspires.ftc.teamcode.ThatHertzAutonomousInstructions";
    private ArrayList<String> methods = new ArrayList<String>(5){{add("findStripeFront"); add("findStripeBack"); add("findColor"); add("hitBeacon"); add("hitBall");}};
    private Class<?> instrucs;
    private Object instanceInstrucs;

    public void createClass(){
        try {instrucs = Class.forName(className);} catch(Exception e){}
        try {instanceInstrucs = instrucs.newInstance();} catch(Exception e){}
        System.out.println("done");
    }
    createClass();


    public void runOpMode() {
        //for ultrasonic sensors
//        ultrasonicLeft = hardwareMap.ultrasonicSensor.get("ultrasonic_l");
//        ultrasonicRight = hardwareMap.ultrasonicSensor.get("ultrasonic_r");
//        legacy = hardwareMap.legacyModule.get("legacy");
//        legacy.enable9v(4, true); //these two ports must have 9 volts
//        legacy.enable9v(5, true); //to make sure that ultrasonics work

        //for DIM
        ThatHertzRobot.dim = hardwareMap.deviceInterfaceModule.get("d_i_m");

        //for color sensor
        ThatHertzRobot.color = hardwareMap.colorSensor.get("color");
        ThatHertzRobot.dim.setDigitalChannelMode(LED_CHANNEL, DigitalChannelController.Mode.OUTPUT);

        //for DIM
        ThatHertzRobot.dim = hardwareMap.deviceInterfaceModule.get("d_i_m");

        //for color sensor
        ThatHertzRobot.color = hardwareMap.colorSensor.get("color");
        ThatHertzRobot.dim.setDigitalChannelMode(LED_CHANNEL, DigitalChannelController.Mode.OUTPUT);
        ThatHertzRobot.dim.setDigitalChannelState(LED_CHANNEL, true);

        //for wheels
        ThatHertzRobot.frontLeftMotor = hardwareMap.dcMotor.get("f_l_m");
        ThatHertzRobot.frontRightMotor = hardwareMap.dcMotor.get("f_r_m");
        ThatHertzRobot.backLeftMotor = hardwareMap.dcMotor.get("b_l_m");
        ThatHertzRobot.backRightMotor = hardwareMap.dcMotor.get("b_r_m");
        ThatHertzRobot.frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        ThatHertzRobot.backLeftMotor.setDirection(DcMotor.Direction.REVERSE);

        //for servos

//        GRAY_COLOR_CONSTANT_FRONT = frontLightSensor.getRawLightDetected();
//        WHITE_COLOR_CONSTANT_FRONT = GRAY_COLOR_CONSTANT_FRONT + .15;
//        GRAY_COLOR_CONSTANT_BACK = backLightSensor.getRawLightDetected();
//        WHITE_COLOR_CONSTANT_BACK = GRAY_COLOR_CONSTANT_BACK + .15;

        waitForStart();

        while(opModeIsActive()) {
            if(methods.size() > 0)
            {
                String method = methods.get(0);

            }

//            if(!servosFixed) {
//                servoLeft.setPosition(0);
//                servoRight.setPosition(1);
//            }
//            if (!foundFront) {
//                foundFront = findStripeFront();
//            }
//            if (foundFront && !choseColor) {
//                choseColor = findColor();
//            }
//            if(foundFront && choseColor && !foundBack) {
//                foundBack = findStripeBack();
//            }
//            if (foundFront && foundBack && choseColor && !hitBeaconOne) {
//                hitBeaconOne = hitBeacon();
//            }
//            if(foundFront && foundBack && choseColor && hitBeaconOne && !hitBall()) {
//                ballHit = hitBall();
//            }

            telemetry.addData("Back Light: Raw", backLightSensor.getRawLightDetected());
            telemetry.addData("Back Light: Normal", backLightSensor.getLightDetected());
            telemetry.addData("Front Light: Raw", frontLightSensor.getRawLightDetected());
            telemetry.addData("Front Light: Normal", frontLightSensor.getLightDetected());
            telemetry.addData("Color: Clear", color.alpha());
            telemetry.addData("Color: Red  ", color.red());
            telemetry.addData("Color: Green", color.green());
            telemetry.addData("Color: Blue ", color.blue());
            telemetry.addData("Ultrasonic Sensor Left", ultrasonicLeft.getUltrasonicLevel() + "");
    //        telemetry.addData("Ultrasonic Sensor Right", ultrasonicRight.getUltrasonicLevel() + "");
            telemetry.addData("Ultrasonic Difference", ultrasonicDifference + "");
            telemetry.update();
        }
    }
}
