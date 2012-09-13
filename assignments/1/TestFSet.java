// Basic test program for assignment 1.
//
// Revision 1: 9 May 2012 (added tests for FSet.isSubset(FSet))

public class TestFSet {

    // Runs the tests.
               
    public static void main(String args[]) {
        TestFSet test = new TestFSet();

        // Test with 0-argument FSet.emptySet().

        System.out.println("Testing 0-argument emptySet()");
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
            f1 = FSet.adjoin (f0, one);
            f2 = FSet.adjoin (f1, two);
            f3 = FSet.adjoin (f2, three);
            f4 = FSet.adjoin (f3, four);
            f5 = FSet.adjoin (f2, one);
            f6 = FSet.adjoin (FSet.adjoin (f2, four), three);

            f7 = FSet.adjoin (f0, one);
            f7 = FSet.adjoin (f7, two);
            f7 = FSet.adjoin (f7, two);
            f7 = FSet.adjoin (f7, one);

            s1 = FSet.adjoin (f0, one);
            s2 = FSet.adjoin (f0, two);
            s3 = FSet.adjoin (f0, three);
            s4 = FSet.adjoin (f0, four);

            s12 = FSet.adjoin (f1, new String(two));
            s13 = FSet.adjoin (f1, new String(three));
            s14 = FSet.adjoin (f1, new String(four));
            s23 = FSet.adjoin (s2, new String(three));
            s34 = FSet.adjoin (s3, new String(four));

            s123 = FSet.add (s12, three);
            s124 = FSet.add (s12, four);
            s134 = FSet.add (s13, four);
            s234 = FSet.add (s23, four);

            s134 = FSet.add (s134, four);
            s234 = FSet.add (s234, four);

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
            assertTrue ("empty", FSet.isEmpty (f0));
            assertFalse ("nonempty", FSet.isEmpty (f1));
            assertFalse ("nonempty", FSet.isEmpty (f3));

            assertTrue ("f0.size()", FSet.size (f0) == 0);
            assertTrue ("f1.size()", FSet.size (f1) == 1);
            assertTrue ("f2.size()", FSet.size (f2) == 2);
            assertTrue ("f3.size()", FSet.size (f3) == 3);
            assertTrue ("f4.size()", FSet.size (f4) == 4);
            assertTrue ("f5.size()", FSet.size (f5) == 2);
            assertTrue ("f7.size()", FSet.size (f7) == 2);

            assertFalse ("contains01", FSet.contains (f0, one));
            assertFalse ("contains04", FSet.contains (f0, four));
            assertTrue  ("contains11", FSet.contains (f1, one));
            assertTrue  ("contains11new", FSet.contains (f1, new String(one)));
            assertFalse ("contains14", FSet.contains (f1, four));
            assertTrue  ("contains21", FSet.contains (f2, one));
            assertFalse ("contains24", FSet.contains (f2, four));
            assertTrue  ("contains31", FSet.contains (f3, one));
            assertFalse ("contains34", FSet.contains (f3, four));
            assertTrue  ("contains41", FSet.contains (f4, one));
            assertTrue  ("contains44", FSet.contains (f4, four));
            assertTrue  ("contains51", FSet.contains (f5, one));
            assertFalse ("contains54", FSet.contains (f5, four));

            assertTrue  ("isSubset00", FSet.isSubset (f0, f0));
            assertTrue  ("isSubset01", FSet.isSubset (f0, f1));
            assertFalse ("isSubset10", FSet.isSubset (f1, f0));
            assertTrue  ("isSubset02", FSet.isSubset (f0, f2));
            assertFalse ("isSubset20", FSet.isSubset (f2, f0));
            assertTrue  ("isSubset24", FSet.isSubset (f2, f4));
            assertFalse ("isSubset42", FSet.isSubset (f4, f2));
            assertTrue  ("isSubset27", FSet.isSubset (f2, f7));
            assertTrue  ("isSubset72", FSet.isSubset (f7, f2));
            assertTrue  ("isSubset 13 3", FSet.isSubset (s13, f3));
            assertFalse ("isSubset 3 13", FSet.isSubset (f3, s13));
            assertTrue  ("isSubset 123 3", FSet.isSubset (s123, f3));
            assertTrue  ("isSubset 3 123", FSet.isSubset (f3, s123));

            assertTrue ("without f0 1", FSet.isEmpty (FSet.without (f0, one)));
            assertTrue ("without f1 1", FSet.isEmpty (FSet.without (f1, one)));
            assertTrue ("without f1 2", FSet.without (f1, two).equals(s1));
            assertTrue ("without f2 1", FSet.without (f2, one).equals(s2));
            assertTrue ("without f2 2", FSet.without (f2, two).equals(s1));
            assertTrue ("without f2 3", FSet.without (f2, three).equals(s12));
            assertTrue ("without f2 12",
                        FSet.isEmpty (FSet.without (FSet.without (f2, one),
                                                    two)));
            assertTrue ("without f2 21",
                        FSet.isEmpty (FSet.without (FSet.without (f2, two),
                                                    one)));
            assertTrue ("without f6 1", FSet.without (f6, one).equals(s234));
            assertTrue ("without f6 2", FSet.without (f6, two).equals(s134));
            assertTrue ("without f6 3", FSet.without (f6, three).equals(s124));
            assertTrue ("without f6 4", FSet.without (f6, four).equals(s123));

            assertTrue ("union f0 f0", FSet.isEmpty (FSet.union (f0, f0)));
            assertTrue ("union f0 s1", FSet.union (f0, s1).equals(s1));
            assertTrue ("union s1 f0", FSet.union (s1, f0).equals(s1));
            assertTrue ("union s1 s1", FSet.union (s1, s1).equals(s1));
            assertTrue ("union s1 s2", FSet.union (s1, s2).equals(s12));
            assertTrue ("union s2 s1", FSet.union (s2, s1).equals(s12));
            assertTrue ("union s2 s2", FSet.union (s2, s2).equals(s2));
            assertTrue ("union s12 s3", FSet.union (s12, s3).equals(s123));
            assertTrue ("union s3 s12", FSet.union (s3, s12).equals(s123));
            assertTrue ("union s13 s2", FSet.union (s13, s2).equals(s123));
            assertTrue ("union s2 s13", FSet.union (s2, s13).equals(s123));
            assertTrue ("union s123 s234",
                        FSet.union (s123, s234).equals(f4));
            assertTrue ("union f4 f6",
                        FSet.union (f4, f6).equals(f4));
            assertTrue ("union f6 f4",
                        FSet.union (f6, f4).equals(f4));
            assertTrue ("union f6 f7",
                        FSet.union (f6, f7).equals(f4));
            assertTrue ("union f7 f6",
                        FSet.union (f7, f6).equals(f4));

            assertTrue ("intersect f0 f0",
                        FSet.isEmpty (FSet.intersect (f0, f0)));
            assertTrue ("intersect f0 s1",
                        FSet.isEmpty (FSet.intersect (f0, s1)));
            assertTrue ("intersect s1 f0",
                        FSet.isEmpty (FSet.intersect (s1, f0)));
            assertTrue ("intersect s1 s1",
                        FSet.intersect (s1, s1).equals(s1));
            assertTrue ("intersect s1 s2",
                        FSet.isEmpty (FSet.intersect (s1, s2)));
            assertTrue ("intersect s2 s1",
                        FSet.isEmpty (FSet.intersect (s2, s1)));
            assertTrue ("intersect s2 s2",
                        FSet.intersect (s2, s2).equals(s2));
            assertTrue ("intersect s12 s3",
                        FSet.isEmpty (FSet.intersect (s12, s3)));
            assertTrue ("intersect s3 s12",
                        FSet.isEmpty (FSet.intersect (s3, s12)));
            assertTrue ("intersect s13 s2",
                        FSet.isEmpty (FSet.intersect (s13, s2)));
            assertTrue ("intersect s2 s13",
                        FSet.isEmpty (FSet.intersect (s2, s13)));
            assertTrue ("intersect s123 s234",
                        FSet.intersect (s123, s234).equals(s23));
            assertTrue ("intersect f4 f6",
                        FSet.intersect (f4, f6).equals(FSet.union (s12, s34)));
            assertTrue ("intersect f6 f4",
                        FSet.intersect (f6, f4).equals(FSet.union (s12, s34)));
            assertTrue ("intersect f6 f7",
                        FSet.intersect (f6, f7).equals(s12));
            assertTrue ("intersect f7 f6",
                        FSet.intersect (f7, f6).equals(s12));
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

            System.out.println(f2.size() + ", " + f7.size());
            System.out.println(f2.hashCode() + ", " + f7.hashCode());
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
