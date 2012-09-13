

public abstract class FMap<K,V> {

    
    public abstract boolean isEmpty();

    
    public abstract int size();

    
    public abstract boolean containsKey(K k);

    
    public abstract V get(K k);

    
    public abstract boolean containsPair(K k, V v);

    
    public abstract boolean containsAllPairs(FMap<K,V> f);

    
    public static <K,V> FMap<K,V> emptyMap() {
	return new EmptyMap<K,V>();
    }


    
    
    public FMap<K,V> add(K k, V v) {
	if (this.containsKey(k)
	    && this.get(k).equals(v)) {
	    return this;
	} else {
	return new Add<K,V>(this, k, v);
	}
    }

    
    public String toString() {
	return "{...(" 
	     + this.size() 
	     + " entries)...}";
    }

    
    public int hashCode() {
	return this.size();
    }

    @Override
    @SuppressWarnings("unchecked")
    
    public boolean equals(Object o) {
	if (o instanceof FMap) {
	    FMap<K,V> f = (FMap<K,V>) o;
	    return this.containsAllPairs(f)
		&& f.containsAllPairs(this);
	} else { return false; }
    }
    

}


class EmptyMap<K,V> extends FMap<K,V> {

    public boolean isEmpty() {
	return true;
    }

    public int size() {
	return 0;
    }

    public boolean containsKey(K k) {
	return false;
    }

    public V get(K k) {
	String e = "Key not found.";
	throw new RuntimeException(e);
    }

    public  boolean containsPair(K k, V v) {
	return false;
    }

    public boolean containsAllPairs(FMap<K,V> f) {
	return f.isEmpty();
    }


}


class Add<K,V> extends FMap<K,V> {
    FMap<K,V> m0; 
    K k0; 
    V v0; 

    Add(FMap<K,V> m0, K k0, V v0) {
	this.m0 = m0;
	this.k0 = k0;
	this.v0 = v0;
    }

    public boolean isEmpty() {
	return false;
    }

    public int size() {
	if (m0.containsKey(k0)) {
	    return m0.size();
	} else {
	    return 1 + m0.size();
	}
    }

    public boolean containsKey(K k) {
	return k.equals(k0)
	    || m0.containsKey(k);
    }

    public V get(K k) {
	if (k.equals(k0)) {
	    return v0;
	} else {
	    return (V) m0.get(k);
	}
    }

    public  boolean containsPair(K k, V v) {
	return (k.equals(k0) && v.equals(v0))
	    || m0.containsPair(k, v);
    }

    public boolean containsAllPairs(FMap<K,V> f) {
	if (!f.isEmpty()) {
	    Add<K,V> x = ( Add<K,V> ) f;
	return this.containsPair(x.k0, x.v0)
	    && this.containsAllPairs(x.m0);
	} else { return true; }
    }

}