package com.aitongyi.producer.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by win on 2018/1/23.
 */
/*用户类*/
public class UserCustomer implements Serializable {

    private String name;
    private  Integer age;
    private String phone;
    private String address;


    public UserCustomer(){

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}

