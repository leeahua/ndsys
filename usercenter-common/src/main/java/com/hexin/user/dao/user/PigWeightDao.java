package com.hexin.user.dao.user;

import com.hexin.user.common.BaseDao;
import com.hexin.user.model.PigInfo;
import com.hexin.user.model.PigWeight;
import com.hexin.user.model.PigWidth;
import com.hexin.user.vo.PigWeightVO;

import java.util.List;


/**
* Created by liyaohua on 2017-10-25.
*/
public interface PigWeightDao extends BaseDao<PigWeight> {
    /**
     * 分页查询
     * */
    List<PigWeight> selectByPage(PigWeightVO record);

    /**
     * 分页查询个数
     * */
    Integer countSelectByPage(PigWeightVO record);

    /**
     * 获取指定批次的最新的一条数据
     * */
    PigWeight selectLasterByBatchNo(String batchNo);

}