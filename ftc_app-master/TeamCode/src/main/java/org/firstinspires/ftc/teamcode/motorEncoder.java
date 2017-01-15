package org.firstinspires.ftc.teamcode;

/**
 * Created by thomashan on 1/12/17.
 */
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.hardware.LegacyModule;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "[5968] Motor-Encoder", group = "Match-Ready")
public class motorEncoder extends LinearOpMode {
    ElapsedTime runtime = new ElapsedTime();

    //for legacy sensors
    private LegacyModule legacy = null;

    //for light sensor
    private LightSensor backLightSensor = null;
    private LightSensor frontLightSensor = null;
    private double GRAY_COLOR_CONSTANT_FRONT = 0.0;
    private double WHITE_COLOR_CONSTANT_FRONT = 0.0;
    private double GRAY_COLOR_CONSTANT_BACK = 0.0;
    private double WHITE_COLOR_CONSTANT_BACK = 0.0;

    //for ultrasonic sensors
    private UltrasonicSensor ultrasonicLeft = null;
    //    private UltrasonicSensor ultrasonicRight = null;
    private double ultrasonicDifference = 0.0;

    //for I2C sensors
    private DeviceInterfaceModule dim = null;

    //for color sensor
    private ColorSensor color = null;
    static final int LED_CHANNEL = 5;
    static final int HELPER_LED_CHANNEL = 4;
    float hsvValues[] = {0F, 0F, 0F};

    //for wheels
    private DcMotor frontLeftMotor = null;
    private DcMotor frontRightMotor = null;
    private DcMotor backLeftMotor = null;
    private DcMotor backRightMotor = null;

    private Servo servoRight = null;
    private Servo servoLeft = null;
    private boolean servosFixed = false;

    //for autonomous loop
    private boolean foundBack = false;
    private boolean foundFront = false;
    private boolean hitBeaconOne = false;
    private boolean choseColor = false;
    private boolean ballHit = false;
    private boolean forward = false;
    private boolean jerked = false;
    private boolean done = false;

    public void runOpMode(){
        ultrasonicLeft = hardwareMap.ultrasonicSensor.get("ultrasonic_l");
//        ultrasonicRight = hardwareMap.ultrasonicSensor.get("ultrasonic_r");
        legacy = hardwareMap.legacyModule.get("legacy");
        legacy.enable9v(4, true); //these two ports must have 9 volts
        legacy.enable9v(5, true); //to make sure that ultrasonics work

        //for DIM
        dim = hardwareMap.deviceInterfaceModule.get("d_i_m");

        //for color sensor
        color = hardwareMap.colorSensor.get("color");
        dim.setDigitalChannelMode(LED_CHANNEL, DigitalChannelController.Mode.OUTPUT);
        dim.setDigitalChannelMode(HELPER_LED_CHANNEL, DigitalChannelController.Mode.OUTPUT);

        //for light sensors
        backLightSensor = hardwareMap.lightSensor.get("b_light");
        frontLightSensor = hardwareMap.lightSensor.get("f_light");
        ultrasonicLeft = hardwareMap.ultrasonicSensor.get("ultrasonic_l");
//        ultrasonicRight = hardwareMap.ultrasonicSensor.get("ultrasonic_r");
        legacy = hardwareMap.legacyModule.get("legacy");
        legacy.enable9v(4, true); //these two ports must have 9 volts
        legacy.enable9v(5, true); //to make sure that ultrasonics work

        //for DIM
        dim = hardwareMap.deviceInterfaceModule.get("d_i_m");

        //for color sensor
        color = hardwareMap.colorSensor.get("color");
        dim.setDigitalChannelMode(LED_CHANNEL, DigitalChannelController.Mode.OUTPUT);
        dim.setDigitalChannelState(LED_CHANNEL, true);
        dim.setDigitalChannelState(HELPER_LED_CHANNEL, true);

        //for light sensors
        backLightSensor = hardwareMap.lightSensor.get("b_light");
        frontLightSensor = hardwareMap.lightSensor.get("f_light");

        //for wheels
        frontLeftMotor = hardwareMap.dcMotor.get("f_l_m");
        frontRightMotor = hardwareMap.dcMotor.get("f_r_m");
        backLeftMotor = hardwareMap.dcMotor.get("b_l_m");
        backRightMotor = hardwareMap.dcMotor.get("b_r_m");
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);



        //for servos
        servoRight = hardwareMap.servo.get("r_b_s");
        servoLeft = hardwareMap.servo.get("l_b_s");


        GRAY_COLOR_CONSTANT_FRONT = frontLightSensor.getRawLightDetected();
        WHITE_COLOR_CONSTANT_FRONT = GRAY_COLOR_CONSTANT_FRONT + .15;
        GRAY_COLOR_CONSTANT_BACK = backLightSensor.getRawLightDetected();
        WHITE_COLOR_CONSTANT_BACK = GRAY_COLOR_CONSTANT_BACK + .15;

        waitForStart();

        runtime.reset();

        while(opModeIsActive()){
            if (done == false) {

                frontLeftMotor.setPower(-1);
                frontLeftMotor.setTargetPosition(1680);

                frontRightMotor.setPower(1);
                frontRightMotor.setTargetPosition(1680);

                backLeftMotor.setPower(-1);
                backLeftMotor.setTargetPosition(1680);

                backRightMotor.setPower(1);
                backRightMotor.setTargetPosition(1680);
                done = true;
            }

        }


    }
}
