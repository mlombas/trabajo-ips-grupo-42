package view.organizador.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import util.exceptions.ModelException;
import view.organizador.OrganizadorMain;

public class AtrasOrganizadorButton extends JButton {

	private static final long serialVersionUID = 1L;
	
	public AtrasOrganizadorButton(String back) {
		this.setText("Atrás");
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					OrganizadorMain.getInstance().flipCard(back);
				} catch (ModelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	
}
