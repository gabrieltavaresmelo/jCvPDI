package br.com.translate.view;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import br.com.translate.business.ListenerEarthTranslate;
import br.com.translate.business.ThreadEarthTranslate;
import br.com.translate.util.Histogram1D;
import br.com.translate.util.OpenCvUtil;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

/**
 * Classe de EVENTOS da TelaPrincipal para iteracao com o usuario
 * 
 * @author: Gabriel Tavares
 *
 */
public class MainWindow extends MainWindowView implements ListenerEarthTranslate{

	private static final long serialVersionUID = 1L;
	
	// Unica instancia da tela
	private static MainWindow instance = null;

	private ThreadEarthTranslate threadEarthTranslate;
		
	public MainWindow() {
		super();
		initialize();
		loadData();
	}
	
	public static MainWindow getInstance() {
		if (instance == null) {
			instance = new MainWindow();
		}
		return instance;
	}

	private void initialize() {
		this.addWindowListener(new WindowAdapter() {
			// Fecha a janela
			public void windowClosing (WindowEvent e) {
				getThreadEarthTranslate().stop();
			}
		});
	}
	
	private void loadData() {
//		int x = getLbImgEarth().getX();
//	    int y = getLbImgEarth().getY();
	    		    
	    new Thread(getThreadEarthTranslate()).start();
	}
	
	public ThreadEarthTranslate getThreadEarthTranslate() {
		if(threadEarthTranslate == null){
			threadEarthTranslate = new ThreadEarthTranslate(this,
					getLbImgEarth().getSize(), getJContentPane().getSize());
		}
		return threadEarthTranslate;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(getJMenuItemCapture())){
			IplImage planet1ToIpl = IplImage.createFrom(getImageVenus());
			IplImage planet2ToIpl = IplImage.createFrom(getImageNeptune());
			IplImage planet3ToIpl = IplImage.createFrom(getImageSolarSystem());
			
			OpenCvUtil.save("venusTest.jpg", planet1ToIpl);
			OpenCvUtil.save("mercurioTest.jpg", planet2ToIpl);
			OpenCvUtil.save("netunoTest.jpg", planet3ToIpl);
			
		} else if(e.getSource().equals(getJMenuItemExit())){
			getThreadEarthTranslate().stop();
			this.dispose();
			
		} else if(e.getSource().equals(getJMenuItemReset())){
			resetImages();
			
		} else if(e.getSource().equals(getJMenuItemToGray())){
			resetImages();
			
			IplImage planet1ToIpl = IplImage.createFrom(getImageVenus());
			IplImage planet2ToIpl = IplImage.createFrom(getImageNeptune());
			IplImage planet3ToIpl = IplImage.createFrom(getImageSolarSystem());
			
			IplImage planet1New = OpenCvUtil.colorToGrayImage(planet1ToIpl);
			IplImage planet2New = OpenCvUtil.colorToGrayImage(planet2ToIpl);
			IplImage planet3New = OpenCvUtil.colorToGrayImage(planet3ToIpl);
			
			setLbImgVenus(planet1New.getBufferedImage());
			setLbImgMercurio(planet2New.getBufferedImage());
			setLbImgSolarSystem(planet3New.getBufferedImage());
			
		} else if(e.getSource().equals(getJMenuItemToBinary())){
			resetImages();
			
			IplImage planet1ToIpl = IplImage.createFrom(getImageVenus());
			IplImage planet2ToIpl = IplImage.createFrom(getImageNeptune());
			IplImage planet3ToIpl = IplImage.createFrom(getImageSolarSystem());
			
			IplImage planet1New = OpenCvUtil.colorToBinaryImage(planet1ToIpl);
			IplImage planet2New = OpenCvUtil.colorToBinaryImage(planet2ToIpl);
			IplImage planet3New = OpenCvUtil.colorToBinaryImage(planet3ToIpl);
			
			setLbImgVenus(planet1New.getBufferedImage());
			setLbImgMercurio(planet2New.getBufferedImage());
			setLbImgSolarSystem(planet3New.getBufferedImage());
			
		} else if(e.getSource().equals(getJMenuItemToErode())){
			String input = JOptionPane.showInputDialog("Fator:");
			int fator;
			try {
				fator = Integer.parseInt(input);
			} catch (NumberFormatException e1) {
				fator = 2;
			}
			
			resetImages();
			
			IplImage planet1ToIpl = IplImage.createFrom(getImageVenus());
			IplImage planet2ToIpl = IplImage.createFrom(getImageNeptune());
			IplImage planet3ToIpl = IplImage.createFrom(getImageSolarSystem());

			IplImage planet1New = OpenCvUtil.colorToErodeImage(planet1ToIpl, fator);
			IplImage planet2New = OpenCvUtil.colorToErodeImage(planet2ToIpl, fator);
			IplImage planet3New = OpenCvUtil.colorToErodeImage(planet3ToIpl, fator);
			
			setLbImgVenus(planet1New.getBufferedImage());
			setLbImgMercurio(planet2New.getBufferedImage());
			setLbImgSolarSystem(planet3New.getBufferedImage());
			
		} else if(e.getSource().equals(getJMenuItemToDilate())){
			String input = JOptionPane.showInputDialog("Fator:");
			int fator;
			try {
				fator = Integer.parseInt(input);
			} catch (NumberFormatException e1) {
				fator = 2;
			}
			
			resetImages();
			
			IplImage planet1ToIpl = IplImage.createFrom(getImageVenus());
			IplImage planet2ToIpl = IplImage.createFrom(getImageNeptune());
			IplImage planet3ToIpl = IplImage.createFrom(getImageSolarSystem());

			IplImage planet1New = OpenCvUtil.colorToDilateImage(planet1ToIpl, fator);
			IplImage planet2New = OpenCvUtil.colorToDilateImage(planet2ToIpl, fator);
			IplImage planet3New = OpenCvUtil.colorToDilateImage(planet3ToIpl, fator);
			
			setLbImgVenus(planet1New.getBufferedImage());
			setLbImgMercurio(planet2New.getBufferedImage());
			setLbImgSolarSystem(planet3New.getBufferedImage());
			
		} else if(e.getSource().equals(getJMenuItemToNegative())){
			resetImages();
			
			IplImage planet1ToIpl = IplImage.createFrom(getImageVenus());
			IplImage planet2ToIpl = IplImage.createFrom(getImageNeptune());
			IplImage planet3ToIpl = IplImage.createFrom(getImageSolarSystem());
			
			IplImage planet1New = OpenCvUtil.toNegativeImage(planet1ToIpl);
			IplImage planet2New = OpenCvUtil.toNegativeImage(planet2ToIpl);
			IplImage planet3New = OpenCvUtil.toNegativeImage(planet3ToIpl);
			
			setLbImgVenus(planet1New.getBufferedImage());
			setLbImgMercurio(planet2New.getBufferedImage());
			setLbImgSolarSystem(planet3New.getBufferedImage());
			
		} else if(e.getSource().equals(getJMenuItemToHistogramEq())){
			resetImages();
			
			IplImage planet1ToIpl = IplImage.createFrom(getImageVenus());
			IplImage planet2ToIpl = IplImage.createFrom(getImageNeptune());
			IplImage netunoToIpl = IplImage.createFrom(getImageSolarSystem());
			
			IplImage planet1ToIplDest = Histogram1D.equalize(planet1ToIpl);
			IplImage planet2ToIplDest = Histogram1D.equalize(planet2ToIpl);
			IplImage planet3ToIpl = Histogram1D.equalize(netunoToIpl);

			BufferedImage venusIplHist = new Histogram1D().getHistogramImage(planet1ToIplDest);
			BufferedImage mercurioIplHist = new Histogram1D().getHistogramImage(planet2ToIplDest);
			BufferedImage netunoIplHist = new Histogram1D().getHistogramImage(planet3ToIpl);
			
			CanvasFrame canvasVenus = new CanvasFrame("Histograma Venus");
			canvasVenus.showImage(venusIplHist);
			
			CanvasFrame canvasMercurio = new CanvasFrame("Histograma Mercurio");
			canvasMercurio.showImage(mercurioIplHist);
			
			CanvasFrame canvasNetuno = new CanvasFrame("Histograma Netuno");
			canvasNetuno.showImage(netunoIplHist);
			
			setLbImgVenus(planet1ToIplDest.getBufferedImage());
			setLbImgMercurio(planet2ToIplDest.getBufferedImage());
			setLbImgSolarSystem(planet3ToIpl.getBufferedImage());
		
		} else if(e.getSource().equals(getJMenuItemToHistogram())){
			
			IplImage planet1ToIpl = IplImage.createFrom(getImageVenus());
			IplImage planet2ToIpl = IplImage.createFrom(getImageNeptune());
			IplImage netunoToIpl = IplImage.createFrom(getImageSolarSystem());

			CanvasFrame canvasVenus = new CanvasFrame("Histograma Venus");
			canvasVenus.showImage(new Histogram1D().getHistogramImage(planet1ToIpl));
			
			CanvasFrame canvasMercurio = new CanvasFrame("Histograma Mercurio");
			canvasMercurio.showImage(new Histogram1D().getHistogramImage(planet2ToIpl));
			
			CanvasFrame canvasNetuno = new CanvasFrame("Histograma Netuno");
			canvasNetuno.showImage(new Histogram1D().getHistogramImage(netunoToIpl));
			
		} else if(e.getSource().equals(getJMenuItemToSmooth())){
			resetImages();
			
			IplImage planet1ToIpl = IplImage.createFrom(getImageVenus());
			IplImage planet2ToIpl = IplImage.createFrom(getImageNeptune());
			IplImage netunoToIpl = IplImage.createFrom(getImageSolarSystem());
			
			IplImage planet1New = OpenCvUtil.toSmoothImage(planet1ToIpl, true);
			IplImage planet2New = OpenCvUtil.toSmoothImage(planet2ToIpl, true);
			IplImage planet3New = OpenCvUtil.toSmoothImage(netunoToIpl, true);
			
			setLbImgVenus(planet1New.getBufferedImage());
			setLbImgMercurio(planet2New.getBufferedImage());
			setLbImgSolarSystem(planet3New.getBufferedImage());
			
		} else if(e.getSource().equals(getJMenuItemToBrightness())){
			String input = JOptionPane.showInputDialog("Fator:");
			int fator;
			try {
				fator = Integer.parseInt(input);
			} catch (NumberFormatException e1) {
				fator = 2;
			}
			
			IplImage planet1ToIpl = IplImage.createFrom(getImageVenus());
			IplImage planet2ToIpl = IplImage.createFrom(getImageNeptune());
			IplImage netunoToIpl = IplImage.createFrom(getImageSolarSystem());
			
			IplImage planet1New = OpenCvUtil.toBrightnessImage(planet1ToIpl, fator, true);
			IplImage planet2New = OpenCvUtil.toBrightnessImage(planet2ToIpl, fator, true);
			IplImage planet3New = OpenCvUtil.toBrightnessImage(netunoToIpl, fator, true);
			
			setLbImgVenus(planet1New.getBufferedImage());
			setLbImgMercurio(planet2New.getBufferedImage());
			setLbImgSolarSystem(planet3New.getBufferedImage());

		} else if(e.getSource().equals(getJMenuItemToContrast())){
						
			IplImage planet1ToIpl = IplImage.createFrom(getImageVenus());
			IplImage planet2ToIpl = IplImage.createFrom(getImageNeptune());
			IplImage netunoToIpl = IplImage.createFrom(getImageSolarSystem());
			
			IplImage planet1New = OpenCvUtil.colorToHighContrast(planet1ToIpl, true);
			IplImage planet2New = OpenCvUtil.colorToHighContrast(planet2ToIpl, true);
			IplImage planet3New = OpenCvUtil.colorToHighContrast(netunoToIpl, true);
			
			setLbImgVenus(planet1New.getBufferedImage());
			setLbImgMercurio(planet2New.getBufferedImage());
			setLbImgSolarSystem(planet3New.getBufferedImage());
			
		} else if(e.getSource().equals(getJMenuItemResize())){
			resetImages();
			
			String input = JOptionPane.showInputDialog("Fator:");
			double fator;
			try {
				fator = Double.parseDouble(input);
			} catch (NumberFormatException e1) {
				fator = 2;
			}
			
			resetImages();
			
			IplImage planet1ToIpl = IplImage.createFrom(getImageVenus());
			IplImage planet2ToIpl = IplImage.createFrom(getImageNeptune());
			IplImage netunoToIpl = IplImage.createFrom(getImageSolarSystem());

			IplImage planet1New = OpenCvUtil.resize(planet1ToIpl, fator);
			IplImage planet2New = OpenCvUtil.resize(planet2ToIpl, fator);
			IplImage planet3New = OpenCvUtil.resize(netunoToIpl, fator);
			
			setLbImgVenus(planet1New.getBufferedImage());
			setLbImgMercurio(planet2New.getBufferedImage());
			setLbImgSolarSystem(planet3New.getBufferedImage());
			
		} else if(e.getSource().equals(getJMenuItemRotate())){
			int angulo = 90;
			
			IplImage planet1ToIpl = IplImage.createFrom(getImageVenus());
			IplImage planet2ToIpl = IplImage.createFrom(getImageNeptune());
			IplImage netunoToIpl = IplImage.createFrom(getImageSolarSystem());

			IplImage planet1New = OpenCvUtil.rotateImage(planet1ToIpl, angulo);
			IplImage planet2New = OpenCvUtil.rotateImage(planet2ToIpl, angulo);
			IplImage planet3New = OpenCvUtil.rotateImage(netunoToIpl, angulo);
			
			setLbImgVenus(planet1New.getBufferedImage());
			setLbImgMercurio(planet2New.getBufferedImage());
			setLbImgSolarSystem(planet3New.getBufferedImage());
			
		} else if(e.getSource().equals(getJMenuItemCrop())){
			//TODO
		} else if(e.getSource().equals(getJMenuItemAbout())){
			String conteudo = "";
			
			conteudo += "Disciplina:";
			conteudo += "<br/>";
			conteudo += "Sistemas Multimídia";
			conteudo += "<br/>";
			conteudo += "<br/>";

			conteudo += "Trabalho:";
			conteudo += "<br/>";
			conteudo += "Operações com Imagens Digitais";
			conteudo += "<br/>";
			conteudo += "<br/>";
			
			conteudo += "Equipe:";
			conteudo += "<br/>";
			conteudo += "Gabriel Martins";
			conteudo += "<br/>";
			conteudo += "Flávio Neves";
			conteudo += "<br/>";
			conteudo += "Alisson Saraiva";
			conteudo += "<br/>";
			conteudo += "Ranara Louise";
			conteudo += "<br/>";
			conteudo += "Gabriel Tavares";
			conteudo += "<br/>";
			conteudo += "<br/>";
			
			JOptionPane.showMessageDialog(null, "<html><body>" + conteudo
					+ "</body></html>", "Operações com Imagens Digitais", 
					JOptionPane.INFORMATION_MESSAGE,
					new ImageIcon(getIconImage()));
		}
	}

	private void resetImages() {
		imageVenus = null;
		imageNeptune = null;
		imageSolarSystem = null;
		
		setLbImgVenus(getImageVenus());
		setLbImgMercurio(getImageNeptune());
		setLbImgSolarSystem(getImageSolarSystem());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		JLabel lbImg = (JLabel) e.getSource();
		
		int x = e.getXOnScreen();
		int y = e.getYOnScreen();
		
		lbImg.setBounds(new Rectangle(x-130, y-110, lbImg.getWidth(),
						lbImg.getHeight()));
	}

	@Override
	public void mouseMoved(MouseEvent e) { }

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON3){
			JLabel lbImg = (JLabel) e.getSource();
			int angulo = 90;
			
			if(lbImg.equals(getLbImgVenus())){
				IplImage planet1ToIpl = IplImage.createFrom(getImageVenus());
				IplImage planet1New = OpenCvUtil.rotateImage(planet1ToIpl, angulo);
				setLbImgVenus(planet1New.getBufferedImage());
			} else if(lbImg.equals(getLbImgNeptune())){
				IplImage planet2ToIpl = IplImage.createFrom(getImageNeptune());
				IplImage planet2New = OpenCvUtil.rotateImage(planet2ToIpl, angulo);
				setLbImgMercurio(planet2New.getBufferedImage());
			} else{
				IplImage netunoToIpl = IplImage.createFrom(getImageSolarSystem());
				IplImage planet3New = OpenCvUtil.rotateImage(netunoToIpl, angulo);
				setLbImgSolarSystem(planet3New.getBufferedImage());
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }

	@Override
	public void update(Rectangle r) {
		getLbImgEarth().setBounds(r);		
	}
}
