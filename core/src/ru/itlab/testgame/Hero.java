package ru.itlab.testgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Hero extends Actor {
    private final int maxJumps = 1;
    private int Jumps = maxJumps;

    private boolean isLeft = false;
    private boolean isDash = false;
    private final int Final_DashTime = 7;
    private int dash_time = 0;
    private int dash_divider = 2;
    private final int horizontal_speed = 350 / Constants.devider;
    private final int vertical_speed = 500000 / (Constants.devider * 5);
    private Texture texture = new Texture("hero.png");
    private Vector2 size = new Vector2(70 / Constants.devider, 70 / Constants.devider);
    private Body body;

    public Hero(Vector2 pos, WorldManager worldManager) {
        body = worldManager.createDynamicBox(size.x, size.y, .5f, .1f, 0).getBody();
        body.setTransform(pos, 0);
    }

    @Override
    public void act(float delta) {
        float posX = 0;
        super.act(delta);
        if (!isDash && Gdx.input.isKeyPressed(Input.Keys.D)) {
            posX += horizontal_speed;
            isLeft = false;
        }
        if (!isDash && Gdx.input.isKeyPressed(Input.Keys.A)) {
            posX -= horizontal_speed;
            isLeft = true;
        }
        if (!isDash && Gdx.input.isKeyJustPressed(Input.Keys.W))
            if (Jumps > 0) {
                body.applyForceToCenter(new Vector2(0, vertical_speed), false);
                Jumps--;
            }
        if (!isDash && Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT))
            isDash = true;
        if (isDash) {
            posX += 78 * (isLeft ? (dash_time % dash_divider == 0 ? -horizontal_speed : 0) : (dash_time % dash_divider == 0 ? horizontal_speed : 0));
            dash_time += 1;
            if (dash_time == Final_DashTime) {
                dash_time = 0;
                isDash = false;
            }
        }
        if (Math.abs(body.getLinearVelocity().y) <= 0.1) Jumps = maxJumps;
        System.out.println(body.getLinearVelocity().y);
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//            if (isJump) {
//                isJump = false;
//          s      nowJumpTime = 0;
        }
        body.setLinearVelocity(posX, body.getLinearVelocity().y);
        System.out.println(body.getLinearVelocity());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(texture, isLeft ? size.x + body.getPosition().x : body.getPosition().x, body.getPosition().y, isLeft ? -size.x : size.x, size.y);
    }

    public Vector2 getPos() {
        return body.getPosition();
    }

    public Vector2 getSize() {
        return size;
    }
}