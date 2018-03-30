package org.integratedmodelling.klab.api.knowledge;

import org.integratedmodelling.kim.api.IParameters;

public interface IMetadata extends IParameters {

    /**
     * 
     */
    public static String       DC_NAME                        = "dc:name";
    /**
     * 
     */
    public static String       DC_LABEL                       = "dc:label";
    /**
     * 
     */
    public static String       DC_COMMENT                     = "dc:comment";
    /**
     * 
     */
    public static String       DC_DEFINITION                  = "dc:definition";
    /**
     * 
     */
    public static String       DC_SEEALSO                     = "dc:name";

    /**
     * DCMI point http://dublincore.org/documents/dcmi-point/ ISO 3166
     * http://www.din.de/gremien/nas/nabd/iso3166ma/codlstp1/index.html DCMI box
     * http://dublincore.org/documents/dcmi-box/ TGN http://shiva.pub.getty.edu/tgn_browser/
     */
    public static String       DC_COVERAGE_SPATIAL            = "dc:coverage-spatial";

    /**
     * DCMI period http://dublincore.org/documents/dcmi-period/ W3C-DTF http://www.w3.org/TR/NOTE-datetime
     */
    public static String       DC_COVERAGE_TEMPORAL           = "dc:coverage-temporal";

    /**
     * free text
     */
    public static String       DC_DESCRIPTION                 = "dc:description";

    /**
     * free text
     */
    public static String       DC_DESCRIPTION_TABLEOFCONTENTS = "dc:tableofcontents";

    /**
     * free text
     */
    public static String       DC_DESCRIPTION_ABSTRACT        = "dc:abstract";

    /**
     * DCMI type vocabulary http://dublincore.org/documents/dcmi-type-vocabulary/
     */
    public static String       DC_TYPE                        = "dc:type";

    /**
     * @deprecated use subclasses
     */
    @Deprecated
    public static String       DC_RELATION                    = "dc:relation";

    /**
     * URI http://www.ietf.org/rfc/rfc2396.txt
     */
    public static String       DC_RELATION_ISVERSIONOF        = "dc:isversionof";

    /**
     * URI
     */
    public static String       DC_RELATION_HASVERSION         = "dc:hasversion";

    /**
     * URI
     */
    public static String       DC_RELATION_ISREPLACEDBY       = "dc:isreplacedby";

    /**
     * URI
     */
    public static String       DC_RELATION_REPLACES           = "dc:replaces";

    /**
     * URI
     */
    public static String       DC_RELATION_ISREQUIREDBY       = "dc:isrequiredby";

    /**
     * URI
     */
    public static String       DC_RELATION_REQUIRES           = "dc:requires";

    /**
     * URI
     */
    public static String       DC_RELATION_ISPARTOF           = "dc:ispartof";

    /**
     * URI
     */
    public static String       DC_RELATION_HASPART            = "dc:haspart";

    /**
     * URI
     */
    public static String       DC_RELATION_ISREFERENCEDBY     = "dc:isreferencedby";

    /**
     * URI
     */
    public static String       DC_RELATION_REFERENCES         = "dc:references";

    /**
     * URI
     */
    public static String       DC_RELATION_ISFORMATOF         = "dc:isformatof";

    /**
     * URI
     */
    public static String       DC_RELATION_HASFORMAT          = "dc:hasformat";

    /**
     * URI
     */
    public static String       DC_SOURCE                      = "dc:source";

    /**
     * Vocabularies:
     * 
     * LCSH Library of Congress Subject Headings MeSH http://www.nlm.nih.gov/mesh/meshhome.html DDC
     * http://www.oclc.org/dewey/index.htm LCC http://lcweb.loc.gov/catdir/cpso/lcco/lcco.html UDC
     * http://www.udcc.org/
     */
    public static String       DC_SUBJECT                     = "dc:subject";
    public static String       DC_TITLE                       = "dc:title";
    public static String       DC_TITLE_ALTERNATIVE           = "dc:title-alternative";

    public static String       DC_CONTRIBUTOR                 = "dc:contributor";
    // TODO is this in DC?
    public static String       DC_URL                         = "dc:url";
    // TODO is this in DC?
    public static String       DC_ORIGINATOR                  = "dc:originator";
    public static String       DC_CREATOR                     = "dc:creator";
    public static String       DC_PUBLISHER                   = "dc:publisher";
    public static String       DC_RIGHTS                      = "dc:rights";

    /**
     * DCMI period http://dublincore.org/documents/dcmi-period/ W3C-DTF http://www.w3.org/TR/NOTE-datetime
     */
    public static String       DC_DATE_CREATED                = "dc:date-created";
    /**
     * DCMI period W3C-DTF
     */
    public static String       DC_DATE_VALID                  = "dc:date-valid";
    /**
     * DCMI period W3C-DTF
     */
    public static String       DC_DATE_AVAILABLE              = "dc:date-available";
    /**
     * DCMI period W3C-DTF
     */
    public static String       DC_DATE_ISSUED                 = "dc:date-issued";
    /**
     * DCMI period W3C-DTF
     */
    public static String       DC_MODIFIED                    = "dc:modified";

    public static String       DC_FORMAT_EXTENT               = "dc:format-extent";

    /**
     * http://www.isi.edu/in-notes/iana/assignments/media-types/media-types
     */
    public static String       DC_FORMAT_MEDIUM               = "dc:format-medium";

    public static String       DC_IDENTIFIER                  = "dc:identifier";

    /**
     * ISO639-2 http://www.w3.org/TR/NOTE-datetime RFC1766 http://www.ietf.org/rfc/rfc1766.txt
     */
    public static final String DC_LANGUAGE                    = "dc:language";

    public static final String IM_NAME                        = "im:name";
    public static final String IM_MIN_SPATIAL_SCALE           = "im:min-spatial-scale";
    public static final String IM_MAX_SPATIAL_SCALE           = "im:max-spatial-scale";
    public static final String IM_MIN_TEMPORAL_SCALE          = "im:min-temporal-scale";
    public static final String IM_MAX_TEMPORAL_SCALE          = "im:max-temporal-scale";

    /**
     * Bounding box - expected to point to a PolygonValue
     */
    public static final String GEOSPACE_BOUNDING_BOX          = "geospace:hasBoundingBox";

    /*
     * fields for state. These end up in metadata at the server side, and are serialized
     * for transfer to clients.
     */
    public static final String STATE_HISTOGRAM                = "state:histogram";
    public static final String STATE_BOUNDARIES               = "state:boundaries";
    public static final String STATE_COLORMAP                 = "state:colormap";
    public static final String STATE_DISPLAY_DATA             = "state:display-data";
    public static final String STATE_CLASS                    = "state:class";
    public static final String STATE_STATED_BOUNDARIES        = "state:stated-boundaries";

    /*
     * KLAB-specific, for visualization and display
     */
    public static final String KLAB_LINE_COLOR                = "klab:linecolor";
    public static final String KLAB_FILL_COLOR                = "klab:fillcolor";
    public static final String KLAB_OPACITY                   = "klab:opacity";


}
