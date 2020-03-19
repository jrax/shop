package com.qf.controller;

import com.qf.constant.CookieConstant;
import com.qf.constant.RedisConstant;
import com.qf.service.CartWebService;
import com.qf.vo.CartItem;
import com.qf.vo.GoodsInfoVO;
import com.qf.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
public class CartWebController {

    @Autowired
    private CartWebService webService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("showCart")
    public String showCart(@RequestParam String name, ModelMap map){
        List<GoodsInfoVO> goodsInfos = webService.selectByIds(name);
        map.put("goodsInfos",goodsInfos);
        return "shopcart";
    }

    @RequestMapping("saveCart")
    @ResponseBody
    public ResultVO saveCart(@RequestParam Integer id, @RequestParam Integer number, HttpServletRequest request, HttpServletResponse response){
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
            String name = (String) user.get("name");
            String userCartKey = RedisConstant.CART_KEY + name;
            List<CartItem>  userCartItems = (List<CartItem>) redisTemplate.opsForValue().get(userCartKey);


            //user购物车有没有数据或者存不存在
            if(userCartItems == null){
                CartItem cartItem = new CartItem(id, number, new Date());
                userCartItems.add(cartItem);
                //把数据存到reids的user购物车中
                redisTemplate.opsForValue().set(userCartKey,userCartItems,30, TimeUnit.DAYS);
            }else {//user购物车有数据
                //先将传进来的数据存到reids的user购物车中
                Boolean flagCartRedis = true;
                for (CartItem cartItem : userCartItems) {
                    if (cartItem.getId().equals(id)){
                        Integer numberOff = cartItem.getNumber() + number;
                        Date date = new Date();
                        cartItem.setNumber(numberOff);
                        cartItem.setTimer(date);
                        flagCartRedis = false;
                    }
                }
                if (flagCartRedis){
                    CartItem cartItem = new CartItem(id,number,new Date());
                    userCartItems.add(cartItem);
                }

                //把数据存到reids的user购物车中
                redisTemplate.opsForValue().set(userCartKey,userCartItems,30, TimeUnit.DAYS);
            }
        }else {
            Cookie[] cookiesCart = request.getCookies();
            Boolean flagCartCookie = true;
            if(cookiesCart!=null){
                for (Cookie cookie : cookiesCart) {
                    if(cookie.getName().equals(CookieConstant.USER_CART)){
                        flagCartCookie = false;
                        String uuid = cookie.getValue();
                        String cartKey = RedisConstant.CART_KEY + uuid;
                        List<CartItem>  cartItems = (List<CartItem>) redisTemplate.opsForValue().get(cartKey);
                        //redis没有cookie购物车
                        if(cartItems == null){
                            List<CartItem>  cartItemList = new ArrayList<>();
                            CartItem cartItem = new CartItem(id,number,new Date());
                            cartItemList.add(cartItem);
                            redisTemplate.opsForValue().set(cartKey,cartItemList,30, TimeUnit.DAYS);
                        }else {
                            Boolean flagCartRedis = true;
                            for (CartItem cartItem : cartItems) {
                                if (cartItem.getId().equals(id)){
                                    Integer numberOff = cartItem.getNumber() + number;
                                    Date date = new Date();
                                    cartItem.setNumber(numberOff);
                                    cartItem.setTimer(date);
                                    flagCartRedis = false;
                                }
                            }
                            if (flagCartRedis){
                                CartItem cartItem = new CartItem(id,number,new Date());
                                cartItems.add(cartItem);
                            }
                            //更新redis
                            redisTemplate.opsForValue().set(cartKey,cartItems,30, TimeUnit.DAYS);
                        }
                    }
                }
            }

            if(flagCartCookie){
                //生成uuid
                String uuid = UUID.randomUUID().toString();
                Cookie cookie = new Cookie(CookieConstant.USER_CART, uuid);
                //cookie要发送给客户端
                cookie.setMaxAge(30*24*60*60);
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
            }

        }

        return new ResultVO(200,"添加成功",null);
    }

}
