package com.hexin.user.controller;


import com.alibaba.fastjson.JSONObject;
import com.hexin.user.enums.ResultStatueEnum;
import com.hexin.user.exception.UserException;
import com.hexin.user.service.user.PigWeightService;
import com.hexin.user.model.PigWeight;

import java.util.Date;
import java.util.List;

import com.hexin.user.utils.ResultUtil;
import com.hexin.user.vo.PigWeightVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
* Created by liyaohua on 2017-10-25.
*/
@Controller
@RequestMapping("/pigweight")
public class PigWeightController {
    private static Logger logger = LoggerFactory.getLogger(PigWeightController.class);
    @Autowired
    PigWeightService pigWeightService;
    /**
     * 获取单个数据信息
     *
     * */
    @RequestMapping(value="/getone")
    @ResponseBody
    public Object getone(Integer id){
        logger.info("[/pigweight/querybyid],请求参数id:{}"+id);
        PigWeight pigWeight=new PigWeight();
        if(id!=null&&id!=0)
            pigWeight.setId(id);
        return ResultUtil.success(pigWeightService.select(pigWeight));
    }
    /**
     * 获取数据信息
     * */
    @RequestMapping(value="/getforpage")
    @ResponseBody
    public Object getAll(@RequestParam(name="page",defaultValue = "1", required=false)String page,
                         @RequestParam(name="rows",defaultValue = "20",required=false)String rows,
                         @RequestParam(name="pigBatchNo",required = false)String pigBatchNo){
        PigWeightVO pigWeightVO = new PigWeightVO();
        pigWeightVO.setPage(Integer.valueOf(page));
        pigWeightVO.setPageSize(Integer.valueOf(rows));
        pigWeightVO.setPigBatchNo(pigBatchNo);
        pigWeightVO.setStart(pigWeightVO.getStart());
        List<PigWeight> pigWeightList = pigWeightService.selectByPage(pigWeightVO);
        Integer total = pigWeightService.countSelectByPage(pigWeightVO);
        JSONObject result=new JSONObject();
        result.put("rows", pigWeightList);
        result.put("total", total);
        return result;
    }
    /**
     * 新增
     * */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
    public Object insertPigWeight(@RequestBody PigWeight pigWeight,
                                  HttpServletRequest request,
                                  BindingResult bindingResult){
        logger.info("[/pigweight/update],请求参数:{}"+ JSONObject.toJSONString(pigWeight));
        if(bindingResult.hasErrors()){
            throw new UserException(ResultStatueEnum.PARAMETER_ERROR);
        }

        pigWeight.setCreateTime(new Date());
        pigWeight.setUpdateTime(new Date());
        pigWeight.setChargeMan((String)request.getSession().getAttribute("user"));
        if(pigWeight.getChargeMan()==null){
            pigWeight.setChargeMan("admin");
        }
        return ResultUtil.loadResult(pigWeight,"save",pigWeightService);
    }

    /**
     * 更新信息
     * */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @ResponseBody
    public Object updatePigWeight(@RequestBody PigWeight pigWeight,
                                  @RequestParam(name = "id")Integer id,
                                  HttpServletRequest request,
                                  BindingResult bindingResult){
        logger.info("[/pigweight/update],请求参数:{},id:{}"+ JSONObject.toJSONString(pigWeight),id);
        if(id==null){
            return ResultUtil.error(ResultStatueEnum.PARAMETER_ERROR);
        }
        if(bindingResult.hasErrors()){
            throw new UserException(ResultStatueEnum.PARAMETER_ERROR);
        }
        pigWeight.setId(id);
        pigWeight.setUpdateTime(new Date());
        return ResultUtil.loadResult(pigWeight,"update",pigWeightService);
    }

    /**
     * 删除数据信息
     * */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @ResponseBody
    public Object deletePigWeight(@RequestParam(name="id")Integer id){
        logger.info("[/pigweight/delete],请求参数:{}"+ id);
        PigWeight pigWeight = new PigWeight();
        pigWeight.setId(id);
        return ResultUtil.loadResult(pigWeight,"delete",pigWeightService);
    }

    @RequestMapping(value="/deletes",method = RequestMethod.POST)
    @ResponseBody
    public Object deleteCarioLogs(@RequestBody Integer[] ids){
        return pigWeightService.deletes(ids);
    }


}
