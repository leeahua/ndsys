package com.hexin.user.service.user;


import com.hexin.user.dao.user.PigLevelDao;
import com.hexin.user.model.PigLevel;
import com.hexin.user.model.PigPound;
import com.hexin.user.model.PigWidth;
import com.hexin.user.vo.PigLevelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* Created by liyaohua on 2017-10-25.
*/
@Service
public class PigLevelService {

    @Autowired
    PigLevelDao pigLevelDao;

    public int insert(PigLevel pigLevel){
        pigLevel.setCreateBy("admain");
        pigLevel.setCreateTime(new Date());
        pigLevel.setUpdateBy("admin");
        pigLevel.setUpdateTime(new Date());
        return pigLevelDao.insert(pigLevel);
    }

    public List<PigLevel> select(PigLevel pigLevel){
        return pigLevelDao.select(pigLevel);
    }

    public int update(PigLevel pigLevel){
        pigLevel.setUpdateBy("admin");
        pigLevel.setUpdateTime(new Date());
        return pigLevelDao.update(pigLevel);
    }



    public List<PigLevel> selectByPage(PigLevelVO pigLevelsVO){
        return pigLevelDao.selectByPage(pigLevelsVO);
    }

    public Integer countSelectByPage(PigLevelVO PigLevelsVO){
        return pigLevelDao.countSelectByPage(PigLevelsVO);
    }
    public PigLevel selectOne(){
        return pigLevelDao.selectOne();
    }

    public int delete(PigLevel pigLevel) {
        return pigLevelDao.delete(pigLevel);
    }
}