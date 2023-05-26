package ru.itlab.testgame;

import com.badlogic.gdx.Game;

import java.io.IOException;

public class MainActivity extends Game {

    @Override
    public void create() {
        try {
            front_map_generator map_generator = new front_map_generator(0, 50);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setScreen(new game_screen(28f / Constants.devider, 400f / Constants.devider));
    }
}
