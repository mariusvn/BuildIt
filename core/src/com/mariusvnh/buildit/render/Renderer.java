package com.mariusvnh.buildit.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Renderer
{

    private final Environment environment;
    private final PerspectiveCamera cam;
    private final CameraInputController camController;
    private final Map<Integer, IRenderable> renderableMap = new HashMap<>();
    private Integer idIncrement = 0;

    public Renderer()
    {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(1f, 1f, 1f);
        cam.lookAt(0, 0, 0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);
    }

    public void render()
    {
        camController.update();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));


        Iterator<Entry<Integer, IRenderable>> it = renderableMap.entrySet().iterator();
        while (it.hasNext())
        {
            Entry<Integer, IRenderable> pair = it.next();
            IRenderable tempRenderable = pair.getValue();
            if (tempRenderable instanceof ILoadable)
            {
                if (!((ILoadable) tempRenderable).isLoading())
                    tempRenderable.render(environment, cam);
            } else
            {
                tempRenderable.render(environment, cam);
            }
        }
    }

    public int register(IRenderable renderable)
    {
        renderableMap.put(idIncrement, renderable);
        int save = idIncrement;
        idIncrement++;
        return save;
    }

    public void unregister(int id)
    {
        if (renderableMap.remove(id) == null)
            throw new ArrayIndexOutOfBoundsException("invalid unregistering in renderer");
    }

    public void resize(int width, int height)
    {
        Gdx.gl.glViewport(0, 0, width, height);
    }
}
