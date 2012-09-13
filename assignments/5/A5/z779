



public abstract class FMap<K,V> { 

    
    public abstract int size ();
 
    
    public abstract boolean isEmpty ();

    
    public abstract boolean containsKey (K k); 

    
    public abstract V get (K k);
    

    
    public static <K,V> FMap<K,V> emptyMap () { 
	return new EmptyMap<K,V> ();
    }
    
    
    public FMap<K,V> add(K k, V v) {
	return new Add<K,V>(this, k , v);
    }   

    
    
    abstract boolean areKeysSubset (FMap<K,V> that);
    
    
    
    abstract boolean valuesEqual (FMap<K,V> m1, FMap<K,V> m2);

    
    
   
    @SuppressWarnings(value="unchecked") 
    public boolean equals (Object x) {
	if (x instanceof FMap) { 
    FMap<K,V> m2 = (FMap<K,V>) x;
	    return ((this.areKeysSubset(m2) && m2.areKeysSubset(this)) &&
		    this.valuesEqual(this, m2));
	}
	else return false;
    }
    
    
    public int hashCode () {
	return this.size() * 657890 + 2345;
    }
    
    
    
    public String toString () {
	return  "{...(" + this.size() + " entries)...}";
    } 
    
}


class EmptyMap<K,V> extends FMap {
    
    EmptyMap () {}
    
    public int size () {
	return 0;
    }

    public boolean isEmpty () {
	return true;
    }

    public boolean containsKey (K k) {
	return false;
    }

    public V get (K k) {
	throw new IllegalArgumentException("Illeagal call to get on an EmptyMap"); 
    }

    boolean areKeysSubset(FMap that) {
	return true;
    }

    boolean valuesEqual(FMap m1,FMap m2){
	return true;
    }

    
}


class Add<K,V> extends FMap {

    FMap m0; 
    K k0; 
    V v0; 
    
    Add (FMap m0, K k0, V v0) {
	this.m0 = m0;
	this.k0 = k0;
	this.v0 = v0;
    }
    
    public int size () {
	if (m0.containsKey(k0)) {
		return m0.size();
	    }
	else {
		return 1 + m0.size();
	    }
    }

    public boolean isEmpty () {
	return false;
    }

    public boolean containsKey (K k) {
	if (k.equals(k0)) {
		return true;
	    }
	else {
		return m0.containsKey(k);
	    }
    }

    public V get (K k) {
	if (k.equals(k0)) {
	    return v0;
	}
	else {
	    return m0.get(k);
	}
    }

    boolean areKeysSubset(FMap that) {
	if (that.containsKey(k0)) {
		return m0.areKeysSubset(that);
	}
	else {
	    return false;
	}
    }

    boolean valuesEqual(FMap m1,FMap m2) {
	if (m1.get(k0).equals(m2.get(k0))) {
		return m0.valuesEqual(m1,m2);
	}
	else {
	    return false;
	}
    }
}
