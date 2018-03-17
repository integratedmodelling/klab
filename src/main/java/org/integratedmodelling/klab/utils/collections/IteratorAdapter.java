package org.integratedmodelling.klab.utils.collections;

import java.util.Iterator;

public class IteratorAdapter<T> implements Iterator<T> {

  Iterator<?> iterator;
  
  public IteratorAdapter(Iterator<?> iterator) {
    this.iterator = iterator;
  }
  
  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @SuppressWarnings("unchecked")
  @Override
  public T next() {
    return (T)iterator.next();
  }

}
