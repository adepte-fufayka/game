package ru.itlab.testgame;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.itlab.testgame.MainActivity;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowSizeLimits(1440,720,1441,721);
		config.setForegroundFPS(60);
		config.setTitle("Corruption");
		new Lwjgl3Application(new MainActivity(), config);
	}
}
