package br.com.translate;

import javax.swing.SwingUtilities;

import br.com.translate.view.MainWindow;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {	
			public void run() {				
				MainWindow.getInstance().setVisible(true);
			}
		});
	}

}
