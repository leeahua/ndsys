package com.hexin.user.controller;

import com.hexin.user.constants.Constans;
import com.hexin.user.enums.ResultStatueEnum;
import com.hexin.user.model.PigPound;
import com.hexin.user.serialfull.SerialCom2Observable;
import com.hexin.user.serialfull.SerialCom3Observable;
import com.hexin.user.serialfull.SerialCom5Observable;
import com.hexin.user.service.user.PigPoundService;
import com.hexin.user.service.user.PigWeightService;
import com.hexin.user.service.user.PigWidthService;
import com.hexin.user.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on IDEA.
 * User: lyh
 * Date: 2017/12/17
 * Time: 1:03
 */
@Controller
@RequestMapping("/serial")
public class SerialController {
    private static Logger log = LoggerFactory.getLogger(SerialController.class);
    @Autowired
    private PigWeightService pigWeightService;
    @Autowired
    private PigWidthService pigWidthService;

    @Autowired
    private PigPoundService pigPoundService;

    private static SerialCom2Observable serialCom2Observable;

    private static SerialCom3Observable serialCom3Observable;
    private static SerialCom5Observable serialCom5Observable;

    private static String isopen ;

    @RequestMapping("/start")
    @ResponseBody
    public Object start( HttpServletRequest request){
        PigPound pigPound = pigPoundService.selectOne();
        if(pigPound==null){
            return  ResultUtil.error(ResultStatueEnum.POUND_DATA_NOT_EXISTS);
        }
        if(pigPound.getBatchNum()==null){
            return ResultUtil.error(ResultStatueEnum.BATCH_DATA_NOT_EXISTS);
        }

        Constans.poundData.put("pound",Double.toString(pigPound.getBotpounds()));
        Constans.poundData.put("batchNum",pigPound.getBatchNum());


        if(request.getSession().getAttribute("isopen")==null) {
            //监听单片机指令串口COM2发送的电子磅的数据.
            serialCom2Observable = new SerialCom2Observable(pigWeightService);
            serialCom2Observable.send("");
            //监听单片机指令串口COM3发送过来的的指令  偶数.
            serialCom3Observable = new SerialCom3Observable(pigWidthService,pigPoundService);
            serialCom3Observable.send("");
            //监听单片机指令串口COM7发送过来的的指令  奇数.
            serialCom5Observable = new SerialCom5Observable(pigWidthService,pigPoundService);
            serialCom5Observable.send("");
            request.getSession().setAttribute("isopen","1");
        }

        return ResultUtil.success();
    }

    @RequestMapping("/stop")
    @ResponseBody
    public Object stop( HttpServletRequest request){
        if(serialCom2Observable != null){
            serialCom2Observable.close();
        }
        if(serialCom3Observable!=null){
            serialCom3Observable.close();
        }
        if(serialCom5Observable!=null){
            serialCom5Observable.close();
        }
        if("1".equals(request.getSession().getAttribute("isopen"))) {
            request.getSession().setAttribute("isopen",null);
        }
        return ResultUtil.success();
    }
}
