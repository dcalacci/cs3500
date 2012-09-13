// Basic test program for assignment 2

public class TestFSet {

    // Runs the tests.
               
    public static void main(String args[]) {
        TestFSet test = new TestFSet();

        test.creation();
        test.accessors(0);
        test.usual();
        test.accessors(0);    // test twice to detect side effects
        test.usual();

        test.summarize();
    }

    // Prints a summary of the tests.

    private void summarize () {
        System.out.println();
        System.out.println (totalErrors + " errors found in " +
                            totalTests + " tests.");
    }

    public TestFSet () { }

    // String objects to serve as elements.

    private final String one = "one";
    private final String two = "2";
    private final String three = "3";
    private final String four = "four";
    private final String five = "five";
    private final String six = "six";

    // FSet<Integer> objects to be created and then tested.

    private FSet f0;    // { }
    private FSet f1;    // { 1 }
    private FSet f2;    // { 2, 1 }
    private FSet f3;    // { 3, 2, 1 }
    private FSet f4;    // { 4, 3, 2, 1 }
    private FSet f5;    // { 1, 2, 1 }
    private FSet f6;    // { 3, 4, 2, 1 }
    private FSet f7;    // { 1, 2, 2, 1 }

    private FSet s1;    // { 1 }
    private FSet s2;    // { 2 }
    private FSet s3;    // { 3 }
    private FSet s4;    // { 4 }

    private FSet s12;    // { 1, 2 }
    private FSet s13;    // { 1, 3 }
    private FSet s14;    // { 1, 4 }
    private FSet s23;    // { 2, 3 }
    private FSet s34;    // { 3, 4 }

    private FSet s123;   // { 1, 2, 3 }
    private FSet s124;   // { 1, 2, 4 }
    private FSet s134;   // { 1, 3, 4 }
    private FSet s234;   // { 2, 3, 4 }

    // Creates some FSet objects.

    private void creation () {
        try {
            f0 = FSet.emptySet();
            f1 = f0.adjoin (one);
            f2 = f1.adjoin (two);
            f3 = f2.adjoin (three);
            f4 = f3.adjoin (four);
            f5 = f2.adjoin (one);
            f6 = f2.adjoin (four).adjoin (three);

            f7 = f0.adjoin (one);
            f7 = f7.adjoin (two);
            f7 = f7.adjoin (two);
            f7 = f7.adjoin (one);

            s1 = f0.adjoin (one);
            s2 = f0.adjoin (two);
            s3 = f0.adjoin (three);
            s4 = f0.adjoin (four);

            s12 = f1.adjoin (new String(two));
            s13 = f1.adjoin (new String(three));
            s14 = f1.adjoin (new String(four));
            s23 = s2.adjoin (new String(three));
            s34 = s3.adjoin (new String(four));

            s123 = s12.add (three);
            s124 = s12.add (four);
            s134 = s13.add (four);
            s234 = s23.add (four);

            s134 = s134.add (four);
            s234 = s234.add (four);

        }
        catch (Exception e) {
            System.out.println("Exception thrown during creation tests:");
            System.out.println(e);
            assertTrue ("creation", false);
        }
    }

    // Tests the accessors.

    private void accessors (int nargs) {
        try {
            assertTrue  ("empty",    f0.isEmpty());
            assertFalse ("nonempty", f1.isEmpty());
            assertFalse ("nonempty", f3.isEmpty());

            assertTrue ("f0.size()", f0.size() == 0);
            assertTrue ("f1.size()", f1.size() == 1);
            assertTrue ("f2.size()", f2.size() == 2);
            assertTrue ("f3.size()", f3.size() == 3);
            assertTrue ("f4.size()", f4.size() == 4);
            assertTrue ("f5.size()", f5.size() == 2);
            assertTrue ("f7.size()", f7.size() == 2);

            assertFalse ("contains01", f0.contains (one));
            assertFalse ("contains04", f0.contains (four));
            assertTrue  ("contains11", f1.contains (one));
            assertTrue  ("contains11new", f1.contains (new String(one)));
            assertFalse ("contains14", f1.contains (four));
            assertTrue  ("contains21", f2.contains (one));
            assertFalse ("contains24", f2.contains (four));
            assertTrue  ("contains31", f3.contains (one));
            assertFalse ("contains34", f3.contains (four));
            assertTrue  ("contains41", f4.contains (one));
            assertTrue  ("contains44", f4.contains (four));
            assertTrue  ("contains51", f5.contains (one));
            assertFalse ("contains54", f5.contains (four));

            assertTrue  ("isSubset00", f0.isSubset (f0));
            assertTrue  ("isSubset01", f0.isSubset (f1));
            assertFalse ("isSubset10", f1.isSubset (f0));
            assertTrue  ("isSubset02", f0.isSubset (f2));
            assertFalse ("isSubset20", f2.isSubset (f0));
            assertTrue  ("isSubset24", f2.isSubset (f4));
            assertFalse ("isSubset42", f4.isSubset (f2));
            assertTrue  ("isSubset27", f2.isSubset (f7));
            assertTrue  ("isSubset72", f7.isSubset (f2));
            assertTrue  ("isSubset 13 3", s13.isSubset (f3));
            assertFalse ("isSubset 3 13", f3.isSubset (s13));
            assertTrue  ("isSubset 123 3", s123.isSubset (f3));
            assertTrue  ("isSubset 3 123", f3.isSubset (s123));

            assertTrue ("without f0 1", f0.without (one).isEmpty());
            assertTrue ("without f1 1", f1.without (one).isEmpty());
            assertTrue ("without f1 2", f1.without (two).equals(s1));
            assertTrue ("without f2 1", f2.without (one).equals(s2));
            assertTrue ("without f2 2", f2.without (two).equals(s1));
            assertTrue ("without f2 3", f2.without (three).equals(s12));
            assertTrue ("without f2 12",
                        f2.without (one).without (two).isEmpty());
            assertTrue ("without f2 21",
                        f2.without (two).without (one).isEmpty());
            assertTrue ("without f6 1", f6.without (one).equals(s234));
            assertTrue ("without f6 2", f6.without (two).equals(s134));
            assertTrue ("without f6 3", f6.without (three).equals(s124));
            assertTrue ("without f6 4", f6.without (four).equals(s123));

            assertTrue ("union f0 f0", f0.union (f0).isEmpty());
            assertTrue ("union f0 s1", f0.union (s1).equals(s1));
            assertTrue ("union s1 f0", s1.union (f0).equals(s1));
            assertTrue ("union s1 s1", s1.union (s1).equals(s1));
            assertTrue ("union s1 s2", s1.union (s2).equals(s12));
            assertTrue ("union s2 s1", s2.union (s1).equals(s12));
            assertTrue ("union s2 s2", s2.union (s2).equals(s2));
            assertTrue ("union s12 s3", s12.union (s3).equals(s123));
            assertTrue ("union s3 s12", s3.union (s12).equals(s123));
            assertTrue ("union s13 s2", s13.union (s2).equals(s123));
            assertTrue ("union s2 s13", s2.union (s13).equals(s123));
            assertTrue ("union s123 s234",
                        s123.union (s234).equals(f4));
            assertTrue ("union f4 f6",
                        f4.union (f6).equals(f4));
            assertTrue ("union f6 f4",
                        f6.union (f4).equals(f4));
            assertTrue ("union f6 f7",
                        f6.union (f7).equals(f4));
            assertTrue ("union f7 f6",
                        f7.union (f6).equals(f4));

            assertTrue ("intersect f0 f0",
                        f0.intersect (f0).isEmpty());
            assertTrue ("intersect f0 s1",
                        f0.intersect (s1).isEmpty());
            assertTrue ("intersect s1 f0",
                        s1.intersect (f0).isEmpty());
            assertTrue ("intersect s1 s1",
                        s1.intersect (s1).equals(s1));
            assertTrue ("intersect s1 s2",
                        s1.intersect (s2).isEmpty());
            assertTrue ("intersect s2 s1",
                        s2.intersect (s1).isEmpty());
            assertTrue ("intersect s2 s2",
                        s2.intersect (s2).equals(s2));
            assertTrue ("intersect s12 s3",
                        s12.intersect (s3).isEmpty());
            assertTrue ("intersect s3 s12",
                        s3.intersect (s12).isEmpty());
            assertTrue ("intersect s13 s2",
                        s13.intersect (s2).isEmpty());
            assertTrue ("intersect s2 s13",
                        s2.intersect (s13).isEmpty());
            assertTrue ("intersect s123 s234",
                        s123.intersect (s234).equals(s23));
            assertTrue ("intersect f4 f6",
                        f4.intersect (f6).equals(s12.union (s34)));
            assertTrue ("intersect f6 f4",
                        f6.intersect (f4).equals(s12.union (s34)));
            assertTrue ("intersect f6 f7",
                        f6.intersect (f7).equals(s12));
            assertTrue ("intersect f7 f6",
                        f7.intersect (f6).equals(s12));
        }
        catch (Exception e) {
            System.out.println("Exception thrown during accessors tests:");
            System.out.println(e);
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
        }
        catch (Exception e) {
            System.out.println("Exception thrown during usual tests:");
            System.out.println(e);
            assertTrue ("usual", false);
        }
    }

////////////////////////////////////////////////////////////////

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
