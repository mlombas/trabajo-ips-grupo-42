package view;

import java.awt.*;
import javax.swing.UIManager;

import util.database.Database;

public class Main {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// We create the DB
		Database.getInstance();
		
		// We run the app
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = MainWindow.getInstance();
					
					// We establish the look and feel
			         try{
			             UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
			         } catch(Exception e){
			             System.out.println(e);
			         }
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
