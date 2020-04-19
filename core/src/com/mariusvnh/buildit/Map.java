package com.mariusvnh.buildit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.utils.Array;
import com.mariusvnh.buildit.registry.BlockRegistry;
import com.mariusvnh.buildit.render.ILoadable;
import com.mariusvnh.buildit.render.IRenderable;

import java.io.FileNotFoundException;

public class Map implements IRenderable, ILoadable
{

    private ModelBatch modelBatch;
    private boolean isLoading;
    private final int WIDTH = 5;
    private final int DEPTH = 5;
    private Array<Array<ModelInstance>> map = new Array<>();
    public BlockRegistry blockRegistry;

    public Map() {
        modelBatch = new ModelBatch();
        try
        {
            blockRegistry =  new BlockRegistry();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            Gdx.app.exit();
            return;
        }
        this.isLoading = true;
    }

    @Override
    public void render(Environment environment, Camera camera) {
        modelBatch.begin(camera);
        for (int x = 0; x < WIDTH; x++) {
            modelBatch.render(map.get(x), environment);
        }
        modelBatch.end();
    }

    @Override
    public boolean isLoading()
    {
        if (isLoading && !blockRegistry.isLoading())
            this.onDoneLoadingAssets();
        return isLoading;
    }

    private void onDoneLoadingAssets() {
        this.isLoading = false;
        this.generateMap();
    }

    public void generateMap() {
        Model ground = blockRegistry.getBlock("ground").model;
        for (int x = 0; x < WIDTH; x++) {
            map.add(new Array<ModelInstance>());
            for (int y = 0; y < DEPTH; y++) {
                ModelInstance instance = new ModelInstance(ground);
                instance.transform.setToTranslation(x * 10f, 0, y * 10f);
                map.get(x).add(instance);
            }
        }
    }
}
