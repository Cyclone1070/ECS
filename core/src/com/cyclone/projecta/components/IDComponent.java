package com.cyclone.projecta.components;

import com.badlogic.ashley.core.Component;

import java.util.UUID;

public class IDComponent implements Component {
    public final String id;

    public IDComponent(String id) {
        this.id = id;
    }
}
