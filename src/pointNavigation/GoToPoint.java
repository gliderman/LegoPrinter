package pointNavigation;

import lejos.geom.Point;

public class GoToPoint {
	
	Point currentPoint;
	Point lastPoint;

	public GoToPoint() {
		currentPoint	= new Point(0, 0);
		lastPoint		= new Point(0, 0);
	}
	
	public Point getCurrentPoint() {
		return new Point(0, 0);
	}
	
	private void setCurrentPoint(Point point) {
		currentPoint = point;
	}
	
	public Point getLastPoint() {
		return new Point(0, 0);
		
	}
	
	private void setLastPoint(Point point) {
		lastPoint = point;
	}
	
	public void goToPoint(Point point, boolean withLift) {
		
	}
	
}
