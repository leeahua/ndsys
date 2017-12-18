package com.hexin.user.serialfull;



import com.hexin.user.model.PigWeight;
import com.hexin.user.model.PigWidth;
import com.hexin.user.service.user.PigWidthService;
import com.hexin.user.utils.ByteUtil;
import com.hexin.user.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 监听单片机指令串口2发送过来的的指令 COM5.
 * User: lyh
 * Date: 2017/12/16
 * Time: 16:56
 */
public class SerialComLaser5Observable implements Observer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SerialComLaser5Observable.class);
    private static final String PORT = "COM9"; //端口名
    private static final String RATE = "19200"; //波特率
    private static final int TIME_OUT = 100;   //超时时间1秒
    private static final int DELAY = 100;      //延迟1秒
    private static PigWidthService pigWidthServicelocal;
    private  SerialComLaser5 sr = new SerialComLaser5();
    private static int initIndex = 1;
    private static int preIndex = -1;
    public SerialComLaser5Observable(){

    }

    public SerialComLaser5Observable(PigWidthService pigWidthService){
        this.pigWidthServicelocal = pigWidthService;
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
        LOGGER.info("接收hex消息：{}", ByteUtil.BinaryToHexString((byte[])message));
        byte[] result = (byte[])message;
        if(!(result.length==6)){
            LOGGER.error("激光测量2数据位错误");
        }
        String ss = new String(result);
        String hexstr = ByteUtil.BinaryToHexString((byte[])message).replace(" ","").substring(4,8);
        //将获取的十六进制数据转化为十进制
        String width = ByteUtil.hexString2String(hexstr);
        double widthdouble = Double.valueOf(width);
        int  rank = 5;
        if(widthdouble<17){
            rank = 1;
        }else if(widthdouble<27){
            rank = 2;
        }else if(widthdouble<37){
            rank = 3;
        }else if(widthdouble<=45){
            rank = 4;
        }else {
            rank = 5;
        }
        //数据入库
        PigWidth pigWidth = new PigWidth();
        pigWidth.setPigLevel(rank+"");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        String batchNo = sdf.format(new Date());
        pigWidth.setPigBatchNo(batchNo);
        pigWidth.setPigNum(batchNo+String.format("%05d",initIndex));
        initIndex = initIndex +2;
        pigWidth.setPigColor("否");
        pigWidth.setPigWidth(new BigDecimal(widthdouble));
        pigWidthServicelocal.insert(pigWidth);
        preIndex = pigWidth.getId();
    }


    /**
     * 设置扣款逻辑
     * */
    public void refreshData(){
        if(preIndex != -1){
            LOGGER.info("开始扣款前置编号{}的宽度数据！",preIndex);
            PigWidth pigWidth = new PigWidth();
            pigWidth.setId(preIndex);
            List<PigWidth> pigWidthDbs = pigWidthServicelocal.select(pigWidth);
            if(pigWidthDbs.size()==0){
                LOGGER.error("扣款前置编号{}的宽度数据不存在！",preIndex);
            }else{
                PigWidth pigWidthdb =  pigWidthDbs.get(0);
                pigWidthdb.setPigColor("是");
                pigWidthServicelocal.update(pigWidthdb);
                LOGGER.error("扣款前置编号{}的宽度数据完成",preIndex);
            }
        }else{
            LOGGER.info("扣款前置编号{}不存在!",preIndex);
        }
    }
    /**
     * 设置降级
     * */
    public void changeRankData(){
        if(preIndex != -1){
            LOGGER.info("开始降级前置编号{}的宽度数据！",preIndex);
            PigWidth pigWidth = new PigWidth();
            pigWidth.setId(preIndex);
            List<PigWidth> pigWidthDbs = pigWidthServicelocal.select(pigWidth);
            if(pigWidthDbs.size()==0){
                LOGGER.error("开始降级前置编号{}的宽度数据不存在:{}",preIndex);
            }else{
                PigWidth pigWidthdb =  pigWidthDbs.get(0);
                if(Integer.valueOf(pigWidthdb.getPigLevel())<5) {
                    pigWidthdb.setPigLevel((Integer.valueOf(pigWidthdb.getPigLevel()) + 1) + "");
                    pigWidthServicelocal.update(pigWidthdb);
                }
                LOGGER.error("降级前置编号{}的宽度数据完成",preIndex);
            }
        }else{
            LOGGER.info("降级前置编号{}不存在!",preIndex);
        }
    }

    /**
     * 删除
     * */
    public void deletePreData(){
        if(preIndex != -1){
            LOGGER.info("开始删除前置编号{}的宽度数据！",preIndex);
            PigWidth pigWidth = new PigWidth();
            pigWidth.setId(preIndex);
            List<PigWidth> pigWidthDbs = pigWidthServicelocal.select(pigWidth);
            if(pigWidthDbs.size()==0){
                LOGGER.error("需要删除前置编号{}的信息不存在:{}",preIndex);
            }else{
                PigWidth pigWidthdb =  pigWidthDbs.get(0);
                pigWidthServicelocal.delete(pigWidthdb);
                --initIndex;
                LOGGER.error("删除前置编号{}的宽度数据完成",preIndex);
            }
        }else{
            LOGGER.info("删除前置编号{}不存在!",preIndex);
        }
    }




}
