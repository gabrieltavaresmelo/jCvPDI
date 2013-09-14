package br.com.translate.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.googlecode.javacpp.*;
import com.googlecode.javacv.*;
import com.googlecode.javacv.cpp.opencv_core.IplImageArray;

import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_legacy.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;

/**
 * Classe utilitaria com as funcoes do OpenCV
 * 
 * @author Gabriel Tavares
 *
 */
public class OpenCvUtil {

	public static IplImage grayToBinaryImage(IplImage grayImage, boolean isCopy) {
		IplImage binaryImage = isCopy ? IplImage.create(cvGetSize(grayImage),
				grayImage.depth(), grayImage.nChannels()) : grayImage;
		cvThreshold(grayImage, binaryImage, 90, 255, CV_THRESH_BINARY);
		return binaryImage;
	}

	private static IplImage grayToBinaryImageInvert(IplImage grayImage) {
		IplImage binaryImage = IplImage.create(cvGetSize(grayImage), IPL_DEPTH_8U, 1);
		cvThreshold(grayImage, binaryImage, 150, 255, CV_THRESH_BINARY_INV);
		return binaryImage;
	}

	/**
	 * Converte uma IplImage para Tons de Cinza
	 * 
	 * @param image
	 * @return
	 */

	public static IplImage colorToGrayImage(IplImage image) {
		IplImage grayImage = IplImage.create(new CvSize(image.width(), image.height()), IPL_DEPTH_8U, 1);
		cvCvtColor(image, grayImage, CV_BGR2GRAY);
		
		return grayImage;
	}

	public static IplImage colorToHighContrast(IplImage image, boolean isCopy) {
		IplImage contrastImage = isCopy ? image.clone() : image;
				
		// increase contrast in dark pixels
		cvScale(image, contrastImage, 1, -70);
		
		// increase contrast in light pixels
		cvScale(contrastImage, contrastImage, 3, 2);

		return contrastImage;
	}

	public static IplImage colorToEqualized(IplImage image) {
		IplImage grayImage = colorToGrayImage(image);
		
		return grayToEqualized(grayImage);
	}

	public static IplImage grayToEqualized(IplImage grayImage) {
		IplImage equalizedImage = IplImage.create(cvGetSize(grayImage), IPL_DEPTH_8U, 1);
		cvEqualizeHist(grayImage, equalizedImage);
		
		return equalizedImage;
	}
	
	/**
	 * Converte uma IplImage no formato ARGB para RGB
	 * 
	 * @param image
	 * @return
	 */
	public static IplImage ARGB2RGB(IplImage image) {
		IplImage bgrImage = IplImage.create(new CvSize(image.width(), image.height()), image.depth(), image.nChannels() - 1);
		cvCvtColor(image, bgrImage, CV_RGBA2RGB);

		return bgrImage;
	}

	public static IplImage colorToBinaryImage(IplImage colorImage) {
		IplImage grayImage = colorToGrayImage(colorImage);
		IplImage binaryImage = grayToBinaryImage(grayImage, true);
		
		grayImage.release();
		
		return binaryImage;
	}

	public static IplImage colorToBinaryImageInvert(IplImage colorImage) {
		IplImage grayImage = colorToGrayImage(colorImage);
		IplImage binaryImage = grayToBinaryImageInvert(grayImage);
		return binaryImage;
	}

	public static IplImage copyImage(IplImage image) {
		IplImage copyImage = IplImage.create(cvGetSize(image), image.depth(), image.nChannels());
//		IplImage copyImage = IplImage.create(cvGetSize(image), IPL_DEPTH_8U, 1);
		cvCopy(image, copyImage);
		return copyImage;
	}

	public static IplImage colorToErodeImage(IplImage colorImage, int erodeValue) {
		IplImage grayImage = colorToGrayImage(colorImage);
		IplImage erodeImage = toErodeImage(grayImage, erodeValue, true);
		
		grayImage.release();
		
		return erodeImage;
	}

	public static IplImage toErodeImage(IplImage image, int erodeValue, boolean isCopy) {
		IplImage erodeImage = isCopy ? IplImage.create(cvGetSize(image),
				image.depth(), image.nChannels()) : image;
		cvErode(image, erodeImage, null, erodeValue);

		return erodeImage;
	}

	public static IplImage toSmoothImage(IplImage image, boolean isCopy) {
		IplImage newImage = isCopy ? IplImage.create(cvGetSize(image),
				image.depth(), image.nChannels()) : image;
		cvSmooth(image, newImage, CV_GAUSSIAN, 3);

		return newImage;
	}

	public static IplImage toBrightnessImage(IplImage image, double value, boolean isCopy) {
		IplImage newImage = isCopy ? IplImage.create(cvGetSize(image),
				image.depth(), image.nChannels()) : image;
		cvAddS(image, cvScalar(value, value, value, 0), newImage, null);

		return newImage;
	}

	public static IplImage colorToDilateImage(IplImage colorImage, int dilateValue) {
		IplImage grayImage = colorToGrayImage(colorImage);
		IplImage dilateImage = toDilateImage(grayImage, dilateValue, true);
		
		grayImage.release();
		
		return dilateImage;
	}

	public static IplImage toDilateImage(IplImage image, int dilateValue, boolean isCopy) {
		IplImage dilateImage = isCopy ? IplImage.create(cvGetSize(image),
				image.depth(), image.nChannels()) : image;
		cvDilate(image, dilateImage, null, dilateValue);

		return dilateImage;
	}

	public static IplImage toCircularImage(IplImage image) {
		int erodeCount = 2;
		int dilateCount = 1;

		IplImage erodeImage = toErodeImage(image, erodeCount, true);
		IplImage dilateImage = toDilateImage(erodeImage, dilateCount, true);

		cvReleaseImage(erodeImage);
		
		return dilateImage;
	}

	public static IplImage toNegativeImage(IplImage image) {
		IplImage negativeImage = IplImage.create(cvGetSize(image), image.depth(), image.nChannels());
		cvCopy(image, negativeImage); 
		cvNot(image, negativeImage);
		
		return negativeImage;
	}

	/**
	 * Filtro de suavizacao.
	 * Usado para obter uma reducao de ruido
	 * 
	 * @param image
	 * @return
	 */
	public static IplImage toMedianImage(IplImage image, boolean isCopy) {
		IplImage newImage = isCopy ? IplImage.create(cvGetSize(image),
				image.depth(), image.nChannels()) : image;
		cvSmooth(image, newImage, CV_MEDIAN, 5);
		
		return newImage;
	}

	/**
	 * Redimensiona uma IplImage
	 * 
	 * @param image
	 * @param width
	 * @param height
	 * @return image_resized
	 */
	public static IplImage resize(IplImage image, int width, int height) {
		if (width == image.width() && height == image.height()) {
			return image;
		} else if (width == 0 && height == 0) {
			return image;
		}

		width = width < 0 ? (image.width() / 2) : width;
		height = height < 0 ? (image.height() / 2) : height;

		IplImage resizedImage = IplImage.create(new CvSize(width, height), image.depth(), image.nChannels());
		cvResize(image, resizedImage);

		return resizedImage;
	}

	public static IplImage resize(IplImage image, double factor) {
		int width = (int) (image.width() * factor);
		int height = (int) (image.height() * factor);
		IplImage resizedImage = resize(image, width, height);

		return resizedImage;
	}

	public static IplImage getImageFromFile(String filePath) {
		File imageFile = new File(filePath);
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		IplImage iplImage = IplImage.createFrom(bufferedImage);

		return iplImage;
	}

	/**
	 * Cria e recorta a imagem a partir da regiao selecionada (ROI)
	 * 
	 * @param image
	 * @param roi
	 * @return image_cropped
	 */
	public static IplImage crop(IplImage image, CvRect roi) {
		// Seleciona a regiao do frame para processar
		cvSetImageROI(image, roi);

		// Cria a imagem a ser recortada
		IplImage cropped = IplImage.create(cvGetSize(image), image.depth(), image.nChannels());

		// Copia a regiao selecionada (ROI) na imagem original para o cropped
		cvCopy(image, cropped);

		// Restaura a imagem original
		cvResetImageROI(image);

		// cvSaveImage("cropped.png", cropped);
		return cropped;
	}
	
	public static IplImage bgr2hsv(IplImage orgImg) {
        IplImage imgHSV = IplImage.create(cvGetSize(orgImg), 8, 3);
        cvCvtColor(orgImg, imgHSV, CV_BGR2HSV);
        
        return imgHSV;
    }
	
	/**
	 * Obtem os 3 canais da imagem (RGB, BGR, HSV)
	 * e retorna-os num Array
	 * 
	 * @param image
	 * @return
	 */
	public static IplImage [] splitChannels(IplImage image) {
		CvSize size = image.cvSize();
		int depth = image.depth();
		
		IplImage [] imageArray = null;
		
		if(image.nChannels() >= 3){
			IplImage channel0 = IplImage.create(size, depth, 1);
			IplImage channel1 = IplImage.create(size, depth, 1);
			IplImage channel2 = IplImage.create(size, depth, 1);
			
			cvSplit(image, channel0, channel1, channel2, null);
			
			imageArray = new IplImage[]{channel0, channel1, channel2};
			
		} else{
			imageArray = new IplImage[]{image};
		}
		
		return imageArray;
	}
    
	public static IplImage rotateImage(IplImage image, float angle) {

		IplImage rotatedImage = cvCreateImage(image.cvSize(), image.depth(),
				image.nChannels());

		CvPoint2D32f center = new CvPoint2D32f();
		center.x(image.width()/2);
		center.y(image.height()/2);
		CvMat mapMatrix = cvCreateMat(2, 3, CV_32FC1);

		cv2DRotationMatrix(center, angle, 1.0, mapMatrix);
		cvWarpAffine(image, rotatedImage, mapMatrix, CV_INTER_LINEAR
				+ CV_WARP_FILL_OUTLIERS, cvScalarAll(0));

		cvReleaseMat(mapMatrix);

		return rotatedImage;
	}

	public static void save(String filename, IplImage venusToIpl) {
		cvSaveImage(filename, venusToIpl);
	}

	public static BufferedImage loadFromResource(String imagename) {
		try {
	    	URL url = OpenCvUtil.class.getClassLoader().getResource("resources/"+imagename+"");
	    	BufferedImage bi = ImageIO.read(url);
	    	
	    	return bi;
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
