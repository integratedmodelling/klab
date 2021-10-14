package org.integratedmodelling.klab.ide.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.integratedmodelling.klab.api.kim.KimStyle;
import org.integratedmodelling.klab.rest.QueryStatusResponse;
import org.integratedmodelling.klab.rest.StyledKimToken;

/**
 * Shows a set of k.IM concepts with appropriate coloring and spacing, with the option of hiding
 * namespaces and an appropriate icon that can be dragged.
 * 
 * @author Ferd
 *
 */
public class StyledConceptDisplay extends Composite {

    boolean showNamespaces = true;
    private StyledText styledText;
    private Button btnNewButton;
    private Canvas lblNewLabel;

    Map<KimStyle.Color, Color> colors = new HashMap<>();

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
    public StyledConceptDisplay(Composite parent, int style) {
        super(parent, style);
        setLayout(new GridLayout(3, false));

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
        GridData gd_styledText = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        gd_styledText.heightHint = 37;
        styledText.setLayoutData(gd_styledText);

        btnNewButton = new Button(this, SWT.TOGGLE);
        btnNewButton.setSelection(true);
        btnNewButton.setImage(ResourceManager.getPluginImage("org.integratedmodelling.klab.ide",
                "icons/namespace-checked.png"));
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
