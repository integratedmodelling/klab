/*******************************************************************************
 *  Copyright (C) 2007, 2014:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.services.reasoner.owl;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Objects;


/**
 * <p>
 * All relevant knowledge objects accessed through the Knowledge Manager are
 * identified by a semantic type, whose form is a string with two names
 * separated by a colon. The first part identifies the "concept space", the
 * second the local ID within the concept space (local name). Each semantic type
 * must be unique in the KM.
 * </p>
 * 
 * <p>At this time using a semantic type is not required in the knowledge API (all 
 * access is through string or knowledge, to avoid unordinate replication of methods) 
 * but semantic types can be used for deferred evaluation of concepts, validation, and
 * general tagging of IDs that are expected to correspond to knowledge. They also
 * compare to strings, knowledge objects and other semantic types, and 
 * can be used as keys. So they're ideal as "string" keys in maps that
 * are expected to correspond to knowledge even if there is not a corresponding
 * ontology loaded in the knowledge manager. They have no dependency on
 * actual knowledge implementations, so they can be created as fields in
 * an API interface.</p>
 * 
 * <p>
 * Objects that are identified by semantic types are: Concepts, Properties, and
 * Instances. Note that Ontologies as such are not exposed by the KM interface,
 * and the equivalence between the concept space ID and an ontology is not to be
 * relied upon. Even if in the current implementation a concept space ID maps
 * directly to an ontology URI, this may change and more sophisticated and
 * useful notions of a concept space may emerge. Ontologies are only a concern
 * within the physical model of the knowledge base, which should not be seen by
 * users.
 * </p>
 * 
 * @author Ferdinando Villa
 * @author Ioannis N. Athanasiadis
 * 
 * @see IKimConceptStatement
 */
public class QualifiedName implements Serializable {

    private static final long serialVersionUID = -647901196357082946L;

    String conceptSpace;
    String localName;

    private boolean correct = true;

    private void assign(String s) throws UnsupportedEncodingException {
        String[] ss = splitIdentifier(s);
        if (ss == null) {
            throw new UnsupportedEncodingException(s);
        }

        conceptSpace = ss[0];
        localName = ss[1];
    }

    public static QualifiedName create(String s) {
    	return new QualifiedName(s);
    }
    
    /**
     * Split the identifier string into conceptSpace and localName. Validate during processing,
     * and return null if not valid.
     *
     * @param s
     * @return identifiers
     */
    public static String[] splitIdentifier(String s) {
        String[] ss = s.split(":");
        if (ss.length != 2 || ss[0].trim().equals("") || ss[1].trim().equals("")) {
            return null;
        }
        return ss;
    }

    /**
     * Returns a Semantic Type with the same content as the given one.
     * 
     * @param s
     */
    public QualifiedName(QualifiedName s) {
        conceptSpace = s.conceptSpace;
        localName = s.localName;
    }


    /**
     * Default constructor from a String. Note that the String has to be in the
     * form: conceptSpace:LocalName
     * 
     * @param s
     */
    public QualifiedName(String s) {
        try {
            assign(s);
        } catch (UnsupportedEncodingException e) {
            correct = false;
        }
    }
    
    public boolean isCorrect() {
        return correct;
    }

    /**
     * Default constructor from two Strings: first is the Concept Space, second
     * is the Local Name. Both arguments should not contain a semicolon.
     * 
     * @param conceptSpace
     * @param localName
     */
    public QualifiedName(String conceptSpace, String localName) {
        this.conceptSpace = conceptSpace;
        this.localName = localName;
    }

    @Override
    public String toString() {
        return conceptSpace + ":" + localName;
    }

    public String getNamespace() {
        return conceptSpace;
    }

    public String getName() {
        return localName;
    }

    /**
     * Use to check if the string is a valid semantic type. Only checks syntax;
     * ontology or concept may not exist.
     * 
     * @param t
     *            a string to validate
     * @return true if valid token in the form "ontology:resource_id"
     */
    public static boolean validate(String t) {
        String[] ss = splitIdentifier(t);
        return ss != null && ss.length == 2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(conceptSpace, localName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        QualifiedName other = (QualifiedName) obj;
        return Objects.equals(conceptSpace, other.conceptSpace) && Objects.equals(localName, other.localName);
    }

    

}
