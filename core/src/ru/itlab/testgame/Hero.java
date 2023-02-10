package ru.itlab.testgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Hero extends Actor {
    private boolean isJump = false;
    private final int speed = 70;
    private final int jumpTime = 150;
    private int nowJumpTime = 0;
    private final int yBorder = 0;
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
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            pos.x += speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            pos.x -= speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (!isJump && nowJumpTime == 0 && body.getPosition().y <= yBorder) {
                isJump = true;
                nowJumpTime = jumpTime;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            if (isJump) {
                isJump = false;
                nowJumpTime = 0;
            }
        }
        if (!isJump && body.getPosition().y > yBorder)
            pos.y -= (speed);
        if (isJump && nowJumpTime > 0) {
            pos.y += speed;
            nowJumpTime -= 1;
        }
        if (nowJumpTime == 0)
            isJump = false;
        System.out.println(body.getPosition().x + " " + body.getPosition().y + " " + isJump);
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