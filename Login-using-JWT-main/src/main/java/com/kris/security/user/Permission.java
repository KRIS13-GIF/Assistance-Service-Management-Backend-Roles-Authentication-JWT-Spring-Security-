package com.kris.security.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),

    ADMIN_CREATE("admin:create"),

    MANAGER_READ("management:read"),

    MANAGER_CREATE("management:create"),

    ;


    @Getter
    private final String permission;
}
