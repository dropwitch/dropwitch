package com.github.dropwitch.entity;

import com.google.common.collect.ImmutableList;

public class EntityList {
    public static ImmutableList<Class<?>> get() {
        return ImmutableList.of(
                MasterCommon.class,
                User.class
        );
    }
}
