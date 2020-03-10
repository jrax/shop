package com.qf.controller;

import com.qf.service.RegistService;
import com.qf.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegistWebController {

    @Autowired
    private RegistService registService;

    @RequestMapping("regist")
    @ResponseBody
    public ResultVO getResult(@RequestParam String name, @RequestParam String password, @RequestParam String codeID){
        return registService.regist(name,password,codeID);
    }

}
