package com.qf.controller;

import com.qf.constant.RedisConstant;
import com.qf.service.RegistService;
import com.qf.vo.ResultVO;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class RegistWebController {

    @Autowired
    private RegistService registService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("regist")
    @ResponseBody
    public ResultVO getResult(@RequestParam String name, @RequestParam String password, @RequestParam String codeID){
        return registService.regist(name,password,codeID);
    }

    @RequestMapping("register")
    public String registHtml(){
        return "register";
    }

    @RequestMapping("registByEmail")
    @ResponseBody
    public ResultVO registByEmail(@RequestParam String email, @RequestParam String password){
        ResultVO resultVO = new ResultVO();
        //1.生成uuid
        String uuid = UUID.randomUUID().toString();
        //2.发邮件
        ResultVO result = registService.emailSend(email, uuid);
        if(result.getCode()==200){
            //往redis中存入这样的键值对： regist:email:url:128731-sdfl-123 = addr
            String key = RedisConstant.REGIST_EMAIL_URL_KEY_PRE + uuid;
            redisTemplate.opsForValue().set(key,email,15,TimeUnit.MINUTES);
            //存入数据库,创建该用户
            registService.registByEmail(email, password);
            resultVO.setCode(200);
            resultVO.setMsg("注册成功");
        }else {
            resultVO.setCode(400);
            resultVO.setMsg("注册失败");
        }

        return resultVO;
    }

    @RequestMapping("email/active/account")
    @ResponseBody
    public void activeAccount(@RequestParam String uuid){
        registService.activeAccount(uuid);
    }
}
