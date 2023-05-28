package ru.itlab.testgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.sun.management.internal.GarbageCollectionNotifInfoCompositeData;

import java.io.IOException;


public class MainActivity extends Game {
    @Override
    public void create() {
        try {
            map_generator = new front_map_generator(0, 50);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Gdx.graphics.setForegroundFPS(120);
        setScreen(new game_screen(28f / Constants.devider, 400f / Constants.devider, this, map_generator));
    }

    private front_map_generator map_generator;
}
