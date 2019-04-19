package picEditor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class blobArrayList {
	ArrayList<ArrayList<int[]>> blobs;
	ArrayList<int[]> bounds;
	ArrayList<BufferedImage> transformed;

	public blobArrayList() {
		blobs = new ArrayList<ArrayList<int[]>>();
		bounds = new ArrayList<int[]>();
		transformed = new ArrayList<BufferedImage>();
	}

	public void reset() {
		blobs = new ArrayList<ArrayList<int[]>>();
		bounds = new ArrayList<int[]>();
		transformed = new ArrayList<BufferedImage>();
	}

	// calculates the bounds for all blobs
	public void calcBounds(int x, int y) {
		for (int i = 0; i < blobs.size(); i++) {
			int upper_i = blobs.get(i).get(0)[1], lower_i = blobs.get(i).get(0)[1], upper_j = blobs.get(i).get(0)[0],
					lower_j = blobs.get(i).get(0)[0];
			for (int j = 0; j < blobs.get(i).size(); j++) {
				if (blobs.get(i).get(j)[1] > upper_i) {
					upper_i = blobs.get(i).get(j)[1];
				} else if (blobs.get(i).get(j)[1] < lower_i) {
					lower_i = blobs.get(i).get(j)[1];
				}
				if (blobs.get(i).get(j)[0] > upper_j) {
					upper_j = blobs.get(i).get(j)[0];
				} else if (blobs.get(i).get(j)[0] < upper_j) {
					lower_j = blobs.get(i).get(j)[0];
				}
			}
			if (x == 9 && y == 9) {
				if (upper_i - lower_i < 9) {
					upper_i = lower_i+9;
				}
				if (upper_j - lower_j < 9) {
					upper_j = lower_j+9;
				}
			}
			bounds.add(new int[] { lower_j, lower_i, upper_j, upper_i });
		}
	}

	// normalize coordinates/indices
	public void normBlobs() {
		for (int i = 0; i < blobs.size(); i++) {
			BufferedImage temp = new BufferedImage(bounds.get(i)[2] - bounds.get(i)[0] + 1,
					bounds.get(i)[3] - bounds.get(i)[1] + 1, BufferedImage.TYPE_BYTE_GRAY);
			// initialize all to white
			Color x = new Color(255, 255, 255);
			for (int j = 0; j < temp.getHeight(); j++) {
				for (int k = 0; k < temp.getWidth(); k++) {
					temp.setRGB(k, j, x.getRGB());
				}
			}
			x = new Color(0, 0, 0);
			for (int j = 0; j < blobs.get(i).size(); j++) {
				temp.setRGB(blobs.get(i).get(j)[0] - bounds.get(i)[0], blobs.get(i).get(j)[1] - bounds.get(i)[1],
						x.getRGB());
			}
			transformed.add(temp);
		}
	}

	// rescales all blobs to x by y
	public void scaleBlobs(int x, int y) {
		if (transformed.get(0).getHeight() != y && transformed.get(0).getHeight() != x) {

			for (int i = 0; i < transformed.size(); i++) {
				BufferedImage temp = new BufferedImage(x, y, BufferedImage.TYPE_BYTE_GRAY);
				Graphics2D graphics2d = temp.createGraphics();
				graphics2d.drawImage(transformed.get(i), 0, 0, x, y, null);
				graphics2d.dispose();
				transformed.set(i, temp);
			}
		}
	}

	// get black to white ratio for 9 regions of 3x3
	public double[][] getVectors() {
		double[][] ratios = new double[transformed.size()][9];
		for (int x = 0; x < transformed.size(); x++) {
			int count = 0;

			for (int i = 1; i < 9; i += 3) {
				for (int j = 1; j < 9; j += 3) {
					System.out.println(transformed.get(x).getHeight() + "," + transformed.get(x).getWidth());
					int black = 0;
					if (new Color(transformed.get(x).getRGB(j - 1, i - 1)).getRed() == 0) {
						black++;
					}
					if (new Color(transformed.get(x).getRGB(j, i - 1)).getRed() == 0) {
						black++;
					} 
					if (new Color(transformed.get(x).getRGB(j + 1, i - 1)).getRed() == 0) {
						black++;
					} 
					if (new Color(transformed.get(x).getRGB(j - 1, i)).getRed() == 0) {
						black++;
					}
					if (new Color(transformed.get(x).getRGB(j + 1, i)).getRed() == 0) {
						black++;
					}
					if (new Color(transformed.get(x).getRGB(j - 1, i + 1)).getRed() == 0) {
						black++;
					} 
					if (new Color(transformed.get(x).getRGB(j, i + 1)).getRed() == 0) {
						black++;
					} 
					if (new Color(transformed.get(x).getRGB(j + 1, i + 1)).getRed() == 0) {
						black++;
					} 
					if (new Color(transformed.get(x).getRGB(j, i)).getRed() == 0) {
						black++;
					}
					// int temp=white-black
					// double temp=(double)black/(double)white;

					ratios[x][count++] = (double)black/(double)9;
				}
			}
		}
		return ratios;
	}

}
