package ru.itlab.testgame;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class maps_sub_class {
    ArrayList<Vector2[]> door_positions = new ArrayList<>();
    ArrayList<Vector2[]> enemy_positions = new ArrayList<>();

    public maps_sub_class() {
        door_position_generator();
        enemies_position_generator();
    }

    public Vector2[] getDoor_positions(int index) {
        //index - map index
        Vector2[] door_position = door_positions.get(index);
        return door_position;
    }

    public Vector2[] getEnemy_positions(int index) {
        return enemy_positions.get(index);
    }

    private void door_position_generator() {
        door_positions.add(new Vector2[]{new Vector2(194, 128), new Vector2(674, 128)});
        door_positions.add(new Vector2[]{new Vector2(64, 96), new Vector2(482, 128), new Vector2(802, 128)});
        door_positions.add(new Vector2[]{new Vector2(162, 128), new Vector2(386, 128), new Vector2(642, 128)});
        door_positions.add(new Vector2[]{new Vector2(98, 144)});
        door_positions.add(new Vector2[]{new Vector2(98, 48)});
    }

    private void enemies_position_generator() {
        enemy_positions.add(new Vector2[]{new Vector2(98, 736), new Vector2(130, 736), new Vector2(226, 576),
                new Vector2(162, 736), new Vector2(194, 576), new Vector2(98, 480), new Vector2(130, 480),
                new Vector2(162, 480), new Vector2(194, 480), new Vector2(546, 736), new Vector2(578, 736),
                new Vector2(610, 736), new Vector2(642, 736), new Vector2(674, 736), new Vector2(706, 736),
                new Vector2(546, 608), new Vector2(578, 608), new Vector2(610, 608), new Vector2(610, 480),
                new Vector2(642, 480), new Vector2(674, 480), new Vector2(706, 480), new Vector2(738, 480)});
        enemy_positions.add(new Vector2[]{new Vector2(34, 512), new Vector2(66, 512), new Vector2(98, 512), new Vector2(130, 512),
                new Vector2(34, 768), new Vector2(66, 768), new Vector2(98, 768), new Vector2(130, 768),
                new Vector2(66, 640), new Vector2(98, 640), new Vector2(130, 640), new Vector2(162, 640),
                new Vector2(386, 768), new Vector2(418, 768), new Vector2(450, 768), new Vector2(482, 768),
                new Vector2(450, 640), new Vector2(482, 640), new Vector2(514, 640), new Vector2(546, 640),
                new Vector2(386, 512), new Vector2(418, 512), new Vector2(450, 512), new Vector2(482, 512), new Vector2(514, 512),
                new Vector2(706, 768), new Vector2(738, 768), new Vector2(770, 768), new Vector2(802, 768),
                new Vector2(706, 640), new Vector2(738, 640), new Vector2(770, 640), new Vector2(802, 640),
                new Vector2(770, 512), new Vector2(802, 512), new Vector2(834, 512), new Vector2(866, 512)});
        enemy_positions.add(new Vector2[]{new Vector2(98, 480), new Vector2(130, 480), new Vector2(162, 480), new Vector2(192, 480),
                new Vector2(34, 608), new Vector2(66, 608), new Vector2(98, 408), new Vector2(130, 408),
                new Vector2(194, 736), new Vector2(98, 736), new Vector2(130, 736), new Vector2(162, 736),
                new Vector2(34, 416), new Vector2(66, 416),
                new Vector2(290, 416), new Vector2(322, 416), new Vector2(354, 416), new Vector2(386, 416),
                new Vector2(354, 512), new Vector2(386, 512), new Vector2(418, 512), new Vector2(450, 512),
                new Vector2(290, 736), new Vector2(322, 736), new Vector2(354, 736), new Vector2(386, 512),
                new Vector2(290, 608), new Vector2(322, 608),
                new Vector2(546, 480), new Vector2(578, 480), new Vector2(610, 480), new Vector2(642, 480),
                new Vector2(546, 736), new Vector2(578, 736), new Vector2(610, 736), new Vector2(642, 736),
                new Vector2(674, 512), new Vector2(706, 512)});
        enemy_positions.add(new Vector2[]{
                new Vector2(66, 352), new Vector2(98, 352), new Vector2(130, 352), new Vector2(162, 352), new Vector2(194, 352),
                new Vector2(66, 480), new Vector2(98, 480), new Vector2(130, 480), new Vector2(162, 480), new Vector2(194, 480), new Vector2(34, 480),
                new Vector2(98, 608), new Vector2(130, 608), new Vector2(162, 608), new Vector2(194, 608), new Vector2(226, 352),
                new Vector2(66, 736), new Vector2(98, 736), new Vector2(130, 736), new Vector2(162, 736), new Vector2(34, 352)
        });
        enemy_positions.add(new Vector2[]{
                new Vector2(34, 512), new Vector2(66, 512), new Vector2(98, 512), new Vector2(130, 512), new Vector2(162, 512), new Vector2(194, 512),
                new Vector2(226, 640), new Vector2(66, 640), new Vector2(98, 640), new Vector2(130, 640), new Vector2(162, 640), new Vector2(194, 640),
                new Vector2(34, 768), new Vector2(66, 768), new Vector2(98, 768), new Vector2(130, 768), new Vector2(162, 768), new Vector2(194, 768)
        });
    }
}
