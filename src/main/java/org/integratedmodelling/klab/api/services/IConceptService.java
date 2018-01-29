package org.integratedmodelling.klab.api.services;

import java.util.Collection;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IProperty;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public interface IConceptService {

  IProperty getProperty(String propertyId);

  IConcept getConcept(String conceptId);

  /**
   * Build a concept from its k.IM declaration.
   * 
   * @param declaration
   * @return the concept corresponding to a k.IM definition.
   * @throws KlabValidationException if the declaration contains errors or unknown concepts.
   */
  IConcept declare(String declaration) throws KlabValidationException;

  IConcept declare(IKimConcept observable, IMonitor monitor);

  Collection<IConcept> getLeastGeneral(Collection<IConcept> cc);

  IConcept getLeastGeneralCommonConcept(Collection<IConcept> cc);

  IConcept getLeastGeneralCommonConcept(IConcept concept1, IConcept c);

  IConcept getLeastGeneralConcept(Collection<IConcept> cc);

}
