

public abstract class FMap<K, V> {

  
  
  public abstract boolean isEmpty();

  
  
  public abstract Integer size();

  
  
  public abstract boolean containsKey(K key);

  
  
  public abstract V get(K key);

  
  
  public abstract FMap<K, V> add(K key, V val);

  
  
  public abstract String toString(); 

  
  
  abstract boolean containsAll(FMap<K, V> a, FMap<K, V> b);

  
  
  abstract boolean getAll(FMap<K, V> a, FMap<K, V> b);

  
  
  public abstract boolean equals(Object o);

  
  
  public abstract int hashCode();

  
  
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

  @SuppressWarnings("unchecked")
  public boolean equals(Object o) {
    if (o instanceof FMap) {
      FMap<K, V> that = (FMap<K, V>)o;
      return that.isEmpty();
    } else {
      return false;
    }
  }
  boolean containsAll(FMap<K, V> a, FMap<K, V> b) {
    return true;
  }
  boolean getAll(FMap<K, V> a, FMap<K, V> b) {
    return true;
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
    int h = 5;
    if (this.rest.containsKey(this.key)) {
      h = this.rest.hashCode();
    } else {
      h = this.key.hashCode() * this.rest.hashCode();
    }
    return h;
  }

  @SuppressWarnings("unchecked")
  public boolean equals(Object o) {
    if (o instanceof FMap) {
      FMap<K, V> that = (FMap<K, V>)o;
      if (this.containsAll(this, that) && that.containsAll(that, this)) {
        return (this.getAll(this, that) && (that.getAll(that, this)));
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  boolean containsAll(FMap<K, V> a, FMap<K, V> b) {
    if (a.containsKey(this.key) == b.containsKey(this.key)) {
      return this.rest.containsAll(a, b);
    } else {
      return false;
    }
  }

  boolean getAll(FMap<K, V> a, FMap<K, V> b) {
    if (a.get(this.key).equals(b.get(this.key))) {
      return this.rest.getAll(a, b);
    } else {
      return false;
    }
  }
}
