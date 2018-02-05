package org.integratedmodelling.klab.resolution;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.resolution.IPrioritizer;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.persistence.Model;
import org.integratedmodelling.klab.utils.collections.ImmutableList;

public class Prioritizer extends ImmutableList<IModel> implements IPrioritizer<Model> {

  public Prioritizer() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public int compare(Model arg0, Model arg1) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public Map<String, Object> computeCriteria(Model model, IResolutionScope context) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<String, Double> getRanks(Model md) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<String> listCriteria() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int size() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public boolean contains(Object o) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Iterator<IModel> iterator() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object[] toArray() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> T[] toArray(T[] a) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IModel get(int index) {
    // TODO Auto-generated method stub
    return null;
  }

}
