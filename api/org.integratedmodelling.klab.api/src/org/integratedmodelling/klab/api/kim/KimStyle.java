package org.integratedmodelling.klab.api.kim;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.api.knowledge.IConcept;

public class KimStyle {

    public static KimStyle getStyle(IConcept concept) {

        Color color = Color.UNKNOWN;
        FontStyle fontStyle = FontStyle.NORMAL;

        if (concept.is(IKimConcept.Type.DOMAIN)) {
            color = Color.DOMAIN;
        } else if (concept.is(IKimConcept.Type.QUALITY)) {
            color = Color.QUALITY;
        } else if (concept.is(IKimConcept.Type.SUBJECT)) {
            color = Color.SUBJECT;
        } else if (concept.is(IKimConcept.Type.AGENT)) {
            color = Color.SUBJECT;
        } else if (concept.is(IKimConcept.Type.RELATIONSHIP)) {
            color = Color.RELATIONSHIP;
        } else if (concept.is(IKimConcept.Type.EXTENT)) {
            color = Color.EXTENT;
        } else if (concept.is(IKimConcept.Type.EVENT)) {
            color = Color.EVENT;
        } else if (concept.is(IKimConcept.Type.PROCESS)) {
            color = Color.PROCESS;
        } else if (concept.is(IKimConcept.Type.NOTHING)) {
            color = Color.ERROR;
        } else if (concept.is(IKimConcept.Type.TRAIT)) {
            color = Color.TRAIT;
        } else if (concept.is(IKimConcept.Type.ROLE)) {
            color = Color.ROLE;
        }
        
        if (concept.isAbstract()) {
            fontStyle = FontStyle.ITALIC;
        }
        
        return new KimStyle(color, fontStyle);
    }

    public enum FontStyle {
        ITALIC,
        NORMAL,
        BOLD,
        BOLD_ITALIC
    }

    public enum Color {

        DOMAIN(new int[]{255, 255, 255}),
        CONFIGURATION(new int[]{0, 100, 100}),
        EVENT(new int[]{153, 153, 0}),
        EXTENT(new int[]{0, 153, 153}),
        PROCESS(new int[]{204, 0, 0}),
        QUALITY(new int[]{0, 204, 0}),
        RELATIONSHIP(new int[]{210, 170, 0}),
        TRAIT(new int[]{0, 102, 204}),
        ROLE(new int[]{0, 86, 163}),
        SUBJECT(new int[]{153, 76, 0}),
        LIVE_URN(new int[]{0, 102, 0}),
        INACTIVE_URN(new int[]{255, 215, 0}),
        ERROR(new int[]{255, 0, 0}),
        UNKNOWN(new int[]{128, 128, 128}),
        INACTIVE(new int[]{160, 160, 160}),
        VERSION(new int[]{0, 153, 153}),
        VALUE_OPERATOR(new int[]{0, 0, 0}),
        UNARY_OPERATOR(new int[]{0, 0, 0}),
        BINARY_OPERATOR(new int[]{0, 0, 0}),
        SEMANTIC_MODIFIER(new int[]{0, 0, 0});

        public int[] rgb;

        Color(int[] rgb) {
            this.rgb = rgb;
        }

    }

    public KimStyle() {
    }

    public KimStyle(Color color, FontStyle fontStyle) {
        this.color = color;
        this.fontStyle = fontStyle;
    }

    private FontStyle fontStyle = FontStyle.NORMAL;
    private Color color = Color.UNKNOWN;

    public FontStyle getFontStyle() {
        return fontStyle;
    }
    public void setFontStyle(FontStyle fontStyle) {
        this.fontStyle = fontStyle;
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }

}
