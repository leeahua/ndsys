package com.hexin.user.service;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Stack;

/**
 * Created on IDEA.
 * User: lyh
 * Date: 2018/1/29
 * Time: 23:14
 */
public class PigWeightTest {

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
}
