package com.mariusvnh.buildit;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.mariusvnh.buildit.render.IRenderable;

public class Map implements IRenderable {

    private ModelBatch modelBatch;

    public Map() {

        modelBatch = new ModelBatch();
    }

    @Override
    public void render(Environment environment) {

    }
}
