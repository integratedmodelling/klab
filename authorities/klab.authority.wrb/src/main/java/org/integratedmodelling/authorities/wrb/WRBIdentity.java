package org.integratedmodelling.authorities.wrb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.AuthorityIdentity;

/**
 * Intermediate representation for a WRB identity, with methods to build a trait
 * composition that represents it semantically in the terms of the CREA WRB vocabulary.
 */
public class WRBIdentity {


    /**
     * Each concept has one or more optional specifier, looked up sequentially.
     * 
     * @author Ferd
     *
     */
    public static class WRBConcept {

        public String getMainConcept() {
            return mainConcept;
        }

        public void setMainConcept(String mainConcept) {
            this.mainConcept = mainConcept;
        }

        public List<String> getSpecifiers() {
            return specifiers;
        }

        public void setSpecifiers(List<String> specifiers) {
            this.specifiers = specifiers;
        }

        private String       mainConcept;
        private List<String> specifiers;

        @Override
        public String toString() {
            String ret = "";
            if (specifiers != null) {
                for (String specifier : specifiers) {
                    ret += specifier;
                }
            }
            ret += (ret.isEmpty() ? mainConcept : StringUtils.uncapitalize(mainConcept));
            return ret;
        }

        /**
         * Return a short ID suitable for being used in building a concept identifier.
         * 
         * @return
         */
        public String getShortId() {

            String ret = "";
            if (specifiers != null) {
                for (String specifier : specifiers) {
                    ret += WRBVocabulary.get().getSpecifierShortId(specifier);
                }
            }

            /*
             * may be a qualifier or a soil type
             */
            if (WRBVocabulary.get().isQualifier(mainConcept)) {
                ret += WRBVocabulary.get().getQualifierShortId(mainConcept);
            } else {
                ret += WRBVocabulary.get().getRSGShortId(mainConcept);
            }
            return ret;
        }
    }

    /**
     * Main concept (first level)
     */
    private WRBConcept       rsg;

    /**
     * Qualifiers (second level)
     */
    private List<WRBConcept> qualifiers = new ArrayList<>();

    private boolean          specMode;

    private boolean          error;

    private Set<String>      errors     = new HashSet<>();

    /**
     * Invoke validation (just syntax for now, later composition). If valid, pass through
     * without exceptions. Otherwise, throw the exception that contains the explanation
     * and the identity itself.
     * 
     * @throws WRBValidationException
     */
    public void validate() throws KlabValidationException {

        /*
         * 1st check: main must exist and come from known vocabulary for both specifier
         * (if any) and main concept
         */
    }

    @Override
    public String toString() {

        if (rsg == null) {
            return "";
        }

        if (errors.size() > 0) {
            String ret = "Error in parsing: ";
            for (String e : errors) {
                ret += "\n  " + e;
            }
            return ret;
        }

        String ret = "";
        if (qualifiers.size() > 0) {
            ret += qualifiers.get(0);
        }

        ret += (ret.isEmpty() ? "" : " ") + rsg;

        if (qualifiers.size() > 1) {
            ret += " (";

            for (int i = 1; i < qualifiers.size(); i++) {
                if (i > 1) {
                    ret += ", ";
                }
                ret += qualifiers.get(i);
            }

            ret += ")";
        }

        return ret;
    }

    /**
     * Return the shortest possible stable identifier for the identity. This is stable as
     * long as the vocabulary URIs remain stable.
     * 
     * @return
     */
    public String getShortId() {

        List<String> qs = new ArrayList<>();
        for (WRBConcept q : qualifiers) {
            qs.add(q.getShortId());
        }

        Collections.sort(qs);

        String ret = "";

        for (String q : qs) {
            ret += q;
        }

        ret += (ret.isEmpty() ? "" : "_") + rsg.getShortId();

        return ret;
    }

    /*
     * --- parsing methods, invoked by parser.
     */

    public void openSpecifierGroup() {
        specMode = true;
    }

    public void closeSpecifierGroup() {
        if (!specMode) {
            error("syntax error: mismatched parentheses");
        }
        specMode = false;
    }

    public void closeParsing() {
        if (specMode) {
            error("syntax error: mismatched parentheses");
        }
    }

    public void addToken(String token) {

        String group = WRBParser.getGroupTerm(token);
        if (group != null) {
            if (rsg != null) {
                error("cannot have two soil group identifiers: " + group + " and " + rsg);
            } else {
                rsg = new WRBConcept();
                rsg.mainConcept = group;
            }

        } else {

            try {

                WRBConcept spec = WRBParser.chomp(token);

                if (spec.specifiers != null && spec.specifiers.size() > 1) {
                    error("cannot use more than one specifier in " + spec);
                }
                if (qualifiers.size() > 0 && !specMode) {
                    error("syntax error: more than one qualifier without postfix parenthesized list");
                } else {
                    qualifiers.add(spec);
                }
            } catch (KlabValidationException e) {
                error(e.getMessage());
            }

        }
    }

    private void error(String string) {
        this.error = true;
        this.errors.add(string);
    }

    public AuthorityIdentity getConceptDefinition() {

    	AuthorityIdentity ret = new AuthorityIdentity();

        if (error) {
            ret.setError(StringUtils.join(errors, "; "));
        }

        ret.setId(getShortId());
        ret.setLabel(toString());
        ret.setConceptName(getShortId());

        return ret;
    }

    public WRBConcept getRsg() {
        return rsg;
    }

    public void setRsg(WRBConcept rsg) {
        this.rsg = rsg;
    }

    public List<WRBConcept> getQualifiers() {
        return qualifiers;
    }

    public void setQualifiers(List<WRBConcept> qualifiers) {
        this.qualifiers = qualifiers;
    }

    public boolean isSpecMode() {
        return specMode;
    }

    public void setSpecMode(boolean specMode) {
        this.specMode = specMode;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Set<String> getErrors() {
        return errors;
    }

    public void setErrors(Set<String> errors) {
        this.errors = errors;
    }
}
