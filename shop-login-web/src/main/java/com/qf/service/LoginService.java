package com.qf.service;

import com.qf.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "shop-login-service")
public interface LoginService {

    @RequestMapping("login")
    @ResponseBody
    ResultVO login(@RequestParam("uname") String uname,@RequestParam("password") String password);
}
