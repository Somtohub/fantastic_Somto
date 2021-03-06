import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class SnakePanel extends JPanel implements Runnable, KeyListener
{

	public static final int WIDTH = 600, HEIGHT = 600;
//graphical properties	
	private Graphics2D graphicLayout;
	private BufferedImage image;
//game Loops	
	private Thread thread;																		
	
	private boolean running;
	
	private long targetTime;
//other game properties
	private final int SIZE = 10;
	private BodyPart head, food;
	private ArrayList<BodyPart> snake;														
	private int score;
	private int level;
	private boolean gameOver;
//movement	
	private int dx,dy;	
	
//user input 	
	private boolean  up, down, right, left, start;


	

	public SnakePanel()
	{	
		setPreferredSize(new Dimension(WIDTH, HEIGHT));										
		setFocusable(true);
		requestFocus();
		addKeyListener(this);
	}
	
	@Override
	public void addNotify()
	{
		super.addNotify();
		thread = new Thread(this);
		thread.start();
	}
	private void setFPS(int fps)
	{
		targetTime = 1000 / fps;
	}

	@Override
	public void run() 
	{
		if(running) return;
		init();
		long startTime;
		long elapsed;
		long wait;
		while(running)
		{
			startTime = System.currentTimeMillis();
			
			update();
			requestRender();
			
			elapsed = System.currentTimeMillis() - startTime;								
			wait = targetTime - elapsed / 1000000;
			if (wait > 0)
			{
				try 
				{
					Thread.sleep(wait);
				}catch(Exception e)
					{
						e.printStackTrace();
					}
			}
		}

	}
	private void init()
	{
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);			
		graphicLayout = image.createGraphics();
		running = true;
		setUplevel();
	}
	
//set snake position on screen
	private void setUplevel() 
	{
		snake = new ArrayList<BodyPart>();
		head = new BodyPart(SIZE);
		head.setPosition(WIDTH / 2, HEIGHT / 2);
		snake.add(head);
		
		for (int i = 1; i < 5; i++)										
		{
			BodyPart body = new BodyPart(SIZE);
			body.setPosition(head.getX() + (i * SIZE), head.getY());
			snake.add(body);
		}
		food = new BodyPart(SIZE);
		setFood();
		score = 0;														
		gameOver = false;
		level = 1;
		dx = dy = 0;
		setFPS(level * 10);
	}
//set Food display location
	public void setFood()
	{
		int x = (int)(Math.random() * (WIDTH - SIZE));
		int y = (int)(Math.random() * (HEIGHT - SIZE));					
		x = x - (x % SIZE);
		y = y - (y % SIZE);
		food.setPosition(x, y);
	}
	
	private void update() 
	{
		//gameOver and restart
		if(gameOver)
		{
			if(start)
			{
				setUplevel();
			}
			
			return;
		}
		/*movement pattern*/
		if (up && dy == 0)
		{
			dy = -SIZE;
			dx = 0;
		}
		if (down && dy == 0)								
		{
			dy = SIZE;
			dx = 0;
		}
		if (left && dx == 0)
		{
			dy = 0;
			dx = -SIZE;
		}
		if (right && dx == 0 && dy != 0)
		{
			dy = 0;
			dx = SIZE;
		}
		if (dx != 0 || dy != 0)
		{
			for (int i = snake.size() - 1; i > 0; i--)
			{
				snake.get(i).setPosition
					(snake.get(i - 1).getX(),
					 snake.get(i - 1).getY());							
			}
			head.move(dx, dy);
		}
		
		for(BodyPart e : snake)
		{
			if(e.isCollision(head))
			{
				gameOver = true;										
				break;
			}
		}
		
		if(food.isCollision(head))
		{
			score++;
			setFood();
			
			BodyPart e = new BodyPart(SIZE);
			e.setPosition(-100, -100);
			snake.add(e);
			
		/*increase level speed*/																
			if(score % 10 == 0)
			{
				level++;
				if(level > 10) level = 10;
					setFPS(level * 10);
			}
		}
		
//border collision
		if(head.getX() >= WIDTH - 10 || head.getX() <= 0)
		{
			gameOver = true;
		}
		if(head.getY() <= 0 || head.getY() >= HEIGHT - 10)
		{
			gameOver = true;
		}
		
	}


	private void requestRender() 
	{
		render(graphicLayout);
		Graphics g = getGraphics();
		g.drawImage(image, 0, 0, null);									
		g.dispose();
	}
	
//set display	
	private void render(Graphics2D graphicLayout) 
	{
		graphicLayout.clearRect(0, 0, WIDTH, HEIGHT);
		/*repaint*/
		graphicLayout.setColor(Color.WHITE);
		for (BodyPart e : snake) 
		{
			e.render(graphicLayout);
		}
		
		graphicLayout.setColor(Color.RED);													
		food.render(graphicLayout);
		if (gameOver)
		{
			graphicLayout.drawString("GameOver!", 300, 300);
		}
		graphicLayout.setColor(Color.WHITE);
		graphicLayout.drawString("Total Score : " + score + "  Level : " + level, 15, 15);
		if (dx == 0 && dy == 0)
		{
			graphicLayout.drawString("Are you Ready?? ", 300, 280);
		}
	}

//key Listeners	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT)								
		{
			left = true;

		}
		if (key == KeyEvent.VK_RIGHT)
		{
			right = true;

		}
		if (key == KeyEvent.VK_UP)		
		{
			up = true;

		}
		if (key == KeyEvent.VK_DOWN)
		{
			down = true;
		}
		if (key == KeyEvent.VK_ENTER)
		{
			start = true;

		}
	}		
	
	@Override
	public void keyReleased(KeyEvent e) 
	{
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT)
		{																
			left = false;
		}
		if (key == KeyEvent.VK_RIGHT)
		{
			right = false;
		}
		if (key == KeyEvent.VK_UP)
		{
			up = false;
		}
		if (key == KeyEvent.VK_DOWN)
		{
			down = false;
		}
		if (key == KeyEvent.VK_ENTER)
		{
			start = false;
		}	
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		
		
	}
}
