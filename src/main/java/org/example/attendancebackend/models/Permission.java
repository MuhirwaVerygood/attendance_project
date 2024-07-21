package org.example.attendancebackend.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    FATHER_READ("father:read"),
    FATHER_UPDATE("father:update"),
    FATHER_CREATE("father:create"),
    FATHER_DELETE("father:delete");

    ;

    @Getter
    private final String permission;
}
