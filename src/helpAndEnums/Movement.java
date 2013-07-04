package helpAndEnums;

/**
 * Represents movement the object can have. Has some uselful methods for 
 * calculating movements
 *
 * @author Gandalf.
 *         Created 4.7.2013.
 */
public class Movement
{
	// ATTRIBUTES	------------------------------------------------------
	
	private double hspeed;
	private double vspeed;
	
	
	// CONSTRUCTOR	------------------------------------------------------
	
	/**
	 * Creates a new movement with the given information
	 *
	 * @param hspeed The horizontal speed in the movement
	 * @param vspeed The vertical speed in the movement
	 */
	public Movement(double hspeed, double vspeed)
	{
		// Initializes attributes
		this.hspeed = hspeed;
		this.vspeed = vspeed;
	}
	
	/**
	 * Alternate method for creating a movement. Uses direction and speed.
	 *
	 * @param direction The direction of the movement
	 * @param speed The movement's speed
	 * @return The movement with the given information
	 */
	public static Movement createMovement(double direction, double speed)
	{
		Movement newmovement = new Movement(0, 0);
		newmovement.setDirSpeed(direction, speed);
		return newmovement;
	}
	
	
	// GETTERS & SETTERS	----------------------------------------------
	
	/**
	 * @return The horizontal component of the movement
	 */
	public double getHSpeed()
	{
		return this.hspeed;
	}
	
	/**
	 * @return The vertical component of the movement
	 */
	public double getVSpeed()
	{
		return this.vspeed;
	}
	
	/**
	 * @return The movement's direction
	 */
	public double getDirection()
	{
		return HelpMath.getVectorDirection(getHSpeed(), getVSpeed());
	}
	
	/**
	 * @return The movement's total speed
	 */
	public double getSpeed()
	{
		return Math.abs(getHSpeed()) + Math.abs(getVSpeed());
	}
	
	/**
	 * @return The opposing movement to the calling movement. The sum of these 
	 * two movements is always 0.
	 */
	public Movement getOpposingMovement()
	{
		return new Movement(-getHSpeed(), -getVSpeed());
	}
	
	/**
	 * Changes the movement's direction
	 *
	 * @param direction The new direction of the movement
	 */
	public void setDirection(double direction)
	{
		setDirSpeed(direction, getSpeed());
	}
	
	/**
	 * Changes the speed of the movement
	 *
	 * @param speed The movement's new speed
	 */
	public void setSpeed(double speed)
	{
		setDirSpeed(getDirection(), speed);
	}
	
	/**
	 * Increases the movement's speed by the given amount
	 *
	 * @param accelration How much the speed increases
	 */
	public void addSpeed(double accelration)
	{
		setSpeed(getSpeed() + accelration);
	}
	
	/**
	 * Changes the movement's direction
	 * 
	 * @param rotation How much the direction turns (degrees)
	 */
	public void addDirection(double rotation)
	{
		setDirection(getDirection() + rotation);
	}
	
	
	// OTHER METHODS	--------------------------------------------------
	
	/**
	 * Returns a movement projected to the given direction
	 *
	 * @param direction The direction to which the movement is projected
	 * @return The projected movement
	 */
	public Movement getDirectionalMovement(double direction)
	{
		double newspeed = HelpMath.getDirectionalForce(getDirection(), 
				getSpeed(), direction);
		return createMovement(direction, newspeed);
	}
	
	/**
	 * returns a movement created multiplying the current movement with a certain multiplier
	 *
	 * @param multiplier The multiplier used in creating the new movement
	 * @return The movement multiplied using the multiplier
	 */
	public Movement getMultiplication(double multiplier)
	{
		return new Movement(getHSpeed() * multiplier, getVSpeed() * multiplier);
	}
	
	/**
	 * Puts the two movements together and returns their sum
	 * 
	 * @param movement1 The first movement
	 * @param movement2 The second movement
	 * @return The sum of the two movements
	 */
	public static Movement movementSum(Movement movement1, Movement movement2)
	{
		// Adds the movements together
		double hspeed = movement1.getHSpeed() + movement2.getHSpeed();
		double vspeed = movement1.getVSpeed() + movement2.getVSpeed();
		// Returns the sum of the movements
		return new Movement(hspeed, vspeed);
	}
	
	private void setDirSpeed(double direction, double speed)
	{
		// If speed is 0, simply creates a new movement
		if (speed == 0)
		{
			this.hspeed = 0;
			this.vspeed = 0;
			return;
		}
		
		double checkdir = HelpMath.checkDirection(direction);
		double alpha = checkdir % 90;
		double firstspeed = alpha / 90 * speed;
		double secondspeed = speed - firstspeed;
		
		if (checkdir >= 270)
		{
			this.hspeed = firstspeed;
			this.vspeed = secondspeed;
		}
		else if (checkdir >= 180)
		{
			this.hspeed = -secondspeed;
			this.vspeed = firstspeed;
		}
		else if (checkdir >= 90)
		{
			this.hspeed = -firstspeed;
			this.vspeed = -secondspeed;
		}
		else
		{
			this.hspeed = secondspeed;
			this.vspeed = -firstspeed;
		}
	}
}
