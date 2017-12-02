package com.hexin.user.controller;


import com.alibaba.fastjson.JSONObject;
import com.hexin.user.enums.ResultStatueEnum;
import com.hexin.user.exception.UserException;
import com.hexin.user.model.PigInfo;
import com.hexin.user.model.PigWeight;
import com.hexin.user.service.user.PigInfoService;
import com.hexin.user.service.user.PigWeightService;
import com.hexin.user.utils.ResultUtil;
import com.hexin.user.vo.PigInfoVO;
import com.hexin.user.vo.PigWeightVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
* Created by liyaohua on 2017-10-25.
*/
@Controller
@RequestMapping("/piginfo")
public class PigInfoController {
    private static Logger logger = LoggerFactory.getLogger(PigInfoController.class);
    @Autowired
    PigInfoService pigInfoService;

    /**
     * 获取数据信息
     * */
    @RequestMapping(value="/getforpage")
    @ResponseBody
    public Object getAll(@RequestParam(name="page",defaultValue = "1", required=false)String page,
                         @RequestParam(name="rows",defaultValue = "20",required=false)String rows,
                         @RequestParam(name="pigBatchNo",required = false)String pigBatchNo){
        PigInfoVO pigInfoVO = new PigInfoVO();
        pigInfoVO.setPage(Integer.valueOf(page));
        pigInfoVO.setPageSize(Integer.valueOf(rows));
        pigInfoVO.setPigBatchNo(pigBatchNo);
        pigInfoVO.setStart(pigInfoVO.getStart());
        List<PigInfo> pigWeightList = pigInfoService.selectByPage(pigInfoVO);
        Integer total = pigInfoService.countSelectByPage(pigInfoVO);
        JSONObject result=new JSONObject();
        result.put("rows", pigWeightList);
        result.put("total", total);
        return result;
    }


    /**
     * 获取数据信息
     * */
    @RequestMapping(value="/statisticsData")
    @ResponseBody
    public Object statisticsData(@RequestParam(name="page",defaultValue = "1", required=false)String page,
                         @RequestParam(name="rows",defaultValue = "20",required=false)String rows,
                         @RequestParam(name="date",required = false)String date){
        List<Map<String,String>> dataList = new ArrayList<>();
        dataList = pigInfoService.statisticsDataByDate(date);
        JSONObject result=new JSONObject();
        result.put("rows", dataList);
        result.put("total", dataList.size());
        return result;
    }


}
