package com.hexin.user.controller;

import com.hexin.user.constants.Constans;
import com.hexin.user.serialfull.SerialCom2Observable;
import com.hexin.user.serialfull.SerialCom3Observable;
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

    private static String isopen ;

    @RequestMapping("/start")
    @ResponseBody
    public Object start( HttpServletRequest request){
        if(request.getSession().getAttribute("isopen")==null) {
            SerialCom2Observable serialCom2Observable = new SerialCom2Observable(pigWeightService);
            serialCom2Observable.send("");
            SerialCom3Observable serialCom3Observable = new SerialCom3Observable(pigWidthService);
            serialCom3Observable.send("");
            request.getSession().setAttribute("isopen","1");
        }
        return ResultUtil.success();


    }
}
