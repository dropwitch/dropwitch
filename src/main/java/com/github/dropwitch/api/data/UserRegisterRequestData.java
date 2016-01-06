package com.github.dropwitch.api.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRegisterRequestData {
    private final String name;

    @JsonCreator
    public UserRegisterRequestData() {
        this.name = null;
    }

    @JsonCreator
    public UserRegisterRequestData(@JsonProperty String name) {
        this.name = name;
    }

    @JsonProperty
    public String getName() {
        return this.name;
    }
}
