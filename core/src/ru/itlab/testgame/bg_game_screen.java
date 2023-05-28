package ru.itlab.testgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class bg_game_screen implements Screen {
    private front_map_generator map_generator;
    private Camera camera;
    private Game main_activity;
    private float accumulator = 0;
    private Hero hero;
    Vector2 pos;
    private Stage stage;
    int width = 0;
    int height = 0;
    private Texture W_texture = new Texture("assets/button_textures/w_texture.png"), A_texture = new Texture("assets/button_textures/a_texture.png"), D_texture = new Texture("assets/button_textures/d_texture.png"), SHIFT_texture = new Texture("assets/button_textures/shift_texture.png"), ENTER_texture = new Texture("assets/button_textures/enter_texture.png");
    private WorldManager worldManager;
    private Box2DDebugRenderer debugRenderer;
    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;
    Vector2 velocities = new Vector2(0, 0);
    private Array<Fixture> mapBody = new Array<>();
    private ImageButton W, A, D, ENTER, SHIFT;


    public bg_game_screen(float x, float y, Game game, front_map_generator map_generator) {
        this.map_generator = map_generator;
        pos = new Vector2(x, y);
        main_activity = game;
    }

    public void show() {
        TextureRegionDrawable trd = new TextureRegionDrawable(W_texture);
        trd.setMinSize(20f / Constants.devider, 20f / Constants.devider);
        W = new ImageButton(trd);
        trd = new TextureRegionDrawable(A_texture);
        trd.setMinSize(20f / Constants.devider, 20f / Constants.devider);
        A = new ImageButton(trd);
        trd = new TextureRegionDrawable(D_texture);
        trd.setMinSize(20f / Constants.devider, 20f / Constants.devider);
        D = new ImageButton(trd);
        trd = new TextureRegionDrawable(SHIFT_texture);
        trd.setMinSize(20f / Constants.devider, 20f / Constants.devider);
        SHIFT = new ImageButton(trd);
        trd = new TextureRegionDrawable(ENTER_texture);
        trd.setMinSize(20f / Constants.devider, 20f / Constants.devider);
        ENTER = new ImageButton(trd);
        worldManager = new WorldManager();
        debugRenderer = new Box2DDebugRenderer();
        hero = new Hero(pos, worldManager, map_generator);
        camera = new Camera(hero);
        FillViewport viewport = new FillViewport(Gdx.graphics.getWidth() / Constants.devider, Gdx.graphics.getHeight() / Constants.devider, camera.getCamera());
        stage = new Stage(viewport);
        float mapScale = 1f / (Constants.devider * 0.7f);
        map = new TmxMapLoader().load("assets/bg_automatic_generated_map.tmx");
        width = Integer.parseInt(map.getProperties().get("width").toString()) * 32;
        height = Integer.parseInt(map.getProperties().get("height").toString()) * 32;
        tmr = new OrthogonalTiledMapRenderer(map, mapScale, stage.getBatch());
        mapBody = TiledObjectsConverter.importObjects(map, worldManager, mapScale);

        stage.addActor(hero);
        stage.addActor(W);
        stage.addActor(A);
        stage.addActor(D);
        stage.addActor(SHIFT);
        stage.addActor(ENTER);
        W.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pos = new Vector2(pos.x, pos.y + hero.getVertical_speed());
            }
        });
        A.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pos = new Vector2(pos.x - hero.getHorizontal_speed(), pos.y);
            }
        });
        D.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pos = new Vector2(pos.x + hero.getHorizontal_speed(), pos.y);
            }
        });
        ENTER.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        SHIFT.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });
        W.setPosition(64f / Constants.devider, 64f / Constants.devider);
        A.setPosition(32f / Constants.devider, 32f / Constants.devider);
        D.setPosition(96f / Constants.devider, 32f / Constants.devider);
        SHIFT.setPosition(32f / Constants.devider, 128f / Constants.devider);
        ENTER.setPosition(96f / Constants.devider, 128f / Constants.devider);
    }

    public void render(float delta) {
        if (hero.isDoorEnter()) {
            main_activity.setScreen(new game_screen(hero.getPos().x, hero.getPos().y, main_activity, map_generator));
            dispose();
        } else {
            Gdx.input.setInputProcessor(stage);
            ScreenUtils.clear(0 / 255f, 0 / 255f, 0 / 255f, 0);
            doPhysicsStep(delta, worldManager);
            stage.act();
            tmr.setView(camera.getCamera());
            tmr.render();
            stage.draw();
            debugRenderer.render(worldManager.getWorld(), camera.getCamera().combined);
            camera.update();
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
