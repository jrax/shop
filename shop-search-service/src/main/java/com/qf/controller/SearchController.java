package com.qf.controller;

import com.qf.entity.TGoodsInfo;
import com.qf.mapper.TGoodsInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private TGoodsInfoMapper goodsInfoMapper;

    @RequestMapping("selectAll")
    @ResponseBody
    public List<TGoodsInfo> selectAll(){
        List<TGoodsInfo> goodsInfos = goodsInfoMapper.selectAll();
        return goodsInfos;
    }


    @RequestMapping("showIntroduct")
    @ResponseBody
    public TGoodsInfo showIntroduct(@RequestParam Integer id){
        TGoodsInfo tGoodsInfo = goodsInfoMapper.selectByPrimaryKey(id);
        return tGoodsInfo;
    }
}
