package objects;

import engine.Level;
import engine.Main;
import util.Point;

public class Food extends GameObject {
	
	public Food(Point position) {
		super(position, 16, 16);
		this.hue = 0.0f;
		this.saturation = 0.0f;
		this.brightness = 0.7f;
	}
	
	public void update(long deltaTime) {
		
		updateAppearance(deltaTime);
		
	}
	
	private void updateAppearance(long deltaTime) {
		// Apply breathing effect
		double breatheEffectPeriod = 600 / Main.fastForward;
		double breatheX = ((getAge() % breatheEffectPeriod) / breatheEffectPeriod);
		double minimumSize = 14;
		double expansionSize = 2 + 2 * Math.cos(2 * Math.PI * breatheX);
		double newSize = minimumSize + expansionSize;
		drawWidth = newSize;
		drawHeight = newSize;
	}
	
}
