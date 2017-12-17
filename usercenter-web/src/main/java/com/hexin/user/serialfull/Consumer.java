package com.hexin.user.serialfull;

import com.hexin.user.utils.ByteUtil;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 处理磅的队列.
 * User: lyh
 * Date: 2017/12/16
 * Time: 22:57
 */
public class Consumer implements Runnable {
    private BlockingQueue<byte[]> queue;
    private static final int      DEFAULT_RANGE_FOR_SLEEP = 1000;

    public Consumer(BlockingQueue<byte[]> queue) {
        this.queue = queue;
    }

    public void run() {
        System.out.println("启动消费者线程！");
        Random r = new Random();
        boolean isRunning = true;
        try {
            while (isRunning) {
                System.out.println(Thread.currentThread().getName()+"正从队列获取数据...");
                byte[] data = queue.poll(2, TimeUnit.SECONDS);

                if (null != data) {
                    for(int k=0;k<(data.length/18);k++){
                        byte[] bytes = new byte[18];
                        System.arraycopy(data,18*(k),bytes,0,18*(k+1));
                        String hexstr = ByteUtil.BinaryToHexString((byte[])bytes).replace(" ","").substring(8,32);
                        String weight = ByteUtil.hexString2String(hexstr);
                        if(!"000000000000".equals(weight)){
                            Double wt = Double.valueOf(weight.substring(3,6))/10.0;
                        }

                    }
                    //TODO 处理数据
                } else {
                    // 超过2s还没数据，认为所有生产线程都已经退出，自动退出消费线程。
                    isRunning = false;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(Thread.currentThread().getName()+"退出消费者线程！");
        }
    }


}
