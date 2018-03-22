package pong;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


class gameplay extends JPanel implements KeyListener,ActionListener {

	private boolean play= false;
	private boolean endgame = false;
	private boolean closewindow=false;
	//private int score = 0;
	
	static menu m;
	
	private Timer timer;
	private int delay = 8;
	
	private int playerx= 30;
	
	private double ballposx = 340;
	public double ballposy = 290;
	private double ballxdir = randomspeed() * randomdirection();
	private double ballydir = randomspeed() * randomdirection();

	humanpaddle p1;
	aipaddle p2;
	
	public gameplay()
	{
		endgame=false;
		closewindow=false;
		p1=new humanpaddle(1);
		p2=new aipaddle(2);
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
		g.fillRect(0,569,692,3);

		//paddle
		g.setColor(Color.GREEN);
		g.fillRect(p1.x, (int)p1.y, 8, 100);
		g.fillRect(p2.x, (int)p2.y, 8, 100);
		
		g.setColor(Color.YELLOW);
		g.fillOval((int)ballposx, (int)ballposy, 20, 20);
		
		if(ballposx<=-20) 
		{
			play=false;
			endgame=true;
			g.setColor(Color.RED);
			
			g.setFont(new Font("arial", Font.BOLD, 20));
			g.drawString("	AI WINS", 300,290);
			g.drawString("PRESS ENTER TO CONTINUE", 200,320);
			g.drawString("ESC TO  QUIT	",270,350);
		}
		
		else if(ballposx >=689)
		{
			play=false;
			endgame=true;
			g.setColor(Color.RED);
			g.setFont(new Font("arial", Font.BOLD, 20));
			g.drawString("PLAYER 1 WINS", 270,290);
			g.drawString("Press ENTER to continue", 270,320);
			g.drawString("ESC TO  QUIT	",270,350);
		}
		 
		
		g.dispose();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play && !endgame)
		{
			p1.move();
			move();
			
			ballposx+=ballxdir;
			ballposy+=ballydir;
			if(ballposy<3)
			{
				ballydir=-ballydir;
			}
			if(ballposy>557)
			{
				ballydir=-ballydir;
			}
			
			//collision
			if(ballposx<=28)
			{
				if(ballposy>=p1.gety() && ballposy<=p1.gety()+100)
				{
					ballxdir=-ballxdir;
				}
			}
			
			else if(ballposx>=650)
			{
				if(ballposy>=p2.gety() && ballposy<=p2.gety()+100)
				{
					ballxdir=-ballxdir;
				}
			}
			
			
			repaint();	
		}
	
		else if(closewindow==true)
		{
		
			
			endgame=false;
			closewindow=false;
			m =new menu();
			m.frame.setVisible(true);
		}
		
		
	
	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(endgame==false);
		{
		if(e.getKeyCode()==KeyEvent.VK_UP)
		{
			play=true;
			p1.setupacel(true);
		
		}
		
		if(e.getKeyCode()==KeyEvent.VK_DOWN)
		{
			play=true;
			p1.setdownacel(true);
			
		}
		}
		
		if(endgame==true)
		{
			if(e.getKeyCode()==KeyEvent.VK_ENTER)
			{
				//play=true;
				endgame=false;
				ballposx = 340;
				ballposy = 290;
				p1.y=270;
				p2.y=270;
				ballxdir = randomspeed() * randomdirection();
				ballydir = randomspeed() * randomdirection();
				
				
				repaint();
			}
				
			if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
				{
				ballposx = 340;
				ballposy = 290;
				p1.y=270;
				p2.y=270;
				ballxdir = randomspeed() * randomdirection();
				ballydir = randomspeed() * randomdirection();
				 closewindow=true;
				// play=false;
				}
			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_UP)
		{
			p1.setupacel(false);
		}
		
		if(e.getKeyCode()==KeyEvent.VK_DOWN)
		{
			p1.setdownacel(false);
		}
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public int getballposy()
	{
		return (int)ballposy;
	}
	
	public int getballposx()
	{
		return (int)ballposx;
	}
	
	public void move()
	{
		p2.y=ballposy-40;
		
		if(p2.y<=4)
		{
			p2.y=4;
		}
		else if(p2.y>=466)
		{
			p2.y=466;
		}
	}
	
	public double randomspeed() {
		return(Math.random()*3 +2);
	}
	
	public int randomdirection() {
		int rand = (int)(Math.random()*2);
		if (rand==1)
			return 1;
		else
			return -1;
	}
	
	

}



