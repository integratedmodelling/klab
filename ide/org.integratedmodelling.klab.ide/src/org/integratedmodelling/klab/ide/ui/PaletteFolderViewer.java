//package org.integratedmodelling.klab.ide.ui;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.dnd.DND;
//import org.eclipse.swt.dnd.DropTarget;
//import org.eclipse.swt.dnd.DropTargetAdapter;
//import org.eclipse.swt.dnd.DropTargetEvent;
//import org.eclipse.swt.dnd.TextTransfer;
//import org.eclipse.swt.dnd.Transfer;
//import org.eclipse.swt.graphics.Point;
//import org.eclipse.swt.layout.GridData;
//import org.eclipse.swt.layout.GridLayout;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Control;
//import org.eclipse.swt.widgets.Label;
//import org.eclipse.wb.swt.SWTResourceManager;
//import org.integratedmodelling.common.client.palette.PaletteFolder;
//import org.integratedmodelling.common.client.palette.PaletteItem;
//import org.integratedmodelling.common.client.palette.PaletteManager;
//import org.integratedmodelling.common.utils.StringUtils;
//import org.integratedmodelling.thinkcap.ui.PaletteItemWidget.SelectListener;
//import org.integratedmodelling.thinkcap.views.PaletteView;
//
//public class PaletteFolderViewer extends Composite {
//
//    private int   N_COLUMNS = 3;
//    PaletteFolder folder;
//    PaletteView   viewer;
//
//    public PaletteFolderViewer(Composite parent, PaletteView view, PaletteFolder folder) {
//        super(parent, SWT.SHADOW_OUT);
//        this.viewer = view;
//        DropTarget dropTarget = new DropTarget(this, DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK);
//        dropTarget.setTransfer(new Transfer[] { TextTransfer.getInstance() });
//        dropTarget.addDropListener(new DropTargetAdapter() {
//
//            @Override
//            public void drop(DropTargetEvent event) {
//                Point where = PaletteFolderViewer.this.toControl(event.x, event.y);
//                int column = (int) (N_COLUMNS
//                        * ((float) where.x / (float) PaletteFolderViewer.this.getSize().x));
//                boolean copy = (event.detail & DND.DROP_COPY) == DND.DROP_COPY;
//                if (event.data != null && event.data.toString().startsWith("@PI|")) {
//                    String path = event.data.toString().substring(4);
//                    PaletteItem item = PaletteManager.get().getItem(path);
//                    if (copy) {
//                        /*
//                         * TODO make a copy w/o IDs 
//                         */
//                    }
//                    item.setColumn(column);
//                    if (item != null) {
//                        folder.addItem(item);
//                        viewer.draw();
//                    }
//                }
//            }
//        });
//
//        draw(folder);
//    }
//
//    private void draw(PaletteFolder folder) {
//
//        this.folder = folder;
//
//        /*
//         * remove everything
//         */
//        for (Control control : this.getChildren()) {
//            control.dispose();
//        }
//
//        /**
//         * Arrange in columns; determine n. of columns, if necessary up the total columns
//         * in grid.
//         */
//        int ncols = 0;
//        for (PaletteItem pi : folder.getItems()) {
//            if (ncols < pi.getColumn()) {
//                ncols = pi.getColumn();
//            }
//        }
//
//        N_COLUMNS = Math.max(N_COLUMNS, ncols);
//
//        setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
//        setLayout(new GridLayout(3, false));
//
//        if (folder.getDescription() != null && !folder.getDescription().trim().isEmpty()) {
//            Badge descLabel = new Badge(this,  Badge.CLOSEABLE
//                    | Badge.MULTILINE | Badge.ROUNDED, SWT.NONE) {
//                @Override
//                protected void close() {
//                    folder.setDescription(null);
//                    viewer.draw();
//                }
//            };
//            descLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
//            descLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
//            descLabel.setText(StringUtils.justifyLeft(folder.getDescription(), 70));
//            descLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
//        }
//
//        /*
//         * boring, verbose and easy.
//         */
//        List<List<PaletteItem>> columns = new ArrayList<>();
//        for (int i = 0; i < N_COLUMNS; i++) {
//            columns.add(new ArrayList<>());
//        }
//
//        int nrows = 0;
//        for (PaletteItem pi : folder.getItems()) {
//            columns.get(pi.getColumn()).add(pi);
//            if (nrows < columns.get(pi.getColumn()).size()) {
//                nrows = columns.get(pi.getColumn()).size();
//            }
//        }
//
//        for (int row = 0; row < nrows; row++) {
//            for (int col = 0; col < N_COLUMNS; col++) {
//                List<PaletteItem> its = columns.get(col);
//                if (its.size() > row) {
//                    PaletteItemWidget widget = new PaletteItemWidget(this, its.get(row));
//                    widget.setSelectListener(new SelectListener() {
//
//                        @Override
//                        public void onSelect(PaletteItem item, boolean enabled, boolean shiftPressed) {
//                            viewer.notifySelection(item, folder, enabled, shiftPressed);
//                        }
//
//                        @Override
//                        public void onExpand(PaletteItem item, boolean enabled, boolean shiftPressed) {
//                            viewer.notifyExpansion(item, folder, enabled, shiftPressed);
//                        }
//                    });
//                    widget.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
//                } else {
//                    new Label(this, SWT.NONE);
//                }
//            }
//        }
//
//    }
//
//}
