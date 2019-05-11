package org.integratedmodelling.klab.ide.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.utils.StringUtil;

public class Badge extends Canvas {

    boolean                 mouseIn      = false;

    int                     LEFT_MARGIN  = 5;
    int                     RIGHT_MARGIN = 24;
    int                     TOP_MARGIN   = 2;

    public static final int CLOSEABLE    = 0x01;
    public static final int SELECTABLE   = 0x02;
    public static final int ROUNDED      = 0x04;
    public static final int MULTILINE    = 0x08;

    boolean                 closeable    = false;
    boolean                 selectable   = false;
    boolean                 multiline    = false;
    boolean                 rounded      = false;
    boolean                 selected     = false;
    Color                   backColor;

    private Rectangle       closeBox;
    private CLabel          textLabel;

    private List<Control>   widgets      = new ArrayList<>();

	private int containerWidth;

    public Badge(Composite parent, int behavior, int style, int width) {

    	super(parent, style);
    	
    	this.containerWidth = width;
        this.closeable = (behavior & CLOSEABLE) != 0;
        this.selectable = (behavior & SELECTABLE) != 0;
        this.multiline = (behavior & MULTILINE) != 0;
        this.rounded = (behavior & ROUNDED) != 0;
        setLayout(null);

        if (this.rounded) {
            setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        }

        addPaintListener(new PaintListener() {
            @Override
            public void paintControl(PaintEvent e) {
                paint(e.gc);
            }
        });

        addMouseListener(new ML(true));
        addMouseTrackListener(new MTL());

    }

    @Override
    public void setBackground(Color color) {
        backColor = color;
        if (rounded) {
            super.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        } else {
            super.setBackground(color);
        }
        if (textLabel != null) {
            textLabel.setBackground(color);
        }
    }

    /**
     * Reimplement this do-nothing to perform any close actions.
     */
    protected void close() {
    }

    /**
     * Reimplement this do-nothing to perform any selection actions.
     */
    protected void select(boolean selected) {
    }

    private void paint(GC gc) {

        gc.setAntialias(SWT.ON);

        if (backColor == null) {
            backColor = rounded ? SWTResourceManager.getColor(SWT.COLOR_WHITE) : getBackground();
        }

        for (Control w : widgets) {
            w.setBackground(backColor);
        }

        Point size = this.getSize();

        if (rounded) {
            gc.setBackground(backColor);
            gc.fillRoundRectangle(0, 0, size.x, size.y, 16, 16);
        }

        if (closeable) {
            int x = size.x - 12 - ((rounded && multiline) ? 2 : 0);
            int y = multiline ? (rounded ? 4 : 2) : (size.y - 10) / 2;
            closeBox = new Rectangle(x, y, 10, 10);
        }

        if (mouseIn) {
            gc.drawImage(ResourceManager
                    .getPluginImage(Activator.PLUGIN_ID, "icons/close.gif"), size.x
                            - 12 - (rounded ? 2 : 0), rounded ? 4 : 2);
        }

        if (selected) {
            if (rounded) {
                gc.drawRoundRectangle(0, 0, size.x, size.y, 16, 16);
            } else {
                gc.drawRectangle(0, 0, size.x, size.y);
            }
        }
    }

    private int getNextY() {
        int ret = TOP_MARGIN;
        for (Control c : widgets) {
            ret += c.getSize().y + 2;
        }
        return ret;
    }

    public void addTitle(String title) {
        addTitle(title, null);
    }

    public void addTitle(String title, Image icon) {

        // make it wider if there's a title
        TOP_MARGIN = 5;

        Label tlabel = new Label(this, SWT.NONE);
        tlabel.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
        GC gc = new GC(tlabel);
        Point size = gc.textExtent(title);
        int wHint = size.x / 2 + 48;
        tlabel.setText(title);
        if (icon != null) {
            tlabel.setImage(icon);
        }
        tlabel.setBounds(LEFT_MARGIN + 4, getNextY(), wHint - 3, 16);
        tlabel.addMouseListener(new ML(false));
        widgets.add(tlabel);
    }

    public void addSeparator() {
        Label separator = new Label(this, SWT.HORIZONTAL | SWT.SEPARATOR);
        Point p = getSize();
        separator.setSize( p.x - LEFT_MARGIN - RIGHT_MARGIN, 8);
        separator.setBounds(LEFT_MARGIN, getNextY(), p.x - LEFT_MARGIN - RIGHT_MARGIN, 8);
        widgets.add(separator);
    }

    public void setText(String text) {
    	
        int[] pxy = StringUtil.getParagraphSize(text);
        textLabel = new CLabel(this, SWT.None);
        GC gc = new GC(textLabel);
        Point size = gc.textExtent(StringUtil.repeat('M', pxy[0]));
        int wHint = size.x / 2 + 36;
        int hHint = size.y * (pxy[1] + 2);

        if (multiline && (text.length() * size.x) > (containerWidth + 36)) {
        	int just = (containerWidth - 36)/size.x;
        	text = StringUtil.justifyLeft(text, just);
        }
        
        textLabel.setForeground(getForeground());
        textLabel.setBounds(LEFT_MARGIN, getNextY(), wHint - 3, hHint - 3);
        textLabel.setText(text);
        textLabel.setAlignment(SWT.CENTER);
        textLabel.addMouseTrackListener(new MTL());
        textLabel.addMouseListener(new ML(false));
        Point lsiz = textLabel.getSize();
        widgets.add(textLabel);
        lsiz.x += LEFT_MARGIN + RIGHT_MARGIN + 2;
        lsiz.y += getNextY() + 2;
        this.setSize(lsiz);
    }

    @Override
    public Point computeSize(int wHint, int hHint) {
        // TODO Auto-generated method stub
        return super.computeSize(wHint, hHint);
    }



    class MTL implements MouseTrackListener {

        @Override
        public void mouseHover(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseExit(MouseEvent e) {
            if (closeable) {
                mouseIn = false;
                redraw();
            }
        }

        @Override
        public void mouseEnter(MouseEvent e) {
            if (closeable) {
                mouseIn = true;
                redraw();
            }
        }
    }

    class ML implements MouseListener {

        boolean close;
        
        ML(boolean close) {
            this.close = close;
        }
        
        @Override
        public void mouseUp(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseDown(MouseEvent e) {
            if (close && closeBox != null) {
                if (closeBox.contains(e.x, e.y)) {
                    close();
                }
            }
        }

        @Override
        public void mouseDoubleClick(MouseEvent e) {
            if (selectable) {
                selected = !selected;
                select(selected);
                redraw();
            }
        }
    }
}
