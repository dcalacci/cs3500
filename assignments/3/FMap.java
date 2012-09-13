/* Dan Calacci
 * dcalacci@ccs.neu.edu
 * No Notes
 */

public abstract class FMap<K, V> {

  // isEmpty : -> boolean
  // Returns true if this FMap is empty
  public abstract boolean isEmpty();

  // size : -> Integer
  // Returns how many distinct key/value pairings this FMap contains
  public abstract Integer size();

  // containskey : K -> boolean
  // Returns true if this FMap has a Key/Value pairing with Key key
  public abstract boolean containsKey(K key);

  // get : K -> V
  // Returns the Value from the key/value pairing with Key key
  public abstract V get(K key);

  // add : K x V -> FMap<K, V>
  // Adds the given Key/Value pairing to this FMap
  public abstract FMap<K, V> add(K key, V val);

  // toString : -> String
  // Returns a string representation of this FMap
  public abstract String toString(); 

  // containsAll : FMap<K, V> x FMap<K, V> -> boolean
  // Returns true if b contains all the keys in a
  abstract boolean containsAll(FMap<K, V> a, FMap<K, V> b);

  // getALl : FMap<K, V> x FMap<K, V> -> boolean
  // Returns true if b.get(k) == a.get(k) for all keys in a 
  abstract boolean getAll(FMap<K, V> a, FMap<K, V> b);

  // equals : Object -> boolean
  // Returns true if the given object is equal to this FMap
  public abstract boolean equals(Object o);

  // hashCode : -> int
  // Returns the hashCode of this FMap
  public abstract int hashCode();

  // emptyMap : -> FMap<K, V>
  // Returns an instance of the EmptyMap class
  public static <K, V> FMap<K, V> emptyMap() {
    return new EmptyMap<K, V>();
  }
}

class EmptyMap<K, V> extends FMap<K, V> {

  //Constructor
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
  K key;                     // this key/value pairing's key
  V val;                     // this key/value pairing's value
  FMap<K, V> rest;           // the next FMap in the data structure

  // Constructor
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
