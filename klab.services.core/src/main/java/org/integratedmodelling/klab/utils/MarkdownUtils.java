//package org.integratedmodelling.klab.utils;
//
//import java.util.Arrays;
//
//import com.vladsch.flexmark.ast.Node;
//import com.vladsch.flexmark.ext.attributes.AttributesExtension;
//import com.vladsch.flexmark.ext.definition.DefinitionExtension;
//import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceExtension;
//import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
//import com.vladsch.flexmark.ext.media.tags.MediaTagsExtension;
//import com.vladsch.flexmark.ext.tables.TablesExtension;
//import com.vladsch.flexmark.html.HtmlRenderer;
//import com.vladsch.flexmark.parser.Parser;
//import com.vladsch.flexmark.util.options.MutableDataSet;
//
//public enum MarkdownUtils {
//
//    INSTANCE;
//
//    MutableDataSet options = new MutableDataSet().set(Parser.EXTENSIONS,
//            Arrays.asList(FootnoteExtension.create(), AttributesExtension.create(),
//                    EnumeratedReferenceExtension.create(), MediaTagsExtension.create(), DefinitionExtension.create(),
//                    TablesExtension.create()));
//
//    Parser parser = Parser.builder(options).build();
//    HtmlRenderer renderer = HtmlRenderer.builder(options).build();
//
//    public String format(String markdown) {
//        Node document = parser.parse(markdown);
//        return renderer.render(document);
//    }
//
//}
