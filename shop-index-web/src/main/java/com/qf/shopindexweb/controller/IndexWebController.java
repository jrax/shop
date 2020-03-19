package com.qf.shopindexweb.controller;

import com.qf.constant.CookieConstant;
import com.qf.constant.RedisConstant;
import com.qf.entity.TGoodsInfo;
import com.qf.entity.TGoodsType;
import com.qf.entity.TUser;
import com.qf.mapper.TGoodsTypeMapper;
import com.qf.shopindexweb.service.IndexServiceController;
import com.qf.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class IndexWebController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IndexServiceController serviceController;

    @RequestMapping("redis")
    @ResponseBody
    public String setredis(){
        List<TGoodsInfo> goodsInfos = serviceController.getGoodsInfo();
        redisTemplate.opsForValue().set(RedisConstant.GOODS_INFOS,goodsInfos);
        return "ok";
    }

    @RequestMapping("showIndex")
    public String showIndex(ModelMap map){
        List<TGoodsType> goodsTypes = serviceController.getGoodsType();
        List<TGoodsInfo> goodsInfos = (List<TGoodsInfo>) redisTemplate.opsForValue().get(RedisConstant.GOODS_INFOS);
        map.put("goodsInfoList",goodsInfos);
        map.put("productTypeList",goodsTypes);
        return "index";
    }

    @RequestMapping("checkLogin")
    @ResponseBody
    public ResultVO checkLogin(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        Map user = null;
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals(CookieConstant.USER_LOGIN)){
                    String uuid = cookie.getValue();
                    String key = RedisConstant.USER_LOGIN_PRE + uuid;
                    user = (Map) redisTemplate.opsForValue().get(key);
                }
            }
        }
        if(user!=null){
            return new ResultVO(200,"已登录",user);
        }else {
            return new ResultVO(400,"未登录",null);
        }

    }

}
