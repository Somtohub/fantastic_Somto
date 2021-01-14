import java.awt.Graphics2D;
import java.awt.Rectangle;

public class BodyPart 
{
	private int xCoordinate, yCoordinate, size;
	public BodyPart(int size)
	{
		this.size = size;
	}
	
	public void setPosition(int x, int y)
	{
		xCoordinate = x;
		yCoordinate = y;
	}
	
	public void move (int dx, int dy)
	{
		xCoordinate += dx;
		yCoordinate += dy;
	}
	
	public Rectangle getBound()
	{
		return new Rectangle (xCoordinate, yCoordinate, size, size);
	}
	
	public boolean isCollision(BodyPart o)
	{
		if (o == this)
		{
			return false;
		}
		return getBound().intersects(o.getBound());
	}
	
	public void render (Graphics2D g2d)
	{
		g2d.fillRect(xCoordinate, yCoordinate, size - 1, size - 1);
	}
	
	public int getX()
	{
		return xCoordinate;
	}
	
	public int getY()
	{
		return yCoordinate;
	}
	
	public void setX(int x)
	{
		xCoordinate = x;
	}
	
	public void setY(int y)
	{
		yCoordinate = y;
	}	
}
