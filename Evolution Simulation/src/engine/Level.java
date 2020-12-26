package engine;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Optional;

import objects.Food;
import objects.GameObject;
import objects.Organism;
import util.Point;

public class Level {
	/*
	 * Where the simulation takes place. Recieves the
	 * deltaTime from Main to be used in update().
	 */
	
	//Display
	public static Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

	// Timing
	public long deltaTime;
	
	// Food
	public long foodTimer = 0;
	public long foodLimit = 4;
	
	// Lists
	public static ArrayList<Food> food = new  ArrayList<Food>();
	public static ArrayList<Organism> organisms = new  ArrayList<Organism>();
	
	public Level() {
		
		populateOrganisms();
		
	}
	
	// Initialization
	private void populateOrganisms() {
		/*
		 * Spawns initial organisms in a different spot around the screen.
		 */

		int initialPopulation = 3;

		for (int i = 0; i < initialPopulation; i++) {
			// Random spawn
			double spawnX = Math.random() * screen.width;
			double spawnY = Math.random() * screen.height;
			Point randomSpawn = new Point(spawnX, spawnY);
			// Create organism
			organisms.add(new Organism(randomSpawn));
		}

	}
	
	// Update
	public void update(long deltaTime) {
		// Update the deltaTime
		this.deltaTime = deltaTime;
		// Produce food over time
		produceFood();
		// Update food
		for (Food f : food) f.update(deltaTime);
		// Update organisms
		for (Organism o : organisms) o.update(deltaTime);
		// Check food collisions
		checkFoodCollisions();
	}
	
	private void produceFood() {
		/*
		 * Food will be produced at a constant rate and spawned
		 * at random locations on screen. The foodTimer long tracks
		 * millis and spawns a new food whenever producitionTimeMillis
		 * is reached.
		 * 
		 * The bounds of potential spawn locations are the screen
		 * bounds because at the moment there is no way to navigate
		 * the world.
		 */
		final long productionTimeMillis = 1000 / Main.fastForward;
		
		// Return if reached limit
		if (food.size() >= foodLimit) return;
		
		// Increment timer
		foodTimer += deltaTime;
		
		// Determine if food needs to be spawned
		if (foodTimer > productionTimeMillis) {
			// Spawn food
			double spawnX = Math.random() * screen.width;
			double spawnY = Math.random() * screen.height;
			food.add(new Food(new Point(spawnX, spawnY)));
			
			// Decrement timer
			foodTimer -= productionTimeMillis;
		}
		
	}
	
	private void checkFoodCollisions() {
		/*
		 * Check if an organism comes into contact with a food
		 * object and then removes the food object.
		 */
		for (Organism o : organisms) {
			Rectangle hitbox = o.getHitbox();
			Food collision = null;
			for (Food f : food) {
				Rectangle foodHitbox = f.getHitbox();
				if (hitbox.intersects(foodHitbox)) {
					collision = f;
					break;
				}
			}
			if (collision != null) {
				food.remove(food.indexOf(collision));
			}
		}
	}
	
	// Render
	public void render(Graphics2D g) {
		// Render food
		for (Food f : food) f.render(g);
		// Render organisms
		for (Organism o : organisms) o .render(g);
		
	}
	
	
	
	
	

}
