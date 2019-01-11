package org.integratedmodelling.klab.ide.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.IExpansionListener;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.wb.swt.SWTResourceManager;
import org.integratedmodelling.klab.organizer.Folder;
import org.integratedmodelling.klab.organizer.Item;
import org.integratedmodelling.klab.organizer.Organizer;

public class Palette extends Composite {

	Composite container;
	Organizer organizer;

	boolean horizontal = false;
	private boolean drawTitle = false;

	public Palette(Organizer organizer, boolean horizontal, Composite parent, int style) {
		super(parent, style);

		this.horizontal = horizontal;
		this.organizer = organizer;

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout gl_composite = new GridLayout(1, false);
		gl_composite.marginWidth = 0;
		gl_composite.marginHeight = 0;
		gl_composite.verticalSpacing = 0;
		gl_composite.horizontalSpacing = 0;
		composite.setLayout(gl_composite);
		ScrolledComposite scroll = new ScrolledComposite(composite, SWT.V_SCROLL);
		scroll.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		scroll.setExpandVertical(true);
		scroll.setExpandHorizontal(true);
		this.container = new Composite(scroll, SWT.NONE);
		GridLayout gl_container = new GridLayout(1, false);
		gl_container.marginWidth = 0;
		gl_container.verticalSpacing = 0;
		gl_container.marginHeight = 0;
		gl_container.horizontalSpacing = 0;
		container.setLayout(gl_container);
		DropTarget dropTarget = new DropTarget(container, DND.DROP_MOVE);
		dropTarget.setTransfer(new Transfer[] { FileTransfer.getInstance() });
		dropTarget.addDropListener(new DropTargetAdapter() {

			@Override
			public void drop(DropTargetEvent event) {
				// file drop.
				if (event.data instanceof String[] && ((String[]) event.data).length > 0) {
					List<File> files = new ArrayList<>();
					for (String f : ((String[]) event.data)) {
						files.add(new File(f));
					}
					acceptFiles(files);
				}
			}

		});

		// TODO adding this it does scroll, but not intelligently. Without this, no
		// scrolling happens
		Rectangle rr = parent.getClientArea();
		scroll.setContent(container);
		scroll.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				Rectangle r = scroll.getClientArea();
				scroll.setMinSize(parent.computeSize(r.width, r.height));
			}
		});

		// if (PaletteManager.get().getPalettes().size() > 0) {
		// this.palette = PaletteManager.get().getPalettes().get(0);
		// draw();
		// }

	}

	protected void acceptFiles(List<File> files) {

		// /*
		// * the drop should only have one main file. Refuse multiple files unless
		// they're
		// * related - for now, just check the file name is the same for all files.
		// */
		// List<Pair<File, Collection<File>>> payload = DataManager.arrangeFiles(files);
		// if (payload.isEmpty()) {
		// return;
		// }
		// if (payload.get(0).getFirst().equals(DataManager.UNKNOWN_FILE)) {
		// Eclipse.alert("One or more files were not recognized as data. Please drop
		// supported data files only.");
		// return;
		// }
		//
		// if (payload.size() > 1) {
		// Eclipse.alert("Import of " + payload.size()
		// + " datasets requested. Please import one main dataset at a time.");
		// return;
		// }
		//
		// /*
		// * TODO do it. This, if successful, should ultimately generate the .k sidecar
		// file
		// * and insert the file in both the toolkit UI and the toolkit namespace.
		// */
		// Eclipse.alert("Import of dataset " +
		// MiscUtilities.getFileName(payload.get(0).getFirst())
		// + " requested. Please wait until this function is implemented.");
		//
	}

	public void draw() {

    	int width = getSize().x;  //computeSize(SWT.DEFAULT, SWT.DEFAULT).x; 
		
		/*
		 * remove everything
		 */
		for (Control control : container.getChildren()) {
			control.dispose();
		}

		// this.setPartName(organizer.getName());
		// if (palette.getIcon() != null) {
		// this.setTitleImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID,
		// palette.getIcon()));
		// }

		/*
		 * add invisible search box. TODO the box itself is unimplemented.
		 */
		// searchBox = new ObservationBox(container, this, SWT.NONE) {
		//
		// @Override
		// protected Composite showResultArea(boolean show) {
		// // TODO Auto-generated method stub
		// return null;
		// }
		//
		// @Override
		// protected void cancelSearch() {
		// searchAction.setChecked(false);
		// }
		// };
		// searchBox.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
		// 1));
		// ((GridData) searchBox.getLayoutData()).exclude = true;
		// searchBox.setVisible(false);

		/*
		 * draw title and description
		 */
		if (drawTitle ) {
			CLabel titleLabel = new CLabel(container, SWT.NONE);
			titleLabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NONE));
			titleLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
			// toolkit.adapt(titleLabel);
			// toolkit.paintBordersFor(titleLabel);
			titleLabel.setText(organizer.getName());
		}

		if (organizer.getDescription() != null && !organizer.getDescription().trim().isEmpty()) {
			Badge descLabel = new Badge(container, Badge.CLOSEABLE | Badge.MULTILINE | Badge.ROUNDED, SWT.NONE, width) {
				@Override
				protected void close() {
					organizer.setDescription(null);
					draw();
				}
			};
			descLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
			descLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
			descLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
			descLabel.setText(organizer.getDescription());
		}

		/*
		 * for each section add a section widget
		 */
		for (Folder folder : organizer.getFolders()) {

			if (folder.isHidden()) {
				continue;
			}

			Section folderSection = new Section(container, Section.TWISTIE);
			folderSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
			folderSection.setText(folder.getName());
			folderSection.setClient(new PaletteFolder(folderSection, this, folder));
			folderSection.setSeparatorControl(
					new Label(folderSection, (this.horizontal ? SWT.VERTICAL : SWT.HORIZONTAL) | SWT.SEPARATOR));
			folderSection.setExpanded(!folder.isClosed());
			folderSection.addExpansionListener(new IExpansionListener() {

				@Override
				public void expansionStateChanging(ExpansionEvent e) {
				}

				@Override
				public void expansionStateChanged(ExpansionEvent e) {
					folder.setClosed(!folderSection.isExpanded());
				}
			});
		}
	}

	public void notifySelection(Item item, Folder folder, boolean enabled, boolean shiftPressed) {
		// TODO Auto-generated method stub
		draw();
	}

	public void notifyExpansion(Item item, Folder folder, boolean enabled, boolean shiftPressed) {
		// if (item.getOnSelect() != null || item.getChildren().size() > 0) {
		// if (!shiftPressed) {
		// for (Folder expanded : currentlyExpandedSet) {
		// expanded.setStatus("hidden");
		// }
		// deactivateOtherItems(item);
		// }
		// for (ItemAction a : parseActions(item)) {
		// a.execute(enabled);
		// }
		// }
		draw();
	}

	private void deactivateOtherItems(Item item) {
		for (Folder folder : organizer.getFolders()) {
			for (Item it : folder.getItems()) {
				if (it.isActive() && !item.equals(it)) {
					it.setActive(false);
				}
			}
		}
	}
}