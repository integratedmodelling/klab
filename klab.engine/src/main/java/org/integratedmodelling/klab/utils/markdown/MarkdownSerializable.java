package org.integratedmodelling.klab.utils.markdown;

/**
 * Created by steppschuh on 07/10/2016.
 */

public interface MarkdownSerializable {

    MarkdownElement toMarkdownElement() throws MarkdownSerializationException;

}
