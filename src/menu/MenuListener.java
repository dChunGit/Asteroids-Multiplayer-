package menu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class MenuListener implements KeyListener
{
	MenuComponent mcomponent;
	public MenuListener(MenuComponent panel)
	{
		mcomponent = panel;
	}

	@Override
	public void keyPressed(KeyEvent p) 
	{
		if(p.getKeyCode() == KeyEvent.VK_UP)
		{
			mcomponent.setGraphics("up");
			switchSound();
		}
		if(p.getKeyCode() == KeyEvent.VK_DOWN)
		{
			mcomponent.setGraphics("down");
			switchSound();
		}
		if(p.getKeyCode()==KeyEvent.VK_ENTER)
		{
			mcomponent.choice();
		}
	}

	@Override
	public void keyReleased(KeyEvent r) 
	{
		
	}

	@Override
	public void keyTyped(KeyEvent t) 
	{
	}
	
	public void play()
	{
		try
		{
			InputStream in=new FileInputStream("Resources/Sounds/ir_inter.wav");
			AudioStream audiostream=new AudioStream(in);
			AudioPlayer.player.start(audiostream);
		}catch (Exception e){
		}
	}
	
	public void switchSound()
	{
		try
		{
			InputStream in=new FileInputStream("Resources/Sounds/ir_inter.wav");
			AudioStream audiostream=new AudioStream(in);
			AudioPlayer.player.start(audiostream);
		}catch (IOException e){
		}
	}
}
