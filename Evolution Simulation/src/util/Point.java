package util;

public class Point {

	public double x;
	public double y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public static double getDistance(Point a, Point b) {
		double deltaX = Math.abs(a.x - b.x);
		double deltaY = Math.abs(a.y - b.y);
		return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	}
	
}
