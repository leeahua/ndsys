package com.hexin.user.serialfull;



import com.hexin.user.constants.Constans;
import com.hexin.user.model.PigWeight;
import com.hexin.user.service.user.PigPoundService;
import com.hexin.user.service.user.PigWeightService;
import com.hexin.user.utils.ByteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 监听电子磅数据串口信息 COM2.
 * User: lyh
 * Date: 2017/12/16
 * Time: 16:56
 */

public class SerialCom2Observable implements Observer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SerialCom2Observable.class);
    private static final String PORT = "COM2"; //端口名
    private static final String RATE = "38400"; //波特率
    private static final int TIME_OUT = 100;   //超时时间1秒
    private static final int DELAY = 100;      //延迟1秒
    private static int initIndex = 0;
    private  SerialCom2 sr = new SerialCom2();
    private  boolean dataflag = false;
    private Stack<Double> data = new Stack<>();
    private  PigWeightService pigWeightService;

    public  SerialCom2Observable(PigWeightService serialService){
            this.pigWeightService = serialService;
    }

    public SerialCom2Observable() {

    }
    public void close(){
        sr.close();
    }
    public static SerialCom2Observable getInstance(PigWeightService serialService){
        return new SerialCom2Observable(serialService);
    }
    public static SerialCom2Observable getInstance(){
        return new SerialCom2Observable();
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

   /* // 静态内部类
    private static class NestClass {
        private static  SerialCom2Observable  serialCom2Observable;
        static {
            serialCom2Observable = new SerialCom2Observable();


        }
    }*/

    @Override
    public void update(Observable o, Object message) {

        //TODO 处理电子磅的数据信息 COM2
        //处理电子磅
        byte[] result = (byte[])message;
        if(result.length%18!=0){
            LOGGER.error("数据位丢失，长度不是18的倍数");
            String str = new String((byte[])message);
            LOGGER.info("--substr:{}",str);
        }else{
            List<byte[]> dataList = new ArrayList<>();
            if(result.length/18!=1){
                LOGGER.info("数据位翻倍");
                for(int k=0;k<result.length/18;k++){
                    byte[] bytes = new byte[18];
                    System.arraycopy(result,18*(k),bytes,0,18);
                    dataList.add(bytes);
                }
                handleData(dataList);
            }else{
                LOGGER.info("数据位正常");
                dataList.add(result);
                handleData(dataList);
            }

        }
    }

    private void handleData(List<byte[]> dataList) {
        for(byte[] message : dataList){
            String hexstr = ByteUtil.BinaryToHexString(message).replace(" ","").substring(8,32);
            String weight = ByteUtil.hexString2String(hexstr);
            Double wt = Double.valueOf(weight.substring(3,6))/10.0;
            if(wt>15){
                dataflag = true;
                if(data.size()>0) {
                    if (Math.abs(wt - data.peek()) >= 0 && Math.abs(wt - data.peek()) < 0.5) {
                        data.push(wt);
                    }
                }else{
                    data.push(wt);
                }
                LOGGER.info("解析数值:{}",wt);
                continue;
            }else{
                if(dataflag){
                    if(data.size()==0)continue;
                    Double[] doubles = new Double[data.size()];
                    data.toArray(doubles);
                    Arrays.sort(doubles);
                    double avg = 0.0;
                    double sum = 0.0;
                    BigDecimal sum2 = new BigDecimal(0.0);
                    BigDecimal avg2 = new BigDecimal(0.0);
                    for(int m=0;m<doubles.length;m++){
                        sum2 =sum2.add(new BigDecimal(Double.toString(doubles[m])));
                    }
                    BigDecimal leng = new BigDecimal(Double.toString(doubles.length));
                    avg2 = sum2.divide(leng,2,BigDecimal.ROUND_CEILING);
                    PigWeight pigWeight = new PigWeight();
                    pigWeight.setChargeMan("admin");
                    Double botweight = Double.valueOf(Constans.poundData.get("pound"));
                    BigDecimal pigW = avg2.subtract(new BigDecimal(Double.toString(botweight))).setScale(2,BigDecimal.ROUND_CEILING);
                    pigWeight.setPigWeight(pigW);
                    pigWeight.setPigBatchNo(Constans.poundData.get("batchNum")==null?"":Constans.poundData.get("batchNum"));
                    pigWeight.setPigNum(String.format("%05d",++initIndex));
                    this.pigWeightService.insert(pigWeight);
                    data.removeAllElements();
                    dataflag = false;
                }

            }
            LOGGER.info("解析数据位：{},对应十进制数为：{}",hexstr,weight);
        }
    }

    private void putData(Double wt) {
            data.push(wt);
    }


}
