package menu;

import java.awt.*; 

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import asteroids.*;

@SuppressWarnings("serial")
public class MenuComponent extends JPanel 
{
	BufferedImage image,quit;
	Graphics G;
	ArrayList<BufferedImage> options = new ArrayList<BufferedImage>();
	JFrame frame;
	GameLoop1P one;
	GameLoop multi;
	boolean quitchoice=false;
	int x = 0;
	float elapsedtime, timekeeper;
	
	public MenuComponent(BufferedImage image) 
	{
		this.image = image;
		
		try
		{
			quit=ImageIO.read(new File("Resources/options/TempQuit.png"));
		}
		catch(IOException e)
		{
			System.out.println(e.toString());
		}
	}
	
	public MenuComponent(BufferedImage[] images,JFrame mainframe)
	{
		for(int a=0;a<images.length;a++)
		{
			options.add(images[a]);
		}
		frame=mainframe;
		
		try
		{
			quit=ImageIO.read(new File("Resources/options/TempQuit.png"));
		}
		catch(IOException e)
		{
			System.out.println(e.toString());
		}
	}
	
	public void setGraphics(String direction)
	{
		if(direction.equalsIgnoreCase("up"))
		{
			if(x > 0)
				x--;
			else
				x = options.size() - 1;
		}
		if(direction.equalsIgnoreCase("down"))
		{
			if(x < options.size() - 1)
				x++;
			else
				x = 0;
		}
		paintComponent(G);
		repaint();
	}
	
	public void choice()
	{
		switch (x)
		{
		case 0:  {
			one=new GameLoop1P();
			frame.setVisible(false);
			frame.dispose();
		}break;
		case 1:	 {
			multi=new GameLoop(4);
			frame.setVisible(false);
			frame.dispose();
		}break;
		case 3:  {
			timekeeper=0;
			elapsedtime=System.currentTimeMillis()/1000000000;
			quitchoice=true;
			repaint();
		}
		}
	}
	
	public void returntomenu()
	{
		frame.setVisible(true);
		one.destroythis(frame);
	}
			
	private void clearGraphics(Graphics g)
	{
		g.setColor(Color.black);
		g.drawRect(0, 0, getWidth(), getHeight());
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		G = g;
		super.paintComponent(g);
        clearGraphics(g);
        Graphics2D g2d=(Graphics2D) g.create();
        if(options.size() == 0)
        {
        	g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        }else if(quitchoice!=true)
        	g2d.drawImage(options.get(x), 0, 0, options.get(x).getWidth(), options.get(x).getHeight(), null);
        else if(quitchoice==true&&timekeeper<200000)
        {
        	g2d.drawImage(quit,
        			0, 0, quit.getWidth(), quit.getHeight(), null);
        	elapsedtime=(System.currentTimeMillis()/1000000000)-elapsedtime;
        	timekeeper+=elapsedtime;
        	repaint();
        }else if(quitchoice==true&&timekeeper>200000)
        {
        	frame.setVisible(false);
        	frame.dispose();
        }
        	
	}
}
