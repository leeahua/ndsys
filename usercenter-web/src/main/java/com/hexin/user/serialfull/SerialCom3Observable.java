package com.hexin.user.serialfull;



import com.hexin.user.constants.Constans;
import com.hexin.user.model.PigPound;
import com.hexin.user.service.user.PigPoundService;
import com.hexin.user.service.user.PigWidthService;
import com.hexin.user.utils.ByteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.BlockingQueue;

/**
 * 监听单片机指令串口1发送过来的的指令 COM3.
 * User: lyh
 * Date: 2017/12/16
 * Time: 16:56
 */
public class SerialCom3Observable implements Observer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SerialCom3Observable.class);
    private static final String PORT = "COM3"; //端口名
    private static final String RATE = "9600"; //波特率
    private static final int TIME_OUT = 100;   //超时时间1秒
    private static final int DELAY = 100;      //延迟1秒
    private static PigWidthService pigWidthServicelocal;
    private static PigPoundService pigPoundService;
    private  SerialCom3 sr = new SerialCom3();
    private static SerialComLaser3Observable serialComLaser3Observable ;
    public SerialCom3Observable(){

    }

    public SerialCom3Observable(PigWidthService pigWidthService,PigPoundService pigPoundService){
        this.pigWidthServicelocal = pigWidthService;
        this.pigPoundService = pigPoundService;
        serialComLaser3Observable  = new SerialComLaser3Observable(pigWidthService,this);
    }

    public void close(){
        sr.close();
    }
    /**
     * 往串口发送字符串数据,实现双向通讯.
     * @param  message
     */
    public  void send(String message){
        this.openSerialPort(message);
    }

    /**
     * 往串口发送二进制指令数据,实现双向通讯.
     * @param  message
     */
    public void send(byte[] message){
        this.openSerialPort(message);
    }

    /**
     * 打开串口,并发送字符串数据
     * @param  message
     */
    public void openSerialPort(byte[] message)
    {
        LOGGER.info("开始发送字符串数据:{},端口：{}",message,PORT);
        HashMap<String, Comparable> params = initPatam();
        boolean result = send(params,message);
        if(result) {
            LOGGER.info("发送十六进制数据:{}成功,端口：{}", message, PORT);
        }else{
            LOGGER.info("发送十六进制数据:{}失败,端口：{}", message, PORT);
        }

    }
    private boolean send(HashMap<String, Comparable> params,byte[] message){
        try {
            sr.open(params);
            sr.addObserver(this);
            if(message!=null&&message.length!=0)
            {
                sr.start();
                sr.run(message);
            }
        } catch (Exception e) {
            LOGGER.error("发送十六进制信息失败",e);
            return false;
        }
        return true;
    }

    private boolean sendStr(HashMap<String, Comparable> paramMap,String message){
        try {
            sr.open(paramMap);
            sr.addObserver(this);
            if(message!=null&&message.length()!=0)
            {
                sr.start();
                sr.run(message);
            }
        } catch (Exception e) {
            LOGGER.error("发送字符串信息失败！",e);
            return false;
        }
        return true;
    }


    /**
     * 打开串口,并发送字符串数据
     * @param  message
     */
    public void openSerialPort(String message)
    {
        LOGGER.info("开始发送字符串数据:{},端口：{}",message,PORT);
        HashMap<String, Comparable> params = initPatam();
        boolean sendresult = sendStr(params,message);
        if(sendresult) {
            LOGGER.info("发送字符串数据:{}成功,端口：{}", message, PORT);
        }else{
            LOGGER.info("发送字符串数据:{}失败,端口：{}", message, PORT);
        }
    }
    private HashMap<String, Comparable> initPatam(){
        HashMap<String, Comparable> params = new HashMap<String, Comparable>();
        params.put( SerialCom2.PARAMS_PORT, PORT ); // 端口名称
        params.put( SerialCom2.PARAMS_RATE, RATE ); // 波特率
        params.put( SerialCom2.PARAMS_TIMEOUT,TIME_OUT ); // 设备超时时间 1秒
        params.put( SerialCom2.PARAMS_DELAY, DELAY ); // 端口数据准备时间 1秒
        return params;
    }

    @Override
    public void update(Observable o, Object message) {
        //TODO 处理激光1指令数据
        //LOGGER.info("接收hex消息：{}", ByteUtil.BinaryToHexString((byte[])message));
        byte[] result = (byte[])message;
        String hexstr = ByteUtil.BinaryToHexString((byte[])message).replace(" ","");
        if("03".equals(hexstr)){//接到手机数据的指令，则取发送指令取获取数据
            byte[] hexdata = new byte[6];//发送的指令 //Measure
            hexdata[0]=(byte)(0x02);
            hexdata[1]=(byte)(0x43);
            hexdata[2]=(byte)(0xB0);
            hexdata[3]=(byte)(0x01);
            hexdata[4]=(byte)(0x03);
            hexdata[5]=(byte)(0xF2);
            serialComLaser3Observable.send(hexdata);
        }else if("08".equals(hexstr)){//处理批次更新命令08
            LOGGER.info("[处理批次更新命令] 开始处理批次更新命令08");
            String batchNum = JOptionPane.showInputDialog( "请输入批次号:");
            System.out.println(batchNum);
            PigPound pigPound = pigPoundService.selectOne();
            pigPound.setBatchNum(batchNum);
            pigPound.setCreateTime(new Date());
            pigPoundService.update(pigPound);
            Constans.poundData.put("batchNum",batchNum);
            LOGGER.info("[处理批次更新命令] 处理批次更新命令08完成");

        }else if("09".equals(hexstr)){//降级
            serialComLaser3Observable.changeRankData();
        }else if("05".equals(hexstr)){//删除
            serialComLaser3Observable.deletePreData();
        }else if("07".equals(hexstr)){//扣款
            serialComLaser3Observable.refreshData();
        }

    }




}
