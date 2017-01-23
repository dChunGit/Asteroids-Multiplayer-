package asteroids;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class ShipDraw extends JPanel
{
	private ArrayList<Ship> ships;
	private ArrayList<Bullets> bullets;
	private HashMap<Ship, Integer> immunity=new HashMap<Ship,Integer>();
	private Set<Ship> dead_ships;
	private boolean[] hit_ships;
	private boolean timed=false;
	private int[] eliminatedtimer;
	private int number_of_ships,life=0;
	private BufferedImage dimg, background;
	private BufferedImage explosion;
	private float interpolation;
	private double maxdistance=600,imgx,imgy,elapsedtime=0;
	
	public ShipDraw()
	{
		ships=new ArrayList<Ship>();
		bullets=new ArrayList<Bullets>();
		dead_ships=new HashSet<Ship>();
		setImage();
		setBackground();
		elapsedtime=System.nanoTime();
	}
	
	public void setNumberShips(int total)
	{
		number_of_ships=total;
		hit_ships=new boolean[number_of_ships];
		eliminatedtimer=new int[number_of_ships];
		for(int a=0;a<number_of_ships;a++)
		{
			hit_ships[a]=false;
			eliminatedtimer[a]=60;
		}
		life=total;
	}
	
	public void addShip(int number)
	{
		if(number_of_ships==1)
		{
			Ship ship=new Ship(650,400,number,3);
			ships.add(ship);
			immunity.put(ship, 60);
		}
		else
		{
			switch (number)
			{
			case 0:
			{
				Ship one=new Ship(50,50,number,3);
				ships.add(one);
				immunity.put(one, 60);
			}break;
			case 1:
			{
				Ship two=new Ship(1300,50,number,3);
				ships.add(two);
				immunity.put(two, 60);
			}break;
			case 2:
			{
				Ship three=new Ship(50,700,number,3);
				ships.add(three);
				immunity.put(three, 60);
			}break;
			case 3:
			{
				Ship four=new Ship(1300,700,number,3);
				ships.add(four);
				immunity.put(four, 60);
			}break;
			}
		}
	}
	
	public String[] getDirections()
	{
		String[] direction=new String[ships.size()];
		for(int a=0;a<ships.size();a++)
		{
			direction[a]=getShip(a).getMovement();
		}
		return direction;
	}
	
	public void setShips(ArrayList<Ship> shipadd)
	{
		ships=shipadd;
	}
	
	public Ship getShip(int index)
	{
		return (Ship) ships.get(index);
	}
	
	public ArrayList<Ship> getShips()
	{
		return ships;
	}
	
	public int Size()
	{
		return ships.size();
	}
	
	public void setInterpolation(float interpolationa)
	{
		interpolation=interpolationa;
	}
	
	public void update(HashMap<Ship,String> pressed,String[] direction)
	{
		life=0;
		int l=0;
		while(l<ships.size())
		{
			if(ships.get(l).getLives()>0)
			{
				life+=1;
			}
			l++;
		}
		if(life>0)
		{
			for(int a=0;a<ships.size();a++)
			{
				Ship s=ships.get(a);
				float velocity=s.getVelocity();
				double distance=0, rotation=.1;
				float acceleration=(float) 3;
				double[] coords;
				
				if(pressed.get(s).equals("true"))
				{
					distance=(velocity*interpolation)+(.5*acceleration*Math.pow(interpolation, 2));
					velocity=velocity+(acceleration*interpolation);
					if(velocity<50)
					{
						s.setVelocity(velocity);
					}
				}else
				{
					distance=(velocity*interpolation)-(.5*(acceleration)*Math.pow(interpolation, 2));
					s.setVelocity(velocity-(acceleration*interpolation));
				}
				
				if(distance>0)
				{
					coords = vectorCalculator(s.getxco(), s.getyco(), s.getangle(),distance);
				  	s.setco(coords[0], coords[1]);
				}else
				{
					s.setVelocity(0);
				}
				
				if(direction[a]=="Left")
				{
					s.setAngle(s.getangle()-rotation);
					
				}else if(direction[a]=="Right")
				{
					s.setAngle(s.getangle()+rotation);
				}
			}
			
			for(int b=0;b<ships.size();b++)
			{
				double xco=ships.get(b).getxco();
				double yco=ships.get(b).getyco();
				
				for(int c=0;c<ships.size();c++)
				{
					double xco1=ships.get(c).getxco();
					double yco1=ships.get(c).getyco();
					
					double distance_to_ship=Math.sqrt(Math.pow((Math.abs(xco-xco1)),2)+Math.pow((Math.abs(yco-yco1)),2));
					if(b!=c)
					{
						if(distance_to_ship>33.5)
							continue;
						else if(!dead_ships.contains(ships.get(c))&&!dead_ships.contains(ships.get(b))&&immunity.get(ships.get(c))==60&&immunity.get(ships.get(b))==60)
						{
							hit_ships[c]=true;
							hit_ships[b]=true;
						}
					}
				}
			}
		}else
		{
			repaint();
		}
	}
	
	public void updatebullets(Set<Bullets> bullets1, Set<Bullets> bullets2, Set<Bullets> bullets3, Set<Bullets> bullets4, ArrayList<Bullets> bullets, HashMap<Ship,String> bpress)
	{
		int size=bullets.size();
		float velocity=105;
		double distance=0,dtraveled;
		double[] coords;
		boolean[] moved=new boolean[size];
		double[] distancemoved=new double[size];
		this.bullets=bullets;

		for(int a=0;a<size;a++)
		{
			Bullets b=bullets.get(a);
				
			if(bullets1.contains(b))
				distance=(velocity+ships.get(0).getVelocity())*interpolation;
			else if(bullets2.contains(b))
				distance=(velocity+ships.get(1).getVelocity())*interpolation;
			else if(bullets3.contains(b))
				distance=(velocity+ships.get(2).getVelocity())*interpolation;
			else if(bullets4.contains(b))
				distance=(velocity+ships.get(3).getVelocity())*interpolation;
			
			dtraveled=b.getDistance();
			if(dtraveled>maxdistance)
			{
				moved[a]=false;
				distancemoved[a]=0;
				continue;
			}else
			{
				coords = vectorCalculator(b.getx(), b.gety(), b.getangle(),distance);
				b.setco(coords[0], coords[1]);
				b.setDistance(distance);
				moved[a]=true;
				distancemoved[a]=distance;
			}
		}
	
		for(int c=0;c<bullets.size();c++)
		{
			if(bullets.get(c)!=null)
				if(moved[c]==false)
				{
					bullets.remove(c);
				}
		}
		
		
		int b=0;
		while(b<bullets.size())
		{
			double xco=bullets.get(b).getx();
			double yco=bullets.get(b).gety();
			
			for(int c=0;c<ships.size();c++)
			{
				double xco1=ships.get(c).getxco();
				double yco1=ships.get(c).getyco();
				double distance_to_ship=Math.sqrt(Math.pow((Math.abs(xco-xco1)),2)+Math.pow((Math.abs(yco-yco1)),2));
				
				
				if(bullets.get(b).getShipNumber()!=c)
				{
					if(distance_to_ship>40)
						continue;
					else if(!dead_ships.contains(ships.get(c))&&!dead_ships.contains(ships.get(b))&&
							immunity.get(ships.get(c))==60&&immunity.get(ships.get(b))==60)
					{
						hit_ships[c]=true;
						if(bullets1.contains(bullets.get(b)))
							ships.get(0).addKill();
						else if(ships.size()>1)
							if(bullets2.contains(bullets.get(b)))
								ships.get(1).addKill();
						else if(ships.size()>2)
							if(bullets3.contains(bullets.get(b)))
								ships.get(2).addKill();
						else if(ships.size()>3)
							if(bullets4.contains(bullets.get(b)))
								ships.get(3).addKill();
						bullets.remove(b);
						b--;
						break;
					}
				}
			}
			b++;
		}
	}
	
	private double[] vectorCalculator(double startX, double startY, double theta, double magnitude)
	{
		double X = startX;
		double Y = startY;
		while(Math.sqrt(Math.pow((X - startX), 2) + Math.pow((Y - startY), 2)) <= magnitude)
		{
			Y += Math.sin(theta);
			X += Math.cos(theta);
		}
		double[] coords = new double[2];
		coords[0] = X;
		coords[1] = Y;
		return coords;
	}
	
	public void drawShips(Graphics g)
	{
		double xco,yco;
		g.drawImage(background, 0, 0, null);
		for(int a=0;a<ships.size();a++)
		{
			if(hit_ships[a]==false&&ships.get(a).getLives()>0)
			{
				Ship s=ships.get(a);
				dimg=s.getImage();
				xco=s.getxco();
				yco=s.getyco();
				Graphics2D g2d=(Graphics2D) g.create();
				if(immunity.get(ships.get(a))<60)
				{
					if(immunity.get(ships.get(a))-1>0)
					{
						immunity.put(ships.get(a), immunity.get(ships.get(a))-1);
//						ship.get(0).setHull(0);
						if(immunity.get(ships.get(a))%5==0)
						{
							dimg=s.getImage();
							g2d.rotate(s.getangle(),(int) (xco + dimg.getWidth() / 2), (int) (yco + dimg.getHeight() / 2));
							g2d.drawImage(dimg, (int) xco, (int) yco, dimg.getWidth(), dimg.getHeight(), null);
						}
					}
					else
					{
						immunity.put(ships.get(a), 60);
//						ship.get(0).setImage(0);
					}
				}else
				{
					dimg=s.getImage();
					g2d.rotate(s.getangle(),(int) (xco + dimg.getWidth() / 2), (int) (yco + dimg.getHeight() / 2));
					g2d.drawImage(dimg, (int) xco, (int) yco, dimg.getWidth(), dimg.getHeight(), null);
				}
				imgx=dimg.getHeight();
				imgy=dimg.getWidth();
				
			}else if(ships.get(a).getLives()==0)
				{
//					drawNoship(g,a);
					if(eliminatedtimer[a]>0)
					{
						drawEliminated(g,a);
						eliminatedtimer[a]=eliminatedtimer[a]-1;
					}
					dead_ships.add(ships.get(a));
				}
			else
				drawExplosion(g,a);
			
			if(immunity.get(ships.get(a))<60)
			{
				if(immunity.get(ships.get(a))-5>0)
				{
					immunity.put(ships.get(a), immunity.get(ships.get(a))-1);
//					ships.get(a).setHull(a);
				}
				else
				{
					immunity.put(ships.get(a),60);
//					ships.get(a).setImage(a);
				}
			}
		}
	}
	
	public void drawEliminated(Graphics g, int a)
	{
		Graphics2D g2d=(Graphics2D) g;
		g.setColor(Color.RED);
		g2d.drawString("Player "+(a+1)+" eliminated", 650, 70);
	}
	
	public void drawBullet(Graphics g)
	{
		double xco,yco;
		for(int a=0;a<bullets.size();a++)
		{
			Bullets b=bullets.get(a);
			dimg=b.getImage();
			xco=b.getx();
			yco=b.gety();
			Graphics2D g3d=(Graphics2D) g.create();
			g3d.rotate(b.getangle(),(int) (xco+imgx/2),(int) (yco+imgy/2));
			g3d.drawImage(dimg, (int) (xco+imgx/2+dimg.getWidth()/3), (int) (yco+imgy/2-dimg.getHeight()/3-5), dimg.getWidth(), dimg.getHeight(),null);
		}
	}
	
	public void drawNoship(Graphics g, int ship_hit)
	{
		double xco=ships.get(ship_hit).getxco();
		double yco=ships.get(ship_hit).getyco();
		ships.get(ship_hit).setHull(ship_hit);
		BufferedImage temp=ships.get(ship_hit).getImage();
		
		Graphics2D g4d=(Graphics2D) g.create();
		g4d.rotate(ships.get(ship_hit).getangle(),(int) (xco+imgx/2),(int) (yco+imgy/2));
		g4d.drawImage(temp, (int) xco, (int) yco, temp.getWidth(), temp.getHeight(), null);
	}
	
	public void drawExplosion(Graphics g, int ship_hit)
	{
		
		double xco=ships.get(ship_hit).getxco();
		double yco=ships.get(ship_hit).getyco();
		Graphics2D g4d=(Graphics2D) g.create();
		g4d.drawImage(explosion, (int) xco, (int) yco, explosion.getWidth(), explosion.getHeight(), null);
		
		hit_ships[ship_hit]=false;
		ships.get(ship_hit).setOriginalCo();
		ships.get(ship_hit).setVelocity(0);
		immunity.put(ships.get(ship_hit), immunity.get(ships.get(ship_hit))-1);
		if(immunity.get(ships.get(ship_hit))<=0)
		{
			immunity.put(ships.get(ship_hit),60);
		}
		
		
		if(ships.get(ship_hit).getLives()>0)
			ships.get(ship_hit).setLives();
	}
	
	public void drawBanner(Graphics g)
	{
		Graphics2D g2d=(Graphics2D) g.create();
		g2d.setColor(Color.WHITE);
		float offsetx=50;
		
		for(int a=0;a<ships.size();a++)
		{
			g2d.drawString("Player "+(a+1)+": "+ships.get(a).getLives(), offsetx, 20);
			offsetx+=400;
		}
	}
	
	public void theend(Graphics g)
	{
		BufferedImage MissionCompleted=null;
		if(!timed)
		{
			elapsedtime=(System.nanoTime()-elapsedtime)*1000;
			elapsedtime=Math.round((elapsedtime/(Math.pow(10, 9))));
			elapsedtime/=1000;
			timed=true;
		}
		Graphics2D g5d=(Graphics2D) g.create();
		try
		{
			MissionCompleted=ImageIO.read(new File("Resources/MissionCompleted.png"));
		}catch (IOException e){
		}
		g5d.drawImage(MissionCompleted, 450, 400, null);
		int b=0,winner=20;
		boolean found=false;
		while(b<ships.size()&&found==false)
		{
			if(ships.get(b).getLives()!=0)
			{
				winner=b;
				found=true;
			}
			b++;
		}
		g5d.setColor(Color.WHITE);
		g5d.scale(1.5,1.5);
		g5d.drawString("Player "+(winner+1)+" has triumphed", 400, 300);
		g5d.drawString("Time To Victory: "+elapsedtime+" seconds", 380, 340);
		int x=230,y=340;
		for(int a=0;a<ships.size();a++)
		{
			double kps=0;
			if(ships.get(a).numKills()==0)
			{
				kps=0;
			}else
			{
				kps=(elapsedtime)/ships.get(a).numKills()*10;
				kps=Math.round(kps);
				kps/=10;
			}
			
			double atpk=0;
			if(ships.get(a).numKills()!=0)
			{
				atpk=(ships.get(a).numKills())/(elapsedtime*60)*10;
				atpk=Math.round(atpk);
				atpk/=10;
			}
			
			g5d.drawString("Player "+(a+1)+" -- Hits: "+ships.get(a).numKills()+"    Deaths: "+ships.get(a).destroyed()+
					"    Hits Per Second: "+kps+"    Average Time Per Hit: "+atpk+" seconds", x, y+20);
			y+=20;
		}
	}

	
	public void setImage()
	{
		try
		{
			explosion=ImageIO.read(new File("Resources/explode.png"));
		}catch (IOException e){
		}
	}
	
	public void setBackground()
	{
		try
		{
			background=ImageIO.read(new File("Resources/Background1.jpg"));
		}catch (IOException e){
		}
	}
	
	private void clearGraphics(Graphics g)
	{
		g.clearRect(0, 0, getWidth(), getHeight());
	}
	
	@Override
    public void paintComponent(Graphics g)
	{
        super.paintComponent(g);
        clearGraphics(g);
        drawShips(g);
        drawBullet(g);
        drawBanner(g);
        
        if(life<2&&ships.size()>1)
        {
        	theend(g);
        }
    }
}
