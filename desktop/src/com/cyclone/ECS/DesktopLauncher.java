package com.cyclone.ECS;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main(String[] arg) {
		com.cyclone.ECS.ECS game = new ECS();

		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("ECS");
		config.setWindowedMode(game.viewportWidth, game.viewportHeight);
		config.useVsync(true);
		new Lwjgl3Application(game, config);
	}
}
