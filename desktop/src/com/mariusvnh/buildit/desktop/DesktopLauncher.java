package com.mariusvnh.buildit.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mariusvnh.buildit.BuildIt;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.samples = 8;
		config.width = 1600;
		config.height = 900;
		config.title = "BuildIt";
		new LwjglApplication(new BuildIt(), config);
	}
}
