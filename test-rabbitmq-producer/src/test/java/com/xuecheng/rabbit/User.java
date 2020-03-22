package com.xuecheng.rabbit;

import java.io.Serializable;

/**
 * @author tpc
 * @date 2020/3/21 18:17
 */
public class User implements Serializable {

    private String username;
    private String passworld;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassworld() {
        return passworld;
    }

    public void setPassworld(String passworld) {
        this.passworld = passworld;
    }
}
