package org.integratedmodelling.klab.ide.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.IMessage.Repeatability;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.rest.ViewPanel;
import org.integratedmodelling.klab.rest.ViewAction;
import org.integratedmodelling.klab.rest.ViewComponent;
import org.integratedmodelling.klab.rest.View;
import org.integratedmodelling.klab.utils.StringUtil;

public class AppView extends Composite {

	private static final String MESSAGE_ID_KEY = "__MESSAGE_ID";

	private Map<String, ViewPanel> panels = new LinkedHashMap<>();
	private Map<String, Composite> composites = new LinkedHashMap<>();
	private List<ViewComponent> widgets = new ArrayList<>();
	private Map<String, Integer> displayed = new HashMap<>();
	private TabFolder tabFolder = null;
	private int nColumns = 1;
	private Composite parent;
	private ViewPart view;

	public AppView(boolean horizontal, Composite parent, int style, ViewPart view) {
		super(parent, style);
		this.parent = parent;
		setLayout(new GridLayout(1, true));
		this.view = view;
		// TODO set n. columns
	}

	/**
	 * Layout is a scrolled composite per panel, in tabs if > 1, each with grid
	 * layout with column number depending on the widget's size.
	 * 
	 * @param layout
	 */
	public void setup(View layout) {

		this.panels.clear();
		this.widgets.clear();
		this.composites.clear();
		this.displayed.clear();
		this.tabFolder = null;

		/*
		 * remove everything
		 */
		Display.getDefault().asyncExec(() -> {
			for (Control control : this.getChildren()) {
				control.dispose();
			}
		});

		if (layout.getHeader() != null) {
			panels.put(layout.getHeader().getName(), layout.getHeader());
			displayed.put(layout.getHeader().getName(), 0);
		}
		for (ViewPanel panel : layout.getLeftPanels()) {
			panels.put(panel.getName(), panel);
			displayed.put(panel.getName(), 0);
		}
		for (ViewPanel panel : layout.getPanels()) {
			panels.put(panel.getName(), panel);
			displayed.put(panel.getName(), 0);
		}
		for (ViewPanel panel : layout.getRightPanels()) {
			panels.put(panel.getName(), panel);
			displayed.put(panel.getName(), 0);
		}
		if (layout.getFooter() != null) {
			panels.put(layout.getFooter().getName(), layout.getFooter());
			displayed.put(layout.getFooter().getName(), 0);
		}

		Display.getDefault().asyncExec(() -> refresh());
	}

	public synchronized void addWidget(IMessage message) {
		ViewComponent widget = message.getPayload(ViewComponent.class);
		// save message ID for responding when interacted with
		widget.getData().put(MESSAGE_ID_KEY, message.getId());
		this.widgets.add(widget);
		Display.getDefault().asyncExec(() -> refresh());
	}

	public void refresh() {

		if (panels.size() > 1) {

			if (this.tabFolder == null) {

				this.tabFolder = new TabFolder(this, SWT.NONE);
				this.tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

				for (String panelId : panels.keySet()) {
					TabItem tbtmNewItem = new TabItem(tabFolder, SWT.NONE);
					tbtmNewItem.setText(StringUtil.capitalize(panelId));
					/* Scrolled */Composite scrolledComposite = new /* Scrolled */Composite(tabFolder,
							SWT.BORDER /* | SWT.H_SCROLL | SWT.V_SCROLL */);
					scrolledComposite.setLayout(new GridLayout(this.nColumns, true));
					tbtmNewItem.setControl(scrolledComposite);
//					scrolledComposite.setExpandHorizontal(true);
//					scrolledComposite.setExpandVertical(true);
					this.composites.put(panelId, scrolledComposite);
				}
			}

		} else if (panels.size() > 0) {

			if (composites.size() == 0) {
				/* Scrolled */Composite scrolledComposite = new /* Scrolled */Composite(this,
						SWT.BORDER /* | SWT.H_SCROLL | SWT.V_SCROLL */);
//				scrolledComposite.setExpandHorizontal(true);
//				scrolledComposite.setExpandVertical(true);
				scrolledComposite.setLayout(new GridLayout(1, true));
				scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//				Composite composite_1 = new Composite(scrolledComposite, SWT.NONE);
//				composite_1.setLayout(new GridLayout(1, false));
//				composite_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
				this.composites.put(panels.values().iterator().next().getName(), scrolledComposite);
			}
		}

		for (String panelId : panels.keySet()) {

			if (displayed.get(panelId) == 0) {
				// reset layout
			}

			for (int i = 0; i < widgets.size(); i++) {

				if (i < displayed.get(panelId)) {
					continue;
				}

				ViewComponent widget = widgets.get(i);
//				if (widget.getParentId() != null && !widget.getParentId().equals(panelId)) {
//					continue;
//				}

				Composite panel = composites.get(panelId);

				System.out.println("WIDGET " + widget);
				
				if (widget.getTitle() != null) {
					// add label
				}

				switch (widget.getType()) {
				case Alert:
					Eclipse.INSTANCE.alert(widget.getContent());
					break;
				case CheckButton:
					break;
				case Combo:
					break;
				case Confirm:
					break;
				case Footer:
					break;
				case Group:
					break;
				case Header:
					break;
				case Map:
					break;
				case Panel:
					break;
				case PushButton:
					Button button = new Button(panel, SWT.NONE);
					button.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
					button.setText(widget.getContent() == null ? "Push me" : widget.getContent());
					button.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							Activator.reply(widget.getData().get(MESSAGE_ID_KEY), IMessage.MessageClass.Run,
									IMessage.Type.RunScript, Repeatability.Repeatable, new ViewAction(widget));
						}
					});
					break;
				case RadioButton:
					break;
				case TextInput:
					Text text = new Text(panel, SWT.BORDER);
					text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
					if (widget.getContent() != null) {
						text.setText(widget.getContent());
					}
					text.addModifyListener(new ModifyListener() {
						@Override
						public void modifyText(ModifyEvent e) {
							Activator.reply(widget.getData().get(MESSAGE_ID_KEY), IMessage.MessageClass.Run,
									IMessage.Type.RunScript, Repeatability.Repeatable, new ViewAction(widget, text.getText()));
						}
					});
					break;
				case Tree:
					break;
				case TreeItem:
					break;
				default:
					break;
				}

				// show widget
				// widget.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1,
				// 1));
			}

			displayed.put(panelId, widgets.size());
		}

//		parent.setSize(this.view.get);
		parent.pack();
		parent.layout(true);
	}
}
