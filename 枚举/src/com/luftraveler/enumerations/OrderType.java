package com.luftraveler.enumerations;

public enum OrderType {
    NONPAYMENT(0, "未付款"),

    PAID(1, "已付款"),

    DELIVERED(2, "已发货"),

    RETURN(3, "退货"),
    CHECKED(4, "已确认"),
    FULFILLED(5, "已配货"),
    EVALUATE(6, "已评价");

    private final Integer orderCode;
    private final String orderName;

    private OrderType(Integer orderCode, String orderName) {
        this.orderCode = orderCode;
        this.orderName = orderName;
    }

    public Integer getOrderCode() {
        return orderCode;
    }

    public String getOrderName() {
        return orderName;
    }
}
