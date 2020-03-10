package com.qf.service;

import com.qf.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "shop-regist-service")
public interface RegistService {

    @RequestMapping("regist")
    public ResultVO regist(@RequestParam("name") String name,
                           @RequestParam("password") String password,
                           @RequestParam("codeID") String codeID
                           );
}
