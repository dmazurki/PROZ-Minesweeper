import javax.swing.SwingUtilities;

import View.View;


public class Main {


		public static void main(String[] args) {
			// TODO Auto-generated method stub
			SwingUtilities.invokeLater(new Runnable(){
				@Override
				public void run()
				{
					View view = new View();
					
				}
			});
		}


	

}
