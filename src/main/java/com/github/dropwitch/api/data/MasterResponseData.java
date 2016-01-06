package com.github.dropwitch.api.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MasterResponseData {
    private final List common;

    @JsonCreator
    public MasterResponseData() {
        this.common = null;
    }

    @JsonCreator
    public MasterResponseData(@JsonProperty List common) {
        this.common = common;
    }

    @JsonProperty
    public List getCommon() {
        return this.common;
    }
}
