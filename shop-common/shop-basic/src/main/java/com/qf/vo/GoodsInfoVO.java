package com.qf.vo;

import java.io.Serializable;

public class GoodsInfoVO implements Serializable {
    private Integer id;

    private String goodsDescription;

    private String goodsPic;

    private Double goodsPrice;

    private Integer goodsStock;

    private Integer cartNumber;

    private Double goodsPriceOff;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsDescription() {
        return goodsDescription;
    }

    public void setGoodsDescription(String goodsDescription) {
        this.goodsDescription = goodsDescription;
    }

    public String getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsStock() {
        return goodsStock;
    }

    public void setGoodsStock(Integer goodsStock) {
        this.goodsStock = goodsStock;
    }

    public Integer getCartNumber() {
        return cartNumber;
    }

    public void setCartNumber(Integer cartNumber) {
        this.cartNumber = cartNumber;
    }

    public Double getGoodsPriceOff() {
        return goodsPriceOff;
    }

    public void setGoodsPriceOff(Double goodsPriceOff) {
        this.goodsPriceOff = goodsPriceOff;
    }

    public GoodsInfoVO(Integer id, String goodsDescription, String goodsPic, Double goodsPrice, Integer goodsStock) {
        this.id = id;
        this.goodsDescription = goodsDescription;
        this.goodsPic = goodsPic;
        this.goodsPrice = goodsPrice;
        this.goodsStock = goodsStock;
    }

    public GoodsInfoVO() {
    }


}
