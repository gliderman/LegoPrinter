package userInterface;

import pointNavigation.GoToPoint;

// "Lego_Printer" is what will be shown on the NXT's file selector
public class Lego_Printer {

	// Motor A rotates the plate
	// Motor B moves the arm
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
	}

}
