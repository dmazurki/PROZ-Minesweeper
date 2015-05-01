import javax.swing.SwingUtilities;

import Model.Model;
import View.View;


public class Main {


		public static void main(String[] args) {
			// TODO Auto-generated method stub
			SwingUtilities.invokeLater(new Runnable(){
				@Override
				public void run()
				{
					Model model = new Model();
					View view = new View(model.getDataPack());
					
					
				}
			});
		}


	

}
