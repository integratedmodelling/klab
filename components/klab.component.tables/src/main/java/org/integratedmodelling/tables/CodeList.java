package org.integratedmodelling.tables;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualTreeBidiMap;
import org.integratedmodelling.klab.Authorities;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.knowledge.IAuthority;
import org.integratedmodelling.klab.api.knowledge.ICodelist;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.Utils;

/**
 * Maps the values in a table (as strings) to other values, in either direction. Can be initialized
 * parametrically (as a function), with conventional mapping types, or initialized from a property
 * file that besides the category mappings (expressed as category.nn=X->Y) may contain a type for
 * the values, a worldview identifier and a root concept if the type is concept and the worldview is
 * set.
 * 
 * TODO must become more sophisticated and enable 1) separate, optional bi-directional
 * specifications and 2) many-to-many mapping. The current implementation uses a bidimap which only
 * handles functional mappings.
 * 
 * TODO must provide a constructor to initialize from a SDMX codelist
 * 
 * TODO must enable descriptions and other metadata for each code - a Map<String, IMetadata>
 * 
 * @author Ferd
 *
 */
public class CodeList implements ICodelist {

    public enum Mapping {
        CODELIST, YEAR, DATE_PATTERN
        // ....
    }

    private Properties properties = new Properties();
    private BidiMap<String, Object> mappings = new DualTreeBidiMap<>();
    private IArtifact.Type type = null;
    private String worldview = null;
    private IConcept rootConcept = null;
    private IAuthority authority;
    private Map<String, IMetadata> metadata = new HashMap<>();

    // we either encode the mapping in a file or use the logic encoded in the next fields
    private Mapping mapping = Mapping.CODELIST;
    private String mappingKey;
    private String name;

    public CodeList(Mapping type, String value) {
        this.mapping = type;
        this.mappingKey = value;
        this.name = type + ": " + value;
    }

    public CodeList(String name, File propertiesFile) {
        this.name = name;
        try (InputStream input = new FileInputStream(propertiesFile)) {
            properties.load(input);
            this.worldview = properties.getProperty("worldview");
            if (properties.containsKey("authority")) {
                this.authority = Authorities.INSTANCE.getAuthority(properties.getProperty("authority"));
            }
            String t = properties.getProperty("type");
            if (t != null) {
                this.type = IArtifact.Type.valueOf(t.toUpperCase());
            }
            if (this.worldview != null && this.type == IArtifact.Type.CONCEPT) {
                /*
                 * check for root concept
                 */
                String rc = properties.getProperty("root.concept");
                if (rc != null) {
                    this.rootConcept = Concepts.INSTANCE.declare(Concepts.INSTANCE.declare(rc));
                }
            }
            String pattern = Pattern.quote("->");
            for (Object key : properties.keySet()) {
                if (key.toString().startsWith("category.")) {
                    String[] pp = properties.getProperty(key.toString()).split(pattern);
                    if (pp.length > 1 && !pp[1].isEmpty()) {
                        mappings.put(pp[0], pp[1]);
                    }
                }
            }
        } catch (Exception e) {
            throw new KlabIOException(e);
        }
    }

    public Object value(String value) {

        if (this.mapping == Mapping.YEAR) {

            if (NumberUtils.encodesInteger(value.toString())) {
                int year = Integer.parseInt(value.toString());
                Time ret = Time.create(year);
                mappings.put(value, ret.encode());
                return ret;
            }

        } else if (this.mapping == Mapping.DATE_PATTERN) {

            // TODO

        } else {

            Object ret = value == null ? null : mappings.get(value.toString());
            if (ret != null) {
                if (this.authority != null) {
                    ret = this.authority.getIdentity(ret.toString(), null);
                } else if (rootConcept != null && this.type == Type.CONCEPT) {
                    ret = Concepts.c(ret.toString());
                    // TODO error if unknown?
                } else if (this.type != null) {
                    ret = Utils.asType(ret, Utils.getClassForType(this.type));
                }
            }
            return ret;
        }

        return null;
    }

    public String key(Object value) {

        if (this.mapping == Mapping.YEAR) {

            if (value instanceof Time) {
                return mappings.inverseBidiMap().get(((Time) value).encode());
            }

        } else if (this.mapping == Mapping.DATE_PATTERN) {

            // TODO

        }

        return value == null ? null : mappings.inverseBidiMap().get(value.toString());
    }

    public IConcept getRootConcept() {
        return rootConcept;
    }

    public String getWorldview() {
        return worldview;
    }

    public IArtifact.Type getType() {
        return type;
    }

    public String getDescription() {
        return this.name;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getAuthorityId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isAuthority() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Collection<String> keys(Object value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPattern() {
        // TODO Auto-generated method stub
        return null;
    }

}
