package buildFiles;

import lejos.nxt.Motor;

public class Circles {

// Creates a single circle
	
	public Circles() {
		drawTheCircles();
	}
	
	public void drawTheCircles() {
		Motor.C.rotateTo(-720);
		Motor.B.rotateTo(-45);
		Motor.C.rotateTo(0);
		Motor.A.rotate(360*24); // One rev
		Motor.C.rotateTo(-720);
		Motor.B.rotateTo(0);
	}
}
