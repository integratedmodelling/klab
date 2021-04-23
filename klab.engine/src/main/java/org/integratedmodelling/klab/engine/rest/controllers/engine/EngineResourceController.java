package org.integratedmodelling.klab.engine.rest.controllers.engine;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

/**
 * The controller implementing the
 * {@link org.integratedmodelling.klab.api.API.ENGINE.OBSERVATION.RESOURCE API}.
 * 
 * @author ferdinando.villa
 *
 */
@RestController
@CrossOrigin(origins = "*")
@Secured(Roles.SESSION)
public class EngineResourceController {

    @RequestMapping(value = API.ENGINE.RESOURCE.GET_PROJECT_RESOURCE, method = RequestMethod.GET)
    public void getObservationData(@PathVariable String project, HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        IProject proj = Resources.INSTANCE.getProject(project);
        if (proj == null) {
            throw new IllegalArgumentException("project " + project + " does not exist");
        }

        String resourcepath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        resourcepath = resourcepath.substring(resourcepath.indexOf(project) + project.length() + 1).replaceAll(":", "/");

        /*
         * for now resourcepath is limited to the application directory. Any : will become / (FIXME:
         * this can be eliminated once legacy usages are).
         */
        File resourceFile = new File(proj.getRoot() + File.separator + IKimProject.SCRIPT_FOLDER + File.separator + resourcepath);

        if (!resourceFile.exists() || !resourceFile.isFile()) {
            throw new IllegalArgumentException("project " + project + " does not exist or resource not found");
        }

        try (InputStream in = new FileInputStream(resourceFile)) {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            IOUtils.copy(in, response.getOutputStream());
        }
    }
    
    @RequestMapping(value = API.ENGINE.RESOURCE.GET_RESOURCE_SPATIAL_IMAGE, method = RequestMethod.GET)
    public void getResourceSpatialImage(@PathVariable String urn, HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedImage image = Resources.INSTANCE.getResourceSpatialContextImage(urn);
        if (image != null) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);
            InputStream in = new ByteArrayInputStream(os.toByteArray());
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
            IOUtils.copy(in, response.getOutputStream());
        }
    }

}
