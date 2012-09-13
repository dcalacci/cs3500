


public abstract class FMap<K, V> {
	
	public static <K, V> FMap<K, V> emptyMap(){
		return new EmptyMap<K, V>();
	}
	
	
	public FMap<K, V> add(K k, V v){
		return new AddMap<K, V>(this, k, v);
	}
	
	
	public abstract boolean isEmpty();
	
	
	public abstract int size();
	
	
	public abstract boolean containsKey(K k);
	
	
	public abstract V get(K k);
	
	
	@Override
	public String toString(){
		return "{...(" + size() + " entries)...}";
	}
	
	
	@Override
	@SuppressWarnings(value = "unchecked")
	public boolean equals(Object x){
		if(x instanceof FMap){
			FMap<K, V> m = (FMap<K, V>) x;
			return hasAllKeys(m) && m.hasAllKeys(this) 
					&& sameValues(this, m);
		}
		else
			return false;
	}
	
	
	@Override
	public int hashCode(){
		return size();
	}
	
	
	abstract boolean hasAllKeys(FMap<K, V> m);
	
	
	abstract boolean sameValues(FMap<K, V> m1, FMap<K, V> m2);
}

class EmptyMap<K, V> extends FMap<K, V>{

	
	@Override
	public boolean isEmpty() {
		return true;
	}

	
	@Override
	public int size() {
		return 0;
	}

	
	@Override
	public boolean containsKey(K k) {
		return false;
	}

	
	@Override
	public V get(K k) {
	
		throw new IllegalArgumentException(
					"This map doesn't have the given key");
	}

	
	@Override
	boolean hasAllKeys(FMap<K, V> m) {
		return true;
	}

	
	@Override
	boolean sameValues(FMap<K, V> m1, FMap<K, V> m2) {
		return true;
	}
}

class AddMap<K, V> extends FMap<K, V>{
	FMap<K, V> m0;	
	K k0;			
	V v0;			
	
	AddMap(FMap<K, V> m, K k, V v){
		m0 = m;
		k0 = k;
		v0 = v;
	}

	
	@Override
	public boolean isEmpty() {
		return false;
	}

	
	@Override
	public int size() {
		if(m0.containsKey(k0))
			return m0.size();
		else
			return m0.size() + 1;
	}

	
	@Override
	public boolean containsKey(K k) {
		if(k.equals(k0))
			return true;
		else
			return m0.containsKey(k);
	}

	
	@Override
	public V get(K k) {
		if(k.equals(k0))
			return v0;
		else
			return m0.get(k);
	}

	
	@Override
	boolean hasAllKeys(FMap<K, V> m) {
		if(m.containsKey(k0))
			return m0.hasAllKeys(m);
		else
			return false;
	}

	
	@Override
	boolean sameValues(FMap<K, V> m1, FMap<K, V> m2) {
		if(m1.get(k0).equals(m2.get(k0)))
			return m0.sameValues(m1, m2);
		else
			return false;
	}
}