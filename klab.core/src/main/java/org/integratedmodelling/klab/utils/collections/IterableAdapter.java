package org.integratedmodelling.klab.utils.collections;

import java.util.Iterator;

public class IterableAdapter<T> implements Iterable<T> {
  
  private Iterable<?> iterable;

  public IterableAdapter(Iterable<?> iterable) {
    this.iterable = iterable;
  }

  @Override
  public Iterator<T> iterator() {
    return new IteratorAdapter<T>(iterable.iterator());
  } 

}
