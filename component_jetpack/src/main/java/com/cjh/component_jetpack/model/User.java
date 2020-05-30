package com.cjh.component_jetpack.model;

/**
 * @author: caijianhui
 * @date: 2020/5/18 10:42
 * @description:
 */
public class User {

    private String name;

    private String password;

    private String img;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
