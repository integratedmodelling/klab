package org.integratedmodelling.klab.rest;

import org.integratedmodelling.kim.api.BinarySemanticOperator;
import org.integratedmodelling.kim.api.SemanticModifier;
import org.integratedmodelling.kim.api.UnarySemanticOperator;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.klab.api.data.mediation.ICurrency;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.kim.KimStyle;
import org.integratedmodelling.klab.api.kim.KimStyle.FontStyle;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;

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

    public static StyledKimToken create(Object c) {
        return create(c, false);
    }
    
    public static StyledKimToken unknown() {

        StyledKimToken ret = new StyledKimToken();
        ret.needsWhitespaceBefore = true;
        ret.needsWhitespaceAfter = true;
        ret.value = "?";
        return ret;
    }

    /**
     * Recognizes the type and fills in the style automatically. Only acceptable string token is
     * open/closed parenthesis, everything else must be the actual object. May throw exceptions if
     * used inappropriately.
     * 
     * @param c
     * @param alternative selects the "second" form if any, e.g. the comparison 'of' in ratio ... of
     *        ...
     * @return
     */
    public static StyledKimToken create(Object c, boolean alternative) {

        StyledKimToken ret = new StyledKimToken();
        ret.needsWhitespaceBefore = true;
        ret.needsWhitespaceAfter = true;
        ret.color = KimStyle.Color.UNKNOWN;
        
        if (c instanceof IConcept) {
            ret.value = c.toString();
            KimStyle style = KimStyle.getStyle((IConcept)c);
            ret.color = style.getColor();
            ret.font = style.getFontStyle();
            ret.description = ((IConcept) c).getMetadata().get(IMetadata.DC_COMMENT, String.class);
        } else if (c instanceof ValueOperator) {
            ret.value = ((ValueOperator) c).declaration;
        } else if (c instanceof SemanticModifier) {
            ret.value = ((SemanticModifier) c).declaration[alternative ? 1 : 0];
            ret.font = FontStyle.BOLD;
        } else if (c instanceof UnarySemanticOperator) {
            ret.value = ((UnarySemanticOperator) c).declaration[alternative ? 1 : 0];
            ret.font = FontStyle.BOLD;
        } else if (c instanceof BinarySemanticOperator) {
            ret.value = ((BinarySemanticOperator)c).name().toLowerCase();
            ret.font = FontStyle.BOLD;
        } else if (c instanceof String) {

            if ("(".equals(c) || ")".equals(c)) {
                ret.value = c.toString();
                ret.needsWhitespaceBefore = "(".equals(c);
                ret.needsWhitespaceAfter = ")".equals(c);
            } else if ("in".equals(c) || "per".equals(c)) {
                ret.value = c.toString();
                ret.font = FontStyle.BOLD;
            } else {
                throw new KlabIllegalArgumentException("token " + c + " is not a recognized k.IM token");
            }

        } else if (c instanceof IUnit) {
            ret.value = c.toString();
        } else if (c instanceof ICurrency) {
            ret.value = c.toString();
        } else {
            throw new KlabIllegalArgumentException("token " + c + " is not a recognized k.IM token");
        }
        
        return ret;
    }

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
