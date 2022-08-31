package org.integratedmodelling.klab.ide.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.integratedmodelling.klab.api.kim.KimStyle;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.rest.QueryStatusResponse;
import org.integratedmodelling.klab.rest.SearchMatch;
import org.integratedmodelling.klab.rest.SearchRequest;
import org.integratedmodelling.klab.rest.SearchRequest.Mode;
import org.integratedmodelling.klab.rest.SearchResponse;
import org.integratedmodelling.klab.rest.StyledKimToken;

/**
 * Shows a set of k.IM concepts with appropriate coloring and spacing, with the option of hiding
 * namespaces and an appropriate icon that can be dragged.
 * 
 * @author Ferd
 *
 */
public class StyledConceptEditor extends Composite {

    boolean showNamespaces = true;
    private StyledText styledText;
    private Button btnNewButton;
    private Canvas lblNewLabel;
    private String contextId = null;
    private long requestId;
    private List<SearchMatch> accepted = new ArrayList<>();
    private List<SearchMatch> matches = new ArrayList<>();

    Map<KimStyle.Color, Color> colors = new HashMap<>();
    private Text text;

    private Color getColor(KimStyle.Color color) {
        Color ret = colors.get(color);
        if (ret == null) {
            ret = new Color(getDisplay(), color.rgb[0], color.rgb[1], color.rgb[2]);
            colors.put(color, ret);
        }
        return ret;
    }

    private int getFont(KimStyle.FontStyle font) {
        switch(font) {
        case BOLD:
            return SWT.BOLD;
        case BOLD_ITALIC:
            return SWT.BOLD | SWT.ITALIC;
        case ITALIC:
            return SWT.ITALIC;
        default:
            break;
        }
        return SWT.NORMAL;
    }

    /**
     * Create the composite.
     * 
     * @param parent
     * @param style
     */
    public StyledConceptEditor(Composite parent, int style) {
        super(parent, style);
        setLayout(new GridLayout(4, false));

        lblNewLabel = new Canvas(this, SWT.NO_REDRAW_RESIZE);
        GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_lblNewLabel.heightHint = 28;
        gd_lblNewLabel.widthHint = 28;
        lblNewLabel.setLayoutData(gd_lblNewLabel);
        lblNewLabel.addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {
                Rectangle clientArea = lblNewLabel.getClientArea();
                e.gc.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
             e.gc.fillOval(0,0,clientArea.width,clientArea.height);
            }
        });
        
        styledText = new StyledText(this, SWT.READ_ONLY | SWT.SINGLE);
        styledText.setLeftMargin(3);
        styledText.setTopMargin(3);
        styledText.setEnabled(false);
        GridData gd_styledText = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
        gd_styledText.heightHint = 37;
        styledText.setLayoutData(gd_styledText);
        
        text = new Text(this, SWT.BORDER);
        text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        text.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.keyCode == SWT.ARROW_DOWN) {
//                    treeViewer.getTree().forceFocus();
                } else if (e.keyCode == SWT.ESC) {
                    reset();
                } else if (text.getText().trim().isEmpty()) {
                    clearMatches();
                } else {
                    search(text.getText());
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (text.getText().isEmpty() && e.keyCode == SWT.BS) {
                    removeLastMatch();
                } else if (e.character == '(') {
                    e.doit = false;
                    openParenthesis();
                } else if (e.character == ')') {
                    e.doit = false;
                    closeParenthesis();
                }
            }

        });
        text.addSelectionListener(new SelectionListener(){

            @Override
            public void widgetSelected(SelectionEvent e) {
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                if (text.getText().isEmpty() && !accepted.isEmpty() /* TODO and have observable */) {
                    observeMatching();
                }
            }
        });
        btnNewButton = new Button(this, SWT.TOGGLE);
        btnNewButton.setSelection(true);
        btnNewButton.setImage(ResourceManager.getPluginImage("org.integratedmodelling.klab.ide",
                "icons/namespace-checked.png"));
    }
    
    protected void search(String text2) {

        if (text2.trim().isEmpty()) {
            return;
        }

        showSearchResults(true);
        matches.clear();

        SearchRequest request = new SearchRequest();

        request.setMaxResults(50);
        request.setQueryString(text2);
        request.setSearchMode(Mode.SEMANTIC);
        request.setContextId(this.contextId);
        request.setRequestId(this.requestId++);

        Activator.post((message) -> {
            SearchResponse response = message.getPayload(SearchResponse.class);
            this.matches.addAll(response.getMatches());
            this.contextId = response.getContextId();
            Display.getDefault().asyncExec(() -> {
//                treeViewer.setInput(matches);
            });
        }, IMessage.MessageClass.Search, IMessage.Type.SemanticSearch, request);

	}

	private void showSearchResults(boolean show) {
		// TODO Auto-generated method stub
		
	}

	protected void closeParenthesis() {

        SearchRequest request = new SearchRequest();
        request.setSearchMode(Mode.CLOSE_SCOPE);
        request.setContextId(this.contextId);
        Activator.post(IMessage.MessageClass.Search, IMessage.Type.SemanticSearch, request);
    }

    protected void openParenthesis() {

        SearchRequest request = new SearchRequest();
        request.setSearchMode(Mode.OPEN_SCOPE);
        request.setContextId(this.contextId);
        Activator.post(IMessage.MessageClass.Search, IMessage.Type.SemanticSearch, request);
    }
    
    private void removeLastMatch() {

        SearchRequest request = new SearchRequest();
        request.setSearchMode(Mode.UNDO);
        request.setContextId(this.contextId);

        Activator.post(IMessage.MessageClass.Search, IMessage.Type.SemanticSearch, request);

    }

    protected void observeMatching() {
        Activator.session().observe(getMatchedText());
    }
    @Override
    protected void checkSubclass() {
        // Disable the check that prevents subclassing of SWT components
    }

    public void reset() {
        Display.getDefault().asyncExec(() -> {
            styledText.setText("");
        });
    }
    
    private String getMatchedText() {
        String ret = "";
        for (SearchMatch match : accepted) {
            ret += (ret.isEmpty() ? "" : " ") + match.getId();
        }
        return ret;
    }

    private void clearMatches() {
//        matches.clear();
//        showSearchResults(false);
//        treeViewer.setInput(matches);
    }
    
    public void setStatus(QueryStatusResponse status) {

        Display.getDefault().asyncExec(() -> {

            styledText.setText("");

            List<StyleRange> styles = new ArrayList<>();
            StringBuffer text = new StringBuffer(512);
            StyledKimToken previous = null;
            for (StyledKimToken token : status.getCode()) {
                if (token.isNeedsWhitespaceBefore() && text.length() > 0) {
                    // there's probably a more readable way to say this
                    if (!(previous != null && !previous.isNeedsWhitespaceAfter())) {
                        text.append(" ");
                    }
                }
                int a = text.length();
                text.append(token.getValue());
                int b = text.length();
                if (token.getColor() != null && token.getFont() != null) {
                    StyleRange style = new StyleRange();
                    style.start = a;
                    style.length = b - a;
                    style.foreground = getColor(token.getColor());
                    style.fontStyle = getFont(token.getFont());
                    styles.add(style);
                }
                previous = token;
            }

            styledText.setText(text.toString());
            for (StyleRange style : styles) {
                styledText.setStyleRange(style);
            }
            
            if (status.getCurrentType() != null) {
                lblNewLabel.redraw();
            }

        });
    }

    @Override
    public void dispose() {
        for (Color color : colors.values()) {
            color.dispose();
        }
        super.dispose();
    }

}
