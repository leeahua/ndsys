package com.hexin.user.service;

import com.hexin.user.constants.Constans;
import com.hexin.user.model.PigWeight;
import com.hexin.user.serialfull.SerialCom2;
import com.hexin.user.serialfull.SerialCom2Observable;
import com.hexin.user.service.user.PigWeightService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created on IDEA.
 * User: lyh
 * Date: 2018/1/29
 * Time: 23:14
 */
public class PigWeightTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SerialCom2Observable.class);
    private static final String PORT = "COM1"; //端口名
    private static final String RATE = "38400"; //波特率
    private static final int TIME_OUT = 100;   //超时时间1秒
    private static final int DELAY = 50;      //延迟100ms
    private static int initIndex = 0;
    private SerialCom2 sr = new SerialCom2();
    private  static boolean dataflag = false;
    private static Stack<Double> data = new Stack<>();
    private static List<Double> lowdata = new ArrayList<>();
    private PigWeightService pigWeightService;
    private String preHexData;
    private static int stopFlag = 0;
    private static AtomicInteger count = new AtomicInteger(0);

    @Test
    public void test(){
        Stack<Double> data = new Stack<>();
        data.push(30.0);

        data.push(31.0);
        data.push(32.0);
        data.push(33.0);
        data.push(39.0);
        data.push(40.0);
        Double[] doubles = new Double[data.size()];
        data.toArray(doubles);
        Arrays.sort(doubles);
        double avg = 0.0;
        double sum = 0.0;
        BigDecimal sum2 = new BigDecimal(0.0);
        BigDecimal avg2 = new BigDecimal(0.0);
        if(doubles.length==4){
            sum2 = sum2.add(new BigDecimal(Double.toString(doubles[1])))
                    .add(new BigDecimal(Double.valueOf(doubles[2])));
            avg2 = sum2.divide(new BigDecimal(Double.toString(2)),2,BigDecimal.ROUND_CEILING);
        }else if(doubles.length<4){
            for(int m=0;m<doubles.length;m++){
                sum2 =sum2.add(new BigDecimal(Double.toString(doubles[m])));
            }
            BigDecimal leng = new BigDecimal(Double.toString(doubles.length));
            avg2 = sum2.divide(leng,2,BigDecimal.ROUND_CEILING);
        }else{
            int length = doubles.length;
            int tagIndex = length/2;
            if(length%2==0){
                sum2 = sum2.add(new BigDecimal(Double.toString(doubles[tagIndex])))
                        .add(new BigDecimal(Double.toString(doubles[tagIndex-1])));
                if(Math.abs(doubles[tagIndex-1]-doubles[tagIndex-2])>Math.abs(doubles[tagIndex]-doubles[tagIndex+1])){
                    sum2 = sum2.add(new BigDecimal(Double.toString(doubles[tagIndex+1])));
                }else{
                    sum2 = sum2.add(new BigDecimal(Double.toString(doubles[tagIndex-2])));
                }

            }else{
                sum2 = sum2.add(new BigDecimal(Double.toString(doubles[tagIndex])))
                        .add(new BigDecimal(Double.toString(doubles[tagIndex-1])))
                        .add(new BigDecimal(Double.toString(doubles[tagIndex+1])));
            }
            avg2 = sum2.divide(new BigDecimal(3.0),2,BigDecimal.ROUND_CEILING);

        }
        System.out.println(avg2.doubleValue());
    }

    @Test
    public void test2(){

    }

    public static void main(String[] args){
        List<Integer> datas = new ArrayList<>();
        datas.add(2300);
        for(Integer data : datas) {
            //int data = 2200;
            if (2300 < data && data <= 60536) {
                LOGGER.info("非法数据不处理：{}", data);
                return;
            }
            if (data > 60536) {
                data = 65536 - data;
                data = 2300 + data;
            } else {
                data = 2300 - data;
            }
            data = (data - 100) / 100 * 100;
            double widthdouble = Double.valueOf(data + "");
            System.out.println("输入："+data+"--->输出："+widthdouble);
        }
        /*File file = new File("E:\\aaa.txt");

            //InputStream is = new FileInputStream(file);
        List<Double> list = readFileByLines("E:\\aaa.txt");
        *//*for(int i=0;i<list.size();i++){
            System.out.println(i+":"+list.get(i));
        }*//*
        handleWt(list);*/
    }
    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     *
     * @param fileName
     *            文件名
     */
    public static List<Double> readFileByLines(String fileName) {
        List<Double> list = new ArrayList<>(5000);
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                list.add(Double.valueOf(tempString));
               // System.out.println("line " + line + ": " + tempString);
                line++;
            }
            reader.close();
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }


    private static void handleWt(List<Double> wtList) {
        //LOGGER.info("---------handleData----------当前处理线程：{} 开始",Thread.currentThread().getName());
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
/*
                    lowdata.add(wt);
                    if(lowdata.size()>1){
                        if(wt<=lowdata.get(lowdata.size()-2)){
                            stopFlag++;
                        }
                    }

                    */
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
                    //如果没有连续五个数是下降趋势,则继续收集数据
                    if(stopFlag<4){
                        continue;
                    }
                    Double[] doubles = new Double[data.size()];
                    data.toArray(doubles);
                    Arrays.sort(doubles);
                    BigDecimal sum2 = new BigDecimal(0.0);
                    BigDecimal avg2 = new BigDecimal(0.0);

                    for(Double dataDou:doubles){
                        sum2 = sum2.add(new BigDecimal(Double.toString(dataDou)));
                    }
                    avg2 = sum2.divide(new BigDecimal(Double.toString(doubles.length)),2,BigDecimal.ROUND_CEILING);
                    LOGGER.info("入库数据:{}，count：{}",avg2,count.addAndGet(1));
                    data.clear();
                    dataflag = false;
                    stopFlag = 0;
                    lowdata.clear();
                }

            }

        }
        LOGGER.info("---------handleData----------当前处理线程：{} 开始",Thread.currentThread().getName());
    }
}
