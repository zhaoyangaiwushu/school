package com.luftraveler.enumerations;

public enum PayType {
    ALIPAY(1, "支付宝"),
    WEIXINPAY(2, "微信"),
    PAYPAL(3, "PAYPAL"),
    BANK(4,"银行卡"),
    CREDIT(5,"信用卡"),
    BALANCE(4, "余额")
    ;

    private final Integer payCode;
    private final String payName;

    private PayType(Integer payCode, String payName) {
        this.payCode = payCode;
        this.payName = payName;
    }

    public Integer getPayCode() {
        return payCode;
    }

    public String getPayName() {
        return payName;
    }
}
