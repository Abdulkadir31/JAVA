package pong;
 import brick.*;
import multiplayer_pong.*;
 import  snake.*;
 import tictactoe.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class menu {
	 static mainsp m1=new mainsp();
	  static mainsnake m2=new mainsnake();
	  static mainbrick m3=new mainbrick();
	  static mainmp m4=new mainmp();
	  static tictactoe m5=new tictactoe();
	 

	 public JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
					
					menu window = new menu();
					window.frame.setVisible(true);
					
			
	}

	/**
	 * Create the application.
	 */
	public menu() {
		
		m1.close();
  	    m2.close();
		m3.close();
		m4.close();
		initialize();
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnSnaked = new JButton("Snake 2D");
		btnSnaked.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				m2.mainsnake();
				frame.setVisible(false);
				
			}
		});
		btnSnaked.setBounds(141, 36, 155, 36);
		frame.getContentPane().add(btnSnaked);
		
		JButton btnPong = new JButton("Pong SP");
		btnPong.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				//frame.dispose();
				frame.setVisible(false);
				m1.mainsp();
								
				}
		});

		btnPong.setBounds(141, 183, 155, 36);
		frame.getContentPane().add(btnPong);
		
		JButton btnBrickBall = new JButton("Brick Ball");
		btnBrickBall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				m3.mainbrick();
				frame.setVisible(false);
			}
		});
		btnBrickBall.setBounds(141, 110, 155, 36);
		frame.getContentPane().add(btnBrickBall);
		
		JButton btnPongmp = new JButton("Pong MP");
		btnPongmp.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				//frame.dispose();
				frame.setVisible(false);
				m4.mainmp();
								
				}
		});
		btnPongmp.setBounds(141, 253, 155, 36);
		frame.getContentPane().add(btnPongmp);
		
		
		
		btnBrickBall.setBounds(141, 110, 155, 36);
		frame.getContentPane().add(btnBrickBall);
		
		JButton btntic = new JButton("TIC TAC TOE");
		btntic.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				//frame.dispose();
				frame.setVisible(false);
				m5.tictactoe();
								
				}
		});
		btntic.setBounds(141, 325, 155, 36);
		frame.getContentPane().add(btntic);
	}


}