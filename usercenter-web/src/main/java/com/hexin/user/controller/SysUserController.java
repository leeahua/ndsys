package com.hexin.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.hexin.user.enums.ResultStatueEnum;
import com.hexin.user.service.user.SysUserService;
import com.hexin.user.model.SysUser;
import java.util.List;
import com.hexin.user.utils.EncryptUtil;
import com.hexin.user.utils.ResultUtil;
import com.hexin.user.vo.SysUserVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
* Created by liyaohua on 2017-10-25.
*/
@Controller
@RequestMapping("/user")
public class SysUserController {

    private static Logger logger = LoggerFactory.getLogger(SysUserController.class);
    @Autowired
    SysUserService sysUserService;

    @RequestMapping(value="/getone",method = RequestMethod.GET)
    @ResponseBody
    public Object getOne(Integer id){
        logger.info("[/user/getone],request:id:{}",id);
        SysUser sysUser=new SysUser();
        if(id!=null&&id!=0)
            sysUser.setId(id);
        return ResultUtil.success(sysUserService.select(sysUser));
    }

    /**
     * 获取数据信息
     * */
    @RequestMapping(value="/getforpage")
    @ResponseBody
    public Object getforpage(@RequestParam(name="page",defaultValue = "1", required=false)Integer page,
                             @RequestParam(name="rows",defaultValue = "20",required=false)Integer rows,
                             @RequestParam(name="username",required = false)String username){
        logger.info("[/user/getforpage],request:username:{}",username);
        SysUserVO sysUserVO = new SysUserVO();
        sysUserVO.setPage(page);
        sysUserVO.setPageSize(rows);
        sysUserVO.setStart(sysUserVO.getStart());
        sysUserVO.setUsername(username);
        List<SysUser> sysUserList = sysUserService.selectByPage(sysUserVO);
        Integer total = sysUserService.countSelectByPage(sysUserVO);
        JSONObject result=new JSONObject();
        result.put("rows", sysUserList);
        result.put("total", total);
        return result;
    }



    @RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
    public Object insertSysUser(@RequestBody SysUser sysUser){
        logger.info("[/user/save],request:sysUser:{}",sysUser);
        sysUser.setPassword(EncryptUtil.encrypt(sysUser.getPassword()));
        return ResultUtil.loadResult(sysUser,"save",sysUserService);
    }


    @RequestMapping(value="/update",method = RequestMethod.POST)
    @ResponseBody
    public Object updateSysUser(@RequestBody SysUser sysUser,
                                @RequestParam(name = "id")Integer id){
        logger.info("[/user/save],request:sysUser:{}",sysUser);
        if("".equals(id)){
            return ResultUtil.error(ResultStatueEnum.PARAMETER_ERROR);
        }
        SysUser queryUser = new SysUser();
        queryUser.setId(id);
        List<SysUser> dbUser = sysUserService.select(queryUser);
        if(dbUser.size()==0){
            return ResultUtil.error(ResultStatueEnum.USER_ERROR);
        }
        if(!StringUtils.isBlank(sysUser.getPassword())&&!sysUser.getPassword().equals(dbUser.get(0).getPassword())){
            sysUser.setPassword(EncryptUtil.encrypt(sysUser.getPassword()));
        }
        if(StringUtils.isBlank(sysUser.getPassword())){
            sysUser.setPassword(null);
        }
        sysUser.setId(id);
        return ResultUtil.loadResult(sysUser,"update",sysUserService);
    }


    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @ResponseBody
    public Object deleteSysUser(@RequestParam(name="id")Integer id){
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        return ResultUtil.loadResult(sysUser,"delete",sysUserService);
    }
}
