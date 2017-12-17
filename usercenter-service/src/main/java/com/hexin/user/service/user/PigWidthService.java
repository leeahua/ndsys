package com.hexin.user.service.user;

import com.hexin.user.dao.user.PigWidthDao;
import com.hexin.user.model.PigWidth;
import com.hexin.user.vo.PigWidthVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* Created by liyaohua on 2017-10-25.
*/
@Service
public class PigWidthService {

    @Autowired
    PigWidthDao pigWidthDao;

    public int insert(PigWidth pigWidth){
        pigWidth.setCreateTime(new Date());
        pigWidth.setUpdateTime(new Date());
        pigWidth.setChargeMan("admin");
        return pigWidthDao.insert(pigWidth);
    }

    public int delete(PigWidth pigWidth){
        return pigWidthDao.delete(pigWidth);
    }

    public int deletes(Integer[] ids) {
        return  pigWidthDao.deletes(ids);
    }

    public int update(PigWidth pigWidth){
        pigWidth.setUpdateTime(new Date());
        return pigWidthDao.update(pigWidth);
    }

    public List<PigWidth> select(PigWidth pigWidth){
        return pigWidthDao.select(pigWidth);
    }

    public List<PigWidth> selectByPage(PigWidthVO pigWidthVO) {
        return pigWidthDao.selectByPage(pigWidthVO);
    }

    public Integer countSelectByPage(PigWidthVO pigWidthVO) {
        return pigWidthDao.countSelectByPage(pigWidthVO);
    }
}