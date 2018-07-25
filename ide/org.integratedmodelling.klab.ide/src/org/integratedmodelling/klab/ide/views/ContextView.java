
package org.integratedmodelling.klab.ide.views;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.model.KlabPeer;
import org.integratedmodelling.klab.ide.model.KlabPeer.Sender;
import org.integratedmodelling.klab.ide.navigator.model.EConcept;
import org.integratedmodelling.klab.ide.navigator.model.EKimObject;
import org.integratedmodelling.klab.ide.navigator.model.EModel;
import org.integratedmodelling.klab.ide.navigator.model.EObserver;
import org.integratedmodelling.klab.ide.navigator.model.EScript;
import org.integratedmodelling.klab.ide.navigator.model.ETestCase;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.rest.ResourceReference;

public class ContextView extends ViewPart {
	public ContextView() {
	}

	private Composite container;
	private Button searchModeButton;
	private Text subjectLabel;
	private SashForm dropArea;
	private Label dropImage;
	private TableViewer tableViewer;
	private Table queryResults;
	private CLabel scenariosLabel;
	private CLabel spatialContext;
	private Button btnNewButtonSp;
	private CLabel temporalContext;
	private Button btnNewButtonT;

	private KlabPeer klab;

	@Override
	public void createPartControl(Composite parent) {

		container = new Composite(parent, SWT.NONE);
		container.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		GridLayout gl_container = new GridLayout(1, false);
		gl_container.horizontalSpacing = 0;
		gl_container.verticalSpacing = 0;
		gl_container.marginHeight = 0;
		gl_container.marginWidth = 0;
		container.setLayout(gl_container);
		{
			Composite ccombo = new Composite(container, SWT.NONE);
			ccombo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			// ccombo.setBackground(SWTResourceManager
			// .getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
			ccombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			GridLayout gl_ccombo = new GridLayout(3, false);
			gl_ccombo.marginWidth = 0;
			gl_ccombo.marginHeight = 0;
			ccombo.setLayout(gl_ccombo);

			searchModeButton = new Button(ccombo, SWT.TOGGLE);
			searchModeButton.setToolTipText("Query the network for a context ");
			searchModeButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseUp(MouseEvent e) {
					//					KlabNavigator.refresh();
					// searchMode(searchModeButton.getSelection());
				}
			});
			searchModeButton.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/Database.png"));
			// toolkit.adapt(searchModeButton, true, true);
			{
				subjectLabel = new Text(ccombo, SWT.NONE);
				subjectLabel.setEnabled(false);
				subjectLabel.setEditable(false);
				subjectLabel.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
				subjectLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND));
				subjectLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				subjectLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
				// toolkit.adapt(subjectLabel, true, true);
				subjectLabel.setText("No context");
				subjectLabel.addListener(SWT.Traverse, new Listener() {
					@Override
					public void handleEvent(Event event) {
						if (event.detail == SWT.TRAVERSE_RETURN) {
							searchObservations(subjectLabel.getText());
						}
					}
				});
			}
			{
				final Button btnNewButton = new Button(ccombo, SWT.NONE);
				btnNewButton.setToolTipText("Choose target subject");
				btnNewButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseUp(MouseEvent e) {
						// if (Environment.get().getContext() != null) {
						// PopupTreeChooser ptc = new PopupTreeChooser(Eclipse
						// .getShell(), new ContextLabelProvider(), new ContextContentProvider(),
						// Environment
						// .get().getContext()) {
						//
						// @Override
						// protected void objectSelected(Object object) {
						// if (object instanceof ISubject) {
						// setObservationTarget((ISubject) object);
						// }
						// super.objectSelected(object);
						// }
						//
						// };
						// ptc.show(btnNewButton.toDisplay(new Point(e.x, e.y)));
						// } else {
						// Eclipse.beep();
						// }
					}
				});
				btnNewButton.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/Tree.png"));
				// toolkit.adapt(btnNewButton, true, true);
			}
		}
		{
			dropArea = new SashForm(container, SWT.NONE);
			dropArea.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			dropArea.setLayout(new FillLayout(SWT.HORIZONTAL));
			GridData gd_dropArea = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
			gd_dropArea.widthHint = 168;
			gd_dropArea.heightHint = 168;
			dropArea.setLayoutData(gd_dropArea);
			// toolkit.adapt(dropArea);
			// toolkit.paintBordersFor(dropArea);
			dropImage = new Label(dropArea, SWT.SHADOW_NONE | SWT.CENTER);
			dropImage.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
			dropImage.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseDown(MouseEvent e) {
					/*
					 * TODO act as an interrupt button when the task is running
					 */
					// if (_taskId >= 0) {
					// showData(e.x, e.y);
					// }
				}
			});
			dropImage.setToolTipText("Drop a subject to define the context.");
			dropImage.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/ndrop.png"));
			// toolkit.adapt(dropImage, true, true);
			DropTarget dropTarget = new DropTarget(dropImage, DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK);
			dropTarget.setTransfer(new Transfer[] {
					// BookmarkTransfer.getInstance(),
					TextTransfer.getInstance(), LocalSelectionTransfer.getTransfer() });
			DragSource dragSource = new DragSource(dropImage, DND.DROP_MOVE | DND.DROP_COPY);
			dragSource.setTransfer(new Transfer[] { TextTransfer.getInstance() });

			tableViewer = new TableViewer(dropArea, SWT.BORDER | SWT.FULL_SELECTION);
			queryResults = tableViewer.getTable();
			queryResults.setHeaderVisible(true);
			queryResults.setVisible(false);
			queryResults.setLinesVisible(true);
			queryResults.addMouseListener(new MouseListener() {

				@Override
				public void mouseUp(MouseEvent e) {
				}

				@Override
				public void mouseDown(MouseEvent e) {
				}

				@Override
				public void mouseDoubleClick(MouseEvent e) {
					// observeFromDatabase((ObservationMetadata) queryResults
					// .getSelection()[0].getData());
					// searchMode(false);
				}
			});
			// toolkit.paintBordersFor(queryResults);

			TableColumn tblclmnNewColumn = new TableColumn(queryResults, SWT.NONE);
			tblclmnNewColumn.setWidth(200);
			tblclmnNewColumn.setText("Name");

			TableColumn tblclmnNewColumn_1 = new TableColumn(queryResults, SWT.NONE);
			tblclmnNewColumn_1.setWidth(160);
			tblclmnNewColumn_1.setText("Observable");

			TableColumn tblclmnNewColumn_2 = new TableColumn(queryResults, SWT.NONE);
			tblclmnNewColumn_2.setWidth(140);
			tblclmnNewColumn_2.setText("Namespace");

			TableColumn tblclmnNewColumn_3 = new TableColumn(queryResults, SWT.NONE);
			tblclmnNewColumn_3.setWidth(400);
			tblclmnNewColumn_3.setText("Description");
			dropArea.setWeights(new int[] { 100, 0 });
			tableViewer.setLabelProvider(new ResultLabelProvider());
			tableViewer.setContentProvider(new ResultContentProvider());

			Label label = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
			label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			// toolkit.adapt(label, true, true);
			{
				Composite labelContainer = new Composite(container, SWT.NONE);
				labelContainer.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				GridLayout gl_labelContainer = new GridLayout(3, false);
				gl_labelContainer.verticalSpacing = 0;
				gl_labelContainer.marginWidth = 0;
				gl_labelContainer.marginHeight = 0;
				labelContainer.setLayout(gl_labelContainer);
				labelContainer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
				// toolkit.adapt(labelContainer);
				// toolkit.paintBordersFor(labelContainer);

				Label lblNewLabel = new Label(labelContainer, SWT.NONE);
				lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
				GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_lblNewLabel.widthHint = 62;
				lblNewLabel.setLayoutData(gd_lblNewLabel);
				lblNewLabel.setBounds(0, 0, 55, 15);
				// toolkit.adapt(lblNewLabel, true, true);
				lblNewLabel.setText("Scenarios");
				{
					scenariosLabel = new CLabel(labelContainer, SWT.NONE);
					scenariosLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					scenariosLabel.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
					scenariosLabel.setText("No scenarios active");
					scenariosLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
					scenariosLabel.setAlignment(SWT.LEFT);
					// toolkit.adapt(scenariosLabel, true, true);
				}
				{
					final Button btnNewButtonSC = new Button(labelContainer, SWT.NONE);
					btnNewButtonSC.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							// Environment.get().clearScenarios();
						}
					});
					btnNewButtonSC.setToolTipText("Reset all scenarios");
					btnNewButtonSC.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
					btnNewButtonSC.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseUp(MouseEvent e) {
						}
					});
					btnNewButtonSC
							.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/Player Record.png"));
					// toolkit.adapt(btnNewButtonSC, true, true);
				}
				Label lblNewLabel_1 = new Label(labelContainer, SWT.NONE);
				lblNewLabel_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				lblNewLabel_1.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
				GridData gd_lblNewLabel_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_lblNewLabel_1.widthHint = 62;
				lblNewLabel_1.setLayoutData(gd_lblNewLabel_1);
				// toolkit.adapt(lblNewLabel_1, true, true);
				lblNewLabel_1.setText("Space");

				spatialContext = new CLabel(labelContainer, SWT.NONE);
				spatialContext.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
				// spatialContext.setText(Environment.get().getSpatialForcing().toString());
				spatialContext.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
				spatialContext.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				spatialContext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
				// toolkit.adapt(spatialContext, true, true);

				Menu menu = new Menu(spatialContext);
				spatialContext.setMenu(menu);

				MenuItem mntmSetThisAs = new MenuItem(menu, SWT.NONE);
				mntmSetThisAs.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						// makeDefault(Environment.get().getSpatialForcing());
					}
				});
				mntmSetThisAs.setText("Set this as default");

				MenuItem mntmResetDefaults = new MenuItem(menu, SWT.NONE);
				mntmResetDefaults.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						// makeDefault(Space.getForcing(1, "km"));
					}
				});
				mntmResetDefaults.setText("Reset defaults");
				{
					btnNewButtonSp = new Button(labelContainer, SWT.NONE);
					btnNewButtonSp.setToolTipText("Choose default spatial representation");
					btnNewButtonSp.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
					btnNewButtonSp.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseUp(MouseEvent e) {
							// PopupSpaceChooser ptc = new PopupSpaceChooser(Eclipse
							// .getShell(), SWT.BORDER, Environment.get()
							// .getSpatialForcing(), ContextView.this);
							// ptc.show(btnNewButtonSp.toDisplay(new Point(e.x, e.y - 110)));
						}
					});
					btnNewButtonSp.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/Globe.png"));
					// toolkit.adapt(btnNewButtonSp, true, true);
				}

				Label lblNewLabel_3 = new Label(labelContainer, SWT.NONE);
				lblNewLabel_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				lblNewLabel_3.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
				GridData gd_lblNewLabel_3 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_lblNewLabel_3.widthHint = 62;
				lblNewLabel_3.setLayoutData(gd_lblNewLabel_3);
				// toolkit.adapt(lblNewLabel_3, true, true);
				lblNewLabel_3.setText("Time");

				temporalContext = new CLabel(labelContainer, SWT.NONE);
				temporalContext.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
				temporalContext.setText("No temporal context");
				temporalContext.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
				temporalContext.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				temporalContext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
				// toolkit.adapt(temporalContext, true, true);

				Menu menu_1 = new Menu(temporalContext);
				temporalContext.setMenu(menu_1);

				MenuItem mntmSetThisAs_1 = new MenuItem(menu_1, SWT.NONE);
				mntmSetThisAs_1.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						// makeDefault(Environment.get().getTemporalForcing());
					}
				});
				mntmSetThisAs_1.setText("Set this as default");

				MenuItem mntmResetDefaults_1 = new MenuItem(menu_1, SWT.NONE);
				mntmResetDefaults_1.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						// makeDefault(Time.getForcing(0, 0, 0));
					}
				});
				mntmResetDefaults_1.setText("Reset defaults");
				{
					btnNewButtonT = new Button(labelContainer, SWT.NONE);
					btnNewButtonT.setToolTipText("Choose default temporal representation");
					btnNewButtonT.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
					btnNewButtonT.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseUp(MouseEvent e) {
							// PopupTimeChooser ptc = new PopupTimeChooser(Eclipse
							// .getShell(), SWT.BORDER, Environment.get()
							// .getTemporalForcing(), ContextView.this);
							// ptc.show(btnNewButtonSp.toDisplay(new Point(e.x, e.y - 110)));
						}
					});
					btnNewButtonT.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/Clock.png"));
					// toolkit.adapt(btnNewButtonT, true, true);
				}
			}
			dropTarget.addDropListener(new DropTargetAdapter() {

				@Override
				public void drop(DropTargetEvent event) {

					boolean addToContext = (event.detail & DND.DROP_COPY) == DND.DROP_COPY;

					if (!Activator.engineMonitor().isRunning()) {
						Eclipse.INSTANCE.alert("Please ensure the engine is running before making observations.");
					} else {

						Object dropped = event.data instanceof TreeSelection
								? ((TreeSelection) event.data).getFirstElement()
								: null;

						if (dropped instanceof ETestCase || dropped instanceof EScript) {
							File file = ((EKimObject) dropped).getPhysicalFile();
							if (file != null) {
								try {
									if (dropped instanceof ETestCase) {
										Activator.session().launchTest(file.toURI().toURL());
									} else {
										Activator.session().launchScript(file.toURI().toURL());
									}
								} catch (MalformedURLException e) {
									Eclipse.INSTANCE.handleException(e);
								}
							}
							
						} else if (dropped instanceof EModel || dropped instanceof EConcept) {
							Activator.session().observe((EKimObject) dropped);
						} else if (dropped instanceof EObserver) {
							Activator.session().observe((EObserver) dropped, addToContext);
						} else if (dropped instanceof ResourceReference) {
							Activator.session().previewResource((ResourceReference) dropped);
						}
					}

					// /*
					// * reset forcings to default
					// */
					// setTimeForcing(defaultTemporalForcing);
					// setSpatialForcing(defaultSpatialForcing);
					// }
				}
			});
		}

		klab = new KlabPeer(Sender.ANY, (message) -> handleMessage(message));

	}

	private void handleMessage(IMessage message) {

		switch (message.getType()) {
		case DataflowCompiled:
			break;
		case Debug:
			break;
		case DebugScript:
			break;
		case DebugTest:
			break;
		case EngineDown:
			Display.getDefault().asyncExec(
					() -> dropImage.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/ndrop.png")));
			break;
		case EngineUp:
			Display.getDefault().asyncExec(
					() -> dropImage.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/odrop.png")));
			break;
		case MatchAction:
			break;
		case ModifiedObservation:
			break;
		case NewObservation:
			break;
		case PeriodOfInterest:
			break;
		case QueryResult:
			break;
		case RegionOfInterest:
			break;
		case RequestObservation:
			break;
		case RunScript:
			break;
		case RunTest:
			break;
		case ScriptStarted:
			Display.getDefault().asyncExec(
					() -> dropImage.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/orun.png")));
			break;
		case SubmitSearch:
			break;
		case TaskAborted:
			Display.getDefault().asyncExec(
					() -> dropImage.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/ostop.png")));
			break;
		case TaskFinished:
			Display.getDefault().asyncExec(
					() -> dropImage.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/odrop.png")));
			break;
		case TaskStarted:
			Display.getDefault().asyncExec(
					() -> dropImage.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/orun.png")));
			break;
		case UserProjectDeleted:
			break;
		case UserProjectModified:
			break;
		case UserProjectOpened:
			break;
		default:
			break;

		}
	}

	protected void searchObservations(String text) {
		// TODO Auto-generated method stub

	}

	class ContextContentProvider implements ITreeContentProvider {

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@Override
		public Object[] getElements(Object inputElement) {
			/*
			 * if (inputElement instanceof IContext) { return new Object[] { ((IContext)
			 * inputElement).getSubject() }; } else
			 */ if (inputElement instanceof ISubject) {
				return ((ISubject) inputElement).getSubjects().toArray();
			}
			return null;
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			return getElements(parentElement);
		}

		@Override
		public Object getParent(Object element) {
			if (element instanceof ISubject) {
				// return ((ISubject) element).getContextObservation() == null
				// ? Environment.get().getContext()
				// : ((ISubject) element).getContextObservation();
			}
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return false;
			// element instanceof IContext
			// || (element instanceof ISubject && ((ISubject) element).getSubjects().size()
			// > 0);
		}

	}

	class ContextLabelProvider extends BaseLabelProvider implements ILabelProvider {

		@Override
		public Image getImage(Object element) {
			if (element instanceof ISubject) {
				return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/observer.gif");
			}
			return null;
		}

		@Override
		public String getText(Object element) {
			if (element instanceof ISubject) {
				return ((ISubject) element).getName();
			}
			return null;
		}

	}

	class ResultContentProvider implements ITreeContentProvider {

		// painful, but I really don't want to store this in the view. Cross
		// fingers.
		List<?> data = null;

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof List) {
				data = (List<?>) inputElement;
				return data.toArray();
			}
			return null;
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			return getElements(parentElement);
		}

		@Override
		public Object getParent(Object element) {
			if (element instanceof List) {
				data = (List<?>) element;
			}
			// if (element instanceof ObservationMetadata) {
			// return data;
			// }
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			if (element instanceof List) {
				data = (List<?>) element;
			}
			return element instanceof List && data != null && data.size() > 0;
		}
	}

	class ResultLabelProvider extends BaseLabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			// if (columnIndex == 0 && element instanceof ObservationMetadata) {
			// return ResourceManager
			// .getPluginImage(Activator.PLUGIN_ID, "icons/observer.gif");
			// }
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			// if (element instanceof ObservationMetadata) {
			// switch (columnIndex) {
			// case 0:
			// return ((ObservationMetadata) element).id;
			// case 1:
			// return ((ObservationMetadata) element).observableName;
			// case 2:
			// return ((ObservationMetadata) element).namespaceId.equals(KLAB.NAME)
			// ? "Local database"
			// : ((ObservationMetadata) element).namespaceId;
			// case 3:
			// return ((ObservationMetadata) element).description;
			// }
			// }
			return null;
		}

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		klab.dispose();
		super.dispose();
	}

}