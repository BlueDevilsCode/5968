/* Copyright (c) 2014 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
public class HertzTeleOp extends LinearOpMode {
    DcMotor leftMotors, rightMotors, bucket, collection;
    DcMotorController DcMotorController1;
    double bucketPower, collectionPower, leftPower, rightPower;

    public void runOpMode() throws InterruptedException {

        //WHEELS ---
        leftMotors = hardwareMap.dcMotor.get("motor_left");
        rightMotors = hardwareMap.dcMotor.get("motor_right");
        leftMotors.setDirection(DcMotor.Direction.REVERSE);
        //WHEELS END ---

        //COLLECTION MECH ---
        bucket = hardwareMap.dcMotor.get("bucket");
        collection = hardwareMap.dcMotor.get("collection");
        //COLLECTION MECH END ---

        //DCMOTORCONTROLLER ---
        DcMotorController1 = hardwareMap.dcMotorController.get("DcMotorController1");
        //DCMOTORCONTROLLER ---

        //ENCODERS ---
        leftMotors.setMode(DcMotorController.RunMode.RESET_ENCODERS); //Resets encoders
        while (leftMotors.getCurrentPosition() != 0) {
            leftMotors.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            waitOneFullHardwareCycle();
        }

        // This method will be called repeatedly in a loop
        // Available mappable buttons:
        // left_stick_y/x, right_stick_y/x, a, b, x, y, left_trigger/bumper, right_trigger/bumper
        // left_stick_button, right_stick_button, dpad_up/down/left/right
        rightPower = -gamepad1.right_stick_y;
        leftPower = -gamepad1.left_stick_y;

        if (gamepad1.dpad_down && bucketPower > -1) {
            bucketPower = -0.2;
        } else if (gamepad1.dpad_up && bucketPower < 1) {
            bucketPower = +0.2;
        } else {
            bucketPower = 0;
        }

        if (gamepad1.left_bumper) {
            collectionPower = -1;
        } else if (gamepad1.right_bumper) {
            collectionPower = 1;
        } else {
            collectionPower = 0;
        }


        // clip and then scale the power values
        rightPower = Math.signum(rightPower);
        leftPower = Math.signum(leftPower);


        // write the values to the motors
        rightMotors.setPower(rightPower);
        leftMotors.setPower(leftPower);

        collection.setPower(collectionPower);
        bucket.setPower(bucketPower);

        //Log to logs
        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("left tgt pwr", "left  pwr: " + String.format("%.2f", leftPower));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", rightPower));
        telemetry.addData("bucket tgt pwr", "bucket pwr: " + String.format("%.2f", bucketPower));
        telemetry.addData("collection tgt pwr: ", "collection pwr" + String.format("%.2f", collectionPower));


//Lest's Meme

    }
}