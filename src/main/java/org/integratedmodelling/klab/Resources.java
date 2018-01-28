package org.integratedmodelling.klab;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringEscapeUtils;
import org.integratedmodelling.kim.api.IKimFunctionCall;
import org.integratedmodelling.kim.model.Urns;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.services.IResourceService;
import org.integratedmodelling.klab.exceptions.KlabUnauthorizedUrnException;
import org.integratedmodelling.klab.exceptions.KlabUnknownUrnException;
import org.integratedmodelling.klab.utils.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * Static functions related to the management and resolution of URNs. Also holds the URN metadata database for
 * local URNs, synchronized with the META-INF/data.kim file and maintained automatically.
 * 
 * @author ferdinando.villa
 *
 */
public enum Resources implements IResourceService {

    INSTANCE;

    @Override
    public IResource getResource(final String urn)
            throws KlabUnknownUrnException, KlabUnauthorizedUrnException {

        String id = urn;
        IResource ret = null;

        if (id.startsWith(Urns.LOCAL_URN_PREFIX)) {
            id = id.substring(Urns.LOCAL_URN_PREFIX.length());
        } else if (id.startsWith(Urns.KLAB_URN_PREFIX)) {
            id = id.substring(Urns.KLAB_URN_PREFIX.length());
        }

        if (id.startsWith(Urns.LOCAL_BOOLEAN_PREFIX)) {

            Boolean b = Boolean
                    .parseBoolean(StringEscapeUtils
                            .unescapeHtml(id.substring(Urns.LOCAL_BOOLEAN_PREFIX.length())));
            ret = getLiteralResource(b);

        } else if (id.startsWith(Urns.LOCAL_FILE_PREFIX)) {

            String text = StringEscapeUtils.unescapeHtml(id.substring(Urns.LOCAL_TEXT_PREFIX.length()));
            ret = getLocalFileResource(new File(text));

        } else if (id.startsWith(Urns.LOCAL_NUMBER_PREFIX)) {

            Number number = Double
                    .parseDouble(StringEscapeUtils
                            .unescapeHtml(id.substring(Urns.LOCAL_NUMBER_PREFIX.length())));
            ret = getLiteralResource(number);

        } else if (id.startsWith(Urns.LOCAL_TEXT_PREFIX)) {

            String text = StringEscapeUtils.unescapeHtml(id.substring(Urns.LOCAL_TEXT_PREFIX.length()));
            ret = getLiteralResource(text);

        } else if (id.startsWith(Urns.LOCAL_FUNCTION_PREFIX)) {

        } else {

            /*
             * it's a remote URN: find in cache, retrieve if not there or expired
             */
        }

        return ret;
    }

    @Override
    public IResource getLocalFileResource(File file) {
        return null;
    }

    @Override
    public IResource getComputedResource(IKimFunctionCall function) {
        return null;
    }

    @Override
    public IResource getLiteralResource(Object inlineResource) {
        return null;
    }

    /**
     * Extract the OWL assets in the classpath (under /knowledge/**) to the specified filesystem directory.
     * 
     * @param destinationDirectory
     * @throws IOException
     */
    public void extractKnowledgeFromClasspath(File destinationDirectory) throws IOException {

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

    @Override
    public IKimObject getModelObject(String urn) {
        // TODO Auto-generated method stub
        return null;
    }

}
