package memeTeme;
import robocode.*;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;
import java.awt.Color;
import static robocode.util.Utils.normalRelativeAngleDegrees;
//import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Beef_Jerky - a robot by (your name here)
 */
public class Beef_Jerky extends TeamRobot
	
{
private byte moveDirection = 1;
private byte scanDirection = 1;
	
	public void run() {
		setAdjustRadarForGunTurn(true);
		// Turn the scanner until we find an enemy robot
		setTurnRadarRight(36000);
		execute(); // you must call this!!!
	}

	public void onScannedRobot(ScannedRobotEvent e) {

		// When we scan a robot, turn toward him
		setTurnRight(e.getBearing());
		if(e.getName() == "Beef_Jerky"){
			return;
		}
		else{
		// move a little closer
		if (e.getDistance() > 200)
			setAhead(e.getDistance() / 2);
		// but not too close
		if (e.getDistance() < 100)
			setBack(e.getDistance());

		// shoot at him
		if(e.getDistance() < 150) {
			fire(3);
		}
		else if(e.getDistance() < 250){
			fire(2);
		}
		else{
			fire(1);
		}

		// wobble the radar to generate another scan event
		scanDirection *= -1;
		setTurnRadarRight(36000 * scanDirection);
		}
	}
	
	public void onHitRobot(HitRobotEvent e) {
	

	}
	
	public void onHitByBullet(HitByBulletEvent e) {
		setTurnRight(e.getBearing() + 90);
		setAhead(100 * moveDirection);
		moveDirection *= -1;
	}
	 
	public void onHitWall(HitWallEvent e) {
		setTurnRight(-1 * e.getBearing());
		setAhead(60);
	}
}
