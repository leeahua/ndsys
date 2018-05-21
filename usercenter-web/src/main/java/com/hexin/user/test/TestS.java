package com.hexin.user.test;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created on IDEA.
 * User: lyh
 * Date: 2017/12/14
 * Time: 0:28
 */
public class TestS {


    public static void main(String[] args){
        try {
            FileReader  fis = new FileReader (new File("C:\\Users\\lyh\\Desktop\\标称1\\1.txt"));
            BufferedReader bis = new BufferedReader(fis);
            String str = null;
            List<Double> lists = new ArrayList<>();
            while((str=bis.readLine())!=null){
                lists.add(Double.valueOf(str));
            }
            CalcWeight calcWeight = new CalcWeight();
            calcWeight.handleData(lists);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
