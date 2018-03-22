package multiplayer_pong;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;


public class mainmp extends JFrame {


public void close()
{
	this.dispose();
}
public void	mainmp()
	{
	
		gameplaymp game4 = new gameplaymp();
		setBounds(10,10,700,600);
		setTitle("MULTIPLAYER PONG");
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(game4);
		
		
	}
	

	}


