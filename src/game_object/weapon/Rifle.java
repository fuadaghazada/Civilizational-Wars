package game_object.weapon;

import game_object.general.GameObject;
import game_object.general.GameObjectHandler;
import game_object.general.ObjectID;
import texture_stuff.ImageLoader;

import java.awt.*;

/**
 *  This class will represent the Rifle object as a weapon with its properties.
 *
 */

public class Rifle extends Weapon
{
    //Textures
    private ImageLoader imageLoader;
    private GameObject owner;

    /**
     * Constructing the game object weapon with given parameters.
     *
     * @param x  - x coordinate of the game object.
     * @param y  - y coordinate of the game object.
     * @param id - object id defines the type of the objects.
     */
    public Rifle(float x, float y, ObjectID id, GameObject owner)
    {
        super(x, y, id);
        this.owner = owner;
        this.setHeight(20);
        this.setWidth(40);



        //textures
        imageLoader =  new ImageLoader(ObjectID.Weapon);
    }

    /**
     *  Fires the rifle
     *  @param gameObjectHandler - all game objects
     */
    public void fire(GameObjectHandler gameObjectHandler, int dir)
    {
        gameObjectHandler.addBullet(new Bullet(x + width,
                                    y + height/2,
                                    ObjectID.Bullet,
                                    dir * 15, gameObjectHandler, this, 0));
    }

    @Override
    public void update(GameObjectHandler gameObjectHandler)
    {
        this.setX(owner.getX() + owner.getWidth()/4);
        this.setY(owner.getY() + owner.getHeight()/2 + 8);

        this.clearBulletList(gameObjectHandler);
    }

    @Override
    public void render(Graphics g)
    {
        g.drawImage(imageLoader.getWeapons()[1], (int)x , (int)y, null);
    }

    @Override
    protected boolean checkCollision(GameObjectHandler gameObjectHandler)
    {
        return false;
    }

    /**
     *  Clears the bullets when it hits tiles.
     *  @param gameObjectHandler - game objects
     */
    public void clearBulletList(GameObjectHandler gameObjectHandler)
    {

        for(int i = 0; i < gameObjectHandler.getBullets().size(); i++)
        {
            Bullet bullet = gameObjectHandler.getBullets().get(i);

            if(bullet.checkCollision(gameObjectHandler))
            {
                gameObjectHandler.removeBullet(bullet);
                break;
            }
        }
    }

    @Override
    public Rectangle getBounds()
    {
        return null;
    }

    public GameObject getOwner() {
        return owner;
    }

    public void setOwner(GameObject owner) {
        this.owner = owner;
    }
}
