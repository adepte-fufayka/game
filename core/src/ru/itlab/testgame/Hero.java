package ru.itlab.testgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Hero extends Actor {
    private Texture texture = new Texture("hero.png");
    private Vector2 size = new Vector2(78, 72);
    private Body body;

    public Hero(Vector2 pos, WorldManager worldManager) {
        body = worldManager.createDynamicBox(size.x, size.y, 1, 1, -100).getBody();
        body.setTransform(pos, 0);
    }

    @Override
    public void act(float delta) {
        Vector2 pos = new Vector2(0, 0);
        super.act(delta);
        int speed = 40;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            pos.x += speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            pos.x -= speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            pos.y += speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            pos.y -= speed;
        }
        body.setLinearVelocity(pos);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(texture, body.getPosition().x, body.getPosition().y, size.x, size.y);
    }

    public Vector2 getPos() {
        return body.getPosition();
    }

    public Vector2 getSize() {
        return size;
    }
}