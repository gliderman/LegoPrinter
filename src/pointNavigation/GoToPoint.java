package pointNavigation;

import lejos.geom.Point;
import lejos.nxt.Motor;

public class GoToPoint {
	
	Point currentPoint;
	Point lastPoint;
	double distToCenterOfPlate = 228; // in mm
	double distToEndOfArm = distToCenterOfPlate;

	public GoToPoint() {
		currentPoint	= new Point(0, 0);
		lastPoint		= new Point(0, 0);
		
		// Stop motors
		Motor.A.stop();
		Motor.B.stop();
		Motor.C.stop();
		
		// deg/sec
		Motor.A.setSpeed(180);
		Motor.B.setSpeed(90);
		Motor.C.setSpeed(720);

		// Reset motors
		Motor.A.resetTachoCount();
		Motor.B.resetTachoCount();
		Motor.C.resetTachoCount();
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
	
	public void goToPointWithPen(Point point, boolean withLift) {
		if (point == currentPoint) {
			// Why should it move?
		} else {
			// Lift pen
			if (withLift) {
				Motor.C.rotateTo(-720);
			}
			
			double[] angles = calculateAnglesForPoint(point);
			angles[0] = correctAngleForPlate(angles[0], point);
			
			Motor.A.rotateTo((int) angles[0], true);
			Motor.B.rotateTo(-((int) angles[1]), false);
			
			// Lower pen
			if (withLift) {
				Motor.C.rotateTo(0);
			}
		}
	}
	
	/**
	 * This method returns an array of doubles filled with the
	 * angles of the triangle created between the center of the
	 * plate, the arm pivot point, and the actual point to go to.
	 * And it returns them in that order, too.
	 * @param p
	 * @return angles
	 */
	private double[] calculateAnglesForPoint(Point p) {
		double centerToPoint = calculateDistFromCenterToPoint(p);
		double[] angles = {0.0, 0.0, 0.0};
		
		angles[0] = findAngleForTriangleLengths(distToEndOfArm,
				distToCenterOfPlate, centerToPoint);
		angles[1] = findAngleForTriangleLengths(centerToPoint,
				distToCenterOfPlate, distToEndOfArm);
		angles[2] = findAngleForTriangleLengths(distToCenterOfPlate,
				centerToPoint, distToEndOfArm);
		
		return angles;
	}
	
	private double findAngleForTriangleLengths(double sideOpposite,
			double otherSide, double anotherSide) {
		
		double theOtherSidesMultiplied = (2 * otherSide * anotherSide);
		double sidesAddedAndSquared = (Math.pow(otherSide, 2) +
				Math.pow(anotherSide, 2) - Math.pow(sideOpposite, 2));
		
		double neededAngle =
				Math.acos(theOtherSidesMultiplied/sidesAddedAndSquared);
		
		return neededAngle;
	}
	
	private double calculateDistFromCenterToPoint(Point p) {
		double length = Math.sqrt(Math.pow(p.x, 2) + Math.pow(p.y, 2));
		return length;
	}
	
	private double correctAngleForPlate(double originalAngle, Point p) {
		// If the point is with completely positive coordinates,
		// then it leaves the angle alone.
		
		if ((p.x < 0) && (p.y >= 0)) {
			originalAngle += 270;
		}
		
		if ((p.x >=0) && (p.y < 0)) {
			originalAngle += 90;
		}
		
		if ((p.x < 0) && (p.y < 0)) {
			originalAngle += 180;
		}
		
		return originalAngle;
	}
	
}
