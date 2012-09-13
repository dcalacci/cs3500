

public abstract class FMap<K,V>{
	
	abstract boolean isEmpty();
	abstract int size();
	abstract boolean containsKey(K x);
	abstract V get(K x);
	
	
	
	abstract boolean isSubsetWRTKeys(FMap<K,V> that);
	
	
	
	
	abstract boolean valuesEqual(FMap<K,V> m1, FMap<K,V> m2);
	
	
	public static <K,V> FMap <K,V> emptyMap(){
		return new EmptyMap<K,V>();
	}
	
	
	public FMap<K,V> add(FMap<K,V> m0, K k0, V v0){
		return new Add<K,V>(this, k0, v0);
	}
	
	
	public static <K,V> boolean isEmpty(FMap<K,V> m0){
		return m0.isEmpty();
	}
	
	
	public static <K,V> int size(FMap<K,V> m0){
		return m0.size();
	}
	
	
	public static <K,V> boolean containsKey(FMap<K,V> m0, K x){
		return m0.containsKey(x);
	}
	
	
	public static <K,V> V get(FMap<K,V> m0, K x){
		return (V) m0.get(x);
	}
	
	
	public String toString(){
		return "{...(" + this.size() + " entries)...}";
	}
	
	@Override
	@SuppressWarnings(value = "unchecked")
	
	public boolean equals(Object x){
		if(x instanceof FMap){
			FMap<K,V> that = (FMap<K,V>) x;
			return ((this.isSubsetWRTKeys(that) && 
					 that.isSubsetWRTKeys(this))
					 &&	this.valuesEqual(this, that));
		}else{
			return false;
		}
	}	
	
	
	public int hashCode(FMap<K,V> m){
		return 13771010;
	}
}

class EmptyMap<K,V> extends FMap<K,V>{
	
	public EmptyMap(){
	}

	@Override
	
	boolean isEmpty(){
		return true;
	}

	@Override
	
	int size(){
		return 0;
	}

	@Override
	
	boolean containsKey(K x){
		return false;
	}

	@Override
	
	V get(K x){
		throw new IllegalArgumentException
		("Cannot 'get' from the EmptyMap class");
	}
	
	
	
	boolean isSubsetWRTKeys(FMap<K,V> that){
		return true;
	}
	
	
	
	
	boolean valuesEqual(FMap<K,V> m1, FMap<K,V> m2){
		return true;
	} 
}

class Add<K,V> extends FMap<K,V>{
	
	
	private K k0;
	
	private V v0;
	
	private FMap<K,V> m0;
	
	
	public Add(FMap<K, V> m0, K k, V v) {
		this.m0 = m0;
		this.k0 = k0;
		this.v0 = v0;
	}
	
	@Override
	
	boolean isEmpty(){
		return false;
	}

	@Override
	
	int size(){
		if(this.containsKey(k0)){
			return FMap.size(m0);
		}else{
			return 1 + FMap.size(m0);
		}
	}

	@Override
	
	boolean containsKey(K x){
		if(x.equals(k0)){
			return true;
		}else{
			return this.containsKey(x);
		}
	}

	@Override
	
	V get(K x){
		if(x.equals(k0)){
			return v0;
		}else{
			return this.get(x);
		}
	}
	
	
	
	boolean isSubsetWRTKeys(FMap<K,V> that){
		if(that.containsKey(this.k0)){
			return this.m0.isSubsetWRTKeys(that);
		}else{
			return false;
		}
	}
	
	
	
	
	boolean valuesEqual(FMap<K,V> m1, FMap<K,V> m2){
		if(m1.get(this.k0).equals(m2.get(this.k0))){
			return this.m0.valuesEqual(m1, m2);
		}else{
			return false;
		}
	} 
}
	