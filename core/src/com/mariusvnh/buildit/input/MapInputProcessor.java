package com.mariusvnh.buildit.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.mariusvnh.buildit.BuildIt;

public class MapInputProcessor implements InputProcessor
{
    @Override
    public boolean keyDown(int keycode)
    {
        BuildIt.logger.log("Keycode: " + keycode);
        if (keycode == 8)
        {
            BuildIt app = (BuildIt) Gdx.app.getApplicationListener();
            app.map.setWireframeMode(!app.map.isWireframeModeActivated());
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        return false;
    }
}
