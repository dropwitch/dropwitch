package com.github.dropwitch.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@Builder
@Data
@XmlRootElement
public class ResponseBody {
    private boolean result;
    private Object data;

    @JsonCreator
    ResponseBody(@JsonProperty("result") boolean result, @JsonProperty("data") Object data) {
        this.result = result;
        this.data = data;
    }
}
