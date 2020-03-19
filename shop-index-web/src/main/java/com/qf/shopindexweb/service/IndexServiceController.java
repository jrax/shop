package com.qf.shopindexweb.service;

import com.qf.entity.TGoodsInfo;
import com.qf.entity.TGoodsType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "shop-index-service")
public interface IndexServiceController {

    @RequestMapping("goodstype")
    List<TGoodsType> getGoodsType();

    @RequestMapping("goodsInfo")
    List<TGoodsInfo> getGoodsInfo();

}
