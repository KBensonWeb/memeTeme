package memeTeme;
import robocode.*;
import java.awt.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Celeste - a robot by Kelsi
 */
public class Celeste extends RateControlRobot
{
	int turnCounter;
	int count = 0;
	double gunTurnAmt; // How much to turn our gun when searching
	String trackName; // Name of the robot we're currently tracking

	public void run() {
		setColors(Color.black,Color.red,Color.red);
		setScanColor(Color.white);
		setBulletColor(Color.red);

		turnCounter = 0;
		setGunRotationRate(15);
		trackName = null; // Initialize to not tracking anyone
		setAdjustGunForRobotTurn(true); // Keep the gun still when we turn
		gunTurnAmt = 10; // Initialize gunTurn to 10

		// Loop forever
		while (true) {
			// turn the Gun (looks for enemy)
			turnGunRight(gunTurnAmt);
			// Keep track of how long we've been looking
			count++;
			// If we've haven't seen our target for 2 turns, look left
			if (count > 3) {
				gunTurnAmt = -10;
			}
			// If we still haven't seen our target for 5 turns, look right
			if (count > 5) {
				gunTurnAmt = 10;
			}
			// If we *still* haven't seen our target after 10 turns, find another target
			if (count > 11) {
				trackName = null;
			}
		}
	}

	/**
	 * onScannedRobot:  Here's the good stuff
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		if (trackName != null && !e.getName().equals(trackName)) {
			return;
		}

		if (trackName == null) {
			if (isTeammate(e.getName())) {
				return;
			}
			trackName = e.getName();
		}

		count = 0;
		// If our target is too far away, turn and move toward it.
		if (e.getDistance() > 150) {
			fire(3);
			gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));

			setTurnGunRight(gunTurnAmt);
			setTurnRight(e.getBearing());
			setVelocityRate(4);
			execute();
			return;
		}

		gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
		setTurnGunRight(gunTurnAmt);
		fire(3);

		if (e.getDistance() < 100) {
			fire(3);
			if (e.getBearing() > -90 && e.getBearing() <= 90) {
				setVelocityRate(-6);
				execute();
			} else {
				setVelocityRate(4);
				execute();
			}
		}
		scan();
	}

	/**
	 * onHitRobot:  Set him as our new target
	 */
	public void onHitRobot(HitRobotEvent e) {
		// Set the target
		if (trackName == null) {
			if (isTeammate(e.getName())) {
				return;
			}
			trackName = e.getName();
		}
		gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
		setTurnGunRight(gunTurnAmt);
		fire(3);
		setVelocityRate(-6);
		execute();
	}

	public void onHitByBullet(HitByBulletEvent e) {
		// Turn to confuse the other robot
		setTurnRate(5);
	}

	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		setVelocityRate(-1 * getVelocityRate());
		execute();
	}
}
