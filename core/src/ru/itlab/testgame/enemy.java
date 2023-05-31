package ru.itlab.testgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.awt.Point;

public class enemy extends Actor {
    private Vector2 position;
    private float hp;
    private Body enemy_body;

    private float dmg;

    private boolean isDead = false;

    private boolean isLeft = false;
    private int score = 25;
    private float horizontal_speed = 400f / Constants.devider;
    private Texture texture;
    private Vector2 size;
    private float pos = 0;
    private Animation<TextureRegion> IdleAnimation;
    private float idle_time;
    private Vector2 for_idle_animation = new Vector2(8, 0.1f);
//    private Vector2 for_move_animation=new Vector2(9,0.05f);
//    private Animation<TextureRegion> MoveAnimation;
//    private float move_time;
    private Vector2 for_attack_animation = new Vector2(5, 0.1f);
    private Animation<TextureRegion> AttackAnimation;
    private float attack_time;
    private Vector2 for_death_animation = new Vector2(6, 0.1f);
    private float death_time;
    private Animation<TextureRegion> DeathAnimation;
    private Vector2 for_get_dmg_animation = new Vector2(7, 0.1f);
    private float get_dmg_time;
    private Animation<TextureRegion> GetDMGAnimation;
//    private WorldManager worldManager;

    public enemy(Vector2 size, float hp, float dmg, Vector2 pos, WorldManager worldManager, int score) {
        position = new Vector2(pos);
//        this.worldManager = worldManager;
        this.score = score;
        enemy_body = worldManager.createDynamicBox(size.x, size.y, .0005f, 0f, 0).getBody();
        enemy_body.setTransform(pos, 0);
        this.dmg = dmg;
        this.hp = hp;
        this.size = size;
        createIdleAnimation();
        createAttackAnimation();
        createDeathAnimation();
        createGetDamageAnimation();
//        createMoveAnimation();
//        this.texture = texture;
    }

    private boolean is_get_dmg = false, is_moving = false, is_attacking = false;

    public float getDmg() {
        return dmg;
    }

    public float getHp() {
        return hp;
    }

    public Vector2 getSize() {
        return size;
    }

    public Vector2 getPosition() {
        return position;
    }

//    public Texture getTexture() {
//        return texture;
//    }

    @Override
    public void act(float delta) {
        if (hp < 0) isDead = true;
        if (!isDead) {
            pos = 0;
            super.act(delta);
            boolean move_now = 2 == (int) (Math.random() * 16);
            if (move_now) {
                is_moving = !is_attacking && is_get_dmg;
                if (1 == (int) (Math.random() * 2))
                    move_right();
                else move_left();
            } else is_moving = false;
            enemy_body.setLinearVelocity(pos, enemy_body.getLinearVelocity().y);
            position = new Vector2(enemy_body.getPosition());
        }
    }

    //    public void setWorld_manager(WorldManager worldManager)
    private void createIdleAnimation() {
        int len = (int) for_idle_animation.x;
        Texture local_texture = new Texture(Gdx.files.internal("enemies_assets/enemy1/enemy_idle.png"));
        TextureRegion[][] tmp = TextureRegion.split(local_texture, local_texture.getWidth() / len, local_texture.getHeight());
        TextureRegion[] frames = new TextureRegion[len];
        for (int i = 0; i < len; i++) {
            frames[i] = tmp[0][i];
        }
        IdleAnimation = new Animation<TextureRegion>(for_idle_animation.y, frames);
        idle_time = 0f;
    }

    private void createGetDamageAnimation() {
        int len = (int) for_get_dmg_animation.x;
        Texture local_texture = new Texture(Gdx.files.internal("enemies_assets/enemy1/enemy_get_damage.png"));
        TextureRegion[][] tmp = TextureRegion.split(local_texture, local_texture.getWidth() / len, local_texture.getHeight());
        TextureRegion[] frames = new TextureRegion[len];
        for (int i = 0; i < len; i++) {
            frames[i] = tmp[0][i];
        }
        GetDMGAnimation = new Animation<TextureRegion>(for_get_dmg_animation.y, frames);
        get_dmg_time = 0f;
    }

    private void createDeathAnimation() {
        int len = (int) for_death_animation.x;
        Texture local_texture = new Texture(Gdx.files.internal("enemies_assets/enemy1/enemy_died.png"));
        TextureRegion[][] tmp = TextureRegion.split(local_texture, local_texture.getWidth() / len, local_texture.getHeight());
        TextureRegion[] frames = new TextureRegion[len];
        for (int i = 0; i < len; i++) {
            frames[i] = tmp[0][i];
        }
        DeathAnimation = new Animation<TextureRegion>(for_death_animation.y, frames);
        death_time = 0f;
    }

    private void createAttackAnimation() {
        int len = (int) for_attack_animation.x;
        Texture local_texture = new Texture(Gdx.files.internal("enemies_assets/enemy1/enemy_attack.png"));
        TextureRegion[][] tmp = TextureRegion.split(local_texture, local_texture.getWidth() / len, local_texture.getHeight());
        TextureRegion[] frames = new TextureRegion[len];
        for (int i = 0; i < len; i++) {
            frames[i] = tmp[0][i];
        }
        AttackAnimation = new Animation<TextureRegion>(for_attack_animation.y, frames);
        attack_time = 0f;
    }

//    private void createMoveAnimation() {
//        int len = (int) for_move_animation.x;
//        Texture local_texture = new Texture(Gdx.files.internal("assets/enemies_assets/enemy1/enemy_move.png"));
//        TextureRegion[][] tmp = TextureRegion.split(local_texture, local_texture.getWidth() / len, local_texture.getHeight());
//        TextureRegion[] frames = new TextureRegion[len];
//        for (int i = 0; i < len; i++) {
//            frames[i] = tmp[0][i];
//        }
//        MoveAnimation = new Animation<TextureRegion>(for_move_animation.y, frames);
//        move_time = 0f;
//    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        idle_time += Gdx.graphics.getDeltaTime();
        idle_time %= ((int) for_idle_animation.x * for_idle_animation.y);
        TextureRegion local_texture = IdleAnimation.getKeyFrame(idle_time);
//        if (is_attacking{reg=}
//        Texture local_texture = reg.getTexture();
//        Texture local_texture=;
        if (is_attacking) {
            attack_time += Gdx.graphics.getDeltaTime();
            attack_time %= (int) for_attack_animation.x * for_attack_animation.y;
            local_texture = AttackAnimation.getKeyFrame(attack_time);
        }
//        if (is_moving) {
//            move_time += Gdx.graphics.getDeltaTime();
////            System.out.println(moving_time);
//            move_time %= (int) for_move_animation.x * for_move_animation.y;
//            local_texture = MoveAnimation.getKeyFrame(move_time);
//        }
        if (is_get_dmg) {
            get_dmg_time += Gdx.graphics.getDeltaTime();
            get_dmg_time %= (int) for_get_dmg_animation.x * for_get_dmg_animation.y;
            local_texture = GetDMGAnimation.getKeyFrame(get_dmg_time);
        }
        if (isDead) {
            death_time += Gdx.graphics.getDeltaTime();
            death_time %= (int) for_death_animation.x * for_death_animation.y;
            local_texture = DeathAnimation.getKeyFrame(death_time);
        }
        batch.draw(local_texture, isLeft ? size.x + enemy_body.getPosition().x : enemy_body.getPosition().x, enemy_body.getPosition().y, isLeft ? -size.x : size.x, size.y);
    }


    public void getDamage(float damage) {
        hp -= damage;
        is_get_dmg = true;
    }

    public boolean isDead() {
        return isDead;
    }

    public int getScore() {
        return score;
    }

    public float doDamage() {
        return (float) ((int) (30 * Math.random() + 85) / 100f * dmg);
    }

    private void move_right() {
        isLeft = false;
        pos += horizontal_speed;
    }

    private void move_left() {
        isLeft = true;
        pos -= horizontal_speed;
    }

}
