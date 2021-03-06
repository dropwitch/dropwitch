package com.github.dropwitch.api;

import lombok.Builder;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@Builder
@Data
@XmlRootElement
public class ResponseBody {
    private boolean result;
    private Object data;
}
