








import java.util.*;

public abstract class FMap<K,V> implements Iterable<K> {

    public static <K,V> FMap<K,V> emptyMap () {
        return new EmptyMap<K,V>();
    }

    public static <K,V> FMap<K,V> emptyMap (Comparator<? super K> c) {
        return new EmptyTree<K,V>(c);
    }

    

    public int height () {
        return size ();
    }

    

    public abstract FMap<K,V> add (K k, V v);
    public abstract boolean isEmpty ();
    public abstract int size ();
    public abstract boolean containsKey (K x);
    public abstract V get (K x);

    

    abstract boolean equalsLoop (FMap<K,V> m2);

    
    

    abstract void addKeys (ArrayList<K> keys);

    @Override
    public String toString () {
        return "{...(" + size() + " entries)...}";
    }

    

    @Override
    public boolean equals (Object x) {
        if (x instanceof FMap) {
            @SuppressWarnings(value="unchecked")
            FMap<K,V> f2 = (FMap<K,V>) x;
            return this.equalsLoop (f2)
                && f2.equalsLoop (this);
        }
        else return false;
    }

    public Iterator<K> iterator () {
        ArrayList<K> a = new ArrayList<K>(this.size());
        this.addKeys (a);
        return new KeyIterator<K>(a);
    }

    private static abstract class AssociationList<K,V> extends FMap<K,V> {

        public FMap<K,V> add (K k, V v) {
            return new Add<K,V>(this, k, v);
        }

    }

    private static class EmptyMap<K,V> extends AssociationList<K,V> {

        Comparator<? super K> c;    

        EmptyMap () { }

        EmptyMap (Comparator<? super K> c) {
            this.c = c;
        }

        

        private static class UsualIntegerComparator
            implements Comparator<Integer> {

            public int compare (Integer m, Integer n) {
                return m.compareTo(n);
            }

        }

        Comparator<Integer> usualIntegerComparator
            = new UsualIntegerComparator();

        @Override
        public FMap<K,V> add (K k, V v) {
            if ((c == null) && (k instanceof Integer)) {
                @SuppressWarnings(value="unchecked")
                FMap<K,V> m0 = (FMap<K,V>) emptyMap (usualIntegerComparator);
                
                return m0.add (k, v);
            }
            return new Add<K,V>(this, k, v);
        }

        public boolean isEmpty () { return true; }

        public int size () { return 0; }

        public boolean containsKey (K x) { return false; }

        public V get (K x) {
            throw new IllegalArgumentException();
        }

        private static final String errorMsg
            = "illegal argument passed to get";

        boolean equalsLoop (FMap m2) {
            return true;
        }

        @Override
        public int hashCode () {
            if (true) return this.size();
            return 1524734285;
        }

        void addKeys (ArrayList<K> keys) {
            if (c != null)
                Collections.sort (keys, c);
        }
    }

    private static class Add<K,V> extends AssociationList<K,V> {

        private FMap<K,V> m0;
        private K k0;
        private V v0;

        Add (FMap<K,V> m0, K k0, V v0) {
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

        boolean equalsLoop (FMap<K,V> m2) {
            if (! (m2.containsKey(k0))) {
                return false;
            }
            else return m0.equalsLoop (m2);
        }

        @Override
        public int hashCode () {
            
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

        void addKeys (ArrayList<K> keys) {
            if (! (keys.contains(k0)))
                keys.add(k0);
            m0.addKeys (keys);
        }
    }

    

    private static abstract class FTree<K,V> extends FMap<K,V> {

        

        Comparator<? super K> c;

        

        boolean color;

        public FMap<K,V> add (K k, V v) {
            return this.insert (k, v).makeBlack();
        }

        

        abstract FTree<K,V> insert (K k, V v);

        

        FTree<K,V> makeBlack () {
            return new Node<K,V> (key(), value(), left(), right(), BLACK);
        }

        
        
        
        
        

        FTree<K,V> balance (K k, V v, FTree<K,V> t1, FTree<K,V> t2,
                            boolean color) {
            if (color == RED) {
                return new Node<K,V> (k, v, t1, t2, color);
            }

            

            else if (color == BLACK
                     && (! t1.isEmpty())
                     && (isRed (t1))
                     && (! t1.left().isEmpty())
                     && (isRed (t1.left()))) {

                return new Node<K,V> (t1.key(), t1.value(),
                                      t1.left().makeBlack(),
                                      new Node<K,V> (k, v,
                                                     t1.right(),
                                                     t2,
                                                     BLACK),
                                      RED);
            }

            

            else if (color == BLACK
                     && (! t1.isEmpty())
                     && (isRed (t1))
                     && (! t1.right().isEmpty())
                     && (isRed (t1.right()))) {

                return new Node<K,V> (t1.right().key(), t1.right().value(),
                                      new Node<K,V> (t1.key(), t1.value(),
                                                     t1.left(),
                                                     t1.right().left(),
                                                     BLACK),
                                      new Node<K,V> (k, v,
                                                     t1.right().right(),
                                                     t2,
                                                     BLACK),
                                      RED);
            }

            

            else if (color == BLACK
                     && (! t2.isEmpty())
                     && (isRed (t2))
                     && (! t2.right().isEmpty())
                     && (isRed (t2.right()))) {

                return new Node<K,V> (t2.key(), t2.value(),
                                      new Node<K,V> (k, v,
                                                     t1,
                                                     t2.left(),
                                                     BLACK),
                                      t2.right().makeBlack(),
                                      RED);
            }

            

            else if (color == BLACK
                     && (! t2.isEmpty())
                     && (isRed (t2))
                     && (! t2.left().isEmpty())
                     && (isRed (t2.left()))) {

                return new Node<K,V> (t2.left().key(), t2.left().value(),
                                      new Node<K,V> (k, v,
                                                     t1,
                                                     t2.left().left(),
                                                     BLACK),
                                      new Node<K,V> (t2.key(), t2.value(),
                                                     t2.left().right(),
                                                     t2.right(),
                                                     BLACK),
                                      RED);
            }
            else {
                return new Node<K,V> (k, v, t1, t2, color);
            }
        }

        

        boolean color () { return color; }

        FTree<K,V> left ()  { blowup("left");  return null; }
        FTree<K,V> right () { blowup("right"); return null; }
        K key ()            { blowup("key");   return null; }
        V value ()          { blowup("value"); return null; }

        private void blowup (String name) {
            throw new RuntimeException ("bug detected by " + name);
        }

        

        static final boolean RED = false;
        static final boolean BLACK = true;

        boolean isRed (FTree<K,V> t) {
            return t.color == RED;
        }

        boolean isBlack (FTree<K,V> t) {
            return t.color == BLACK;
        }

        @Override
            public String toString () {
            if (false) {
                if (isEmpty())
                    return "{}";
                else
                    return "{ " + left().toString()
                        + "( " + key() + " )"
                        + right().toString() + " }";
            }
            return "{...(" + size() + " entries)...}";
        }
    }

    private static class EmptyTree<K,V> extends FTree<K,V> {

        EmptyTree (Comparator<? super K> c) {
            this.c = c;
            this.color = BLACK;
        }

        public boolean isEmpty () { return true; }

        public int size () { return 0; }

        public boolean containsKey (K x) { return false; }

        public V get (K x) {
            throw new IllegalArgumentException();
        }

        FTree<K,V> insert (K k, V v) {
            return new Node<K,V> (k, v, this, this, RED);
        }

        private static final String errorMsg
            = "illegal argument passed to get";

        boolean equalsLoop (FMap m2) {
            return true;
        }

        @Override
        public int hashCode () {
            if (true) return this.size();
            return 1524734285;
        }

        void addKeys (ArrayList<K> keys) { }
    }

    private static class Node<K,V> extends FTree<K,V> {

        private FTree<K,V> left;
        private FTree<K,V> right;
        private K k0;
        private V v0;
        private int theSize;

        

        Node (K k0, V v0, FTree<K,V> left, FTree<K,V> right, boolean color) {
            this.k0 = k0;
            this.v0 = v0;
            this.left = left;
            this.right = right;
            this.theSize = 1 + left.size() + right.size();
            this.c = left.c;
            this.color = color;
        }

        FTree<K,V> left ()  { return left; }
        FTree<K,V> right () { return right; }
        K key ()            { return k0; }
        V value ()          { return v0; }

        

        public int height () {
            return 1 + Math.max (left.height(), right.height());
        }

        public boolean isEmpty () { return false; }

        public int size () {
            if (true)
                return theSize;
            return 1 + left.size() + right.size();
        }

        public boolean containsKey (K x) {
            int direction = c.compare (x, k0);
            if (direction < 0)
                return left.containsKey(x);
            else if (direction == 0)
                return true;
            else
                return right.containsKey(x);
        }

        public V get (K x) {
            int direction = c.compare (x, k0);
            if (direction < 0)
                return left.get(x);
            else if (direction == 0)
                return v0;
            else
                return right.get(x);
        }

        FTree<K,V> insert (K k, V v) {
            int direction = c.compare (k, k0);
            if (direction < 0)
                return balance (k0, v0, left.insert (k, v), right, color);
            else if (direction == 0)
                return new Node<K,V> (k0, v, left, right, color);
            else
                return balance (k0, v0, left, right.insert (k, v), color);
        }

        boolean equalsLoop (FMap<K,V> m2) {
            return left.equalsLoop(m2)
                && m2.containsKey(k0)
                && v0.equals(m2.get(k0))
                && right.equalsLoop(m2);
        }

        @Override
        public int hashCode () {
            
            int result = -2138064442;
            FMap<K,V> m1 = this;

            
            
            

            for (K k : this) {
                int n1 = k.hashCode();
                int n2 = get (k).hashCode();
                result = result + n1 * n2;
            }

            return result;
        }

        void addKeys (ArrayList<K> keys) {
            left.addKeys (keys);
            keys.add(k0);
            right.addKeys (keys);
        }
    }

    private static class KeyIterator<K> implements Iterator<K> {

        Iterator<K> it;

        KeyIterator (ArrayList<K> keys) {
            this.it = keys.iterator();
        }

        public boolean hasNext () {
            return it.hasNext();
        }

        public K next () {
            K result = it.next();
            if (true) return result;
            return it.next();
        }

        public void remove () {
            throw new UnsupportedOperationException();
        }
    }
}
