package com.kris.security.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    USER_CREATE_PRODUCT("user:create"),

    USER_CONSULT("user:consult"),

    USER_COLLECT("user:collect"),

    //create order; repair sheet after accept
    ACCEPTANCE_CREATE("acceptance:create"),


    ACCEPTANCE_INFORM("acceptance:inform"),

    TECHNICIAN_CREATE_ORDER("technician:order"),

    TECHNICIAN_REPAIR("technician:repair"),

    TECHNICIAN_NOT_REPAIR("technician:repair"),

    TECHNICIAN_PUT_FINISH("technician:finish")



    ;


    @Getter
    private final String permission;
}
