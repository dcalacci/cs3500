
public abstract class FMap<K,V> {
	
	public static <K,V> FMap<K,V>
	emptyMap(){
		return new EmptyMap <K,V>();
	}

	
	
	public FMap<K,V> add(K k, V v){
		return new Add<K,V>(this,k,v);
	}
	
	public boolean  isEmpty(){
		return this.isEmpty();
	}
	
	public int      size(){
		return this.size();
	}
	
	public boolean  containsKey(K k){
		return this.containsKey(k);
	}
	
	public boolean equals(Object x){
		return this.equals(x);
	}

	
	public String toString(){
		return "{...(" + 
				this.size() + " entries)...}";
	}

	
	public int hashCode(){
		return this.size() * 123456 + 654321;
	}
	
	public V get(K k){
		return this.get(k);
	}
	
	public boolean isSubsetWRTkeys(FMap<K,V> s1){
		return this.isSubsetWRTkeys(s1);
	}
	
	public boolean valequal(FMap<K,V> s1, FMap<K,V> s2){
		return this.valequal(s1, s2);
	}



}
class EmptyMap<K,V> extends FMap<K,V>{
	EmptyMap(){}

	public boolean  isEmpty(){
		return true;
	}
	public int size(){
		return 0;	
	}
	public boolean  containsKey(K k){
		return false;	
	}
	public V get(K k){
		throw new RuntimeException ("error");
	}
	
	@SuppressWarnings(value="unchecked")
	public boolean equals(Object x){
		if (x instanceof FMap){
			FMap<K,V> S2 = (FMap<K,V>) x;
			if(S2.isEmpty()){
				return true;
			}
			else return false;

		}
		else return false;
	}
	public boolean isSubsetWRTkeys(FMap<K,V> s1){
		return true;
	}
	public boolean valequal(FMap<K,V> s1, FMap<K,V> s2){
		return true;
	}
}

class Add<K,V> extends FMap<K,V>{
	FMap<K,V> n; 
	K k;
	V v;
	
	Add(FMap<K,V> n, K k, V v){
		this.n = n;
		this.k = k;
		this.v = v;		
	}

	public boolean  isEmpty(){
		return false;
	}

	public int size() {
		if (n.containsKey (k))
			return n.size();
		else{
			return 1 + n.size();
		}
	}
	public boolean  containsKey(K k){
		if (k.equals(this.k)){
			return true;
		}
		else{
			return n.containsKey(k);
		}
	}
	public V get(K k){
		if (k.equals(this.k)){
			return v;
		}
		else{
			return this.n.get(k);
		}

	}
	
	@SuppressWarnings(value="unchecked")
	public boolean equals(Object x){
		if (x instanceof FMap){
			FMap<K,V> S2 = (FMap<K,V>) x;
			return this.isSubsetWRTkeys(S2)&&
					S2.isSubsetWRTkeys(this)&&
					this.valequal(this,S2);}
		else return false;
	}

	public boolean isSubsetWRTkeys(FMap<K,V> s1){
		if(s1.containsKey(this.k))
			return this.n.isSubsetWRTkeys(s1);
		else return false;
	}


	public boolean valequal(FMap<K,V> s1, FMap<K,V> s2){
		if(s1.get(this.k) == s2.get(this.k))
			return this.n.valequal(s1,s2);
		else return false;
	}
}


