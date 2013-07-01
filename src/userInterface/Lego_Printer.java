package userInterface;

import buildFiles.*;
import lejos.geom.Point;
import lejos.util.Delay;
import pointNavigation.GoToPoint;

// "Lego_Printer" is what will be shown on the NXT's file selector
public class Lego_Printer {

	// Motor A rotates the plate has a 1:20
	// Motor B moves the arm has a 12:36 or 1:3
	// Motor C lifts the arm
	
	// This code assumes that the arm, lifter, and plate are in starting positions.
	
	GoToPoint arm;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Lego_Printer();
	}
	
	public Lego_Printer() {
		arm = new GoToPoint();
		new Circles();
//		arm.goToPointWithPen(new Point(50, 50), true);
		Delay.msDelay(5000);
	}

}
