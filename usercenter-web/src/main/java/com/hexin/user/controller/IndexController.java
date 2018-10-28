package com.hexin.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 负责全局的跳转.
 * User: lyh
 * Date: 2017/10/28
 * Time: 17:28
 */
@Controller
@RequestMapping("/index/")
public class IndexController {

    private boolean checkLogin(HttpServletRequest request){
        if(((String)request.getSession().getAttribute("user")) ==null){
            return false;
        }
        return true;
    }
    /**
     * 跳转到主页面
     * */
    @RequestMapping("/toindex")
    public String toIndex(HttpServletRequest request){
        if(!checkLogin(request)){
            return "login";
        }
        return "index";
    }
    /**
     * 跳转到主页面
     * */
    @RequestMapping("/topiginfo")
    public String toPigInfo(HttpServletRequest request){
        if(!checkLogin(request)){
            return "login";
        }
        return "piginfo";
    }

    @RequestMapping("/tostatic")
    public String tostatic(HttpServletRequest request){
        if(!checkLogin(request)){
            return "login";
        }
        return "pigstatistics";
    }
    /**
     * 跳转到猪肉详细信息维护界面
     * */
    @RequestMapping("/topigweight")
    public String toPigWeight(HttpServletRequest request){
        if(!checkLogin(request)){
            return "login";
        }
        return "pigweight";
    }

    /**
     * 跳转到猪肉详细信息维护界面
     * */
    @RequestMapping("/topigwidth")
    public String topigwidth(HttpServletRequest request){
        if(!checkLogin(request)){
            return "login";
        }
        return "pigwidth";
    }
    /**
     * 跳转到主页面
     * */
    @RequestMapping("/topigpounds")
    public String toPigPound(HttpServletRequest request){
        if(!checkLogin(request)){
            return "login";
        }
        return "pigpounds";
    }

    /**
     * 跳转到登录页面
     * */
    @RequestMapping("/tologin")
    public String toLogin(){
        return "login";
    }
    /**
     * 跳转到用户页面
     * */
    @RequestMapping("/touser")
    public String toUser(HttpServletRequest request){
        if(!checkLogin(request)){
            return "login";
        }
        return "sysuser";
    }

    /**
     * 跳转到登录页面
     * */
    @RequestMapping("/towelcome")
    public String toWelcome(HttpServletRequest request){
        if(!checkLogin(request)){
            return "login";
        }
        return "welcome";
    }

    /**
     * 跳转到登录页面
     * */
    @RequestMapping("/tolevel")
    public String toLevel(HttpServletRequest request){
        if(!checkLogin(request)){
            return "login";
        }
        return "piglevel";
    }

}
