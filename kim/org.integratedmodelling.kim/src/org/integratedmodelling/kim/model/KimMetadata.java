package org.integratedmodelling.kim.model;

import java.util.ArrayList;
import java.util.List;
import org.integratedmodelling.kim.api.IKimMetadata;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.kim.Metadata;
import org.integratedmodelling.kim.validation.KimValidator;
import org.integratedmodelling.klab.utils.Parameters;

public class KimMetadata extends KimStatement implements IKimMetadata {

  private static final long serialVersionUID = 3885078963867253246L;

  /*
   * It's a multimap: the value can be a list and if so, the API must be capable of dealing with it.
   */
  protected Parameters<String> data;

  public KimMetadata(Metadata statement, IKimStatement parent) {
    super(statement, parent);
    this.data = Kim.INSTANCE.parseMetadata(statement,
        Kim.INSTANCE.getNamespace(KimValidator.getNamespace(statement)));
  }

  public IParameters<String> getData() {
    return data;
  }

  @SuppressWarnings("unchecked")
  public void put(String key, Object value) {

    if (value == null) {
      return;
    }

    if (data.containsKey(key)) {
      if (data.get(key) instanceof List) {
        ((List<Object>) data.get(key)).add(value);
      } else {
        Object o = data.get(key);
        data.put(key, new ArrayList<Object>());
        ((List<Object>) data.get(key)).add(o);
        ((List<Object>) data.get(key)).add(value);
      }
    }
  }


}
