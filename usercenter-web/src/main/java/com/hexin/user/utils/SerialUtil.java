package com.hexin.user.utils;


import com.alibaba.fastjson.serializer.SerializeBeanInfo;
import com.hexin.user.enums.ResultStatueEnum;
import com.hexin.user.exception.UserException;
import gnu.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * 串口工具类.
 * User: lyh
 * Date: 2017/10/30
 * Time: 23:06
 */
public class SerialUtil {

    private static Logger logger = LoggerFactory.getLogger(SerialUtil.class);

    //私有化构造方法
    private SerialUtil() {
    }

    /**
     * 获取所有可用端口
     *
     * @return 可用端口名称列表
     */
    public static final ArrayList<String> findPort() {
        //获得当前所有可用串口
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
        ArrayList<String> portNameList = new ArrayList<>();
        //将可用串口名添加到List并返回该List
        while (portList.hasMoreElements()) {
            String portName = portList.nextElement().getName();
            portNameList.add(portName);
        }
        return portNameList;
    }

    /**
     * 打开串口
     *
     * @param portName 端口名称
     * @param baudrate 波特率
     * @return 串口对象
     */
    public static SerialPort openPort(String portName, int baudrate) {
        logger.info("开始打开串口，串口名：{}，波特率：{}", portName, baudrate);
        try {
            //通过端口名识别端口
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
            //打开端口，并给端口名字和一个timeout（打开操作的超时时间）
            CommPort commPort = portIdentifier.open(portName, 2000);
            //判断是不是串口
            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                //设置一下串口的波特率等参数
                serialPort.setSerialPortParams(baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                return serialPort;
            } else {
                logger.error("非标准串口类型");
                return null;
            }
        } catch (PortInUseException e) {
            logger.error("端口正在被使用");
            return null;
        } catch (NoSuchPortException e) {
            logger.error("端口不存在");
            return null;
        } catch (UnsupportedCommOperationException e) {
            logger.error("不支持的串口类型");
            return null;
        }
    }

    /**
     * 关闭串口
     * @param serialPort 待关闭的串口对象
     */
    public static void closePort(SerialPort serialPort) {
        if (serialPort != null) {
            serialPort.close();
            serialPort = null;
        }
    }


    /**
     * 往串口发送数据
     * @param serialPort 串口对象
     * @param order	待发送数据
     */

    public static void sendToPort(SerialPort serialPort, byte[] order) throws UserException{
        OutputStream out = null;
        try {
            out = serialPort.getOutputStream();
            out.write(order);
            out.flush();

        } catch (IOException e) {
        } finally {
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
            } catch (IOException e) {
                throw new UserException(ResultStatueEnum.SERIAL_SEND_ERROR);
            }
        }
    }

    /**
     * 从串口读取数据
     * @param serialPort 当前已建立连接的SerialPort对象
     * @return 读取到的数据
     */
    public static byte[] readFromPort(SerialPort serialPort) throws UserException {

        InputStream in = null;
        byte[] bytes = null;

        try {

            in = serialPort.getInputStream();
            int bufflenth = in.available();        //获取buffer里的数据长度

            while (bufflenth != 0) {
                bytes = new byte[bufflenth];    //初始化byte数组为buffer中数据的长度
                in.read(bytes);
                bufflenth = in.available();
            }
        } catch (IOException e) {
            throw new UserException(ResultStatueEnum.SERIAL_PORT_ERROR);
        } finally {
            try {
                if (in != null) {
                    in.close();
                    in = null;
                }
            } catch (IOException e) {
                throw new UserException(ResultStatueEnum.SERIAL_PORT_ERROR);
            }

        }
        return bytes;
    }

    /**
     * 添加监听器
     * @param port     串口对象
     * @param listener 串口监听器
     */
    public static void addListener(SerialPort port, SerialPortEventListener listener) throws UserException {
        try {
            //给串口添加监听器
            port.addEventListener(listener);
            //设置当有数据到达时唤醒监听接收线程
            port.notifyOnDataAvailable(true);
            //设置当通信中断时唤醒中断线程
            port.notifyOnBreakInterrupt(true);

        } catch (TooManyListenersException e) {
            logger.error("接口已经被监听");
            throw new UserException(ResultStatueEnum.SERIAL_PORT_ERROR);
        }
    }

    public static void main(String[] args){
        List<String> postList = SerialUtil.findPort();
        for(String str : postList){
            System.out.println(str);
        }
        Thread threadwrite =new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> postList = SerialUtil.findPort();
                for(String str : postList){
                    System.out.println(str);
                }
                for(int i=0;i<10;i++){
                    System.out.println("threadwrite ---21开始写--"+i+"条数据");
                    /**
                     * 串口写
                     * */
                    //1.定义变量
                    CommPortIdentifier com11 = null;//用于记录本地串口
                    SerialPort serialCom11 = null;//用于标识打开的串口
                    try {
                        //获取com1串口对象
                        com11 = CommPortIdentifier.getPortIdentifier("COM2");
                        //打开串口
                        serialCom11 = (SerialPort) com11.open("Com21Writer", 9600);
                        //4.往串口写数据（使用串口对应的输出流对象）
                        //4.1.获取串口的输出流对象
                        OutputStream outputStream = serialCom11.getOutputStream();

                        //4.2.通过串口的输出流向串口写数据“Hello World!”：
                        //使用输出流往串口写数据的时候必须将数据转换为byte数组格式或int格式，
                        //当另一个串口接收到数据之后再根据双方约定的规则，对数据进行解码。

                        String douData = DateTimeUtil.date2Str(new Date(),"yyyyMMdd")+i+"@"+ (0.5 + i)+"";
                        outputStream.write(douData.getBytes("utf-8"));
                        outputStream.flush();
                        //4.3.关闭输出流
                        outputStream.close();
                        //5.关闭串口
                        serialCom11.close();
                        Thread.currentThread().sleep(3000L);
                    } catch (NoSuchPortException e) {
                        e.printStackTrace();
                    } catch (PortInUseException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        Thread threadwrite31 =new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> postList = SerialUtil.findPort();
                for(String str : postList){
                    System.out.println(str);
                }
                for(int i=0;i<10;i++){
                    System.out.println("threadwrite ---31开始写--"+i+"条数据");
                    /**
                     * 串口写
                     * */
                    //1.定义变量
                    CommPortIdentifier com11 = null;//用于记录本地串口
                    SerialPort serialCom11 = null;//用于标识打开的串口
                    try {
                        //获取com1串口对象
                        com11 = CommPortIdentifier.getPortIdentifier("COM2");
                        //打开串口
                        serialCom11 = (SerialPort) com11.open("Com31Writer", 9600);
                        //4.往串口写数据（使用串口对应的输出流对象）
                        //4.1.获取串口的输出流对象
                        OutputStream outputStream = serialCom11.getOutputStream();

                        //4.2.通过串口的输出流向串口写数据“Hello World!”：
                        //使用输出流往串口写数据的时候必须将数据转换为byte数组格式或int格式，
                        //当另一个串口接收到数据之后再根据双方约定的规则，对数据进行解码。

                        String douData = DateTimeUtil.date2Str(new Date(),"yyyyMMdd")+i+"@"+ (0.5 + i)+"";
                        outputStream.write(douData.getBytes("utf-8"));
                        outputStream.flush();
                        //4.3.关闭输出流
                        outputStream.close();
                        //5.关闭串口
                        serialCom11.close();
                        Thread.currentThread().sleep(2000L);
                    } catch (NoSuchPortException e) {
                        e.printStackTrace();
                    } catch (PortInUseException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        Thread threadread =new Thread(new Runnable() {
            @Override
            public void run() {
                //1.定义变量
                CommPortIdentifier com2read = null;//用于记录本地串口
                SerialPort serialPortRead = null;//用于标识打开的串口
                try {
                    //获取并打开串口
                    com2read = CommPortIdentifier.getPortIdentifier("COM2");
                    serialPortRead = (SerialPort)com2read.open("com2read", 1000);
                    InputStream inputStream = serialPortRead.getInputStream();
                    //4.从串口读入数据
                    //定义用于缓存读入数据的数组
                    byte[] cache = new byte[1024];
                    //记录已经到达串口COM21且未被读取的数据的字节（Byte）数。
                    int availableBytes = 0;
                    //无限循环，每隔20毫秒对串口COM21进行一次扫描，检查是否有数据到达
                    while(true) {
                        //获取串口COM21收到的可用字节数
                        availableBytes = inputStream.available();
                        //如果可用字节数大于零则开始循环并获取数据
                        while (availableBytes > 0) {
                            //从串口的输入流对象中读入数据并将数据存放到缓存数组中
                            inputStream.read(cache);
                            //将获取到的数据进行转码并输出
                            for (int j = 0; j < cache.length && j < availableBytes; j++) {
                                //因为COM11口发送的是使用byte数组表示的字符串，
                                //所以在此将接收到的每个字节的数据都强制装换为char对象即可，
                                //这是一个简单的编码转换，读者可以根据需要进行更加复杂的编码转换。
                                System.out.print((char) cache[j]);
                            }
                            System.out.println();
                            //更新循环条件
                            availableBytes = inputStream.available();
                        }
                        //让线程睡眠20毫秒
                        Thread.sleep(2000);
                    }
                } catch (NoSuchPortException e) {
                    e.printStackTrace();
                } catch (PortInUseException e) {
                    e.printStackTrace();
                }  catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        threadwrite.start();
        //threadwrite31.start();
        //threadread.start();

    }
}
