package com.example.accessingdatamysql.common;

import lombok.Getter;

@Getter
public enum  ServiceStatus {
    LOGIN_SUCCESS(0, "Login success");

    private final int id;
    private final String message;
    //Enum constructor
    ServiceStatus(int id, String message) {
        this.id = id;
        this.message = message;
    }
}
