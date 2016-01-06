package com.github.dropwitch.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseBody {
    private final boolean result;
    private final Object data;

    @JsonCreator
    public ResponseBody() {
        this.result = false;
        this.data = null;
    }

    @JsonCreator
    public ResponseBody(@JsonProperty Object data) {
        this.result = true;
        this.data = data;
    }

    @JsonProperty
    public boolean isResult() {
        return this.result;
    }

    @JsonProperty
    public Object getData() {
        return this.data;
    }
}
