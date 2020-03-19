package com.qf.service;

import com.qf.entity.TGoodsInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient(value = "shop-search-service")
public interface SearchWebService {

    @RequestMapping("selectAll")
    @ResponseBody
    List<TGoodsInfo> selectAll();

    @RequestMapping("showIntroduct")
    @ResponseBody
    TGoodsInfo selectByID(@RequestParam("id") Integer id);

}
