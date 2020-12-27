package food;

import util.Point;

public class Veggie extends Food {
	
	public Veggie(Point position) {
		super(position);
		this.hue = 0.352f;
		minimumSize = 14;
		maxExpansionSize = 2;
		
		sustinence = 10;
	}

}
