//package bingo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class bingoclient implements Runnable {

	private String ip = "localhost";
	private int port = 22222;
	private Scanner scanner = new Scanner(System.in);
	private JFrame frame;
	private final int WIDTH = 506;
	private final int HEIGHT = 527;
	private Thread thread;

	private Painter painter;
	private Socket socket;
	private DataOutputStream dos;
	private DataInputStream dis;

	private ServerSocket serverSocket;
	
	private BufferedImage board;
	private BufferedImage redX;
	private BufferedImage blueX;
	private BufferedImage redCircle;
	private BufferedImage blueCircle;
	private BufferedImage[] numbers=new BufferedImage[9];

	private String[] spaces = new String[9];
	private int[] bspaces={-1,-1,-1,-1,-1,-1,-1,-1,-1};
	private int[]bsspaces= {-1,-1,-1,-1,-1,-1,-1,-1,-1};
	private int[] firstSpot = {-1,-1,-1,-1,-1,-1,-1,-1};
	private int[] secondSpot={-1,-1,-1,-1,-1,-1,-1,-1};


	
	private boolean playerone=false;
	private boolean playertwo=false;
	private boolean yourTurn = false;
	private boolean circle = true;
	private boolean accepted = false;
	private boolean unableToCommunicateWithOpponent =false;
	private boolean won = false;
	private boolean enemyWon = false;
	private boolean tie = false;
	private boolean gameplay=false;

	private int lengthOfSpace = 160;
	private int errors = 0;
	
	private int place=-1;
	private int splace=-1;

	private Font font = new Font("Verdana", Font.BOLD, 32);
	private Font smallerFont = new Font("Verdana", Font.BOLD, 20);
	private Font largerFont = new Font("Verdana", Font.BOLD, 50);

	private String waitingString = "Waiting for another player";
	private String unableToCommunicateWithOpponentString = "Unable to communicate with opponent.";
	private String wonString = "BINGO You won!";
	private String enemyWonString = "Opponent won!";
	private String tieString = "ITS A TIE.";

	private int[][] wins = new int[][] { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, { 0, 4, 8 }, { 2, 4, 6 } };

	/**
	 * <pre>
	 * 0, 1, 2 
	 * 3, 4, 5 
	 * 6, 7, 8
	 * </pre>
	 */

	public bingoclient() {
		System.out.println("Please input the IP: ");
		ip = scanner.nextLine();
		System.out.println("Please input the port: ");
		port = scanner.nextInt();
		while (port < 1 || port > 65535) {
			System.out.println("The port you entered was invalid, please input another port: ");
			port = scanner.nextInt();
		}
		loadImages();

		painter = new Painter();
		painter.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame = new JFrame();
		frame.setTitle("Tic-Tac-Toe");
		frame.setContentPane(painter);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		
		if (!connect()) 
			listenForServerRequest();
		
		
		thread = new Thread(this, "BINGO");
		thread.start();

		
	}

	public void run() {
		while (true) {
			tick();
			painter.repaint();

			

		}
	}

	 void render(Graphics g) {
		g.drawImage(board, 0, 0, null);
		if (unableToCommunicateWithOpponent) {
			g.setColor(Color.RED);
			g.setFont(smallerFont);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			int stringWidth = g2.getFontMetrics().stringWidth(unableToCommunicateWithOpponentString);
			g.drawString(unableToCommunicateWithOpponentString, WIDTH / 2 - stringWidth / 2, HEIGHT / 2);
			return;
		}

		
		
			if (playertwo && accepted) {
				
				for (int i = 0; i < bsspaces.length; i++) {
				
					if (bsspaces[i] != -1 && bsspaces[i]!=50) {
						g.drawImage(numbers[bsspaces[i]], (i % 3) * lengthOfSpace + 10 * (i % 3), (int) (i / 3) * lengthOfSpace + 10 * (int) (i / 3), null);
					}
					else if (bsspaces[i] == 50) {
						g.drawImage(redX, (i % 3) * lengthOfSpace + 10 * (i % 3), (int) (i / 3) * lengthOfSpace + 10 * (int) (i / 3), null);
						
						
					}
				}
			
				for (int i = 0; i < firstSpot.length; i++) {
					
					if(firstSpot[i]!=-1 && secondSpot[i]!=-1)
					{
						Graphics2D g2 = (Graphics2D) g;
						g2.setStroke(new BasicStroke(10));
						g.setColor(Color.RED);
						g.drawLine(firstSpot[i] % 3 * lengthOfSpace + 10 * firstSpot[i] % 3 + lengthOfSpace / 2, (int) (firstSpot[i] / 3) * lengthOfSpace + 10 * (int) (firstSpot[i] / 3) + lengthOfSpace / 2, secondSpot[i] % 3 * lengthOfSpace + 10 * secondSpot[i] % 3 + lengthOfSpace / 2, (int) (secondSpot[i] / 3) * lengthOfSpace + 10 * (int) (secondSpot[i] / 3) + lengthOfSpace / 2);

				}
				
				}
			
			if (won && !tie) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(10));
				
				//g.drawLine(firstSpot % 3 * lengthOfSpace + 10 * firstSpot % 3 + lengthOfSpace / 2, (int) (firstSpot / 3) * lengthOfSpace + 10 * (int) (firstSpot / 3) + lengthOfSpace / 2, secondSpot % 3 * lengthOfSpace + 10 * secondSpot % 3 + lengthOfSpace / 2, (int) (secondSpot / 3) * lengthOfSpace + 10 * (int) (secondSpot / 3) + lengthOfSpace / 2);

				g.setColor(Color.BLUE);
				g.setFont(largerFont);
					int stringWidth = g2.getFontMetrics().stringWidth(wonString);
					g.drawString(wonString, WIDTH / 2 - stringWidth / 2, HEIGHT / 2);
			
					
			}
			if (enemyWon && !tie) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(10));
				
				//g.drawLine(firstSpot % 3 * lengthOfSpace + 10 * firstSpot % 3 + lengthOfSpace / 2, (int) (firstSpot / 3) * lengthOfSpace + 10 * (int) (firstSpot / 3) + lengthOfSpace / 2, secondSpot % 3 * lengthOfSpace + 10 * secondSpot % 3 + lengthOfSpace / 2, (int) (secondSpot / 3) * lengthOfSpace + 10 * (int) (secondSpot / 3) + lengthOfSpace / 2);

				g.setColor(Color.BLUE);
				g.setFont(largerFont);
					int stringWidth = g2.getFontMetrics().stringWidth(enemyWonString);
					g.drawString(enemyWonString, WIDTH / 2 - stringWidth / 2, HEIGHT / 2);
				
					
			}
			if (tie) {
				Graphics2D g2 = (Graphics2D) g;
				g.setColor(Color.BLUE);
				g.setFont(largerFont);
				int stringWidth = g2.getFontMetrics().stringWidth(tieString);
				g.drawString(tieString, WIDTH / 2 - stringWidth / 2, HEIGHT / 2);
			}
			}
		 else if(!accepted) {
			g.setColor(Color.RED);
			g.setFont(font);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			int stringWidth = g2.getFontMetrics().stringWidth(waitingString);
			g.drawString(waitingString, WIDTH / 2 - stringWidth / 2, HEIGHT / 2);
		}

	}

	void tick() {
		if (errors >= 10) unableToCommunicateWithOpponent = true;

		if (!yourTurn && !unableToCommunicateWithOpponent && gameplay) {
			try {
				int space = dis.readInt();
				System.out.println("Data Received");
				
				if (space==150)
				{
					tie=true;
				}
				
				if(space==100) {
					System.out.println("Enemy WON is true");
					enemyWon=true;
					space=dis.readInt();
					}
	
				
				//checkForTie();
				for(int i=0;i<bsspaces.length;i++)
				{
					if(bsspaces[i]==space)bsspaces[i]=50;
						
				}
				if(!tie)checkForWin();
				yourTurn=true;
				
			} catch (IOException e) {
				e.printStackTrace();
				errors++;
			}
		}
	}

	 void checkForWin() {
		 int count=0;
		 for (int i = 0; i < wins.length; i++) {
				
				
					if (bsspaces[wins[i][0]] == 50 && bsspaces[wins[i][1]] == 50 && bsspaces[wins[i][2]] == 50 ) {
						firstSpot[i] = wins[i][0];
						secondSpot[i] = wins[i][2];
						count=count+1;
						System.out.println("count is "+count);
					}
				}
		 if(count>=3)
			{
				won=true;
				if(!enemyWon)
				{
				try {
					dos.writeInt(100);
					dos.flush();
				} catch (IOException e1) {
					errors++;
					e1.printStackTrace();
				}
			}
				else if(enemyWon){
			try {
				tie=true;
				dos.writeInt(150);
				dos.flush();
			} catch (IOException e1) {
				errors++;
				e1.printStackTrace();
			}
			
			}
					
					
				System.out.println("DATA WAS SENT from check from win");
			}
	}

	

	 void checkForTie() {
		for (int i = 0; i < spaces.length; i++) {
			if (spaces[i] == null) {
				return;
			}
		}
		tie = true;
	}
	void listenForServerRequest() {
		Socket socket = null;
		try {
			socket = serverSocket.accept();
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			accepted = true;
			playertwo=true;
			System.out.println("CLIENT HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	 boolean connect() {
		try {
			socket = new Socket(ip, port);
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			accepted = true;
			playertwo=true;
		} catch (IOException e) {
			System.out.println("Connecting to Server");
			return false;
		}
		System.out.println("Successfully connected to the server.");
		
		return true;
	}

	void loadImages() {
		try {
			board = ImageIO.read(getClass().getResourceAsStream("/board.png"));
			redX = ImageIO.read(getClass().getResourceAsStream("/star.png"));
			numbers[0]=ImageIO.read(getClass().getResourceAsStream("/1.png"));
			numbers[1]=ImageIO.read(getClass().getResourceAsStream("/2.png"));
			numbers[2]=ImageIO.read(getClass().getResourceAsStream("/3.png"));
			numbers[3]=ImageIO.read(getClass().getResourceAsStream("/4.png"));
			numbers[4]=ImageIO.read(getClass().getResourceAsStream("/5.png"));
			numbers[5]=ImageIO.read(getClass().getResourceAsStream("/6.png"));
			numbers[6]=ImageIO.read(getClass().getResourceAsStream("/7.png"));
			numbers[7]=ImageIO.read(getClass().getResourceAsStream("/8.png"));
			numbers[8]=ImageIO.read(getClass().getResourceAsStream("/9.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	

	 public static void main(String[] args) {
			bingoclient ticTacToe = new bingoclient();
		}

	@SuppressWarnings("unused")
	

	
	class Painter extends JPanel implements MouseListener {
		private static final long serialVersionUID = 1L;

		public Painter() {
			setFocusable(true);
			requestFocus();
			setBackground(Color.WHITE);
			addMouseListener(this);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			render(g);
		}
	
		@Override
		public void mouseClicked(MouseEvent e) {
			if (accepted && !gameplay && splace<8) {
				
				if (playertwo && !unableToCommunicateWithOpponent && !won && !enemyWon) {
					int x = e.getX() / lengthOfSpace;
					int y = e.getY() / lengthOfSpace;
					y *= 3;
					int position = x + y;
					

					if (bsspaces[position] == -1) {
						System.out.println("In click method of player 2");
						if (playertwo) bsspaces[position] = splace+1;
						
						++splace;
						if(splace==8)
						{
							gameplay=true;
							
						}
					


					}
				}
			}
			
			else if(yourTurn && playertwo && !won &&!enemyWon)
			{	int q=0;
				while(yourTurn)
				{
					
				int x = e.getX() / lengthOfSpace;
				int y = e.getY() / lengthOfSpace;
				y *= 3;
				int position = x + y;
					
					if(bsspaces[position]!=50)
					{
						q=bsspaces[position];
						bsspaces[position]=50;
						yourTurn=false;
					}
				
					}	
				checkForWin();
				//yourTurn=false;
				try {
				dos.writeInt(q);
				dos.flush();
			} catch (IOException e1) {
				errors++;
				e1.printStackTrace();
			}
				
				
			System.out.println("DATA WAS SENT");
			
			//checkForTie();

				repaint();
				
				
				
			}
			
			
			
			
			
			
		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

	}
}

