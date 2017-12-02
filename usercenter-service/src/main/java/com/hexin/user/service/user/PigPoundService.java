package com.hexin.user.service.user;


import com.hexin.user.dao.user.PigPoundDao;
import com.hexin.user.model.PigPound;
import com.hexin.user.vo.PigPoundsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* Created by liyaohua on 2017-10-25.
*/
@Service
public class PigPoundService {

    @Autowired
    PigPoundDao pigPoundDao;

    public int insert(PigPound pigPound){
        pigPound.setCreateTime(new Date());
        return pigPoundDao.insert(pigPound);
    }

    public int delete(PigPound pigPound){
        return pigPoundDao.delete(pigPound);
    }

    public int deletes(Integer[] ids) {
        return  pigPoundDao.deletes(ids);
    }

    public int update(PigPound pigPound){
        return pigPoundDao.update(pigPound);
    }

    public List<PigPound> select(PigPound pigPound){
        return pigPoundDao.select(pigPound);
    }

    public List<PigPound> selectByPage(PigPoundsVO pigPoundsVO){
        return pigPoundDao.selectByPage(pigPoundsVO);
    }

    public Integer countSelectByPage(PigPoundsVO pigPoundsVO){
        return pigPoundDao.countSelectByPage(pigPoundsVO);
    }
}