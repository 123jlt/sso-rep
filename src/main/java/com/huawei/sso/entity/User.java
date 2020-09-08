package com.huawei.sso.entity;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String userId;
    private String username;
    private String name;
    private String email;
    private int mobile;
    private String account;

    public User(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public User(int id, String userId, String username, String name, String email, int mobile, String account) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.account = account;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobile=" + mobile +
                ", account='" + account + '\'' +
                '}';
    }
}
