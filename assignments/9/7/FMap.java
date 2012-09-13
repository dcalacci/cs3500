/* Dan Calacci
 * dcalacci@ccs.neu.edu
 * */
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Iterable;
import java.util.Collections;
import java.util.NoSuchElementException;

public abstract class FMap<K, V> implements Iterable<K> {

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

  // getAll : FMap<K, V> x FMap<K, V> -> boolean
  // Returns true if b.get(k) == a.get(k) for all keys in a 
  abstract boolean getAll(FMap<K, V> a, FMap<K, V> b);

  // equals : Object -> boolean
  // Returns true if the given object is equal to this FMap
  public abstract boolean equals(Object o);

  // hashCode : -> int
  // Returns the hashCode of this FMap
  public abstract int hashCode();

  // getKeys() : -> ArrayList<K>
  // Returns an arraylist populated with the keys of this FMap.
  // if this FMap is empty, throw an error.
  abstract ArrayList<K> getKeys();

  // restKeys() : -> ArrayList<K>
  // Helper/recursive method for getKeys()
  abstract ArrayList<K> restKeys(ArrayList<K> keys);

  // iterator : -> Iterator<K>
  // Returns an iterator that generates every key k in this
  //  FMap
  public abstract Iterator<K> iterator();

  // iterator : -> Iterator<K>
  // Returns an iterator that generates every key k in this
  //  FMap, with keys generated in increasing order as
  //  determined by c
  public abstract Iterator<K> iterator(Comparator<? super K> c);

  public abstract FMap<K, V> accept(Visitor<K, V> visitor);

  // emptyMap : -> FMap<K, V>
  // Returns an instance of the EmptyMap class
  public static <K, V> FMap<K, V> emptyMap() {
    return new EmptyMap<K, V>();
  }

  // emptyMap : Comparator -> FMap<K, V>
  // Reuturns an instance of the EmptyMap class
  public static <K, V> FMap<K, V> emptyMap(Comparator<? super K> c) {
    return new EmptyTree<K, V>(c);
  }
}

abstract class FTree<K, V> extends FMap<K, V> implements Iterable<K> {
  public abstract boolean     isEmpty();
  public abstract Integer     size();
  public abstract boolean     containsKey(K key);
  public abstract V           get(K key);
  abstract ArrayList<K>       getKeys();
  public abstract FTree<K, V> insert(K key, V val);
  abstract ArrayList<K>       restKeys(ArrayList<K> keys);
  public FMap<K, V> add(K key, V val) {
    return this.insert(key, val);
  }
  abstract void getKeysHelper(ArrayList<K> keys);
}



class EmptyTree<K, V> extends FTree<K, V> {
  Comparator<? super K> c;

  EmptyTree(Comparator<? super K> c) {
    this.c = c;
  }

  public boolean isEmpty() {
    return true;
  }

  public Integer size() {
    return 0;
  }

  public boolean containsKey(K key) {
    return false;
  }

  public V get(K key) {
    throw new RuntimeException("does not contain given key.");
  }

  public FTree<K, V> insert(K key, V val) {
    return new Node<K, V>(key, val, c, this, this);
  }

  ArrayList<K> getKeys() {
    return new ArrayList<K>();
  }
  void getKeysHelper(ArrayList<K> keys) {
    keys = keys;
  }

  public Iterator<K> iterator(Comparator<? super K> c) {
    return new FMapIterator(this.getKeys(), c);
  }

  public Iterator<K> iterator() {
    return new FMapIterator(this.getKeys());
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

  ArrayList<K> restKeys(ArrayList<K> keys) {
    throw new RuntimeException("should never be called");
  }

  public int hashCode() {
    return 5;
  }

  public String toString() {
    return "{...(" + this.size() + " entries)...}";
  }
  public FMap<K, V> accept(Visitor<K, V> visitor) {
    return this;
  }



}

class Node<K, V> extends FTree<K, V> {
  K key;
  V val;
  Comparator<? super K> c;
  FTree<K, V> t1;
  FTree<K, V> t2;
  Integer size;

  Node(K key, V val, 
      Comparator<? super K> c, FTree<K, V> t1, FTree<K, V> t2) {
    this.key =  key;
    this.val =  val;
    this.c =    c;
    this.t1 =   t1;
    this.t2 =   t2;
    size = this.preSize();
  }

  public boolean isEmpty() {
    return false;
  }

  Integer preSize() {
    return 1 + this.t1.size() + this.t2.size();
  }

  public Integer size() {
    return this.size;
  }

  public boolean containsKey(K key) {
    if (this.c.compare(this.key, key) > 0) {
      return this.t2.containsKey(key);
    } else if (this.c.compare(this.key, key) < 0) {
      return this.t1.containsKey(key);
    } else {
      return this.key.equals(key);
    }
  }

  public FTree<K, V> insert(K key, V val) {
    if (this.key.equals(key) && this.val.equals(val)) {
      return this;
    } else if (this.key.equals(key)) {
      return new Node<K, V>(key, val, this.c, this.t1, this.t2);
     // return new Node<K, V>(key, val, this.c, this.t1, this.t2);
    } else if (this.c.compare(this.key, key) > 0) {
      return new Node<K, V>(this.key, this.val, this.c, this.t1, 
          this.t2.insert(key, val));
    } else {
      return new Node<K, V>(this.key, this.val, this.c,
          this.t1.insert(key, val), this.t2);
    }
  }

  public V get(K key) {
    if (this.key.equals(key)) {
      return this.val;
    } else if (this.c.compare(this.key, key) > 0) {
      return this.t2.get(key);
    } else {
      return this.t1.get(key);
    }
  }

  void getKeysHelper(ArrayList<K> keys) {
    keys.add(this.key);
    if (!this.t1.isEmpty()) {
      this.t1.getKeysHelper(keys);
    }
    if (!this.t2.isEmpty()) {
      this.t2.getKeysHelper(keys);
    }
  }

  ArrayList<K> getKeys() {
    ArrayList<K> keys = new ArrayList<K>();
    this.getKeysHelper(keys);
    return keys;
  }


  public Iterator<K> iterator(Comparator<? super K> c) {
    return new FMapIterator(this.getKeys(), c);
  }

  public Iterator<K> iterator() {
    return new FMapIterator(this.getKeys());
  }

  @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
      if (o instanceof FMap) {
        FMap<K, V> that = (FMap<K, V>)o;
        if (that.isEmpty()) {
          return false;
        }
        else if (this.containsAll(this, that)
            && (that.containsAll(that, this))) {
        return (this.getAll(this, that)
            && (that.getAll(that, this)));
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  boolean containsAll(FMap<K, V> a, FMap<K, V> b) {
    if (a.containsKey(this.key) == b.containsKey(this.key)) {
      return this.t1.containsAll(a, b) && this.t2.containsAll(a, b);
    } else {
      return false;
    }
  }

  boolean getAll(FMap<K, V> a, FMap<K, V> b) {
    if (a.get(this.key).equals(b.get(this.key))) {
      return this.t1.getAll(a, b) && this.t2.getAll(a, b);
    } else {
      return false;
    }
  }

  //artifact of lazy design
  ArrayList<K> restKeys(ArrayList<K> keys) {
    throw new RuntimeException("should never be called");
  }

  public int hashCode() {
    ArrayList<K> keys = this.getKeys();
    Iterator<K> it = keys.iterator();
    Integer i = 1;
    while (it.hasNext()) {
      K key = it.next();
      V val = this.get(key);
      i = i * ((13+key.hashCode()) + (5+val.hashCode()));
    }
    return i;
  }

  public String toString() {
    return "{...(" + this.size() + " entries)...}";
  }
  public FMap<K,V> accept(Visitor<K, V> visitor) {
    FMap<K,V> m2 = FMap.emptyMap();
    ArrayList<K> keys = this.getKeys();
    for (K k : keys) {
      V v = visitor.visit(k, this.get(k));
      m2 = m2.add(k, v);
    }
    return m2;
  }
}


class EmptyMap<K, V> extends FMap<K, V> {
  Comparator<? super K> c;
  //Constructor
  EmptyMap() { }

  EmptyMap(Comparator<? super K> c) {
    this.c = c;
  }

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
    throw new RuntimeException("Attempt to get key/value"
        + "pairing from an emptymap.");
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

  // I would have liked to maybe throw an error here instead
  ArrayList<K> getKeys() {
    return new ArrayList<K>();
  }

  ArrayList<K> restKeys(ArrayList<K> keys) {
    return keys;
  }

  @SuppressWarnings("unchecked")
  public Iterator<K> iterator() {
    return new FMapIterator<K>(this);
  }

  @SuppressWarnings("unchecked")
  public Iterator<K> iterator(Comparator<? super K> c) {
    return new FMapIterator<K>(this, c);
  }

  public FMap<K,V> accept(Visitor<K, V> visitor) {
    return this;
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
    ArrayList<K> keys = this.getKeys();
    Iterator<K> it = keys.iterator();
    int i = 1;
    while(it.hasNext()) {
      K key = it.next();
      V val = this.get(key);
      i = i * ((13+key.hashCode()) + (5+val.hashCode()));
    }
    return i;
  }
  @SuppressWarnings("unchecked")
  public boolean equals(Object o) {
    if (o instanceof FMap) {
      FMap<K, V> that = (FMap<K, V>)o;
      if (that.isEmpty()) {
        return false;
      }
      else if (this.containsAll(this, that) 
          && that.containsAll(that, this)) {
        return (this.getAll(this, that) && (that.getAll(that, this)));
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  boolean containsAll(FMap<K, V> a, FMap<K, V> b) {
    if (this.rest.containsKey(this.key)) {
      return this.rest.containsAll(a, b);
    } else if (a.containsKey(this.key) == b.containsKey(this.key)) {
      return this.rest.containsAll(a, b);
    } else {
      return false;
    }
  }

  boolean getAll(FMap<K, V> a, FMap<K, V> b) {
    if (this.rest.containsKey(this.key)) {
      return this.rest.getAll(a, b);
    } else if (a.get(this.key).equals(b.get(this.key))) {
      return this.rest.getAll(a, b);
    } else {
      return false;
    }
  }

  // NOTE: Skips over elements whose key is equal to a previous
  // element's key
  public ArrayList<K> getKeys() {
    ArrayList<K> keys = new ArrayList<K>();
    keys = this.restKeys(keys);
    return keys;
  }

  // Helper method for getKeys()
  ArrayList<K> restKeys(ArrayList<K> keys) {
    if (this.rest.containsKey(this.key)) {
      return this.rest.restKeys(keys);
    } else {
      keys.add(this.key);
      return this.rest.restKeys(keys);
    }
  }

  @SuppressWarnings("unchecked")
  public Iterator<K> iterator() {
    return new FMapIterator<K>(this);
  }

  @SuppressWarnings("unchecked")
  public Iterator<K> iterator(Comparator<? super K> c) {
   return new FMapIterator<K>(this, c);
  }

  public FMap<K,V> accept(Visitor<K, V> visitor) {
    FMap<K,V> m2 = FMap.emptyMap();
    for (K k : this) {
      V v = visitor.visit(k, this.get(k));
      m2 = m2.add(k, v);
    }
    return m2;
  }

}


class FMapIterator<K> implements Iterator {
  FMap map;                 //The map whose data we are iterating over
  ArrayList<K> keys;        //Arraylist of keys from map
  Iterator<K> keysIterator; //Nested Iterator for the Arraylist
  Comparator<? super K> c;          //Comparator to sort the keys

  // Constructor with no comparator
  @SuppressWarnings("unchecked")
    FMapIterator(FMap map) {
      if (!map.isEmpty()) {
        this.map = map;
        this.keys = map.getKeys();
        this.keysIterator = keys.iterator();
      } else {  //if the fmap is empty
        this.keys = new ArrayList<K>();
        this.keysIterator = keys.iterator();
      }
    }

  // Constructor with comparator
  @SuppressWarnings("unchecked")
  FMapIterator(FMap map, Comparator<? super K> c) {
    if (!map.isEmpty()) {
      this.map = map;
      this.keys = map.getKeys();
      Collections.sort(keys, c); //Sorts keys based on c
      this.keysIterator = keys.iterator();
    } else {  //if the fmap is empty
      this.keys = new ArrayList<K>();
      this.keysIterator = keys.iterator();
    }
  }

  @SuppressWarnings("unchecked")
  FMapIterator(ArrayList<K> keys, Comparator<? super K> c) {
    this.keys = keys;
    this.c = c;
    Collections.sort(keys, c);
    this.keysIterator = keys.iterator();
  }

  @SuppressWarnings("unchecked")
  FMapIterator(ArrayList<K> keys) {
    this.keys = keys;
    this.keysIterator = keys.iterator();
  }

  // hasNext() : -> boolean
  // Any more elements?
  public boolean hasNext() {
    return keysIterator.hasNext();
  }

  //next() : -> boolean
  //Returns the next element
  public K next() {
    return keysIterator.next();
  }

  // Not supported
  public void remove() {
    throw new UnsupportedOperationException("Remove not supported");
  }
}
