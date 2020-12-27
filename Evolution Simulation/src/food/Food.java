package food;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.Main;
import objects.GameObject;
import util.Point;

public abstract class Food extends GameObject {
	
	// Appearence
	protected double minimumSize;
	protected double maxExpansionSize;
	
	// Maximum additional energy
	public double sustinence;
	
	public Food(Point position) {
		super(position, 16, 16);
		this.hue = 0.0f;
		this.saturation = 0.2f;
		this.brightness = 0.85f;
	}
	
	// Update
	public void update(long deltaTime) {
		
		updateAppearance(deltaTime);
		
	}
	
	private void updateAppearance(long deltaTime) {
		// Apply breathing effect
		double breatheEffectPeriod = 600 / Main.fastForward;
		double breatheX = ((getAge() % breatheEffectPeriod) / breatheEffectPeriod);
		double expansionSize = maxExpansionSize + maxExpansionSize * Math.cos(2 * Math.PI * breatheX);
		double newSize = minimumSize + expansionSize;
		drawWidth = newSize;
		drawHeight = newSize;
	}
	
	// Render
	public void render(Graphics2D g) {
		super.render(g);
		// Outline
		g.setColor(Color.getHSBColor(hue, saturation, 0.97f));
		g.drawRect((int) (position.x - drawWidth / 2), (int) (position.y - drawHeight / 2), (int) (drawWidth), (int) (drawHeight));
	}
	
}
