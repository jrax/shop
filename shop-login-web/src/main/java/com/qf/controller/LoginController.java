package com.qf.controller;

import com.qf.constant.CookieConstant;
import com.qf.constant.RedisConstant;
import com.qf.service.LoginService;
import com.qf.vo.CartItem;
import com.qf.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("loginVisit")
    public String loginVisit(){
        return "login";
    }

    @RequestMapping("login")
    @ResponseBody
    public ResultVO login(@RequestParam String uname,
                          @RequestParam String password,
                          HttpServletResponse response, HttpServletRequest request){
        ResultVO result = loginService.login(uname, password);
        if (result.getCode()==200){

            //更新redis中的user购物车
            String userCartKey = RedisConstant.CART_KEY + uname;
            List<CartItem> userCartItems = (List<CartItem>) redisTemplate.opsForValue().get(userCartKey);
            //user购物车有没有数据或者存不存在
            if(userCartItems == null) {
                Cookie[] cookiesCart = request.getCookies();
                //redis中有没有cookie购物车
                for (Cookie cookie : cookiesCart) {
                    if (cookie.getName().equals(CookieConstant.USER_CART)) {
                        String uuid = cookie.getValue();
                        String cookieCartKey = RedisConstant.CART_KEY + uuid;
                        List<CartItem> cookieCartItems = (List<CartItem>) redisTemplate.opsForValue().get(cookieCartKey);
                        //redis的cookie购物车
                        if (cookieCartItems != null) {
                            //把数据存到reids的user购物车中
                            redisTemplate.opsForValue().set(userCartKey, cookieCartItems, 30, TimeUnit.DAYS);
                            //清空redis的cookie购物车数据
                            redisTemplate.opsForValue().set(cookieCartKey, null, 30, TimeUnit.DAYS);
                        }
                    }
                }
            }else {
                Cookie[] cookiesCart = request.getCookies();
                //redis中有没有cookie购物车
                for (Cookie cookie : cookiesCart){
                    if(cookie.getName().equals(CookieConstant.USER_CART)){
                        String uuid = cookie.getValue();
                        String cookieCartKey = RedisConstant.CART_KEY + uuid;
                        List<CartItem>  cookieCartItems = (List<CartItem>) redisTemplate.opsForValue().get(cookieCartKey);

                        //redis的cookie购物车
                        if(cookieCartItems != null){
                            List<CartItem> cartItemSave = new ArrayList<>();
                            for (CartItem userCartItem : userCartItems) {
                                for (CartItem cookieCartItem : cookieCartItems) {
                                    if(cookieCartItem.getId().equals(userCartItem.getId())){
                                        Integer numberUser = userCartItem.getNumber();
                                        Integer numberCookie = cookieCartItem.getNumber();
                                        Integer numberOff = numberUser + numberCookie;
                                        Date date = new Date();
                                        userCartItem.setNumber(numberOff);
                                        userCartItem.setTimer(date);
                                        //将重复的cookieCartItem储存起来
                                        cartItemSave.add(cookieCartItem);
                                    }
                                }

                                //去重
                                for (CartItem cartItem : cartItemSave) {
                                    cookieCartItems.remove(cartItem);
                                }
                                //合并
                                for (CartItem cookieCartItem : cookieCartItems) {
                                    userCartItems.add(cookieCartItem);
                                }
                                //把数据存到reids的user购物车中
                                redisTemplate.opsForValue().set(userCartKey,userCartItems,30, TimeUnit.DAYS);
                                //清空redis的cookie购物车数据
                                redisTemplate.opsForValue().set(cookieCartKey,null,30, TimeUnit.DAYS);
                            }
                        }
                    }
                }
            }

            //将登录数据缓存到cookie与redis
            //生成uuid
            String uuid = UUID.randomUUID().toString();
            Cookie cookie = new Cookie(CookieConstant.USER_LOGIN, uuid);
            //cookie要发送给客户端
            cookie.setMaxAge(30*24*60*60);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);

            //存入redis
            String key = RedisConstant.USER_LOGIN_PRE + uuid;
            redisTemplate.opsForValue().set(key,result.getData(),30, TimeUnit.DAYS);



            return new ResultVO(200,"登录成功","");
        }else {
            return new ResultVO(400,"登陆失败","");
        }
    }
}
