package com.mariusvnh.buildit;

import com.badlogic.gdx.Gdx;

public class Logger
{
    private String name;

    public Logger(String name) {
        this.name = name;
    }

    public void log(String data) {
        Gdx.app.log(name, data);
    }
}
