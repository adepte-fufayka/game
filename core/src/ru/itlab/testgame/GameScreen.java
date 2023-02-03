package ru.itlab.testgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class GameScreen implements Screen {

    private Camera camera;
    private Stage stage, stage2;
    Vector2 pos;
    float speed = 2.5F;
    private WorldManager worldManager;
    private float accumulator = 0;
    private Box2DDebugRenderer debugRenderer;

    private TiledMap map;
    private OrthogonalTiledMapRenderer tmr;
    private Array<Fixture> mapBody;

    @Override
    public void show() {

        worldManager = new WorldManager();
        debugRenderer = new Box2DDebugRenderer();


        //

        Hero hero = new Hero(new Vector2(200,200), worldManager);

        camera = new Camera(hero);
        FillViewport viewport = new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera.getCamera());
        stage = new Stage(viewport);
        stage2 = new Stage();

        //

        float mapScale = .5f;
        map = new TmxMapLoader().load("map.tmx");
        tmr = new OrthogonalTiledMapRenderer(map, mapScale, stage.getBatch());
        mapBody = TiledObjectsConverter.importObjects(map, worldManager, mapScale);

//        stage.addActor(new Image(new Texture("bg.jpg")));

        stage.addActor(hero);

        Button settingsButton = new Button(new SpriteDrawable(new Sprite(new Texture("settings.png"))));
        settingsButton.setSize(50, 50);
        settingsButton.setPosition(0, camera.getCamera().viewportHeight - settingsButton.getHeight());
        stage2.addActor(settingsButton);

        Gdx.input.setInputProcessor(stage2);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);

        stage.act();
        stage2.act();

        camera.update();
        tmr.setView(camera.getCamera());

        tmr.render();

        stage.draw();
        stage2.draw();

        doPhysicsStep(delta);
        debugRenderer.render(worldManager.getWorld(), camera.getCamera().combined);
    }

    @Override
    public void dispose() {
        stage.dispose();
        stage2.dispose();
        worldManager.dispose();
        debugRenderer.dispose();
        map.dispose();
        tmr.dispose();
    }

    private void doPhysicsStep(float deltaTime) {
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= Constants.TIME_STEP) {
            worldManager.getWorld().step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
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