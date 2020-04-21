package com.mariusvnh.buildit.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.utils.Array;

public class InputHandler
{
    public final Array<InputProcessor> inputProcessors = new Array<InputProcessor>(new InputProcessor[]{
            new MapInputProcessor()
    });

    private InputMultiplexer inputMultiplexer = new InputMultiplexer();

    public InputHandler()
    {
        Gdx.input.setInputProcessor(inputMultiplexer);
        this.subrscibeAllProcessors();
    }

    public InputHandler(InputProcessor addToList)
    {
        this();
        this.addProcessor(addToList);
    }

    private void subrscibeAllProcessors()
    {
        for (InputProcessor i : inputProcessors)
        {
            inputMultiplexer.addProcessor(i);
        }
    }

    private void unsubscribeAllProcessors()
    {
        inputMultiplexer.clear();
    }

    public void refreshProcessors()
    {
        this.unsubscribeAllProcessors();
        this.subrscibeAllProcessors();
    }

    public void addProcessor(InputProcessor processor)
    {
        this.inputProcessors.add(processor);
        this.inputMultiplexer.addProcessor(processor);
    }

    public void removeProcessor(InputProcessor processor)
    {
        this.inputMultiplexer.removeProcessor(processor);
        this.inputProcessors.removeValue(processor, false);
    }
}
