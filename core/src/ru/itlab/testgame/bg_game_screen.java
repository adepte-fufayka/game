package ru.itlab.testgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;

import java.util.Arrays;

public class bg_game_screen implements Screen {
    private front_map_generator map_generator;
    //        private SpriteBatch map_batch = new SpriteBatch();
    private Camera camera;
    private SpriteBatch ui2 = new SpriteBatch();
    private enemy[] enemies;
    BitmapFont font = new BitmapFont();

    private Stage ui;
    private Game main_activity;
    private float accumulator = 0;
    private Hero hero;
    Vector2 pos;
    private Stage stage;
    int width = 0;
    int height = 0;
    private final Texture attack_texture = new Texture("button_textures/attack_texture.png"), W_texture = new Texture("button_textures/w_texture.png"), A_texture = new Texture("button_textures/a_texture.png"), D_texture = new Texture("button_textures/d_texture.png"), SHIFT_texture = new Texture("button_textures/shift_texture.png"), ENTER_texture = new Texture("button_textures/enter_texture.png");
    private WorldManager worldManager;
    private Box2DDebugRenderer debugRenderer;
    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;
    private Array<Fixture> mapBody = new Array<>();
    private ImageButton W, A, D, ENTER, SHIFT, ATTACK;
    private boolean is_w, is_a, is_d, is_enter, is_shift, is_atc;


    public bg_game_screen(float x, float y, Game game, front_map_generator map_generator, enemy[] enemies) {
        worldManager = new WorldManager();
        this.map_generator = map_generator;
        this.enemies = new enemy[enemies.length];
        for (int i = 0; i < enemies.length; i++) {
            this.enemies[i] = new enemy(enemies[i].getSize(), enemies[i].getHp(), enemies[i].getDmg(), enemies[i].getPosition(), this.worldManager, enemies[i].getScore());
        }
        pos = new Vector2(x, y);
        main_activity = game;
    }

    public void show() {
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
        float mapScale = 1f / (Constants.devider * 0.7f);
        map = new TmxMapLoader().load("bg_automatic_generated_map.tmx");
        width = Integer.parseInt(map.getProperties().get("width").toString()) * 32;
        height = Integer.parseInt(map.getProperties().get("height").toString()) * 32;
        tmr = new OrthogonalTiledMapRenderer(map, mapScale, stage.getBatch());
        mapBody = TiledObjectsConverter.importObjects(map, worldManager, mapScale);
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
        for (int i = 0; i < enemies.length; i++) {
            stage.addActor(enemies[i]);
        }
//        Table table = new Table();
//        table.add(A).width(5).height(5);
//        table.add(W).width(5).height(5);
//        table.add(D).width(5).height(5);
//        table.add(SHIFT).width(5).height(5);
//        table.add(ENTER).width(5).height(5);
//        stage.setDebugAll(true);
//        ui.setDebugAll(true);
        stage.addActor(hero);
//        InputMultiplexer input = new InputMultiplexer(stage, ui);
        Gdx.input.setInputProcessor(ui);
        //        stage.addActor(table);
    }

    private int time1 = 0;

    public void render(float delta) {
        time1 += 1;
        if (time1 % 5 == 0) {
            for (int i = 0; i < enemies.length; i++) {
                if (enemies[i].getPosition().x + 48 / Constants.devider > hero.getPos().x && enemies[i].getPosition().x - 48 / Constants.devider < hero.getPos().x && hero.getPos().y == enemies[i].getPosition().y) {
                    if (hero.isIs_attacking()) {
                        enemies[i].getDamage(hero.getDmg());
                    } else hero.HeroGetHit(enemies[i].getDmg());
                    System.out.println("contact");
                }
            }
        }
//        Gdx.input.setInputProcessor(ui);
//        System.out.println(camera.getCamera().position);
//        if (camera.getCamera().position.y < Gdx.graphics.getHeight() / 2f / Constants.devider)
//            camera.getCamera().position.y = Gdx.graphics.getHeight() / 2f / Constants.devider;
//        if (camera.getCamera().position.x < Gdx.graphics.getWidth() / 2f / Constants.devider)
//            camera.getCamera().position.x =Gdx.graphics.getWidth() / 2f / Constants.devider;
        boolean flag = false;
        if (is_a) hero.left_moving();
        if (is_d) hero.right_moving();
        if (is_w) hero.up_moving();
        if (is_enter) hero.enter_touched();
        if (is_shift) hero.dash_touched();
        if (is_atc) hero.attack();
        if (hero.isDoorEnter()) {
            main_activity.setScreen(new game_screen(hero.getPos().x, hero.getPos().y, main_activity, map_generator, enemies));
            dispose();
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

    private void doPhysicsStep(float deltaTime, WorldManager world) {
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= Constants.TIME_STEP) {
            world.getWorld().step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
            accumulator -= Constants.TIME_STEP;
        }
    }

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
