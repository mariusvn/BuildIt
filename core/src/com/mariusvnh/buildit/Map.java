package com.mariusvnh.buildit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mariusvnh.buildit.registry.BlockRegistry;
import com.mariusvnh.buildit.render.ILoadable;
import com.mariusvnh.buildit.render.IRenderable;
import com.mariusvnh.buildit.render.instanceTypes.WireframeModelInstance;

import java.io.FileNotFoundException;

public class Map implements IRenderable, ILoadable
{

    private ModelBatch modelBatch;
    private boolean isLoading;
    private final int WIDTH = 5;
    private final int HEIGHT = 5;
    private final int DEPTH = 5;
    /**
     * 1 - height, 2 - width, 3 - depth
     */
    private Array<Array<Array<ModelInstance>>> map = new Array<>(HEIGHT);
    public BlockRegistry blockRegistry;
    private final Vector3 blockSize = new Vector3(10f, 2.5f, 10f);
    private boolean isMapInstanceExists = false;
    private boolean isWireframeModeActivated = false;


    public Map()
    {
        modelBatch = new ModelBatch();
        try
        {
            blockRegistry = new BlockRegistry();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            Gdx.app.exit();
            return;
        }
        this.isLoading = true;
    }

    @Override
    public void render(Environment environment, Camera camera)
    {
        modelBatch.begin(camera);
        for (int h = 0; h < HEIGHT; h++)
        {
            for (int x = 0; x < WIDTH; x++)
            {
                modelBatch.render(map.get(h).get(x), environment);
            }
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

    private void onDoneLoadingAssets()
    {
        this.isLoading = false;
        this.generateMap();
    }

    public void setWireframeMode(boolean active)
    {
        this.isWireframeModeActivated = active;
        this.clearMap();
        this.generateMap();
    }

    public boolean isWireframeModeActivated()
    {
        return this.isWireframeModeActivated;
    }

    public void generateMap()
    {
        if (isMapInstanceExists)
            return;
        map.setSize(HEIGHT);
        Model ground = blockRegistry.getBlock("ground").model;
        for (int h = 0; h < HEIGHT; h++)
        {
            map.set(h, new Array<Array<ModelInstance>>());
            map.get(h).setSize(WIDTH);
            for (int x = 0; x < WIDTH; x++)
            {
                map.get(h).set(x, new Array<ModelInstance>(DEPTH));
                map.get(h).get(x).setSize(DEPTH);
                for (int y = 0; y < DEPTH; y++)
                {
                    ModelInstance instance = this.isWireframeModeActivated ? new WireframeModelInstance(ground) : new ModelInstance(ground);
                    instance.transform.setToTranslation(x * blockSize.x, h * blockSize.y, y * blockSize.z);
                    map.get(h).get(x).set(y, instance);
                }
            }
        }
        isMapInstanceExists = true;
    }

    public void clearMap()
    {
        if (!isMapInstanceExists)
            return;
        for (int h = 0; h < HEIGHT; h++)
        {
            for (int x = 0; x < WIDTH; x++)
            {
                map.get(h).get(x).clear();
            }
            map.get(h).clear();
        }
        map.clear();
        isMapInstanceExists = false;
    }
}
