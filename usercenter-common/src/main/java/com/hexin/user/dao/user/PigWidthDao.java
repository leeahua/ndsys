package com.hexin.user.dao.user;

import com.hexin.user.common.BaseDao;
import com.hexin.user.model.PigWeight;
import com.hexin.user.model.PigWidth;
import com.hexin.user.vo.PigWeightVO;
import com.hexin.user.vo.PigWidthVO;

import java.util.List;


/**
* Created by liyaohua on 2017-10-25.
*/
public interface PigWidthDao extends BaseDao<PigWidth> {
    /**
     * 分页查询
     * */
    List<PigWidth> selectByPage(PigWidthVO record);

    /**
     * 分页查询个数
     * */
    Integer countSelectByPage(PigWidthVO record);

}