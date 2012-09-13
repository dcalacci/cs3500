                                                           
                                         

public abstract class FMap<K, V> {

	
	public static <K, V> FMap<K, V> emptyMap() {
		return new EmptyMap<K, V>();
	}

	
	public FMap<K, V> add(K k, V v) {
		return new Add<K, V>(this, k, v);
	}

	
	public String toString() {
		return "{...(" + this.size() + " entries)...}";
	}

	
	public abstract boolean isEmpty();

	
	public abstract boolean containsKey(K k0);

	
	public abstract int size();

	
	public abstract V get(K k0);

	
	public abstract boolean equals(Object x);

	
	public abstract boolean isSubsetKeys(FMap<K, V> that);

	
	
	public abstract boolean EqualKeys(FMap<K, V> f1, FMap<K, V> f2);

}





class EmptyMap<K, V> extends FMap<K, V> {
	
	public boolean isEmpty() {
		return true;
	}

	
	public boolean containsKey(K k0) {
		return false;
	}

	
	public int size() {
		return 0;
	}

	
	public V get(K k0) {
		throw new RuntimeException("Object not found");
	}

	
	public boolean equals(Object x) {
		if (x instanceof FMap) {
			FMap<K, V> f2 = (FMap<K,V>) x;
			return f2.isEmpty();
		} else {
			return false;
		}
	}

	
	public boolean isSubsetKeys(FMap<K, V> that) {
		return true;
	}

	
	
	public boolean EqualKeys(FMap<K, V> f1, FMap<K, V> f2) {
		return true;
	}

}




class Add<K, V> extends FMap<K, V> {
	FMap<K, V> fm;
	K k;
	V v;

	Add(FMap<K, V> fm, K k, V v) {
		this.fm = fm;
		this.k = k;
		this.v = v;
	}

	
	public boolean isEmpty() {
		return false;
	}

	
	public boolean containsKey(K k0) {
		if (this.k.equals(k0)) {
			return true;
		} else {
			return this.fm.containsKey(k0);
		}
	}

	
	public int size() {
		if (this.fm.containsKey(this.k)) {
			return fm.size();
		} else {
			return 1 + fm.size();
		}
	}

	
	public V get(K k0) {
		if (this.k.equals(k0)) {
			return this.v;
		} else {
			return this.fm.get(k0);
		}
	}

	
	public boolean equals(Object x) {
		if (x instanceof FMap) {
			FMap<K, V> f2 = (FMap<K, V>) x;
			return this.isSubsetKeys(f2) && f2.isSubsetKeys(this)
					&& this.EqualKeys(this, f2);
		} else {
			return false;
		}
	}

	
	public boolean isSubsetKeys(FMap<K, V> that) {
		if (that.containsKey(this.k)) {
			return this.fm.isSubsetKeys(that);
		} else {
			return false;
		}
	}

	
	
	public boolean EqualKeys(FMap<K, V> f1, FMap<K, V> f2) {
		if (f1.get(this.k).equals(f2.get(this.k))){
			return this.fm.EqualKeys(f1, f2);
		} else {
			return false;
		}

	}

}

