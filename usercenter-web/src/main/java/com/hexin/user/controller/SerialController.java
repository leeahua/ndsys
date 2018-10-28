package com.hexin.user.controller;

import com.hexin.user.constants.Constans;
import com.hexin.user.enums.ResultStatueEnum;
import com.hexin.user.model.PigLevel;
import com.hexin.user.model.PigPound;
import com.hexin.user.serialfull.SerialCom2Observable;
import com.hexin.user.serialfull.SerialCom3Observable;
import com.hexin.user.serialfull.SerialCom5Observable;
import com.hexin.user.service.user.PigLevelService;
import com.hexin.user.service.user.PigPoundService;
import com.hexin.user.service.user.PigWeightService;
import com.hexin.user.service.user.PigWidthService;
import com.hexin.user.utils.ResultUtil;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private PigLevelService pigLevelService;

    private static SerialCom2Observable serialCom2Observable;

    private static SerialCom3Observable serialCom3Observable;
    private static SerialCom5Observable serialCom5Observable;


    @RequestMapping("/start")
    @ResponseBody
    public Object start( HttpServletRequest request){
        log.info("开始加载底榜数据...");
        //加载底榜数据
        PigPound pigPound = pigPoundService.selectOne();

        log.info("底榜数据：{}",pigPound);
        if(pigPound==null){
            log.info("底榜数据：{}",pigPound);
            return  ResultUtil.error(ResultStatueEnum.POUND_DATA_NOT_EXISTS);
        }
        if(pigPound.getBatchNum()==null){
            return ResultUtil.error(ResultStatueEnum.BATCH_DATA_NOT_EXISTS);
        }
        Constans.poundData.put("pound",Double.toString(pigPound.getBotpounds()));
        Constans.poundData.put("batchNum",pigPound.getBatchNum());
        log.info("底榜数据加载完毕：pound:{},batchNum:{}",Constans.poundData.get("pound")
        ,Constans.poundData.get("batchNum"));
        //加载等级数据
        log.info("开始加载等级数据...");
        PigLevel pigLevel = pigLevelService.selectOne();
        log.info("等级数据：{}",pigLevel);
        if(pigLevel==null){
            return  ResultUtil.error(ResultStatueEnum.LEVEL_DATA_NOT_EXISTS);
        }
        if(StringUtils.isEmpty(String.valueOf(pigLevel.getLevel1()))
                ||StringUtils.isEmpty(String.valueOf(pigLevel.getLevel2()))
                ||StringUtils.isEmpty(String.valueOf(pigLevel.getLevel3()))
                ||StringUtils.isEmpty(String.valueOf(pigLevel.getLevel4()))
                /*||StringUtils.isEmpty(String.valueOf(pigLevel.getLevel5()))*/){
            return  ResultUtil.error(ResultStatueEnum.LEVEL_DATA_NOT_FULL);
        }
        Constans.levelDataMap.put("LV1",pigLevel.getLevel1().toString());
        Constans.levelDataMap.put("LV2",pigLevel.getLevel2().toString());
        Constans.levelDataMap.put("LV3",pigLevel.getLevel3().toString());
        Constans.levelDataMap.put("LV4",pigLevel.getLevel4().toString());
        log.info("等级数据加载完毕");

        if(StringUtils.isEmpty(Constans.openStatusMap.get("isopen"))) {
            log.info("开始连接设备");
            //监听单片机指令串口COM2发送的电子磅的数据.
            serialCom2Observable = new SerialCom2Observable(pigWeightService,pigPoundService);
            serialCom2Observable.send("");
            //监听单片机指令串口COM3发送过来的的指令  偶数.
            serialCom3Observable = new SerialCom3Observable(pigWidthService,pigPoundService);
            serialCom3Observable.send("");
            //监听单片机指令串口COM7发送过来的的指令  奇数.
            serialCom5Observable = new SerialCom5Observable(pigWidthService,pigPoundService);
            serialCom5Observable.send("");
            Constans.openStatusMap.put("isopen","1");
            log.info("连接设备完成");
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
        Constans.openStatusMap.remove("isopen");
        return ResultUtil.success();
    }
}
