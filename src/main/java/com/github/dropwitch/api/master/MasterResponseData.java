package com.github.dropwitch.api.master;

import lombok.Builder;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Builder
@Data
@XmlRootElement
public class MasterResponseData {
    private List common;
}
