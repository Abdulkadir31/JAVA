package pong;

	import java.awt.Graphics;

	
	public class aipaddle {

		double y,yvel;
		int player,x;
		
		public aipaddle(int player)
		{
			y=270;
			yvel=0;
			if(player==1)
			{
				x=30;
			}
			else
			{
				x=670;
			}
		}
		
		public int gety()
		{
			return (int)y;
		}
		
	}



