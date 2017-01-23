package asteroids;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;

public class Asteroids 
{
	private double xco, yco,theta;
	private float velocity;
	private BufferedImage dimg;
	private boolean speed;
	private int iteration,maxiteration;
	
	public Asteroids(double x, double y, double a)
	{
		xco=x;
		yco=y;
		theta=a;
		setImage();
		velocity=0;
		iteration=1;
	}
	
	public void setco(double x, double y)
	{
		if(x>1400)
		{
			xco=0;
		}else if(x<0)
		{
			xco=1400;
		}else if(y>850)
		{
			yco=0;
		}else if(y<0)
		{
			yco=850;
		}else
		{
			xco=x;
			yco=y;
		}
	}
	
	public double getxco()
	{
		return xco;
	}
	
	public double getyco()
	{
		return yco;
	}
	
	public double getAngle()
	{
		return theta;
	}
	
	public void setAngle(double a)
	{
		theta=a;
	}
	
	public void setVelocity(float vel)
	{
		velocity=vel;
	}
	
	public void setIteration()
	{
		iteration++;
		setIterationImage();
		velocity+=10;
//		theta-=.4;
	}
	
	public int getIteration()
	{
		return iteration;
	}
	
	public int getmaxIteration()
	{
		return maxiteration;
	}
	
	public void setMaxiteration(int i)
	{
		maxiteration=i;
	}
	
	public float getVelocity()
	{
		return velocity;
	}
	
	public void speedBumped()
	{
		speed=!speed;
	}
	
	public boolean getSpeed()
	{
		return speed;
	}
	
	public void setIterationImage()
	{
		Random one=new Random();
		int number=one.nextInt(3);
		if(number==0)
		{
			try
			{
				dimg=ImageIO.read(new File("Resources/brentshead.png"));
				maxiteration=2;
			}catch (Exception e){
			}
		}
		if(number==1)
		{
			try
			{
				dimg=ImageIO.read(new File("Resources/asteroid3.png"));
				maxiteration=1;
				velocity+=15;
			}catch (Exception e){
			}
		}
		if(number==2)
		{
			try
			{
				dimg=ImageIO.read(new File("Resources/asteroid4.png"));
				maxiteration=1;
				velocity+=15;
			}catch (Exception e){
			}
		}
	}
	
	public void setImage()
	{
		Random one=new Random();
		int number=one.nextInt(5);
		if(number==0)
		{
			try
			{
				dimg=ImageIO.read(new File("Resources/asteroid1.png"));
				maxiteration=2;
			}catch (Exception e){
			}
		}
		if(number==1)
		{
			try
			{
				dimg=ImageIO.read(new File("Resources/asteroid2.png"));
				maxiteration=1;
				velocity+=15;
			}catch (Exception e){
			}
		}
		if(number==2)
		{
			try
			{
				dimg=ImageIO.read(new File("Resources/asteroid3.png"));
				maxiteration=1;
				velocity+=15;
			}catch (Exception e){
			}
		}
		if(number==3)
		{
			try
			{
				dimg=ImageIO.read(new File("Resources/asteroid4.png"));
				maxiteration=1;
				velocity+=15;
			}catch (Exception e){
			}
		}
		if(number==4)
		{
			try
			{
				dimg=ImageIO.read(new File("Resources/asteroid5.png"));
				maxiteration=2;
			}catch (Exception e){
			}
		}
	}
	
	public BufferedImage getImage()
	{
		return dimg;
	}
}
