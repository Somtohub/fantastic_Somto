# fantastic_Somto
# This is my first commit

import java.awt.Dimension;

import javax.swing.JFrame;

public class Main 
{
	
	public static void main(String[] args) 
	{

		JFrame myFrame = new JFrame();
		
		SnakePanel snakePanel = new SnakePanel();
		myFrame.add(snakePanel);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
// Set JFrame attributes

		myFrame.setTitle("SOMTO'S SNAKE");
		myFrame.setResizable(false);
		myFrame.pack();
		
		myFrame.setPreferredSize(new Dimension(SnakePanel.WIDTH, SnakePanel.HEIGHT));
		myFrame.setLocationRelativeTo(null);
		myFrame.setVisible(true);
		
	}
	
}
