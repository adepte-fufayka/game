package ru.itlab.testgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Camera {
    private OrthographicCamera camera;
    private Hero hero;

    public Camera(Hero hero) {
        camera = new OrthographicCamera(Gdx.graphics.getWidth() / Constants.devider, Gdx.graphics.getHeight() / Constants.devider);
        this.hero = hero;
    }

    public void update() {
        camera.position.set(
                hero.getPos().x + hero.getSize().x / 2,
                hero.getPos().y + hero.getSize().y / 2,
                0);
        camera.update();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
