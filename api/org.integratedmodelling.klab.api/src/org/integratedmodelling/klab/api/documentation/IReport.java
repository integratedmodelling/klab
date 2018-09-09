/*******************************************************************************
 * Copyright (C) 2007, 2015:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any
 * other authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable
 * modular, collaborative, integrated development of interoperable data and model
 * components. For details, see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms
 * of the Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any
 * warranty; without even the implied warranty of merchantability or fitness for a
 * particular purpose. See the Affero General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this
 * program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite
 * 330, Boston, MA 02111-1307, USA. The license is also available at:
 * https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.api.documentation;

import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.runtime.IComputationContext;

/**
 * A report contains a DAG of sections.
 * 
 * @author Ferd
 *
 */
public interface IReport {

    /**
     * Sections represent any element of the report, including title, footnotes, figures, tables and
     * references. Each may have structured content (accessible through the {@link IParameters} API) 
     * and/or type-specific content, not exposed in the API. Sections can have children and references
     * to other sections.
     * 
     * @author Ferd
     *
     */
    interface ISection extends IParameters<String> {
        
        enum Type {
            TITLE,
            HEADER,
            FOOTER,
            FOOTNOTE,
            FIGURE,
            TABLE,
            REFERENCE
        }

        /**
         * ID of the section, unique and not for user consumption.
         * 
         * @return
         */
        String getId();
        
        /**
         * Render to target language in passed context.
         * 
         * @param context
         * @return
         */
        String render(IComputationContext context);
        
        /**
         * Child sections in appropriate order.
         * 
         * @return
         */
        List<ISection> getChildren();

        /**
         * IDs of all sections referenced in this section.
         * 
         * @return
         */
        List<String> getReferences();
    }

    /**
     * Get all the root-level sections for the report.
     * 
     * @return
     */
    List<ISection> getSections();

    /**
     * Get a specific section by ID.
     * 
     * @param id
     * @return
     */
    ISection getSection(String id);
    
    /**
     * Render to the target language.
     * 
     * @return
     */
    String render(IComputationContext context);
    
    // /**
    // * Set the report title.
    // *
    // * @param title
    // */
    // void setTitle(String title);
    //
    // /**
    // *
    // * @return
    // */
    // String getTitle();
    //
    // /**
    // * Load references and any other relevant field from the passed documentation.
    // *
    // * @param documentation
    // */
    // void loadDocumentation(IDocumentation documentation);
    //
    // /**
    // * Write string directly to report.
    // *
    // * @param markdown
    // */
    // void write(String markdown);
    //
    // /**
    // * Add a link - either to an anchor created by {@link #getAnchor()} or
    // * {@link IReport#addAttachment(Object)} or to a passed URL.
    // *
    // * @param markdown
    // */
    // void writeLink(String markdown, String anchorOrUrl);
    //
    // /**
    // * Write string followed by newline.
    // *
    // * @param markdown
    // */
    // void writeln(String markdown);
    //
    // /**
    // * Reference the passed refs.
    // *
    // * @param ref
    // */
    // void reference(String... ref);
    //
    //
    // /**
    // * Insert a section if necessary, possibly along with its parents, and set it to the
    // * current one. Any writing will be done on it until the next section() is called. Use
    // * slash separators to specify sub-sections. Once one section has been added, no more
    // * writing can happen on the main body. Sections can be rearranged (when implemented)
    // * but not deleted.
    // *
    // * @param section
    // * @param path
    // */
    // void setSection(String path);
    //
    // /**
    // * Return the html correspondent to the report so far.
    // *
    // * @return
    // */
    // String asHTML();
    //
    // /**
    // * Turn the report into ASCII text and return it.
    // *
    // * @return
    // */
    // String asText();
    //
    // /**
    // * Attach object to report. Return the anchor to insert in the text if wanted.
    // *
    // */
    // String addAttachment(Object o);
    //
    // /**
    // * Insert an anchor to this point in the text and return it for
    // * future references.
    // *
    // * @return
    // */
    // String getReference();
    //
    // /**
    // * Insert predefined description of passed object, or nothing if we don't
    // * know what to do with it. Implementations should provide a way to configure
    // * description templates. In general this should know what to do with all
    // * types of knowledge - both concepts and observations.
    // *
    // * @param o
    // */
    // void describe(Object o);

}
