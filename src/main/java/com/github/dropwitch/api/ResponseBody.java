package com.github.dropwitch.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@Builder
@Data
@XmlRootElement
public class ResponseBody<T> {
    private boolean result;
    private T data;

    @JsonCreator
    ResponseBody(@JsonProperty("result") boolean result, @JsonProperty("data") T data) {
        this.result = result;
        this.data = data;
    }
}
