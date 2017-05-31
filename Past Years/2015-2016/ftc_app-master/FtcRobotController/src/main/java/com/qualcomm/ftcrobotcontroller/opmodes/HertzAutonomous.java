/**
 * Autonomous Mode
 * <p>
 * Enables control of the robot via the gamepad
 */

package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Autonomous Mode
 * <p>
 * Enables autonomous control over the robot
 *
 * Written by: Sa'id Kharboutli
 */

public class HertzAutonomous extends LinearOpMode {

    DcMotor rightMotors, leftMotors;
    //Servo hanger;

    public void runOpMode() throws InterruptedException {
        //Get the motors from config
        rightMotors = hardwareMap.dcMotor.get("motors_right");
        leftMotors = hardwareMap.dcMotor.get("motors_left");
        rightMotors.setDirection(DcMotor.Direction.REVERSE);
        leftMotors.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        leftMotors.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightMotors.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        //Get the servo from config
        //hanger = hardwareMap.servo.get("hanger`");

        waitForStart();

        //Wait 10 seconds after starting to avoid penalties for crossing the midfield line


        //Make sure the motors are at position 0
        while (leftMotors.getCurrentPosition() != 0) {
            leftMotors.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            waitOneFullHardwareCycle();
        }

        while (opModeIsActive()) {
            leftMotors.setTargetPosition(9556);
            leftMotors.setPower(1);
            rightMotors.setPower(1);

            while (leftMotors.getCurrentPosition() != 9556) {
                waitOneFullHardwareCycle();
            }

            leftMotors.setTargetPosition(10996);
            leftMotors.setPower(1);
            rightMotors.setPower(-1);

            while (leftMotors.getCurrentPosition() != 10996) {
                waitOneFullHardwareCycle();
            }

            leftMotors.setTargetPosition(13156);
            leftMotors.setPower(1);
            rightMotors.setPower(1);

            while (leftMotors.getCurrentPosition() != 13156) {
                waitOneFullHardwareCycle();
            }

            leftMotors.setPower(0);
            rightMotors.setPower(0);

            //hanger.setPosition(1);

            //while (hanger.getPosition() != 1) {
            //    waitOneFullHardwareCycle();
            //}

            waitOneFullHardwareCycle();

            telemetry.addData("Autonomous:", " Finished");

        }
    }
}
//Lest's Meme