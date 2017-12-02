package com.hexin.user.service.user;

import com.hexin.user.dao.user.PigInfoDao;
import com.hexin.user.model.PigInfo;
import com.hexin.user.vo.PigInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* Created by liyaohua on 2017-10-25.
*/
@Service
public class PigInfoService {

    @Autowired
    PigInfoDao pigInfoDao;

    public List<PigInfo> selectByPage(PigInfoVO pigInfoVO) {
        return pigInfoDao.selectByPage(pigInfoVO);
    }

    public Integer countSelectByPage(PigInfoVO pigInfoVO) {
        return pigInfoDao.countSelectByPage(pigInfoVO);
    }

    public List<Map<String,String>> statisticsDataByDate(String date){
        return pigInfoDao.statisticsDataByDate(date);
    }
}