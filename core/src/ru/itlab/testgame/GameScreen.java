package ru.itlab.testgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class GameScreen implements Screen {
    SpriteBatch background/* = new SpriteBatch()*/;
    private Camera camera;
    private Stage stage, stage2;
    Vector2 pos;
    float speed = 2.5F;
    private WorldManager worldManager;
    private float accumulator = 0;
    private float map_pos[];
    private Box2DDebugRenderer debugRenderer;
    private int width = Gdx.graphics.getWidth();
    private int height = Gdx.graphics.getHeight();
    private TiledMap[] map;
    private TiledMap map1;
    private OrthogonalTiledMapRenderer tmr1;
    private Array<Fixture> mapBody1;
    private OrthogonalTiledMapRenderer[] tmr;
    private Array<Fixture>[] mapBody;
    private ArrayList<Integer[]> the_stars = new ArrayList<Integer[]>();

    public void stars_generation() {

        for (int i = 0; i < width; i += 4) {
            for (int j = 0; j < height; j += 4) {
                int chance = (int) (Math.random() * 100001f);
                if (chance >= 99999) {
                    the_stars.add(new Integer[]{i, j, 2});
                } else if (chance >= 99700) {
                    the_stars.add(new Integer[]{i, j, 1});
                } else if (chance > 99000) {
                    the_stars.add(new Integer[]{i, j, 0});
                }
            }
        }
    }

    @Override
    public void show() {
        stars_generation();
        worldManager = new WorldManager();
        debugRenderer = new Box2DDebugRenderer();

        //

        Hero hero = new Hero(new Vector2(0f / Constants.devider, 0f / Constants.devider), worldManager);

        camera = new Camera(hero);
        FillViewport viewport = new FillViewport(Gdx.graphics.getWidth() / 8f, Gdx.graphics.getHeight() / 8f, camera.getCamera());
        stage = new Stage(viewport);
        stage2 = new Stage();

        background = (SpriteBatch) stage.getBatch();
        //


        float mapScale = 1f / (Constants.devider * 0.7f);
        try {
            Map_generator map_generator = new Map_generator(0,25);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        map1 = new TmxMapLoader().load("assets/automatic_generated_map.tmx");
        map1=new TmxMapLoader().load("assets/tiled_map_assets/tiled_map_1.tmx");
        tmr1 = new OrthogonalTiledMapRenderer(map1, mapScale, stage.getBatch());
        mapBody1 = TiledObjectsConverter.importObjects(map1, worldManager, mapScale);


//        map = map_generator.getMap();
//        tmr = new OrthogonalTiledMapRenderer[map.length];
//        mapBody = new Array[map.length];
//        map_pos = new float[map.length];
//        for (int i = 0; i < map.length; i++) {
//            if (i > 0)
//                map_pos[i] = map_pos[i - 1] + mapScale * Integer.parseInt(map[i].getProperties().get("width").toString()) * Integer.parseInt(map[i].getProperties().get("tilewidth").toString());
//            System.out.println(map_pos[i]);
//            tmr[i] = new OrthogonalTiledMapRenderer(map[i], mapScale, stage.getBatch());
//            mapBody[i] = TiledObjectsConverter.importObjects(map[i], worldManager, mapScale);
//            for (Fixture fixture : mapBody[i]) {
//                fixture.getBody().setTransform(new Vector2(map_pos[i], 0), 0);
//            }
//            tmr[i].setView(camera.getCamera().projection, map_pos[i], 0, tmr[i].getViewBounds().width, tmr[i].getViewBounds().height);
//            tmr[i].render();
//        }

//        stage.addActor(new Image(new Texture("bg.jpg")));

        stage.addActor(hero);

        Button settingsButton = new Button(new SpriteDrawable(new Sprite(new Texture("settings.png"))));
        settingsButton.setSize(50, 50);
        settingsButton.setPosition(0, camera.getCamera().viewportHeight - settingsButton.getHeight());
//        stage2.addActor(settingsButton);

        Gdx.input.setInputProcessor(stage2);
    }

//    public void stars(SpriteBatch batch) {
//        int width = Gdx.graphics.getWidth();
//        int height = Gdx.graphics.getHeight();
//        File folder = new File("assets/background_assets");
//        File[] listOfFiles = folder.listFiles();
//        Texture[] stars = new Texture[listOfFiles.length];
//        for (int i = 0; i < listOfFiles.length; i++) {
//            stars[i] = new Texture(listOfFiles[i].getPath());
//        }
//        for (int i = 0; i < the_stars.size(); i++) {
//            batch.draw(stars[the_stars.get(i)[2]], the_stars.get(i)[0], the_stars.get(i)[1], ((float) the_stars.get(i)[2] + 1f) / Constants.devider, ((float) the_stars.get(i)[2] + 1f) / Constants.devider);
//        }
//    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(15 / 255f, 9 / 255f, 43 / 255f, 0);
//        background.begin();
//        stars(background);
//        background.end();
        stage.act();
        stage2.act();
//        tmr[1].setView(stage.getBatch().getProjectionMatrix(), 20, 20,240,60);
//        tmr[1].render();
        camera.update();
//        for (int i = 0; i < tmr.length; i++) {
//            System.out.println(tmr[i].getMap().getProperties().get("width") + " " + tmr[i].getMap().getProperties().get("height"));
//            tmr[i].setView(stage.getBatch().getProjectionMatrix(), map_pos[i], 0,30 * 32/28f + map_pos[i], 30 * 32);
//            tmr[i].render();//Integer.parseInt(tmr[i].getMap().getProperties().get("width").toString()),Integer.parseInt(tmr[i].getMap().getProperties().get("height").toString())
//        }


        tmr1.setView(camera.getCamera());
        tmr1.render();


            stage.draw();
            stage2.draw();

            doPhysicsStep(delta);
            debugRenderer.render(worldManager.getWorld(), camera.getCamera().combined);
        }

        @Override
        public void dispose () {
            stage.dispose();
            stage2.dispose();
            worldManager.dispose();
            debugRenderer.dispose();
//            for (int i = 0; i < map.length; i++) {
//                tmr[i].dispose();
//                map[i].dispose();
//            }


        tmr1.dispose();
        map1.dispose();
        }

        private void doPhysicsStep ( float deltaTime){
            float frameTime = Math.min(deltaTime, 0.25f);
            accumulator += frameTime;
            while (accumulator >= Constants.TIME_STEP) {
                worldManager.getWorld().step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
                accumulator -= Constants.TIME_STEP;
            }
        }

        @Override
        public void resize ( int width, int height){
        }

        @Override
        public void pause () {

        }

        @Override
        public void resume () {

        }

        @Override
        public void hide () {

        }
    }