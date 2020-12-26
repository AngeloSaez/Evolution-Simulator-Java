package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import util.Point;
import util.Vect2D;

public abstract class GameObject {
	
	public long birthTime;

	public Point position;
	public Vect2D velocity;
	
	public double drawWidth;
	public double drawHeight;
	
	public float hue;
	public float saturation;
	public float brightness;
	
	public Color getColor() {
		return Color.getHSBColor(hue, saturation, brightness);
	}
	
	public GameObject(Point position, double drawWidth, double drawHeight) {
		this.birthTime = System.currentTimeMillis();
		
		this.position = position;
		this.drawWidth = drawWidth;
		this.drawHeight = drawHeight;
		this.velocity = new Vect2D(0, 0);
		
		this.hue = 1.0f;
		this.saturation = 1.0f;
		this.brightness = 1.0f;
	}
	
	public void update(long deltaTime) {
		
	}
	
	public void render(Graphics2D g) {
		g.setColor(getColor());
		g.fillRect((int) (position.x - drawWidth / 2), (int) (position.y - drawHeight / 2), (int) (drawWidth), (int) (drawHeight));
	}
	
	// Misc
	public long getAge() {
		// Returns the age in millis of the object
		return System.currentTimeMillis() - birthTime;
	}
	
	public Rectangle getHitbox() {
		return new Rectangle((int) (position.x - drawWidth / 2), (int) (position.y - drawHeight / 2), (int) (drawWidth), (int) (drawHeight));
	}
	
}
