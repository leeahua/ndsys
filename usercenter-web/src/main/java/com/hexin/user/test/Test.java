package com.hexin.user.test;

import com.hexin.user.utils.ByteUtil;
import gnu.io.SerialPort;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class Test implements Observer{ 
	 
	SerialReader sr=new SerialReader();

	// 静态内部类
	private static class NestClass {
		private static  Test  test;
		static {
			test = new Test();
		}
	}
	public static Test getInstance(){
		return NestClass.test;
	}
    private Test()
    {    
       //openSerialPort("COM1"); //打开串口。
    } 
    public void update(Observable o, Object arg){    
    	String mt=new String((byte[])arg);
    	System.out.println("-----------------"+ ByteUtil.BinaryToHexString((byte[])arg));
    }


	/**
	 * 往串口发送数据,实现双向通讯.
	 * @param  message
	 */
	public void send(byte[] message)
	{
		Test test = new Test();
		test.openSerialPort(message);
	}

	/**
	 * 打开串口
	 * @param  message
	 */
	public void openSerialPort(byte[] message)
	{
		HashMap<String, Comparable> params = new HashMap<String, Comparable>();
		String port="COM1";
		String rate = "9600";
		String dataBit = ""+ SerialPort.DATABITS_8;
		String stopBit = ""+ SerialPort.STOPBITS_1;
		String parity = ""+ SerialPort.PARITY_NONE;
		int parityInt = SerialPort.PARITY_NONE;
		params.put( SerialReader.PARAMS_PORT, port ); // 端口名称
		params.put( SerialReader.PARAMS_RATE, rate ); // 波特率
		params.put( SerialReader.PARAMS_DATABITS,dataBit  ); // 数据位
		params.put( SerialReader.PARAMS_STOPBITS, stopBit ); // 停止位
		params.put( SerialReader.PARAMS_PARITY, parityInt ); // 无奇偶校验
		params.put( SerialReader.PARAMS_TIMEOUT,100 ); // 设备超时时间 1秒
		params.put( SerialReader.PARAMS_DELAY, 100 ); // 端口数据准备时间 1秒
		try {
			sr.open(params);
			sr.addObserver(this);
			if(message!=null&&message.length!=0)
			{
				sr.start();
				sr.run(message);
			}
		} catch (Exception e) {
		}
	}

    /**
     * 往串口发送数据,实现双向通讯.
     * @param  message
     */
    public void send(String message)
    {
    	Test test = new Test();
    	test.openSerialPort(message);
    }


	
    /**
     * 打开串口
     * @param  message
     */
	public void openSerialPort(String message)
    { 
        HashMap<String, Comparable> params = new HashMap<String, Comparable>();  
        String port="COM1";
        String rate = "9600";
        String dataBit = ""+ SerialPort.DATABITS_8;
        String stopBit = ""+ SerialPort.STOPBITS_1;
        String parity = ""+ SerialPort.PARITY_NONE;
        int parityInt = SerialPort.PARITY_NONE;
        params.put( SerialReader.PARAMS_PORT, port ); // 端口名称
        params.put( SerialReader.PARAMS_RATE, rate ); // 波特率
        params.put( SerialReader.PARAMS_DATABITS,dataBit  ); // 数据位
        params.put( SerialReader.PARAMS_STOPBITS, stopBit ); // 停止位
        params.put( SerialReader.PARAMS_PARITY, parityInt ); // 无奇偶校验
        params.put( SerialReader.PARAMS_TIMEOUT,100 ); // 设备超时时间 1秒
        params.put( SerialReader.PARAMS_DELAY, 100 ); // 端口数据准备时间 1秒
        try {
			sr.open(params);
		    sr.addObserver(this);
			if(message!=null&&message.length()!=0)
			 {  
				sr.start();  
				sr.run(message);
			 } 
		} catch (Exception e) { 
		}
    }
    
	

} 
 
