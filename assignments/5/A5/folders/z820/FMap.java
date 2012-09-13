public abstract class FMap<K, V> {

	
	abstract int sizeMethod();

	
	abstract boolean isEmptyMethod();

	
	abstract boolean containsKeyMethod(K key);

	
	abstract V getMethod(K key);
	
	
	abstract boolean isSubsetKeyMethod(FMap sub);
	
	
	abstract boolean keyToValue(FMap fmap);
	
	
	abstract int hash();

	
	public static <K, V> FMap<K, V> emptyMap() {
		return new EmptySet<K, V>();
	}

	 
	public FMap<K, V> add(K key, V value) {
		if (this.containsKeyMethod(key))
			return this;
		else
			return new Adjoin(this, key, value);
	}

	
	public boolean isEmpty() {
		return this.isEmptyMethod();
	}

	
	public int size() {
		return this.sizeMethod();
	}

	
	public boolean containsKey(K key) {
		return this.containsKeyMethod(key);
	}

	
	public V get(K key) {
		return this.getMethod(key);
	}
	
	
	public boolean isSubsetKey(FMap sub) {
		return this.isSubsetKeyMethod(sub);
	}

	
	public String toString() {
		return "{...(" + this.sizeMethod() + " entries)...}";
	}

	
	public boolean equals(Object s) {
		if (s == null)
			return false;
		else if (s instanceof FMap) {
			if (((FMap) s).isSubsetKey(this) && this.isSubsetKey((FMap) s)) {
				return this.keyToValue((FMap) s);
			} else
				return false;
		} else
			return false;
	}

	
	public int hashCode() {
		return this.hash();
	}

	
	private static class EmptySet<K, V> extends FMap<K, V> {
		
		EmptySet() {
		}

		int sizeMethod() {
			return 0;
		}

		boolean isEmptyMethod() {
			return true;
		}

		boolean containsKeyMethod(K key) {
			return false;
		}

		V getMethod(K key) {
			throw new RuntimeException(
					"Key does not exist within this set");
		}
		
		boolean isSubsetKeyMethod(FMap sub) {
			return true;
		}
		
		boolean keyToValue(FMap fmap) {
			return true;
		}
		
		
		int hash() {
			return 56789032;
		}

	}

	
	private static class Adjoin<K, V> extends FMap<K, V> {
		
		FMap<K, V> fmap;
		
		K key;
		
		V value;
		
		Adjoin(FMap<K, V> fmap, K key, V value) {
			this.fmap = fmap;
			this.key = key;
			this.value = value;
		}

		int sizeMethod() {
			if (this.fmap.containsKeyMethod(this.key))
				return this.fmap.sizeMethod();
			else
				return 1+this.fmap.sizeMethod();
		}

		boolean isEmptyMethod() {
			return false;
		}

		boolean containsKeyMethod(K key) {
			return this.key.equals(key) || this.fmap.containsKeyMethod(key);
		}

		V getMethod(K key) {
			if (key.equals(this.key))
				return this.value;
			else
				return this.fmap.getMethod(key);
		}
		
		boolean isSubsetKeyMethod(FMap sub) {
			return sub.containsKeyMethod(this.key) 
					&& this.fmap.isSubsetKeyMethod(sub);
		}
		
		boolean keyToValue(FMap fmap) {
				return this.value.equals(fmap.getMethod(this.key)) && this.fmap.keyToValue(fmap);
		}
		
		int hash() {
			if (this.containsKey(this.key))
				return this.hash();
			else
				return this.key.hashCode() + this.fmap.hash();
		}

	}

}
