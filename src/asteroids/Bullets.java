package asteroids;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Bullets
{
	private double theta = 0;
	private double xco, yco;
	private BufferedImage dimg;
	private double dtraveled=0;
	private int shipnum;
	private boolean hitwall=false;
	
	public Bullets(double startx, double starty, double theta1, int shipnumber){
		
		vectorCalculator(startx,starty,theta1,2);
		theta=theta1;
		shipnum=shipnumber;
		setImage(shipnum);
	}
	
	public int getShipNumber()
	{
		return shipnum;
	}
	
	public double getx(){
		return xco;
	}
	
	public double gety(){
		return yco;
	}
	
	public void setco(double x,double y)
	{
		if(x>1400)
		{
			xco=0;
			hitwall=true;
		}else if(x<0)
		{
			xco=1400;
			hitwall=true;
		}else if(y>850)
		{
			yco=0;
			hitwall=true;
		}else if(y<0)
		{
			yco=850;
			hitwall=true;
		}else
		{
			xco=x;
			yco=y;
		}
	}
	
	public boolean hitWall()
	{
		return hitwall;
	}
	
	public void setAngle(Double theta1)
	{
		theta=theta1;
	}
	
	public double getangle()
	{
		return theta;
	}
	
	public void setDistance(double d)
	{
		dtraveled+=d;
	}
	
	public double getDistance()
	{
		return dtraveled;
	}
	
	public void setImage(int num)
	{
		if(num==0)
		{
			try
			{
				dimg=ImageIO.read(new File("Resources/LaserBullet.png"));
			}catch (IOException e){
			}
		}
		if(num==1)
		{
			try
			{
				dimg=ImageIO.read(new File("Resources/LaserBullet2.png"));
			}catch (IOException e){
			}
		}
		if(num==2)
		{
			try
			{
				dimg=ImageIO.read(new File("Resources/LaserBullet3.png"));
			}catch (IOException e){
			}
		}
		if(num==3)
		{
			try
			{
				dimg=ImageIO.read(new File("Resources/LaserBullet4.png"));
			}catch (IOException e){
			}
		}
	}
	
	public BufferedImage getImage()
	{
		return dimg;
	}
		
	private void vectorCalculator(double startX, double startY, double theta, double magnitude)
	{
		double X = startX;
		double Y = startY;
		while(Math.sqrt(Math.pow((X - startX), 2) + Math.pow((Y - startY), 2)) <= magnitude)
		{
			Y += Math.sin(theta);
			X += Math.cos(theta);
		}
		xco = X;
		yco = Y;
	}
}