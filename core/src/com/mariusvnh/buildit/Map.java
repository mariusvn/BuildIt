package com.mariusvnh.buildit;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.utils.Array;
import com.mariusvnh.buildit.render.ILoadable;
import com.mariusvnh.buildit.render.IRenderable;

public class Map implements IRenderable, ILoadable
{

    private ModelBatch modelBatch;
    private AssetManager assetManager;
    private boolean isLoading;
    private Model ground;
    private Array<ModelInstance> groundInstances = new Array<>();
    private final int WIDTH = 10;
    private final int DEPTH = 15;

    public Map() {
        modelBatch = new ModelBatch();
        assetManager = new AssetManager();

        assetManager.load("ground_dirt.g3db", Model.class);
        this.isLoading = true;
    }

    @Override
    public void render(Environment environment, Camera camera) {
        modelBatch.begin(camera);
        modelBatch.render(groundInstances, environment);
        modelBatch.end();
    }

    @Override
    public boolean isLoading()
    {
        if (isLoading && assetManager.update())
            this.onDoneLoadingAssets();
        return isLoading;
    }

    private void onDoneLoadingAssets() {
        this.isLoading = false;
        this.ground = assetManager.get("ground_dirt.g3db", Model.class);
        for (float x = 0; x < WIDTH; x++) {
            for (float y = 0; y < DEPTH; y++) {
                ModelInstance instance = new ModelInstance(this.ground);
                instance.transform.translate(x * 10f, 0, y * 10f);
                groundInstances.add(instance);
            }
        }
    }
}
