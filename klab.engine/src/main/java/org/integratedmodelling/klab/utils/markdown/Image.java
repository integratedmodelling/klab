package org.integratedmodelling.klab.utils.markdown;

public class Image extends Link {

    public Image(Object text, String url) {
        super(text, url);
    }

    public Image(String url) {
        this(url, url);
    }

    @Override
    public String serialize() throws MarkdownSerializationException {
        return "!" + super.serialize();
    }

}
