package ru.itlab.testgame;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Map_generator {
    private TiledMap[] tiledMapAssets;
    private TiledMap[] map;

    public TiledMap[] getMap() {
        return map;
    }

    public Map_generator(int zone) {
        Create();
    }

    private void Create() {
        File folder = new File("assets/tiled_map_assets");
        File[] listOfFiles = folder.listFiles();
        this.tiledMapAssets = new TiledMap[listOfFiles.length];
        for (int i = 0; i < tiledMapAssets.length; i++) {
            tiledMapAssets[i] = new TmxMapLoader().load(listOfFiles[i].getPath());
        }
        Randomise(5);
    }

    private void Randomise(int len) {
        map = new TiledMap[len];
        for (int i = 0; i < len; i++) {
            int flag = 0;
            while (flag >= tiledMapAssets.length) {
                flag = (int) (Math.random() * tiledMapAssets.length + Math.random());
            }
            map[i] = tiledMapAssets[flag];
        }
    }
}
