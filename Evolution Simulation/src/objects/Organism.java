package objects;

import java.util.ArrayList;

import engine.Level;
import engine.Main;
import util.Point;

public class Organism extends GameObject {
	/*
	 * Simulated beings in the program. Extends from a
	 * game object to give it some basic properties and
	 * has a few variables categorized as 'traits' that
	 * are randomized on creation to make each one unique.
	 */
	
	// Traits
	double runSpeed;
	
	public Organism(Point position) {
		super(position, Main.tileRes, Main.tileRes);
		// Game object initialization
		this.saturation = 0.4f;
		this.brightness = 0.7f;
		// Trait initialization
		hue = (float) (Math.random());
		runSpeed = 0.001 + Math.random() * 0.099;
	}
	
	public void update(long deltaTime) {
		seekFood(deltaTime);
		translate(deltaTime);
	}
	
	public void translate(long deltaTime) {
		position.x += velocity.i * deltaTime;
		position.y += velocity.j * deltaTime;
	}

	public void seekFood(long deltaTime) {
		/*
		 * Accelerates towards the nearest food object.
		 * Math done with basic trig. Speed based off
		 * of the runSpeed trait.
		 * 
		 * Velocity does not need to be multiplied by
		 * deltaTime here because it is done in the
		 * translate() method.
		 */
		ArrayList<Food> food = Level.food;
		
		// Returns if no food present
		if (food.size() < 1) {
			velocity.i = 0;
			velocity.j = 0;
			return;
		}

		// Find the closest food
		double shortestDist = -1;
		int shortestDistIndex = 0;
		for (Food f : food) {
			// Find the distance of the first shortest food
			if (shortestDist == -1) {
				shortestDist = Point.getDistance(this.position, f.position);
			}
			// Compare subsequent food distances
			else {
				double dist = Point.getDistance(this.position, f.position);
				if (dist < shortestDist) {
					shortestDist = dist;
					shortestDistIndex = food.indexOf(f);
				}
			}
		}
		// Go towards closest food object
		Food closest = food.get(shortestDistIndex);
		double hDist = Math.abs(closest.position.x - position.x);
		double vDist = Math.abs(closest.position.y - position.y);
		double theta = Math.atan((vDist) / hDist);
		velocity.i = runSpeed * Math.cos(theta);
		velocity.i = (closest.position.x - position.x > 0) ? velocity.i : -velocity.i;
		velocity.j = runSpeed * Math.sin(theta);
		velocity.j = (closest.position.y - position.y > 0) ? velocity.j : -velocity.j;
	}
	
	
	
}
