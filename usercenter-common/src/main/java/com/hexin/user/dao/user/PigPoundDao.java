package com.hexin.user.dao.user;

import com.hexin.user.common.BaseDao;
import com.hexin.user.model.PigPound;
import com.hexin.user.vo.PigPoundsVO;

import java.util.List;


/**
 * 底磅
* Created by liyaohua on 2017-10-25.
*/
public interface PigPoundDao extends BaseDao<PigPound> {
    /**
     * 分页查询
     * */
    List<PigPound> selectByPage(PigPoundsVO pigPoundsVO);
    PigPound selectOne();
    /**
     * 分页查询个数
     * */
    Integer countSelectByPage(PigPoundsVO pigPoundsVO);
}