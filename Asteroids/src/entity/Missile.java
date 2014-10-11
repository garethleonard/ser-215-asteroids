package entity;

import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Created by gareth on 10/4/14.
 */
public class Missile extends MapObject {
    
	private int lifespan;
	private long lifeTimer;
	private Entity entity;
	private int damage;

    public Missile(Entity entity, double[] position, double[] velocity, int missileDamage) {
    	lifespan = 500;
    	angularVelocity = 0;
    	this.position = position;
    	this.velocity = velocity;
    	damage = missileDamage;
    	lifeTimer = System.nanoTime();
    	this.entity = entity;
    	try {
			image = ImageIO.read(getClass().getResourceAsStream("/Resources/missiles/normalMissile.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	radius = image.getWidth() / 2;
    }

    public int getLifespan() {
        return lifespan;
    }

    public void setLifespan(int lifespan) {
        this.lifespan = lifespan;
    }
    
    public void update() {
    	super.update();
    	
    	for (int i = 0; i < entity.getState().getAsteroids().size(); i++) {
    		Asteroid a = entity.getState().getAsteroids().get(i);
    		double aX = a.position[0];
    		double aY = a.position[1];
    		if (Entity.getDistance(position[0], position[1], aX, aY) <= (a.getRadius() + radius)) {
    			a.hit(damage, entity);
    			entity.removeMissile(this);
    		}
    	}
    	
    	if ((System.nanoTime() - lifeTimer) / 1000000 > lifespan) {
    		entity.removeMissile(this);
    	}
    }
    
    public void draw(Graphics2D g) {
    	super.draw(g);
		g.drawImage(image, (int)(position[0] - radius), (int)(position[1] - radius), null);
    }
    
}
