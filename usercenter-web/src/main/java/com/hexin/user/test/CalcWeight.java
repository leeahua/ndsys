package com.hexin.user.test;

import com.hexin.user.constants.Constans;
import com.hexin.user.model.PigWeight;
import com.hexin.user.utils.ByteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.DoubleConsts;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Created on IDEA.
 * User: lyh
 * Date: 2018/5/20
 * Time: 14:35
 */
public class CalcWeight {
    private  boolean dataflag = false;
    private static final Logger LOGGER = LoggerFactory.getLogger(CalcWeight.class);
    private Stack<Double> data = new Stack<>();
    private List<Double> lowdata = new ArrayList<>();
    private Double byteToDouble(byte[] data){
        String hexstr = ByteUtil.BinaryToHexString(data).replace(" ","").substring(8,32);
        String weight = ByteUtil.hexString2String(hexstr);
        Double wt = Double.valueOf(weight.substring(3,6))/10.0;
        return wt;
    }
    public void handleData(List<Double> dataList) {
        for (int i = 1; i < dataList.size(); i++) {
            //  byte[] message =  dataList.get(i);

            Double wt = dataList.get(i);
            LOGGER.info("-------------------接收数据位:{}", wt);
            if (wt > 25) {
                dataflag = true;
                Double beforWt = dataList.get(i - 1);
                if (Math.abs(wt - beforWt) <= 0.5) {
                    data.push(wt);
                    LOGGER.info("解析数值:{}", wt);
                }
                lowdata.clear();
                continue;
            } else {
                if (dataflag) {
                    if (data.size() == 0) continue;
                    if (wt <= 15) {
                        lowdata.add(wt);
                    }
                    if (lowdata.size() <= 3) continue;
                    Double[] doubles = new Double[data.size()];
                    data.toArray(doubles);
                    Arrays.sort(doubles);
                    double avg = 0.0;
                    double sum = 0.0;
                    BigDecimal sum2 = new BigDecimal(0.0);
                    BigDecimal avg2 = new BigDecimal(0.0);

                    for (Double dataDou : doubles) {
                        sum2 = sum2.add(new BigDecimal(Double.toString(dataDou)));
                    }
                    avg2 = sum2.divide(new BigDecimal(Double.toString(doubles.length)), 2, BigDecimal.ROUND_CEILING);

                    dataflag = false;

                    System.out.print("入库数据:"+avg2);
                }

            }

        }
        Double[] doubles = new Double[data.size()];
        data.toArray(doubles);
        Arrays.sort(doubles);
        System.out.print("当前站里面的数据:");
        for(Double dd: doubles){
            System.out.print(""+dd+",");
        }
        data.removeAllElements();
    }
}
