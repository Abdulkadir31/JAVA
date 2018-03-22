package pong;
import java.awt.Graphics;

public class humanpaddle {

	double y,yvel;
	boolean upacel,downacel;
	int player,x;
	
	public humanpaddle(int player)
	{
		yvel=0;
		y=270;
		if(player==1)
		{
			x=20;
		}
		else
		{
			x=670;
		}
	}
	
	public void setupacel(boolean input)
	{
		upacel=input;
	}
	
	public void setdownacel(boolean input)
	{
		downacel=input;
	}
	
	public void move()
	{
		if(upacel)
		{
			yvel-=2;
		}
		else if(downacel)
		{
			yvel+=2;
		}
		else if(!upacel && !downacel)
		{
			yvel*=0.94;
		}
		if(yvel>=5)
		{
			yvel=5;
		}
		else if(yvel<=-5)
		{
			yvel=-5;
		}
		y+=yvel;
		
		if(y<=4)
		{
			y=4;
		}
		else if(y>=466)
		{
			y=466;
		}
	}
	
	public int gety()
	{
		return (int)y;
	}
	
}
