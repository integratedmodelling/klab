package org.integratedmodelling.klab.api.data.artifacts;

import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.provenance.IArtifact;

/**
 * When a {@link IKimModel model specification} is the result of an observation activity, the
 * {@code IModelArtifact} is the produced artifact. This happens in <strong>learning
 * processes</strong>, where the result of contextualizing knowledge is a model instead of a
 * finished observation.
 * <p>
 * 
 * @author ferdinando.villa
 *
 */
public interface IModelArtifact extends IArtifact {

  /**
   * The model specification produced by the activity. This can be serialized to k.IM or turned into
   * a {@link IModel} for distribution or execution.
   * 
   * @return the model specifications
   */
  IKimModel getModel();

}
