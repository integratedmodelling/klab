package org.integratedmodelling.klab.rest;

import org.integratedmodelling.klab.api.kim.KimStyle;

/**
 * A styled token to properly show k.IM code outside of a configured editor.
 * 
 * @author Ferd
 *
 */
public class StyledKimToken {

    private String value;
    private KimStyle.Color color;
    private KimStyle.FontStyle font;
    private String description;
    private boolean needsWhitespaceBefore;
    private boolean needsWhitespaceAfter;
    
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public KimStyle.Color getColor() {
        return color;
    }
    public void setColor(KimStyle.Color color) {
        this.color = color;
    }
    public KimStyle.FontStyle getFont() {
        return font;
    }
    public void setFont(KimStyle.FontStyle font) {
        this.font = font;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isNeedsWhitespaceBefore() {
        return needsWhitespaceBefore;
    }
    public void setNeedsWhitespaceBefore(boolean needsWhitespaceBefore) {
        this.needsWhitespaceBefore = needsWhitespaceBefore;
    }
    public boolean isNeedsWhitespaceAfter() {
        return needsWhitespaceAfter;
    }
    public void setNeedsWhitespaceAfter(boolean needsWhitespaceAfter) {
        this.needsWhitespaceAfter = needsWhitespaceAfter;
    }

}
