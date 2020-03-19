package com.qf.mapper;

import com.qf.entity.TGoodsInfo;

import java.util.List;

public interface TGoodsInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TGoodsInfo record);

    int insertSelective(TGoodsInfo record);

    TGoodsInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TGoodsInfo record);

    int updateByPrimaryKey(TGoodsInfo record);

    List<TGoodsInfo> selectAll();

    List<TGoodsInfo> selectByIds(List<Integer> ids);
}