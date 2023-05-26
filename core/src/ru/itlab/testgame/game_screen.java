package ru.itlab.testgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class game_screen implements Screen {
    SpriteBatch background;
    private Game main_activity;
    private front_map_generator map_generator;
    private Camera camera;
    private Hero hero;
    private Stage stage;
    Vector2 pos;
    float speed = 2.5F;
    private WorldManager worldManager;
    //    private WorldManager worldManager1;
    private float accumulator = 0;
    private Box2DDebugRenderer debugRenderer;
    private int width = Gdx.graphics.getWidth();
    private int height = Gdx.graphics.getHeight();
    private TiledMap map;
    //    private TiledMap bg_map1;
    private OrthogonalTiledMapRenderer tmr;
//    private OrthogonalTiledMapRenderer bg_tmr1;

    private Array<Fixture> mapBody = new Array<>();
    //    private Array<Fixture> bg_mapBody1 = new Array<>();
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

    public game_screen(float x, float y, Game game, front_map_generator map_generator) {
        this.map_generator = map_generator;
        pos = new Vector2(x, y);
        main_activity = game;
    }


    @Override
    public void show() {
        worldManager = new WorldManager();
//        worldManager1 = new WorldManager();
        debugRenderer = new Box2DDebugRenderer();

        //

        hero = new Hero(pos, worldManager,map_generator);

        camera = new Camera(hero);
        FillViewport viewport = new FillViewport(Gdx.graphics.getWidth() / 28f, Gdx.graphics.getHeight() / 28f, camera.getCamera());
        stage = new Stage(viewport);
//        stage2 = new Stage();
        background = (SpriteBatch) stage.getBatch();
        float mapScale = 1f / (Constants.devider * 0.7f);
        map = new TmxMapLoader().load("assets/automatic_generated_map.tmx");
//        bg_map1 = new TmxMapLoader().load("assets/bg_automatic_generated_map.tmx");
        width = Integer.parseInt(map.getProperties().get("width").toString()) * 32;
        height = Integer.parseInt(map.getProperties().get("height").toString()) * 32;
        tmr = new OrthogonalTiledMapRenderer(map, mapScale, stage.getBatch());
//        bg_tmr1 = new OrthogonalTiledMapRenderer(bg_map1, mapScale, stage2.getBatch());
        mapBody = TiledObjectsConverter.importObjects(map, worldManager, mapScale);
//        bg_mapBody1 = TiledObjectsConverter.importObjects(bg_map1, worldManager1, mapScale);
        stage.addActor(hero);
        stars_generation();
//        Gdx.input.setInputProcessor(stage2);
    }

//        Button settingsButton = new Button(new SpriteDrawable(new Sprite(new Texture("settings.png"))));
//        settingsButton.setSize(50, 50);
//        settingsButton.setPosition(0, camera.getCamera().viewportHeight - settingsButton.getHeight());
//        stage2.addActor(settingsButton);


//    public void stars(SpriteBatch batch) {
//        File folder = new File("assets/background_assets");
//        File[] listOfFiles = folder.listFiles();
//        Texture[] stars = new Texture[listOfFiles.length];
//        for (int i = 0; i < listOfFiles.length; i++) {
//            stars[i] = new Texture(listOfFiles[i].getPath());
//        }
//        for (int i = 0; i < the_stars.size(); i++) {
//            batch.draw(stars[the_stars.get(i)[2]], the_stars.get(i)[0] / (float) Constants.devider, the_stars.get(i)[1] / (float) Constants.devider, ((float) the_stars.get(i)[2] + 1f) / Constants.devider, ((float) the_stars.get(i)[2] + 1f) / Constants.devider);
//        }
//    }

    //        stage.getBatch().begin();
//        stars((SpriteBatch) stage.getBatch());
//        stage.getBatch().end();
    @Override
    public void render(float delta) {
        if (hero.isDoorEnter()) {
            main_activity.setScreen(new bg_game_screen(hero.getPos().x, hero.getPos().y, main_activity, map_generator));
            dispose();
            worldManager.dispose();
        } else {
            Gdx.input.setInputProcessor(stage);
            ScreenUtils.clear(15 / 255f, 9 / 255f, 43 / 255f, 0);
            doPhysicsStep(delta, worldManager);
            stage.act();
            tmr.setView(camera.getCamera());
            tmr.render();
            stage.draw();
            debugRenderer.render(worldManager.getWorld(), camera.getCamera().combined);
            camera.update();
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
//        stage2.dispose();
        worldManager.dispose();
        debugRenderer.dispose();
//        bg_tmr1.dispose();
        map.dispose();
        tmr.dispose();
//        map1.dispose();
    }

    private void doPhysicsStep(float deltaTime, WorldManager world) {
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= Constants.TIME_STEP) {
            world.getWorld().step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
            accumulator -= Constants.TIME_STEP;
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}