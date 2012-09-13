


public abstract class FMap<K,V> {
	
	
	public static <K,V> FMap<K,V> emptyMap() {
		return new EmptyMap<K,V>();
	}
	
	
	public FMap<K,V> add(K k, V v) {
		return new Add<K,V>(this, k, v);
	}
	
	
	public abstract boolean isEmpty();
	
	public abstract int size();
	
	public abstract boolean containsKey(K k);
	
	public abstract V get(K k);
	
	abstract boolean subsetK(FMap<K,V> f);
	
	abstract boolean sameVals(FMap<K,V> f0, FMap<K,V> f1);

	
	@Override
	public abstract int hashCode();
	@Override
	public String toString() {
		return "{...(" + this.size() + " entries)...}";
	}
	@Override
	public boolean equals(Object o) {
		if (o instanceof FMap) {
			FMap<K,V> f = (FMap<K, V>) o;
			
			if (this.subsetK(f) && f.subsetK(this) &&
					
					this.sameVals(this, f))
				return true;
			else 
				return false;
		}
		else
			return false;
	}
	
	
	
	private static class EmptyMap<K,V> extends FMap<K,V>{
		
		
		EmptyMap(){}
		
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
			throw new RuntimeException("No value for " +
					"the given key found in this FMap.");
		}
		public int hashCode(){
			return 0;
		}
		boolean subsetK(FMap<K,V> f){
			return true;
		}
		boolean sameVals(FMap<K,V> f0, FMap<K,V> f1) { 
			return true;
		}
	}
	
	
	private static class Add<K,V> extends FMap<K,V>{
		
		
		FMap<K,V> m;
		
		K k;
		
		V v;
		
		
		Add(FMap<K,V> m, K k, V v){
			this.m = m;
			this.k = k;
			this.v = v;
		}
		
		public boolean isEmpty(){
			return false;
		}
		public int size(){
			if (this.m.containsKey(this.k)) 
				return this.m.size();
			else
				return 1 + this.m.size();
		}
		public boolean containsKey(K k){
			if (k.equals(this.k)) 
				return true;
			else
				return this.m.containsKey(k);
		}
		public V get(K k){
			if (k.equals(this.k))
				return this.v;
			else
				return this.m.get(k);
		}
		public int hashCode(){
			return (this.k.hashCode() * this.v.hashCode())
					+ this.m.hashCode();
		}
		boolean subsetK(FMap<K,V> f){
			if (f.containsKey(this.k))
				return this.m.subsetK(f);
			else
				return false;
		}
		boolean sameVals(FMap<K,V> f0, FMap<K,V> f1) {
			if (f1.isEmpty()) 
				return false;
			else {
				
				
				
				
				return f0.get(this.k).equals(f1.get(this.k)) &&
						this.m.sameVals(f0, f1);
			}
		}
	}
}
