package com.hexin.user.controller;


import com.alibaba.fastjson.JSONObject;
import com.hexin.user.enums.ResultStatueEnum;
import com.hexin.user.exception.UserException;
import com.hexin.user.model.PigPound;
import com.hexin.user.service.user.PigPoundService;
import com.hexin.user.utils.DateTimeUtil;
import com.hexin.user.utils.ResultUtil;
import com.hexin.user.vo.PigPoundsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.List;


/**
* Created by liyaohua on 2017-10-25.
*/
@Controller
@RequestMapping("/pigpound")
public class PigPoundController {

    private static Logger logger = LoggerFactory.getLogger(PigPoundController.class);
    @Autowired
    PigPoundService pigPoundService;

    @RequestMapping(value="/getone",method = RequestMethod.GET)
    @ResponseBody
    public Object getone(Integer id){
        logger.info("[/pigpound/querybyid],请求参数id:{}",id);
        PigPound pigPound=new PigPound();
        if(id!=null&&id!=0)
            pigPound.setId(id);
        return ResultUtil.success(pigPoundService.select(pigPound));
    }

    /**
     * 获取数据信息
     * */
    @RequestMapping(value="/getforpage")
    @ResponseBody
    public Object getforpage(@RequestParam(name="page",defaultValue = "1", required=false)String page,
                             @RequestParam(name="rows",defaultValue = "20",required=false)String rows,
                             @RequestParam(name="startTime",required = false)String startTime,
                             @RequestParam(name="endTime",required = false)String endTime){
        PigPoundsVO pigPoundsVO = new PigPoundsVO();
        pigPoundsVO.setPage(Integer.valueOf(page));
        pigPoundsVO.setPageSize(Integer.valueOf(rows));
        pigPoundsVO.setStart(pigPoundsVO.getStart());

        try {
            if(startTime!=null){
                pigPoundsVO.setStartTime(DateTimeUtil.str2DateTime(startTime,"yyyy-MM-dd"));
            }
            if(endTime!=null){
                pigPoundsVO.setEndTime(DateTimeUtil.str2DateTime(endTime,"yyyy-MM-dd"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<PigPound> pigPoundsList = pigPoundService.selectByPage(pigPoundsVO);
        Integer total = pigPoundService.countSelectByPage(pigPoundsVO);
        JSONObject result=new JSONObject();
        result.put("rows", pigPoundsList);
        result.put("total", total);
        return result;
    }
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
    public Object insertPigPound(@RequestBody PigPound pigPound,
                                 BindingResult bindingResult){
        logger.info("[/pigpound/save],请求参数:{}",JSONObject.toJSONString(pigPound));
        if(bindingResult.hasErrors()){
            return ResultUtil.error(ResultStatueEnum.PARAMETER_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        Integer total = pigPoundService.countSelectByPage(new PigPoundsVO());
        if(total!=null && total>0){
            return ResultUtil.error(ResultStatueEnum.DATA_ALREAD_EXISTS);
        }
        pigPound.setCreateTime(new Date());
        pigPound.setBatchNum(pigPound.getBatchNum());
        return ResultUtil.loadResult(pigPound,"save",pigPoundService);
    }


    @RequestMapping(value="/update",method = RequestMethod.POST)
    @ResponseBody
    public Object updatePigPound(@RequestBody PigPound pigPound,
                                 @RequestParam(name = "id")Integer id,
                                 BindingResult bindingResult){
        logger.info("[/pigpound/update],请求参数:{}",JSONObject.toJSONString(pigPound));
        if(bindingResult.hasErrors()){
            return ResultUtil.error(ResultStatueEnum.PARAMETER_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        if(id==null){
            return ResultUtil.error(ResultStatueEnum.PARAMETER_ERROR);
        }

        pigPound.setId(id);
        pigPound.setCreateTime(new Date());
        return ResultUtil.loadResult(pigPound,"update",pigPoundService);
    }


    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @ResponseBody
    public Object deletePigPound(@RequestParam(name="id")Integer id){
        logger.info("[/pigpound/delete],请求参数:{}",id);
        PigPound pigPound = new PigPound();
        pigPound.setId(id);
        return ResultUtil.loadResult(pigPound,"delete",pigPoundService);
    }


}
