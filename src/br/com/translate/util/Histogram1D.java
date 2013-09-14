package br.com.translate.util;


import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_legacy.*;
import static com.googlecode.javacv.cpp.opencv_core.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Histogram1D {

	int numberOfBins = 256;
	float _minRange = 0.0f;
	float _maxRange = 255.0f;

	public void setRanges(float minRange, float maxRange) {
		_minRange = minRange;
		_maxRange = maxRange;
	}

	/**
	 * Computes histogram of an image. This method is `private` since its proper use requires
	 * knowledge of inner working of the implementation:
	 * # how to extract data from the CvHistogram structure
	 * # CvHistogram has to be manually deallocated after use.
	 *
	 * @param image input image
	 * @param mask optional mask
	 * @return OpenCV histogram object
	 */
	public CvHistogram getHistogram(IplImage image, IplImage mask) {
		// Allocate histogram object
		int dims = 1;
		int[] sizes = new int[] { numberOfBins };
		int histType = CV_HIST_ARRAY;
		//        ranges = Array(Array(_minRange, _maxRange));
		float[] minMax = new float[] { _minRange, _maxRange };
		float[][] ranges = new float[][] { minMax };
		CvHistogram hist = cvCreateHist(dims, sizes, histType, ranges, 1);

		// Compute histogram
		int accumulate = 0;
		IplImage [] iplArr = OpenCvUtil.splitChannels(image);
		
		if(iplArr != null){
			cvCalcHist(iplArr, hist, accumulate, mask);
		}
		
		return hist;
	}

	/**
	 * Computes histogram of an image.
	 * @param image input image
	 * @return histogram represented as an array
	 */
	public float [] getHistogramAsArray(IplImage image){
		// Create and calculate histogram object
		CvHistogram histogram = getHistogram(image, null);

		// Extract values to an array
		float[] dest = new float[numberOfBins];
		for (int bin = 0; bin < numberOfBins; bin++) {
			dest[bin] = cvQueryHistValue_1D(histogram, bin);
		}

		// Release the memory allocated for histogram
		cvReleaseHist(histogram);

		return dest;
	}

	public BufferedImage getHistogramImage(IplImage image) {

		// Output image size
		int width = numberOfBins;
		int height = numberOfBins;

		float[] hist = getHistogramAsArray(image);
		// Set highest point to 90% of the number of bins
		double scale = 0.9 / hist.length * height;

		// Create a color image to draw on
		BufferedImage canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = canvas.createGraphics();

		// Paint background
		g.setPaint(Color.WHITE);
		g.fillRect(0, 0, width, height);

		// Draw a vertical line for each bin
		g.setPaint(Color.BLUE);
		for (int bin = 0; bin < numberOfBins; bin++) {
			int h = (int) Math.round(hist[bin] * scale);
			g.drawLine(bin, height - 1, bin, height - h - 1);
		}

		// Cleanup
		g.dispose();

		return canvas;
	}

	public static IplImage equalize(IplImage src) {
		IplImage newImg = src;
		
		if(src.nChannels() > 1){
			newImg = OpenCvUtil.colorToGrayImage(src);
		}
		
		IplImage dest = IplImage.create(new CvSize(newImg.width(), newImg.height()),
				newImg.depth(), newImg.nChannels());

		// Equaliza o histograma
		cvEqualizeHist(newImg, dest);

		return dest;
	}
}
