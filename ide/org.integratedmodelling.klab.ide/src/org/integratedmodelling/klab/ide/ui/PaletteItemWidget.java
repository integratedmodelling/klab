//package org.integratedmodelling.klab.ide.ui;
//
//import org.eclipse.jface.resource.FontDescriptor;
//import org.eclipse.jface.resource.JFaceResources;
//import org.eclipse.jface.resource.LocalResourceManager;
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.dnd.DND;
//import org.eclipse.swt.dnd.DragSource;
//import org.eclipse.swt.dnd.DragSourceAdapter;
//import org.eclipse.swt.dnd.DragSourceEvent;
//import org.eclipse.swt.dnd.TextTransfer;
//import org.eclipse.swt.dnd.Transfer;
//import org.eclipse.swt.events.MouseEvent;
//import org.eclipse.swt.events.MouseListener;
//import org.eclipse.swt.events.PaintEvent;
//import org.eclipse.swt.events.PaintListener;
//import org.eclipse.swt.graphics.Color;
//import org.eclipse.swt.graphics.Font;
//import org.eclipse.swt.graphics.GC;
//import org.eclipse.swt.graphics.Point;
//import org.eclipse.swt.graphics.RGB;
//import org.eclipse.swt.widgets.Canvas;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.wb.swt.SWTResourceManager;
//import org.integratedmodelling.common.client.palette.PaletteItem;
//import org.integratedmodelling.common.utils.StringUtils;
//
//public class PaletteItemWidget extends Canvas {
//
//    public static interface SelectListener {
//        void onSelect(PaletteItem item, boolean enabled, boolean shiftPressed);
//
//        void onExpand(PaletteItem item, boolean enabled, boolean shiftPressed);
//    }
//
//    private int                  LINE_WIDTH  = 140;
//    private int                  LINE_HEIGHT = 24;
//    private LocalResourceManager resourceManager;
//    private PaletteItem          item        = null;
//    private Color                back, shadow1, shadow2, orange;
//    private Font                 boldArialFont;
//    private SelectListener       listener;
//
//    private MouseListener        mouseListener;
//    private boolean              inited;
//
//    public PaletteItemWidget(Composite parent, PaletteItem item) {
//
//        super(parent, SWT.NONE);
//        this.item = item;
//
//        resourceManager = new LocalResourceManager(JFaceResources.getResources(), this);
//
//        DragSource dragSource = new DragSource(this, DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK);
//        dragSource.setTransfer(new Transfer[] {
//                TextTransfer.getInstance() });
//        dragSource.addDragListener(new DragSourceAdapter() {
//            @Override
//            public void dragSetData(DragSourceEvent event) {
//                event.data = item.createTransferSignature();
//            }
//        });
//
//        back = resourceManager.createColor(new RGB(240, 240, 240));
//        shadow1 = resourceManager.createColor(new RGB(236, 236, 236));
//        shadow2 = resourceManager.createColor(new RGB(228, 228, 228));
//        orange = resourceManager.createColor(new RGB(255, 140, 0));
//        boldArialFont = resourceManager.createFont(FontDescriptor.createFrom("Arial", 8, SWT.BOLD));
//
//        addMouseListener(this.mouseListener = new MouseListener() {
//
//            @Override
//            public void mouseUp(MouseEvent e) {
//            }
//
//            @Override
//            public void mouseDown(MouseEvent e) {
//                if ((item.getOnSelect() != null || item.getChildren().size() > 0)
//                        && listener != null
//                        && e.x > 5
//                        && e.x < 19 && e.y > 5 && e.y < 17) {
//                    item.setActive(!item.isActive());
//                    // assumes listener will redraw
//                    listener.onExpand(item, item.isActive(), (e.stateMask & SWT.SHIFT) != 0);
//                }
//            }
//
//            @Override
//            public void mouseDoubleClick(MouseEvent e) {
//                if (listener != null && e.x > 32) {
//                    item.setSelected(!item.isSelected());
//                    // assumes listener will redraw
//                    listener.onSelect(item, item.isSelected(), (e.stateMask & SWT.SHIFT) == e.stateMask);
//                }
//            }
//        });
//
//        addPaintListener(new PaintListener() {
//            @Override
//            public void paintControl(PaintEvent e) {
//                paint(e.gc);
//            }
//        });
//    }
//
//    public void setSelectListener(SelectListener listener) {
//        this.listener = listener;
//    }
//
//    protected void paint(GC gc) {
//
//        Point size = this.getSize();
//
//        gc.setAntialias(SWT.ON);
//        gc.setTextAntialias(SWT.ON);
//
//        this.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
//
//        gc.setFont(boldArialFont);
//
//        gc.setBackground(shadow1);
//        gc.fillRoundRectangle(1, 1, size.x - 2, size.y - 2, 16, 16);
//        gc.setBackground(back);
//        gc.fillRoundRectangle(1, 1, size.x - 2, size.y - 2, 16, 16);
//        gc.setBackground(getColor());
//        gc.fillRoundRectangle(1, 1, 32, size.y - 2, 16, 16);
//        gc.setBackground(back);
//        gc.fillRectangle(25, 1, 8, size.y - 2);
//
//        if (item.isSelected()) {
//            gc.drawRoundRectangle(1, 1, size.x - 2, size.y - 2, 16, 16);
//        }
//
//        /*
//         * draw selection button if selectable
//         */
//        if (item.getOnSelect() != null || item.getChildren().size() > 0) {
//            /*
//             * white circle
//             */
//            gc.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
//            gc.fillArc(5, 4, 16, 16, 0, 360);
//
//            if (item.isActive()) {
//                gc.setBackground(getEnabledColor());
//                gc.fillArc(7, 6, 12, 12, 0, 360);
//            }
//        }
//
//        /*
//         * TODO draw info and menu
//         */
//
//        this.setToolTipText(StringUtils.justifyLeft(item.getDescription(), 60));
//
//        gc.drawString(item.getName(), 28, 4, true);
//
//    }
//
//    private Color getEnabledColor() {
//        // TODO analyze context to see if this can be computed; make it green if
//        // so, yellow if partial, grey if not.
//        return SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
//    }
//
//    @Override
//    public Point computeSize(int wHint, int hHint, boolean changed) {
//
//        int width = wHint != SWT.DEFAULT ? Math.min(wHint, LINE_WIDTH)
//                : LINE_WIDTH;
//        int height = hHint != SWT.DEFAULT ? Math.min(hHint, LINE_HEIGHT)
//                : LINE_HEIGHT;
//
//        return new Point(width, height);
//    }
//
//    public Color getColor() {
//        switch (item.getColor()) {
//        case "red":
//            return SWTResourceManager.getColor(SWT.COLOR_RED);
//        case "green":
//            return SWTResourceManager.getColor(SWT.COLOR_GREEN);
//        case "yellow":
//            return SWTResourceManager.getColor(SWT.COLOR_YELLOW);
//        case "blue":
//            return SWTResourceManager.getColor(SWT.COLOR_BLUE);
//        case "cyan":
//            return SWTResourceManager.getColor(SWT.COLOR_CYAN);
//        case "magenta":
//            return SWTResourceManager.getColor(SWT.COLOR_MAGENTA);
//        case "dark green":
//            return SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN);
//        case "dark blue":
//            return SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE);
//        case "dark red":
//            return SWTResourceManager.getColor(SWT.COLOR_DARK_RED);
//        case "dark yellow":
//            return SWTResourceManager.getColor(SWT.COLOR_DARK_YELLOW);
//        case "dark cyan":
//            return SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN);
//        case "dark gray":
//            return SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY);
//        case "dark magenta":
//            return SWTResourceManager.getColor(SWT.COLOR_DARK_MAGENTA);
//        }
//        return SWTResourceManager.getColor(SWT.COLOR_GRAY);
//
//    }
//
//    @Override
//    public void dispose() {
//        resourceManager.dispose();
//        removeMouseListener(mouseListener);
//        super.dispose();
//    }
//
//}
