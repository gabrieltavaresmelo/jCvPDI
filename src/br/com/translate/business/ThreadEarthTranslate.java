package br.com.translate.business;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Random;

/**
 * Thread responsavel por fazer a Terra girar em 
 * torno do Sol.
 * 
 * @author Gabriel Tavares
 *
 */
public class ThreadEarthTranslate implements Runnable{

	private boolean isRunning = false;
	
	private int index;
	private Random rand = new Random();
	private Rectangle r = new Rectangle();
	private int wImgEarth;
	private int hImgEarth;
	private int wPanel;
	private int hPanel;
	private int RADIUS;

	private ListenerEarthTranslate listener = null;

	public ThreadEarthTranslate(ListenerEarthTranslate listener, Dimension dimension, Dimension dimension2) {
		this.listener  = listener;
		
		wImgEarth = dimension.width;
	    hImgEarth = dimension.height;
	    
	    wPanel = dimension2.width;
	    hPanel = dimension2.height;
	    
	    RADIUS = wImgEarth/2;
	}

	public void run() {
		isRunning = true;
		
		while (isRunning) {
			delay();
			
	        double theta = 2 * Math.PI * index++ / 64;
//			getLbImgEarth().setIcon(
//					new ImageIcon(ImageManipulation.rotate(getIconEarth(),
//							rand.nextInt(360))));
//			getLbImgSol().setIcon(
//					new ImageIcon(ImageManipulation.rotate(getIconSol(),
//							rand.nextInt(360))));
//	        System.out.println(Math.toDegrees(theta));
	        	        
		    r.setBounds(
		            (int) (Math.sin(theta) * wPanel / 3 + wPanel / 2 - RADIUS),
		            (int) (Math.cos(theta) * hPanel / 3 + hPanel / 2 - RADIUS),
		            wImgEarth, hImgEarth);
			
		    if(listener != null){
		    	listener.update(r);
		    }
		}
	}


	private void delay() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		isRunning = false;
	}
}
