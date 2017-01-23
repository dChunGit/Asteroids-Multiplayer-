package asteroids;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;


public class BulletListener implements KeyListener
{
	private HashMap<Ship,String> bpress;
	private ArrayList<Ship> ships;
	private ArrayList<Bullets> bullets;
	private Set<Bullets> bullets1,bullets2,bullets3,bullets4;
	
	public BulletListener(int shipnum,ArrayList<Ship> shipsinit)
	{
		
		ships=shipsinit;
		
		bpress=new HashMap<Ship,String>();
		
		
		for(int b=0;b<shipsinit.size();b++)
		{
			bpress.put(shipsinit.get(b), "false");
		}
		
		bullets=new ArrayList<Bullets>();
		bullets1=new HashSet<Bullets>();
		if(shipnum>1)
		{
			bullets2=new HashSet<Bullets>();
		}
		if(shipnum>2)
		{
			bullets3=new HashSet<Bullets>();
		}
		if(shipnum>3)
		{
			bullets4=new HashSet<Bullets>();
		}
	}
	
	public Set<Bullets> getBullets1()
	{
		return bullets1;
	}
	
	public Set<Bullets> getBullets2()
	{
		return bullets2;
	}
	
	public Set<Bullets> getBullets3()
	{
		return bullets3;
	}
	
	public Set<Bullets> getBullets4()
	{
		return bullets4;
	}
	
	public ArrayList<Bullets> getBullets()
	{
		return bullets;
	}
	
	public HashMap<Ship,String> getbpress()
	{
		return bpress;
	}
	
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode()==KeyEvent.VK_SPACE)
		{
			if(bullets.size()<(5*ships.size())&&ships.get(0).getLives()>0)
			{
				Bullets b=new Bullets(ships.get(0).getxco(),ships.get(0).getyco(),ships.get(0).getangle(),0);
				bullets1.add(b);
				bullets.add(b);
				bpress.put(ships.get(0), "true");
				play();
			}
		}
		
		
		if(ships.size()>1)
		{
			if(e.getKeyCode()==KeyEvent.VK_Q)
			{
				if(bullets.size()<(5*ships.size())&&ships.get(1).getLives()>0)
				{
					Bullets b=new Bullets(ships.get(1).getxco(),ships.get(1).getyco(),ships.get(1).getangle(),1);
					bullets2.add(b);
					bullets.add(b);
					bpress.put(ships.get(1), "true");
					play();
				}
			}
		}
		
		if(ships.size()>2)
		{
			if(e.getKeyCode()==KeyEvent.VK_NUMPAD0)
			{
				if(bullets.size()<(5*ships.size())&&ships.get(2).getLives()>0)
				{
					Bullets b=new Bullets(ships.get(2).getxco(),ships.get(2).getyco(),ships.get(2).getangle(),2);
					bullets2.add(b);
					bullets.add(b);
					bpress.put(ships.get(2), "true");
					play();
				}
			}
		}
		
		if(ships.size()>3)
		{
			if(e.getKeyCode()==KeyEvent.VK_U)
			{
				if(bullets.size()<(5*ships.size())&&ships.get(3).getLives()>0)
				{
					Bullets b=new Bullets(ships.get(3).getxco(),ships.get(3).getyco(),ships.get(3).getangle(),3);
					bullets2.add(b);
					bullets.add(b);
					bpress.put(ships.get(3), "true");
					play();
				}
			}
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode()==KeyEvent.VK_SPACE)
		{
			bpress.put(ships.get(0), "false");
		}
		
		if(ships.size()>1)
		{
			if(e.getKeyCode()==KeyEvent.VK_Q)
			{
				bpress.put(ships.get(1), "false");
			}
		}
		
		if(ships.size()>2)
		{
			if(e.getKeyCode()==KeyEvent.VK_NUMPAD0)
			{
				bpress.put(ships.get(2), "false");
			}
		}
		
		if(ships.size()>3)
		{
			if(e.getKeyCode()==KeyEvent.VK_U)
			{
				bpress.put(ships.get(3), "false");
			}
		}
	}
	
	public void keyTyped(KeyEvent e)
	{
		
	}
	
	public void play()
	{
		try
		{
			InputStream in=new FileInputStream("Resources/Sounds/shotsound.wav");
			AudioStream audiostream=new AudioStream(in);
			AudioPlayer.player.start(audiostream);
		}catch (Exception e){
		}
	}
}
