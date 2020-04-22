package com.mariusvnh.buildit.registry;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.mariusvnh.buildit.BuildIt;
import com.mariusvnh.buildit.render.ILoadable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;


public class BlockRegistry implements ILoadable
{
    public final String version;
    public final int blocksNbr;
    public final JSONArray rawBlockList;
    private final AssetManager assetManager;
    private Array<RegistryBlock> blocksList = new Array<>();
    private boolean isLoading;

    public BlockRegistry() throws FileNotFoundException, RuntimeException
    {
        assetManager = new AssetManager();
        JSONParser parser = new JSONParser();
        FileHandle file = Gdx.files.internal("blocks/blocks.json");
        JSONObject blocks = null;
        try {
            String content = file.readString();
            blocks = (JSONObject) parser.parse(content);
        } catch (GdxRuntimeException e) {
            throw new FileNotFoundException("Cannot find /blocks/blocks.json");
        } catch (ParseException e) {
            throw new RuntimeException("Cannot parse /blocks/blocks.json");
        }
        version = (String) blocks.get("version");
        rawBlockList = (JSONArray) blocks.get("blocks");
        blocksNbr = rawBlockList.size();
        BuildIt.logger.log("Reading blocks.json version " + version + "...");
        BuildIt.logger.log("Reading " + blocksNbr + " blocks ...");
        for (int i = 0; i < blocksNbr; i++) {
            JSONObject obj = (JSONObject) rawBlockList.get(i);
            BuildIt.logger.log("Adding " + obj.get("name") + " ...");
            assetManager.load("blocks/" + obj.get("model"), Model.class);
        }
        isLoading = true;
    }

    public RegistryBlock getBlock(String name) {
        for (RegistryBlock tmpBlock : blocksList) {
            if (tmpBlock.name.equals(name))
                return tmpBlock;
        }
        return null;
    }

    public RegistryBlock getBlock(long id) {
        for (RegistryBlock tmpBlock : blocksList) {
            if (tmpBlock.id == id)
                return tmpBlock;
        }
        return null;
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
        for (int i = 0; i < blocksNbr; i++) {
            JSONObject obj = (JSONObject) rawBlockList.get(i);
            Model tmpModel = assetManager.get("blocks/" + obj.get("model"), Model.class);
            RegistryBlock block = new RegistryBlock((String) obj.get("name"), (String) obj.get("model"), tmpModel, (long) obj.get("id"));
            blocksList.add(block);
        }
    }

    public class RegistryBlock {
        public final long id;
        public final String name;
        public final String path;
        public final Model model;

        public RegistryBlock(String name, String path, Model model, long id) {
            this.id = id;
            this.name = name;
            this.path = "blocks/" + path;
            this.model = model;
        }
    }
}
