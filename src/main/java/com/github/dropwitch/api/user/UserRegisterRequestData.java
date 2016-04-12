package com.github.dropwitch.api.user;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement
public class UserRegisterRequestData {
    private String name;
    private String screenName;
}
