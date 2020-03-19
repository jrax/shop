package com.qf.controller;

import com.qf.constant.RedisConstant;
import com.qf.entity.TGoodsInfo;
import com.qf.mapper.TGoodsInfoMapper;
import com.qf.vo.CartItem;
import com.qf.vo.GoodsInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartServiceController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TGoodsInfoMapper goodsInfoMapper;

    @RequestMapping("selectByIds")
    @ResponseBody
    public List<GoodsInfoVO> selectByIds(@RequestParam String name){
        //查询redis中user购物车数据
        String userCartKey = RedisConstant.CART_KEY + name;
        List<CartItem>  userCartItems = (List<CartItem>) redisTemplate.opsForValue().get(userCartKey);
        List<Integer> ids = new ArrayList<>();
        for (CartItem cartItem : userCartItems) {
            ids.add(cartItem.getId());
        }
        List<TGoodsInfo> goodsInfos = goodsInfoMapper.selectByIds(ids);
        List<GoodsInfoVO> goodsInfoVOS = new ArrayList<>();
        for (TGoodsInfo goodsInfo : goodsInfos) {
            GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
            goodsInfoVO.setId(goodsInfo.getId());
            goodsInfoVO.setGoodsDescription(goodsInfo.getGoodsDescription());
            goodsInfoVO.setGoodsPic(goodsInfo.getGoodsPic());
            goodsInfoVO.setGoodsPrice(goodsInfo.getGoodsPrice());
            goodsInfoVO.setGoodsStock(goodsInfo.getGoodsStock());
            goodsInfoVO.setGoodsPriceOff(goodsInfo.getGoodsPriceOff());
            //
            for (CartItem userCartItem : userCartItems) {
                if (userCartItem.getId().equals(goodsInfo.getId())){
                    goodsInfoVO.setCartNumber(userCartItem.getNumber());
                }
            }
            goodsInfoVOS.add(goodsInfoVO);
        }
        return goodsInfoVOS;
    }
}
