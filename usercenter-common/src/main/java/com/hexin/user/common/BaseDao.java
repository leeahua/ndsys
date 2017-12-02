package com.hexin.user.common;

import com.hexin.user.model.PigPound;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created on IDEA.
 * User: lyh
 * Date: 2017/10/25
 * Time: 23:30
 */
public interface BaseDao<T> {
    int insert(T t);

    int delete(T t);

    int deletes(@Param("ids") Integer[] ids);

    int update(T t);

    List<T> select(T t);
}
