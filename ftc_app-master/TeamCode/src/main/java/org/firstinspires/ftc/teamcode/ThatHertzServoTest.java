package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Sa'id on 12/9/2016.
 */
@Autonomous(name = "ThatHertzServoTest", group = "Testing")
public class ThatHertzServoTest extends OpMode {
    ElapsedTime runtime = new ElapsedTime();

    private Servo servo = null;

    @Override

    public void init() {
        servo = hardwareMap.servo.get("servo");
    }

    @Override
    public void start() {runtime.reset();}

    @Override
    public void loop() {
        servo.setPosition(0);
        try {
            Thread.sleep(5000);
        } catch(Exception e) {}

        servo.setPosition(.5);
        try {
            Thread.sleep(5000);
        } catch(Exception e) {}

        servo.setPosition(1);
        try {
            Thread.sleep(5000);
        } catch(Exception e) {}
    }
}
