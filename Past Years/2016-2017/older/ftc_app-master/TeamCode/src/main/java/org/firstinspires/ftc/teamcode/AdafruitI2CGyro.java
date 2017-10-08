//package org.firstinspires.ftc.teamcode;
//
//import org.firstinspires.ftc.teamcode.AdafruitI2CGyro;
//import com.qualcomm.hardware.modernrobotics.ModernRoboticsUsbDeviceInterfaceModule;
//import com.qualcomm.robotcore.hardware.GyroSensor;
//import com.qualcomm.robotcore.hardware.HardwareDevice;
//import com.qualcomm.robotcore.hardware.I2cAddr;
//import com.qualcomm.robotcore.hardware.I2cController;
//import com.qualcomm.robotcore.hardware.I2cControllerPortDeviceImpl;
//import com.qualcomm.robotcore.util.RobotLog;
//
//import java.nio.ByteBuffer;
//import java.nio.ByteOrder;
//import java.util.Arrays;
//import java.util.Iterator;
//import java.util.concurrent.ConcurrentLinkedQueue;
//import java.util.concurrent.locks.Lock;
//
///**
// * Created by thomashan on 12/28/16.
// */
//
//public class AdafruitI2CGyro  extends I2cControllerPortDeviceImpl implements GyroSensor, I2cController.I2cPortReadyCallback {
//    //----------------------------------------------------------------------------------------------
//    // Types
//    //----------------------------------------------------------------------------------------------
//
//    enum I2cTransactionState {
//        QUEUED,
//        PENDING_I2C_READ,
//        PENDING_I2C_WRITE,
//        PENDING_READ_DONE,
//        DONE
//    }
//
//    public class GyroI2cTransaction {
//
//        AdafruitI2CGyro.I2cTransactionState state;
//
//        byte[] buffer;
//        byte offset;
//        byte len;
//        boolean write;
//        boolean blocking;
//
//        /*
//         * Generic read transaction.
//         */
//        public GyroI2cTransaction(boolean blocking)
//        {
//            offset = OFFSET_FIRMWARE_REV;
//            len = BUFFER_LENGTH;
//            write = false;
//            this.blocking = blocking;
//        }
//
//        /*
//         * Write the command byte.
//         */
//        public GyroI2cTransaction(byte data)
//        {
//            offset = OFFSET_COMMAND;
//            buffer = new byte[1];
//            buffer[0] = data;
//            len = (byte)buffer.length;
//            write = true;
//        }
//
//        /*
//         * Write the scaling coefficient.  Bricked my test gyro.  Making private
//         * to prevent usage.
//         */
//        private GyroI2cTransaction(byte fsb, byte lsb)
//        {
//            offset = OFFSET_Z_AXIS_SCALE_COEF;
//            buffer = new byte[2];
//            buffer[0] = fsb;
//            buffer[1] = lsb;
//            len = (byte)buffer.length;
//            write = true;
//        }
//
//        public boolean isEqual(AdafruitI2CGyro.GyroI2cTransaction transaction)
//        {
//            if (this.offset != transaction.offset) {
//                return false;
//            } else {
//                switch (this.offset) {
//                    case OFFSET_COMMAND:
//                    case OFFSET_Z_AXIS_SCALE_COEF:
//                        if (Arrays.equals(this.buffer, transaction.buffer)) {
//                            return true;
//                        }
//                        break;
//                    default:
//                        return false;
//                }
//            }
//            return false;
//        }
//    };
//
//    //----------------------------------------------------------------------------------------------
//    // Constants
//    //----------------------------------------------------------------------------------------------
//
//    public final static I2cAddr ADDRESS_I2C_DEFAULT     = I2cAddr.create8bit(0x6B);
//
//    final static int OUT_X_LOW      = 0x28; //r
//    final static int OUT_X_HIGH     = 0x29; //r
//    final static int OUT_Y_LOW      = 0x2A; //r
//    final static int OUT_Y_HIGH     = 0x2B; //r
//    final static int OUT_Z_LOW      = 0x2C; //r
//    final static int OUT_Z_HIGH     = 0x2D; //r
//    final static int FIFO_CTRL_REG  = 0x2E; //rw
//    final static int FIFO_SRC_REG   = 0x2F; //r
//    final static int INT1_CFG       = 0x30; //rw
//    final static int INT1_SRC       = 0x31; //r
//    final static int INT1_TSH_XH    = 0x32; //rw
//    final static int INT1_TSH_XL    = 0x33; //rw
//    final static int INT1_TSH_YH    = 0x34; //rw
//    final static int INT1_TSH_YL    = 0x35; //rw
//    final static int INT1_TSH_ZH    = 0x36; //rw
//    final static int INT1_TSH_ZL    = 0x37; //rw
//    final static int INT1_DURATION  = 0x38; //rws
//    final static int OUT_TEMP       = 0x26; //r
//    final static int STATUS_REG     = 0x27; //r
//
////    protected final static int OFFSET_FIRMWARE_REV      = 0x00;
////    protected final static int OFFSET_MANUFACTURE_CODE  = 0x01;
////    protected final static int OFFSET_SENSOR_ID         = 0x02;
////    protected final static int OFFSET_COMMAND           = 0x03;
////    protected final static int OFFSET_HEADING_DATA      = 0x04;
////    protected final static int OFFSET_INTEGRATED_Z_VAL  = 0x06;
////    protected final static int OFFSET_RAW_X_VAL         = 0x08;
////    protected final static int OFFSET_RAW_Y_VAL         = 0x0A;
////    protected final static int OFFSET_RAW_Z_VAL         = 0x0C;
////    protected final static int OFFSET_Z_AXIS_OFFSET     = 0x0E;
////    protected final static int OFFSET_Z_AXIS_SCALE_COEF = 0x10;
////
////    protected final static int OFFSET_NEW_I2C_ADDRESS   = 0x70;
////    protected final static int OFFSET_TRIGGER_1         = 0x71;
////    protected final static int OFFSET_TRIGGER_2         = 0x72;
////    protected final static int TRIGGER_1_VAL            = 0x55;
////    protected final static int TRIGGER_2_VAL            = 0xAA;
////
////    protected final static byte COMMAND_NORMAL          = 0x00;
////    protected final static byte COMMAND_NULL            = 0x4E;
////    protected final static byte COMMAND_RESET_Z_AXIS    = 0x52;
////    protected final static byte COMMAND_WRITE_EEPROM    = 0x57;
////
////    protected final static int BUFFER_LENGTH            = 0x12;
//
//    //----------------------------------------------------------------------------------------------
//    // State
//    //----------------------------------------------------------------------------------------------
//
//    protected ConcurrentLinkedQueue<AdafruitI2CGyro.GyroI2cTransaction> transactionQueue;
//
//    private I2cAddr i2cAddress;
//    private byte[] readBuffer;
//    private Lock readLock;
//    private byte[] writeBuffer;
//    private Lock writeLock;
//
//    private final static boolean debug = false;
//    private volatile boolean waitingForGodot = false;
//
//    /**
//     * Configure the software to return either cartesian or cardinal headings.
//     */
//    public enum HeadingMode {
//        HEADING_CARTESIAN,
//        HEADING_CARDINAL,
//    };
//
//    public enum MeasurementMode {
//        GYRO_CALIBRATION_PENDING,
//        GYRO_CALIBRATING,
//        GYRO_NORMAL,
//    };
//
//    private class GyroMemoryMap {
//        byte firmwareRev;
//        byte manfacturerCode;
//        byte sensorId;
//        byte command;
//        short heading;
//        short integratedZ;
//        short rawX;
//        short rawY;
//        short rawZ;
//        short ZOffset;
//        short ZScalingCoef;
//    };
//
//    private AdafruitI2CGyro.HeadingMode headingMode;
//    private AdafruitI2CGyro.MeasurementMode measurementMode;
//    private AdafruitI2CGyro.GyroMemoryMap memoryMap;
//
//    //----------------------------------------------------------------------------------------------
//    // Construction
//    //----------------------------------------------------------------------------------------------
//
//    public AdafruitI2CGyro(I2cController module, int physicalPort, I2cAddr i2cAddress)
//    {
//        super(module, physicalPort);
//
//        this.headingMode = AdafruitI2CGyro.HeadingMode.HEADING_CARDINAL;
//        this.i2cAddress = i2cAddress;
//
//        transactionQueue = new ConcurrentLinkedQueue<AdafruitI2CGyro.GyroI2cTransaction>();
//        memoryMap = new AdafruitI2CGyro.GyroMemoryMap();
//
//        measurementMode = AdafruitI2CGyro.MeasurementMode.GYRO_NORMAL;
//
//        finishConstruction();
//    }
//
//    public AdafruitI2CGyro(I2cController module, int physicalPort)
//    {
//        this(module, physicalPort, ADDRESS_I2C_DEFAULT);
//    }
//
//    @Override
//    protected void controllerNowArmedOrPretending()
//    {
//        this.readBuffer  = controller.getI2cReadCache(physicalPort);
//        this.readLock    = controller.getI2cReadCacheLock(physicalPort);
//        this.writeBuffer = controller.getI2cWriteCache(physicalPort);
//        this.writeLock   = controller.getI2cWriteCacheLock(physicalPort);
//
//        controller.enableI2cReadMode(physicalPort, i2cAddress, OFFSET_FIRMWARE_REV, BUFFER_LENGTH);
//        controller.setI2cPortActionFlag(physicalPort);
//        controller.writeI2cCacheToController(physicalPort);
//
//        controller.registerForI2cPortReadyCallback(this, physicalPort);
//    }
//
//    //----------------------------------------------------------------------------------------------
//    // Operations
//    //----------------------------------------------------------------------------------------------
//
//    public void setI2cAddress(I2cAddr newAddress) {
//        ModernRoboticsUsbDeviceInterfaceModule.throwIfModernRoboticsI2cAddressIsInvalid(newAddress);
//        RobotLog.i(getDeviceName() + ", just changed I2C address. Original address: " + i2cAddress + ", new address: " + newAddress);
//
//        i2cAddress = newAddress;
//    }
//
//    public I2cAddr getI2cAddress() {
//        return i2cAddress;
//    }
//
//    protected boolean queueTransaction(AdafruitI2CGyro.GyroI2cTransaction transaction, boolean force)
//    {
//        /*
//         * Yes, inefficient, but if the queue is more than a few transactions
//         * deep we have other problems.  The force parameter allows a controller
//         * to queue a transaction regardless of whether or not a matching
//         * transaction is already queued.
//         */
//        if (!force) {
//            Iterator<AdafruitI2CGyro.GyroI2cTransaction> it = transactionQueue.iterator();
//            while (it.hasNext()) {
//                AdafruitI2CGyro.GyroI2cTransaction t = (AdafruitI2CGyro.GyroI2cTransaction)it.next();
//                if (t.isEqual(transaction)) {
//                    buginf("NO Queue transaction " + transaction.toString());
//                    return false;
//                }
//            }
//            /*
//             * One might ask if we have a property match, but a value mismatch, why
//             * not replace the new value with the old?  That would result in transaction
//             * reordering which might not be desirable.  Something to think on.
//             */
//        }
//
//        /*
//         * Doesn't exist, plop it in.
//         */
//        buginf("YES Queue transaction " + transaction.toString());
//        transactionQueue.add(transaction);
//        return true;
//    }
//
//    protected boolean queueTransaction(AdafruitI2CGyro.GyroI2cTransaction transaction)
//    {
//        return queueTransaction(transaction, false);
//    }
//
//    public void calibrate()
//    {
//        AdafruitI2CGyro.GyroI2cTransaction transaction = new AdafruitI2CGyro.GyroI2cTransaction(COMMAND_NULL);
//
//        queueTransaction(transaction);
//
//        measurementMode = AdafruitI2CGyro.MeasurementMode.GYRO_CALIBRATION_PENDING;
//    }
//
//    public boolean isCalibrating()
//    {
//        if (measurementMode == AdafruitI2CGyro.MeasurementMode.GYRO_NORMAL) {
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    /**
//     * Return the current heading mode.
//     * @return HeadingMode
//     */
//    public AdafruitI2CGyro.HeadingMode getHeadingMode()
//    {
//        return headingMode;
//    }
//
//    /**
//     * Set the heading mode to use when calling getHeading()
//     * @param headingMode Either HEADING_CARDINAL or HEADING_CARTESIAN
//     */
//    public void setHeadingMode(AdafruitI2CGyro.HeadingMode headingMode)
//    {
//        this.headingMode = headingMode;
//    }
//
//    public AdafruitI2CGyro.MeasurementMode getMeasurementMode()
//    {
//        return measurementMode;
//    }
//
//    public int getHeading()
//    {
//        if (headingMode == AdafruitI2CGyro.HeadingMode.HEADING_CARDINAL) {
//            if (memoryMap.heading == 0) {
//                return memoryMap.heading;
//            } else {
//                return Math.abs(memoryMap.heading - 360);
//            }
//        } else {
//            return memoryMap.heading;
//        }
//    }
//
//    /**
//     * Method not supported by hardware.
//     * @return nothing
//     * @throws UnsupportedOperationException
//     */
//    @Override
//    public double getRotationFraction()
//    {
//        notSupported();
//        return 0;
//    }
//
//    public int getIntegratedZValue()
//    {
//        return memoryMap.integratedZ;
//    }
//
//    public int rawX()
//    {
//        return memoryMap.rawX;
//    }
//
//    public int rawY()
//    {
//        return memoryMap.rawY;
//    }
//
//    public int rawZ()
//    {
//        return memoryMap.rawZ;
//    }
//
//    public void resetZAxisIntegrator()
//    {
//        AdafruitI2CGyro.GyroI2cTransaction transaction;
//
//        transaction = new AdafruitI2CGyro.GyroI2cTransaction(COMMAND_RESET_Z_AXIS);
//        queueTransaction(transaction);
//        transaction = new AdafruitI2CGyro.GyroI2cTransaction(true);
//        queueTransaction(transaction);
//        if (queueTransaction(transaction)) {
//            waitOnRead();
//        }
//    }
//
//    /*
//     * I don't understand how this works yet, so making it private.  Testing
//     * is producing strange data.  Need input from Modern Robotics.
//     */
//    private void setZAxisScalingCoefficient(double coefficient)
//    {
//        byte fsb;
//        byte lsb;
//
//        fsb = (byte)coefficient;
//        lsb = (byte)Math.abs(((double)(fsb - coefficient) * 100));
//
//        AdafruitI2CGyro.GyroI2cTransaction transaction = new AdafruitI2CGyro.GyroI2cTransaction(fsb, lsb);
//
//        queueTransaction(transaction);
//    }
//
//    @Override public HardwareDevice.Manufacturer getManufacturer()
//    {
//        return HardwareDevice.Manufacturer.ModernRobotics;
//    }
//
//    @Override
//    public String getDeviceName()
//    {
//        return "Modern Robotics Gyro";
//    }
//
//    @Override
//    public String getConnectionInfo()
//    {
//        return controller.getConnectionInfo() + "; I2C port: " + physicalPort;
//    }
//
//    @Override
//    public String status()
//    {
//        return String.format("Modern Robotics Gyro, connected via device %s, port %d",
//                controller.getSerialNumber().toString(), physicalPort);
//    }
//
//    @Override
//    public int getVersion()
//    {
//        return 1;
//    }
//
//    @Override
//    public void resetDeviceConfigurationForOpMode()
//    {
//    }
//
//    @Override
//    public void close()
//    {
//        // take no action
//    }
//
//    private void populateGyroMemoryMap(boolean blocking)
//    {
//        try {
//            readLock.lock();
//            ByteBuffer buf = ByteBuffer.wrap(readBuffer);
//            buf.order(ByteOrder.LITTLE_ENDIAN);
//            memoryMap.firmwareRev = readBuffer[OFFSET_FIRMWARE_REV + ModernRoboticsUsbDeviceInterfaceModule.OFFSET_I2C_PORT_MEMORY_BUFFER];
//            memoryMap.manfacturerCode = readBuffer[OFFSET_MANUFACTURE_CODE + ModernRoboticsUsbDeviceInterfaceModule.OFFSET_I2C_PORT_MEMORY_BUFFER];
//            memoryMap.sensorId = readBuffer[OFFSET_SENSOR_ID + ModernRoboticsUsbDeviceInterfaceModule.OFFSET_I2C_PORT_MEMORY_BUFFER];
//            memoryMap.command = readBuffer[OFFSET_COMMAND + ModernRoboticsUsbDeviceInterfaceModule.OFFSET_I2C_PORT_MEMORY_BUFFER];
//            memoryMap.heading = buf.getShort(OFFSET_HEADING_DATA + ModernRoboticsUsbDeviceInterfaceModule.OFFSET_I2C_PORT_MEMORY_BUFFER);
//            memoryMap.integratedZ = buf.getShort(OFFSET_INTEGRATED_Z_VAL + ModernRoboticsUsbDeviceInterfaceModule.OFFSET_I2C_PORT_MEMORY_BUFFER);
//            memoryMap.rawX = buf.getShort(OFFSET_RAW_X_VAL + ModernRoboticsUsbDeviceInterfaceModule.OFFSET_I2C_PORT_MEMORY_BUFFER);
//            memoryMap.rawY = buf.getShort(OFFSET_RAW_Y_VAL + ModernRoboticsUsbDeviceInterfaceModule.OFFSET_I2C_PORT_MEMORY_BUFFER);
//            memoryMap.rawZ = buf.getShort(OFFSET_RAW_Z_VAL + ModernRoboticsUsbDeviceInterfaceModule.OFFSET_I2C_PORT_MEMORY_BUFFER);
//            memoryMap.ZOffset = buf.getShort(OFFSET_Z_AXIS_OFFSET + ModernRoboticsUsbDeviceInterfaceModule.OFFSET_I2C_PORT_MEMORY_BUFFER);
//            memoryMap.ZScalingCoef = buf.getShort(OFFSET_Z_AXIS_SCALE_COEF + ModernRoboticsUsbDeviceInterfaceModule.OFFSET_I2C_PORT_MEMORY_BUFFER);
//        } finally {
//            readLock.unlock();
//        }
//        if (blocking) {
//            synchronized (this) {
//                if (waitingForGodot) {
//                    waitingForGodot = false;
//                    this.notify();
//                }
//            }
//        }
//    }
//
//    private void doRead()
//    {
//        AdafruitI2CGyro.GyroI2cTransaction transaction;
//
//        transaction = new AdafruitI2CGyro.GyroI2cTransaction(false);
//        queueTransaction(transaction);
//    }
//
//    @Override
//    public void portIsReady(int port)
//    {
//        if (transactionQueue.isEmpty()) {
//            doRead();
//            return;
//        }
//
//        AdafruitI2CGyro.GyroI2cTransaction transaction = transactionQueue.peek();
//
//        /*
//         * If the transaction is in the PENDING_I2C state then if this is a read
//         * go fetch the result (and wait for another round trip to this function.
//         *
//         * If it's a write, we are done, pull it off the transaction queue.
//         *
//         * Process the next transaction if the queue is not empty.
//         */
//        if (transaction.state == AdafruitI2CGyro.I2cTransactionState.PENDING_I2C_READ) {
//            /*
//             * Go do a usb read, and then come back here.
//             */
//            controller.readI2cCacheFromModule(physicalPort);
//            transaction.state = AdafruitI2CGyro.I2cTransactionState.PENDING_READ_DONE;
//            return;
//        } else if (transaction.state == AdafruitI2CGyro.I2cTransactionState.PENDING_I2C_WRITE) {
//            /*
//             * It was a write, dequeue it and see if we have anything else.
//             */
//            transaction = transactionQueue.poll();
//            /*
//             * Now are we empty?  If so we are done.
//             */
//            if (transactionQueue.isEmpty()) {
//                return;
//            }
//            /*
//             * Not done, grab the next transaction.
//             */
//            transaction = transactionQueue.peek();
//        } else if (transaction.state == AdafruitI2CGyro.I2cTransactionState.PENDING_READ_DONE) {
//            /*
//             * The read is done, pull our data out of the buffer.
//             */
//            populateGyroMemoryMap(transaction.blocking);
//
//            transaction = transactionQueue.poll();
//            if (transactionQueue.isEmpty()) {
//                return;
//            }
//            transaction = transactionQueue.peek();
//        }
//
//        try {
//            if (transaction.write) {
//                if (transaction.offset == OFFSET_COMMAND) {
//                    memoryMap.command = transaction.buffer[0];
//                    measurementMode = AdafruitI2CGyro.MeasurementMode.GYRO_CALIBRATING;
//                }
//                controller.enableI2cWriteMode(port, i2cAddress, transaction.offset, transaction.len);
//                controller.copyBufferIntoWriteBuffer(port, transaction.buffer);
//                transaction.state = AdafruitI2CGyro.I2cTransactionState.PENDING_I2C_WRITE;
//            } else {
//                controller.enableI2cReadMode(port, i2cAddress, transaction.offset, transaction.len);
//                transaction.state = AdafruitI2CGyro.I2cTransactionState.PENDING_I2C_READ;
//            }
//            controller.writeI2cCacheToController(port);
//        } catch (IllegalArgumentException e) {
//            RobotLog.e(e.getMessage());
//        }
//
//        if (memoryMap.command == COMMAND_NORMAL) {
//            measurementMode = AdafruitI2CGyro.MeasurementMode.GYRO_NORMAL;
//        }
//    }
//
//    /*
//     * A convenience function for turning off/on local debugs.
//     */
//    protected void buginf(String s)
//    {
//        if (debug) {
//            RobotLog.i(s);
//        }
//    }
//
//    protected void notSupported()
//    {
//        throw new UnsupportedOperationException("This method is not supported for " + getDeviceName());
//    }
//
//    private void waitOnRead()
//    {
//        synchronized(this) {
//            waitingForGodot = true;
//            try {
//                while (waitingForGodot) {
//                    this.wait(0);
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
