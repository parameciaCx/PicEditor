/**
 * 
 */
package picEditor;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.xml.ws.AsyncHandler;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * @author Joey
 *
 */

public class main {
	static BufferedImage imga = null; // original image
	static BufferedImage imga2 = null;
	static BufferedImage imga3 = null;
	static BufferedImage imga4 = null;
	static BufferedImage imga5 = null;
	static JLabel bwCanvas;
	static JLabel analyzedCanvas;
	static JFrame analyzeFrame;
	static int[][] visited;
	static blobArrayList mainList = new blobArrayList();
	static List<Point> toWhite = new ArrayList<>();
	static char[][] grid;
	final static int[][] nbrs = { { 0, -1 }, { 1, -1 }, { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1 }, { -1, 0 }, { -1, -1 },
			{ 0, -1 } };
	final static int[][][] nbrGroups = { { { 0, 2, 4 }, { 2, 4, 6 } }, { { 0, 2, 6 }, { 0, 4, 6 } } };

	/**
	 * @param args
	 */

	public static int symbol(int i, int j, BufferedImage img) {

		ArrayList<int[]> asdf = unvisitedNeighbors(i, j, img);
		visited[j][i] = -1;
		int s = 0;
		if (asdf.size() == 0) {
			return 1;
		} else {
			for (int k = 0; k < asdf.size(); k++) {
				if (visited[asdf.get(k)[0]][asdf.get(k)[1]] != -1) { // make sure unvisited neighbors didn't get visited
																		// after last checked
					s = s + symbol(asdf.get(k)[1], asdf.get(k)[0], img);
				}
			}
		}
		return s;

	}

	// put unvisited neighbors that are black into arraylist
	public static ArrayList<int[]> unvisitedNeighbors(int i, int j, BufferedImage img) {
		ArrayList<int[]> neighbors = new ArrayList<int[]>();
		if (i == 0 && j == 0) { // top left corner

			// append relevant neighbors if unvisited
			if (visited[j + 1][i] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j + 1, i)).getRed()) {
				neighbors.add(new int[] { j + 1, i });
			}
			if (visited[j][i + 1] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j, i + 1)).getRed()) {
				neighbors.add(new int[] { j, i + 1 });
			}
			if (visited[j + 1][i + 1] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j + 1, i + 1)).getRed()) {
				neighbors.add(new int[] { j + 1, i + 1 });
			}

		} else if (i == 0 && j == img.getWidth() - 1) {
			// top right corner
			if (visited[j - 1][i] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j - 1, i)).getRed()) {
				neighbors.add(new int[] { j - 1, i });
			}
			if (visited[j][i + 1] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j, i + 1)).getRed()) {
				neighbors.add(new int[] { j, i + 1 });
			}
			if (visited[j - 1][i + 1] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j - 1, i + 1)).getRed()) {
				neighbors.add(new int[] { j - 1, i + 1 });
			}
		} else if (j == 0 && i == img.getHeight() - 1) { // bottom left corner
			if (visited[j + 1][i] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j + 1, i)).getRed()) {
				neighbors.add(new int[] { j + 1, i });
			}
			if (visited[j][i - 1] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j, i - 1)).getRed()) {
				neighbors.add(new int[] { j, i - 1 });
			}
			if (visited[j + 1][i - 1] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j + 1, i - 1)).getRed()) {
				neighbors.add(new int[] { j + 1, i - 1 });
			}
		} else if (j == img.getWidth() - 1 && i == img.getHeight() - 1) {// bottom right corner
			if (visited[j - 1][i] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j - 1, i)).getRed()) {
				neighbors.add(new int[] { j - 1, i });
			}
			if (visited[j][i - 1] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j, i - 1)).getRed()) {
				neighbors.add(new int[] { j, i - 1 });
			}
			if (visited[j - 1][i - 1] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j - 1, i - 1)).getRed()) {
				neighbors.add(new int[] { j - 1, i - 1 });
			}
		} else if (i == 0) {// first row that is not a corner
			if (visited[j + 1][i] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j + 1, i)).getRed()) {
				neighbors.add(new int[] { j + 1, i });
			}
			if (visited[j - 1][i] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j - 1, i)).getRed()) {
				neighbors.add(new int[] { j - 1, i });
			}
			if (visited[j - 1][i + 1] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j - 1, i + 1)).getRed()) {
				neighbors.add(new int[] { j - 1, i + 1 });
			}
			if (visited[j][i + 1] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j, i + 1)).getRed()) {
				neighbors.add(new int[] { j, i + 1 });
			}
			if (visited[j + 1][i + 1] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j + 1, i + 1)).getRed()) {
				neighbors.add(new int[] { j + 1, i + 1 });
			}
		} else if (j == 0) {// first column that is not a corner
			if (visited[j + 1][i] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j + 1, i)).getRed()) {
				neighbors.add(new int[] { j + 1, i });
			}
			if (visited[j + 1][i - 1] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j + 1, i - 1)).getRed()) {
				neighbors.add(new int[] { j + 1, i - 1 });
			}
			if (visited[j][i + 1] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j, i + 1)).getRed()) {
				neighbors.add(new int[] { j, i + 1 });
			}
			if (visited[j][i - 1] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j, i - 1)).getRed()) {
				neighbors.add(new int[] { j, i - 1 });
			}
			if (visited[j + 1][i + 1] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j + 1, i + 1)).getRed()) {
				neighbors.add(new int[] { j + 1, i + 1 });
			}
		} else if (i == img.getHeight() - 1) {
			if (visited[j + 1][i] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j + 1, i)).getRed()) {
				neighbors.add(new int[] { j + 1, i });
			}
			if (visited[j - 1][i] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j - 1, i)).getRed()) {
				neighbors.add(new int[] { j - 1, i });
			}
			if (visited[j][i - 1] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j, i - 1)).getRed()) {
				neighbors.add(new int[] { j, i - 1 });
			}
			if (visited[j - 1][i - 1] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j - 1, i - 1)).getRed()) {
				neighbors.add(new int[] { j - 1, i - 1 });
			}
			if (visited[j + 1][i - 1] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j + 1, i - 1)).getRed()) {
				neighbors.add(new int[] { j + 1, i - 1 });
			}
		} else if (j == img.getWidth() - 1) {// last column that is not a corner
			if (visited[j - 1][i] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j - 1, i)).getRed()) {
				neighbors.add(new int[] { j - 1, i });
			}
			if (visited[j - 1][i - 1] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j - 1, i - 1)).getRed()) {
				neighbors.add(new int[] { j - 1, i - 1 });
			}
			if (visited[j][i + 1] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j, i + 1)).getRed()) {
				neighbors.add(new int[] { j, i + 1 });
			}
			if (visited[j][i - 1] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j, i - 1)).getRed()) {
				neighbors.add(new int[] { j, i - 1 });
			}
			if (visited[j - 1][i + 1] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j - 1, i + 1)).getRed()) {
				neighbors.add(new int[] { j - 1, i + 1 });
			}
		} else {// elements where the 8 surrounding elements are all in the image (non edge
				// elements)

			if (visited[j + 1][i] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j + 1, i)).getRed()) {
				neighbors.add(new int[] { j + 1, i });
			}
			if (visited[j + 1][i - 1] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j + 1, i - 1)).getRed()) {
				neighbors.add(new int[] { j + 1, i - 1 });
			}
			if (visited[j + 1][i + 1] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j + 1, i + 1)).getRed()) {
				neighbors.add(new int[] { j + 1, i + 1 });
			}
			if (visited[j][i + 1] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j, i + 1)).getRed()) {
				neighbors.add(new int[] { j, i + 1 });
			}
			if (visited[j][i - 1] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j, i - 1)).getRed()) {
				neighbors.add(new int[] { j, i - 1 });
			}
			if (visited[j - 1][i + 1] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j - 1, i + 1)).getRed()) {
				neighbors.add(new int[] { j - 1, i + 1 });
			}
			if (visited[j - 1][i] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j - 1, i)).getRed()) {
				neighbors.add(new int[] { j - 1, i });
			}
			if (visited[j - 1][i - 1] == 255
					&& new Color(img.getRGB(j, i)).getRed() == new Color(img.getRGB(j - 1, i - 1)).getRed()) {
				neighbors.add(new int[] { j - 1, i - 1 });
			}
		}
		return neighbors;
	}

	// bufferedimages
	static String[] thinImage() {
		boolean firstStep = false;
		boolean hasChanged;

		do {
			hasChanged = false;
			firstStep = !firstStep;

			for (int r = 1; r < grid.length - 1; r++) {
				for (int c = 1; c < grid[0].length - 1; c++) {

					if (grid[r][c] != '#')
						continue;

					int nn = numNeighbors(r, c);
					if (nn < 2 || nn > 6)
						continue;

					if (numTransitions(r, c) != 1)
						continue;

					if (!atLeastOneIsWhite(r, c, firstStep ? 0 : 1))
						continue;

					toWhite.add(new Point(c, r));
					hasChanged = true;
				}
			}

			for (Point p : toWhite)
				grid[p.y][p.x] = ' ';
			toWhite.clear();

		} while (firstStep || hasChanged);

		return assignResult();

	}

	static int numNeighbors(int r, int c) {
		int count = 0;
		for (int i = 0; i < nbrs.length - 1; i++)
			if (grid[r + nbrs[i][1]][c + nbrs[i][0]] == '#')
				count++;
		return count;
	}

	static int numTransitions(int r, int c) {
		int count = 0;
		for (int i = 0; i < nbrs.length - 1; i++)
			if (grid[r + nbrs[i][1]][c + nbrs[i][0]] == ' ') {
				if (grid[r + nbrs[i + 1][1]][c + nbrs[i + 1][0]] == '#')
					count++;
			}
		return count;
	}

	static boolean atLeastOneIsWhite(int r, int c, int step) {
		int count = 0;
		int[][] group = nbrGroups[step];
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < group[i].length; j++) {
				int[] nbr = nbrs[group[i][j]];
				if (grid[r + nbr[1]][c + nbr[0]] == ' ') {
					count++;
					break;
				}
			}
		return count > 1;
	}

	static String[] assignResult() {
		String[] tempImg = new String[grid.length];
		imga2 = new BufferedImage(grid[0].length, grid.length, BufferedImage.TYPE_BYTE_GRAY);
		int i = 0;
		for (char[] row : grid)
			tempImg[i++] = new String(row);

		return tempImg;

	}

	public static void main(String[] args) {

		// initialize
		JFrame frame = new JFrame("Picture Editor");
		JPanel panel = new JPanel();
		JTextField j0 = new JTextField();
		JTextField j1 = new JTextField();
		JTextField j2 = new JTextField();
		JTextField j3 = new JTextField();
		JTextField j4 = new JTextField();
		JTextField j5 = new JTextField();
		JTextField j6 = new JTextField();
		JTextField j7 = new JTextField();
		JTextField j8 = new JTextField();
		JPanel conv_flow = new JPanel();
		conv_flow.setLayout(new FlowLayout());
		conv_flow.setSize(200, 150);
		JPanel thin_flow = new JPanel();
		thin_flow.setLayout(new FlowLayout());
		thin_flow.setSize(100, 150);
		JPanel filter_flow = new JPanel();
		filter_flow.setLayout(new FlowLayout());
		filter_flow.setSize(100, 150);
		JPanel scale_flow = new JPanel();
		scale_flow.setLayout(new FlowLayout());
		scale_flow.setSize(100, 150);
		JPanel panel2 = new JPanel();
		panel2.setLocation(150, 250);
		JTextField c0 = new JTextField();
		JTextField c1 = new JTextField();
		JTextField c2 = new JTextField();
		panel2.setLayout(new GridLayout(3, 3));
		panel2.setSize(150, 150);
		j0.setPreferredSize(new Dimension(50, 50));
		panel.setSize(300, 300);
		JLabel lblX = new JLabel("X");
		JLabel lblY = new JLabel("Y");
		lblX.setBounds(274, 72, 11, 14);
		lblY.setBounds(274, 106, 11, 14);
		JFrame filterFrame = new JFrame("Filter transform");
		JFrame convolutionFrame = new JFrame("Convolution");
		JFrame thinFrame = new JFrame("Thinning");
		JFrame imgaFrame = new JFrame("Main image");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(450, 600);
		JFrame symbolFrame = new JFrame("Resized Symbols");
		JButton analyzeButton = new JButton("Analyze");
		analyzeButton.setBounds(74, 102, 109, 23);
		JButton scaleButton = new JButton("Scale Symbols");
		scaleButton.setBounds(250, 137, 150, 23);
		JButton applyFilterButton = new JButton("Apply Filter");
		applyFilterButton.setBounds(160, 411, 127, 23);
		JButton browseImageButton = new JButton("Browse");
		browseImageButton.setBounds(74, 68, 100, 23);
		JTextField symbolX = new JTextField();
		symbolX.setBounds(295, 67, 50, 25);
		JTextField symbolY = new JTextField();
		symbolY.setBounds(295, 101, 50, 25);
		symbolX.setPreferredSize(new Dimension(50, 25));
		symbolY.setPreferredSize(new Dimension(50, 25));
		JButton convolutionButton = new JButton("Convolution");
		convolutionButton.setBounds(74, 136, 120, 23);
		JButton thinButton = new JButton("Thin");
		thinButton.setBounds(74, 166, 100, 23);
		JButton currentImageButton = new JButton("Display");
		currentImageButton.setBounds(74, 196, 100, 23);
		;
		JButton h_conv = new JButton("Horizontal");
		JButton v_conv = new JButton("Vertical");
		JButton conv_applyButton = new JButton("Apply to Original");
		JButton filter_applyButton = new JButton("Apply to Original");
		JButton thin_applyButton = new JButton("Apply to Original");
		JButton scale_applyButton = new JButton("Apply to Original");
		conv_flow.add(c0);
		conv_flow.add(c1);
		conv_flow.add(c2);
		c0.setPreferredSize(new Dimension(50, 50));
		c1.setPreferredSize(new Dimension(50, 50));
		c2.setPreferredSize(new Dimension(50, 50));
		conv_flow.add(h_conv);
		conv_flow.add(v_conv);
		conv_flow.add(conv_applyButton);
		conv_flow.setOpaque(false);
		filter_flow.add(filter_applyButton);
		filter_flow.setBounds(150, 0, 100, 23);
		filter_flow.setOpaque(false);
		thin_flow.add(thin_applyButton);
		thin_flow.setOpaque(false);
		scale_flow.add(scale_applyButton);
		scale_flow.setOpaque(false);
		JLabel convCanvas = new JLabel();
		JLabel symbolCanvas = new JLabel();
		JLabel afterCanvas = new JLabel();
		JLabel filterCanvas = new JLabel();
		JLabel thinCanvas = new JLabel();
		JLabel imgaCanvas = new JLabel();
		convolutionFrame.getContentPane().add(conv_flow);
		thinFrame.getContentPane().add(thin_flow);
		filterFrame.getContentPane().add(filter_flow);
		symbolFrame.getContentPane().add(scale_flow);
		panel.setLayout(null);
		panel.add(browseImageButton);
		panel.add(analyzeButton);
		panel.add(symbolX);
		panel.add(symbolY);
		panel.add(scaleButton);
		panel.add(convolutionButton);
		panel.add(applyFilterButton);
		panel.add(thinButton);
		panel.add(currentImageButton);
		panel.add(lblX);
		panel.add(lblY);
		panel2.add(j0);
		panel2.add(j1);
		panel2.add(j2);
		panel2.add(j3);
		panel2.add(j4);
		panel2.add(j5);
		panel2.add(j6);
		panel2.add(j7);
		panel2.add(j8);
		panel.add(panel2);
		frame.getContentPane().add(panel);
		frame.setVisible(true);

		// don't allow scale button to be used until symbol has been analyzed
		scaleButton.setEnabled(false);

		// browse image file
		browseImageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				fc.setDialogTitle("Select an image");
				fc.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "png", "jpg", "jpeg");
				fc.addChoosableFileFilter(filter);
				fc.showOpenDialog(null);
				File file = fc.getSelectedFile();
				try {
					imga = ImageIO.read(file);
				} catch (IOException err) {
				}

				imga2 = null;
				imga3 = null;
				imga4 = null;
				imga5 = null;
				visited = null;
				toWhite = new ArrayList<>();
				grid = null;

			}
		});

		currentImageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				imgaFrame.getContentPane().add(imgaCanvas);
				imgaCanvas.setIcon(new ImageIcon(imga));
				imgaCanvas.setBounds(0, 105, imga.getWidth(), imga.getHeight());
				imgaFrame.setVisible(true);
				imgaFrame.pack();
			}

		});

		applyFilterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				double[] operator = new double[9];
				// process inputs, add them to array
				if (j0.getText().contains("/")) {
					String[] temp = j0.getText().split("/");
					operator[0] = (Double.parseDouble(temp[0]) / Double.parseDouble(temp[1]));
				} else {
					operator[0] = Double.parseDouble(j0.getText());
				}
				if (j1.getText().contains("/")) {
					String[] temp = j1.getText().split("/");
					operator[1] = (Double.parseDouble(temp[0]) / Double.parseDouble(temp[1]));
				} else {
					operator[1] = Double.parseDouble(j1.getText());
				}
				if (j2.getText().contains("/")) {
					String[] temp = j2.getText().split("/");
					operator[2] = (Double.parseDouble(temp[0]) / Double.parseDouble(temp[1]));
				} else {
					operator[2] = Double.parseDouble(j2.getText());
				}
				if (j3.getText().contains("/")) {
					String[] temp = j3.getText().split("/");
					operator[3] = (Double.parseDouble(temp[0]) / Double.parseDouble(temp[1]));
				} else {
					operator[3] = Double.parseDouble(j3.getText());
				}
				if (j4.getText().contains("/")) {
					String[] temp = j4.getText().split("/");
					operator[4] = (Double.parseDouble(temp[0]) / Double.parseDouble(temp[1]));
				} else {
					operator[4] = Double.parseDouble(j4.getText());
				}
				if (j5.getText().contains("/")) {
					String[] temp = j5.getText().split("/");
					operator[5] = (Double.parseDouble(temp[0]) / Double.parseDouble(temp[1]));
				} else {
					operator[5] = Double.parseDouble(j5.getText());
				}
				if (j6.getText().contains("/")) {
					String[] temp = j6.getText().split("/");
					operator[6] = (Double.parseDouble(temp[0]) / Double.parseDouble(temp[1]));
				} else {
					operator[6] = Double.parseDouble(j6.getText());
				}
				if (j7.getText().contains("/")) {
					String[] temp = j7.getText().split("/");
					operator[7] = (Double.parseDouble(temp[0]) / Double.parseDouble(temp[1]));
				} else {
					operator[7] = Double.parseDouble(j7.getText());
				}
				if (j8.getText().contains("/")) {
					String[] temp = j8.getText().split("/");
					operator[8] = (Double.parseDouble(temp[0]) / Double.parseDouble(temp[1]));
				} else {
					operator[8] = Double.parseDouble(j8.getText());
				}

				imga2 = new BufferedImage(imga.getWidth(), imga.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
				for (int i = 0; i < imga.getHeight(); i++) {
					for (int j = 0; j < imga.getWidth(); j++) {
						double color;
						Color x = new Color(imga.getRGB(j, i));

						if (i == 0 && j == 0) { // top left corner
							color = operator[0] * new Color(imga.getRGB(j, i)).getRed()
									+ operator[1] * new Color(imga.getRGB(j, i)).getRed()
									+ operator[2] * new Color(imga.getRGB(j + 1, i)).getRed()
									+ operator[3] * new Color(imga.getRGB(j, i)).getRed()
									+ operator[4] * new Color(imga.getRGB(j, i)).getRed()
									+ operator[5] * new Color(imga.getRGB(j + 1, i)).getRed()
									+ operator[6] * new Color(imga.getRGB(j, i + 1)).getRed()
									+ operator[7] * new Color(imga.getRGB(j, i + 1)).getRed()
									+ operator[8] * new Color(imga.getRGB(j + 1, i + 1)).getRed();
						} else if (i == 0 && j == imga.getWidth() - 1) { // top right corner
							color = operator[0] * new Color(imga.getRGB(j - 1, i)).getRed()
									+ operator[1] * new Color(imga.getRGB(j, i)).getRed()
									+ operator[2] * new Color(imga.getRGB(j, i)).getRed()
									+ operator[3] * new Color(imga.getRGB(j - 1, i)).getRed()
									+ operator[4] * new Color(imga.getRGB(j, i)).getRed()
									+ operator[5] * new Color(imga.getRGB(j, i)).getRed()
									+ operator[6] * new Color(imga.getRGB(j - 1, i + 1)).getRed()
									+ operator[7] * new Color(imga.getRGB(j, i + 1)).getRed()
									+ operator[8] * new Color(imga.getRGB(j, i + 1)).getRed();
						} else if (j == 0 && i == imga.getHeight() - 1) { // bottom left corner
							color = operator[0] * new Color(imga.getRGB(j, i - 1)).getRed()
									+ operator[1] * new Color(imga.getRGB(j, i - 1)).getRed()
									+ operator[2] * new Color(imga.getRGB(j + 1, i - 1)).getRed()
									+ operator[3] * new Color(imga.getRGB(j, i)).getRed()
									+ operator[4] * new Color(imga.getRGB(j, i)).getRed()
									+ operator[5] * new Color(imga.getRGB(j + 1, i)).getRed()
									+ operator[6] * new Color(imga.getRGB(j, i)).getRed()
									+ operator[7] * new Color(imga.getRGB(j, i)).getRed()
									+ operator[8] * new Color(imga.getRGB(j + 1, i)).getRed();
						} else if (j == imga.getWidth() - 1 && i == imga.getHeight() - 1) {// bottom right corner
							color = operator[0] * new Color(imga.getRGB(j - 1, i - 1)).getRed()
									+ operator[1] * new Color(imga.getRGB(j, i - 1)).getRed()
									+ operator[2] * new Color(imga.getRGB(j, i - 1)).getRed()
									+ operator[3] * new Color(imga.getRGB(j - 1, i)).getRed()
									+ operator[4] * new Color(imga.getRGB(j, i)).getRed()
									+ operator[5] * new Color(imga.getRGB(j, i)).getRed()
									+ operator[6] * new Color(imga.getRGB(j - 1, i)).getRed()
									+ operator[7] * new Color(imga.getRGB(j, i)).getRed()
									+ operator[8] * new Color(imga.getRGB(j, i)).getRed();
						} else if (i == 0) {// first row that is not a corner
							color = operator[0] * new Color(imga.getRGB(j - 1, i)).getRed()
									+ operator[1] * new Color(imga.getRGB(j, i)).getRed()
									+ operator[2] * new Color(imga.getRGB(j + 1, i)).getRed()
									+ operator[3] * new Color(imga.getRGB(j - 1, i)).getRed()
									+ operator[4] * new Color(imga.getRGB(j, i)).getRed()
									+ operator[5] * new Color(imga.getRGB(j + 1, i)).getRed()
									+ operator[6] * new Color(imga.getRGB(j - 1, i + 1)).getRed()
									+ operator[7] * new Color(imga.getRGB(j, i + 1)).getRed()
									+ operator[8] * new Color(imga.getRGB(j + 1, i + 1)).getRed();
						} else if (j == 0) {// first column that is not a corner
							color = operator[0] * new Color(imga.getRGB(j, i - 1)).getRed()
									+ operator[1] * new Color(imga.getRGB(j, i - 1)).getRed()
									+ operator[2] * new Color(imga.getRGB(j + 1, i - 1)).getRed()
									+ operator[3] * new Color(imga.getRGB(j, i)).getRed()
									+ operator[4] * new Color(imga.getRGB(j, i)).getRed()
									+ operator[5] * new Color(imga.getRGB(j + 1, i)).getRed()
									+ operator[6] * new Color(imga.getRGB(j, i + 1)).getRed()
									+ operator[7] * new Color(imga.getRGB(j, i + 1)).getRed()
									+ operator[8] * new Color(imga.getRGB(j + 1, i + 1)).getRed();
						} else if (i == imga.getHeight() - 1) {// last row that is not a corner
							color = operator[0] * new Color(imga.getRGB(j - 1, i - 1)).getRed()
									+ operator[1] * new Color(imga.getRGB(j, i - 1)).getRed()
									+ operator[2] * new Color(imga.getRGB(j + 1, i - 1)).getRed()
									+ operator[3] * new Color(imga.getRGB(j - 1, i)).getRed()
									+ operator[4] * new Color(imga.getRGB(j, i)).getRed()
									+ operator[5] * new Color(imga.getRGB(j + 1, i)).getRed()
									+ operator[6] * new Color(imga.getRGB(j - 1, i)).getRed()
									+ operator[7] * new Color(imga.getRGB(j, i)).getRed()
									+ operator[8] * new Color(imga.getRGB(j + 1, i)).getRed();
						} else if (j == imga.getWidth() - 1) {// last column that is not a corner
							color = operator[0] * new Color(imga.getRGB(j - 1, i - 1)).getRed()
									+ operator[1] * new Color(imga.getRGB(j, i - 1)).getRed()
									+ operator[2] * new Color(imga.getRGB(j, i - 1)).getRed()
									+ operator[3] * new Color(imga.getRGB(j - 1, i)).getRed()
									+ operator[4] * new Color(imga.getRGB(j, i)).getRed()
									+ operator[5] * new Color(imga.getRGB(j, i)).getRed()
									+ operator[6] * new Color(imga.getRGB(j - 1, i + 1)).getRed()
									+ operator[7] * new Color(imga.getRGB(j, i + 1)).getRed()
									+ operator[8] * new Color(imga.getRGB(j, i + 1)).getRed();
						} else {// elements where the 8 surrounding elements are all in the image (non edge
								// elements)
							color = operator[0] * new Color(imga.getRGB(j - 1, i - 1)).getRed()
									+ operator[1] * new Color(imga.getRGB(j, i - 1)).getRed()
									+ operator[2] * new Color(imga.getRGB(j + 1, i - 1)).getRed()
									+ operator[3] * new Color(imga.getRGB(j - 1, i)).getRed()
									+ operator[4] * new Color(imga.getRGB(j, i)).getRed()
									+ operator[5] * new Color(imga.getRGB(j + 1, i)).getRed()
									+ operator[6] * new Color(imga.getRGB(j - 1, i + 1)).getRed()
									+ operator[7] * new Color(imga.getRGB(j, i + 1)).getRed()
									+ operator[8] * new Color(imga.getRGB(j + 1, i + 1)).getRed();
						}
						if (color < 0) {
							color = 0;
						} else if (color > 255) {
							color = 255;
						}
						x = new Color((int) color, (int) color, (int) color);

						imga2.setRGB(j, i, x.getRGB());
					}
				}
				JTabbedPane tPane1 = new JTabbedPane();

				filterCanvas.setIcon(new ImageIcon(imga));
				afterCanvas.setIcon(new ImageIcon(imga2));
				filterFrame.getContentPane().add(tPane1);
				afterCanvas.setPreferredSize(new Dimension(imga.getWidth(), imga.getHeight()));
				filterCanvas.setPreferredSize(new Dimension(imga2.getWidth(), imga2.getHeight()));
				tPane1.addTab("Before", filterCanvas);
				tPane1.addTab("After", afterCanvas);
				filterFrame.setVisible(true);
				filterFrame.pack();
			}
		});

		// sets pixels to black or white depending on what value they are
		analyzeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainList.reset();
				bwCanvas = new JLabel();
				analyzedCanvas = new JLabel();
				analyzeFrame = new JFrame("Before and After");
				visited = new int[imga.getWidth()][imga.getHeight()];

				// visited is initialized to all 255 (white), 255 assumed as unvisited
				for (int k = 0; k < visited.length; k++) {
					for (int l = 0; l < visited[k].length; l++) {
						visited[k][l] = 255;
					}
				}
				imga2 = new BufferedImage(imga.getWidth(), imga.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

				for (int i = 0; i < imga.getHeight(); i++) {
					for (int j = 0; j < imga.getWidth(); j++) {
						int color;
						Color x = new Color(imga.getRGB(j, i));
						if (x.getRed() > 127) {
							color = 255;
						} else {
							color = 0;
						}
						x = new Color(color, color, color);

						imga2.setRGB(j, i, x.getRGB());
					}
				}
				JTabbedPane tPane = new JTabbedPane();
				bwCanvas.setIcon(new ImageIcon(imga2));
				bwCanvas.setPreferredSize(new Dimension(imga2.getWidth(), imga2.getHeight()));
				tPane.addTab("B/W", bwCanvas);
				analyzeFrame.getContentPane().add(tPane);
				analyzeFrame.setVisible(true);

				// IMGA3 IS SET TO IMAGE WHERE THE BLOBS ARE COLORED BASED ON THE NUMBER OF
				// CONNECT COMPONENTS, MORE COMPONENTS = DARKER
				imga3 = new BufferedImage(imga.getWidth(), imga.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
				for (int i = 0; i < imga2.getHeight(); i++) {
					for (int j = 0; j < imga2.getWidth(); j++) {
						int color = 255;
						Color x = new Color(imga2.getRGB(j, i));

						// if the pixel is black, and has not been visited
						if (x.getRed() == 0 && visited[j][i] == 255) {
							color = symbol(i, j, imga2);

							// go through visited array, normalize the values, subtract the # of connect
							// components from 255, (more connected = darker = lower value)
							ArrayList<int[]> tempArrayList = new ArrayList<int[]>();
							for (int k = 0; k < visited.length; k++) {
								for (int l = 0; l < visited[k].length; l++) {
									if (visited[k][l] == -1) {
										// add coordinates of the pixels in this blob to temp arraylist
										if (color > 255) {
											visited[k][l] = 0;
										} else {
											tempArrayList.add(new int[] { k, l });
											visited[k][l] = 255 - color;
										}

									}
								}
							}
							// add coordinates of this blob to main arraylist
							mainList.blobs.add(tempArrayList);
						}

					}
				}

				// go through visited and assign to picture
				for (int k = 0; k < visited.length; k++) {
					for (int l = 0; l < visited[k].length; l++) {
						Color x = new Color(visited[k][l], visited[k][l], visited[k][l]);
						imga3.setRGB(k, l, x.getRGB());
					}
				}

				scaleButton.setEnabled(true);

				/*
				 * //DRAW BLOBS TO SYMBOL PICTURE (COMMENT OUT ABOVE) Color x=new
				 * Color(255,255,255); for (int k=0;k<imga3.getWidth();k++) { for (int
				 * l=0;l<imga3.getHeight();l++) {
				 * 
				 * imga3.setRGB(k,l, x.getRGB()); } } x=new Color(0,0,0);
				 * 
				 * //print a specific blob for (int l=0;l<mainList.blobs.get(2).size();l++) {
				 * imga3.setRGB(mainList.blobs.get(2).get(l)[0],
				 * mainList.blobs.get(2).get(l)[1], x.getRGB()); }
				 * 
				 */

				/*
				 * //SCALE ALL SYMBOLS TO 9X9 AND PRINT TO PICTURE mainList.scaleBlobs(9, 9);
				 * 
				 * 
				 * //PRINT transformed BLOBS ON IMAGE IN ORIGINAL POSITIONS (transformed COORDS
				 * + LOWER BOUND) //INITIALIZE IMAGE TO ALL WHITE imga3=new
				 * BufferedImage(imga.getWidth(), imga.getHeight(),
				 * BufferedImage.TYPE_BYTE_GRAY); Color c=new Color(255,255,255); for (int
				 * k=0;k<imga3.getWidth();k++) { for (int l=0;l<imga3.getHeight();l++) {
				 * imga3.setRGB(k,l, c.getRGB()); } } //PRINT transformed BLOB TO THEIR ORIGINAL
				 * POSITION for (int j=0;j<mainList.transformed.size();j++) { for (int
				 * k=0;k<mainList.transformed.get(j).getHeight();k++) { for (int
				 * l=0;l<mainList.transformed.get(j).getWidth();l++) { int
				 * x=mainList.transformed.get(j).getRGB(l, k);
				 * imga3.setRGB(l+mainList.bounds.get(j)[0], k+mainList.bounds.get(j)[1], x); }
				 * 
				 * } }
				 */

				analyzedCanvas.setIcon(new ImageIcon(imga3));
				analyzedCanvas.setPreferredSize(new Dimension(imga3.getWidth(), imga3.getHeight()));
				tPane.addTab("Symbol", analyzedCanvas);
				analyzeFrame.pack();

			}
		});
		convolutionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				imga5 = imga;
				convolutionFrame.getContentPane().add(convCanvas);
				convCanvas.setIcon(new ImageIcon(imga5));
				convCanvas.setBounds(0, 105, imga5.getWidth(), imga5.getHeight());
				convolutionFrame.setVisible(true);
				convolutionFrame.pack();
			}

		});

		conv_applyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				imga = imga5;
				imgaCanvas.setIcon(new ImageIcon(imga));
			}

		});
		scale_applyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				imga = imga3;
				imgaCanvas.setIcon(new ImageIcon(imga));
			}

		});
		filter_applyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				imga = imga2;
				imgaCanvas.setIcon(new ImageIcon(imga));
			}

		});
		thin_applyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				imga = imga4;
				imgaCanvas.setIcon(new ImageIcon(imga));
			}

		});

		thinButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] tempImg = new String[imga2.getHeight()];
				for (int i = 0; i < imga2.getHeight(); i++) {
					String tempString = "";
					for (int j = 0; j < imga2.getWidth(); j++) {
						if (new Color(imga2.getRGB(j, i)).getRed() == 0) {
							tempString += "#";
						} else {
							tempString += " ";
						}
					}
					tempImg[i] = tempString;
				}

				grid = new char[tempImg.length][];
				for (int r = 0; r < tempImg.length; r++)
					grid[r] = tempImg[r].toCharArray();

				tempImg = thinImage();

				imga4 = new BufferedImage(imga2.getWidth(), imga2.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
				for (int i = 0; i < tempImg.length; i++) {
					String tempString = tempImg[i];
					for (int j = 0; j < tempString.length(); j++) {
						if (tempString.charAt(j) == '#') {
							Color c = new Color(0, 0, 0);
							imga4.setRGB(j, i, c.getRGB());
						} else {
							Color c = new Color(255, 255, 255);
							imga4.setRGB(j, i, c.getRGB());
						}
					}
					tempImg[i] = tempString;
				}
				thinFrame.getContentPane().add(thinCanvas);
				thinCanvas.setIcon(new ImageIcon(imga4));
				thinCanvas.setPreferredSize(new Dimension(imga4.getWidth(), imga4.getHeight()));
				thinFrame.setVisible(true);
				thinFrame.pack();
			}

		});

		// SCALES ALL SYMBOLS TO A CERTAIN DIMENSION, PRINTS ALL ONTO ORIGINAL PIC
		scaleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int x_val = Integer.parseInt(symbolX.getText());
				int y_val = Integer.parseInt(symbolY.getText());
				mainList.calcBounds(x_val, y_val);
				mainList.normBlobs();

				if (mainList.transformed.get(0).getHeight() != y_val
						&& mainList.transformed.get(0).getHeight() != x_val) {
					mainList.scaleBlobs(y_val, x_val);
				}
				// PRINT TRANSFORMED BLOBS ON IMAGE IN ORIGINAL POSITIONS (transformed COORDS +
				// LOWER BOUND)
				// INITIALIZE IMAGE TO ALL WHITE
				imga3 = new BufferedImage(imga.getWidth(), imga.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
				Color c = new Color(255, 255, 255);
				for (int k = 0; k < imga3.getWidth(); k++) {
					for (int l = 0; l < imga3.getHeight(); l++) {
						imga3.setRGB(k, l, c.getRGB());
					}
				}
				// PRINT TRANSFORMED BLOB TO THEIR ORIGINAL POSITION
				for (int j = 0; j < mainList.transformed.size(); j++) {
					for (int k = 0; k < mainList.transformed.get(j).getHeight(); k++) {
						for (int l = 0; l < mainList.transformed.get(j).getWidth(); l++) {
							int x = mainList.transformed.get(j).getRGB(l, k);
							imga3.setRGB(l + mainList.bounds.get(j)[0], k + mainList.bounds.get(j)[1], x);
						}

					}
				}

				symbolFrame.getContentPane().add(symbolCanvas);
				symbolCanvas.setIcon(new ImageIcon(imga3));

				symbolCanvas.setPreferredSize(new Dimension(imga3.getWidth(), imga3.getHeight()));
				symbolFrame.pack();
				symbolFrame.setVisible(true);

				// if all symbols are 9 by 9, get their vectors.
				// middle points: 11,14,17,41,44,47,71,74,77

				if (Integer.parseInt(symbolY.getText()) == 9 && Integer.parseInt(symbolX.getText()) == 9) {
					int dialogResult = JOptionPane.showConfirmDialog(null, "Would you like to save ratios to a file?",
							"Save", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (dialogResult == JOptionPane.YES_OPTION) {
						double[][] vectorList;
						vectorList = mainList.getVectors();
						BufferedWriter outputWriter = null;
						try {
							outputWriter = new BufferedWriter(new FileWriter("output.txt", false));
							for (int i = 0; i < vectorList.length; i++) {
								for (int j = 0; j < vectorList[i].length; j++) {
									outputWriter.write(vectorList[i][j] + ",");
								}
								outputWriter.write("\n");
							}
							outputWriter.flush();
							outputWriter.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					dialogResult = JOptionPane.showConfirmDialog(null,
							"Would you like to compare distance to vectors in output.txt?", "Compare",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (dialogResult == JOptionPane.YES_OPTION) {
						try {
							BufferedReader br = new BufferedReader(new FileReader("output.txt"));
							String st;
							double[][] vectorList = mainList.getVectors();
							double[][] savedList = new double[10][9];
							int ind = 0;
							while ((st = br.readLine()) != null) {
								String[] temp = st.split(",");
								for (int i = 0; i < temp.length; i++) {
									savedList[ind][i] = Double.parseDouble(temp[i]);
								}
								ind++;
							}

							String s = "";
							// euclidean distances
							for (int i = 0; i < vectorList.length; i++) {
								double min_e = 99;
								double min_i = 0;
								for (int j = 0; j < savedList.length; j++) {
									double euc_dist = 0;
									for (int k = 0; k < 9; k++) {
										euc_dist += Math.pow((savedList[i][k] - vectorList[j][k]), 2);
									}
									euc_dist = Math.sqrt(euc_dist);

									if (euc_dist < min_e) {
										min_e = euc_dist;
										min_i = j;

									}
								}

								s += ("unknown symbol "+i+ ": " + "min:" + min_e + ",closest to symbol " + min_i + "\n");
							}
							JOptionPane.showMessageDialog(null, s);
							br.close();
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}

				}

			}

		});

		h_conv.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				imga2 = new BufferedImage(imga5.getWidth() + 2, imga5.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
				double[] operator = new double[3];

				// populate the operator
				if (c0.getText().contains("/")) {
					String[] temp = c0.getText().split("/");
					operator[0] = (Double.parseDouble(temp[0]) / Double.parseDouble(temp[1]));
				} else {
					operator[0] = Double.parseDouble(c0.getText());
				}
				if (c1.getText().contains("/")) {
					String[] temp = c1.getText().split("/");
					operator[1] = (Double.parseDouble(temp[0]) / Double.parseDouble(temp[1]));
				} else {
					operator[1] = Double.parseDouble(c1.getText());
				}
				if (c2.getText().contains("/")) {
					String[] temp = c2.getText().split("/");
					operator[2] = (Double.parseDouble(temp[0]) / Double.parseDouble(temp[1]));
				} else {
					operator[2] = Double.parseDouble(c2.getText());
				}

				/*
				 * 2nd col: VC[1]*matrix[j][i-1]+VC[2]*matrix[j][i] 3rd col to last:
				 * VC[0]*matrix[j][i-2]+VC[1]*matrix[j][i-1]+VC[2]*matrix[j][i] last+1:
				 * VC[0]*matrix[j][i-1]+VC[1]*matrix[j][i] last+2: VC[0]*matrix[j][i]
				 */
				for (int i = 0; i < imga2.getHeight(); i++) {
					for (int j = 0; j < imga2.getWidth(); j++) {

						Color x;
						double color = 0;

						if (j == 0) {
							color = operator[2] * new Color(imga5.getRGB(j, i)).getRed();
						} else if (j == 1) {
							color = operator[1] * new Color(imga5.getRGB(j - 1, i)).getRed()
									+ operator[2] * new Color(imga5.getRGB(j, i)).getRed();
							;
						} else if (j == imga5.getWidth()) {
							color = operator[0] * new Color(imga5.getRGB(j - 2, i)).getRed()
									+ operator[1] * new Color(imga5.getRGB(j - 1, i)).getRed();
						} else if (j > imga5.getWidth()) {
							color = operator[0] * new Color(imga5.getRGB(j - 2, i)).getRed();
						} else {
							color = operator[0] * new Color(imga5.getRGB(j - 2, i)).getRed()
									+ operator[1] * new Color(imga5.getRGB(j - 1, i)).getRed()
									+ operator[2] * new Color(imga5.getRGB(j, i)).getRed();
						}

						x = new Color((int) color, (int) color, (int) color);
						imga2.setRGB(j, i, x.getRGB());
					}
				}
				imga5 = imga2;

				convCanvas.setIcon(new ImageIcon(imga5));
				convCanvas.setPreferredSize(new Dimension(imga5.getWidth(), imga5.getHeight()));
				convolutionFrame.setVisible(true);
			}

		});
		v_conv.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				imga2 = new BufferedImage(imga5.getWidth(), imga5.getHeight() + 2, BufferedImage.TYPE_BYTE_GRAY);
				double[] operator = new double[3];
				// populate the operator
				if (c0.getText().contains("/")) {
					String[] temp = c0.getText().split("/");
					operator[0] = (Double.parseDouble(temp[0]) / Double.parseDouble(temp[1]));
				} else {
					operator[0] = Double.parseDouble(c0.getText());
				}
				if (c1.getText().contains("/")) {
					String[] temp = c1.getText().split("/");
					operator[1] = (Double.parseDouble(temp[0]) / Double.parseDouble(temp[1]));
				} else {
					operator[1] = Double.parseDouble(c1.getText());
				}
				if (c2.getText().contains("/")) {
					String[] temp = c2.getText().split("/");
					operator[2] = (Double.parseDouble(temp[0]) / Double.parseDouble(temp[1]));
				} else {
					operator[2] = Double.parseDouble(c2.getText());
				}

				/*
				 * 2nd col: VC[1]*matrix[j][i-1]+VC[2]*matrix[j][i] 3rd col to last:
				 * VC[0]*matrix[j][i-2]+VC[1]*matrix[j][i-1]+VC[2]*matrix[j][i] last+1:
				 * VC[0]*matrix[j][i-1]+VC[1]*matrix[j][i] last+2: VC[0]*matrix[j][i]
				 */
				for (int i = 0; i < imga2.getHeight(); i++) {
					for (int j = 0; j < imga2.getWidth(); j++) {

						Color x;
						double color = 0;

						if (i == 0) {
							color = operator[2] * new Color(imga5.getRGB(j, i)).getRed();
						} else if (i == 1) {
							color = operator[1] * new Color(imga5.getRGB(j, i - 1)).getRed()
									+ operator[2] * new Color(imga5.getRGB(j, i)).getRed();
							;
						} else if (i == imga5.getHeight()) {
							color = operator[0] * new Color(imga5.getRGB(j, i - 2)).getRed()
									+ operator[1] * new Color(imga5.getRGB(j, i - 1)).getRed();
						} else if (i > imga5.getHeight()) {
							color = operator[0] * new Color(imga5.getRGB(j, i - 2)).getRed();
						} else {
							color = operator[0] * new Color(imga5.getRGB(j, i - 2)).getRed()
									+ operator[1] * new Color(imga5.getRGB(j, i - 1)).getRed()
									+ operator[2] * new Color(imga5.getRGB(j, i)).getRed();
						}

						x = new Color((int) color, (int) color, (int) color);
						imga2.setRGB(j, i, x.getRGB());
					}
				}
				imga5 = imga2;

				convCanvas.setIcon(new ImageIcon(imga5));
				convCanvas.setPreferredSize(new Dimension(imga5.getWidth(), imga5.getHeight()));
				convolutionFrame.setVisible(true);
			}

		});

	}
}
