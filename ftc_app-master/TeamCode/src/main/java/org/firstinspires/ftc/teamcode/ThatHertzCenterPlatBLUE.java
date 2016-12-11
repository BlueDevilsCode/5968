package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
@Autonomous(name = "[5968] ThatHertz CenterPlat BLUE", group = "TeleOp")
public class ThatHertzCenterPlatBLUE extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();

    //for wheels
    private DcMotor frontLeftMotor = null;
    private DcMotor frontRightMotor = null;
    private DcMotor backLeftMotor = null;
    private DcMotor backRightMotor = null;
    //private UltrasonicSensor ultrasonicLeft = null;
    private UltrasonicSensor ultrasonicRight = null;
    //for collection sweeper
    private DcMotor collection = null;
    boolean run = true;

    //for beacon scoring
    //private Servo rightBeaconServo = null;
    //private Servo leftBeaconServo = null;

    //code to run on init
    @Override
    public void init() {
        telemetry.addData("Status", "Initializing");

        //for wheels
        frontLeftMotor = hardwareMap.dcMotor.get("f_l_m");
        frontRightMotor = hardwareMap.dcMotor.get("f_r_m");
        backLeftMotor = hardwareMap.dcMotor.get("b_l_m");
        backRightMotor = hardwareMap.dcMotor.get("b_r_m");
        ultrasonicRight = hardwareMap.ultrasonicSensor.get("ultrasonic_l");


        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);

        //for collection sweeper
        collection = hardwareMap.dcMotor.get("collection");

        //for beacon scoring
        //rightBeaconServo = hardwareMap.servo.get("r_b_s");
        //leftBeaconServo = hardwareMap.servo.get("l_b_s");

        //rightBeaconServo.setPosition(0); //probably will need changing (especially left)
        //leftBeaconServo.setPosition(0);

        telemetry.addData("Status", "Initialized");
    }
    @Override
    public void start() {runtime.reset();}

    @Override
    public void loop () {
        if (run == true){
            try {
                Thread.sleep(15000);
            } catch(Exception e) {
                telemetry.addData("WAIT STATUS", "FAILED");
            }
            telemetry.addData("Left Ultrasonic", ultrasonicRight.getUltrasonicLevel());
            frontLeftMotor.setPower(0.3);
            frontRightMotor.setPower(0.3);
            backLeftMotor.setPower(0.3);
            backRightMotor.setPower(0.3);
            try {
                Thread.sleep(500);
            } catch(Exception e) {
                telemetry.addData("WAIT STATUS", "FAILED");
            }

            while (ultrasonicRight.getUltrasonicLevel() < 185){
                frontLeftMotor.setPower(0.3);
                frontRightMotor.setPower(0.3);
                backLeftMotor.setPower(0.3);
                backRightMotor.setPower(0.3);
                telemetry.addData("Left Ultrasonic", ultrasonicRight.getUltrasonicLevel());
            }
            frontLeftMotor.setPower(0.0);
            frontRightMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            backRightMotor.setPower(0.0);
            run = false;
        }
    }
}