package br.com.translate;

import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {	
			public void run() {				
//				MainWindow.getInstance().setVisible(true);
			}
		});
	}

}
