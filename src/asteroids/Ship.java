package asteroids;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ship 
{
	private double xco,yco,origxco,origyco;
	private ShipMovement movement;
	private float velocity;
	private BufferedImage dimg;
	private int lives,kills=0,destroyed=0;
	
	public Ship(double startx, double starty, int number,int life)
	{
		origxco=xco=startx;
		origyco=yco=starty;
		switch (number)
		{
		case 0: movement=new ShipMovement(45);break;
		case 1: movement=new ShipMovement(90);break;
		case 2: movement=new ShipMovement(-45);break;
		case 3: movement=new ShipMovement(-90);break;
		}
		setImage(number);
		lives=life;
	}
	
	public void setVelocity(float vel)
	{
		velocity=vel;
	}
	
	public float getVelocity()
	{
		return velocity;
	}
	
	public void setOriginalCo()
	{
		xco=origxco;
		yco=origyco;
		movement.resetMovement();
	}
	
	public String getMovement()
	{
		return movement.getDirection();
	}
	
	public void setAngle(Double theta1)
	{
		movement.settheta(theta1);
	}
	
	public void setMovement(String direction)
	{
		switch (direction)
		{
		case "Up": movement.moveup();break;
		case "Right": movement.moveright();break;
		case "Down": movement.movedown();break;
		case "Left": movement.moveleft();break;
		default: movement.movedown();
		}
	}
	
	public void setLives()
	{
		if(lives>0)
			lives-=1;
		destroyed++;
	}
	
	public void addLife()
	{
		lives++;
	}
	
	public int getLives()
	{
		return lives;
	}
	
	public int numKills()
	{
		return kills;
	}
	
	public void addKill()
	{
		kills++;
	}
	
	public int destroyed()
	{
		return destroyed;
	}
	
	public double getangle()
	{
		return movement.gettheta();
	}
	
	public void setco(double x,double y)
	{
		if(x>1400)
		{
//			xco=1300;
//			setVelocity(10);
//			movement.settheta(90-getangle()+10);
			xco=0;
		}else if(x<0)
		{
//			xco=40;
//			setVelocity(10);
//			movement.settheta(90-getangle()+10);
			xco=1400;
		}else if(y>850)
		{
//			yco=750;
//			setVelocity(10);
//			movement.settheta(90-getangle()+10);
			yco=0;
		}else if(y<0)
		{
//			yco=50;
//			setVelocity(10);
//			movement.settheta(90-getangle()+10);
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
	
	public void setHull(int num)
	{
		if(num==0)
		{
			try
			{
				dimg=ImageIO.read(new File("Resources/Ship4Hull.png"));
			}catch (IOException e){
			}
		}
		if(num==1)
		{
			try
			{
				dimg=ImageIO.read(new File("Resources/Ship1Hull.png"));
			}catch (IOException e){
			}
		}
		if(num==2)
		{
			try
			{
				dimg=ImageIO.read(new File("Resources/Ship2Hull.png"));
			}catch (IOException e){
			}
		}
		if(num==3)
		{
			try
			{
				dimg=ImageIO.read(new File("Resources/Ship3Hull.png"));
			}catch (IOException e){
			}
		}
	}
	
	public void setImage(int num)
	{
		if(num==0)
		{
			try
			{
				dimg=ImageIO.read(new File("Resources/Ship4.png"));
			}catch (IOException e){
			}
		}
		if(num==1)
		{
			try
			{
				dimg=ImageIO.read(new File("Resources/Ship1.png"));
			}catch (IOException e){
			}
		}
		if(num==2)
		{
			try
			{
				dimg=ImageIO.read(new File("Resources/Ship2.png"));
			}catch (IOException e){
			}
		}
		if(num==3)
		{
			try
			{
				dimg=ImageIO.read(new File("Resources/Ship3.png"));
			}catch (IOException e){
			}
		}
	}
	
	public BufferedImage getImage()
	{
		return dimg;
	}
}
