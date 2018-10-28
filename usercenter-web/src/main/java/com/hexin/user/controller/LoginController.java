package com.hexin.user.controller;

import com.hexin.user.constants.Constans;
import com.hexin.user.enums.ResultStatueEnum;
import com.hexin.user.model.PigPound;
import com.hexin.user.model.SysUser;
import com.hexin.user.service.user.PigPoundService;
import com.hexin.user.service.user.SysUserService;
import com.hexin.user.utils.EncryptUtil;
import com.hexin.user.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created on IDEA.
 * User: lyh
 * Date: 2017/10/29
 * Time: 20:06
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private PigPoundService pigPoundService;
    /**
     * 验证登录
     * */
    @RequestMapping(value="/dologin")
    @ResponseBody
    public Object doLogin(@RequestParam(name="username")String username,
                          @RequestParam(name="password")String password,
                          HttpServletRequest request){
        logger.info("[dologin],username:{}",username);
        if("".equals(username) || "".equals(password)){
            return ResultUtil.error(ResultStatueEnum.PARAMETER_ERROR);
        }
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        List<SysUser> dbUserList = sysUserService.select(sysUser);
        if(dbUserList.size()==0){
            return ResultUtil.error(ResultStatueEnum.LOGIN_ERROR);
        }

        if(!EncryptUtil.encrypt(password).equals(dbUserList.get(0).getPassword())){
            return ResultUtil.error(ResultStatueEnum.LOGIN_ERROR);
        }
        //记录用户信息session
        request.getSession().setAttribute("user",username);







        return ResultUtil.success();
    }

    private void initPoundData(){

    }
    /**
     * 退出登录
     *
     *
     * */
    @RequestMapping(value="/dologinout")
    public String doLoginOut(HttpServletRequest request){
        logger.info("[dologinout]-begin,sessionUser:{}",request.getSession().getAttribute("user"));
        //记录用户信息session
        request.getSession().removeAttribute("user");
        logger.info("[dologinout]-end,sessionUser:{}",request.getSession().getAttribute("user"));
        return "redirect:/index/tologin";
    }
}
