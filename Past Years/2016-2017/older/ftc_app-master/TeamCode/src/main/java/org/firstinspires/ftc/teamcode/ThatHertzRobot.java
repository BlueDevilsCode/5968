package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

/**
 * Created by Sa'id on 2/4/2017.
 */
public class ThatHertzRobot {

    public static double ultrasonicDifference = 0.0;

    //for I2C sensors
    public static DeviceInterfaceModule dim = null;

    //for color sensor
    public static ColorSensor color = null;
    static final int LED_CHANNEL = 5;
    float hsvValues[] = {0F, 0F, 0F};

    //for wheels
    public static DcMotor frontLeftMotor = null;
    public static DcMotor frontRightMotor = null;
    public static DcMotor backLeftMotor = null;
    public static DcMotor backRightMotor = null;

    //for lift
    public static DcMotor lift = null;
}
