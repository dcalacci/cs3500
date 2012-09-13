public abstract class FMap<K, V> {

  public abstract boolean isEmpty();
  public abstract Integer size();
  public abstract boolean containsKey(K key);
  public abstract V get(K key);
  public abstract FMap<K, V> add(K key, V val);
  public abstract String toString(); 
  public static <K, V> FMap<K, V> emptyMap() {
    return new EmptyMap<K, V>();
  }
}

class EmptyMap<K, V> extends FMap<K, V> {
  EmptyMap() { }

  public boolean isEmpty() {
    return true;
  }
  public FMap<K, V> add(K key, V val) {
    return new add<K, V>(key, val, this);
  }

  public Integer size() {
    return 0;
  }

  public boolean containsKey(K k) {
    return false;
  }

  public V get(K key) {
    throw new RuntimeException("Attempt to get key/value pairing from an emptymap.");
  }

  public String toString() {
    return "{...(" + this.size() + " entries)...}";
  }

  public int hashCode() {
    return 5;
  }

  public boolean equals(Object o) {
    if (o instanceof FMap) {
      FMap<K, V> that = (FMap<K, V>)o;
      return that.isEmpty();
    } else {
      return false;
  }
}
}

class add<K, V> extends FMap<K, V> {
  K key;
  V val;
  FMap<K, V> rest;
  add(K key, V val, FMap<K, V> rest) {
    this.key = key;
    this.val = val;
    this.rest = rest;
  }

  public boolean isEmpty() {
    return false;
  }

  public Integer size() {
    if (this.rest.containsKey(this.key)) {
      return this.rest.size();
    } else {
      return 1 + this.rest.size();
    }
  }

  public boolean containsKey(K k) {
    if (this.key.equals(k)) {
      return true;
    } else {
      return this.rest.containsKey(k);
    }
  }

  //note: returns the value in the FIRST key/value pairing that matches
  //the given key.
  public V get(K key) {
    if (this.key.equals(key)) {
      return this.val;
    } else {
      return this.rest.get(key);
    }
  }

  public FMap<K, V> add(K key, V val) {
    return new add<K, V>(key, val, this);
  }
  
  public String toString() {
    return "{...(" + this.size() + " entries)...}";
  }

  public int hashCode() {
    // h represents the hash code of this object.
    //int h = 5; // arbitrary starting value to decrease false positives
    return 5;


  //  if (this.fset.contains(this.s)) {
    //  h = this.fset.hashCode(); //ignores duplicate elements
   // } else {
      // this method of computing the hashCode ignores order and produces
      // a number sufficiently large enough to ensure that it is very 
      // unlikely that two unequal FSets will have the same hash code.
     // h = this.s.hashCode() * this.fset.hashCode();
   // }
 //   return h;
  }

  public boolean equals(Object o) {
    if (o instanceof FMap) {
      FMap<K, V> that = (FMap<K, V>)o;
      if (that.containsKey(this.kal) && (that.get(this.kal).equals(this.get(this.key)))) {
        return this.rest.equals(that);
      } else {
        return false;
      }
    }
  }
}
