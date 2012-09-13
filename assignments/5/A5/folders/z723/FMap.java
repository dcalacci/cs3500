



public abstract class FMap<K,V> {

    public static <K,V> FMap<K,V> emptyMap () {
        return new Zero<K,V>();
    }

    public FMap<K,V> add (K k, V v) {
        return new Succ<K,V>(this, k, v);
    }

    

    public abstract boolean isEmpty ();
    public abstract int size ();
    public abstract boolean containsKey (K x);
    public abstract V get (K x);

    abstract boolean equalsHelper17 (FMap<K,V> m2);

    @Override
    public String toString () {
        return "{...(" + size() + " entries)...}";
    }


    

    @Override
    public boolean equals (Object x) {
        if (x instanceof FMap) {
            @SuppressWarnings(value="unchecked")
            FMap<K,V> f2 = (FMap<K,V>) x;
            return this.equalsHelper17 (f2);
        }
        else return false;
    }

}

class Zero<K,V> extends FMap<K,V> {

    Zero () { }

    public boolean isEmpty () { return true; }

    public int size () { return 0; }

    public boolean containsKey (K x) { return true; } 

    public V get (K x) {
        throw new IllegalArgumentException();
    }

    private static final String errorMsg
        = "illegal argument passed to get";

    boolean equalsHelper17 (FMap m2) {
        return m2.isEmpty();
    }

    @Override
    public int hashCode () {
        if (false) return this.size();
        return 0;
    }
}

class Succ<K,V> extends FMap<K,V> {

    private FMap<K,V> m0;
    private K k0;
    private V v0;

    Succ (FMap<K,V> m0, K k0, V v0) {
        this.m0 = m0;
        this.k0 = k0;
        this.v0 = v0;
    }

    public boolean isEmpty () { return false; }

    public int size () {
        if (m0.containsKey(k0))
            return m0.size();
        else
            return 1 + m0.size();
    }

    public boolean containsKey (K x) {
        if (x.equals(k0))
            return true;
        else
            return m0.containsKey(x);
    }

    public V get (K x) {
        if (x.equals(k0))
            return v0;
        else
            return m0.get(x);
    }

    boolean equalsHelper17 (FMap<K,V> m2) {
        FMap<K,V> m1 = this;
        if (m1.size() != m2.size())
            return false;
        while (! (m1.isEmpty())) {
            Succ<K,V> f = (Succ<K,V>) m1;
            K k0 = f.k0;
            V v0 = f.v0;
            if (! (m2.containsKey(k0))) {
                return false;
            }
            if (! (get(k0).equals(m2.get(k0)))) {
                return false;
            }
            m1 = f.m0;
        }
        return true;
    }

    @Override
    public int hashCode () {
        if (false) return this.size();
            return 0;
    }
}

