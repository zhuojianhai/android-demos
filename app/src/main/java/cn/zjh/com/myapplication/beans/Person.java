package cn.zjh.com.myapplication.beans;

import java.io.Serializable;

/**
 * Created by zhuojh on 2019/1/4.
 */

public class Person implements Serializable{
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

    private String name;
    private int age;


}
