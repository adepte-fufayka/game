package ru.itlab.testgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Arrays;

public class Hero extends Actor {
    private final int maxJumps = 1;
    private float hp = 100f;
    private float dmg = 30f;

    private int Jumps = maxJumps;
    private boolean enters = false;
    private boolean isEnter = false;
    private boolean isLeft = false;
    private boolean isDash = false;
    private int dash_time = 0;
    private final float horizontal_speed = 330f * 45f / 35 / Constants.devider;
    private final float vertical_speed = 120f * 45f / 35 / (Constants.devider);
    //    private final Texture texture = new Texture("hero.png");
    private final Vector2 const_size = new Vector2(45f, 70f);
    private Body body;
    private final Vector2 size = new Vector2(const_size.x / Constants.devider, const_size.y / Constants.devider);
    WorldManager worldManager;
    private front_map_generator map_generator;
    private final Vector2[] doors_pos;

    public Hero(Vector2 pos, WorldManager worldManager, front_map_generator map_generator) {
        createAttackAnimation();
        createDashAnimation();
        createDeathAnimation();
        createIdleAnimation();
        createGetHitAnimation();
        createJumpAnimation();
        createMovingAnimation();
        body = worldManager.createDynamicBox(size.x, size.y, .0005f, 0f, 0).getBody();
        body.setTransform(pos, 0);
        this.worldManager = worldManager;
        this.map_generator = map_generator;
        doors_pos = Arrays.copyOf(map_generator.getDoor_pos(), map_generator.getDoor_pos().length);
    }

    public float getHp() {
        return hp;
    }

    public void HeroGetHit(float dmg) {
        hp -= dmg;
        if (hp < 0) {
            is_death = true;
        }
        is_get_hit = true;
        get_hit_time = 0f;
    }

    public float getDmg() {
        return dmg;
    }

    public float getHorizontal_speed() {
        return horizontal_speed;
    }

    public float getVertical_speed() {
        return vertical_speed;
    }

    public Body getBody() {
        return body;
    }

    private float posX = 0;
    private boolean is_moving = false, is_jumping = false, is_dashing = false, is_attacking = false, is_death = false, is_get_hit = false, is_idle = true;

    public boolean isDash() {
        return isDash;
    }

    private boolean falg1 = true;

    @Override
    public void act(float delta) {
        super.act(delta);
//        is_moving = false;
//        boolean flag = true;
        System.out.println(is_moving);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            attack();
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            right_moving();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            left_moving();
        }
        is_moving = !falg1 && !is_attacking && !is_jumping;
        System.out.println(is_moving);
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            enter_touched();
        }
        falg1 = true;
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            up_moving();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) {
            dash_touched();
        }
        if (isDash) {
            dash_moving();
        }
        if (Jumps == 1) is_jumping = false;
        if (Jumps == 0) is_jumping = true;
        if (Math.abs(body.getLinearVelocity().y) <= 0.01) Jumps = maxJumps;
//        System.out.println(body.getLinearVelocity().y);
        if (Math.abs(body.getLinearVelocity().y) > 0.01) Jumps = 0;
//        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//        }
        body.setLinearVelocity(posX, body.getLinearVelocity().y);
        posX = 0;
//        System.out.println(body.getLinearVelocity());
    }

    private Animation<TextureRegion> IdleAnimation;
    private Animation<TextureRegion> MovingAnimation;
    private Animation<TextureRegion> JumpAnimation;
    private Animation<TextureRegion> GetHitAnimation;
    private Animation<TextureRegion> DeathAnimation;
    private Animation<TextureRegion> DashAnimation;
    private Animation<TextureRegion> AttackAnimation;

//    private TextureRegion idle_frame;
//    private TextureRegion moving_frame;
//    private TextureRegion jump_frame;
//    private TextureRegion get_hit_frame;
//    private TextureRegion death_frame;
//    private TextureRegion dash_frame;
//    private TextureRegion attack_frame;

    private float idle_time;
    private float moving_time;
    private float jump_time;
    private float get_hit_time;
    private float death_time;
    private float dash_staints_time;
    private float attack_time;
    private final Vector2 for_idle_animation = new Vector2(8, 0.1f);
    private final Vector2 for_attack_animation = new Vector2(25, 0.1f);
    private final Vector2 for_move_animation = new Vector2(10, 0.1f);
    private final Vector2 for_jump_animation = new Vector2(15, 1.2f / 15f);
    private final Vector2 for_dash_animation = new Vector2(10, 0.1f);
    private final Vector2 for_death_animation = new Vector2(16, 0.1f);
    private final Vector2 for_get_hit_animation = new Vector2(7, 0.1f);


    private void createIdleAnimation() {
        int len = (int) for_idle_animation.x;
        Texture local_texture = new Texture(Gdx.files.internal("assets/Ronin/spr_RoninIdle_strip.png"));
        TextureRegion[][] tmp = TextureRegion.split(local_texture, local_texture.getWidth() / len, local_texture.getHeight());
        TextureRegion[] frames = new TextureRegion[len];
        for (int i = 0; i < len; i++) {
            frames[i] = tmp[0][i];
        }
        IdleAnimation = new Animation<TextureRegion>(for_idle_animation.y, frames);
        idle_time = 0f;
    }

    private void createMovingAnimation() {
        int len = (int) for_move_animation.x;
        Texture local_texture = new Texture(Gdx.files.internal("assets/Ronin/spr_RoninRun_strip.png"));
        TextureRegion[][] tmp = TextureRegion.split(local_texture, local_texture.getWidth() / len, local_texture.getHeight());
        TextureRegion[] frames = new TextureRegion[len];
        for (int i = 0; i < len; i++) {
            frames[i] = tmp[0][i];
        }
        MovingAnimation = new Animation<TextureRegion>(for_move_animation.y, frames);
        moving_time = 0f;
    }

    private void createJumpAnimation() {
        int len = (int) for_jump_animation.x;
        Texture local_texture = new Texture(Gdx.files.internal("assets/Ronin/spr_RoninJump_strip.png"));
        TextureRegion[][] tmp = TextureRegion.split(local_texture, local_texture.getWidth() / len, local_texture.getHeight());
        TextureRegion[] frames = new TextureRegion[len];
        for (int i = 0; i < len; i++) {
            frames[i] = tmp[0][i];
        }
        JumpAnimation = new Animation<TextureRegion>(for_jump_animation.y, frames);
        jump_time = 0f;
    }

    private void createGetHitAnimation() {
        int len = (int) for_get_hit_animation.x;
        Texture local_texture = new Texture(Gdx.files.internal("assets/Ronin/spr_RoninGetHit_strip.png"));
        TextureRegion[][] tmp = TextureRegion.split(local_texture, local_texture.getWidth() / len, local_texture.getHeight());
        TextureRegion[] frames = new TextureRegion[len];
        for (int i = 0; i < len; i++) {
            frames[i] = tmp[0][i];
        }
        GetHitAnimation = new Animation<TextureRegion>(for_get_hit_animation.y, frames);
        get_hit_time = 0f;
    }

    public boolean isIs_attacking() {
        return is_attacking;
    }

    private void createDeathAnimation() {
        int len = (int) for_death_animation.x;
        Texture local_texture = new Texture(Gdx.files.internal("assets/Ronin/spr_RoninDeath_strip.png"));
        TextureRegion[][] tmp = TextureRegion.split(local_texture, local_texture.getWidth() / len, local_texture.getHeight());
        TextureRegion[] frames = new TextureRegion[len];
        for (int i = 0; i < len; i++) {
            frames[i] = tmp[0][i];
        }
        DeathAnimation = new Animation<TextureRegion>(for_death_animation.y, frames);
        death_time = 0f;
    }

    private void createDashAnimation() {
        int len = (int) for_dash_animation.x;
        Texture local_texture = new Texture(Gdx.files.internal("assets/Ronin/spr_RoninDash_strip.png"));
        TextureRegion[][] tmp = TextureRegion.split(local_texture, local_texture.getWidth() / len, local_texture.getHeight());
        TextureRegion[] frames = new TextureRegion[len];
        for (int i = 0; i < len; i++) {
            frames[i] = tmp[0][i];
        }
        DashAnimation = new Animation<TextureRegion>(for_dash_animation.y, frames);
        dash_staints_time = 0f;
    }

    private void createAttackAnimation() {
        int len = (int) for_attack_animation.x;
        Texture local_texture = new Texture(Gdx.files.internal("assets/Ronin/spr_RoninAttack_strip.png"));
        TextureRegion[][] tmp = TextureRegion.split(local_texture, local_texture.getWidth() / len, local_texture.getHeight());
        TextureRegion[] frames = new TextureRegion[len];
        for (int i = 0; i < len; i++) {
            frames[i] = tmp[0][i];
        }
        AttackAnimation = new Animation<TextureRegion>(for_attack_animation.y, frames);
        attack_time = 0f;
    }

    //touch obrabotka
    public void right_moving() {
        if (!isDash) {
            posX += horizontal_speed;
            isLeft = false;
            is_death = false;
            is_get_hit = false;
            is_idle = false;
            if (!is_moving)
                moving_time = 0f;
            is_moving = !is_attacking && !is_jumping;
            falg1 = false;
        }
    }

    public void left_moving() {
        if (!isDash) {
            posX -= horizontal_speed;
            isLeft = true;
//            is_jumping=false;
//            is_attacking=false;
//            is_dashing=false;
            is_death = false;
            is_get_hit = false;
            is_idle = false;
            if (!is_moving)
                moving_time = 0f;
            is_moving = !is_attacking && !is_jumping;
            falg1 = false;
        }
    }

    public void enter_touched() {
        boolean flag = false;
        for (Vector2 doors_po : doors_pos) {
            if (body.getPosition().x * Constants.devider >= doors_po.x - 32 && body.getPosition().x * Constants.devider <= doors_po.x + 32 && body.getPosition().y * Constants.devider <= doors_po.y + 32) {
                flag = true;
                break;
            }
        }
        if (flag) {
            isEnter = !isEnter;
            enters = true;
        }
    }

    public void up_moving() {
        if (!isDash) {
            if (Jumps > 0) {
                body.applyForceToCenter(new Vector2(0, vertical_speed), false);
                Jumps--;
                jump_time = 0f;
                is_jumping = true;
                is_death = false;
                is_get_hit = false;
                is_idle = false;
                is_moving = false;
            }
        }
    }

    public void attack() {
        is_attacking = true;
        attack_time = 0f;
    }

    public void dash_touched() {
        if (!isDash) {
            isDash = Jumps != 0;
            if (isDash) {
                dash_staints_time = 0f;
                is_jumping = false;
                is_dashing = !is_attacking;
                is_death = false;
                is_get_hit = false;
                is_idle = false;
                is_moving = false;
            }
        }
    }

    public void dash_moving() {
        int dash_divider = 3;
        posX += 78 * (isLeft ? (dash_time % dash_divider == 0 ? -horizontal_speed : 0) : (dash_time % dash_divider == 0 ? horizontal_speed : 0));
        dash_time += 1;
        int final_DashTime = 16;
        if (dash_time == final_DashTime) {
            dash_time = 0;
            isDash = false;
            is_dashing = false;
        }
    }


    //end of touch obrabotka
    public boolean isDoorEnter() {

        return isEnter;
    }

    //    public boolean entres() {
//        return enters;
//    }
    private boolean eye_of_heaven = false;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (is_death)
            idle_time += Gdx.graphics.getDeltaTime();
        idle_time %= ((int) for_idle_animation.x * for_idle_animation.y);
        TextureRegion local_texture = IdleAnimation.getKeyFrame(idle_time);
//        if (is_attacking{reg=}
//        Texture local_texture = reg.getTexture();
//        Texture local_texture=;
        float multiply = 1f;
        if (is_death) {
            death_time += Gdx.graphics.getDeltaTime();
            death_time %= (int) for_death_animation.x * for_death_animation.y;
            local_texture = DeathAnimation.getKeyFrame(death_time);
        } else if (is_get_hit) {
            get_hit_time += Gdx.graphics.getDeltaTime();
            get_hit_time %= (int) for_get_hit_animation.x * for_get_hit_animation.y;
            local_texture = GetHitAnimation.getKeyFrame(get_hit_time);
        } else if (is_attacking) {
            attack_time += Gdx.graphics.getDeltaTime();
            multiply = 2.5f;
            if (attack_time >= for_attack_animation.y * for_attack_animation.x) {
                multiply = 1f;
                is_attacking = false;
            }
            local_texture = AttackAnimation.getKeyFrame(attack_time);
        } else if (is_dashing) {
            dash_staints_time += Gdx.graphics.getDeltaTime();
            dash_staints_time %= (int) for_dash_animation.x * for_dash_animation.y;
            System.out.println(dash_staints_time);
            local_texture = DashAnimation.getKeyFrame(dash_staints_time);
        } else if (is_jumping) {
            jump_time += Gdx.graphics.getDeltaTime();
            jump_time %= (int) for_jump_animation.x * for_jump_animation.y;
            System.out.println(jump_time);
            local_texture = JumpAnimation.getKeyFrame(jump_time);
        } else if (is_moving) {
            moving_time += Gdx.graphics.getDeltaTime();
//            System.out.println(moving_time);
            moving_time %= (int) for_move_animation.x * for_move_animation.y;
            local_texture = MovingAnimation.getKeyFrame(moving_time);
        }
        super.draw(batch, parentAlpha);
        batch.draw(local_texture, isLeft ? multiply * size.x + body.getPosition().x : body.getPosition().x, body.getPosition().y, isLeft ? -multiply * size.x : multiply * size.x, size.y);
//        batch.draw(,getX(),getY(),getWidth()/2,getHeight()/2,getWidth(),getHeight(),getScaleX(),getScaleY(),getRotation());
    }

    public Vector2 getPos() {
        return body.getPosition();
    }

    public Vector2 getSize() {
        return size;
    }
}