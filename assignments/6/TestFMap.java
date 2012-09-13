// Basic test program for assignment 6.

import java.util.Random;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class TestFMap {

    // Runs the tests.
               
    public static void main(String args[]) {
        TestFMap test = new TestFMap();
        test.creation();
        test.accessors();
        test.usual();
        test.accessors();            // repeated to test for side effects
        test.usual();                // repeated to test for side effects
        test.iterators(0);
        test.iterators(1);
        // test.bad();
        test.summarize();
    }

    // Prints a summary of the tests.

    private void summarize () {
        System.out.println();
        System.out.println (totalErrors + " errors found in " +
                            totalTests + " tests.");
    }

    public TestFMap () { }

    // String objects to serve as values.

    private final String alice = "Alice";
    private final String bob = "Bob";
    private final String carol = "Carol";
    private final String dave = "Dave";

    // Integer objects to serve as keys.

    private final Integer one = 1;
    private final Integer two = 2;
    private final Integer three = 3;
    private final Integer four = 4;
    private final Integer five = 5;
    private final Integer six = 6;

    // FMap<Integer,String> objects to be created and then tested.

    private FMap<Integer,String> f0;// [ ]
    private FMap<Integer,String> f1;// [ (1 Alice) ]
    private FMap<Integer,String> f2;// [ (2 Bob) (1 Alice) ]
    private FMap<Integer,String> f3;// [ (3 Carol) (2 Bob) (1 Alice) ]
    private FMap<Integer,String> f4;// [ (4 Dave) (3 Carol) (2 Bob) (1 Alice) ]
    private FMap<Integer,String> f5;// [ (1 Carol) (2 Bob) (1 Alice) ]
    private FMap<Integer,String> f6;// [ (3 Carol) (4 Dave) (2 Bob) (1 Alice) ]
    private FMap<Integer,String> f7;// [ (1 Alice) (2 Bob) (2 dave) (1 dave) ]

    // Creates some FMap<Integer,String> objects.

    private void creation () {
        try {
            f0 = FMap.emptyMap();
            f1 = f0.add(one, alice);
            f2 = f1.add(two, bob);
            f3 = f2.add(three, carol);
            f4 = f3.add(four, dave);
            f5 = f2.add(one, carol);
            f6 = f2.add(four, dave).add(three, carol);

            f7 = f0.add(one, dave);
            f7 = f7.add(two, dave);
            f7 = f7.add(two, bob);
            f7 = f7.add(one, alice);
        }
        catch (Exception e) {
            assertTrue ("creation", false);
        }
    }

    // Tests the accessors.

    private void accessors () {
        try {
            assertTrue ("empty", f0.isEmpty());
            assertFalse ("nonempty", f1.isEmpty());
            assertFalse ("nonempty", f3.isEmpty());

            assertTrue ("f0.size()", f0.size() == 0);
            assertTrue ("f1.size()", f1.size() == 1);
            assertTrue ("f2.size()", f2.size() == 2);
            assertTrue ("f3.size()", f3.size() == 3);
            assertTrue ("f4.size()", f4.size() == 4);
            assertTrue ("f5.size()", f5.size() == 2);
            assertTrue ("f7.size()", f7.size() == 2);

            assertFalse ("containsKey01", f0.containsKey(one));
            assertFalse ("containsKey04", f0.containsKey(four));
            assertTrue  ("containsKey11", f1.containsKey(one));
            assertFalse ("containsKey14", f1.containsKey(four));
            assertTrue  ("containsKey21", f2.containsKey(one));
            assertFalse ("containsKey24", f2.containsKey(four));
            assertTrue  ("containsKey31", f3.containsKey(one));
            assertFalse ("containsKey34", f3.containsKey(four));
            assertTrue  ("containsKey41", f4.containsKey(one));
            assertTrue  ("containsKey44", f4.containsKey(four));
            assertTrue  ("containsKey51", f5.containsKey(one));
            assertFalse ("containsKey54", f5.containsKey(four));

            assertTrue ("get11", f1.get(one).equals(alice));
            assertTrue ("get21", f2.get(one).equals(alice));
            assertTrue ("get22", f2.get(two).equals(bob));
            assertTrue ("get31", f3.get(one).equals(alice));
            assertTrue ("get32", f3.get(two).equals(bob));
            assertTrue ("get33", f3.get(three).equals(carol));
            assertTrue ("get41", f4.get(one).equals(alice));
            assertTrue ("get42", f4.get(two).equals(bob));
            assertTrue ("get43", f4.get(three).equals(carol));
            assertTrue ("get44", f4.get(four).equals(dave));
            assertTrue ("get51", f5.get(one).equals(carol));
            assertTrue ("get52", f5.get(two).equals(bob));
            assertTrue ("get71", f7.get(one).equals(alice));
            assertTrue ("get72", f7.get(two).equals(bob));
        }
        catch (Exception e) {
            assertTrue ("accessors", false);
        }
    }

    // Tests the usual overloaded methods.

    private void usual () {
        try {
            assertTrue ("toString0",
                        f0.toString().equals("{...(0 entries)...}"));
            assertTrue ("toString1",
                        f1.toString().equals("{...(1 entries)...}"));
            assertTrue ("toString7",
                        f7.toString().equals("{...(2 entries)...}"));

            assertTrue ("equals00", f0.equals(f0));
            assertTrue ("equals33", f3.equals(f3));
            assertTrue ("equals55", f5.equals(f5));
            assertTrue ("equals46", f4.equals(f6));
            assertTrue ("equals64", f6.equals(f4));
            assertTrue ("equals27", f2.equals(f7));
            assertTrue ("equals72", f7.equals(f2));

            assertFalse ("equals01", f0.equals(f1));
            assertFalse ("equals02", f0.equals(f2));
            assertFalse ("equals10", f1.equals(f0));
            assertFalse ("equals12", f1.equals(f2));
            assertFalse ("equals21", f2.equals(f1));
            assertFalse ("equals23", f2.equals(f3));
            assertFalse ("equals35", f3.equals(f5));

            assertFalse ("equals0string", f0.equals("just a string"));
            assertFalse ("equals4string", f4.equals("just a string"));

            assertFalse ("equals0null", f0.equals(null));
            assertFalse ("equals1null", f1.equals(null));

            assertTrue ("hashCode00", f0.hashCode() == f0.hashCode());
            assertTrue ("hashCode44", f4.hashCode() == f4.hashCode());
            assertTrue ("hashCode46", f4.hashCode() == f6.hashCode());
            assertTrue ("hashCode27", f2.hashCode() == f7.hashCode());

            // probabilisticTests();
        }
        catch (Exception e) {
            assertTrue ("usual", false);
        }
    }

    // Tests the 0-argument or 1-argument iterator method,
    // as determined by nargs.

    private void iterators (int nargs) {
        if (nargs == 0)
            iterators();
        else
            iteratorsSorted();
    }

    private void iterators () {
        try {
            FMap<Integer,String> f;
            FMap<Integer,String> m;
            Iterator<Integer> it;
            int count;

            f = f0;
            m = f0;
            it = f.iterator();
            count = 0;
            while (it.hasNext()) {
                Integer k = it.next();
                m = m.add(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator001", f0.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count001", f.size() == count);

            f = f0;
            m = f0;
            count = 0;
            for (Integer k : f) {
                m = m.add(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator001", f0.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count001", f.size() == count);

            f = f5;
            m = f0;
            it = f.iterator();
            count = 0;
            while (it.hasNext()) {
                Integer k = it.next();
                m = m.add(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator551", f5.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count551", f.size() == count);

            f = f5;
            m = f0;
            count = 0;
            for (Integer k : f) {
                m = m.add(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator551", f5.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count551", f.size() == count);

            f = f6;
            m = f0;
            it = f.iterator();
            count = 0;
            while (it.hasNext()) {
                Integer k = it.next();
                m = m.add(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator461", f4.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count461", f.size() == count);

            f = f6;
            m = f0;
            count = 0;
            for (Integer k : f) {
                m = m.add(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator461", f4.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count461", f.size() == count);

            f = f4;
            m = f0;
            it = f.iterator();
            count = 0;
            while (it.hasNext()) {
                Integer k = it.next();
                m = m.add(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator641", f6.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count641", f.size() == count);

            f = f4;
            m = f0;
            count = 0;
            for (Integer k : f) {
                m = m.add(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator641", f6.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count641", f.size() == count);

            f = f7;
            m = f0;
            it = f.iterator();
            count = 0;
            while (it.hasNext()) {
                Integer k = it.next();
                m = m.add(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator271", f2.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count271", f.size() == count);

            f = f7;
            m = f0;
            count = 0;
            for (Integer k : f) {
                m = m.add(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator271", f2.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count271", f.size() == count);

            f = f2;
            m = f0;
            it = f.iterator();
            count = 0;
            while (it.hasNext()) {
                Integer k = it.next();
                m = m.add(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator721", f7.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count721", f.size() == count);

            f = f2;
            m = f0;
            count = 0;
            for (Integer k : f) {
                m = m.add(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator721", f7.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count721", f.size() == count);

            // Make sure the next() method throws the right exception.

            try {
                it.next();
                assertTrue ("next (exception)", false);
            }
            catch (NoSuchElementException e) {
                assertTrue ("next (exception)", true);
            }
            catch (Exception e) {
                assertTrue ("next (exception)", false);
            }

            // Make sure the remove() method throws the right exception.

            try {
                it = f.iterator();
                it.remove();
                assertTrue ("remove", false);
            }
            catch (UnsupportedOperationException e) {
                assertTrue ("remove", true);
            }
            catch (Exception e) {
                assertTrue ("remove", false);
            }
        }
        catch (Exception e) {
            System.out.println("Exception thrown during iterator tests:");
            System.out.println(e);
            assertTrue ("iterators", false);
        }
    }

    // A comparator for Integer values.

    private static class UsualIntegerComparator
        implements Comparator<Integer> {

        public int compare (Integer m, Integer n) {
            return m.compareTo(n);
        }

    }

    Comparator<Integer> usualIntegerComparator
        = new UsualIntegerComparator();

    // Another comparator for Integer values.

    private static class ReverseIntegerComparator
        implements Comparator<Integer> {

        public int compare (Integer m, Integer n) {
            return n.compareTo(m);
        }

    }

    Comparator<Integer> reverseIntegerComparator
        = new ReverseIntegerComparator();

    private void iteratorsSorted () {
        try {
            FMap<Integer,String> f;
            FMap<Integer,String> m;
            Iterator<Integer> it;
            int count;

            f = f0;
            m = f0;
            it = f.iterator(usualIntegerComparator);
            count = 0;
            while (it.hasNext()) {
                Integer k = it.next();
                m = m.add(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator001", f0.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count001", f.size() == count);

            f = f5;
            m = f0;
            it = f.iterator(reverseIntegerComparator);
            count = 0;
            while (it.hasNext()) {
                Integer k = it.next();
                m = m.add(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator551", f5.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count551", f.size() == count);

            f = f6;
            m = f0;
            it = f.iterator(usualIntegerComparator);
            count = 0;
            while (it.hasNext()) {
                Integer k = it.next();
                m = m.add(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator461", f4.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count461", f.size() == count);

            f = f4;
            m = f0;
            it = f.iterator(reverseIntegerComparator);
            count = 0;
            while (it.hasNext()) {
                Integer k = it.next();
                m = m.add(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator641", f6.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count641", f.size() == count);

            f = f7;
            m = f0;
            it = f.iterator(usualIntegerComparator);
            count = 0;
            while (it.hasNext()) {
                Integer k = it.next();
                m = m.add(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator271", f2.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count271", f.size() == count);

            f = f2;
            m = f0;
            it = f.iterator(reverseIntegerComparator);
            count = 0;
            while (it.hasNext()) {
                Integer k = it.next();
                m = m.add(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator721", f7.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count721", f.size() == count);

            // The keys must be generated in ascending order,
            // as determined by the comparator.

            it = f6.iterator(reverseIntegerComparator);
            Integer previous = it.next();
            while (it.hasNext()) {
                Integer k = it.next();
                int comparison
                    = reverseIntegerComparator.compare(previous, k);
                assertTrue("ascending", comparison < 0);
                previous = k;
            }

            it = f6.iterator(usualIntegerComparator);
            previous = it.next();
            while (it.hasNext()) {
                Integer k = it.next();
                int comparison
                    = usualIntegerComparator.compare(previous, k);
                assertTrue("ascending2", comparison < 0);
                previous = k;
            }

            // Make sure the next() method throws the right exception.

            try {
                it.next();
                assertTrue ("next (exception)", false);
            }
            catch (NoSuchElementException e) {
                assertTrue ("next (exception)", true);
            }
            catch (Exception e) {
                assertTrue ("next (exception)", false);
            }

            // Make sure the remove() method throws the right exception.

            try {
                it = f.iterator(reverseIntegerComparator);
                it.remove();
                assertTrue ("remove", false);
            }
            catch (UnsupportedOperationException e) {
                assertTrue ("remove", true);
            }
            catch (Exception e) {
                assertTrue ("remove", false);
            }
        }
        catch (Exception e) {
            System.out.println("Exception thrown during iterator tests:");
            System.out.println(e);
            assertTrue ("iterators", false);
        }
    }

    private int totalTests = 0;       // tests run so far
    private int totalErrors = 0;      // errors so far

    // For anonymous tests.  Deprecated.

    private void assertTrue (boolean result) {
      assertTrue ("anonymous", result);
    }

    // Prints failure report if the result is not true.

    private void assertTrue (String name, boolean result) {
        if (! result) {
            System.out.println ();
            System.out.println ("***** Test failed ***** "
                                + name + ": " +totalTests);
            totalErrors = totalErrors + 1;
        }
        totalTests = totalTests + 1;
    }

    // For anonymous tests.  Deprecated.

    private void assertFalse (boolean result) {
        assertTrue (! result);
    }

    // Prints failure report if the result is not false.

    private void assertFalse (String name, boolean result) {
        assertTrue (name, ! result);
    }

}
