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
 *
 * Written by; Sa'id Kharboutli
 */
public class HertzPresentation extends LinearOpMode {
    DcMotor rightMotors, leftMotors;
    double rightPower, leftPower;

    public void runOpMode() throws InterruptedException {
        //WHEELS ---
        rightMotors = hardwareMap.dcMotor.get("motors_right");
        leftMotors = hardwareMap.dcMotor.get("motors_left");

        //COLLECTION MECH --

        waitForStart();

        while (opModeIsActive()) {
            // This method will be called repeatedly in a loop
            // Available mappable buttons:
            // left_stick_y/x, right_stick_y/x, a, b, x, y, left_trigger/bumper, right_trigger/bumper
            // left_stick_button, right_stick_button, dpad_up/down/left/right

            if (gamepad1.left_bumper) {
                rightPower = -1;
                leftPower = -1;
            }
            if (gamepad1.right_bumper) {
                rightPower = 1;
                leftPower = 1;
            }
            if (!gamepad1.right_bumper && !gamepad1.left_bumper) {
                rightPower = 0;
                leftPower = 0;
            }

            // write the values to the motors
            rightMotors.setPower(rightPower);
            leftMotors.setPower(leftPower);

            //Log to logs
            telemetry.addData("Text", "*** Robot Data***");
            telemetry.addData("left tgt pwr", "left  pwr: " + String.format("%.2f", leftPower));
            telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", rightPower));
            waitOneFullHardwareCycle();
        }
    }
}


//Lest's Meme