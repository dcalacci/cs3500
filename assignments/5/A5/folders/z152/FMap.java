


public abstract class FMap<K,V> {

	

	
	public static <K,V> FMap<K,V> emptyMap(){
		return new EmptyMap<K,V>();
	}

	

	
	public FMap<K,V> add(K k, V v){
		return new Add<K,V>(this, k, v);
	}

	
	public boolean isEmpty(){
		return this.isEmpty();
	}

	
	public int size(){
		return this.size();
	}

	
	public boolean containsKey(K k){
		return this.containsKey(k);
	}

	
	public V get(K k){
		return this.get(k);
	}

	
	public String toString(){
		return "{...(" + this.size() + " entries)...}";
	}

	
	public int hashCode(){
		return this.size() * 50601991 + 123456789;
	}


	
	public boolean equals(Object x){
		return this.equals(x);
	}

	
	
	public boolean isSubsetWRTKeys(FMap<K,V> that){
		return this.isSubsetWRTKeys(that);
	}

	
	public boolean valuesEqual(FMap<K, V> m1, FMap<K, V> m2 ){
		return this.valuesEqual(m1, m2);
	}
}


class EmptyMap<K,V> extends FMap<K,V>{
	EmptyMap() {}

	public boolean isEmpty(){
		return true;
	}

	public int size(){
		return 0;
	}

	public boolean containsKey(K k){
		return false;
	}

	public V get(K k){
		String s = "cannot get a value from an emptyMap";
		throw new RuntimeException(s);
	}

	public boolean isSubsetWRTKeys(FMap<K, V> that) {
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean equals(Object x) {
		if (x instanceof FMap){
			FMap<K,V> m2 = (FMap<K, V>) x;
			if(m2.isEmpty()){
				return true;
			}
			else 
				return false;
		}
		else
			return false;
	}

	public boolean valuesEqual(FMap<K, V> m1, FMap<K, V> m2) {
		return true;
	}
}


class Add<K,V> extends FMap<K,V>{
	FMap<K, V> m;   
	K k;			
	V v;			
	Add(FMap<K, V> m ,K k, V v){
		this.m = m;
		this.k = k;
		this.v = v;
	}

	public boolean isEmpty(){
		return false;
	}

	public int size(){
		if (m.containsKey(k))
			return m.size();
		else
			return 1 + m.size();
	}

	public boolean containsKey(K k){
		if (k.equals(this.k))
			return true;
		else
			return this.m.containsKey(k);
	}

	public V get(K k){
		if (k.equals(this.k))
			return v;
		else
			return this.m.get(k);
	}

	@SuppressWarnings("unchecked")
	public boolean equals (Object x){
		if (x instanceof FMap){
			FMap<K,V> m2 = (FMap<K, V>) x;
			return this.isSubsetWRTKeys(m2) &&
					m2.isSubsetWRTKeys(this) &&
					this.valuesEqual(this, m2);
		}
		else
			return false;
	}

	public boolean isSubsetWRTKeys(FMap<K, V> that) {
		if(that.containsKey(this.k))
			return this.m.isSubsetWRTKeys(that);
		else
			return false;
	}

	public boolean valuesEqual(FMap<K, V> m1, FMap<K, V> m2 ){
		if(m1.get(this.k) == m2.get(this.k))
			return this.m.valuesEqual(m1, m2);
		else
			return false;
	}
}

