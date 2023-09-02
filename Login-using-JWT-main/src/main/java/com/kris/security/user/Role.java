package com.kris.security.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.kris.security.user.Permission.*;

@RequiredArgsConstructor
public enum Role {
    USER(
            Set.of(

                    USER_CREATE_PRODUCT,
                    USER_CONSULT,
                    USER_COLLECT

            )
    ),
    ACCEPTANCE(
            Set.of(

                    ACCEPTANCE_CREATE,
                    ACCEPTANCE_INFORM
            )
    ),

    TECHNICIAN(

            Set.of(
                    TECHNICIAN_CREATE_ORDER,
                    TECHNICIAN_REPAIR,
                    TECHNICIAN_NOT_REPAIR,
                    TECHNICIAN_PUT_FINISH


            )
    )
    ;



    @Getter
    private final Set<Permission>permissions;

    public List<SimpleGrantedAuthority>getAuthorities(){

        var authorities= getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_"+ this.name())); // you are getting the specific role
    return authorities;

    }


}
