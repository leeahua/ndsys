package com.hexin.user.dao.user;

import com.hexin.user.common.BaseDao;
import com.hexin.user.model.PigLevel;
import com.hexin.user.vo.PigLevelVO;

import java.util.List;

public interface PigLevelDao extends BaseDao<PigLevel> {
    /**
     * 分页查询
     * */
    List<PigLevel> selectByPage(PigLevelVO pigLevelVO);


    PigLevel selectOne();
    /**
     * 分页查询个数
     * */
    Integer countSelectByPage(PigLevelVO pigLevelVO);
}