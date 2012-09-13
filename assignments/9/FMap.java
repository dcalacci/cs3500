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

  // iterator : -> Iterator<K>
  // Returns an iterator that generates every key k in this
  //  FMap
  public abstract Iterator<K> iterator();

  // iterator : -> Iterator<K>
  // Returns an iterator that generates every key k in this
  //  FMap, with keys generated in increasing order as
  //  determined by c
  public abstract Iterator<K> iterator(Comparator<? super K> c);

  // accept : Visitor<K, V> -> FMap<K, V>
  // Accepts the visitor and calls it's visit() method for
  // this fmap
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

  // restKeys() : -> ArrayList<K>
  // Helper/recursive method for getKeys()
  abstract ArrayList<K> restKeys(ArrayList<K> keys);

}

// Color enumeration for coloring of nodes
enum Color {
  RED,BLACK
}

abstract class RBTree<K, V>  extends FMap<K, V> 
                              implements Iterable<K> {
  public abstract boolean       isEmpty();
  public abstract Integer       size();
  public abstract boolean       containsKey(K key);
  public abstract V             get(K key);

  public FMap<K, V>    add(K key, V val) {
    return this.ins(key, val).makeBlack();
  }

  abstract ArrayList<K>         getKeys();

  // Helper method for getKeys()
  abstract void getKeysHelper(ArrayList<K> keys);

  abstract Color getColor();
  abstract RBTree<K, V> setColor(Color color);

  abstract RBTree<K, V> getLeft();
  abstract RBTree<K, V> getRight();
  abstract K getKey();
  abstract V getVal();


  // Balancing helper methods
  abstract RBTree<K, V> case1();
  abstract RBTree<K, V> case2();
  abstract RBTree<K, V> case3();
  abstract RBTree<K, V> case4();
  abstract RBTree<K, V> ins(K key, V val);
  abstract RBTree<K, V> balance(RBTree<K, V> t);
  abstract RBTree<K, V> makeBlack();

}


class Node<K, V> extends RBTree<K, V> {
  K key;                        // This node's key
  V val;                        // This node's value
  Comparator<? super K> c;      // Comparator for keys
  RBTree<K, V>          left;   // Left child
  RBTree<K, V>          right;  // Right child
  Integer               size;   // Size of the tree at this node
  Color                 color;  // Color of this node

  // Constructor with no color, color is set to BLACK
  //Node(K key, V val, Comparator<? super K> c, RBTree<K, V> left,
                                              //RBTree<K, V> right) {
    //this.key    = key;
    //this.val    = val;
    //this.c      = c;
    //this.left   = left;
    //this.right  = right;
    //this.size   = this.preSize();
    //this.color  = Color.BLACK;
  //}
  //Constructor with color
  Node(K key, V val, Comparator<? super K> c, RBTree<K, V> left,
                                              RBTree<K, V> right,
                                              Color color) {
    this.key    = key;
    this.val    = val;
    this.c      = c;
    this.left   = left;
    this.right  = right;
    this.size   = this.preSize();
    this.color  = color;
  }

  // abstract ArrayList<K> restKeys(ArrayList<K> keys);
  ArrayList<K> restKeys(ArrayList<K> keys) {
    throw new RuntimeException("This should not be called");
  }

  Integer preSize() {
    return 1 + this.left.size() + this.right.size();
  }

  public Integer size() {
    return this.size;
  }

  public boolean isEmpty() {
    return false;
  }

  // Read-Only operations(accessors)
  public boolean containsKey(K key) {
    if (this.c.compare(this.key, key) > 0) {
      return this.right.containsKey(key);
    } else if (this.c.compare(this.key, key) < 0) {
      return this.left.containsKey(key);
    } else {
      return this.key.equals(key);
    }
  }

  public V get(K key) {
    if (this.c.compare(this.key, key) == 0) {
      return this.val;
    } else if (this.c.compare(this.key, key) > 0) {
      return this.right.get(key);
    } else {
      return this.left.get(key);
    }
  }

  ArrayList<K> getKeys() {
    ArrayList<K> keys = new ArrayList<K>();
    this.getKeysHelper(keys);
    return keys;
  }

  void getKeysHelper(ArrayList<K> keys) {
    keys.add(this.key);
    if (!this.left.isEmpty()) {
      this.left.getKeysHelper(keys);
    }
    if (!this.right.isEmpty()) {
      this.right.getKeysHelper(keys);
    }
  }

  boolean containsAll(FMap<K, V> a, FMap<K, V> b) {
    if (a.containsKey(this.key) == b.containsKey(this.key)) {
      return this.left.containsAll(a, b) && this.right.containsAll(a, b);
    } else {
      return false;
    }
  }

  boolean getAll(FMap<K, V> a, FMap<K, V> b) {
    if (a.get(this.key).equals(b.get(this.key))) {
      return this.left.getAll(a, b) && this.right.getAll(a, b);
    } else {
      return false;
    }
  }

  RBTree<K, V> makeBlack() {
    return new Node<K, V>(this.key, 
                            this.val, 
                            this.c, 
                            this.left, 
                            this.right, 
                            Color.BLACK);
  }

  RBTree<K, V> ins(K key, V val) {
    if (this.c.compare(this.key, key) < 0) {
        return (new Node<K, V>(this.key, this.val,
                                      this.c, 
                                      this.right, 
                                      this.left.ins(key, val),
                                      this.color));
    } else if (this.c.compare(this.key, key) > 0) {
      return (new Node<K, V>(this.key, this.val,
                                      this.c, 
                                      this.right.ins(key, val), 
                                      this.left,
                                      this.color));
    } else {
      return new Node<K, V>(key, val, 
                                      this.c, 
                                      this.left, 
                                      this.right, 
                                      this.color);
    }
  }

  //Balancing and helper methods
  RBTree<K, V> balance(RBTree<K, V> t) {
    if (t.getLeft().isEmpty() && t.getRight().isEmpty()) {
      return t;
    } else if (t.getLeft().getColor().equals(Color.RED) &&
      t.getLeft().getRight().getColor().equals(Color.RED)) {
      return t.case1();
    } else if (t.getLeft().getColor().equals(Color.RED) &&
        (t.getLeft().getLeft().getColor().equals(Color.RED))) {
      return t.case2();
    } else if ((t.getRight().getColor().equals(Color.RED)) &&
        (t.getRight().getLeft().getColor().equals(Color.RED))) {
      return t.case3();
    } else if ((t.getRight().getColor().equals(Color.RED)) &&
        (t.getRight().getRight().getColor().equals(Color.RED))) {
      return t.case4();
    } else {
      return t;
    }
  }

  // Case 1 for imbalance - red left child with a red right child
  RBTree<K, V> case1() {
    // "value" trees
    RBTree<K, V> x = this.left;
    RBTree<K, V> y = this.left.getRight();
    RBTree<K, V> z = this;
    // "leaf" trees
    RBTree<K, V> a = this.left.getLeft();
    RBTree<K, V> b = this.left.getRight().getLeft();
    RBTree<K, V> c = this.left.getRight().getRight();
    RBTree<K, V> d = this.right;

    return this.makeBalanced(x, y, z, a, b, c, d);
  }

  // Case 2 for imbalance - red left child with a red left child
  RBTree<K, V> case2() {
    // "value" trees
    RBTree<K, V> x = this.left.getLeft();
    RBTree<K, V> y = this.left;
    RBTree<K, V> z = this;
    // "leaf" trees
    RBTree<K, V> a = this.left.getLeft().getLeft();
    RBTree<K, V> b = this.left.getLeft().getRight();
    RBTree<K, V> c = this.left.getRight();
    RBTree<K, V> d = this.right;

    return this.makeBalanced(x, y, z, a, b, c, d);
  }

  // Case 3 for imbalance - red right child with a red left child
  RBTree<K, V> case3() {
    // "value" trees
    RBTree<K, V> x = this;
    RBTree<K, V> y = this.right.getLeft();
    RBTree<K, V> z = this.right;
    // "leaf" trees
    RBTree<K, V> a = this.left;
    RBTree<K, V> b = this.right.getLeft().getLeft();
    RBTree<K, V> c = this.right.getLeft().getRight();
    RBTree<K, V> d = this.right.getRight();

    return this.makeBalanced(x, y, z, a, b, c, d);
  }

  //Case 4 for imbalance - red right child with a red right child
  RBTree<K, V> case4() {
    // "value" trees
    RBTree<K, V> x = this;
    RBTree<K ,V> y = this.right;
    RBTree<K, V> z = this.right.getRight();
    // "leaf" trees
    RBTree<K, V> a = this.left;
    RBTree<K, V> b = this.right.getLeft();
    RBTree<K, V> c = this.right.getRight().getLeft();
    RBTree<K, V> d = this.right.getRight().getRight();

    return this.makeBalanced(x, y, z, a, b, c, d);
  }

  // makes the balanced tree out of the parts of the
  // imbalanced trees (helper method)
  RBTree<K, V> makeBalanced(RBTree<K, V> x, RBTree<K, V> y,
      RBTree<K, V> z, RBTree<K, V> a,
      RBTree<K, V> b, RBTree<K, V> c,
      RBTree<K, V> d) {
    //Convenience
    Comparator<? super K> co = this.c;
    // Left child of the tree to return
    RBTree<K, V> left   = new Node<K, V>(x.getKey(), x.getVal(), co,
        a, b, Color.BLACK);
    // Right child of the tree to return
    RBTree<K, V> right  = new Node<K, V>(z.getKey(), z.getVal(), co,
        c, d, Color.BLACK);
    // Construct and return the balanced tree
    return new Node<K, V>(y.getKey(), y.getVal(), co,
        left, right, Color.RED);
  }

  //Color operations
  Color getColor() {
    return this.color;
  }

  RBTree<K, V> setColor(Color color) {
    return new Node<K, V>(this.key, this.val, this.c, this.left, 
                            this.right, color);
  }

  // Simple accessors for balancing
  RBTree<K, V> getLeft() {
    return this.left;
  }
  RBTree<K, V> getRight() {
    return this.right;
  }
  K getKey() {
    return this.key;
  }
  V getVal() {
    return this.val;
  }

  // Public Methods
  public Iterator<K> iterator(Comparator<? super K> c) {
    return new FMapIterator(this.getKeys(), c);
  }

  public Iterator iterator() {
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

  public int hashCode() {
    ArrayList<K> keys = this.getKeys();
    Iterator<K> it = keys.iterator();
    Integer i = 1;
    while (it.hasNext()) {
      K key = it.next();
      V val = this.get(key);
      i = i * key.hashCode() * (5+val.hashCode());
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

class EmptyTree<K, V> extends RBTree<K, V> {
  Comparator<? super K> c;  // Comparator for keys
  Color color = Color.BLACK;
  //Constructor.  Should only ever be called with a comparator.
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
  ArrayList<K> getKeys() {
    return new ArrayList<K>();
  }
  void getKeysHelper(ArrayList<K> keys) {
    keys = keys;
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
  boolean getAll(FMap<K, V> a, FMap<K, V> v) {
    return true;
  }
  //public methods
  public int hashCode() {
    return 5;
  }
  public String toString() {
    return "{...(" + this.size() + " entries)...}";
  }
  public FMap<K, V> accept(Visitor<K, V> visitor) {
    return this;
  }
  // Iterator Methods
  public Iterator<K> iterator(Comparator<? super K> c) {
    return new FMapIterator(this.getKeys(), c);
  }
  public Iterator<K> iterator() {
    return new FMapIterator(this.getKeys());
  }
  RBTree<K, V> right() {
    throw new RuntimeException("Error in accessing trees..?");
  }
  RBTree<K, V> left() {
    throw new RuntimeException("Error in accessing trees..?");
  }

  Color getColor() {
    return Color.BLACK;
  }
  RBTree<K, V> makeBlack() {
    return this;
  }

  RBTree<K, V> getLeft(){
    throw new RuntimeException("getLeft unsupported"); 
  }
  RBTree<K, V> getRight(){
    throw new RuntimeException("getRight unsupported"); 
  }
  K getKey(){
    throw new RuntimeException("getKey unsupported"); 
  }
  V getVal(){
    throw new RuntimeException("getVal unsupported"); 
  }

  // Balancing helper methods
  RBTree<K, V> case1(){
    throw new RuntimeException("case1 unsupported"); 
  }
  RBTree<K, V> case2(){
    throw new RuntimeException("case2 unsupported"); 
  }
  RBTree<K, V> case3(){
    throw new RuntimeException("case3 unsupported"); 
  }
  RBTree<K, V> case4(){
    throw new RuntimeException("case4 unsupported"); 
  }
  // make red node to keep invariant 2
  RBTree<K, V> ins(K key, V val){
    return new Node<K, V>(key, val, this.c, this, this, Color.RED);
  }
  RBTree<K, V> balance(RBTree<K, V> t){
    throw new RuntimeException("balance unsupported");
  }
  // Unsupported Operations
  ArrayList<K> restKeys(ArrayList<K> keys) {
    throw new RuntimeException("restKeys will never be called");
  }
  RBTree<K, V> setColor(Color c) {
    throw new RuntimeException("empty trees are always black");
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
      i = i * key.hashCode() * (5+val.hashCode());
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
