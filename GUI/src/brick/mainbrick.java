package brick;
import javax.swing.JFrame;


public class mainbrick extends JFrame {

	public void close()
	{
		this.dispose();
	}
	
	public	void mainbrick()
	{
		JFrame obj = new JFrame();
		gameplaybrick game = new gameplaybrick();
		setBounds(10,10,700,600);
		setTitle("Brick Ball");
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(game);
		
	}
	

}
