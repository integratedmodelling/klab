package org.integratedmodelling.klab.ide.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.integratedmodelling.klab.rest.AuthorityReference;
import org.integratedmodelling.klab.utils.Pair;

public class AuthorityEditor extends Composite {

    private Text text;
    private Combo subAuthority;
    private Combo mainAuthority;
    private Label authDescription;
    private Map<String, AuthorityReference> authorities = new HashMap<>();
    protected AuthorityReference currentAuthority;

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
                currentAuthority = authorities.get(mainAuthority.getItem(mainAuthority.getSelectionIndex()));
                authDescription.setText(currentAuthority == null ? "Choose an authority from the list" : currentAuthority.getDescription());
                subAuthority.removeAll();
                if (currentAuthority != null && !currentAuthority.getSubAuthorities().isEmpty()) {
                    subAuthority.setEnabled(true);
                    for (Pair<String, String> sub : currentAuthority.getSubAuthorities()) {
                        subAuthority.add(sub.getFirst());
                    }
                } else {
                    subAuthority.setEnabled(false);
                }
                text.setText("");
            }
        });
        mainAuthority.setBounds(0, 0, 57, 20);

        subAuthority = new Combo(composite, SWT.READ_ONLY);
        subAuthority.addSelectionListener(new SelectionAdapter(){
            @Override
            public void widgetSelected(SelectionEvent e) {
            }
        });
        subAuthority.setEnabled(false);
        subAuthority.setBounds(0, 0, 57, 20);

        text = new Text(composite, SWT.BORDER);
        text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        text.setBounds(0, 0, 41, 19);

        Button btnNewButton = new Button(composite, SWT.NONE);
        btnNewButton.setToolTipText("Copy the currently selected identity code to the clipboard");
        btnNewButton.setImage(ResourceManager.getPluginImage("org.eclipse.ui", "/icons/full/etool16/copy_edit.png"));
        btnNewButton.setBounds(0, 0, 70, 21);

        ScrolledComposite composite_1 = new ScrolledComposite(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        composite_1.setExpandVertical(true);
        composite_1.setExpandHorizontal(true);
        composite_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        composite_1.setBounds(0, 0, 32, 32);

        authDescription = new Label(this, SWT.NONE);
        authDescription.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        authDescription.setBounds(0, 238, 56, 16);
        authDescription.setText("Choose an authority from the list");
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
