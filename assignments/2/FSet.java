/*
 * Dan Calacci
 * dcalacci@ccs.neu.edu
*/


// FSet is one of:
// -- new EmptySet()
// -- new Adjoin(FSet, String)
public abstract class FSet {
  // size : -> FSet
  // Computes the number of elements in this FSet, ignoring duplicate
  //   elements
  abstract int      size();

  // isEmpty : -> boolean
  // Returns whether or not this FSet is empty
  abstract boolean  isEmpty();

  // contains : String -> boolean
  // Returns true if this FSet contains the given string
  abstract boolean  contains(String s);

  // isSubset : FSet -> boolean
  // Returns true if this FSet is a subset of the given FSet
  abstract boolean  isSubset(FSet fset);

  // without : String -> FSet
  // Returns an FSet that is identical to this FSet, except all elements
  //   equal to the given string are removed
  abstract FSet     without(String s);

  // add : String -> FSet
  // Adds the given string to this FSet
  abstract FSet     add(String s);

  // union : FSet -> FSet
  // Returns the union of this FSet and the given FSet
  abstract FSet     union(FSet fset);

  // intersect : FSet -> FSet
  // Returns the intersection of this FSet and the given FSet
  abstract FSet     intersect(FSet fset);

  // adjoin : String -> FSet
  // Adds s to this FSet
  abstract FSet adjoin(String s);



  // hashCode : -> int
  // Returns the hashcode of this FSet
  public abstract int hashCode();



  // emptySet : -> FSet
  // Returns an instance of the EmptySet class
  public static FSet emptySet(){
    return new EmptySet();
  }

  // toString : -> String
  // Returns a string representation of this FSet
  public String toString() {
    return "{...(" + this.size() + " entries)...}";
  }

  // equals : Object -> boolean
  // Returns true if o is an FSet whose elements are the same as this 
  //   FSet's.
  public boolean equals(Object o) {
    if (! (o instanceof FSet)) {
      return false;
    }
    else {
      FSet set = (FSet)o;
      // check to see if o is an instance of this for efficiency
      return (this == set) | this.isSubset(set) && set.isSubset(this);
    }
  }
}


class EmptySet extends FSet {
  EmptySet() { }

  FSet adjoin(String s) {
    return new Adjoin(this, s);
  }
  int size() {
    return 0;
  }
  FSet without(String s) {
    return this;
  }
  FSet add(String s) {
    return adjoin(s);
  }
  boolean isSubset(FSet fset) {
    return true;
  }
  boolean contains(String s) {
    return false;
  }
  boolean isEmpty() {
    return true;
  }
  FSet intersect(FSet fset) {
    return this;
  }
  FSet union(FSet fset) {
    return fset;
  }
  public int hashCode() {
    return 0;
  }
}

class Adjoin extends FSet {
  FSet fset;    // the nested FSet
  String s;     // the first element of this FSet

  //Constructor
  Adjoin(FSet fset, String s) {
    this.fset = fset;
    this.s    = s;
  }


  FSet adjoin(String s) {
    return new Adjoin(this, s);
  }

  boolean isEmpty() {
    return false;
  }
  int size() {
    if (this.fset.contains(s)) {
      return this.fset.size();
    } else {
      return 1 + this.fset.size();
    }
  }
  boolean contains(String s) {
    if (this.s.equals(s)) {
      return true;
    } else {
      return this.fset.contains(s);
    }
  }
  boolean isSubset(FSet fset) {
    if (fset.contains(this.s)) {
      return this.fset.isSubset(fset);
    } else {
      return false;
    }
  }
  FSet add(String s) {
    if (contains(s)) {
      return this; // if this already contains s, do nothing
    } else {
      return this.adjoin(s); // otherwise add s to this
    }
  }
  FSet without(String s) {
    if (this.s.equals(s)) {
      return this.fset;
    } else
      //not sure if adjoin or add
      return this.fset.without(s).add(this.s);
  }
  FSet intersect(FSet fset) {
    if (fset.contains(this.s)) {
      return this.fset.intersect(fset).add(this.s);
    } else {
      return this.fset.intersect(fset);
    }
  }
  FSet union(FSet fset) {
    if (fset.contains(this.s)) {
      return this.fset.union(fset);
    } else {
      return this.fset.union(fset).add(this.s);
    }
  }
  public int hashCode() {
    // h represents the hash code of this object.
    int h = 5; // arbitrary starting value to decrease false positives
    if (this.fset.contains(this.s)) {
      h = this.fset.hashCode(); //ignores duplicate elements
    } else {
      // this method of computing the hashCode ignores order and produces
      // a number sufficiently large enough to ensure that it is very 
      // unlikely that two unequal FSets will have the same hash code.
      h = this.s.hashCode() * this.fset.hashCode();
    }
    return h;
  }
}
