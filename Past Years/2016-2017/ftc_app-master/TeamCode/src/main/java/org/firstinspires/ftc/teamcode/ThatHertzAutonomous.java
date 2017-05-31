package org.firstinspires.ftc.teamcode;

import java.lang.reflect.Method;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.LegacyModule;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/**
 * Created by Sa'id on 12/1/2016.
 */

@Disabled
@Autonomous(name = "[5968] ThatHertz Autonomous", group = "Autonomous")

public class ThatHertzAutonomous extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();

    //for legacy sensors
    private LegacyModule legacy = null;

    //for light sensor
    private LightSensor backLightSensor = null;
    private LightSensor frontLightSensor = null;

    //for ultrasonic sensors
    private UltrasonicSensor ultrasonicLeft = null;
    private UltrasonicSensor ultrasonicRight = null;
    private double ultrasonicDifference = 0.0;

    //for I2C sensors
    private DeviceInterfaceModule dim = null;

    //for color sensor
    private ColorSensor color = null;
    static final int LED_CHANNEL = 5;
    float hsvValues[] = {0F,0F,0F};

    //for wheels
    private DcMotor frontLeftMotor = null;
    private DcMotor frontRightMotor = null;
    private DcMotor backLeftMotor = null;
    private DcMotor backRightMotor = null;

    //for collection
    private DcMotor collection = null;

    //for beacon scoring
    private Servo rightBeaconServo = null;
    private Servo leftBeaconServo = null;

    private String CURRENT_DIRECTORY = "org.firstinspires.ftc.teamcode";
//    private String OP_QUE[] = {
//                              };

    @Override
    public void init() {

    }


    @Override
    public void start() {

    }


    @Override
    public void loop() {

    }
}
