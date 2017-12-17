package com.hexin.user.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on IDEA.
 * User: lyh
 * Date: 2017/12/14
 * Time: 0:28
 */
public class TestS {
    private static void test(Map<String,String> map){
        Map<String,String> local = map;
        map = null;
        System.out.println("mmm--"+local.get("name"));
    }
    public static void main(String[] args){

        for(int i=0;i<2;i++){
            System.out.println(String.format("%05d",i));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        String str = sdf.format(new Date());
        System.out.println(str);
        Map<String,String> local = new HashMap<>();
        local.put("name","123");
        test(local);
        System.out.println("main----"+local.get("name"));

       /* Test test = Test.getInstance();
        byte[] hexdata = new byte[6];//发送的数据
        hexdata[0]=(byte)(0x02);
        hexdata[1]=(byte)(0x43);
        hexdata[2]=(byte)(0xB0);
        hexdata[3]=(byte)(0x01);
        hexdata[4]=(byte)(0x03);
        hexdata[5]=(byte)(0xF2);
        test.send(hexdata);
        Test test2 = Test.getInstance();
        test2.send(hexdata);
        test2.send("hello");*/

    }
}
