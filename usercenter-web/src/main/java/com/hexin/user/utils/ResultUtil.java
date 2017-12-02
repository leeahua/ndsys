package com.hexin.user.utils;


import com.hexin.user.enums.ResultStatueEnum;
import com.hexin.user.exception.UserException;
import com.hexin.user.model.PigPound;
import com.hexin.user.model.PigWeight;
import com.hexin.user.model.PigWidth;
import com.hexin.user.model.SysUser;
import com.hexin.user.service.user.PigPoundService;
import com.hexin.user.service.user.PigWeightService;
import com.hexin.user.service.user.PigWidthService;
import com.hexin.user.service.user.SysUserService;
import com.hexin.user.vo.ResultVO;

import java.util.Arrays;

/**
 * 响应参数封装
 * @date:2017/10/10
 * @time:16:36
 * @uthor:LYH
 */
public class ResultUtil {

    private ResultUtil(){}
    /**
     * 成功返回结果集
     * @param data 响应数据
     * @return ResultVO
     */
    public static ResultVO success(Object data) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultStatueEnum.SUCCESS.getCode());
        resultVO.setMsg(ResultStatueEnum.SUCCESS.getMsg());
        resultVO.setData(Arrays.asList(data));
        return resultVO;
    }
    /**
     * 成功返回结果集
     * @return ResultVO
     */
    public static ResultVO success() {
        return success(null);
    }
    /**
     * 失败返回结果集
     * @return ResultVO
     */
    public static ResultVO error(ResultStatueEnum resultStatueEnum) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(resultStatueEnum.getCode());
        resultVO.setMsg(resultStatueEnum.getMsg());
        resultVO.setData(null);
        return resultVO;
    }
    /**
     * 失败返回结果集
     * @return ResultVO
     */
    public static ResultVO error() {
        return error(ResultStatueEnum.ERROE);
    }

    public static ResultVO loadResult(Object dataObject, String type,Object businessService){
        int result = 0;
        if(businessService instanceof PigPoundService){
            PigPoundService pigPoundService = (PigPoundService)businessService;
            PigPound pigPound = (PigPound) dataObject;
            try {
                switch (type){
                    case "save":
                        result = pigPoundService.insert(pigPound);
                        break;
                    case "update":
                        result = pigPoundService.update(pigPound);
                        break;
                    case "delete":
                        result = pigPoundService.delete(pigPound);
                        break;
                    default:
                        result = 0;
                        break;
                }
            }catch(Exception e){
                throw new UserException(ResultStatueEnum.ERROE);

            }
        }
        if(businessService instanceof PigWeightService){
            PigWeightService pigWeightService = (PigWeightService)businessService;
            PigWeight pigWeight = (PigWeight)dataObject;
            try {
                switch (type){
                    case "save":
                        result = pigWeightService.insert(pigWeight);
                        break;
                    case "update":
                        result = pigWeightService.update(pigWeight);
                        break;
                    case "delete":
                        result = pigWeightService.delete(pigWeight);
                        break;
                    default:
                        return null;
                }
            }catch(Exception e){
                throw new UserException(ResultStatueEnum.ERROE);

            }
        }
        if(businessService instanceof PigWidthService){
            PigWidthService pigWidthService = (PigWidthService)businessService;
            PigWidth pigWeight = (PigWidth)dataObject;
            try {
                switch (type){
                    case "save":
                        result = pigWidthService.insert(pigWeight);
                        break;
                    case "update":
                        result = pigWidthService.update(pigWeight);
                        break;
                    case "delete":
                        result = pigWidthService.delete(pigWeight);
                        break;
                    default:
                        return null;
                }
            }catch(Exception e){
                throw new UserException(ResultStatueEnum.ERROE);

            }
        }

        if(businessService instanceof SysUserService){
            SysUserService sysUserService = (SysUserService)businessService;
            SysUser sysUser = (SysUser)dataObject;
            try {
                switch (type){
                    case "save":
                        result = sysUserService.insert(sysUser);
                        break;
                    case "update":
                        result = sysUserService.update(sysUser);
                        break;
                    case "delete":
                        result = sysUserService.delete(sysUser);
                        break;
                    default:
                        return null;
                }
            }catch(Exception e){
                throw new UserException(ResultStatueEnum.ERROE);

            }
        }
        if(result==1){
            return ResultUtil.success();
        }else{
            return ResultUtil.error(ResultStatueEnum.ERROE);
        }

    }


}
