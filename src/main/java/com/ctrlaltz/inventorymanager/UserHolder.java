package com.ctrlaltz.inventorymanager;

public final class UserHolder {

    private Integer id;
    private final static UserHolder INSTANCE = new UserHolder();

    private UserHolder() {}

    public static UserHolder getInstance() {
        return INSTANCE;
    }

    public void setUser(Integer u) {
        this.id = u;
    }

    public Integer getUser() {
        return this.id;
    }
}
