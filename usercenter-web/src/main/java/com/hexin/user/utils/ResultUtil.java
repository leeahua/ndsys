package com.hexin.user.utils;


import com.hexin.user.constants.Constans;
import com.hexin.user.enums.ResultStatueEnum;
import com.hexin.user.exception.UserException;
import com.hexin.user.model.*;
import com.hexin.user.service.user.*;
import com.hexin.user.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * 响应参数封装
 * @date:2017/10/10
 * @time:16:36
 * @uthor:LYH
 */
public class ResultUtil {
    private static final Logger log = LoggerFactory.getLogger(ResultUtil.class);
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
    public static ResultVO error(String code, String message) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(message);
        resultVO.setData(null);
        return resultVO;
    }
    private static void updatePigLevelMap(PigLevel pigLevel){
        log.info("开始修改等级阀值");
        Constans.levelDataMap.put("LV1",pigLevel.getLevel1().toString());
        Constans.levelDataMap.put("LV2",pigLevel.getLevel2().toString());
        Constans.levelDataMap.put("LV3",pigLevel.getLevel3().toString());
        Constans.levelDataMap.put("LV4",pigLevel.getLevel4().toString());
        log.info("开始修改等级阀值，pigLevel：{}",pigLevel);
    }
    private static void updatePigWeightMap(PigPound pigPound){
        log.info("开始修改底榜数据");
        Constans.poundData.put("pound",Double.toString(pigPound.getBotpounds()));
        Constans.poundData.put("batchNum",pigPound.getBatchNum());
        log.info("开始修改底榜数据，pigPound：{}",pigPound);
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
        if(businessService instanceof PigLevelService){
            PigLevelService pigPoundService = (PigLevelService)businessService;
            PigLevel pigLevel = (PigLevel) dataObject;
            try {
                switch (type){
                    case "save":
                        result = pigPoundService.insert(pigLevel);
                        if(result>0) {
                            updatePigLevelMap(pigLevel);
                        }
                        break;
                    case "update":
                        result = pigPoundService.update(pigLevel);
                        if(result>0) {
                            updatePigLevelMap(pigLevel);
                        }
                        break;
                    case "delete":
                        result = pigPoundService.delete(pigLevel);
                        break;
                    default:
                        result = 0;
                        break;
                }
            }catch(Exception e){
                log.error(e.getMessage());
                throw new UserException(ResultStatueEnum.ERROE);

            }
        }
        if(businessService instanceof PigPoundService){
            PigPoundService pigPoundService = (PigPoundService)businessService;
            PigPound pigPound = (PigPound) dataObject;
            try {
                switch (type){
                    case "save":
                        result = pigPoundService.insert(pigPound);
                        if(result>0){
                            updatePigWeightMap(pigPound);
                        }
                        break;
                    case "update":
                        result = pigPoundService.update(pigPound);
                        if(result>0){
                            updatePigWeightMap(pigPound);
                        }
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
