package org.integratedmodelling.klab.api.lang.kim.impl;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.collections.KParameters;
import org.integratedmodelling.klab.api.data.KMetadata;
import org.integratedmodelling.klab.api.lang.KAnnotation;
import org.integratedmodelling.klab.api.lang.kim.KKimStatement;

/**
 * 
 * @author Ferd
 *
 */
public class KimStatement extends KimScope implements KKimStatement {

    private static final long serialVersionUID = -7273214821906819558L;
    private int firstLine;
    private int lastLine;
    private int firstCharOffset;
    private int lastCharOffset;
    private List<KAnnotation> annotations = new ArrayList<>();
    private String deprecation;
    private boolean deprecated;
    private String sourceCode;
    private boolean errors;
    private boolean warnings;
    private KMetadata metadata;
    private KParameters<String> documentationMetadata;
    private String namespace;
    private Scope scope;

    @Override
    public int getFirstLine() {
        return this.firstLine;
    }

    @Override
    public int getLastLine() {
        return this.lastLine;
    }

    @Override
    public int getFirstCharOffset() {
        return this.firstCharOffset;
    }

    @Override
    public int getLastCharOffset() {
        return this.lastCharOffset;
    }

    @Override
    public List<KAnnotation> getAnnotations() {
        return this.annotations;
    }

    @Override
    public String getDeprecation() {
        return this.deprecation;
    }

    @Override
    public boolean isDeprecated() {
        return this.deprecated;
    }

    @Override
    public String getSourceCode() {
        return this.sourceCode;
    }

    @Override
    public boolean isErrors() {
        return this.errors;
    }

    @Override
    public boolean isWarnings() {
        return this.warnings;
    }

    @Override
    public KMetadata getMetadata() {
        return this.metadata;
    }

    @Override
    public void visit(Visitor visitor) {
        // TODO Auto-generated method stub

    }

    @Override
    public KParameters<String> getDocumentationMetadata() {
        return this.documentationMetadata;
    }

    @Override
    public String getNamespace() {
        return this.namespace;
    }

    @Override
    public Scope getScope() {
        return this.scope;
    }

    public void setFirstLine(int firstLine) {
        this.firstLine = firstLine;
    }

    public void setLastLine(int lastLine) {
        this.lastLine = lastLine;
    }

    public void setFirstCharOffset(int firstCharOffset) {
        this.firstCharOffset = firstCharOffset;
    }

    public void setLastCharOffset(int lastCharOffset) {
        this.lastCharOffset = lastCharOffset;
    }

    public void setAnnotations(List<KAnnotation> annotations) {
        this.annotations = annotations;
    }

    public void setDeprecation(String deprecation) {
        this.deprecation = deprecation;
    }

    public void setDeprecated(boolean deprecated) {
        this.deprecated = deprecated;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public void setErrors(boolean errors) {
        this.errors = errors;
    }

    public void setWarnings(boolean warnings) {
        this.warnings = warnings;
    }

    public void setMetadata(KMetadata metadata) {
        this.metadata = metadata;
    }

    public void setDocumentationMetadata(KParameters<String> documentationMetadata) {
        this.documentationMetadata = documentationMetadata;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

}
