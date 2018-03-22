package snake;

import java.awt.Color;

	import javax.swing.JFrame;

	public class mainsnake extends JFrame {

		public void close()
		{
			this.dispose();
		}
		public void mainsnake() {

			gameplaysnake gameplay =new gameplaysnake();
			
			setBounds(10,10,905,700);
			setBackground(Color.DARK_GRAY );
			setResizable(false);
			setVisible(true);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			add(gameplay);
			
		}

	}


