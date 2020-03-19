package com.qf.vo;

import java.io.Serializable;
import java.util.Date;

public class CartItem implements Serializable {
    Integer id;
    Integer number;
    Date timer;

    public CartItem() {
    }

    public CartItem(Integer id, Integer number, Date timer) {
        this.id = id;
        this.number = number;
        this.timer = timer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getTimer() {
        return timer;
    }

    public void setTimer(Date timer) {
        this.timer = timer;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", number=" + number +
                ", timer=" + timer +
                '}';
    }
}
