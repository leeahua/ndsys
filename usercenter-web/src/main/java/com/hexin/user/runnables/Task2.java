package com.hexin.user.runnables;

import com.hexin.user.utils.ByteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created on IDEA.
 * User: lyh
 * Date: 2018/5/31
 * Time: 22:45
 */
public class Task2 implements Callable<Map<Integer,List<Double>>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Task2.class);
    private Integer index;
    private String hexStr;
    public Task2(Integer index, String hexStr){
        this.index = index;
        this.hexStr = hexStr;
    }

    @Override
    public Map<Integer, List<Double>> call() throws Exception {
        Map<Integer, List<Double>> result = new HashMap<>();
        String hexStrNoBlank = hexStr.replace(" ","");
        /*LOGGER.info("追加前：{}",hexStrNoBlank);
        LOGGER.info("需要追加的内容：{}",preHexData);
        if(preHexData != null){
            hexStrNoBlank = preHexData + hexStrNoBlank;
            preHexData = null;
        }*/
        LOGGER.info("追加后：{}",hexStrNoBlank);
        String[] arr = hexStrNoBlank.split("022B");
        List<Double> dataList = new ArrayList<>();

        for(int k=1;k<arr.length;k++){
           /* if(arr[k].length()<18 && k==arr.length-1){
                //需要保存和下一次的数据保存
                LOGGER.info("这是需要保存的:022B"+arr[k]);
                preHexData = "022B"+arr[k];
                continue;
            }*/
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
        result.put(index,dataList);
        return result;
    }
}
