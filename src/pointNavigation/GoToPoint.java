package pointNavigation;

import lejos.geom.Point;
import lejos.nxt.Motor;

/**
 * This class is for moving the print head
 * to different points and does all the
 * calculations for this. It will eventually
 * be able to draw straight lines and draw
 * curves. Eventually...
 * 
 * @author Gliderman
 *
 */
public class GoToPoint {
	
	Point currentPoint;
	Point lastPoint;
	double distToCenterOfPlate = 240; // in mm
	double distToEndOfArm = distToCenterOfPlate;
	
	double returnValue;
	double[] returnArray;

	/**
	 * Constructor, sets defaults to points, and
	 * resets the motors, set thier speeds and
	 * stops them if they are moving.
	 */
	public GoToPoint() {
		currentPoint	= new Point(0, 0);
		lastPoint		= new Point(0, 0);
		returnArray		= new double[3];
		
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
	
	/**
	 * Get the current point.
	 * @return
	 */
	public Point getCurrentPoint() {
		return currentPoint;
	}
	
	/**
	 * Set the current point.
	 * @param point
	 */
	private void setCurrentPoint(Point point) {
		currentPoint = point;
	}
	
	/**
	 * Get the last point.
	 * @return
	 */
	public Point getLastPoint() {
		return lastPoint;
		
	}
	
	/**
	 * Set the last point.
	 * @param point
	 */
	private void setLastPoint(Point point) {
		lastPoint = point;
	}
	
	/**
	 * This is the method to be used most often, it will
	 * call the methods to do the calculations for the
	 * positioning and also run the motors.
	 * @param point
	 * @param withLift
	 */
	public void goToPointWithPen(Point point, boolean withLift) {
		if (point == getCurrentPoint()) {
			// Why should it move?
		} else {
			// Lift pen
			if (withLift) {
				Motor.C.rotateTo(-720);
			}
			
			// Get angles
			double[] angles = calculateAnglesForPoint(point);
			angles[0] = correctAngleForPlate(angles[0], point);
			
			angles[1] = (angles[1] * 3);
			
//			System.out.println(angles[0]);

			// Move motors
			Motor.B.rotateTo(-((int) angles[1]), false);
			Motor.A.rotateTo((int) angles[0], false);
			
			// Lower pen
			if (withLift) {
				Motor.C.rotateTo(0);
			}
			setLastPoint(currentPoint);
			setCurrentPoint(point);
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
		
		returnArray[0] = findAngleForTriangleLengths(distToEndOfArm,
				distToCenterOfPlate, centerToPoint);
		returnArray[1] = findAngleForTriangleLengths(centerToPoint,
				distToCenterOfPlate, distToEndOfArm);
		returnArray[2] = findAngleForTriangleLengths(distToCenterOfPlate,
				centerToPoint, distToEndOfArm);
		
		return returnArray;
	}
	
	/**
	 * Uses SSS for finding the angles (in degrees) for a set
	 * of 3 sides (hence SSS).
	 * @param sideOpposite
	 * @param otherSide
	 * @param anotherSide
	 * @return returnValue
	 */
	private double findAngleForTriangleLengths(double sideOpposite,
			double otherSide, double anotherSide) {
		
		double theOtherSidesMultiplied = (2 * otherSide * anotherSide);
		
		double sidesAddedAndSquared = (Math.pow(otherSide, 2) +
				Math.pow(anotherSide, 2) - Math.pow(sideOpposite, 2));
		
		double divided = (sidesAddedAndSquared/theOtherSidesMultiplied);
		
		returnValue = (Math.toDegrees(Math.acos(divided)));
		
		return returnValue;
	}

	/**
	 * This method uses the pythagorean theorem to find the
	 * distance between the center of the plate and the point.
	 * @param p
	 * @return returnValue
	 */
	private double calculateDistFromCenterToPoint(Point p) {
		returnValue = Math.sqrt(Math.pow(p.x, 2) + Math.pow(p.y, 2));
		return returnValue;
	}
	
	/**
	 * This checks in which quadrant the point is in and
	 * adds to the rotation the coresponding amount for it
	 * to get to the correct spot. Also multiplies by 20 for
	 * the gear ratio.
	 * @param originalAngle
	 * @param p
	 * @return
	 */
	private double correctAngleForPlate(double originalAngle, Point p) {
		// If the point is with completely positive coordinates,
		// then it leaves the angle alone.
		
		if ((p.x >=0) && (p.y < 0)) {
			originalAngle += 90;
		}
		
		if ((p.x < 0) && (p.y < 0)) {
			originalAngle += 180;
		}

		if ((p.x < 0) && (p.y >= 0)) {
			originalAngle += 270;
		}
		
		return (originalAngle*20);
	}
	
}
