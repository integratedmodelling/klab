/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
package org.integratedmodelling.klab.data;

import java.util.Map;
import org.integratedmodelling.kim.api.IKimMetadata;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.api.knowledge.IMetadata;

// TODO: Auto-generated Javadoc
/**
 * The Class Metadata.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public class Metadata extends Parameters<String> implements IMetadata {

  /**
   * Instantiates a new metadata.
   *
   * @param metadata the metadata
   */
  public Metadata(IKimMetadata metadata) {
    putAll(metadata.getData());
  }
  
  /**
   * Instantiates a new metadata.
   *
   * @param data the data
   */
  public Metadata(Map<String, Object> data) {
    super(data);
  }
  
  /**
   * Instantiates a new metadata.
   */
  public Metadata() {}

  /**
   * Copy.
   *
   * @return the metadata
   */
  public Metadata copy() {
    Metadata ret = new Metadata();
    ret.putAll(this);
    return ret;
  }

}
