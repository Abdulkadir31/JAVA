package pong;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;


public class mainsp extends JFrame {


void close()
{
	this.dispose();
}
public void	mainsp()
	{
	
		gameplay game1 = new gameplay();
		setBounds(10,10,700,600);
		setTitle("PONG");
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(game1);
		
		
	}
	

	}


