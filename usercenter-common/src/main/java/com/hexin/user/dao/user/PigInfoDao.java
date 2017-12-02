package com.hexin.user.dao.user;


import com.hexin.user.model.PigInfo;
import com.hexin.user.model.PigWeight;
import com.hexin.user.vo.PigInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created on IDEA.
 * User: lyh
 * Date: 2017/12/1
 * Time: 22:28
 */
public interface PigInfoDao {
    /**
     * 分页查询
     * */
    List<PigInfo> selectByPage(PigInfoVO record);

    /**
     * 分页查询个数
     * */
    Integer countSelectByPage(PigInfoVO record);

    List<Map<String,String>> statisticsDataByDate(@Param("date") String date);

}
