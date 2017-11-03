package game_object.enemy;

import game_object.general.GameObject;
import game_object.general.GameObjectHandler;
import game_object.general.ObjectID;
import game_object.player.Character;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *  This class will represent the enemies of the game with its given parameters and methods.
 */

public class Enemy extends GameObject
{
    private float healthLevel;
    private float damage;
    private GameObjectHandler gameObjectHandler;

    private boolean forceJump = false;

    private float[] moveAxisSpeeds = new float[2];
    private long jumpActivationTime;
    private final float FORCE_JUMP_DURATION = 250f;

    //These properties can be used in specific enemy types
    public static final float HORIZONTAL_SPEED = 3;
    public static final float FIGHT_RANGE = 250;

    public Enemy(float x, float y, ObjectID id, GameObjectHandler gameObjectHandler){
        super(x, y, id);
        this.gameObjectHandler = gameObjectHandler;

        setHeight(70);
        setWidth(60);

    }


    public boolean checkKilled()
    {
        return false;
    }


    public void fight()
    {

    }


    @Override
    public void update(ArrayList<GameObject> gameObjects) {

        super.update(gameObjects);
        //TODO: Move through the character
        Character player =  gameObjectHandler.getCharacter();

        if(Math.abs(this.x - player.getX()) > FIGHT_RANGE)
        {
            if(this.x - player.getX() < 0)
                this.x += HORIZONTAL_SPEED;
            else
                this.x -= HORIZONTAL_SPEED;
        }


        if(forceJump)
        {
            this.y -= moveAxisSpeeds[1];
        }

        if(forceJump && ((System.currentTimeMillis() - jumpActivationTime)   >= FORCE_JUMP_DURATION))
        {
            forceJump = false;
        }


        this.checkCollision(gameObjects);


    }

    public void jumpRight()
    {
        jumpActivationTime = System.currentTimeMillis();
        moveAxisSpeeds[0] = HORIZONTAL_SPEED;
        moveAxisSpeeds[1] = 10;
        forceJump = true;
    }

    public void jumpLeft()
    {
        jumpActivationTime = System.currentTimeMillis();
        moveAxisSpeeds[0] = -HORIZONTAL_SPEED;
        moveAxisSpeeds[1] = 10;
        forceJump = true;
    }


    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);

        //g.fillRect((int)x, (int)y, width, height);


        g.drawImage(new ImageIcon("src/resources/game_textures/enemy/test_enemy.png").getImage(), (int) x, (int) (y), null);
    }

    private void checkCollision(ArrayList<GameObject> gameObjects)
    {

        for(int i = 0; i < gameObjects.size(); i++)
        {
            // To keep the game objects in a temp variable - for simplicity
            GameObject tempObject = gameObjectHandler.getGame_objects().get(i);

            //checking collision with the tiles.
            if(tempObject.getId() == ObjectID.Tile)
            {
                // Top collision
                if(this.getBoundsTop().intersects(tempObject.getBounds()))
                {
                    setVelY(0);
                }

                // Right collision
                if(this.getBoundsRight().intersects(tempObject.getBounds()))
                {
                    setX(tempObject.getX() - getWidth());
                    jumpRight();
                }

                // Left collision
                if(this.getBoundsLeft().intersects(tempObject.getBounds()))
                {
                    setX(tempObject.getX() + 2 * getWidth() );
                    jumpLeft();
                }

                // Bottom Collision
                if(this.getBounds().intersects(tempObject.getBounds()))
                {
                    setVelY(0);
                    setFall(false);
                    setJump(false);
                }
                else
                {
                    setFall(true);
                }
            }
        }

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) (x + (width/2) - (width/2)/2), (int)( y + (height/2)), width/2, height/2);
    }

    public Rectangle getBoundsRight()
    {
        return new Rectangle((int)(x + width - 5), (int) y+5, 5, height-10);
    }

    public Rectangle getBoundsLeft()
    {
        return new Rectangle((int) x, (int) y+5, 5, height - 10);
    }

    public Rectangle getBoundsTop() { return new Rectangle((int) (x + (width/2) - (width/2)/2), (int) y, width/2, height/2); }
}