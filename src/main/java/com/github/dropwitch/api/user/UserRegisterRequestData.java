package com.github.dropwitch.api.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@XmlRootElement
public class UserRegisterRequestData {
    private String name;
    private String screenName;
}
