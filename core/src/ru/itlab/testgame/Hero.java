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
    private final int horizontal_speed = 15;
    private final int vertical_speed = 10;
    private final int jumpTime = 90;
    private int nowJumpTime = 0;
    private final int yBorder = 0;
    private Texture texture = new Texture("hero.png");
    private Vector2 size = new Vector2(7, 7);
    private Body body;

    public Hero(Vector2 pos, WorldManager worldManager) {
        body = worldManager.createDynamicBox(size.x, size.y, 5f, .1f, 0).getBody();
        body.setTransform(pos, 0);
    }

    @Override
    public void act(float delta) {
        float posX = 0;
        super.act(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            posX += horizontal_speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            posX -= horizontal_speed;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            body.applyForceToCenter(new Vector2(0,-299999),true );
//            if (!isJump && nowJumpTime == 0 && body.getPosition().y <= yBorder) {
//                isJump = true;
//                nowJumpTime = jumpTime;
//            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//            if (isJump) {
//                isJump = false;
//                nowJumpTime = 0;
//            }
        }
//        if (!isJump && body.getPosition().y > yBorder)
//            pos.y -= (vertical_speed);
//        if (isJump && nowJumpTime > 0) {
//            pos.y += vertical_speed;
//            nowJumpTime -= 1;
//        }
//        if (nowJumpTime == 0)
//            isJump = false;
//        System.out.println(body.getPosition().x + " " + body.getPosition().y + " " + isJump);
        body.setLinearVelocity(posX, body.getLinearVelocity().y);
        System.out.println(body.getLinearVelocity());
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