package com.qf.controller;

import com.qf.entity.TUser;
import com.qf.mapper.TUserMapper;
import com.qf.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class Loginservice {

    @Autowired
    private TUserMapper userMapper;

    @RequestMapping("login")
    @ResponseBody
    public ResultVO login(@RequestParam String uname, @RequestParam String password, HttpServletRequest request){
        HashMap map = new HashMap();
        map.put("uname",uname);
        map.put("password",password);
        TUser user = userMapper.selectforLogin(map);
        if(user!=null){
            return new ResultVO(200,"",user);
        }else{
            return new ResultVO(400,"",user);
        }
    }
}
