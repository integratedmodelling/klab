/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify it under the terms of the Affero
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root directory of the k.LAB
 * distribution (LICENSE.txt). If this cannot be found see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned in author tags. All
 * rights reserved.
 */
package org.integratedmodelling.klab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IProperty;
import org.integratedmodelling.klab.api.services.IConceptService;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.owl.Concept;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.owl.Property;

/**
 * The Enum Concepts.
 */
public enum Concepts implements IConceptService {

  INSTANCE;

  @Override
  public IProperty getProperty(String propertyId) {
    return OWL.INSTANCE.getProperty(propertyId);
  }

  @Override
  public Concept getConcept(String conceptId) {
    return OWL.INSTANCE.getConcept(conceptId);
  }

  @Override
  public Object getMetadata(IConcept concept, String field) {
    Object ret = concept.getMetadata().get(field);
    if (ret != null) {
      return ret;
    }
    for (IConcept c : concept.getParents()) {
      ret = getMetadata(c, field);
      if (ret != null) {
        return ret;
      }
    }
    return null;
  }

  /**
   * Quick static way to obtain a concept that is known to exist. Throws an unchecked exception if
   * the concept isn't found.
   *
   * @param conceptId the concept id
   * @return the concept. Never null.
   */
  public static Concept c(String conceptId) {

    if (conceptId == null || conceptId.isEmpty()) {
      return null;
    }

    Concept ret = OWL.INSTANCE.getConcept(conceptId);
    if (ret == null) {
      throw new KlabResourceNotFoundException("cannot find concept " + conceptId);
    }
    return ret;

  }

  /**
   * Quick static way to obtain a property that is known to exist. Throws an unchecked exception if
   * the property isn't found.
   *
   * @param propertyId the property id
   * @return the property. Never null.
   */
  public static Property p(String propertyId) {

    if (propertyId == null || propertyId.isEmpty()) {
      return null;
    }

    Property ret = OWL.INSTANCE.getProperty(propertyId);
    if (ret == null) {
      throw new KlabResourceNotFoundException("cannot find property " + propertyId);
    }
    return ret;

  }

  /**
   * Get the best display name for a concept.
   *
   * @param t the t
   * @return a name for display
   */
  public String getDisplayName(IConcept t) {

    String ret = t.getMetadata().get(NS.DISPLAY_LABEL_PROPERTY, String.class);

    if (ret == null) {
      ret = t.getMetadata().get(IMetadata.DC_LABEL, String.class);
    }
    if (ret == null) {
      ret = t.getName();
    }
    if (ret.startsWith("i")) {
      ret = ret.substring(1);
    }
    return ret;
  }

  /**
   * Arrange a set of concepts into the collection of the most specific members of each concept
   * hierarchy therein. Return one concept or null.
   * 
   * @param cc
   * @return least general
   */
  @Override
  public IConcept getLeastGeneralConcept(Collection<IConcept> cc) {
    Collection<IConcept> z = getLeastGeneral(cc);
    return z.size() > 0 ? z.iterator().next() : null;
  }

  @Override
  public IConcept getLeastGeneralCommonConcept(IConcept concept1, IConcept c) {
    return concept1.getLeastGeneralCommonConcept(c);
  }

  @Override
  public IConcept getLeastGeneralCommonConcept(Collection<IConcept> cc) {

    IConcept ret = null;
    Iterator<IConcept> ii = cc.iterator();

    if (ii.hasNext()) {

      ret = ii.next();

      if (ret != null)
        while (ii.hasNext()) {
          ret = ret.getLeastGeneralCommonConcept(ii.next());
          if (ret == null)
            break;
        }
    }

    return ret;
  }

  /**
   * Arrange a set of concepts into the collection of the most specific members of each concept
   * hierarchy therein.
   * 
   * @param cc
   * @return least general
   */
  @Override
  public Collection<IConcept> getLeastGeneral(Collection<IConcept> cc) {

    Set<IConcept> ret = new HashSet<>();
    for (IConcept c : cc) {
      List<IConcept> ccs = new ArrayList<>(ret);
      boolean set = false;
      for (IConcept kn : ccs) {
        if (c.is(kn)) {
          ret.remove(kn);
          ret.add(c);
          set = true;
        } else if (kn.is(c)) {
          set = true;
        }
      }
      if (!set) {
        ret.add(c);
      }
    }
    return ret;
  }

  /**
   * True if concept was declared in k.IM at root level. These serve as the "official" least general
   * "family" of concepts for several purposes - e.g. for traits, only these are seen as "general"
   * enough to be used in an "exposes" statement.
   *
   * @param tr the tr
   * @return true if the concept was declared in a root-level k.IM statement.
   */
  public boolean isBaseDeclaration(IConcept tr) {
    return tr.getMetadata().get(NS.BASE_DECLARATION) != null;
  }


  @Override
  public int getAssertedDistance(IConcept from, IConcept to) {
    if (from.equals(to)) {
      return 0;
    }
    int ret = 1;
    while (true) {
      Collection<IConcept> parents = from.getParents();
      if (parents.isEmpty()) {
        break;
      }
      if (parents.contains(to)) {
        return ret;
      }
      for (IConcept parent : parents) {
        int d = getAssertedDistance(from, parent);
        if (d >= 0) {
          return ret + d;
        }
      }
      ret++;
    }
    return -1;
  }

  @Override
  public int compareSpecificity(IConcept c1, IConcept c2, boolean useBaseTrait) {

    if (c1.equals(c2)) {
      return 0;
    }

    IConcept common = null;
    if (useBaseTrait) {
      IConcept common1 = Traits.INSTANCE.getBaseParentTrait(c1);
      IConcept common2 = Traits.INSTANCE.getBaseParentTrait(c2);
      if (common1 == null || common2 == null || !common1.equals(common2)) {
        return Integer.MIN_VALUE;
      }
      common = common1;
    } else {
      common = getLeastGeneralCommonConcept(c1, c2);
    }
    if (common == null) {
      return Integer.MIN_VALUE;
    }

    int d1 = getAssertedDistance(c1, common);
    int d2 = getAssertedDistance(c2, common);

    return (d1 < 0 || d2 < 0) ? Integer.MIN_VALUE : d1 - d2;
  }

}
