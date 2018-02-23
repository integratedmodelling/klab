package org.integratedmodelling.klab.clitool.console.commands.reason;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.integratedmodelling.kim.api.IKimFunctionCall;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.clitool.api.ICommand;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class Parents implements ICommand {

  @Override
  public Object execute(IKimFunctionCall call, ISession session) throws KlabValidationException {
    String declaration = StringUtils.join((List<?>) call.getParameters().get("arguments"), ' ');
    IConcept concept = Concepts.INSTANCE.declare(declaration);
    if (concept == null) {
      throw new KlabValidationException("expression '" + declaration + "' does not specify a concept");
    }
    return listParents(concept, 0);
  }

  private String listParents(IConcept concept, int i) {
    String ret = StringUtils.repeat(" ", i) + concept;
    for (IConcept child : concept.getParents()) {
      ret += "\n" + listParents(child, i + 3);
    }
    return ret;
  }


}
