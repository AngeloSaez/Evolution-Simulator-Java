package engine;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Optional;

import objects.Food;
import objects.GameObject;
import objects.Organism;
import util.Point;
import util.Style;

public class Level {
	/*
	 * Where the simulation takes place. Recieves the
	 * deltaTime from Main to be used in update().
	 */
	
	//Display
	public static Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	
	// World
	public final int width = screen.width * 2;
	public final int height = screen.height * 2;
	
	// Camera
	public Point cameraTranslation = new Point(0, 0);
	public double cameraZoom = 1.0;

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

		int initialPopulation = 5;

		for (int i = 0; i < initialPopulation; i++) {
			// Random spawn
			double spawnX = Math.random() * width;
			double spawnY = Math.random() * height;
			Point randomSpawn = new Point(spawnX, spawnY);
			// Create organism
			organisms.add(new Organism(randomSpawn));
		}

	}
	
	// Update
	public void update(long deltaTime) {
		// Update the deltaTime
		this.deltaTime = deltaTime;
		// Update camera translation
		updateCamera();
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
			double spawnX = Math.random() * width;
			double spawnY = Math.random() * height;
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
		// Camera
		renderCamera(g);
		// Render world
		g.setColor(Style.levelBackground);
		g.fillRect(0, 0, width, height);
		// Render food
		for (Food f : food) f.render(g);
		// Render organisms
		for (Organism o : organisms) o .render(g);
		
	}
	
	// Camera update/render
	private void updateCamera() {
		// Camera moves independently from both framerate and from the fast forward
		double translationStep = 0.5 * deltaTime / Main.fastForward;
		// Camera translation panning
		if (north) cameraTranslation.y += translationStep;
		if (east) cameraTranslation.x -= translationStep;
		if (south) cameraTranslation.y -= translationStep;
		if (west) cameraTranslation.x += translationStep;
		// Camera zooms independently from both framerate and from the fast forward
		double zoomStep = 0.002 * deltaTime / Main.fastForward;
		// Camera zoom change
		if (zoomIn) cameraZoom += zoomStep;
		if (zoomOut) cameraZoom -= zoomStep;
		// Zoom correction
		if (cameraZoom < 0) cameraZoom = 0;
	}
	
	private void renderCamera(Graphics2D g) {
		// Translate
		g.translate(cameraTranslation.x, cameraTranslation.y);
		// Zoom
		g.scale(cameraZoom, cameraZoom);
	}
	
	// Control
	public boolean north = false;
	public boolean east = false;
	public boolean south = false;
	public boolean west = false;
	public boolean zoomIn = false;
	public boolean zoomOut = false;
	
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			north = true;
			break;
		case KeyEvent.VK_RIGHT:
			east = true;
			break;
		case KeyEvent.VK_DOWN:
			south = true;
			break;
		case KeyEvent.VK_LEFT:
			west = true;
			break;
		case KeyEvent.VK_SHIFT:
			zoomIn = true;
			break;
		case KeyEvent.VK_CONTROL:
			zoomOut = true;
			break;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			north = false;
			break;
		case KeyEvent.VK_RIGHT:
			east = false;
			break;
		case KeyEvent.VK_DOWN:
			south = false;
			break;
		case KeyEvent.VK_LEFT:
			west = false;
			break;
		case KeyEvent.VK_SHIFT:
			zoomIn = false;
			break;
		case KeyEvent.VK_CONTROL:
			zoomOut = false;
			break;
		}
	}
	
	
	

}
