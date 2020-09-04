package com.luftraveler.test;

import com.luftraveler.enumerations.SeasonType;

public class SeasonEnumTets {
    public static void main(String[] args) {
        //1.toString():返回枚举类对象的名称
        System.out.println(SeasonType.AUTUMN.toString());

        //2.values():返回所有的枚举类对象构成的数组
        for (SeasonType value : SeasonType.values()) {
            System.out.println(value.show());
        }
        System.out.println("线程的生命周期.................");
        Thread.State[] values1 = Thread.State.values();
        for (int i = 0; i < values1.length; i++) {
            System.out.println(values1[i]);
        }
        System.out.println(".................");

        //3.valueOf(String objName):返回枚举类中对象名是objName的对象。
        //如果没有objName的枚举类对象，则抛异常：IllegalArgumentException
        SeasonType winter = SeasonType.valueOf("WINTER");
        System.out.println(winter+"-"+winter.show());
    }
}
