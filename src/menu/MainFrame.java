package menu;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainFrame 
{
	ArrayList<MenuComponent> MenuComp = new ArrayList<MenuComponent>();

	public static void main(String arg[])
	{
		MainFrame mainmenu=new MainFrame();
	}
	
	public MainFrame()
	{
		JFrame mainframe = new JFrame();
		mainframe.setLayout(new BorderLayout());
		BufferedImage comp = null;
		
		try
		{
			comp=ImageIO.read(new File("Resources/Title.png"));
			MenuComponent title = new MenuComponent(comp);
			title.setPreferredSize(new Dimension(comp.getWidth(), comp.getHeight()));
			title.setBackground(Color.black);
			mainframe.add(title, BorderLayout.PAGE_START);
		}
		catch (IOException e)
		{
			System.out.print(e.toString());
		}
		
		try
		{
			comp=ImageIO.read(new File("Resources/cw.png"));
			MenuComponent copywright = new MenuComponent(comp);
			copywright.setPreferredSize(new Dimension(comp.getWidth(), comp.getHeight()));
			mainframe.add(copywright, BorderLayout.PAGE_END);
		}
		catch (IOException e)
		{
			System.out.print(e.toString());
		}
		
		JPanel buffer = new JPanel();
		buffer.setPreferredSize(new Dimension(490, 550));
		buffer.setBackground(Color.black);
		JPanel buffer1 = new JPanel();
		buffer1.setPreferredSize(new Dimension(495, 550));
		buffer1.setBackground(Color.black);
		mainframe.add(buffer, BorderLayout.LINE_START);
		mainframe.add(buffer1, BorderLayout.LINE_END);
		
		try
		{
			BufferedImage[] menuOptions = 
			{
					ImageIO.read(new File("Resources/options/Option1.png")), ImageIO.read(new File("Resources/options/Option2.png")),
					ImageIO.read(new File("Resources/options/Option3.png")), ImageIO.read(new File("Resources/options/Option4.png"))
			};
			MenuComponent mainscreen = new MenuComponent(menuOptions,mainframe);
			mainscreen.setLocation(400, 150);
			mainscreen.setPreferredSize(new Dimension(comp.getWidth(), comp.getHeight()));
			mainframe.add(mainscreen, BorderLayout.CENTER);
			MenuListener listener = new MenuListener(mainscreen);
			mainframe.addKeyListener(listener);
		}
		catch (IOException e)
		{
			System.out.print(e.toString());
		}
		
		
		
		mainframe.pack();
		mainframe.setTitle("ASTEROIDS");
		mainframe.setSize(1400, 850);
		mainframe.setFocusable(true);
		mainframe.setVisible(true);
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
