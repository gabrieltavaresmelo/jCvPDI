package br.com.translate.view;


import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import br.com.translate.util.OpenCvUtil;

/**
 * Classe de layout da TelaPrincipal 
 * 
 * @author: Gabriel Tavares
 *
 */
public abstract class MainWindowView extends JFrame implements ActionListener, MouseMotionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	
	// Widgets
	private JPanel jContentPane = null;
	private JMenuBar jJMenuBar = null;
	private JMenu jMenuFile = null;
	private JMenu jMenuProcess = null;
	private JMenu jMenuTransform = null;
	private JMenu jMenuHelp = null;
	private JMenuItem jMenuItemCapture = null;
	private JMenuItem jMenuItemExit = null;
	private JMenuItem jMenuItemReset;
	private JMenuItem jMenuItemToGray;
	private JMenuItem jMenuItemToBinary;
	private JMenuItem jMenuItemToErode;
	private JMenuItem jMenuItemToDilate;
	private JMenuItem jMenuItemToNegative;
	private JMenuItem jMenuItemToSmooth;
	private JMenuItem jMenuItemToBrightness;
	private JMenuItem jMenuItemToContrast;
	private JMenuItem jMenuItemToHistogramEq;
	private JMenuItem jMenuItemToHistogram;
	private JMenuItem jMenuItemRotate;
	private JMenuItem jMenuItemResize;
	private JMenuItem jMenuItemToCrop;
	private JMenuItem jMenuItemAbout;
	private JLabel lbImgSol = null;
	private JLabel lbImgEarth = null;
	private JLabel lbImgVenus = null;
	private JLabel lbImgNeptune = null;
	private JLabel lbImgSolarSystem = null;

	protected BufferedImage imageVenus = null;
	protected BufferedImage imageNeptune = null;
	protected BufferedImage imageSolarSystem = null;

	
	public MainWindowView() {
		super();
		initialize();
	}

	private void initialize() {
		this.setSize(900, 700);
		this.setJMenuBar(getJJMenuBar());
		this.setContentPane(getJContentPane());
		this.setTitle("Sistemas Multímidia");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation(
						((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - (this
								.getWidth() / 2)),
						((Toolkit.getDefaultToolkit().getScreenSize().height / 2) - (this
								.getHeight() / 2)));
		
		// Inicializa a aplicacao com a Janela Maximizada
	    this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	    
	    URL url = this.getClass().getClassLoader().getResource("resources/logo.png");
	    this.setIconImage(new ImageIcon(url).getImage());
	}
	
	protected JPanel getJContentPane() {
		if (jContentPane == null) {
			try {
//				URL url = this.getClass().getClassLoader().getResource("resources/planets.jpg");
//				BufferedImage bi = ImageIO.read(url);
//				
//				jContentPane = new ImagePanel(bi);
				jContentPane = new JPanel();
				jContentPane.setLayout(null);
				jContentPane.setSize(getSize());
				jContentPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
				jContentPane.add(getLbImgSol(), null);
				jContentPane.add(getLbImgEarth(), null);
				jContentPane.add(getLbImgVenus(), null);
				jContentPane.add(getLbImgNeptune(), null);
				jContentPane.add(getLbImgSolarSystem(), null);
				jContentPane.setBackground(Color.BLACK);
//				jContentPane.setBorder(BorderFactory.createLineBorder(Color.RED));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return jContentPane;
	}

	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getJMenuFile());
			jJMenuBar.add(getJMenuConvert());
			jJMenuBar.add(getJMenuTransform());
			jJMenuBar.add(getJMenuHelp());
		}
		
		return jJMenuBar;
	}

	private JMenu getJMenuFile() {
		if (jMenuFile == null) {
			jMenuFile = new JMenu();
			jMenuFile.setText("Arquivo");
			jMenuFile.add(getJMenuItemCapture());
			jMenuFile.addSeparator();
			jMenuFile.add(getJMenuItemExit());
		}
		return jMenuFile;
	}

	private JMenu getJMenuConvert() {
		if (jMenuProcess == null) {
			jMenuProcess = new JMenu();
			jMenuProcess.setText("Processamento");
			jMenuProcess.add(getJMenuItemReset());
			jMenuProcess.addSeparator();
			jMenuProcess.add(getJMenuItemToGray());
			jMenuProcess.add(getJMenuItemToBinary());
			jMenuProcess.add(getJMenuItemToErode());
			jMenuProcess.add(getJMenuItemToDilate());
			jMenuProcess.add(getJMenuItemToNegative());
			jMenuProcess.add(getJMenuItemToSmooth());
			jMenuProcess.add(getJMenuItemToBrightness());
			jMenuProcess.add(getJMenuItemToContrast());
			jMenuProcess.add(getJMenuItemToHistogramEq());
			jMenuProcess.addSeparator();
			jMenuProcess.add(getJMenuItemToHistogram());
		}
		return jMenuProcess;
	}

	private JMenu getJMenuTransform() {
		if (jMenuTransform == null) {
			jMenuTransform = new JMenu();
			jMenuTransform.setText("Transformação");
			jMenuTransform.add(getJMenuItemResize());
			jMenuTransform.add(getJMenuItemRotate());
			jMenuTransform.add(getJMenuItemCrop());
		}
		return jMenuTransform;
	}

	private JMenu getJMenuHelp() {
		if (jMenuHelp == null) {
			jMenuHelp = new JMenu();
			jMenuHelp.setText("Ajuda");
			jMenuHelp.add(getJMenuItemAbout());
		}
		return jMenuHelp;
	}

	protected JMenuItem getJMenuItemCapture() {
		if (jMenuItemCapture == null) {
			jMenuItemCapture = new JMenuItem();
			jMenuItemCapture.setText("Capturar");
			jMenuItemCapture.addActionListener(this);
		}
		return jMenuItemCapture;
	}

	protected JMenuItem getJMenuItemExit() {
		if (jMenuItemExit == null) {
			jMenuItemExit = new JMenuItem();
			jMenuItemExit.setText("Sair");
			jMenuItemExit.addActionListener(this);
		}
		return jMenuItemExit;
	}

	protected JMenuItem getJMenuItemAbout() {
		if (jMenuItemAbout == null) {
			jMenuItemAbout = new JMenuItem();
			jMenuItemAbout.setText("Sobre");
			jMenuItemAbout.addActionListener(this);
		}
		return jMenuItemAbout;
	}

	protected JMenuItem getJMenuItemReset() {
		if (jMenuItemReset == null) {
			jMenuItemReset = new JMenuItem();
			jMenuItemReset.setText("Reset");
			jMenuItemReset.addActionListener(this);
		}
		return jMenuItemReset;
	}

	protected JMenuItem getJMenuItemRotate() {
		if (jMenuItemRotate == null) {
			jMenuItemRotate = new JMenuItem();
			jMenuItemRotate.setText("Rotacionar");
			jMenuItemRotate.addActionListener(this);
		}
		return jMenuItemRotate;
	}

	protected JMenuItem getJMenuItemResize() {
		if (jMenuItemResize == null) {
			jMenuItemResize = new JMenuItem();
			jMenuItemResize.setText("Redimensionar");
			jMenuItemResize.addActionListener(this);
		}
		return jMenuItemResize;
	}

	protected JMenuItem getJMenuItemToGray() {
		if (jMenuItemToGray == null) {
			jMenuItemToGray = new JMenuItem();
			jMenuItemToGray.setText("Escala Cinza");
			jMenuItemToGray.addActionListener(this);
		}
		return jMenuItemToGray;
	}

	protected JMenuItem getJMenuItemToBinary() {
		if (jMenuItemToBinary == null) {
			jMenuItemToBinary = new JMenuItem();
			jMenuItemToBinary.setText("Binarização");
			jMenuItemToBinary.addActionListener(this);
		}
		return jMenuItemToBinary;
	}

	protected JMenuItem getJMenuItemToErode() {
		if (jMenuItemToErode == null) {
			jMenuItemToErode = new JMenuItem();
			jMenuItemToErode.setText("Erosão");
			jMenuItemToErode.addActionListener(this);
		}
		return jMenuItemToErode;
	}

	protected JMenuItem getJMenuItemToDilate() {
		if (jMenuItemToDilate == null) {
			jMenuItemToDilate = new JMenuItem();
			jMenuItemToDilate.setText("Dilatação");
			jMenuItemToDilate.addActionListener(this);
		}
		return jMenuItemToDilate;
	}

	protected JMenuItem getJMenuItemToNegative() {
		if (jMenuItemToNegative == null) {
			jMenuItemToNegative = new JMenuItem();
			jMenuItemToNegative.setText("Negativa");
			jMenuItemToNegative.addActionListener(this);
		}
		return jMenuItemToNegative;
	}

	protected JMenuItem getJMenuItemToSmooth() {
		if (jMenuItemToSmooth == null) {
			jMenuItemToSmooth = new JMenuItem();
			jMenuItemToSmooth.setText("Suavização");
			jMenuItemToSmooth.addActionListener(this);
		}
		return jMenuItemToSmooth;
	}

	protected JMenuItem getJMenuItemToBrightness() {
		if (jMenuItemToBrightness == null) {
			jMenuItemToBrightness = new JMenuItem();
			jMenuItemToBrightness.setText("Brilho");
			jMenuItemToBrightness.addActionListener(this);
		}
		return jMenuItemToBrightness;
	}

	protected JMenuItem getJMenuItemToContrast() {
		if (jMenuItemToContrast == null) {
			jMenuItemToContrast = new JMenuItem();
			jMenuItemToContrast.setText("Contraste");
			jMenuItemToContrast.addActionListener(this);
		}
		return jMenuItemToContrast;
	}

	protected JMenuItem getJMenuItemCrop() {
		if (jMenuItemToCrop == null) {
			jMenuItemToCrop = new JMenuItem();
			jMenuItemToCrop.setText("Cortar");
			jMenuItemToCrop.addActionListener(this);
			jMenuItemToCrop.setVisible(false);//TODO
		}
		return jMenuItemToCrop;
	}

	protected JMenuItem getJMenuItemToHistogramEq() {
		if (jMenuItemToHistogramEq == null) {
			jMenuItemToHistogramEq = new JMenuItem();
			jMenuItemToHistogramEq.setText("Equalização de Histograma");
			jMenuItemToHistogramEq.addActionListener(this);
		}
		return jMenuItemToHistogramEq;
	}

	protected JMenuItem getJMenuItemToHistogram() {
		if (jMenuItemToHistogram == null) {
			jMenuItemToHistogram = new JMenuItem();
			jMenuItemToHistogram.setText("Histograma");
			jMenuItemToHistogram.addActionListener(this);
		}
		return jMenuItemToHistogram;
	}
	
	protected JLabel getLbImgSol() {
		if (lbImgSol == null) {		    		
		    int x0 = (getJContentPane().getWidth()/2)-70;
		    int y0 = (getJContentPane().getHeight()/2)-90;

		    URL url = this.getClass().getClassLoader().getResource("resources/sol.gif");
			ImageIcon icon = new ImageIcon(url);
//		    ImageIcon icon = new ImageIcon(getIconSol());
						
			lbImgSol = new JLabel();
			lbImgSol.setIcon(icon);
			lbImgSol.setBounds(new Rectangle(x0, y0, icon
					.getIconWidth(), icon.getIconHeight()));
//			lbImgSol.setSize(icon.getIconWidth()/2, icon.getIconHeight()/2);
			lbImgSol.setHorizontalAlignment(SwingConstants.CENTER);
			lbImgSol.setVerticalAlignment(SwingConstants.CENTER);
//			lbImgSol.setBorder(BorderFactory.createLineBorder(Color.BLUE));
//			lbImg.addMouseListener(this);
		}
		
		return lbImgSol;
	}
	
	protected JLabel getLbImgEarth() {
		if (lbImgEarth == null) {		
			URL url = this.getClass().getClassLoader().getResource("resources/earth.gif");
			ImageIcon icon = new ImageIcon(url);
			
		    lbImgEarth = new JLabel();
			lbImgEarth.setIcon(icon);
			lbImgEarth.setSize(icon.getIconWidth(), icon.getIconHeight());
			lbImgEarth.setHorizontalAlignment(SwingConstants.CENTER);
			lbImgEarth.setVerticalAlignment(SwingConstants.CENTER);
			lbImgEarth.setText("");
		}
		
		return lbImgEarth;
	}
	
	protected JLabel getLbImgVenus() {
		if (lbImgVenus == null) {	
			ImageIcon icon = new ImageIcon(getImageVenus());
			
			lbImgVenus = new JLabel();
			lbImgVenus.setIcon(icon);
			lbImgVenus.setHorizontalAlignment(SwingConstants.CENTER);
			lbImgVenus.setVerticalAlignment(SwingConstants.CENTER);
			lbImgVenus.setBounds(new Rectangle(30, 600, icon
					.getIconWidth(), icon.getIconHeight()));
			lbImgVenus.setText("");
			lbImgVenus.addMouseListener(this);
			lbImgVenus.addMouseMotionListener(this);
//			lbImgVenus.setBorder(BorderFactory.createLineBorder(Color.RED));
		}
		
		return lbImgVenus;
	}
	
	protected JLabel getLbImgNeptune() {
		if (lbImgNeptune == null) {		
			ImageIcon icon = new ImageIcon(getImageNeptune());
			
			lbImgNeptune = new JLabel();
			lbImgNeptune.setIcon(icon);
			lbImgNeptune.setHorizontalAlignment(SwingConstants.CENTER);
			lbImgNeptune.setVerticalAlignment(SwingConstants.CENTER);
			lbImgNeptune.setBounds(new Rectangle(30, 30, icon
					.getIconWidth(), icon.getIconHeight()));
			lbImgNeptune.setText("");
			lbImgNeptune.addMouseListener(this);
			lbImgNeptune.addMouseMotionListener(this);
		}
		
		return lbImgNeptune;
	}
	
	protected JLabel getLbImgSolarSystem() {
		if (lbImgSolarSystem == null) {		
			ImageIcon icon = new ImageIcon(getImageSolarSystem());
			
			lbImgSolarSystem = new JLabel();
			lbImgSolarSystem.setIcon(icon);
			lbImgSolarSystem.setHorizontalAlignment(SwingConstants.CENTER);
			lbImgSolarSystem.setVerticalAlignment(SwingConstants.CENTER);
			lbImgSolarSystem.setBounds(new Rectangle(740, 10, icon
					.getIconWidth(), icon.getIconHeight()));
			lbImgSolarSystem.setText("");
			lbImgSolarSystem.addMouseListener(this);
			lbImgSolarSystem.addMouseMotionListener(this);
		}
		
		return lbImgSolarSystem;
	}
	
	protected void resetBounds(Icon icon) {
		getLbImgVenus().setBounds(new Rectangle(1100, 300, icon
				.getIconWidth(), icon.getIconHeight()));

		getLbImgNeptune().setBounds(new Rectangle(800, 500, icon
				.getIconWidth(), icon.getIconHeight()));
		
		getLbImgSolarSystem().setBounds(new Rectangle(30, 30, icon
				.getIconWidth(), icon.getIconHeight()));
	}
	
	public void setLbImgVenus(BufferedImage img) {
		this.imageVenus = img;
		ImageIcon icon = new ImageIcon(img);
		getLbImgVenus().setIcon(icon);
	}
	
	public void setLbImgMercurio(BufferedImage img) {
		this.imageNeptune = img;
		ImageIcon icon = new ImageIcon(img);
		getLbImgNeptune().setIcon(icon);
	}
	
	public void setLbImgSolarSystem(BufferedImage img) {
		this.imageSolarSystem = img;
		ImageIcon icon = new ImageIcon(img);
		getLbImgSolarSystem().setIcon(icon);
	}

	public BufferedImage getImageVenus() {
		if(imageVenus == null){
			try {
				imageVenus = OpenCvUtil.loadFromResource("venus.jpg");
//		    	System.out.println(imageVenus.getWidth() +", "+ imageVenus.getHeight());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return imageVenus;
	}

	public BufferedImage getImageNeptune() {
		if(imageNeptune == null){
			try {
				imageNeptune = OpenCvUtil.loadFromResource("neptune.jpg");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return imageNeptune;
	}

	public BufferedImage getImageSolarSystem() {
		if(imageSolarSystem == null){
			try {
				imageSolarSystem = OpenCvUtil.loadFromResource("solarsystem.jpg");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return imageSolarSystem;
	}

	public abstract void actionPerformed(ActionEvent e);
}
