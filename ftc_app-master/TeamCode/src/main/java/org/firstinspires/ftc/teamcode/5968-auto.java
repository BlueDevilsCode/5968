package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name = "ThatHertzTestDrive", group = "Tests")
public class ThatHertzTestDrive extends LinearOpMode {

    VuforiaLocalizer vuforia;

    private DcMotor frontRightMotor;
    private DcMotor frontLeftMotor;
    private DcMotor backRightMotor;
    private DcMotor backLeftMotor;

    private Servo rightServos;
    private Servo leftServos;

    private Servo rightClaw;
    private Servo leftClaw;
    private Servo wrist;

    @Override
    public void runOpMode{
        //Vuforia_Class_FIRST vuforia = new Vuforia_Class_FIRST();

        parameters.VuforiaLicenseKey = "Afzt2sX/////AAAAGdAQ8bWAYksyvYrDpJjfLmJ2Fjjz8wm9v0TR/pHdH7q2Hm10l+3xqlxIJ4ePhNqYfmXZQ2Yr72aWbpvjaa6dDqZFO2WpE1PgNF7S3M8aeZAbWEPSXVS7GQFyF8cQHP4fHU+x9PvzFN1RRvL/nSJYH5ThwRA1BeldxsNSj6Lvqju7sYTknBbNIADXpLKL2fHYWFw7HGgEnXT9sfHK4cB5EFBUDx0sI2exPIhioPrbuItTZPkOS+M0wxkv7nvHtrku0Hv2WqY5q3+KNgj1u9ONG0sOy7sAXyGTU/bUSg7a9JBO6jrCulXIPQmxpnkdTCFUhdgmSt7d9upsoYveGVHo78dcwE+hv8inDMxuxM8dTVC2\n"

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        frontRightMotor = hardwareMap.dcMotor.get("frMotor");
        frontLeftMotor = hardwareMap.dcMotor.get("flMotor");
        backRightMotor = hardwareMap.dcMotor.get("brMotor");
        backLeftMotor = hardwareMap.dcMotor.get("blMotor");

        rightServos = hardwareMap.servo.get("rServos");
        leftServos = hardwareMap.servo.get("lServos");

        rightClaw = hardwareMap.servo.get("rClaw");
        leftClaw = hardwareMap.servo.get("lClaw");
        wrist = hardwareMap.servo.get("wrist");

        rightServos.setPosition(.5);
        leftServos.setPosition(.5);

        rightClaw.setPosition(.5);
        leftClaw.setPosition(.5);
        wrist.setPosition(0);

        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        runtime.Reset();

        boolean mark_found = false;
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

        while(!mark_found){
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {

                telemetry.addData("VuMark", "%s visible", vuMark);
                mark_found = true;

            }

        }
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //move according to vumark
        if(vumark == "Left"){
            while (frontRightMotor.getCurrentPosition() != 100) { //change number to rotation desired
            }
        }
        else if(vumark == "Right")
            while (frontRightMotor.getCurrentPosition() != 100){
            // do something
        }
        else if(vumark == "Middle"){
            while(frontRightMOtor.getCurrentPosition() != 100){
        }
    }
}