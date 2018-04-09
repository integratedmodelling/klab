package org.integratedmodelling.klab.api.data.adapters;

import java.net.URL;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public interface IResourceValidator {

  /**
   * Validate the resource pointed to by the URL and tagged with the passed user-provided data
   * (possibly empty). Returns a builder that will be used to produce the resource to be published
   * or to report any errors resulting from unsuccessful validation.
   * 
   * @param url
   * @param userData
   * @return a builder for the resource, containing any validation errors. Do not return null.
   * @throws KlabValidationException
   */
  Builder validate(URL url, IParameters userData);

}
