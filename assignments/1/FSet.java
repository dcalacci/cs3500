/*
 * Dan Calacci
 * dcalacci@ccs.neu.edu
*/


// FSet is one of:
// -- new EmptySet()
// -- new Adjoin(FSet, String)
public abstract class FSet {
  // size : -> FSet
  // Computes the number of elements in this FSet, ignoring duplicate elements
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
  // Returns an FSet that is identical to this FSet, except all elements equal
  //    to the given string are removed
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

  // hashCode : -> int
  // Returns the hashcode of this FSet
  public abstract int hashCode();

  // emptySet : -> FSet
  // Returns an instance of the EmptySet class
  public static FSet emptySet(){
    return new EmptySet();
  }

  // adjoin : FSet x String -> FSet
  // Adds the given element s to fset
  public static FSet adjoin(FSet fset, String s){
    return new Adjoin(fset, s);
  }

  // add : FSet x String -> FSet
  // Adds s to fset
  public static FSet add(FSet fset, String s){
    if (fset.contains(s)) {
      return fset;
    } else {
      return fset.add(s);
    }
  }

  // size : FSet -> int
  // Returns the number of distinct elements in fset
  public static int size(FSet fset){
    return fset.size();
  }

  // isEmpty : FSet -> boolean
  // Returns true if fset is empty
  public static boolean isEmpty(FSet fset){
    return fset.isEmpty();
  }

  // contains : FSet x String -> boolean
  // Returns true if any element in fset is equal to s
  public static boolean contains(FSet fset, String s){
   return fset.contains(s);
  }

  // isSubset : FSet, FSet -> boolean
  // Returns true if fsetA is a subset of fsetB.
  public static boolean isSubset(FSet fsetA, FSet fsetB){
    return fsetA.isSubset(fsetB);
  }

  // without : FSet x String -> FSet
  // Returns a new FSet, identical to fset, but with all occurrences of s removed.
  public static FSet without(FSet fset, String s){
    return fset.without(s);
  }

  // union : FSet x FSet -> FSet
  // Returns a new FSet that is the union of fsetA and fsetB
  public static FSet union(FSet fsetA, FSet fsetB) {
    return fsetA.union(fsetB);
  }

  // intersect : FSet x FSet -> FSet
  // Returns a new FSet that is the intersection of fsetA and fsetB
  public static FSet intersect(FSet fsetA, FSet fsetB) {
    return fsetA.intersect(fsetB);
  }

  // toString : -> String
  // Returns a string representation of this FSet
  public String toString() {
    return "{...(" + this.size() + " entries)...}";
  }

  // equals : Object -> boolean
  // Returns true if o is an FSet whose elements are the same as this FSet's
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

  int size() {
    return 0;
  }
  FSet without(String s) {
    return this;
  }
  FSet add(String s) {
    return adjoin(this, s);
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

  boolean isEmpty() {
    return false;
  }
  int size() {
    if (contains(this.fset, this.s)) {
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
    if (contains(this, s)) {
      return this; // if this already contains s, do nothing
    } else {
      return adjoin(this, s); // otherwise add s to this
    }
  }
  FSet without(String s) {
    if (this.s.equals(s)) {
      return this.fset;
    } else
      return adjoin(this.fset.without(s), this.s);
  }
  FSet intersect(FSet fset) {
    if (fset.contains(this.s)) {
      return adjoin(this.fset.intersect(fset), this.s);
    } else {
      return this.fset.intersect(fset);
    }
  }
  FSet union(FSet fset) {
    if (fset.contains(this.s)) {
      return this.fset.union(fset);
    } else {
      return adjoin(this.fset.union(fset), this.s);
    }
  }
  public int hashCode() {
    int h = 5; // arbitrary starting value to decrease false positives
    if (this.fset.contains(this.s)) {
      h = this.fset.hashCode(); //ignores duplicate elements
    } else {
      // this type of multiplication ensures that the hashcode is dependent
      // on the order of the fields.  37 is used as the multiplier becasue
      // it is an odd prime.  This assumes that the element s(in this 
      // implementation, always a string) has a correct hashCode method
      // of its own.
      h = 37*h + this.s.hashCode();
      h = 37*h + this.fset.hashCode();
    }
    return h;
  }
}
