package org.integratedmodelling.klab.ide.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.nebula.widgets.richtext.RichTextViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.rest.AuthorityIdentity;
import org.integratedmodelling.klab.rest.AuthorityQueryResponse;
import org.integratedmodelling.klab.rest.AuthorityReference;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.StringUtil;

public class AuthorityEditor extends Composite {

    private Text text;
    private Combo subAuthority;
    private Combo mainAuthority;
    private Label authDescription;
    private Map<String, AuthorityReference> authorities = new HashMap<>();
    protected AuthorityReference currentAuthority;
    private TableViewer resultList;
    private RichTextViewer description;
    private String currentCatalog;
    private TableColumn tblclmnCode;
    private TableColumn tblclmnLabel;
    private SashForm sashForm;

    class IdentityLabelProvider extends LabelProvider implements ITableLabelProvider {

        @Override
        public Image getColumnImage(Object element, int columnIndex) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String getColumnText(Object element, int columnIndex) {
            if (element instanceof AuthorityIdentity) {
                switch (columnIndex) {
                case 0: 
                    return ((AuthorityIdentity)element).getId();
                case 1: 
                    return ((AuthorityIdentity)element).getDescription();
                }
            }
            return null;
        }
        
    }
    
    public AuthorityEditor(Composite parent, int style) {
        super(parent, style);
        setLayout(new GridLayout(1, false));

        Composite composite = new Composite(this, SWT.NONE);
        GridLayout gl_composite = new GridLayout(4, false);
        gl_composite.verticalSpacing = 1;
        gl_composite.marginWidth = 0;
        gl_composite.marginHeight = 0;
        gl_composite.horizontalSpacing = 3;
        composite.setLayout(gl_composite);
        composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        composite.setBounds(0, 0, 32, 32);

        mainAuthority = new Combo(composite, SWT.READ_ONLY);
        mainAuthority.addSelectionListener(new SelectionAdapter(){
            @Override
            public void widgetSelected(SelectionEvent e) {
                resultList.setInput(null);
                currentAuthority = authorities.get(mainAuthority.getItem(mainAuthority.getSelectionIndex()));
                subAuthority.removeAll();
                if (currentAuthority != null && !currentAuthority.getSubAuthorities().isEmpty()) {
                    subAuthority.setEnabled(true);
                    for (Pair<String, String> sub : currentAuthority.getSubAuthorities()) {
                        subAuthority.add(sub.getFirst());
                    }
                    subAuthority.select(0);
                    currentCatalog = subAuthority.getItem(0);
                } else {
                    subAuthority.setEnabled(false);
                    currentCatalog = null;
                }
                text.setText("");
                setDocumentation();
            }
        });
        mainAuthority.setBounds(0, 0, 57, 20);

        subAuthority = new Combo(composite, SWT.READ_ONLY);
        subAuthority.addSelectionListener(new SelectionAdapter(){
            @Override
            public void widgetSelected(SelectionEvent e) {
                resultList.setInput(null);
                currentCatalog = subAuthority.getText();
                setDocumentation();
            }
        });
        subAuthority.setEnabled(false);
        subAuthority.setBounds(0, 0, 57, 20);

        text = new Text(composite, SWT.BORDER);
        text.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.keyCode == 13) {
                    search(text.getText());
                }
            }

        });
        text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        text.setBounds(0, 0, 41, 19);

        Button btnNewButton = new Button(composite, SWT.NONE);
        btnNewButton.setToolTipText("Copy the currently selected identity code to the clipboard");
        btnNewButton.setImage(ResourceManager.getPluginImage("org.eclipse.ui", "/icons/full/etool16/copy_edit.png"));
        btnNewButton.setBounds(0, 0, 70, 21);

        Composite composite_1 = new Composite(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
        composite_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        composite_1.setBounds(0, 0, 32, 32);
        
        sashForm = new SashForm(composite_1, SWT.NONE);
        sashForm.setSashWidth(0);
        
        description = new RichTextViewer(sashForm, SWT.BORDER);
        description.setWordSplitRegex("\\s|\\-");
        
        sashForm.setWeights(new int[] {1});

        resultList = new TableViewer(composite_1, SWT.BORDER | SWT.FULL_SELECTION);
        resultList.getTable().setLinesVisible(true);
        resultList.getTable().setHeaderVisible(true);
        
        resultList.setLabelProvider(new IdentityLabelProvider());
        resultList.setContentProvider(new IStructuredContentProvider(){
            
            @Override
            public Object[] getElements(Object inputElement) {
                if (inputElement instanceof AuthorityQueryResponse) {
                    return ((AuthorityQueryResponse)inputElement).getMatches().toArray();
                }
                return null;
            }
        });
        
        tblclmnCode = new TableColumn(resultList.getTable(), SWT.NONE);
        tblclmnCode.setWidth(100);
        tblclmnCode.setText("Code");
        
        tblclmnLabel = new TableColumn(resultList.getTable(), SWT.NONE);
        tblclmnLabel.setWidth(400);
        tblclmnLabel.setText("Label");

        authDescription = new Label(this, SWT.NONE);
        authDescription.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        authDescription.setBounds(0, 238, 56, 16);
        authDescription.setText("Choose an authority from the list");
    }


    // run in UI thread
    protected void setDocumentation() {
        authDescription.setText(
                currentAuthority == null ? "Choose an authority from the list" : currentAuthority.getDescription());
        
        StringBuffer desc = new StringBuffer(512);
        boolean first = true;
        for (String line : StringUtil.lines(currentAuthority == null ? "Choose an authority from the list" : currentAuthority.getDescription())) {
            if (first) {
                desc.append("<b>" + line + "</b><br/><br/>");
            } else {
                desc.append(line);
            }
            first = false;
        }
        
        if (currentCatalog != null) {

            desc.append("<br/><b>Classification " + currentCatalog + "</b><br/><br/>");
            
            for (Pair<String, String> sc : currentAuthority.getSubAuthorities()) {
                if (currentCatalog.equals(sc.getFirst())) {
                    desc.append(StringUtil.justifyLeft(sc.getSecond(), 80).replaceAll("\\r?\\n", "<br/>"));
                    break;
                }
            }
        }
        
        description.setText(desc.toString());
    }

    private void search(String text) {

        if (this.currentAuthority != null) {
            Activator.session().searchAuthority(this.currentAuthority.getName(), this.currentCatalog, text);
        }
    }
    
    public void displayMatches(AuthorityQueryResponse matches) {
        Display.getDefault().asyncExec(() -> {
            resultList.setInput(matches);
        });
    }

    public void setAuthorities(List<AuthorityReference> authorities) {

        this.authorities.clear();

        Display.getDefault().asyncExec(() -> {

            mainAuthority.removeAll();
            subAuthority.removeAll();
            subAuthority.setEnabled(false);
            text.setText("");

            if (authorities != null) {
                for (AuthorityReference authority : authorities) {
                    mainAuthority.add(authority.getName());
                    this.authorities.put(authority.getName(), authority);
                }
            }
        });
    }
}
