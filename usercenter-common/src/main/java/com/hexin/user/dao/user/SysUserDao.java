package com.hexin.user.dao.user;

import com.hexin.user.common.BaseDao;
import com.hexin.user.model.SysUser;
import java.util.List;

import com.hexin.user.vo.SysUserVO;

/**
* Created by liyaohua on 2017-10-25.
*/
public interface SysUserDao extends BaseDao<SysUser> {
    /**
     * 分页查询
     * */
    List<SysUser> selectByPage(SysUserVO record);

    /**
     * 分页查询个数
     * */
    Integer countSelectByPage(SysUserVO record);

}