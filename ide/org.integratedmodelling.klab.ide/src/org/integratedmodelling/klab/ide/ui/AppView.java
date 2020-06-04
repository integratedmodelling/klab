package org.integratedmodelling.klab.ide.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.rest.Layout;
import org.integratedmodelling.klab.rest.ViewAction;
import org.integratedmodelling.klab.rest.ViewComponent;
import org.integratedmodelling.klab.rest.ViewPanel;
import org.integratedmodelling.klab.utils.StringUtil;

public class AppView extends Composite {

	private Composite parent;
	private Map<String, Composite> containers = new HashMap<>();
	private Object currentLayout;

	public AppView(boolean horizontal, Composite parent, int style, ViewPart view) {
		super(parent, style);
		this.parent = parent;
		setLayout(new GridLayout(1, true));
	}

	private Composite makeView(Layout view, Composite parent) {

		Composite ret = new Composite(parent, SWT.NONE);
		Composite header = null;
		Composite footer = null;
		Composite center = null;

		int ncols = 3;
		if (view.getLeftPanels().size() > 0) {
			ncols++;
		}
		if (view.getRightPanels().size() > 0) {
			ncols++;
		}

		ret.setLayout(new GridLayout(1, true));

		if (view.getHeader() != null) {
			header = new Composite(ret, SWT.NONE);
			header.setLayout(new GridLayout(view.getHeader().getComponents().size(), false));
			header.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
		}

		center = new Composite(ret, SWT.NONE);
		center.setLayout(new GridLayout(ncols, false));
		center.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		if (view.getFooter() != null) {
			footer = new Composite(ret, SWT.NONE);
			footer.setLayout(new GridLayout(view.getFooter().getComponents().size(), false));
			footer.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
		}

		Composite leftArea = null;
		Composite rightArea = null;
		Composite centerArea = null;

		if (view.getLeftPanels().size() > 0) {
			leftArea = makePanel(view.getLeftPanels(), center);
			leftArea.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
		}

		centerArea = makePanel(view.getPanels(), center);
		centerArea.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 3, 1));

		if (view.getRightPanels().size() > 0) {
			rightArea = makePanel(view.getRightPanels(), center);
			rightArea.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
		}

		return ret;
	}

	private Composite makePanel(List<ViewPanel> panels, Composite parent) {

		Composite ret = new Composite(parent, SWT.NONE);
		ret.setLayout(new GridLayout(1, false));
		
		if (panels.size() > 1) {

			TabFolder tabFolder = new TabFolder(ret, SWT.NONE);
			tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

			for (ViewPanel panel : panels) {
				TabItem tbtmNewItem = new TabItem(tabFolder, SWT.NONE);
				tbtmNewItem.setText(StringUtil.capitalize(panel.getName()));
				Composite innerFrame = new Composite(tabFolder, SWT.NONE);
				innerFrame.setLayout(new GridLayout(1, false));
				tbtmNewItem.setControl(innerFrame);
				for (ViewComponent component : panel.getComponents()) {
					makeComponent(component, innerFrame);
				}
				innerFrame.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
			}

		} else if (panels.size() > 0) {
			Composite innerFrame = new Composite(ret, SWT.NONE);
			innerFrame.setLayout(new GridLayout(1, false));
			for (ViewComponent component : panels.get(0).getComponents()) {
				makeComponent(component, innerFrame);
			}
			innerFrame.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
		}

		return ret;

	}

	private void makeComponent(ViewComponent component, Composite parent) {
		switch (component.getType()) {
		case Alert:
			Eclipse.INSTANCE.alert(component.getContent());
			break;
		case CheckButton:
			break;
		case Combo:
			break;
		case Confirm:
			break;
		case Container:
		case MultiContainer:
			// composite, add to map
			break;
		case Group:
			Composite group = component.getName() == null ? new Composite(parent, SWT.NONE)
					: new Group(parent, SWT.NONE);
			if (component.getName() != null) {
				((Group)group).setText(component.getName());
			}
			group.setLayout(new GridLayout(component.getComponents().size(), false));
			for (ViewComponent child : component.getComponents()) {
				makeComponent(child, group);
			}
			group.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
			break;
		case Map:
			break;
		case Panel:
			break;
		case PushButton:
			Button button = new Button(parent, SWT.NONE);
			button.setText(component.getName() == null ? "Push me" : component.getName());
			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Activator.post(IMessage.MessageClass.UserInterface, IMessage.Type.ViewAction,
							new ViewAction(component));
				}
			});
			button.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
			break;
		case RadioButton:
			break;
		case TextInput:
			Text text = new Text(parent, SWT.BORDER);
			text.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
			if (component.getContent() != null) {
				text.setText(component.getContent());
			}
			text.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					Activator.post(IMessage.MessageClass.UserInterface, IMessage.Type.ViewAction,
							new ViewAction(component, text.getText()));
				}
			});
			break;
		case Tree:
			break;
		case TreeItem:
			break;
		case View:
			break;
		default:
			break;
		}

	}

	public void setup(Layout layout) {

		if (this.currentLayout != null) {

			/*
			 * remove everything - TODO this isn't called recursively, probably needs a
			 * different way
			 */
			Display.getDefault().asyncExec(() -> {
				for (Control control : this.getChildren()) {
					control.dispose();
				}
			});
		}

		this.currentLayout = layout;
		this.containers.clear();

		Display.getDefault().asyncExec(() -> {
			this.setLayout(new GridLayout(1, true));
			Composite app = makeView(layout, this);
			app.setLayout(new GridLayout(1, true));
			app.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			// parent.setSize(this.view.get);
			parent.pack();
			parent.layout(true);
		});
	}

	public synchronized void addWidget(IMessage message) {
		ViewComponent widget = message.getPayload(ViewComponent.class);
		// TODO parent must be a known container

		// save message ID for responding when interacted with
//		widget.getData().put(MESSAGE_ID_KEY, message.getId());
//		this.widgets.add(widget);
//		Display.getDefault().asyncExec(() -> refresh());
	}

}
