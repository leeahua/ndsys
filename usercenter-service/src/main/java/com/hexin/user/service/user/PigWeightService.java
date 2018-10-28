package com.hexin.user.service.user;

import com.hexin.user.dao.user.PigWeightDao;
import com.hexin.user.model.PigWeight;
import com.hexin.user.model.PigWidth;
import com.hexin.user.vo.PigWeightVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
* Created by liyaohua on 2017-10-25.
*/
@Service
public class PigWeightService {

    @Autowired
    PigWeightDao pigWeightDao;

    public int insert(PigWeight pigWeight){
        pigWeight.setCreateTime(new Date());
        pigWeight.setUpdateTime(new Date());
        return pigWeightDao.insert(pigWeight);
    }

    public int delete(PigWeight pigWeight){
        return pigWeightDao.delete(pigWeight);
    }

    public int deletes(Integer[] ids) {
        return  pigWeightDao.deletes(ids);
    }

    public int update(PigWeight pigWeight){
        pigWeight.setUpdateTime(new Date());
        return pigWeightDao.update(pigWeight);
    }

    public List<PigWeight> select(PigWeight pigWeight){
        return pigWeightDao.select(pigWeight);
    }

    public List<PigWeight> selectByPage(PigWeightVO pigWeightVO) {
        return pigWeightDao.selectByPage(pigWeightVO);
    }

    public Integer countSelectByPage(PigWeightVO pigWeightVO) {
        return pigWeightDao.countSelectByPage(pigWeightVO);
    }
    public PigWeight selectLasterByBatchNo(String batchNo) {
        return pigWeightDao.selectLasterByBatchNo(batchNo);
    }
}