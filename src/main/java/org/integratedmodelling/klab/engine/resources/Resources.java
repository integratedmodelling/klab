package org.integratedmodelling.klab.engine.resources;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import org.apache.catalina.webresources.FileResource;
import org.apache.tomcat.jni.Directory;
import org.integratedmodelling.klab.API;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.URLUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * Access to resources from classpath, configuration or other.
 * 
 * @author ferdinando.villa
 *
 */
public class Resources {

    /**
     * Extract the OWL assets in the classpath (under /knowledge/**) to the specified filesystem directory.
     * 
     * @param destinationDirectory
     * @throws KlabIOException
     */
    public static void extractKnowledgeFromClasspath(File destinationDirectory) throws IOException {

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("/knowledge/**");
        for (Resource resource : resources) {

            String path = null;
            if (resource instanceof FileSystemResource) {
                path = ((FileSystemResource) resource).getPath();
            } else if (resource instanceof ClassPathResource) {
                path = ((ClassPathResource) resource).getPath();
            }
            if (path == null) {
                throw new IOException("internal: cannot establish path for resource "
                        + resource);
            }

            if (!path.endsWith("owl")) {
                continue;
            }

            String filePath = path
                    .substring(path.indexOf("knowledge/") + "knowledge/".length());

            int pind = filePath.lastIndexOf('/');
            if (pind >= 0) {
                String fileDir = filePath.substring(0, pind);
                File destDir = new File(destinationDirectory + File.separator + fileDir);
                destDir.mkdirs();
            }
            File dest = new File(destinationDirectory + File.separator + filePath);
            InputStream is = resource.getInputStream();
            FileUtils.copyInputStreamToFile(is, dest);
            is.close();
        }
    }

}
