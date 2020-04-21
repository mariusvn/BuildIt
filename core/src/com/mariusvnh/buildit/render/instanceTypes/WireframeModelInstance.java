package com.mariusvnh.buildit.render.instanceTypes;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;

public class WireframeModelInstance extends ModelInstance
{
    public WireframeModelInstance(Model model)
    {
        super(model);
    }

    @Override
    public Renderable getRenderable(Renderable out, Node node, NodePart nodePart)
    {
        super.getRenderable(out, node, nodePart);
        out.meshPart.primitiveType = GL20.GL_LINE_STRIP;
        return out;
    }
}
