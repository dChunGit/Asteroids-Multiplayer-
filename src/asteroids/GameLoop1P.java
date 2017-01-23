package asteroids;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;

import menu.MainFrame;

public class GameLoop1P implements ActionListener
{
	private ShipDraw1P panel;
	private static Listener key;
	private static BulletListener bkey;
	private HashMap<Ship,String> pressed;
	private HashMap<Ship,String> bpress;
	private String[] direction;
	private ArrayList<Bullets> bullets;
	private Set<Bullets> bullets1;
	private boolean running=true;
	private boolean paused=false;
	private int fps=60;
	private int framecount=0, numships=0,stagenum=1;

	private JButton startbutton=new JButton("Start");
	private JButton pausebutton=new JButton("Pause");
	private JFrame frame=new JFrame();
	
	public GameLoop1P()
	{
		panel=new ShipDraw1P();
		
		int screen_width = 1400;
		int screen_height = 850;
		
		numships=1;
		panel.addShip();
		
		panel.setFocusable(true);
		
		key=new Listener(numships,panel.getShips());
		bkey=new BulletListener(numships,panel.getShips());
		panel.addKeyListener(key);
		panel.addKeyListener(bkey);
		pressed=key.getPressed();
		direction=key.getDirection();
		bullets1=bkey.getBullets1();
		
		bullets=bkey.getBullets();
		bpress=bkey.getbpress();
		
		frame.setTitle("Asteroids 1v1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add("Center",panel);
		frame.setSize(screen_width, screen_height);
		frame.setVisible(true);
		
		panel.setAsteroids(stagenum);
		runGameLoop();
	}
	
	public void destroythis(JFrame frame)
	{
		frame.setVisible(false);
		frame.dispose();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		Object s=e.getSource();
		if(s==startbutton)
		{
			running=!running;
			if(running)
			{
				startbutton.setText("Stop");
//				runGameLoop(numships);
			}else
			{
				startbutton.setText("Start");
			}
		}
		if(s==pausebutton)
		{
			paused=!paused;
			if(paused)
			{
				pausebutton.setText("Unpause");
			}else
			{
				pausebutton.setText("Pause");
			}
		}
	}
	
	public void runGameLoop()
	{
		Thread loop=new Thread()
		{
			public void run()
			{
				gameLoop();
			}
		};
		loop.start();
	}
	
	private void gameLoop()
	{
		final double gamehertz=30.0;
		final double timebetweenupdates=1000000000/gamehertz;
		final int maxupdatesbeforerender=5;
		double lastupdatetime=System.nanoTime();
		double lastrendertime=System.nanoTime();
		
		final double targetfps=60;
		final double targettimebetweenrender=1000000000/targetfps;
		
		int lastsecondtime=(int)(lastupdatetime/1000000000);
		
		while(running)
		{
			double now=System.nanoTime();
			int updatecount=0;
			
			if(!paused)
			{
				while(now-lastupdatetime>timebetweenupdates && updatecount<maxupdatesbeforerender)
				{
					updateGame();
					lastupdatetime+=timebetweenupdates;
					updatecount++;
				}
				
				if(now-lastupdatetime>timebetweenupdates)
				{
					lastupdatetime=now-timebetweenupdates;
				}
				
				float interpolation=Math.min(1.0f,(float)((now-lastupdatetime)/timebetweenupdates));
				drawGame(interpolation);
				lastrendertime=now;
				
				int thissecond=(int) (lastupdatetime/1000000000);
				if(thissecond>lastsecondtime)
				{
					fps=framecount;
					System.out.println("FPS: "+fps);
					framecount=0;
					lastsecondtime=thissecond;
				}
				
				while(now-lastrendertime<targettimebetweenrender&&now-lastupdatetime<timebetweenupdates)
				{
					Thread.yield();
					try
					{
						Thread.sleep(1);
					}catch(Exception e){
					}
					
					now=System.nanoTime();
				}
			}
			pauseGame();
		}
	}
	
	public void pauseGame()
	{
		paused=key.getPaused();
	}
	
	
	private void drawGame(float interpolation)
	{
		panel.setInterpolation(interpolation);
		
		//TODO: bullets3 and 4
		panel.update(pressed, direction);
		panel.updatebullets(bullets1,bullets,bpress);
		panel.updateAsteroids();
		panel.repaint();
		framecount++;
	}
	
	private void updateGame()
	{
		pressed=key.getPressed();
		direction=key.getDirection();
		bullets1=bkey.getBullets1();
		bullets=bkey.getBullets();
		bpress=bkey.getbpress();
		running=!key.getEnded();
		if(!running)
		{
			endGame();
		}
	}
	
	public void endGame()
	{
		@SuppressWarnings("unused")
		MainFrame mainmenu=new MainFrame();
		frame.setVisible(false);
		frame.dispose();
		frame.setFocusable(false);
	}
}
