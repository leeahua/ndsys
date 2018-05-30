package com.hexin.user.test;

import com.alibaba.fastjson.JSONObject;
import com.hexin.user.service.user.PigWeightService;
import com.hexin.user.utils.ByteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created on IDEA.
 * User: lyh
 * Date: 2017/12/14
 * Time: 0:28
 */
public class TestS {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestS.class);
    private static Stack<Double> data = new Stack<>();
    private static List<Double> lowdata = new ArrayList<>();
    private PigWeightService pigWeightService;
    private static int stopFlag = 0;
    private static boolean dataflag = false;
    public static void main2(String[] args){

        try {
            FileReader  fis = new FileReader (new File("C:\\Users\\lyh\\Desktop\\标称1\\1.txt"));
            BufferedReader bis = new BufferedReader(fis);
            String str = null;
            List<Double> dataList = new ArrayList<>();
            while((str=bis.readLine())!=null){
                dataList.add(Double.valueOf(str));
            }
            for(int i=0;i<dataList.size();i++) {
                Double wt = dataList.get(i);
                if (wt > 25) {
                    dataflag = true;
                    if (data.size() ==0) {
                        data.push(wt);

                    }else {
                        Double beforWt = data.lastElement();
                        if (Math.abs(wt - beforWt) <= 0.5) {
                            data.push(wt);
                        } else {
                            if (data.size() <= 5) {
                                data.clear();
                                data.push(wt);
                            }
                        }
                    }

                } else {
                    if (dataflag) {
                        if (data.size() == 0) continue;
                        lowdata.add(wt);
                        if (lowdata.size() > 1) {
                            if (wt < lowdata.get(lowdata.size() - 2)) {
                                stopFlag++;
                            }
                        }
                        //如果没有连续五个数是下降趋势,则继续收集数据
                        if (stopFlag < 5) {
                            continue;
                        }
                        LOGGER.info("lowaray:{}", JSONObject.toJSONString(lowdata));
                        LOGGER.info("aray:{}", JSONObject.toJSONString(data));
                        lowdata.clear();
                        Double[] doubles = new Double[data.size()];
                        data.toArray(doubles);
                        Arrays.sort(doubles);

                        double avg = 0.0;
                        double sum = 0.0;
                        BigDecimal sum2 = new BigDecimal(0.0);
                        BigDecimal avg2 = new BigDecimal(0.0);
                        for(Double dataDou:doubles){
                            sum2 = sum2.add(new BigDecimal(Double.toString(dataDou)));
                        }
                        avg2 = sum2.divide(new BigDecimal(Double.toString(doubles.length)),2,BigDecimal.ROUND_CEILING);
                        LOGGER.info("生成的数据为:{}",avg2);
                        data.removeAllElements();
                        lowdata.clear();
                        stopFlag =0;
                    }
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static void main(String[] args){
        String hexStr = "02 2B 39 20 30 30 31 31 30 39 34 33 30 30 30 30 0D 23 02 2B 30 20 30 30 30 38 35 35 30 30 30 30 30 30 0D 21 02 2B 30 20";
        String[] arr = hexStr.replace(" ","").split("022B");
        for(int k=0;k<arr.length;k++){
            if(arr[k].length()<18 && k==arr.length-1){
                //需要保存和下一次的数据保存
                System.out.println("这是需要保存的:022B"+arr[k]);
                continue;
            }
            if(arr[k].length()<18){
                //直接丢弃
                System.out.println("这是需要丢弃的的:"+arr[k]);
                continue;
            }
            String dataHex = arr[k].substring(4,arr[k].length()-4);
            System.out.println(dataHex+"---"+arr[k].length());
            String weight = ByteUtil.hexString2String(dataHex);
            System.out.println(weight);
            Double wt = Double.valueOf(weight.substring(2,6))/10.0;
            System.out.println(wt);
        }

    }
}
