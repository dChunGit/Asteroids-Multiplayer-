package asteroids;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;


public class Listener implements KeyListener
{
	private HashMap<Ship,String> pressed;
	private String[] direction;
	private ArrayList<Ship> ships;
	private boolean ended=false,paused=false;
	
	public Listener(int shipnum,ArrayList<Ship> shipsinit)
	{
		ships=shipsinit;
		
		pressed=new HashMap<Ship,String>();
		if(ships.size()==1)
		{
			pressed.put(ships.get(0), "false");
		}else
		{
			for(int a=0;a<shipsinit.size();a++)
			{
				pressed.put(shipsinit.get(a), "false");
			}
		}

		direction=new String[shipnum];
		for(int b=0;b<shipsinit.size();b++)
		{
			direction[b]="Down";
		}
	}
	
	public HashMap<Ship,String> getPressed()
	{
		return pressed;
	}
	
	public String[] getDirection()
	{
		return direction;
	}
	
	public boolean getEnded()
	{
		return ended;
	}
	
	public boolean getPaused()
	{
		return paused;
	}
	
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
		{
			ended=true;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE)
		{
			paused=!paused;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_UP)
		{
			pressed.put(ships.get(0), "true");
			direction[0]="Up";
			ships.get(0).setMovement("Up");
		}
		
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			direction[0]="Left";
			ships.get(0).setMovement("Left");
		}
		
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			direction[0]="Right";
			ships.get(0).setMovement("Right");
		}
		
		if(e.getKeyCode()==KeyEvent.VK_DOWN)
		{
			direction[0]="Down";
			ships.get(0).setMovement("Down");
		}
		
		if(e.getKeyCode()==KeyEvent.VK_SPACE)
		{
		}
		
		
		if(ships.size()>1)
		{
			if(e.getKeyCode()==KeyEvent.VK_W)
			{
				pressed.put(ships.get(1), "true");
				direction[1]="Up";
				ships.get(1).setMovement("Up");
			}
			
			if(e.getKeyCode()==KeyEvent.VK_A)
			{
				direction[1]="Left";
				ships.get(1).setMovement("Left");
			}
			
			if(e.getKeyCode()==KeyEvent.VK_D)
			{
				direction[1]="Right";
				ships.get(1).setMovement("Right");
			}
			
			if(e.getKeyCode()==KeyEvent.VK_S)
			{
				direction[1]="Down";
				ships.get(1).setMovement("Down");
			}
			
			if(e.getKeyCode()==KeyEvent.VK_Q)
			{
			}
		}
		
		if(ships.size()>2)
		{
			if(e.getKeyCode()==KeyEvent.VK_NUMPAD8)
			{
				pressed.put(ships.get(2), "true");
				direction[2]="Up";
				ships.get(2).setMovement("Up");
			}
			
			if(e.getKeyCode()==KeyEvent.VK_NUMPAD4)
			{
				direction[2]="Left";
				ships.get(2).setMovement("Left");
			}
			
			if(e.getKeyCode()==KeyEvent.VK_NUMPAD6)
			{
				direction[2]="Right";
				ships.get(2).setMovement("Right");
			}
			
			if(e.getKeyCode()==KeyEvent.VK_NUMPAD5)
			{
				direction[2]="Down";
				ships.get(2).setMovement("Down");
			}
			
			if(e.getKeyCode()==KeyEvent.VK_NUMPAD0)
			{
			}
		}
		
		if(ships.size()>3)
		{
			if(e.getKeyCode()==KeyEvent.VK_I)
			{
				pressed.put(ships.get(3), "true");
				direction[3]="Up";
				ships.get(3).setMovement("Up");
			}
			
			if(e.getKeyCode()==KeyEvent.VK_J)
			{
				direction[3]="Left";
				ships.get(3).setMovement("Left");
			}
			
			if(e.getKeyCode()==KeyEvent.VK_L)
			{
				direction[3]="Right";
				ships.get(3).setMovement("Right");
			}
			
			if(e.getKeyCode()==KeyEvent.VK_K)
			{
				direction[3]="Down";
				ships.get(3).setMovement("Down");
			}
			
			if(e.getKeyCode()==KeyEvent.VK_U)
			{
			}
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode()==KeyEvent.VK_UP)
		{
			pressed.put(ships.get(0), "false");
		}
		
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			direction[0]="Down";
		}
		
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			direction[0]="Down";
		}
		
		if(e.getKeyCode()==KeyEvent.VK_DOWN)
		{
		}
		
		if(e.getKeyCode()==KeyEvent.VK_SPACE)
		{
		}
		
		if(ships.size()>1)
		{
			if(e.getKeyCode()==KeyEvent.VK_W)
			{
				pressed.put(ships.get(1), "false");
			}
			
			if(e.getKeyCode()==KeyEvent.VK_A)
			{
				direction[1]="Down";
			}
			
			if(e.getKeyCode()==KeyEvent.VK_D)
			{
				direction[1]="Down";
			}
			
			if(e.getKeyCode()==KeyEvent.VK_S)
			{
			}
			
			if(e.getKeyCode()==KeyEvent.VK_Q)
			{
			}
		}
		
		if(ships.size()>2)
		{
			if(e.getKeyCode()==KeyEvent.VK_NUMPAD8)
			{
				pressed.put(ships.get(2), "false");
			}
			
			if(e.getKeyCode()==KeyEvent.VK_NUMPAD4)
			{
				direction[2]="Down";
			}
			
			if(e.getKeyCode()==KeyEvent.VK_NUMPAD6)
			{
				direction[2]="Down";
			}
			
			if(e.getKeyCode()==KeyEvent.VK_NUMPAD5)
			{
			}
			
			if(e.getKeyCode()==KeyEvent.VK_NUMPAD0)
			{
			}
		}
		
		if(ships.size()>3)
		{
			if(e.getKeyCode()==KeyEvent.VK_I)
			{
				pressed.put(ships.get(3), "false");
			}
			
			if(e.getKeyCode()==KeyEvent.VK_J)
			{
				direction[3]="Down";
			}
			
			if(e.getKeyCode()==KeyEvent.VK_L)
			{
				direction[3]="Down";
			}
			
			if(e.getKeyCode()==KeyEvent.VK_K)
			{
			}
			
			if(e.getKeyCode()==KeyEvent.VK_U)
			{
			}
		}
	}
	
	public void keyTyped(KeyEvent e)
	{
	}
}
