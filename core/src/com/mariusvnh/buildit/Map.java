package com.mariusvnh.buildit;

import com.badlogic.gdx.Gdx;
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

public class Map implements IRenderable, ILoadable {

    private ModelBatch modelBatch;
    private boolean isLoading;
    public int width = 5;
    public int height = 5;
    public int depth = 5;
    /**
     * 1 - height, 2 - width, 3 - depth
     */
    private Array<Array<Array<ModelInstance>>> map = new Array<>(height);
    public int[][][] mapGrid = null;
    public boolean[][][] wireframeMap = null;
    public BlockRegistry blockRegistry;
    private final Vector3 blockSize = new Vector3(10f, 2.5f, 10f);
    private boolean isMapInstanceExists = false;
    private boolean isWireframeModeActivated = false;


    public Map() {
        modelBatch = new ModelBatch();
        try {
            blockRegistry = new BlockRegistry();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Gdx.app.exit();
            return;
        }
        this.isLoading = true;
        setDefaultMap();
    }

    private void setDefaultMap() {
        this.mapGrid = new int[height][width][depth];
        this.wireframeMap = new boolean[height][width][depth];
        for (int z = 0; z < height; z++) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < depth; y++) {
                    this.mapGrid[z][x][y] = (z == 0) ? 1 : 0;
                    this.wireframeMap[z][x][y] = false;
                }
            }
        }
        this.mapGrid[0][2][3] = 2;
        this.mapGrid[0][0][0] = 2;
        this.wireframeMap[0][0][0] = true;
    }

    public ModelInstance getModelInstance(int height, int width, int depth) {
        BlockRegistry.RegistryBlock registry = blockRegistry.getBlock(this.mapGrid[height][width][depth]);
        Model model;
        if (registry != null)
            model = registry.model;
        else
            model = new Model();

        if (this.wireframeMap[height][width][depth] || this.isWireframeModeActivated)
            return new WireframeModelInstance(model);
        else
            return new ModelInstance(model);
    }

    @Override
    public void render(Environment environment, Camera camera) {
        modelBatch.begin(camera);
        for (int h = 0; h < height; h++) {
            for (int x = 0; x < width; x++) {
                modelBatch.render(map.get(h).get(x), environment);
            }
        }
        modelBatch.end();
    }

    @Override
    public boolean isLoading() {
        if (isLoading && !blockRegistry.isLoading())
            this.onDoneLoadingAssets();
        return isLoading;
    }

    private void onDoneLoadingAssets() {
        this.isLoading = false;
        this.generateMap();
    }

    public void setWireframeMode(boolean active) {
        this.isWireframeModeActivated = active;
        this.clearMap();
        this.generateMap();
    }

    public boolean isWireframeModeActivated() {
        return this.isWireframeModeActivated;
    }

    public void generateMap() {
        if (isMapInstanceExists)
            return;
        map.setSize(height);
        Model ground = blockRegistry.getBlock("ground").model;
        for (int h = 0; h < height; h++) {
            map.set(h, new Array<Array<ModelInstance>>());
            map.get(h).setSize(width);
            for (int x = 0; x < width; x++) {
                map.get(h).set(x, new Array<ModelInstance>(depth));
                map.get(h).get(x).setSize(depth);
                for (int y = 0; y < depth; y++) {

                    ModelInstance instance = this.getModelInstance(h, x, y);
                    if (instance == null)
                        continue;
                    instance.transform.setToTranslation(x * blockSize.x, h * blockSize.y, y * blockSize.z);
                    map.get(h).get(x).set(y, instance);
                }
            }
        }
        isMapInstanceExists = true;
    }

    public void clearMap() {
        if (!isMapInstanceExists)
            return;
        for (int h = 0; h < height; h++) {
            for (int x = 0; x < width; x++) {
                map.get(h).get(x).clear();
            }
            map.get(h).clear();
        }
        map.clear();
        isMapInstanceExists = false;
    }
}
