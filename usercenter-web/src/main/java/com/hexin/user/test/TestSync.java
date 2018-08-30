package com.hexin.user.test;

import com.hexin.user.runnables.Customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created on IDEA.
 * User: lyh
 * Date: 2018/6/2
 * Time: 7:41
 */
public class TestSync {

    private static ExecutorService service  = new ThreadPoolExecutor(
            10
            ,10,30, TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>(5000));

    public static void main(String[] args){

       /* List<String> list = new ArrayList<>(100);
        list.add("1111");
        list.add("222");
        List<String> list2 = new ArrayList<>(100);
        list2.addAll(list);
        service.submit(new Customer(list2));
        //service.submit(new Customer(list));
        service.shutdown();
        list.clear();*/
        System.out.println("处理完毕");
    }
}
