if (o instanceof FMap) {
  FMap<K, V> that = (FMap<K, V>)o;
  ArrayList<K> thisKeys = this.getKeys();
  ArrayList<K> thatKeys = that.getKeys();
  Iterator<K> thatIt =    thatKeys.iterator();
  Iterator<K> thisIt =    thisKeys.iterator();
  boolean get1 = false;
  boolean get2 = false;
  boolean contains1 = false;
  boolean contains2 = false;

  if (that.isEmpty()) {
    return false;
  }
  while(thatIt.hasNext()) {
    K key = thatIt.next();
    if (this.containsKey(key).equals(that.containsKey(key))) {
    get1 = (this.get(key).equals(that.get(key)));
    }
  }
  while(thisIt.hasNext()) {
    K key = thisIt.next();
    if (this.containsKey(key).equals(that.containsKey(key))) {
      get2 = (this.get(key).equals(that.get(key)));
    }
  }
  if (get1 && get2) {
    return true;
  } else {
    return false;
  }