package com.mariusvnh.buildit.render;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;

public interface IRenderable {
    public void render(Environment environment, Camera camera);
}
