package com.mariusvnh.buildit;

import com.badlogic.gdx.ApplicationAdapter;
import com.mariusvnh.buildit.render.Renderer;


public class BuildIt extends ApplicationAdapter {
	public static Logger logger = new Logger("BUILDIT");

	public Renderer renderer;
	public Map map;

	@Override
	public void create () {
		renderer = new Renderer();
		map = new Map();
		renderer.register(map);
	}

	@Override
	public void render () {
		renderer.render();
	}

	@Override
	public void resize(int width, int height)
	{
		renderer.resize(width, height);
		super.resize(width, height);
	}
}
