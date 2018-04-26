package org.integratedmodelling.klab.client.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.client.http.Client.BeanDescriptor;
import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.rules.RuleFactory;

import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.JType;

/**
 * Read the JSON schemata from an engine and build the corresponding Java POJO classes and
 * objects.
 * 
 * Unfinished. Can be removed in production.
 * 
 * @author ferdinando.villa
 *
 */
public class KlabPOJOGenerator {

    private Map<String, Class<?>> pojoClasses = new HashMap<>();

    public KlabPOJOGenerator(Client client) {

        GenerationConfig config = new DefaultGenerationConfig() {
            @Override
            public boolean isGenerateBuilders() {
                return true;
            }
        };
        
        BeanDescriptor beans = client.getPOJOClasses();
        JCodeModel codeModel = new JCodeModel();
        List<String> classNames = new ArrayList<>();
        SchemaMapper mapper = new SchemaMapper(
                new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()),
                new SchemaGenerator());
        
        for (String cls : beans.classes) {
            try {
                URL getSchema = new URL(client.getUrl() + Client.API_SCHEMA_GET + cls);
                JType type = mapper.generate(codeModel, cls, beans.packageName, getSchema);
                classNames.add(type.fullName());
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
        
        final Map<String, ByteArrayOutputStream> streams = new HashMap<String, ByteArrayOutputStream>();

        try {
            codeModel.build(new CodeWriter() {
                @Override
                public OutputStream openBinary(JPackage jPackage, String name) throws IOException {
                    String fullyQualifiedName = jPackage.name().length() == 0 ? name : jPackage.name().replace(".", "/") + "/" + name;
                    if(!streams.containsKey(fullyQualifiedName)) {
                        streams.put(fullyQualifiedName, new ByteArrayOutputStream());
                    }
                    return streams.get(fullyQualifiedName);
                }

                @Override
                public void close() throws IOException {
                    for (OutputStream outputStream : streams.values()) {
                        outputStream.flush();
                        outputStream.close();
                    }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        for (String cls : streams.keySet()) {
            String code = streams.get(cls).toString();
            // TODO compile and/or generate
        }
        
    }

    public Object newPojo(String className) {
        return null;
    }

}
