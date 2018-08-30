package com.hexin.user.runnables;

import com.hexin.user.constants.Constans;
import com.hexin.user.model.PigWeight;
import com.hexin.user.service.user.PigWeightService;
import com.hexin.user.utils.ByteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created on IDEA.
 * User: lyh
 * Date: 2018/6/1
 * Time: 6:38
 */
public class Customer implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(Customer.class);
    private LinkedBlockingQueue<Object> blockingDeque;
    private AtomicInteger count;
    private Stack<Double> data;
    private PigWeightService pigWeightService;
    private int initIndex;
    public Customer(LinkedBlockingQueue blockingDeque,AtomicInteger count){
        this.blockingDeque = blockingDeque;
        this.count = count;
    }

    public Customer(){

    }

    public Customer(Stack<Double> data, PigWeightService pigWeightService,int initIndex) {
        this.data = data;
        this.pigWeightService = pigWeightService;
        this.initIndex = initIndex;

    }


    @Override
    public void run() {
            log.info("curThread:{},开始入库 data:{}",Thread.currentThread().getName(), data);
            Double[] doubles = new Double[data.size()];
            data.toArray(doubles);
            Arrays.sort(doubles);
            BigDecimal sum2 = new BigDecimal(0.0);
            BigDecimal avg2 = new BigDecimal(0.0);
            for (Double dataDou : doubles) {
                sum2 = sum2.add(new BigDecimal(Double.toString(dataDou)));
            }
            avg2 = sum2.divide(new BigDecimal(Double.toString(doubles.length)), 2, BigDecimal.ROUND_CEILING);
            PigWeight pigWeight = new PigWeight();
            pigWeight.setChargeMan("admin");
            Double botweight = Double.valueOf(Constans.poundData.get("pound"));
            BigDecimal pigW = avg2.subtract(new BigDecimal(Double.toString(botweight))).setScale(2, BigDecimal.ROUND_CEILING);
            pigWeight.setPigWeight(pigW);
            pigWeight.setPigBatchNo(Constans.poundData.get("batchNum") == null ? "" : Constans.poundData.get("batchNum"));
            pigWeight.setPigNum(String.format("%05d", ++initIndex));
            this.pigWeightService.insert(pigWeight);
            log.info("curThread:{},入库完成",Thread.currentThread().getName());
    }
}
