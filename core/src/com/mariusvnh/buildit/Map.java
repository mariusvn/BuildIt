package com.mariusvnh.buildit;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.mariusvnh.buildit.render.ILoadable;
import com.mariusvnh.buildit.render.IRenderable;

public class Map implements IRenderable, ILoadable
{

    private ModelBatch modelBatch;
    private AssetManager assetManager;
    private boolean isLoading;
    private Model ground;
    private ModelInstance groundInstance;

    public Map() {
        modelBatch = new ModelBatch();
        assetManager = new AssetManager();

        assetManager.load("ground_dirt.g3db", Model.class);
        this.isLoading = true;
    }

    @Override
    public void render(Environment environment, Camera camera) {
        modelBatch.begin(camera);
        modelBatch.render(groundInstance, environment);
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
        groundInstance = new ModelInstance(this.ground);
    }
}
