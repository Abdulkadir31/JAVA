package brick;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import pong.menu;

public class gameplaybrick extends JPanel implements KeyListener,ActionListener{

	private boolean play= false;
	private boolean closewindow=false;
	private boolean endgame=false;
	menu m;
	
	private int score = 0;
	
	private int totalbricks = 21;
	
	private Timer timer;
	private int delay = 8;
	
	private int playerx= 310;
	
	private int ballposx = 120;
	private int ballposy = 350;
	private double ballxdir = randomspeed() * randomdirection();
	private double ballydir = randomspeed() * randomdirection();
	
	private mapgenerator map;
	
	public gameplaybrick()
	{
		map= new mapgenerator(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay,this);
		timer.start();
		
	}
	
	public void paint(Graphics g)
	{
		//background
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		// borders
		g.setColor(Color.YELLOW);
		g.fillRect(0,0,3,592);
		g.fillRect(0,0,692,3);
		g.fillRect(691,0,3,592);
		
		//paddle
		g.setColor(Color.GREEN);
		g.fillRect(playerx, 550, 100, 8);

		//score
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD,25));
		g.drawString(""+score, 590, 30);
		
		if(totalbricks<=0) {
			endgame=true;
			play=false;
			ballxdir = 0;
			ballydir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("YOU WON", 250, 250);
			
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Press ENTER to Restart", 220, 350);
			
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Press ESC to Quit", 240, 370);

		}
		
		
		if(ballposy>570)
		{
			endgame=true;
			play=false;
			ballxdir = 0;
			ballydir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("Game Over, Score:"+score, 190, 300);
			
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Press ENTER to Restart", 220, 350);
			
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Press ESC to Quit", 240, 370);
			
			
			
		}
		
		
		//ball
		g.setColor(Color.YELLOW);
		g.fillOval((int)ballposx,(int)ballposy, 20, 20);

		//draw
		map.draw((Graphics2D)g);
		
		g.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play)
		{
			if(new Rectangle(ballposx,ballposy,20,20).intersects(new Rectangle(playerx,550,100,8)))
			{
				ballydir=-ballydir;
			}
			
			A:for(int i=0;i<map.map.length;i++)
			{
				for(int j=0;j<map.map[0].length;j++)
				{
					if(map.map[i][j]>0)
					{
						int brickx=j*map.brickwidth +80;
						int bricky =i*map.brickheight +50;
						int brickwidth=map.brickwidth;
						int brickheight=map.brickheight;
						
						Rectangle rect=new Rectangle(brickx,bricky,brickwidth,brickheight);
						Rectangle ballrect=new Rectangle(ballposx,ballposy,20,20);
						Rectangle brickrect=rect;
						
						if(ballrect.intersects(brickrect))
						{
							map.setbrickvalue(0, i, j);
							totalbricks--;
							score += 5;
							
							if(ballposx + 19 <= brickrect.x || ballposx + 1>=brickrect.x + brickrect.width)
							{
								ballxdir=-ballxdir;
							}
							else
							{
								ballydir=-ballydir;
							}
						
							break A;
						}
					}
				}
			}
			
			
			
			ballposx+=ballxdir;
			ballposy+=ballydir;
			if(ballposx<0)
			{
				ballxdir=-ballxdir;
			}
			if(ballposy<0)
			{
				ballydir=-ballydir;
			}
			if(ballposx>670)
			{
				ballxdir=-ballxdir;
			}
			repaint();
		}
		
		if(closewindow)
		{
		
			
			endgame=false;
			closewindow=false;
			m =new menu();
			m.frame.setVisible(true);
		}

		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(endgame)
		{
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				endgame=false;
				ballposx = 120;
				ballposy =350;
				ballxdir = randomspeed() * randomdirection();
				ballydir = randomspeed() * randomdirection();
				playerx = 310;
				score = 0;
				totalbricks=21;
				map =new mapgenerator(3,7);
						repaint();
			}
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
		//play=true;
			closewindow=true;
			ballposx = 120;
			ballposy =350;
			ballxdir = randomspeed() * randomdirection();
			ballydir = randomspeed() * randomdirection();
			playerx = 310;
			score = 0;
			totalbricks=21;
			map =new mapgenerator(3,7);
					repaint();
		}
			
		}
		
		
		
		
		if(!endgame)
		{
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			if(playerx>=600)
			{
				playerx=600;
			}
			else
			{
				moveRight();
			}
		}
		
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			if(playerx<=10)
			{
				playerx=10;
			}
			else
			{
				moveLeft();
			}
			
		}
	}
	}

	public void moveRight()
	{
		play=true;
		playerx+=20;
	}
	
	public void moveLeft()
	{
		play=true;
		playerx-=20;
	}
	 
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public double randomspeed() {
		double y=(Math.random()*1 +1);
		if(y<1)
		{
			y=1;
			return y;
		}
		else return y;
		
	}
	
	public int randomdirection() {
		int rand = (int)(Math.random()*2);
		if (rand==1)
			return -2;
		else
			return -1;
	}
	
	public int getballposy()
	{
		return (int)ballposy;
	}
	
	public int getballposx()
	{
		return (int)ballposx;
	}
	
	

}
