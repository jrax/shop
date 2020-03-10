package com.qf.controller;

import com.qf.entity.TUser;
import com.qf.mapper.TUserMapper;
import com.qf.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegistController {

    @Autowired
    private TUserMapper userMapper;

    @RequestMapping("regist")
    @ResponseBody
    public ResultVO getResult(@RequestParam String name,@RequestParam String password,@RequestParam String codeID){
        TUser user = new TUser();
        user.setName(name);
        user.setPassword(password);
        int insert = userMapper.insert(user);
        ResultVO resultVO = new ResultVO();

        if (insert>0){
            resultVO.setCode(200);
            resultVO.setData("");
            resultVO.setMsg("添加成功!!!");
        }else {

            resultVO.setCode(400);
            resultVO.setData("");
            resultVO.setMsg("添加失败!!!");
        }

        return resultVO;
    }
}
