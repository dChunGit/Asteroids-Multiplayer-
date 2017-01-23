package asteroids;


public class ShipMovement 
{
	private String direction;
	double theta,origtheta;
	
	public ShipMovement(double angle)
	{
		direction="Down";
		theta=origtheta=angle;
	}
	
	public void moveup()
	{
		direction="Up";
	}
	
	public void movedown()
	{
		direction="Down";
	}
	
	public void moveleft()
	{
		direction="Left";
	}
		
	public void moveright()
	{
		direction="Right";
	}
	
	public void settheta(double theta1)
	{
		theta=theta1;
	}
	
	public double gettheta()
	{
		return theta;
	}
	
	public String getDirection()
	{
		return direction;
	}
	
	public void resetMovement()
	{
		theta=origtheta;
	}
}
