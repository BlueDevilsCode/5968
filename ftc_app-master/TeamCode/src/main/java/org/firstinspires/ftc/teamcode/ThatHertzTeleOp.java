package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "[5968] ThatHertz TeleOp", group = "TeleOp")
public class ThatHertzTeleOp extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();

    //for wheels
    private DcMotor frontLeftMotor = null;
    private DcMotor frontRightMotor = null;
    private DcMotor backLeftMotor = null;
    private DcMotor backRightMotor = null;

    //for collection sweeper
    private DcMotor collection = null;

    //for beacon scoring
    private Servo rightBeaconServo = null;
    private Servo leftBeaconServo = null;

    //code to run on init
    @Override
    public void init() {
        telemetry.addData("Status", "Initializing");

        //for wheels
        frontLeftMotor = hardwareMap.dcMotor.get("f_l_m");
        frontRightMotor = hardwareMap.dcMotor.get("f_r_m");
        backLeftMotor = hardwareMap.dcMotor.get("b_l_m");
        backRightMotor = hardwareMap.dcMotor.get("b_r_m");

        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);

        //for collection sweeper
        collection = hardwareMap.dcMotor.get("collection");

        //for beacon scoring

        rightBeaconServo.setPosition(0); //probably will need changing (especially left)
        leftBeaconServo.setPosition(0);

        telemetry.addData("Status", "Initialized");
    }


    //code to run once start is pressed (instantaneous)
    @Override
    public void start() {runtime.reset();}


    //Main code that will run during play
    @Override
    public void loop() {
        telemetry.addData("Status", "Running: " + runtime.toString());

        //MOVING
        if(gamepad1.dpad_left) {
            //strafe left
            frontLeftMotor.setPower(-.4);
            backRightMotor.setPower(-.4);
            frontRightMotor.setPower(.4);
            backLeftMotor.setPower(.4);
        } else if(gamepad1.dpad_right) {
            //strafe right
            frontLeftMotor.setPower(.4);
            backRightMotor.setPower(.4);
            frontRightMotor.setPower(-.4);
            backLeftMotor.setPower(-.4);
        } else {

            if (gamepad1.left_bumper) { //for accurate movement
                frontLeftMotor.setPower(-gamepad1.left_stick_y * .2);
                backLeftMotor.setPower(-gamepad1.left_stick_y * .2);
                frontRightMotor.setPower(-gamepad1.right_stick_y * .2);
                backRightMotor.setPower(-gamepad1.right_stick_y * .2);
            }
            else {
                frontLeftMotor.setPower(-gamepad1.left_stick_y);
                backLeftMotor.setPower(-gamepad1.left_stick_y);
                frontRightMotor.setPower(-gamepad1.right_stick_y);
                backRightMotor.setPower(-gamepad1.right_stick_y);
            }
        }

        //COLLECTION
        if(gamepad1.a) {
            collection.setPower(-.75);
        } else if(gamepad1.b) {
            collection.setPower(.75);
        } else {
            collection.setPower(0);
        }

        if(gamepad1.right_stick_button) {
            rightBeaconServo.setPosition(.75);
        } else if(gamepad1.left_stick_button) {
            leftBeaconServo.setPosition(.75);
        }
        if(gamepad1.right_stick_button && rightBeaconServo.getPosition() == .75) {
            rightBeaconServo.setPosition(0);
        } else if(gamepad1.left_stick_button && leftBeaconServo.getPosition() == .75) {
            leftBeaconServo.setPosition(0);
        }
        telemetry.addData("FLM Power", frontLeftMotor.getPower());
        telemetry.addData("FRM Power", frontRightMotor.getPower());
        telemetry.addData("BLM Power", backLeftMotor.getPower());
        telemetry.addData("BRM Power", backRightMotor.getPower());
        telemetry.addData("Collection Power", collection.getPower());
        telemetry.addData("LS Position", leftBeaconServo.getPosition());
        telemetry.addData("RS Position", rightBeaconServo.getPosition());

    }
}