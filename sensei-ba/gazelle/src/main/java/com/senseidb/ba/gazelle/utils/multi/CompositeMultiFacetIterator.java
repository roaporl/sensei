package com.senseidb.ba.gazelle.utils.multi;

public class CompositeMultiFacetIterator implements MultiFacetIterator {
  private MultiChunkIterator currentIterator;
  private int currentIndex;
  private final MultiChunkIterator[] iterators;

  public CompositeMultiFacetIterator(MultiChunkIterator[] iterators) {
    this.iterators = iterators;
    currentIterator = iterators[0];
    currentIndex = 0;
  }
  @Override
  public boolean advance(int index) {
    boolean res = currentIterator.advance(index);
    if (!res) {
      while (currentIndex < iterators.length - 1) {
        if (iterators[currentIndex + 1].getStartElement() > index) {
          break;
        }
        currentIndex++;
      }
      if (currentIndex >=  iterators.length) {
        return false;
      }
      currentIterator = iterators[currentIndex];
      return currentIterator.advance(index);
    }
    return true;
  }

  @Override
  public int readValues(int[] buffer) {
    return currentIterator.readValues(buffer);
  }

}
