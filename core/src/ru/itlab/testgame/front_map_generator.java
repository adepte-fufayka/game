package ru.itlab.testgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class front_map_generator {
    private TiledMap[] tiledMapAssets;
    private TiledMap[] map;
    private int start_index_map_data = 5;
    private int end_index_map_data;
    private int bg_start_index_map_data = 6;
    private int bg_end_index_map_data;
    private TiledMap big_map;
    private int[] tile_map_indexes = new int[0];
    private int[] bg_tile_map_indexes = new int[0];
    private FileHandle[] list_of_maps;
    private maps_sub_class mapsSubClass = new maps_sub_class();
    private Vector2[] enemies_positions;
    private Vector2[] door_pos;
    private float[] map_pos = new float[0];

    public TiledMap getMap() {
        return big_map;
    }

    public front_map_generator(int zone, int map_len) throws IOException {
        System.out.println("map_gen_start");
        Create(map_len);
    }

    private void Create(int map_len) throws IOException {
        FileHandle folder = Gdx.files.internal("tiled_map_assets");
        System.out.println(folder.list().length);
//        list_of_maps = Arrays.copyOf(, folder.file().listFiles().length);
        list_of_maps = new FileHandle[folder.list().length];
        int hgfr = 0;
        for (FileHandle entry : folder.list()) {
            list_of_maps[hgfr] = entry;
            hgfr++;
        }
        this.tiledMapAssets = new TiledMap[list_of_maps.length];
        for (int i = 0; i < tiledMapAssets.length; i++) {
            tiledMapAssets[i] = new TmxMapLoader().load(list_of_maps[i].path());
        }
        System.out.println("input successful");
        Randomise(map_len);
    }

    private void Randomise(int len) throws IOException {
        map = new TiledMap[len];
        map_pos = new float[len];
        tile_map_indexes = new int[len];
        bg_tile_map_indexes = new int[len];
        for (int i = 0; i < len; i++) {
            System.out.println(i);
            int flag = tiledMapAssets.length;
            while (flag >= tiledMapAssets.length || flag < tiledMapAssets.length / 2 || i > 0 && flag == tile_map_indexes[i - 1]) {
                flag = (int) (Math.random() * tiledMapAssets.length + Math.random());
                System.out.println(flag);
            }
            map[i] = tiledMapAssets[flag];
            tile_map_indexes[i] = flag;
            bg_tile_map_indexes[i] = flag - tiledMapAssets.length / 2;
            System.out.println(tile_map_indexes[i]);
            if (i > 0) {
                map_pos[i] = map_pos[i - 1] + Integer.parseInt(map[i - 1].getProperties().get("width").toString()) * 32;
            }
        }
        System.out.println("randomised");
        for (int i = 0; i < len; i++) {
            System.out.print(tile_map_indexes[i] - 4 + " ");
        }
        System.out.println();
        unite();
    }

    private void unite() throws IOException {
        FileHandle output_map = Gdx.files.external("assets/automatic_generated_map.tmx");
//        FileWriter pw = new FileWriter(output_map);
        int width = 0, height = 0;
        for (int i = 0; i < map.length; i++) {
            width += Integer.parseInt(map[i].getProperties().get("width").toString());
            System.out.println(map[i].getProperties().get("width").toString());
            height = Math.max(Integer.parseInt(map[i].getProperties().get("height").toString()), height);
        }
        System.out.println(width + " " + height);
//        System.out.println(String.format("width=%o;", width));
        String header_of_map = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<map version=\"1.9\" tiledversion=\"1.9.2\" orientation=\"orthogonal\" renderorder=\"right-down\" width=\"" + width + "\" height=\"" + height + "\" tilewidth=\"32\" tileheight=\"32\" infinite=\"0\" nextlayerid=\"3\" nextobjectid=\"20000\">\n" +
                " <tileset firstgid=\"1\" source=\"../dark city tileset 1.tsx\"/>\n" +
                " <layer id=\"1\" name=\"Tiles_lay_1\" width=\"" + width + "\" height=\"" + height + "\">\n" +
                "  <data encoding=\"csv\">\n";
        output_map.writeString(header_of_map, false);
        ArrayList<ArrayList<String>> maps_data = new ArrayList<>();
        for (int i = 0; i < map.length; i++) {
            FileHandle map_file_reader = Gdx.files.internal(list_of_maps[tile_map_indexes[i]].file().getPath());
//            Scanner map_scanner = new Scanner(map_file_reader);
//            System.out.println(map_file_reader.readString());
            String[] data = map_file_reader.readString().toString().split("\n");
            for (int j = 0; j < data.length; j++) {
//                System.out.println(data[j]);
            }
            ArrayList<String> map_data = new ArrayList<>(Arrays.asList(data));
            maps_data.add(map_data);
//            map_file_reader.close();
//            map_scanner.close();
        }
        end_index_map_data = height + start_index_map_data - 1;
        for (int i = start_index_map_data; i < end_index_map_data; i++) {
            String tiles = "";
            for (int j = 0; j < map.length; j++) {
                tiles += maps_data.get(j).get(i);
            }
            output_map.writeString(tiles + "\n", true);
        }
        String tiles512 = "";
        for (int i = 0; i < map.length - 1; i++) {
            tiles512 += maps_data.get(i).get(end_index_map_data) + ",";
        }
        tiles512 += maps_data.get(map.length - 1).get(end_index_map_data) + "\n";
        output_map.writeString(tiles512, true);
        int border_x = width * 32, border_y = height * 32;
//      start of borders and objects
        start_index_map_data = end_index_map_data + 4;
        output_map.writeString("</data>\n" +
                " </layer>\n" +
                " <objectgroup id=\"2\" name=\"Objects\">\n", true);
        int object_id = 1;
        output_map.writeString("  <object id=\"" + object_id + "\" x=\"0\" y=\"0\">\n" +
                "   <polyline points=\"0,0 " + border_x + ",0 " + border_x + "," + border_y + " " + 0 + "," + border_y + " 0,0\"/>\n" +
                "  </object>\n", true);
        object_id++;
        for (int i = 0; i < map.length; i++) {
            end_index_map_data = maps_data.get(i).size() - 2;
            String borders = "";
            for (int j = start_index_map_data; j < end_index_map_data; j += 3) {
                String input_line_1 = maps_data.get(i).get(j), input_line_2 = maps_data.get(i).get(j + 1), input_line_3 = maps_data.get(i).get(j + 2);
                String[] line_1 = input_line_1.split("\"");
//                for (int k = 0; k < line_1.length; k++) {
//                    System.out.println(k + " " + line_1[k]);
//                }
                output_map.writeString(line_1[0] + "\"" + object_id + "\"" + line_1[2] + "\"" + (float) (Float.parseFloat(line_1[3]) + map_pos[i]) + "\"" + line_1[4] + "\"" + line_1[5] + "\">\n", true);
                output_map.writeString(input_line_2 + "\n", true);
                output_map.writeString(input_line_3 + "\n", true);
                object_id++;
            }
        }

//        object_id++;
        output_map.writeString(" </objectgroup>\n" +
                "</map>\n", true);
//        output_map.close();
        System.out.println("bbb");
        big_map = new TmxMapLoader().load("automatic_generated_map.tmx");
        System.out.println("map has been generated sucessfully");
        bg_unite();
    }

    private void bg_unite() throws IOException {
        FileHandle output_map = Gdx.files.external("assets/bg_automatic_generated_map.tmx");
        int width = 0, height = 0;
        for (TiledMap tiledMap : map) {
            width += Integer.parseInt(tiledMap.getProperties().get("width").toString());
            System.out.println(tiledMap.getProperties().get("width").toString());
            height = Math.max(Integer.parseInt(tiledMap.getProperties().get("height").toString()), height);
        }
        System.out.println(width + " " + height);
        output_map.writeString("", false);
//        System.out.println(String.format("width=%o;", width));
        String header_of_map = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<map version=\"1.9\" tiledversion=\"1.9.2\" orientation=\"orthogonal\" renderorder=\"right-down\" width=\"" + width + "\" height=\"" + height + "\" tilewidth=\"32\" tileheight=\"32\" infinite=\"0\" nextlayerid=\"3\" nextobjectid=\"20000\">\n" +
                " <tileset firstgid=\"1\" source=\"../dark city tileset 1.tsx\"/>\n <tileset firstgid=\"1001\" source=\"../inside_tileset1.tsx\"/>\n" +
                " <layer id=\"1\" name=\"Tiles_lay_1\" width=\"" + width + "\" height=\"" + height + "\">\n" +
                "  <data encoding=\"csv\">\n";
        output_map.writeString(header_of_map, false);
        ArrayList<ArrayList<String>> maps_data = new ArrayList<>();
        for (int i = 0; i < map.length; i++) {
            FileHandle map_file_reader = Gdx.files.internal(list_of_maps[tile_map_indexes[i]].file().getPath());

            String[] data = map_file_reader.readString().toString().split("\n");
            ArrayList<String> map_data = new ArrayList<>(Arrays.asList(data));
            maps_data.add(map_data);
        }
        bg_end_index_map_data = height + bg_start_index_map_data - 1;
        for (int i = bg_start_index_map_data; i < bg_end_index_map_data; i++) {
            String tiles = "";
            for (int j = 0; j < map.length; j++) {
                tiles += maps_data.get(j).get(i);
            }
            output_map.writeString(tiles + "\n", true);
        }
        String tiles512 = "";
        for (int i = 0; i < map.length - 1; i++) {
            tiles512 += maps_data.get(i).get(bg_end_index_map_data) + ",";
        }
        tiles512 += maps_data.get(map.length - 1).get(bg_end_index_map_data) + "\n";
        output_map.writeString(tiles512, true);
        int border_x = width * 32, border_y = height * 32;
//      start of borders and objects
        bg_start_index_map_data = bg_end_index_map_data + 3;
        output_map.writeString("</data>\n" +
                " </layer>\n" +
                " <objectgroup id=\"2\" name=\"Objects\">\n", true);
        int object_id = 1;
        output_map.writeString("  <object id=\"" + object_id + "\" x=\"0\" y=\"0\">\n" +
                "   <polyline points=\"0,0 " + border_x + ",0 " + border_x + "," + border_y + " " + 0 + "," + border_y + " 0,0\"/>\n" +
                "  </object>\n", true);
        object_id++;
        for (int i = 0; i < map.length; i++) {
            bg_end_index_map_data = maps_data.get(i).size() - 2;
            String borders = "";
            for (int j = bg_start_index_map_data; j < bg_end_index_map_data; j += 3) {
                String input_line_1 = maps_data.get(i).get(j), input_line_2 = maps_data.get(i).get(j + 1), input_line_3 = maps_data.get(i).get(j + 2);
                String[] line_1 = input_line_1.split("\"");
                System.out.println(input_line_1);
//                for (int k = 0; k < line_1.length; k++) {
//                    System.out.println(k + " " + line_1[k]);
//                }
                output_map.writeString(line_1[0] + "\"" + object_id + "\"" + line_1[2] + "\"" + (float) (Float.parseFloat(line_1[3]) + map_pos[i]) + "\"" + line_1[4] + "\"" + line_1[5] + "\">\n", true);
                output_map.writeString(input_line_2 + "\n", true);
                output_map.writeString(input_line_3 + "\n", true);
                object_id++;
            }
        }

//        object_id++;
        output_map.writeString(" </objectgroup>\n" +
                "</map>\n", true);
//        output_map.close();
        System.out.println("bbb");
        big_map = new TmxMapLoader().load("bg_automatic_generated_map.tmx");
        System.out.println("background map has been generated sucessfully");
        doors_position_setter();
    }

    private void doors_position_setter() {
        ArrayList<Vector2> positions = new ArrayList<>();
        for (int i = 0; i < tile_map_indexes.length; i++) {
            int n = mapsSubClass.getDoor_positions(bg_tile_map_indexes[i]).length;
            for (int j = 0; j < n; j++) {
                positions.add(new Vector2(mapsSubClass.getDoor_positions(bg_tile_map_indexes[i])[j].x + map_pos[i], mapsSubClass.getDoor_positions(bg_tile_map_indexes[i])[j].y));
            }
        }
        door_pos = new Vector2[positions.size()];
        for (int i = 0; i < door_pos.length; i++) {
            door_pos[i] = new Vector2(positions.get(i));
//            System.out.println(positions.get(i).x + " " + positions.get(i).y);
        }
        System.out.println("doors position was set");
        enemies_positions_setter();
    }

    private void enemies_positions_setter() {
        ArrayList<Vector2> positions = new ArrayList<>();
        for (int i = 0; i < tile_map_indexes.length; i++) {
            int n = mapsSubClass.getEnemy_positions(bg_tile_map_indexes[i]).length;
            for (int j = 0; j < n; j++) {
                if (1 == (int) (Math.random() * 5))
                    positions.add(new Vector2(mapsSubClass.getEnemy_positions(bg_tile_map_indexes[i])[j].x + map_pos[i], mapsSubClass.getEnemy_positions(bg_tile_map_indexes[i])[j].y));
            }
        }
        enemies_positions = new Vector2[positions.size()];
        for (int j = 0; j < positions.size(); j++) {
            enemies_positions[j] = positions.get(j);
            System.out.println(enemies_positions[j]);
        }
        System.out.println("enemies positions was set");
    }

    public Vector2[] getDoor_pos() {
        return door_pos;
    }

    public Vector2[] getEnemies_positions() {
        return enemies_positions;
    }
}
