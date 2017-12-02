package com.hexin.user.service.user;

import com.hexin.user.dao.user.SysUserDao;
import com.hexin.user.model.SysUser;
import com.hexin.user.vo.SysUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* Created by liyaohua on 2017-10-25.
*/
@Service
public class SysUserService {

    @Autowired
    SysUserDao sysUserDao;

    public int insert(SysUser sysUser){
        return sysUserDao.insert(sysUser);
    }
    public int delete(SysUser sysUser){
        return sysUserDao.delete(sysUser);
    }
    public int deletes(Integer[] ids) {
        return  sysUserDao.deletes(ids);
    }
    public int update(SysUser sysUser){
        return sysUserDao.update(sysUser);
    }
    public List<SysUser> select(SysUser sysUser){
        return sysUserDao.select(sysUser);
    }
    public List<SysUser> selectByPage(SysUserVO sysUserVO){
        return sysUserDao.selectByPage(sysUserVO);
    }
    public Integer countSelectByPage(SysUserVO sysUserVO){
        return sysUserDao.countSelectByPage(sysUserVO);
    }
}