package view;

import java.awt.EventQueue;

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
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
