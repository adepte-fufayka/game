package ru.itlab.testgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.sun.management.internal.GarbageCollectionNotifInfoCompositeData;

import java.io.IOException;


public class MainActivity extends Game {
    private ImageButton W, A, D, ENTER, SHIFT;
    @Override
    public void create() {
        try {
            map_generator = new front_map_generator(0, 50);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(2.25f % 2.20f);
        Texture enemy_texture = new Texture("assets/hero.png");
        Gdx.graphics.setForegroundFPS(120);
        enemy[] enemies = new enemy[map_generator.getEnemies_positions().length];
        for (int i = 0; i < enemies.length; i++) {
            enemies[i] = new enemy(new Vector2(40 / Constants.devider, 60 / Constants.devider), 150f, 17f, new Vector2(map_generator.getEnemies_positions()[i].x / Constants.devider, map_generator.getEnemies_positions()[i].y / Constants.devider), new WorldManager(), 25);
        }
        setScreen(new game_screen(5 * 32f / Constants.devider, 400f / Constants.devider, this, map_generator, enemies));
    }

    private front_map_generator map_generator;
}
