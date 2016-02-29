package com.simply.game.threechess.bean;

import java.io.Serializable;

/**
 * User
 * 13-3-16
 *
 * @author who
 */
public class User implements Serializable {
    private String name;
    private String id;
    private String avatar;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
