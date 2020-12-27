package engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Main extends JFrame implements KeyListener {

	//Display
	public static Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	
	// Level
	public static Level level;
	
	// Fast forward time
	public static long fastForward = 1;
	
	// Tiles
	public static int tileRes = 32;
	
	// Run variables
	private static boolean isRunning;

	public Main() {
		// Listener setup
		addKeyListener(this);

		// Window setup
		setSize(screen.width, screen.height);
		setLocation(0, 0);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		// GameState
		level = new Level();
		
		// Run setup
		isRunning = true;
		run();
	}
	
	public void run() {
		/*
		 * Frame rate independent game loop
		 */
		
		// Record timing
		long startTime = System.currentTimeMillis();
		long lastTime = startTime;
		
		while (isRunning) {
			// Calculate delta time
			long deltaTime = System.currentTimeMillis() - lastTime;
			// Level update
			level.update(deltaTime * fastForward);
			// Increment lastTime
			lastTime = System.currentTimeMillis();
			// Render
			render();
		}
	}

	public void render() {
		// Creates 2-buffer buffer strategy
		BufferStrategy bs = getBufferStrategy();
		if (getBufferStrategy() == null) {
			createBufferStrategy(2);
			return;
		}

		// Draws to hidden buffer, clears previous image
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.0f));
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		g.clearRect(0, 0, screen.width, screen.height);
		
		// Level render
		level.render(g);
		
		// Disposes graphics object and shows hidden buffer
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		new Main();
		System.exit(0);
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// Main keyPressed
		switch (e.getKeyCode()) {
		case KeyEvent.VK_BACK_SPACE:
			System.exit(0);
			break;
		}
		// Level keyPressed
		level.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Main keyReleased
		switch (e.getKeyCode()) {
		case KeyEvent.VK_Z:
			if (fastForward > 1) fastForward--;
			break;
		case KeyEvent.VK_X:
			fastForward++;
			break;
		}
		// Level keyReleased
		level.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}





}
