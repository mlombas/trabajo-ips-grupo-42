package view.util.listener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import view.MainWindow;

public class DefaultClosingAdapter extends WindowAdapter {

	private static DefaultClosingAdapter dca;
	
	private DefaultClosingAdapter () {
		super();
	}
	
	public static DefaultClosingAdapter getInstance() {
		if (dca == null)
			dca = new DefaultClosingAdapter();
		
		return dca;
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		if(JOptionPane.showConfirmDialog(null,
				MainWindow.EXIT_DIALOG, 
				MainWindow.TITLE, 
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
	
}
