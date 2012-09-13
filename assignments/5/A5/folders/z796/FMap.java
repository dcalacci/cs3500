

    
    




public abstract class FMap<K,V> {


    public static <K,V> FMap<K,V> emptyMap() {
	return new EmptyMap<K,V>();
    }

    public FMap<K,V> add(K k, V v){
	return new AddMap<K,V>(this, k, v);
    }


    
    public abstract boolean isEmpty();

    
    public abstract int size();
    
     
    public abstract boolean containsKey(K k);

    
    public abstract V get(K k);

    
    public abstract boolean isEqualTo(FMap<K,V> m2);



    
    public String toString(){
	return "{...(" + this.size() + " entries)...}";
    }
    
    
    @SuppressWarnings(value="unchecked")
    public boolean equals(Object o){
      	if (o == null)
	  return false;
	else if (o instanceof FMap){
	    FMap<K,V> m2 = (FMap<K,V>) o;
	    return this.isEqualTo(m2)
		&& m2.isEqualTo(this);	
	}
	else 
	    return false;
    }

    
    public int hashCode(){
	return 0;
    }
}


class EmptyMap<K,V> extends FMap<K,V>{

    
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
	throw new RuntimeException("No key 'k' exists in this FMap");
    }

    public boolean isEqualTo(FMap<K,V> m2){
	return true;
    }
}


class AddMap<K,V> extends FMap<K,V>{
    FMap<K,V> rest;
    K key;
    V val;

    
    AddMap(FMap<K,V> rest, K k, V v){
	this.rest = rest;
	this.key = k;
	this.val = v;
    }

     public boolean isEmpty(){
	return false;
    }

    public int size(){
	if (this.rest.containsKey(this.key))
	    return this.rest.size();
	else
	    return 1 + this.rest.size();
    }

    public boolean containsKey(K k){
	if (k.equals(this.key))
	    return true;
	else if(this.rest.containsKey(k))
	    return true;
	else
	    return false;
    }

    public V get(K k){
	if (this.key.equals(k))
	    return this.val;
	else
	    return this.rest.get(k);
    }

    public boolean isEqualTo(FMap<K,V> m2){
	if (! (m2 instanceof AddMap))
	    return false;
	else{
	    AddMap<K,V> m3 = (AddMap<K,V>) m2;
	    if (m3.containsKey(this.key))
		return this.rest.isEqualTo(m3)
		    && this.val.equals(m3.get(this.key));
	    else
		return false;
	}
    }
}
