package com.hexin.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.hexin.user.enums.ResultStatueEnum;
import com.hexin.user.exception.UserException;
import com.hexin.user.model.PigLevel;
import com.hexin.user.model.SysUser;
import com.hexin.user.service.user.PigLevelService;
import com.hexin.user.service.user.SysUserService;
import com.hexin.user.utils.DateTimeUtil;
import com.hexin.user.utils.EncryptUtil;
import com.hexin.user.utils.ResultUtil;
import com.hexin.user.vo.PigLevelVO;
import com.hexin.user.vo.PigPoundsVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


/**
* Created by liyaohua on 2017-10-25.
*/
@Controller
@RequestMapping("/level")
public class PigLevelController {

    private static Logger logger = LoggerFactory.getLogger(PigLevelController.class);
    @Autowired
    PigLevelService pigLevelService;

    @RequestMapping(value="/getone",method = RequestMethod.GET)
    @ResponseBody
    public Object getOne(Integer id){
        logger.info("[/level/getone],request:id:{}",id);
        PigLevel pigLevel=new PigLevel();
        if(id!=null&&id!=0)
            pigLevel.setId(id);
        return ResultUtil.success(pigLevelService.select(pigLevel));
    }

    /**
     * 获取数据信息
     * */
    @RequestMapping(value="/getforpage")
    @ResponseBody
    public Object getforpage(@RequestParam(name="page",defaultValue = "1", required=false)Integer page,
                             @RequestParam(name="rows",defaultValue = "20",required=false)Integer rows,
                             @RequestParam(name="startTime",required = false)String startTime,
                             @RequestParam(name="endTime",required = false)String endTime){
        logger.info("[/level/getforpage],request:");
        PigLevelVO PigLevelVO = new PigLevelVO();
        PigLevelVO.setPage(page);
        PigLevelVO.setPageSize(rows);
        PigLevelVO.setStart(PigLevelVO.getStart());
        try {
            if(startTime!=null){
                PigLevelVO.setStartTime(DateTimeUtil.str2DateTime(startTime,"yyyy-MM-dd"));
            }
            if(endTime!=null){
                PigLevelVO.setEndTime(DateTimeUtil.str2DateTime(endTime,"yyyy-MM-dd"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<PigLevel> sysUserList = pigLevelService.selectByPage(PigLevelVO);
        Integer total = pigLevelService.countSelectByPage(PigLevelVO);
        JSONObject result=new JSONObject();
        result.put("rows", sysUserList);
        result.put("total", total);
        return result;
    }



    @RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
    public Object insertPigLevel(@RequestBody PigLevel pigLevel, BindingResult bindingResult){
        logger.info("[/level/save],request:sysUser:{}",pigLevel);
        if(bindingResult.hasErrors()){
            return ResultUtil.error(ResultStatueEnum.PARAMETER_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        Integer total = pigLevelService.countSelectByPage(new PigLevelVO());
        if(total!=null && total>0){
            return ResultUtil.error(ResultStatueEnum.DATA_ALREAD_EXISTS);
        }
        return ResultUtil.loadResult(pigLevel,"save",pigLevelService);
    }


    @RequestMapping(value="/update",method = RequestMethod.POST)
    @ResponseBody
    public Object updatePigLevel(@RequestBody PigLevel pigLevel,
                                @RequestParam(name = "id")Integer id){
        logger.info("[/level/save],request:pigLevel:{}",pigLevel);
        if("".equals(id)){
            return ResultUtil.error(ResultStatueEnum.PARAMETER_ERROR);
        }
        PigLevel pigLevel1 = new PigLevel();
        pigLevel1.setId(id);
        List<PigLevel> pigLevels = pigLevelService.select(pigLevel1);
        if(pigLevels.size()==0){
            return ResultUtil.error(ResultStatueEnum.USER_ERROR);
        }

        pigLevel.setId(id);
        return ResultUtil.loadResult(pigLevel,"update",pigLevelService);
    }


    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @ResponseBody
    public Object deletePigLevel(@RequestParam(name="id")Integer id){
        PigLevel pigLevel = new PigLevel();
        pigLevel.setId(id);
        return ResultUtil.loadResult(pigLevel,"delete",pigLevelService);
    }
}
