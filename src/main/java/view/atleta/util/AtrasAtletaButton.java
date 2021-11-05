package view.atleta.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import util.exceptions.ModelException;
import view.atleta.AtletaMain;

public class AtrasAtletaButton extends JButton {

	private static final long serialVersionUID = 1L;
	
	public AtrasAtletaButton(String back) {
		this.setText("Atrás");
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AtletaMain.getInstance().flipCard(back);
				} catch (ModelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	
}
