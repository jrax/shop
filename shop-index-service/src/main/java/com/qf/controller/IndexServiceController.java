package com.qf.controller;

import com.qf.entity.TGoodsInfo;
import com.qf.entity.TGoodsType;
import com.qf.mapper.TGoodsInfoMapper;
import com.qf.mapper.TGoodsTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class IndexServiceController {

    @Autowired
    private TGoodsTypeMapper goodsTypeMapper;
    @Autowired
    private TGoodsInfoMapper goodsInfoMapper;

    @RequestMapping("goodstype")
    @ResponseBody
    public List<TGoodsType> getGoodsType(){
        List<TGoodsType> goodsTypes = goodsTypeMapper.selectAll();
        return goodsTypes;
    }

    @RequestMapping("goodsInfo")
    @ResponseBody
    public  List<TGoodsInfo> getGoodsInfo(){
        List<TGoodsInfo> tGoodsInfos = goodsInfoMapper.selectAll();
        return tGoodsInfos;
    }
}
