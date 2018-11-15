package com.moovfy.moovfy;

public class Usuario {
    private String username;
    private String desc;
    private int icon;

    public Usuario(String username, String desc, int icon) {
        this.username = username;
        this.desc = desc;
        this.icon = icon;
    }

    public String getUsername() {
        return username;
    }

    public String getDesc() {
        return desc;
    }

    public int getIcon() {
        return icon;
    }
}
