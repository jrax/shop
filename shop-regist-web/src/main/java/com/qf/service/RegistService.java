package com.qf.service;

import com.qf.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "shop-regist-service")
public interface RegistService {

    @RequestMapping("regist")
    public ResultVO regist(@RequestParam("name") String name,
                           @RequestParam("password") String password,
                           @RequestParam("codeID") String codeID
                           );

    @RequestMapping("registByEmail")
    public ResultVO registByEmail(@RequestParam("email") String email,
                           @RequestParam("password") String password
    );

    @RequestMapping("email/send")
    public ResultVO emailSend(@RequestParam("email") String email,
                                  @RequestParam("uuid") String uuid
    );

    @RequestMapping("email/active/account")
    public ResultVO activeAccount(@RequestParam(value = "uuid") String uuid);

}
