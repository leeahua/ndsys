package com.hexin.user.controller;


import com.alibaba.fastjson.JSONObject;
import com.hexin.user.enums.ResultStatueEnum;
import com.hexin.user.exception.UserException;
import com.hexin.user.model.PigWidth;
import com.hexin.user.service.user.PigWidthService;
import com.hexin.user.utils.ResultUtil;
import com.hexin.user.vo.PigWidthVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


/**
* Created by liyaohua on 2017-10-25.
*/
@Controller
@RequestMapping("/pigwidth")
public class PigWidthController {
    private static Logger logger = LoggerFactory.getLogger(PigWidthController.class);
    @Autowired
    PigWidthService pigWidthService;
    /**
     * 获取单个数据信息
     *
     * */
    @RequestMapping(value="/getone")
    @ResponseBody
    public Object getone(Integer id){
        logger.info("请求参数id:{}"+id);
        PigWidth pigWidth=new PigWidth();
        if(id!=null&&id!=0)
            pigWidth.setId(id);
        return ResultUtil.success(pigWidthService.select(pigWidth));
    }
    /**
     * 获取数据信息
     * */
    @RequestMapping(value="/getforpage")
    @ResponseBody
    public Object getAll(@RequestParam(name="page",defaultValue = "1", required=false)String page,
                         @RequestParam(name="rows",defaultValue = "20",required=false)String rows,
                         @RequestParam(name="pigBatchNo",required = false)String pigBatchNo){
        PigWidthVO pigWeightVO = new PigWidthVO();
        pigWeightVO.setPage(Integer.valueOf(page));
        pigWeightVO.setPageSize(Integer.valueOf(rows));
        pigWeightVO.setPigBatchNo(pigBatchNo);
        pigWeightVO.setStart(pigWeightVO.getStart());
        List<PigWidth> pigWeightList = pigWidthService.selectByPage(pigWeightVO);
        Integer total = pigWidthService.countSelectByPage(pigWeightVO);
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
    public Object insertPigWeight(@RequestBody PigWidth pigWeight,
                                  HttpServletRequest request,
                                  BindingResult bindingResult){
        logger.info("请求参数:{}"+ JSONObject.toJSONString(pigWeight));
        if(bindingResult.hasErrors()){
            throw new UserException(ResultStatueEnum.PARAMETER_ERROR);
        }
        pigWeight.setCreateTime(new Date());
        pigWeight.setUpdateTime(new Date());
        pigWeight.setChargeMan((String)request.getSession().getAttribute("user"));
        if(pigWeight.getChargeMan()==null){
            pigWeight.setChargeMan("admin");
        }
        return ResultUtil.loadResult(pigWeight,"save",pigWidthService);
    }

    /**
     * 更新信息
     * */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @ResponseBody
    public Object updatePigWeight(@RequestBody PigWidth pigWeight,
                                  @RequestParam(name = "id")Integer id,
                                  HttpServletRequest request,
                                  BindingResult bindingResult){
        logger.info("请求参数:{},id:{}"+ JSONObject.toJSONString(pigWeight),id);
        if(id==null){
            return ResultUtil.error(ResultStatueEnum.PARAMETER_ERROR);
        }
        if(bindingResult.hasErrors()){
            throw new UserException(ResultStatueEnum.PARAMETER_ERROR);
        }
        pigWeight.setId(id);
        pigWeight.setUpdateTime(new Date());
        return ResultUtil.loadResult(pigWeight,"update",pigWidthService);
    }

    /**
     * 删除数据信息
     * */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @ResponseBody
    public Object deletePigWeight(@RequestParam(name="id")Integer id){
        logger.info("[/pigweight/delete],请求参数:{}"+ id);
        PigWidth pigWeight = new PigWidth();
        pigWeight.setId(id);
        return ResultUtil.loadResult(pigWeight,"delete",pigWidthService);
    }

    @RequestMapping(value="/deletes",method = RequestMethod.POST)
    @ResponseBody
    public Object deleteCarioLogs(@RequestBody Integer[] ids){
        return pigWidthService.deletes(ids);
    }


}
