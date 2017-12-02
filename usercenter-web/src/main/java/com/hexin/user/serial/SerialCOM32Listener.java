package com.hexin.user.serial;

import com.hexin.user.model.PigWeight;
import com.hexin.user.service.user.PigWeightService;
import gnu.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.TooManyListenersException;

/**
 * 串口COM32监听类
 *
 * @author liyaohua@hexindai.com
 * @create 2017/10/24 10:19
 */
public class SerialCOM32Listener implements SerialPortEventListener {
    private static Logger log = LoggerFactory.getLogger(SerialCOM22Listener.class);
    @Autowired
    private PigWeightService pigWeightService;
    //1.定义变量
    CommPortIdentifier com21 = null;//未打卡的端口
    SerialPort serialCom21 = null;//打开的端口
    InputStream inputStream = null;//输入流
    //2.构造函数：
    //实现初始化动作：获取串口COM21、打开串口、获取串口输入流对象、为串口添加事件监听对象
    public SerialCOM32Listener(){
        try {
            //获取串口、打开窗串口、获取串口的输入流。
            com21 = CommPortIdentifier.getPortIdentifier("COM2");
            serialCom21 = (SerialPort) com21.open("Com2EventListener", 1000);
            inputStream = serialCom21.getInputStream();
            //向串口添加事件监听对象。
            serialCom21.addEventListener(this);
            //设置当端口有可用数据时触发事件，此设置必不可少。
            serialCom21.notifyOnDataAvailable(true);
        } catch (NoSuchPortException e) {
            e.printStackTrace();
        } catch (PortInUseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TooManyListenersException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void serialEvent(SerialPortEvent event) {
        //定义用于缓存读入数据的数组
        byte[] cache = new byte[1024];
        //记录已经到达串口COM21且未被读取的数据的字节（Byte）数。
        int availableBytes = 0;
        //如果是数据可用的时间发送，则进行数据的读写
        if(event.getEventType() == SerialPortEvent.DATA_AVAILABLE){
            log.info("32开始读取数据");
            try {
                availableBytes = inputStream.available();
                while(availableBytes > 0){
                    inputStream.read(cache);
                    StringBuilder sb = new StringBuilder();
                    for(int i = 0; i < cache.length && i < availableBytes; i++){
                        //解码并输出数据
                        sb.append((char)cache[i]);
                    }
                    System.out.println(sb.toString());
                    PigWeight pigWeightS = new PigWeight();
                    pigWeightS.setPigBatchNo(sb.toString().split("@")[0]);
                    pigWeightS.setPigNum(1);
                    List<PigWeight> dbPigWeightList = pigWeightService.select(pigWeightS);
                    if(dbPigWeightList.size()==0){
                        log.info("32add----batchno:{}------->",new BigDecimal(sb.toString().split("@")[0]));
                        PigWeight pigWeight = new PigWeight();
                        pigWeight.setChargeMan("admin");
                        pigWeight.setCreateTime(new Date());
                        pigWeight.setUpdateTime(new Date());
                        pigWeight.setPigWeight(new BigDecimal(sb.toString().split("@")[1]));
                        pigWeight.setPigBatchNo(sb.toString().split("@")[0]);
                        pigWeight.setPigColor("Z");
                        pigWeight.setPigNum(1);
                        pigWeight.setPigLevel("A");
                        pigWeight.setPigWidth(new BigDecimal("12"));
                        pigWeightService.insert(pigWeight);
                    }else{
                        log.info("32update----batchno:{}------->",new BigDecimal(sb.toString().split("@")[0]));
                        PigWeight dbPig = dbPigWeightList.get(0);
                        dbPig.setPigWidth(new BigDecimal("300"));
                        pigWeightService.update(dbPig);
                    }
                    log.info("{}",pigWeightS);
                    availableBytes = inputStream.available();
                }
                log.info("本次数据读取结束");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //在main方法中创建类的实例
    public static void main(String[] args) {
        System.out.println("开始监听端口数据信息");
        new SerialCOM32Listener();

    }
}
