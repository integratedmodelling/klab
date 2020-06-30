package org.integratedmodelling.geoprocessing.algorithms.viewshed;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.swing.JPanel;

/**
 * A panel for displaying ViewshedMaps.
 * Options:
 *    single-click: clear all/reset display (also prints out data on point)
 * 	  double-click or 'v': toggle between source height map and viewshed heat map
 *    hold 'p' and single click: draw current perimeter (shows shape and size of last used setting)
 * 	  hold 'p' and drag mouse: set new perimeter size
 *    hold 'l' and drag line: draws rays, highlighting in light-blue the visible regions of the initial click
 *    hold 'm' and single click: highlights in blue the viewshed for that point
 *    hold 'm' and drag mouse: highlights viewshed of all points mouse touches
 * 
 * @author Alejandro Frias
 * @version July 2014
 */
public class KhMapPanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;
	static final int DEFAULT_WIDTH = 1920;
	static final int DEFAULT_HEIGHT = 1080;
	static final BasicStroke STROKE_DEFAULT = new BasicStroke(8f);
	static final Color VIEWSHED_COLOR = new Color(0,0,255,155);
	static final Color PERIMETER_COLOR = new Color(0,0,0,255);
	static final Color LINE_VISIBILE_COLOR = new Color(0,255,255,255);
	static final Color LINE_BLOCKED_COLOR = new Color(0,108,181,255);
	BufferedImage _buffer, _bufferPath;
	double _pixX, _pixY;
	int _mLastX, _mLastY, _mActionX, _mActionY;	
	ViewshedMap _map;
	boolean _display_map;
	boolean[][] _viewshed;
	boolean _draw_line = false;
	boolean _draw_viewshed = false;
	boolean _draw_perimeter = false;
	boolean[][] _line_visible;
	boolean[][] _line_blocked;
	boolean[][] _perimeter;
	Point _observer = new Point(0,0,0);

	public KhMapPanel(ViewshedMap map) throws IOException {
		super();
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		_map = map;
		_display_map = false;
		
		int h = map.getSourceHeightMap().h();
		int w = map.getSourceHeightMap().w();
		_viewshed = new boolean[h][w];
		_line_visible = new boolean[h][w];
		_line_blocked = new boolean[h][w];
		_perimeter = new boolean[h][w];

		resetDisplay();
		
		_buffer = new BufferedImage(DEFAULT_WIDTH, DEFAULT_HEIGHT, java.awt.image.BufferedImage.TYPE_INT_RGB);
		_bufferPath = new BufferedImage(DEFAULT_WIDTH, DEFAULT_HEIGHT, java.awt.image.BufferedImage.TYPE_INT_ARGB);
		
		update();
	}			

	
	public void update() {
		Graphics2D g = _buffer.createGraphics();
		WeightsMap map;
		if (_display_map) {
			 map = _map.getSourceHeightMap();
		} else {
			map = _map.getViewshedMap();
		}
		
		int mapW = map.w();
		int mapH = map.h();
		double pixX = DEFAULT_WIDTH / (double)mapW;
		double pixY = DEFAULT_HEIGHT / (double)mapH;
		for (int i = 0; i < mapH; i++) {
			for (int j = 0; j < mapW; j++) {
				if (_perimeter[i][j] && _draw_perimeter) {
					g.setColor(PERIMETER_COLOR);
			    } else if (_line_visible[i][j] && _draw_line) {
					g.setColor(LINE_VISIBILE_COLOR);
				} else if (_line_blocked[i][j] && _draw_line) {
					g.setColor(LINE_BLOCKED_COLOR);
				} else if(_viewshed[i][j] && _draw_viewshed) {
					g.setColor(VIEWSHED_COLOR);
				} else {
					int w = (int)(map.getWeight(j, i) / 4);
					//System.out.println(map.getWeight(j,  i));
					if (w > 255) w = 255;
					int gr = (int)((w > 128 ? 1-2*(w-128)/256.0 : 1.0) * 210);
					int rd = (int)((w > 128 ? 1.0 : 2*w/256.0) * 210);
					g.setColor(new Color(rd,gr,80,255));
				}
				g.fillRect((int)(j*pixX), (int)(i*pixY), (int)pixX+1, (int)pixY+1);

			}
		}
		g.dispose();
	}

	@Override
	public void paintComponent(Graphics gr) {		
		super.paintComponent(gr);
		Dimension sz = getSize();
		int mapW = _map.getViewshedMap().w();
		int mapH = _map.getViewshedMap().h();
		_pixX = sz.width / (double)mapW;
		_pixY = sz.height / (double)mapH;
		
		Graphics2D g = (Graphics2D)gr;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(_buffer, 0, 0, sz.width, sz.height, 0, 0, 
				DEFAULT_WIDTH, DEFAULT_HEIGHT, this);
		g.drawImage(_bufferPath, 0, 0, sz.width, sz.height, 0, 0, 
				DEFAULT_WIDTH, DEFAULT_HEIGHT, this);		
	}
	
    public void mousePressed(MouseEvent e) {
    	WeightsMap map = _map.getSourceHeightMap();
        int x =  e.getX() * map.w() / getWidth();
        int y = e.getY() * map.h() / getHeight();
        
        _observer = new Point(x, y, map.getSource(x, y) + _map.h());
    }
    
    public void mouseReleased(MouseEvent e) {

    }
    
    public void mouseEntered(MouseEvent e) {

    }
    
    public void mouseExited(MouseEvent e) {
    }
    
    public void mouseClicked(MouseEvent e) {
    	System.out.println("Clicks: " + e.getClickCount());
    	if (e.getClickCount() == 1) {
            System.out.println("Clicked mouse at pixel (" + e.getX() + ", " + e.getY() + ")");
            
            WeightsMap map = _map.getSourceHeightMap();
            int x =  e.getX() * map.w() / getWidth();
            int y = e.getY() * map.h() / getHeight();
            System.out.println("Which is cell (" + x + ", " + y + ")");
            System.out.println("Height (weighted): " + map.getWeight(x, y));
            System.out.println("Height (source): " + map.getSource(x, y));
            resetDisplay();
            update();
            repaint();
            
            if (_draw_viewshed) {
            	displayViewShed(x, y, _map.h(), _map.r());
            }
            
            if (_draw_perimeter) {
            	displayPerimeter(x, y, _map.r());
            }
            
            
            
    	} else if (e.getClickCount() == 2) {
    		switchView();
    	}
    }
    
    public void mouseMoved(MouseEvent e) {
    }
    
    public void mouseDragged(MouseEvent e) {
        System.out.println("Dragged mouse to pixel (" + e.getX() + ", " + e.getY() + ")");
        
        WeightsMap map = _map.getSourceHeightMap();
        int x =  e.getX() * map.w() / getWidth();
        int y = e.getY() * map.h() / getHeight();
        System.out.println("Which is cell (" + x + ", " + y + ")");
        System.out.println("Height (weighted): " + map.getWeight(x, y));
        System.out.println("Height (source): " + map.getSource(x, y));
        int radius = (int) Math.sqrt(Math.pow(_observer.x() - x, 2) + Math.pow(_observer.y() - y,  2));
        if (_draw_perimeter) {
        	displayPerimeter(_observer.x(), _observer.y(), radius);
        } else if (_draw_line) {
        	displayLine(_observer.x(), _observer.y(), x, y);
        } else if (_draw_viewshed) {
            displayViewShed(x, y, _map.h(), _map.r());
        }
        

    }
    
    public void keyPressed(KeyEvent e) {
    	
    	int key = e.getKeyCode();
    	//System.out.println("PRESSED A KEY!");
    	
    	switch(key) {
    	case KeyEvent.VK_L:
    		_draw_line = true;
    		break;
    	case KeyEvent.VK_M:
    		_draw_viewshed = true;
    		break;
    	case KeyEvent.VK_P:
    		_draw_perimeter = true;
    		break;
    	case KeyEvent.VK_C:
    		resetDisplay();
    		update();
    		repaint();
    		break;
    	default:
    		//System.out.println("KeyCode " + key + "Pressed");
    	}
    }
    
    public void keyReleased(KeyEvent e) {
    	int key = e.getKeyCode();
    	
    	switch(key) {
    	case KeyEvent.VK_L:
    		_draw_line = false;
    		break;
    	case KeyEvent.VK_M:
    		_draw_viewshed = false;
    		break;
    	case KeyEvent.VK_P:
    		_draw_perimeter = false;
    		break;
    	default:
    		System.out.println("KeyCode " + key + " Released");
    	}
    }
    
    public void keyTyped(KeyEvent e) {
    	int key = e.getKeyChar();
    	
    	switch(key) {
    	case 'v':
    		switchView();
    		break;
    	default:
    		//System.out.println("Key " + key + " Typed");
    	}
    }
    
    
    private void displayPerimeter(int x1, int y1, int radius) {
    	System.out.println("Perimeter with radius " + radius);
    	
    	List<Point> perim;
    	if(ViewshedMap.USE_CIRCULAR_PERIMETER) perim = _map.calcCircularPerimeter(x1, y1, radius); else perim = _map.calcPerimeter(x1, y1, radius);
    	for (Point p : perim) {
    		_perimeter[p.y()][p.x()] = true;
    	}
    	
    	update();
    	repaint();
    }
    
    private void displayLine(int x1, int y1, int x2, int y2) {
    	System.out.println("Line from " + x1 + ", " + y1 + " to (" + x2 + ", " + y2 + ")");
    	List<Point> line = _map.calcBrensenhamLine(x1, y1, x2, y2);
    	double maxSlope = Double.NEGATIVE_INFINITY;
    	for (Point p : line) {
    		double slope = _map.calcSlope(_observer, p);
    		if (slope >= maxSlope) {
    			_line_visible[p.y()][p.x()] = true;
    			maxSlope = slope;
    		} else {
    			_line_blocked[p.y()][p.x()] = true;
    		}
    	}
    	update();
    	repaint();
    }
    
    private void displayViewShed(int x, int y, int h, int r) {
        int count = 0;
        boolean[][] temp_viewshed = _map.calcFullViewshed(x, y, h, r);
        
        int[] corners = _map.calcCorners(x, y, r);
        int x1 = corners[0];
        int y1 = corners[2];
        
        for (int i = 0; i < temp_viewshed.length; i++) {
        	for (int j = 0; j < temp_viewshed[0].length; j++) {
        		if(temp_viewshed[i][j] == true) {
        			count++;
        			_viewshed[y1 + i][x1 + j] = true;
        		}
        	}
        }
        System.out.println("COUNT: " + count);
        
        update();
        repaint();
    }
    
    private void switchView() {
		if (_display_map) {
			_display_map = false;
		} else {
			_display_map = true;
		}
		update();
		repaint();
    }
    
    private void resetDisplay() {
		for (int i = 0; i < _viewshed.length; i++) {
			for (int j = 0; j < _viewshed[0].length; j++) {
				_viewshed[i][j] = false;
				_line_visible[i][j] = false;
				_line_blocked[i][j] = false;
				_perimeter[i][j] = false;
			}
		}
    }
    
    
}
