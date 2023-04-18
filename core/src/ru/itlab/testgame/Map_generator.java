package ru.itlab.testgame;

import com.badlogic.gdx.maps.tiled.TiledMap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Map_generator {
    public Map_generator() throws IOException {
        starter();
    }


    private File map;

    public void starter() throws IOException {
        creater();
    }

    private void creater() throws IOException {
        try {
            map = new File("assets\\automatic_generated_map.tmx");
        } catch (Exception E) {
            System.out.println("File Error");
        }
        FileWriter fileWriter = new FileWriter(map);
        fileWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        fileWriter.write("\n");
        fileWriter.write("<map version=\"1.9\" tiledversion=\"1.9.2\" orientation=\"orthogonal\" renderorder=\"right-down\" width=\"150\" height=\"50\" tilewidth=\"32\" tileheight=\"32\" infinite=\"0\" nextlayerid=\"3\" nextobjectid=\"72\">");
        fileWriter.write("\n");
        fileWriter.write(" <tileset firstgid=\"1\" source=\"dark city tileset 1.tsx\"/>\n");
        fileWriter.write("  <layer id=\"1\" name=\"Слой тайлов 1\" width=\"150\" height=\"50\">\n");
        fileWriter.write("   <data encoding=\"csv\">\n");
        /* тут генератор тайлов в мапу */
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 150; j++) {
                fileWriter.write(String.valueOf(i == 49 && j == 149 ? (int) (Math.random() * 1075) : (int) (Math.random() * 1075) + ","));
            }
            fileWriter.write("\n");
        }
        fileWriter.write("</data>\n" +
                " </layer>\n");
        fileWriter.write(" <objectgroup id=\"2\" name=\"Objects\">\n" +
                "  <object id=\"2\" x=\"3930\" y=\"1087\">\n" +
                "   <polyline points=\"0,0 1,-285 -1180,-288 -1176,-352 -3931,-352 -3931,212 -3528,212 -3529,277 -3235,277 -3237,246 -2250,246 -2247,307 -1954,307 -1954,274 -970,276 -969,338 -675,339 -675,310 -70,310 -70,403 -35,403 -36,58 -40,48 -61,48 -71,19 -42,17 -26,0 1,-1 4,38 -12,53 -11,401 -33,403\"/>\n" +
                "  </object>\n" +
                "  <object id=\"3\" x=\"181\" y=\"978\">\n" +
                "   <polyline points=\"0,0 188,0\"/>\n" +
                "  </object>\n" +
                "  <object id=\"4\" x=\"434\" y=\"1043\">\n" +
                "   <polyline points=\"0,0 224,-5\"/>\n" +
                "  </object>\n" +
                "  <object id=\"5\" x=\"597\" y=\"978\">\n" +
                "   <polyline points=\"0,0 30,0\"/>\n" +
                "  </object>\n" +
                "  <object id=\"6\" x=\"727\" y=\"1008\">\n" +
                "   <polyline points=\"0,0 445,-3\"/>\n" +
                "  </object>\n" +
                "  <object id=\"7\" x=\"1458\" y=\"1009\">\n" +
                "   <polyline points=\"0,0 194,2\"/>\n" +
                "  </object>\n" +
                "  <object id=\"9\" x=\"1716\" y=\"1072\">\n" +
                "   <polyline points=\"0,0 223,2\"/>\n" +
                "  </object>\n" +
                "  <object id=\"10\" x=\"1875\" y=\"1010\">\n" +
                "   <polyline points=\"0,0 32,-1\"/>\n" +
                "  </object>\n" +
                "  <object id=\"11\" x=\"2003\" y=\"1043\">\n" +
                "   <polyline points=\"0,0 448,0\"/>\n" +
                "  </object>\n" +
                "  <object id=\"12\" x=\"2740\" y=\"1041\">\n" +
                "   <polyline points=\"0,0 195,0\"/>\n" +
                "  </object>\n" +
                "  <object id=\"13\" x=\"2998\" y=\"1103\">\n" +
                "   <polyline points=\"0,0 222,-2\"/>\n" +
                "  </object>\n" +
                "  <object id=\"14\" x=\"3157\" y=\"1042\">\n" +
                "   <polyline points=\"0,0 32,0\"/>\n" +
                "  </object>\n" +
                "  <object id=\"15\" x=\"3288\" y=\"1074\">\n" +
                "   <polyline points=\"0,0 441,1\"/>\n" +
                "  </object>\n" +
                "  <object id=\"16\" x=\"3730\" y=\"1235\">\n" +
                "   <polyline points=\"0,0 -68,-2\"/>\n" +
                "  </object>\n" +
                "  <object id=\"17\" x=\"3811\" y=\"1229\">\n" +
                "   <polyline points=\"0,0 -62,-1\"/>\n" +
                "  </object>\n" +
                "  <object id=\"18\" x=\"3554\" y=\"1234\">\n" +
                "   <polyline points=\"0,0 -63,0\"/>\n" +
                "  </object>\n" +
                "  <object id=\"19\" x=\"3329\" y=\"1237\">\n" +
                "   <polyline points=\"0,0 -45,-2\"/>\n" +
                "  </object>\n" +
                "  <object id=\"20\" x=\"3269\" y=\"1246\">\n" +
                "   <polyline points=\"0,0 -57,-8\"/>\n" +
                "  </object>\n" +
                "  <object id=\"21\" x=\"3215\" y=\"1267\">\n" +
                "   <polyline points=\"0,0 -66,0\"/>\n" +
                "  </object>\n" +
                "  <object id=\"22\" x=\"3108\" y=\"1265\">\n" +
                "   <polyline points=\"0,0 -110,2\"/>\n" +
                "  </object>\n" +
                "  <object id=\"23\" x=\"3061\" y=\"1319\">\n" +
                "   <polyline points=\"0,0 87,0\"/>\n" +
                "  </object>\n" +
                "  <object id=\"24\" x=\"3325\" y=\"1290\">\n" +
                "   <polyline points=\"0,0 79,0\"/>\n" +
                "  </object>\n" +
                "  <object id=\"25\" x=\"3612\" y=\"1286\">\n" +
                "   <polyline points=\"0,0 83,1\"/>\n" +
                "  </object>\n" +
                "  <object id=\"26\" x=\"2973\" y=\"1192\">\n" +
                "   <polyline points=\"0,0 -53,-2\"/>\n" +
                "  </object>\n" +
                "  <object id=\"27\" x=\"2928\" y=\"1200\">\n" +
                "   <polyline points=\"0,0 -73,3\"/>\n" +
                "  </object>\n" +
                "  <object id=\"28\" x=\"2778\" y=\"1205\">\n" +
                "   <polyline points=\"0,0 -39,0\"/>\n" +
                "  </object>\n" +
                "  <object id=\"29\" x=\"2647\" y=\"1207\">\n" +
                "   <polyline points=\"0,0 -50,6\"/>\n" +
                "  </object>\n" +
                "  <object id=\"30\" x=\"2531\" y=\"1193\">\n" +
                "   <polyline points=\"0,0 -56,-1\"/>\n" +
                "  </object>\n" +
                "  <object id=\"31\" x=\"2459\" y=\"1206\">\n" +
                "   <polyline points=\"0,0 -86,-5\"/>\n" +
                "  </object>\n" +
                "  <object id=\"32\" x=\"2417\" y=\"1260\">\n" +
                "   <polyline points=\"0,0 -88,1\"/>\n" +
                "  </object>\n" +
                "  <object id=\"33\" x=\"2271\" y=\"1208\">\n" +
                "   <polyline points=\"0,0 -57,-2\"/>\n" +
                "  </object>\n" +
                "  <object id=\"34\" x=\"2047\" y=\"1203\">\n" +
                "   <polyline points=\"0,0 -47,-1\"/>\n" +
                "  </object>\n" +
                "  <object id=\"35\" x=\"2127\" y=\"1256\">\n" +
                "   <polyline points=\"0,0 -87,2\"/>\n" +
                "  </object>\n" +
                "  <object id=\"36\" x=\"1985\" y=\"1216\">\n" +
                "   <polyline points=\"0,0 -49,-9\"/>\n" +
                "  </object>\n" +
                "  <object id=\"37\" x=\"1938\" y=\"1236\">\n" +
                "   <polyline points=\"0,0 -73,-1\"/>\n" +
                "  </object>\n" +
                "  <object id=\"38\" x=\"1870\" y=\"1287\">\n" +
                "   <polyline points=\"0,0 -82,2\"/>\n" +
                "  </object>\n" +
                "  <object id=\"39\" x=\"1804\" y=\"1233\">\n" +
                "   <polyline points=\"0,0 -82,1 -89,1\"/>\n" +
                "  </object>\n" +
                "  <object id=\"40\" x=\"1652\" y=\"1172\">\n" +
                "   <polyline points=\"0,0 -74,-1\"/>\n" +
                "  </object>\n" +
                "  <object id=\"41\" x=\"1649\" y=\"1162\">\n" +
                "   <polyline points=\"0,0 49,0\"/>\n" +
                "  </object>\n" +
                "  <object id=\"42\" x=\"1623\" y=\"1250\">\n" +
                "   <polyline points=\"0,0 -131,-1\"/>\n" +
                "  </object>\n" +
                "  <object id=\"43\" x=\"1506\" y=\"1172\">\n" +
                "   <polyline points=\"0,0 -46,-1\"/>\n" +
                "  </object>\n" +
                "  <object id=\"44\" x=\"1368\" y=\"1174\">\n" +
                "   <polyline points=\"0,0 -49,9\"/>\n" +
                "  </object>\n" +
                "  <object id=\"45\" x=\"1250\" y=\"1162\">\n" +
                "   <polyline points=\"0,0 -59,-1\"/>\n" +
                "  </object>\n" +
                "  <object id=\"46\" x=\"1171\" y=\"1171\">\n" +
                "   <polyline points=\"0,0 -78,-2\"/>\n" +
                "  </object>\n" +
                "  <object id=\"47\" x=\"987\" y=\"1167\">\n" +
                "   <polyline points=\"0,0 -53,0\"/>\n" +
                "  </object>\n" +
                "  <object id=\"48\" x=\"766\" y=\"1168\">\n" +
                "   <polyline points=\"0,0 -43,0\"/>\n" +
                "  </object>\n" +
                "  <object id=\"49\" x=\"844\" y=\"1220\">\n" +
                "   <polyline points=\"0,0 -83,1\"/>\n" +
                "  </object>\n" +
                "  <object id=\"50\" x=\"1132\" y=\"1221\">\n" +
                "   <polyline points=\"0,0 -83,-1\"/>\n" +
                "  </object>\n" +
                "  <object id=\"51\" x=\"709\" y=\"1180\">\n" +
                "   <polyline points=\"0,0 -56,-7\"/>\n" +
                "  </object>\n" +
                "  <object id=\"52\" x=\"657\" y=\"1199\">\n" +
                "   <polyline points=\"0,0 -72,0\"/>\n" +
                "  </object>\n" +
                "  <object id=\"53\" x=\"535\" y=\"1204\">\n" +
                "   <polyline points=\"0,0 -99,-2\"/>\n" +
                "  </object>\n" +
                "  <object id=\"54\" x=\"590\" y=\"1250\">\n" +
                "   <polyline points=\"0,0 -83,0\"/>\n" +
                "  </object>\n" +
                "  <object id=\"55\" x=\"339\" y=\"1221\">\n" +
                "   <polyline points=\"0,0 -132,0\"/>\n" +
                "  </object>\n" +
                "  <object id=\"56\" x=\"355\" y=\"1130\">\n" +
                "   <polyline points=\"0,0 62,0\"/>\n" +
                "  </object>\n" +
                "  <object id=\"57\" x=\"370\" y=\"1144\">\n" +
                "   <polyline points=\"0,0 -66,0\"/>\n" +
                "  </object>\n" +
                "  <object id=\"58\" x=\"225\" y=\"1144\">\n" +
                "   <polyline points=\"0,0 -45,-1\"/>\n" +
                "  </object>\n" +
                "  <object id=\"59\" x=\"86\" y=\"1141\">\n" +
                "   <polyline points=\"0,0 -49,9\"/>\n" +
                "  </object>\n" +
                "  <object id=\"60\" x=\"2900\" y=\"1286\">\n" +
                "   <polyline points=\"0,0 -131,1\"/>\n" +
                "  </object>\n" +
                " </objectgroup>\n");
        fileWriter.write("</map>\n");
        fileWriter.close();
    }
}
