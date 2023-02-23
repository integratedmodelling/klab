/*******************************************************************************
 * Copyright (C) 2007, 2015:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any other
 * authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable modular,
 * collaborative, integrated development of interoperable data and model components. For details,
 * see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any warranty; without
 * even the implied warranty of merchantability or fitness for a particular purpose. See the Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA. The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.api.services.runtime;

import java.util.List;

import org.integratedmodelling.klab.api.collections.IParameters;

/**
 * A report contains a DAG of sections.
 * 
 * @author Ferd
 *
 */
public interface IReport {

    public enum Encoding {
        MARKDOWN, HTML, PDF, LATEX
    }

    public enum View {
        REPORT, FIGURES, TABLES, RESOURCES, MODELS, PROVENANCE, REFERENCES
    }

    /**
     * Roles and titles of main sections, also providing the ordering for the default report
     * template.
     * 
     * @author ferdinando.villa
     *
     */
    public enum SectionRole {
        INTRODUCTION, METHODS, RESULTS, DISCUSSION, CONCLUSIONS, NOTES, REFERENCES, APPENDIX
    };

    /**
     * Sections represent any element of the report, including title, footnotes, figures, tables and
     * references. Each may have structured content (accessible through the {@link IParameters} API)
     * and/or type-specific content, not exposed in the API. Sections can have children and
     * references to other sections.
     * 
     * @author Ferd
     *
     */
    interface Section {

        /**
         * 
         * @author ferdinando.villa
         *
         */
        public enum Type {
            TITLE, BODY, HEADER, FOOTER, FOOTNOTE, CODE, INFO, WARNING, ERROR, FIGURE, TABLE, REFERENCE, LINK, TAGSECTION, CUSTOM
        }

        /**
         * ID of the section, unique and not for user consumption.
         * 
         * @return
         */
        String getId();

        /**
         * Displayable name.
         * 
         * @return
         */
        String getName();

        /**
         * One of the report section roles or null. Root-level sections originating from
         * documentation must have this set.
         * 
         * @return
         */
        SectionRole getRole();

    }

    /**
     * Return all root sections in the order established by the current report template.
     * 
     * @return
     */
    List<Section> getSections();

}
