package asteroids;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

@SuppressWarnings("serial")
public class ShipDraw1P extends JPanel
{
	private ArrayList<Ship> ship;
	private ArrayList<Bullets> bullets;
	private boolean hit_ships,dead_ships;
	private int immunity=60,stagepassed=180;
	private int stagespassed,life=0;
	private int stagenum=1;
	private BufferedImage dimg, background;
	private BufferedImage explosion;
	private float interpolation;
	private double maxdistance=600,imgx,imgy,elapsedtime,timepause;
	private boolean asteroidcompleted=false,timed=false;
//	private boolean nextlevel=false;
	private int asteroidnum;
	private ArrayList<Asteroids> asteroids=new ArrayList<Asteroids>();
	private BufferedImage asteroidimage;
	
	public ShipDraw1P()
	{
		ship=new ArrayList<Ship>();
		bullets=new ArrayList<Bullets>();
		dead_ships=false;
		setImage();
		setBackground();
		hit_ships=false;
		life=1;
		elapsedtime=System.nanoTime();
		startMusic();
	}
	
	public void addShip()
	{
		Ship shipa=new Ship(650,400,0,3);
		ship.add(shipa);
		immunity=60;
	}
	
	public String getDirections()
	{
		String direction=getShip(0).getMovement();
		return direction;
	}
	
	public Ship getShip(int index)
	{
		return ship.get(0);
	}
	
	public ArrayList<Ship> getShips()
	{
		return ship;
	}
	
	public void setInterpolation(float interpolationa)
	{
		interpolation=interpolationa;
	}
	
	public void startMusic()
	{
		try
		{
			InputStream in=new FileInputStream("Resources/Sounds/onestop.wav");
			AudioStream audiostream=new AudioStream(in);
			AudioPlayer.player.start(audiostream);
		}catch (Exception e){
		}
	}
	
	public void setAsteroids(int stage)
	{
		stagenum=stage;
		asteroidnum=stage*16;
		Random one=new Random();
		for(int b=0;b<asteroidnum;b++)
		{
			double a=one.nextDouble()*1000/.8*1.9;
			asteroids.add(new Asteroids(a,a,a));
			asteroids.get(b).setVelocity(asteroids.get(b).getVelocity()+10);
		}
		immunity--;
	}
	
	public void updateAsteroids()
	{
		int a=0;
		int size=asteroids.size();
		float velocity=60;
		double distance=0;
		double[] coords;
		
		if(asteroids.size()==0)
		{
			asteroidcompleted=true;
//			setTimeStart();
			repaint();
		}
		
		while(a<size&&size!=0)
		{
			Asteroids s=asteroids.get(a);
			velocity=s.getVelocity();
			distance=velocity*interpolation;
			coords = vectorCalculator(s.getxco(), s.getyco(), s.getAngle(),distance);
			s.setco(coords[0], coords[1]);
			a++;
		}
		
		
		for(int b=0;b<asteroids.size();b++)
		{
			double xco=asteroids.get(b).getxco();
			double yco=asteroids.get(b).getyco();
			
			double xco1=ship.get(0).getxco();
			double yco1=ship.get(0).getyco();
			double distance_to_ship=Math.sqrt(Math.pow((Math.abs(xco-xco1)),2)+Math.pow((Math.abs(yco-yco1)),2));
			int maxiteration=asteroids.get(b).getmaxIteration();
			if(maxiteration==1)
			{
				if(distance_to_ship>35)
					continue;
				else
				{
					if(immunity==60)
					{
						hit_ships=true;
						ship.get(0).addKill();
						asteroids.remove(b);
						b--;
					}
				}
			}else if(maxiteration==2)
			{
				if(distance_to_ship>60)
					continue;
				else
				{
					if(immunity==60)
					{
						hit_ships=true;
						ship.get(0).addKill();
						if(asteroids.get(b).getIteration()==asteroids.get(b).getmaxIteration())
							asteroids.remove(b);
						else{
							asteroids.get(b).setIteration();
						}
					}
				}
			}
		}

	}
	
	public void update(HashMap<Ship,String> pressed,String[] direction)
	{
		life=0;
		int l=0;
		while(l<ship.size())
		{
			if(ship.get(l).getLives()>0)
			{
				life+=1;
			}
			l++;
		}
		
		if(life>0)
		{
			Ship s=ship.get(0);
			float velocity=ship.get(0).getVelocity();
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
				distance=(velocity*interpolation)-(.5*(acceleration/10)*Math.pow(interpolation, 2));
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
			
			if(direction[0]=="Left")
			{
				s.setAngle(s.getangle()-rotation);
				
			}else if(direction[0]=="Right")
			{
				s.setAngle(s.getangle()+rotation);
			}
		}else
		{
			repaint();
		}		
	}
	
	public void updatebullets(Set<Bullets> bullets1, ArrayList<Bullets> bullets, HashMap<Ship,String> bpress)
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
				distance=(velocity+ship.get(0).getVelocity())*interpolation;
			
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
		double xco1=ship.get(0).getxco();
		double yco1=ship.get(0).getyco();
		while(b<bullets.size())
		{
			double xco=bullets.get(b).getx();
			double yco=bullets.get(b).gety();
			double distance_to_ship=Math.sqrt(Math.pow((Math.abs(xco-xco1)),2)+Math.pow((Math.abs(yco-yco1)),2));
			
			
			if(bullets.get(b).getShipNumber()!=0)
			{
				if(distance_to_ship>40)
					continue;
				else if(!dead_ships&&immunity==60)
				{
					hit_ships=true;
					bullets.remove(b);
					b--;
				}
			}
			b++;
		}
		
		for(int a=0;a<asteroids.size();a++)
		{
			double xco=asteroids.get(a).getxco();
			double yco=asteroids.get(a).getyco();
			for(b=0;b<bullets.size();b++)
			{
				xco1=bullets.get(b).getx();
				yco1=bullets.get(b).gety();
				double distance_to_ship=Math.sqrt(Math.pow((Math.abs(xco-xco1)),2)+Math.pow((Math.abs(yco-yco1)),2));
				if(asteroids.size()>a)
				{
					int maxiteration=asteroids.get(a).getmaxIteration();
					if(maxiteration==1)
					{
						if(distance_to_ship>40)
							continue;
						else
						{
							ship.get(0).addKill();
							asteroids.remove(a);
							bullets.remove(b);
						}
					}else if(maxiteration==2)
					{
						if(distance_to_ship>60)
							continue;
						else
						{
							bullets.remove(b);
							ship.get(0).addKill();
							if(asteroids.get(a).getIteration()==asteroids.get(a).getmaxIteration())
								asteroids.remove(a);
							else asteroids.get(a).setIteration();
						}
					}
				}
			}
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
		if(hit_ships==false&&ship.get(0).getLives()>0)
		{
			Graphics2D g2d=(Graphics2D) g.create();

			Ship s=ship.get(0);
			dimg=s.getImage();
			xco=s.getxco();
			yco=s.getyco();
			
			if(immunity<60)
			{
				if(immunity-1>0)
				{
					immunity--;
//					ship.get(0).setHull(0);
					if(immunity%10==0)
					{
						g2d.rotate(s.getangle(),(int) (xco + dimg.getWidth() / 2), (int) (yco + dimg.getHeight() / 2));
						g2d.drawImage(dimg, (int) xco, (int) yco, dimg.getWidth(), dimg.getHeight(), null);
					}
				}
				else
				{
					immunity=60;
//					ship.get(0).setImage(0);
				}
			}else
			{
				g2d.rotate(s.getangle(),(int) (xco + dimg.getWidth() / 2), (int) (yco + dimg.getHeight() / 2));
				g2d.drawImage(dimg, (int) xco, (int) yco, dimg.getWidth(), dimg.getHeight(), null);
			}
			
			imgx=dimg.getHeight();
			imgy=dimg.getWidth();
		}else if(ship.get(0).getLives()==0)
			{
				drawNoship(g,0);
				dead_ships=true;
			}
		else
			drawExplosion(g,0);
		
		
	}
	//TODO Work on bullets
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
		double xco=ship.get(ship_hit).getxco();
		double yco=ship.get(ship_hit).getyco();
		ship.get(ship_hit).setHull(ship_hit);
		BufferedImage temp=ship.get(ship_hit).getImage();
		
		Graphics2D g4d=(Graphics2D) g.create();
		g4d.rotate(ship.get(ship_hit).getangle(),(int) (xco+imgx/2),(int) (yco+imgy/2));
		g4d.drawImage(temp, (int) xco, (int) yco, temp.getWidth(), temp.getHeight(), null);
	}
	
	public void drawExplosion(Graphics g, int ship_hit)
	{
		double xco=ship.get(ship_hit).getxco();
		double yco=ship.get(ship_hit).getyco();
		Graphics2D g4d=(Graphics2D) g.create();
		g4d.drawImage(explosion, (int) xco, (int) yco, explosion.getWidth(), explosion.getHeight(), null);
		immunity--;
		if(immunity<0)
		{
			immunity=60;
		}

		hit_ships=false;
		ship.get(ship_hit).setOriginalCo();
		ship.get(ship_hit).setVelocity(0);
		
		if(ship.get(ship_hit).getLives()>0)
			ship.get(ship_hit).setLives();
	}
	
	public void drawBanner(Graphics g)
	{
		Graphics2D g2d=(Graphics2D) g.create();
		g2d.setColor(Color.WHITE);
		float offsetx=50;
		
		for(int a=0;a<ship.size();a++)
		{
			g2d.drawString("Player "+(a+1)+": "+ship.get(a).getLives(), offsetx, 20);
			offsetx+=400;
		}
	}
	
	public void drawAsteroids(Graphics g)
	{
		Graphics2D g2d=(Graphics2D) g.create();
		for(int a=0;a<asteroids.size();a++)
		{
			Asteroids b=asteroids.get(a);
			asteroidimage=b.getImage();
			g2d.drawImage(asteroidimage, (int) b.getxco(), (int) b.getyco(), null);
		}
	}
	
	public void setTimeStart()
	{
		timepause=System.nanoTime();
	}
	
	public void nextlevel()
	{
		timepause=System.nanoTime()-timepause           ;
		timepause=Math.round((timepause/(Math.pow(10, 9)))/60);
//		if(timepause>450)
//			nextlevel=true;
	}
	
	public void theend(Graphics g)
	{
		g.drawImage(background, 0, 0, null);
		drawShips(g);
		drawBanner(g);
		if(stagepassed==180)
			stagespassed++;
		ship.get(0).setImage(0);
		BufferedImage MissionCompleted=null;
		Graphics2D g5d=(Graphics2D) g.create();
		try
		{
			MissionCompleted=ImageIO.read(new File("Resources/MissionCompleted.png"));
		}catch (IOException e){
		}
		g5d.drawImage(MissionCompleted, 450, 400, null);
		
		if(stagespassed%2==0&&stagepassed==180)
		{
			ship.get(0).addLife();
		}
		if(stagespassed%5==0)
		{
			g5d.setColor(Color.YELLOW);
			g5d.drawString("Extra Life Obtained", 50, 40);
		}
		g5d.setColor(Color.WHITE);
		g5d.scale(1.5,1.5);
		g5d.drawString("You have triumphed", 400, 300);
		g5d.drawString("Next Stage -- Get Ready", 390, 350);
		if(stagepassed<11)
		{
			g5d.setColor(Color.GREEN);
			g5d.drawString("Stage "+(stagenum+1)+" Starting in "+(stagepassed/10),400,400);
		}else if(stagepassed<51)
		{
			g5d.setColor(Color.RED);
			g5d.drawString("Stage "+(stagenum+1)+" Starting in "+(stagepassed/10),400,400);
		}else
			g5d.drawString("Stage "+(stagenum+1)+" Starting in "+(stagepassed/10),400,400);
		stagepassed--;
		
		if(asteroidcompleted==true&&stagepassed<=0)
		{
//			ship.get(0).setOriginalCo();
			setAsteroids(stagenum++);
			stagenum++;
			asteroidcompleted=false;
			stagepassed=180;
		}
		g5d.setColor(Color.WHITE);
	}
	
	public void failure(Graphics g)
	{
		if(!timed)
		{
			elapsedtime=(System.nanoTime()-elapsedtime)*1000;
			elapsedtime=Math.round((elapsedtime/(Math.pow(10, 9))));
			elapsedtime/=1000;
			timed=true;
		}
		Graphics2D g5d=(Graphics2D) g.create();
		g5d.setColor(Color.WHITE);
		g5d.scale(1.5,1.5);
		g5d.drawString("Player 1 has been destroyed", 380, 300);
		g5d.drawString("Time Survived: "+elapsedtime+" seconds", 380, 340);
		g5d.drawString("You Have Passed "+stagespassed+" stages", 385, 360);
		int x=230,y=360;
		for(int a=0;a<ship.size();a++)
		{
			double kps=0;
			if(ship.get(a).numKills()==0)
			{
				kps=0;
			}else
			{
				kps=(elapsedtime)/ship.get(a).numKills()*10;
				kps=Math.round(kps);
				kps/=10;
			}
			double atpk=0;
			if(ship.get(a).numKills()!=0)
			{
				atpk=(ship.get(a).numKills())/(elapsedtime*60)*10;
				atpk=Math.round(atpk);
				atpk/=10;
			}
			
			g5d.drawString("Player "+(a+1)+" -- Hits: "+ship.get(a).numKills()+"    Deaths: "+ship.get(a).destroyed()+
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
        if(stagepassed>88)
        {
            clearGraphics(g);
        	drawShips(g);
	        drawBullet(g);
	        drawBanner(g);
	    	drawAsteroids(g);
        }
        
        if(life<2&&(asteroidcompleted))
        {
        	theend(g);
        }
        
        if(ship.size()==1&&life<1&&!asteroidcompleted)
        {
        	failure(g);
        }
    }
}
