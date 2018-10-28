package com.hexin.user.serialfull;



import com.hexin.user.constants.Constans;
import com.hexin.user.model.PigPound;
import com.hexin.user.model.PigWeight;
import com.hexin.user.service.user.PigPoundService;
import com.hexin.user.service.user.PigWeightService;
import com.hexin.user.utils.ByteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;

/**
 * 监听电子磅数据串口信息 COM1.
 * User: lyh
 * Date: 2017/12/16
 * Time: 16:56
 */

public class SerialCom2Observable implements Observer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SerialCom2Observable.class);
    private static final String PORT = "COM1"; //端口名
    private static final String RATE = "38400"; //波特率
    private static final int TIME_OUT = 1000;   //超时时间1秒
    private static final int DELAY = 50;      //延迟100ms
    private SerialCom2 sr = new SerialCom2();
    private static boolean dataflag = false;
    private static Stack<Double> data = new Stack<>();
    private static List<Double> lowdata = new ArrayList<>();
    private PigWeightService pigWeightService;
    private PigPoundService pigPoundService;
    private String preHexData;
    private static int stopFlag = 0;
    private static ExecutorService service  = new ThreadPoolExecutor(
            10
            ,10,30, TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>(5000));


    public  SerialCom2Observable(PigWeightService serialService, PigPoundService pigPoundService){
            this.pigWeightService = serialService;
            this.pigPoundService = pigPoundService;
            LOGGER.info("初始化SerialCom2Observable 完毕!");

    }

    public SerialCom2Observable() {

    }
    public void close(){
        sr.close();
    }
    public static SerialCom2Observable getInstance(PigWeightService serialService,PigPoundService pigPoundService){
        return new SerialCom2Observable(serialService,pigPoundService);
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



    @Override
    public void update(Observable o, Object message) {
        LOGGER.info("-------------------当前处理线程：{} 开始",Thread.currentThread().getName());
        byte[] result = (byte[])message;
        String hexStr =ByteUtil.BinaryToHexString(result);
        handleHexData(hexStr);

        LOGGER.info("-------------------当前处理线程：{} 结束",Thread.currentThread().getName());
    }

    private void handleHexData(String hexStr) {
        String hexStrNoBlank = hexStr.replace(" ","");
       /* LOGGER.info("追加前：{}",hexStrNoBlank);
        LOGGER.info("需要追加的内容：{}",preHexData);*/
        if(preHexData != null){
            hexStrNoBlank = preHexData + hexStrNoBlank;
            preHexData = null;
        }
       /* LOGGER.info("追加后：{}",hexStrNoBlank);*/
        String[] arr = hexStrNoBlank.split("022B");
        List<Double> dataList = new ArrayList<>();

        for(int k=1;k<arr.length;k++){
            if(arr[k].length()<18 && k==arr.length-1){
                //需要保存和下一次的数据保存
                LOGGER.info("这是需要保存的:022B"+arr[k]);
                preHexData = "022B"+arr[k];
                continue;
            }
            if(arr[k].length()<18){
                //直接丢弃
                LOGGER.info("这是需要丢弃的的:"+arr[k]);
                continue;
            }
            String dataHex = arr[k].substring(4,arr[k].length()-4);
            String weight = ByteUtil.hexString2String(dataHex);
            Double wt = Double.valueOf(weight.substring(2,6))/10.0;
            dataList.add(wt);
        }

        handleWt(dataList);
    }

    private void handleWt(List<Double> wtList) {
        LOGGER.info("当前处理线程：{} 开始",Thread.currentThread().getName());
        for(int i=0;i<wtList.size();i++){
            Double wt = wtList.get(i);
            if(wt>25){
                dataflag = true;
                if(i<1){
                    data.push(wt);
                }else {
                    Double beforWt = wtList.get(i - 1);
                    if (Math.abs(wt - beforWt) <= 0.5) {
                        data.push(wt);
                    } else {
                        if (data.size() <= 5) {
                            data.clear();
                            data.push(wt);
                        }
                    }
                }
                continue;
            }else{
                if(dataflag){
                    if(data.size()==0)continue;
                   /* lowdata.add(wt);
                    if(lowdata.size()>1){
                        if(wt<=lowdata.get(lowdata.size()-2)){
                            stopFlag++;
                        }
                    }*/
                   if(wt == 0){
                        stopFlag++;
                    }else{
                        if(lowdata.size()==0){
                            lowdata.add(wt);
                        }else {
                            lowdata.add(wt);
                            if(lowdata.size()>1){
                                if(wt<=lowdata.get(lowdata.size()-2)){
                                    stopFlag++;
                                }
                            }
                        }
                    }
                    LOGGER.info("lowdata:{},stopFlag:{}，data:{}", lowdata ,stopFlag,data);
                    //如果没有连续五个数是下降趋势,则继续收集数据
                    if(stopFlag<4){
                        continue;
                    }
                    if(data.size()>1) {
                        LOGGER.info("curThread:{},开始入库 data:{}",Thread.currentThread().getName(), data);
                        Double[] doubles = new Double[data.size()];
                        data.toArray(doubles);
                        Arrays.sort(doubles);
                        BigDecimal sum2 = new BigDecimal(0.0);
                        BigDecimal avg2 = new BigDecimal(0.0);
                        for (Double dataDou : doubles) {
                            sum2 = sum2.add(new BigDecimal(Double.toString(dataDou)));
                        }
                        avg2 = sum2.divide(new BigDecimal(Double.toString(doubles.length)), 2, BigDecimal.ROUND_CEILING);
                        PigWeight dbPigWeight = pigWeightService.selectLasterByBatchNo(Constans.poundData.get("batchNum"));
                        int nextNo = 1;
                        if(dbPigWeight != null){
                            //说明数据库中不存在
                            nextNo = Integer.valueOf(dbPigWeight.getPigNum()==null?"1":dbPigWeight.getPigNum())+1;
                        }
                        PigWeight pigWeight = new PigWeight();
                        pigWeight.setChargeMan("admin");
                        Double botweight = Double.valueOf(Constans.poundData.get("pound"));
                        BigDecimal pigW = avg2.subtract(new BigDecimal(Double.toString(botweight))).setScale(2, BigDecimal.ROUND_CEILING);
                        pigWeight.setPigWeight(pigW);
                        pigWeight.setPigBatchNo(Constans.poundData.get("batchNum") == null ? "" : Constans.poundData.get("batchNum"));

                        pigWeight.setPigNum(String.format("%05d", nextNo));
                        this.pigWeightService.insert(pigWeight);
                        LOGGER.info("curThread:{},入库完成",Thread.currentThread().getName());
                        data.clear();
                        dataflag = false;
                        stopFlag = 0;
                        lowdata.clear();
                    }else{
                        data.clear();
                    }
                }

            }

        }
        wtList.clear();
        LOGGER.info("当前处理线程：{} 结束",Thread.currentThread().getName());
    }

    private Double byteToDouble(byte[] data){
        String hexstr = ByteUtil.BinaryToHexString(data).replace(" ","").substring(8,32);
        String weight = ByteUtil.hexString2String(hexstr);
        Double wt = Double.valueOf(weight.substring(2,6))/10.0;
        return wt;
    }

    private void putData(Double wt) {
            data.push(wt);
    }


}
