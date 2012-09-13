/* Dan Calacci
 * dcalacci@ccs.neu.edu
 * */
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Iterable;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.TreeMap;

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
}



class EmptyTree<K, V> extends FTree<K, V> {
  Comparator<? super K> c;

  EmptyTree() {
  }

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

}

class Node<K, V> extends FTree<K, V> {
  K key;
  V val;
  Comparator<? super K> c;
  FTree<K, V> t1;
  FTree<K, V> t2;

  Node(K key, V val, Comparator<? super K> c, FTree<K, V> t1, FTree<K, V> t2) {
    this.key =  key;
    this.val =  val;
    this.c =    c;
    this.t1 =   t1;
    this.t2 =   t2;
  }

  public boolean isEmpty() {
    return false;
  }

  public Integer size() {
    return 1 + this.t1.size() + this.t2.size();
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
      return new Node<K, V>(key, val, this.c, this.t1, this.t2);
    } else if (this.c.compare(this.key, key) > 0) {
      return new Node<K, V>(this.key, this.val, this.c, this.t1, this.t2.insert(key, val));
    } else {
      return new Node<K, V>(this.key, this.val, this.c, this.t1.insert(key, val), this.t2);
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

  ArrayList<K> getKeys(){
    ArrayList<K> alist;
    if (!this.t1.isEmpty()) {
      alist = this.t1.getKeys();
    }
    alist.add(this.key);
    if (!this.t2.isEmpty()) {
      alist.addAll(this.t2.getKeys());
    }
    return alist;
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
      if (this.containsAll(this, that) && (that.containsAll(that, this))) {
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

  ArrayList<K> restKeys(ArrayList<K> keys) {
    throw new RuntimeException("should never be called");
  }

}
