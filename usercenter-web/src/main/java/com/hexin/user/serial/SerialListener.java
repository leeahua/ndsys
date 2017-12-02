package com.hexin.user.serial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 串口监听类.
 * User: lyh
 * Date: 2017/11/11
 * Time: 20:17
 */
public class SerialListener implements ServletContextListener{

    private static final Logger LOGGER = LoggerFactory.getLogger(SerialListener.class);

    private static SerialCOM22Listener serialCOM22Listener;

    private static SerialCOM32Listener serialCOM32Listener;

    public SerialListener(){}

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOGGER.info("初始化加载监听接口类开始");
       /* if(serialCOM22Listener == null) {
           // serialCOM22Listener = new SerialCOM22Listener();
        }
        if(serialCOM32Listener == null ){
            //serialCOM32Listener = new SerialCOM32Listener();
        }*/
        LOGGER.info("初始化加载监听接口类完成");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOGGER.info("初始化销毁监听接口类开始");
        /*if(serialCOM22Listener != null) {
            serialCOM22Listener = null;
        }
        if(serialCOM32Listener != null ){
            serialCOM32Listener = null;
        }*/
        LOGGER.info("初始化销毁监听接口类结束");
    }
}
