package game_object.weapon;

import game_object.general.GameObject;
import game_object.general.GameObjectHandler;
import game_object.general.ObjectID;
import texture_stuff.ImageLoader;

import java.awt.*;

/**
 *  This class will represent the Laser gun object as a weapon with its properties.
 *
 */

public class LaserGun extends Weapon
{
    //Textures
    private ImageLoader imageLoader;
    private GameObject owner;
    /**
     * Constructing the game object weapon with given parameters.
     *  @param x  - x coordinate of the game object.
     * @param y  - y coordinate of the game object.
     * @param id - object id defines the type of the objects.
     */
    public LaserGun(double x, double y, ObjectID id, GameObject owner)
    {
        super(x, y, id);

        this.setHeight(20);
        this.setWidth(40);

        this.owner = owner;

        //textures
        imageLoader =  new ImageLoader(ObjectID.Weapon);
    }

    /**
     *  Fires the rifle
     */
    public void fire(int dir)
    {
        GameObjectHandler.getInstance().addBullet(new Bullet(x + width,
                y + height/2,
                ObjectID.Bullet,
                dir * 15, this, 1));
    }

    @Override
    public void update()
    {

        this.setX(owner.getX() + owner.getWidth()/4);
        this.setY(owner.getY() + owner.getHeight()/2 + 8);


        this.clearBulletList();
    }

    @Override
    public void render(Graphics g)
    {
        if(this.dir == 1)
            g.drawImage(imageLoader.getWeapons()[4], (int)x , (int)y, null);
        else if(dir == -1)
            g.drawImage(imageLoader.getWeapons()[5], (int)x , (int)y, null);
    }

    @Override
    protected boolean checkCollision()
    {
        return false;
    }

    /**
     *  Clears the bullets when it hits tiles.
     */
    public void clearBulletList()
    {

        for(int i = 0; i < GameObjectHandler.getInstance().getBullets().size(); i++)
        {
            Bullet bullet = GameObjectHandler.getInstance().getBullets().get(i);
            if(bullet.checkCollision())
            {
                GameObjectHandler.getInstance().removeBullet(bullet);
                break;
            }
        }
    }

    @Override
    public Rectangle getBounds()
    {
        return null;
    }

    @Override
    public GameObject getOwner() {
        return owner;
    }

    public void setOwner(GameObject owner) {
        this.owner = owner;
    }
}
