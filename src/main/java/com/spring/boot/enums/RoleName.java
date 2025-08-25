package com.spring.boot.enums;

public enum RoleName {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");
    private String code;
    private RoleName(String code) {
        this.code = code;

    }
    @Override
    public String toString() {
        return code;
    }
}
