package com.qf.service;

import com.qf.vo.GoodsInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient(value = "shop-cart-service")
public interface CartWebService {

    @RequestMapping("selectByIds")
    @ResponseBody
    List<GoodsInfoVO> selectByIds(@RequestParam("name") String name);
}
