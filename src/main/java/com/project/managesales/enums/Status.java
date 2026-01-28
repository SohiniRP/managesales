package com.project.managesales.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Status {
    ACTIVE,
    INACTIVE;


    @JsonCreator
    public static Status from(String value) {
        return Status.valueOf(value.toUpperCase());
    }
}
