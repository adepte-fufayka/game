package ru.itlab.testgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

public class bg_game_screen implements Screen {
    private Camera camera;
    private Game main_activity;
    private float accumulator = 0;
    private Hero hero;
    Vector2 pos;
    private Stage stage;
    int width = 0;
    int height = 0;
    private WorldManager worldManager;
    private Box2DDebugRenderer debugRenderer;
    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;
    private Array<Fixture> mapBody = new Array<>();

    public bg_game_screen(float x, float y, Game game) {
        pos = new Vector2(x, y);
        main_activity = game;
    }

    public void show() {
        worldManager = new WorldManager();
        debugRenderer = new Box2DDebugRenderer();
        hero = new Hero(pos, worldManager);
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
    }

    public void render(float delta) {
        if (hero.isDoorEnter()) {
            main_activity.setScreen(new game_screen(hero.getPos().x, hero.getPos().y, main_activity));
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
