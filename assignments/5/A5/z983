




public abstract class FMap<K,V> {

    public static <K,V> FMap<K,V> emptyMap () {
        return new EmptyMap<K,V>();
    }

    public FMap<K,V> add (K k, V v) {
        return new Add<K,V>(this, k, v);
    }

    

    public abstract boolean isEmpty ();
    public abstract int size ();
    public abstract boolean containsKey (K x);
    public abstract V get (K x);

    abstract boolean equalsLoop (FMap<K,V> m2);

    @Override
    public String toString () {
        return "{...(" + size() + " entries)...}";
    }


    

    @Override
    public boolean equals (Object x) {
        if (x instanceof FMap) {
            @SuppressWarnings(value="unchecked")
            FMap<K,V> f2 = (FMap<K,V>) x;
            return this.equalsLoop (f2);
        }
        else return false;
    }

    private static class EmptyMap<K,V> extends FMap<K,V> {

        EmptyMap () { }

        public boolean isEmpty () { return true; }

        public int size () { return 0; }

        public boolean containsKey (K x) { return false; }

        public V get (K x) {
            throw new IllegalArgumentException();
        }

        private static final String errorMsg
            = "illegal argument passed to get";

        boolean equalsLoop (FMap m2) {
            return m2.isEmpty();
        }

        @Override
        public int hashCode () {
	    if (true) return this.size();
            return 1524734285;
        }
    }

    private static class Add<K,V> extends FMap<K,V> {

        private FMap<K,V> m0;
        private K k0;
        private V v0;

        Add (FMap<K,V> m0, K k0, V v0) {
            this.m0 = m0;
            this.k0 = k0;
            this.v0 = v0;
        }

        public FMap<K,V> add (K k, V v) {
            this.m0 = new Add<K,V> (m0, k0, v0); 
            this.k0 = k;
            this.v0 = v;
            return this;
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

        boolean equalsLoop (FMap<K,V> m2) {
            FMap<K,V> m1 = this;
            if (m1.size() != m2.size())
                return false;
            while (! (m1.isEmpty())) {
                Add<K,V> f = (Add<K,V>) m1;
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
	    if (true) return this.size();
            int result = -2138064442;
            FMap<K,V> m1 = this;

            
            
            

            while (m1.size() > 0) {
                Add<K,V> m2 = (Add<K,V>) m1;
                FMap<K,V> m3 = (FMap<K,V>) m2.m0;
                K k2 = m2.k0;

                
                
                
                

                if (! (m3.containsKey(k2))) {
                    int n1 = k2.hashCode();
                    int n2 = get (k2).hashCode();
                    result = result + n1 * n2;
                }
                m1 = m3;
            }
            return result;
        }
    }
}
