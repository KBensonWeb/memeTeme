package memeTeme;
import robocode.*;
import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Francis - a robot by (your name here)
 */
public class Francis extends TeamRobot
{
private byte moveDirection = 1;
	/**
	 * run: Francis's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:
		setBodyColor(new Color(215,55,255));
		
		
	
		while(true) {
			// Replace the next 4 lines with any behavior you would like
			ahead(100);
			turnGunRight(360);
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
	 if (isTeammate(e.getName())) {
           return;
       }  
		// Replace the next line with any behavior you would like
	double distance = e.getDistance();
	setTurnGunRight(getHeading() - getRadarHeading() + e.getBearing());	
	fire(1);
	
	if (distance < 150) {
		setBack(100);
	}
	}
	

	public void onHitByBullet(HitByBulletEvent e) {
		moveDirection *= -1;
		setTurnRight(moveDirection* e.getBearing());
		setBack(100);
	}
	
	public void onHitWall(HitWallEvent e) {
		//moveDirection *= -1;
		setTurnRight(180);
		setBack(100);	
	}	
}
