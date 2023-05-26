package ru.itlab.testgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import java.util.Arrays;

public class Hero extends Actor {
    private final int maxJumps = 1;
    private int Jumps = maxJumps;
    private boolean enters = false;
    private boolean isEnter = false;
    private boolean isLeft = false;
    private boolean isDash = false;
    private final int Final_DashTime = 16;
    private int dash_time = 0;
    private int dash_divider = 3;
    private final float horizontal_speed = 350f / Constants.devider;
    private final float vertical_speed = 100f / (Constants.devider);
    private Texture texture = new Texture("hero.png");
    private Vector2 const_size = new Vector2(35f / Constants.devider, 70f / Constants.devider);
    private Body body;
    private Vector2 size = new Vector2(const_size.x, const_size.y);
    WorldManager worldManager;
    private front_map_generator map_generator;
    private Vector2[] doors_pos;

    public Hero(Vector2 pos, WorldManager worldManager, front_map_generator map_generator) {
        body = worldManager.createDynamicBox(size.x, size.y, .0005f, 0f, 0).getBody();
        body.setTransform(pos, 0);
        this.worldManager = worldManager;
        this.map_generator = map_generator;
        doors_pos = Arrays.copyOf(map_generator.getDoor_pos(), map_generator.getDoor_pos().length);
    }

    public Body getBody() {
        return body;
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
        enters = false;
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            boolean flag = false;
            for (int i = 0; i < doors_pos.length; i++) {
                if (body.getPosition().x > doors_pos[i].x - 32 && body.getPosition().x < doors_pos[i].x + 32 && body.getPosition().y < doors_pos[i].y + 32) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                isEnter = !isEnter;
                enters = true;
            }
        }
        System.out.println(isEnter + " " + enters);
        System.out.println(body.getPosition());
        if (!isDash && Gdx.input.isKeyJustPressed(Input.Keys.W))
            if (Jumps > 0) {
                body.applyForceToCenter(new Vector2(0, vertical_speed), false);
                Jumps--;
            }
        if (!isDash && Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) {
            isDash = true;
//            size.y = const_size.y / 2;
            if (Jumps == 0) {
                isDash = false;
//            size.y=const_size.y;
            }

        }
        if (isDash) {

            posX += 78 * (isLeft ? (dash_time % dash_divider == 0 ? -horizontal_speed : 0) : (dash_time % dash_divider == 0 ? horizontal_speed : 0));
            dash_time += 1;
            if (dash_time == Final_DashTime) {
                dash_time = 0;
                isDash = false;
//                size.y=const_size.y;
            }
        }
        if (Math.abs(body.getLinearVelocity().y) <= 0.01) Jumps = maxJumps;
        System.out.println(body.getLinearVelocity().y);
        if (Math.abs(body.getLinearVelocity().y) > 0.01) Jumps = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
        }
        body.setLinearVelocity(posX, body.getLinearVelocity().y);
//        System.out.println(body.getLinearVelocity());
    }

    public boolean isDoorEnter() {

        return isEnter;
    }

    public boolean entres() {
        return enters;
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