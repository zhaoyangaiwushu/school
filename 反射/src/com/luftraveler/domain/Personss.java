package com.luftraveler.domain;

/**
 * @author shkstart
 * @create 2019 上午 10:38
 */
public class Personss {

    private String name;
    public int age;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Personss(String name, int age) {

        this.name = name;
        this.age = age;
    }

    private Personss(String name) {
        this.name = name;
    }

    public Personss() {
        System.out.println("无参构造->Person()");
    }

    public void show(){
        System.out.println("你好，我是一个人");
    }

    private String showNation(String nation){
        System.out.println("我的国籍是：" + nation);
        return nation;
    }
}
