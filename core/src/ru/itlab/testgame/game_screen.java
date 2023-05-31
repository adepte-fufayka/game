package ru.itlab.testgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class game_screen implements Screen {
    SpriteBatch background;
    BitmapFont font = new BitmapFont();
    private Game main_activity;
    private enemy[] enemies;
    private front_map_generator map_generator;
    private Camera camera;
    private Hero hero;
    private Stage stage;
    private ImageButton W, A, D, ENTER, SHIFT, ATTACK;
    private boolean is_w, is_a, is_d, is_enter, is_shift, is_atc;
    private final Texture attack_texture = new Texture("button_textures/attack_texture.png"), W_texture = new Texture("button_textures/w_texture.png"), A_texture = new Texture("button_textures/a_texture.png"), D_texture = new Texture("button_textures/d_texture.png"), SHIFT_texture = new Texture("button_textures/shift_texture.png"), ENTER_texture = new Texture("button_textures/enter_texture.png");
    private Stage ui;
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

    public game_screen(float x, float y, Game game, front_map_generator map_generator, enemy[] enemies) {
        this.enemies = Arrays.copyOf(enemies, enemies.length);
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

        TextureRegionDrawable trd = new TextureRegionDrawable(W_texture);
        float multiply = 6.4f;
        float multiply2 = Gdx.graphics.getWidth() / 1440f;
        trd.setMinSize(20f * multiply, 20f * multiply);
        W = new ImageButton(trd);
        trd = new TextureRegionDrawable(A_texture);
        trd.setMinSize(20f * multiply, 20f * multiply);
        A = new ImageButton(trd);
        trd = new TextureRegionDrawable(D_texture);
        trd.setMinSize(20f * multiply, 20f * multiply);
        D = new ImageButton(trd);
        trd = new TextureRegionDrawable(SHIFT_texture);
        trd.setMinSize(20f * multiply, 20f * multiply);
        SHIFT = new ImageButton(trd);
        trd = new TextureRegionDrawable(ENTER_texture);
        trd.setMinSize(20f * multiply, 20f * multiply);
        ENTER = new ImageButton(trd);
        trd = new TextureRegionDrawable(attack_texture);
        trd.setMinSize(20f * multiply, 20f * multiply);
        ATTACK = new ImageButton(trd);
        debugRenderer = new Box2DDebugRenderer();
        hero = new Hero(pos, worldManager, map_generator);
        camera = new Camera(hero);
        FillViewport viewport = new FillViewport(Gdx.graphics.getWidth() / Constants.devider, Gdx.graphics.getHeight() / Constants.devider, camera.getCamera());
        stage = new Stage(viewport);
        ui = new Stage();
//        stage2 = new Stage();
        background = (SpriteBatch) stage.getBatch();
        float mapScale = 1f / (Constants.devider * 0.7f);
        map = new TmxMapLoader().load("automatic_generated_map.tmx");
//        bg_map1 = new TmxMapLoader().load("assets/bg_automatic_generated_map.tmx");
        width = Integer.parseInt(map.getProperties().get("width").toString()) * 32;
        height = Integer.parseInt(map.getProperties().get("height").toString()) * 32;
        tmr = new OrthogonalTiledMapRenderer(map, mapScale, stage.getBatch());
//        bg_tmr1 = new OrthogonalTiledMapRenderer(bg_map1, mapScale, stage2.getBatch());
        mapBody = TiledObjectsConverter.importObjects(map, worldManager, mapScale);
//        bg_mapBody1 = TiledObjectsConverter.importObjects(bg_map1, worldManager1, mapScale);
        stage.addActor(hero);
        ATTACK.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                is_atc = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                is_atc = false;
                super.touchUp(event, x, y, pointer, button);
            }
        });
        W.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                is_w = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("w");
                is_w = false;
            }
        });
        A.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                is_a = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                is_a = false;
                System.out.println("a");
            }
        });
        D.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                is_d = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                is_d = false;
                System.out.println("d");
            }
        });
        ENTER.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                is_enter = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                is_enter = false;
            }
        });
        SHIFT.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                is_shift = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                is_shift = false;
            }
        });
        ATTACK.setPosition(Gdx.graphics.getWidth() - 20 * multiply * multiply2 * 2, 20 * 0 * multiply * multiply2);
        W.setPosition(Gdx.graphics.getWidth() - 20 * 2 * multiply * multiply2, 20 * 2 * multiply * multiply2);
        A.setPosition(20 * multiply * 0.5f * multiply2, 20 * multiply * 0.5f * multiply2);
        D.setPosition(20 * multiply * 2.5f * multiply2, 20 * multiply * 0.5f * multiply2);
        SHIFT.setPosition(20 * multiply * 1.5f * multiply2, 20 * 1 * multiply * multiply2);
        ENTER.setPosition(Gdx.graphics.getWidth() - 20 * multiply * multiply2 * 2, 20 * 1 * multiply * multiply2);
        ui.addActor(W);
        ui.addActor(A);
        ui.addActor(D);
        ui.addActor(SHIFT);
        ui.addActor(ENTER);
        ui.addActor(ATTACK);
//        stars_generation();
        Gdx.input.setInputProcessor(ui);
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
        if (is_a) hero.left_moving();
        if (is_d) hero.right_moving();
        if (is_w) hero.up_moving();
        if (is_enter) hero.enter_touched();
        if (is_shift) hero.dash_touched();
        if (is_atc) hero.attack();
        if (hero.isDoorEnter()) {
            main_activity.setScreen(new bg_game_screen(hero.getPos().x, hero.getPos().y, main_activity, map_generator, enemies));
            dispose();
//            worldManager.dispose();
        } else {
//            Gdx.input.setInputProcessor(stage);
            ScreenUtils.clear(15 / 255f, 9 / 255f, 43 / 255f, 0);
            doPhysicsStep(delta, worldManager);
            stage.act();
            tmr.setView(camera.getCamera());
            tmr.render();
            stage.draw();
//            debugRenderer.render(worldManager.getWorld(), camera.getCamera().combined);
            camera.update();
            ui.act();
            ui.getBatch().begin();
            font.draw(ui.getBatch(), "" + hero.getHp(), 0, Gdx.graphics.getHeight());
            ui.getBatch().end();
            ui.draw();
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