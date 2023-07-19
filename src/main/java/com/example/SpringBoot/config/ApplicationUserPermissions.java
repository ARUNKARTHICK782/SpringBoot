package com.example.SpringBoot.config;

public enum ApplicationUserPermissions{
    READ("READ"),
    WRITE("WRITE");

    private final String permission;

    ApplicationUserPermissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
