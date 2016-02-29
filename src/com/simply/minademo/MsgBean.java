package com.simply.minademo;

import java.io.Serializable;

/**

 * Created by IntelliJ IDEA.
 * User: who
 * Date: 13-3-8
 * Time: 下午10:24
 * To change this template use File | Settings | File Templates.
 */
public class MsgBean implements Serializable {
    
    private String name;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
