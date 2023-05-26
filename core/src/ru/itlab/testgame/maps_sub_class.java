package ru.itlab.testgame;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class maps_sub_class {
    ArrayList<Vector2[]> door_positions = new ArrayList<>();

    public Vector2[] getDoor_positions(int index) {
        //index - map index
        generator();
        Vector2[] door_position = door_positions.get(index);
        return door_position;
    }

    private void generator() {
        door_positions.clear();
        door_positions.add(new Vector2[]{new Vector2(194, 128), new Vector2(642, 128)});
        door_positions.add(new Vector2[]{new Vector2(64, 96), new Vector2(482, 128), new Vector2(802, 128)});
        door_positions.add(new Vector2[]{new Vector2(162, 128), new Vector2(386, 128), new Vector2(642, 128)});
        door_positions.add(new Vector2[]{new Vector2(98, 144)});
        door_positions.add(new Vector2[]{new Vector2(98, 48)});
    }
}
