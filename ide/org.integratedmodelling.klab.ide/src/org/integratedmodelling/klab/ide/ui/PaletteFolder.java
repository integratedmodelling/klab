package org.integratedmodelling.klab.ide.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.integratedmodelling.klab.ide.ui.PaletteItem.SelectListener;
import org.integratedmodelling.klab.ide.utils.StringUtils;
import org.integratedmodelling.klab.organizer.Folder;
import org.integratedmodelling.klab.organizer.Item;

public class PaletteFolder extends Composite {

	private int N_COLUMNS = 3;
	Folder folder;
	Palette palette;

	public PaletteFolder(Composite parent, Palette palette, Folder folder) {
		super(parent, SWT.SHADOW_OUT);
		this.palette = palette;
		DropTarget dropTarget = new DropTarget(this, DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK);
		dropTarget.setTransfer(new Transfer[] { TextTransfer.getInstance() });
		dropTarget.addDropListener(new DropTargetAdapter() {

			@Override
			public void drop(DropTargetEvent event) {
				Point where = PaletteFolder.this.toControl(event.x, event.y);
				int column = (int) (N_COLUMNS * ((float) where.x / (float) PaletteFolder.this.getSize().x));
				boolean copy = (event.detail & DND.DROP_COPY) == DND.DROP_COPY;
				if (event.data != null && event.data.toString().startsWith("@PI|")) {
					String path = event.data.toString().substring(4);
					Item item = folder.getOrganizer().findItem(path);
					if (copy) {
						/*
						 * TODO make a copy w/o IDs
						 */
					}
					item.setColumn(column);
					if (item != null) {
						folder.getItems().add(item);
						redrawViewer();
					}
				}
			}
		});

		draw(folder);
	}

	private void draw(Folder folder) {

		this.folder = folder;
    	int width = computeSize(SWT.DEFAULT, SWT.DEFAULT).x; 


		/*
		 * remove everything
		 */
		for (Control control : this.getChildren()) {
			control.dispose();
		}

		/**
		 * Arrange in columns; determine n. of columns, if necessary up the total
		 * columns in grid.
		 */
		int ncols = 0;
		for (Item pi : folder.getItems()) {
			// if (ncols < pi.getColumn()) {
			// ncols = pi.getColumn();
			// }
		}

		N_COLUMNS = Math.max(N_COLUMNS, ncols);

		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new GridLayout(3, false));

		if (folder.getDescription() != null && !folder.getDescription().trim().isEmpty()) {
			Badge descLabel = new Badge(this, Badge.CLOSEABLE | Badge.MULTILINE | Badge.ROUNDED, SWT.NONE, width) {
				@Override
				protected void close() {
					folder.setDescription(null);
					redrawViewer();
				}

			};
			descLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
			descLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
			descLabel.setText(StringUtils.justifyLeft(folder.getDescription(), 70));
			descLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		}

		/*
		 * boring, verbose and easy.
		 */
		List<List<Item>> columns = new ArrayList<>();
		for (int i = 0; i < N_COLUMNS; i++) {
			columns.add(new ArrayList<>());
		}

		int nrows = 0;
		for (Item pi : folder.getItems()) {
			columns.get(pi.getColumn()).add(pi);
			if (nrows < columns.get(pi.getColumn()).size()) {
				nrows = columns.get(pi.getColumn()).size();
			}
		}

		for (int row = 0; row < nrows; row++) {
			for (int col = 0; col < N_COLUMNS; col++) {
				List<Item> its = columns.get(col);
				if (its.size() > row) {
					PaletteItem widget = new PaletteItem(this, its.get(row));
					widget.setSelectListener(new SelectListener() {

						@Override
						public void onSelect(Item item, boolean enabled, boolean shiftPressed) {
							palette.notifySelection(item, folder, enabled, shiftPressed);
						}

						@Override
						public void onExpand(Item item, boolean enabled, boolean shiftPressed) {
							palette.notifyExpansion(item, folder, enabled, shiftPressed);
						}
					});
					widget.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
				} else {
					new Label(this, SWT.NONE);
				}
			}
		}

	}

	private void redrawViewer() {
		palette.draw();
	}
}
