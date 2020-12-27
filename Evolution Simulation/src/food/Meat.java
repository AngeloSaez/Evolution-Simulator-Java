package food;

import util.Point;

public class Meat extends Food {
	
	public Meat(Point position) {
		super(position);
		this.hue = 0.102f;
		minimumSize = 24;
		maxExpansionSize = 4;
		
		sustinence = 28;
	}

	
	
}
