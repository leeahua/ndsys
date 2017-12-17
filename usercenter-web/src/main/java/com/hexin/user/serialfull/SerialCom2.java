package com.hexin.user.serialfull;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

import com.hexin.user.utils.ByteUtil;
import gnu.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 激光业务处理类.
 * User: lyh
 * Date: 2017/12/16
 * Time: 16:56
 */
public class SerialCom2 extends Observable implements Runnable,SerialPortEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(SerialCom2.class);
    static CommPortIdentifier portId;
    int delayRead = 10;
    int numBytes; // buffer中的实际数据字节数
    private static byte[] readBuffer = new byte[1024]; // 4k的buffer空间,缓存串口读入的数据
    static Enumeration portList;
    InputStream inputStream;
    OutputStream outputStream;
    static SerialPort serialPort;
    HashMap serialParams;
    Thread readThread;//本来是static类型的
    //端口是否打开了
    boolean isOpen = false;
    // 端口读入数据事件触发后,等待n毫秒后再读取,以便让数据一次性读完
    public static final String PARAMS_DELAY = "delay read"; // 延时等待端口数据准备的时间
    public static final String PARAMS_TIMEOUT = "timeout"; // 超时时间
    public static final String PARAMS_PORT = "port name"; // 端口名称
    public static final String PARAMS_RATE = "rate"; // 波特率

    public boolean isOpen(){
        return isOpen;
    }

    public SerialCom2()
    {
        isOpen = false;
    }

    public void open(HashMap params)
    {
        serialParams = params;
        if(isOpen){
            close();
        }
        try
        {
            // 参数初始化
            String port = serialParams.get(PARAMS_PORT).toString();
            int rate = Integer.parseInt( serialParams.get(PARAMS_RATE).toString() );
            int timeout = Integer.parseInt( serialParams.get(PARAMS_TIMEOUT).toString() );
            delayRead = Integer.parseInt( serialParams.get(PARAMS_DELAY).toString() );
            // 打开端口
            portId = CommPortIdentifier.getPortIdentifier(port);
            serialPort = ( SerialPort ) portId.open( "SerialReader", timeout );
            inputStream = serialPort.getInputStream();
            serialPort.addEventListener( this );
            serialPort.notifyOnDataAvailable( true );
            serialPort.setSerialPortParams( rate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE );
            isOpen = true;
        }
        catch ( PortInUseException e )
        {
            LOGGER.error("端口{}已经被占用!",serialParams.get( PARAMS_PORT ).toString(),e);
        }
        catch ( TooManyListenersException e )
        {
            LOGGER.error("端口{},监听者过多!",serialParams.get( PARAMS_PORT ).toString(),e);
        }
        catch ( UnsupportedCommOperationException e )
        {
            LOGGER.error("端口{},端口操作命令不支持!",serialParams.get( PARAMS_PORT ).toString(),e);
        }
        catch ( NoSuchPortException e )
        {
            LOGGER.error("端口{},端口不存在!",serialParams.get( PARAMS_PORT ).toString(),e);
        }
        catch ( IOException e )
        {
            LOGGER.error("端口{},端口打开失败!",serialParams.get( PARAMS_PORT ).toString(),e);
        }
        serialParams.clear();
        Thread readThread = new Thread( this );
        readThread.start();
    }


    public void run()
    {
        try
        {
            Thread.sleep(50);
        }
        catch ( InterruptedException e )
        {
        }
    }
    public void start(){
        try {
            outputStream = serialPort.getOutputStream();
        }
        catch (IOException e) {}
        try{
            readThread = new Thread(this);
            readThread.start();
        }
        catch (Exception e) {  }
    }

    public void run(byte[] message) {
        try {
            Thread.sleep(4);
        }
        catch (InterruptedException e) {  }
        try {
            if(message!=null&&message.length!=0)
            {
                LOGGER.info("发送十六进制信息，hexstr:{}",ByteUtil.BinaryToHexString(message));
                outputStream.write(message); //往串口发送数据，是双向通讯的。
            }
        } catch (IOException e) {}
    }

    public void run(String message) {
        try {
            Thread.sleep(4);
        }
        catch (InterruptedException e) {  }
        try {
            if(message!=null&&message.length()!=0)
            {
                LOGGER.info("发送字符串消息，str:{}",message);
                outputStream.write(message.getBytes()); //往串口发送数据，是双向通讯的。
            }
        } catch (IOException e) {}
    }


    public void close()
    {
        if (isOpen)
        {
            try
            {
                serialPort.notifyOnDataAvailable(false);
                serialPort.removeEventListener();
                inputStream.close();
                serialPort.close();
                isOpen = false;
            } catch (IOException ex)
            {
                //"关闭串口失败";
                LOGGER.error("关闭串口失败！ com2");
            }
        }
    }

    public void serialEvent( SerialPortEvent event )
    {
        try
        {
            Thread.sleep( delayRead );
        }
        catch ( InterruptedException e )
        {
            e.printStackTrace();
        }
        switch ( event.getEventType() )
        {
            case SerialPortEvent.BI: // 10
            case SerialPortEvent.OE: // 7
            case SerialPortEvent.FE: // 9
            case SerialPortEvent.PE: // 8
            case SerialPortEvent.CD: // 6
            case SerialPortEvent.CTS: // 3
            case SerialPortEvent.DSR: // 4
            case SerialPortEvent.RI: // 5
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2
                break;
            case SerialPortEvent.DATA_AVAILABLE: // 1
                try
                {
                    // 多次读取,将所有数据读入
                    while (inputStream.available() > 0) {
                        numBytes = inputStream.read(readBuffer);
                    }
                    changeMessage( readBuffer, numBytes );
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
                break;
        }
    }

    // 通过observer pattern将收到的数据发送给observer
    // 将buffer中的空字节删除后再发送更新消息,通知观察者
    public void changeMessage( byte[] message, int length )
    {
        setChanged();
        byte[] temp = new byte[length];
        System.arraycopy( message, 0, temp, 0, length );
        notifyObservers( temp );
    }

    static void listPorts()
    {
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
        while ( portEnum.hasMoreElements() )
        {
            CommPortIdentifier portIdentifier = ( CommPortIdentifier ) portEnum
                    .nextElement();

        }
    }



    static String getPortTypeName( int portType )
    {
        switch ( portType )
        {
            case CommPortIdentifier.PORT_I2C:
                return "I2C";
            case CommPortIdentifier.PORT_PARALLEL:
                return "Parallel";
            case CommPortIdentifier.PORT_RAW:
                return "Raw";
            case CommPortIdentifier.PORT_RS485:
                return "RS485";
            case CommPortIdentifier.PORT_SERIAL:
                return "Serial";
            default:
                return "unknown type";
        }
    }


    public HashSet<CommPortIdentifier> getAvailableSerialPorts()//本来static
    {
        HashSet<CommPortIdentifier> h = new HashSet<CommPortIdentifier>();
        Enumeration thePorts = CommPortIdentifier.getPortIdentifiers();
        while ( thePorts.hasMoreElements() )
        {
            CommPortIdentifier com = ( CommPortIdentifier ) thePorts
                    .nextElement();
            switch ( com.getPortType() )
            {
                case CommPortIdentifier.PORT_SERIAL:
                    try
                    {
                        CommPort thePort = com.open( "CommUtil", 50 );
                        thePort.close();
                        h.add( com );
                    }
                    catch ( PortInUseException e )
                    {
                        System.out.println( "Port, " + com.getName()
                                + ", is in use." );
                    }
                    catch ( Exception e )
                    {
                        System.out.println( "Failed to open port "
                                + com.getName() + e );
                    }
            }
        }
        return h;
    }
}
