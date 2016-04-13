package com.github.dropwitch.core;

import javax.ws.rs.core.MediaType;

public class DropwitchMediaType extends MediaType {
    public static final String APPLICATION_MSGPACK = "application/x-msgpack";
    public static final MediaType APPLICATION_MSGPACK_TYPE = new MediaType("application", "x-msgpack");
}
