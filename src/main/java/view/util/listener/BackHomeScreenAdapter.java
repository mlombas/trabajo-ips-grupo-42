package view.util.listener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import view.MainWindow;
import view.atleta.AtletaMain;
import view.organizador.OrganizadorMain;

public class BackHomeScreenAdapter extends WindowAdapter {

	private static BackHomeScreenAdapter bhsa;
	
	private BackHomeScreenAdapter () {
		super();
	}
	
	public static BackHomeScreenAdapter getInstance() {
		if (bhsa == null)
			bhsa = new BackHomeScreenAdapter();
		
		return bhsa;
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		AtletaMain.getInstance().startPanel();
		OrganizadorMain.getInstance().startPanel();
		MainWindow.getInstance().flipCard(MainWindow.MAIN_MENU);
	}
	
}
