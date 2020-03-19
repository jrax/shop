package com.qf.mapper;

import com.qf.entity.TUser;
import com.sun.org.glassfish.gmbal.ParameterNames;

import java.util.Map;


public interface TUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TUser record);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);

    TUser selectByEmail(String email);

    TUser selectforLogin (Map map);

}