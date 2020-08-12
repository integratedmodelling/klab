package org.integratedmodelling.geoprocessing.algorithms.viewshed;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Test the ViewshedMap and display it. Prints out performance and settings at start.
 * 
 * @author Alejandro Frias
 * @version July 2014
 */
public class KhTest extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int FRAME_WIDTH = 800;
	static final String TITLE = "Square";
	
	public static void main(String[] args) throws Exception, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {		
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new KhTest(TITLE);
	}
	
	public KhTest(String title) throws Exception {
		super(title);
		
		String file = "C:\\test.dtm";
		System.out.println("Loading "+file);
		long start = System.currentTimeMillis();
		WeightsMap m = new WeightsMap(file);
		long stop = System.currentTimeMillis();
		System.out.println("Total time to load: " + (stop - start));
		System.out.println("Number of points: " + (m.h() * m.w()) );
		System.out.println("Average Load Time per Point: " + (double)(stop - start)/(double)(m.h() * m.w()) + " milliseconds");
		
		
		System.out.println("Width:\t" + m.w());
		System.out.println("Height:\t" + m.h());
		/**/
		int r = 5;
		int h = 20;
		System.out.println("Calculating veiwshed map with radius " + r + " and height " + h);
		System.out.println("Using " + TITLE + " perimeters.");
		
		start = System.currentTimeMillis();
		ViewshedMap vsm = new ViewshedMap(m, r, h);
		stop = System.currentTimeMillis();
		
		System.out.println("Total time: " + (stop - start));
		System.out.println("Number of points: " + (m.h() * m.w()) );
		System.out.println("Average Time per Point: " + (double)(stop - start)/(double)(m.h() * m.w()) + " milliseconds");
		
		KhMapPanel panel = new KhMapPanel(vsm);
		
		double ratio =  (double) vsm.height() / (double)vsm.width();
		panel.setPreferredSize(new Dimension(FRAME_WIDTH, (int) (FRAME_WIDTH * ratio)));
		panel.setFocusable(true);
		panel.requestFocusInWindow();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		Container main = getContentPane();
		main.setLayout(new BorderLayout());
		main.add(panel, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
