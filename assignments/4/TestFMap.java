/*
 *  Dan Calacci
 *  dcalacci@ccs.neu.edu
 *
 */


public class TestFMap {

  public static void main (String args[]) {
    TestFMap test = new TestFMap();

    test.creation();
    test.accessors();
    test.usual();
    test.accessors();
    test.usual();

    test.summarize();
  }

  private void summarize() {
    System.out.println();
    System.out.println (totalErrors + " errors found in " + totalTests +
        " tests.");
  }
  public TestFMap() {}

  // elements

  private final String a = "a";
  private final String b = "b";
  private final String c = "c";
  private final String d = "d";

  private final Integer one = 1;
  private final Integer two = 2;
  private final Integer three = 3;
  private final Integer four = 4;

  private FMap<Integer, String> fempty2;
  private FMap<String, Integer> fempty;
  private FMap<String, Integer> f1;
  private FMap<String, Integer> f2;
  private FMap<String, Integer> f3;
  private FMap<String, Integer> f4;
  private FMap<String, Integer> f5;
  private FMap<Integer, String> f6;
  private FMap<String, Integer> f7;
  private FMap<String, Integer> f8;
  private FMap<String, Integer> f9;
  private FMap<String, Integer> f10;

  private void creation() {
    try {
      fempty = FMap.emptyMap();
      fempty2 = FMap.emptyMap();
      f1 = fempty.add(a, one); // (a|1}
      f2 = f1.add(b, two);  // {a, 1; b,2}
      f3 = f2.add(c, three); // {a,1; b,2; c,3}
      f4 = f3.add(d, four); // {a,1; b,2; c,3; d,4}
      f5 = fempty.add(new String(a), new Integer(1)); // {a,1}
      f6 = fempty2.add(one, a); //{1, a}
      f7 = f3.add(c, three);//{a,1; b,2; c,3; c, 3}
      f8 = fempty.add(b, two);
      f9 = f8.add(a, one);
      f10 = f9.add(c, three);

    }
    catch (Exception e) {
      System.out.println("Exception thrown during creation tests:");
      System.out.println(e);
      assertTrue ("creation", false);
    }
  }

  private void accessors() {
    try {
      // isEmpty testing
      assertTrue("empty", fempty.isEmpty());
      assertFalse("not empty", f1.isEmpty());
      assertFalse("not empty", f4.isEmpty());

      // size testing
      assertTrue("fempty.size()", fempty.size() == 0);
      assertTrue("f1.size()", f1.size() == one);
      assertTrue("f2.size()", f2.size() == two);
      assertTrue("f3.size()", f3.size() == three);
      assertTrue("f4.size():", f4.size() == f3.size() + 1);
      assertFalse("fempty.size()", fempty.size() == one);
      assertFalse("f2.size()", f2.size() == three);

      //containsKey testing
      assertTrue("fempty.containsKey(a)", fempty.containsKey(a) == false);
      assertTrue("f1.containsKey(a)", f1.containsKey(a) == true);
      assertTrue("f2.containsKey(a)", f2.containsKey(a) == true);
      assertTrue("F2.containsKey(b)", f2.containsKey(b) == true);
      assertTrue("F3.containsKey(c)", f3.containsKey(c) == true);
      assertTrue("F3.containsKey(b)", f3.containsKey(b) == true);
      assertTrue("F3.containsKey(d)", f3.containsKey(d) == false);
      assertTrue("F2.containsKey(c)", f2.containsKey(c) == false);
      assertTrue("F2.containsKey(d)", f2.containsKey(d) == false);

      //get testing
      assertTrue("f1.get(a)", f1.get(a).equals(one));
      assertTrue("f2.get(a)", f2.get(a).equals(one));
      assertTrue("f2.get(b)", f2.get(b).equals(two));
      assertTrue("f3.get(a)", f3.get(a).equals(one));
      assertTrue("f3.get(b)", f3.get(b).equals(f2.get(b)));
      assertFalse("f2.get(a)", f2.get(a).equals(f2.get(b)));
    }
    catch(Exception e) {
      System.out.println("Exception thrown during accessors tests:");
      System.out.println(e);
      assertTrue("accessors", false);
    }
  }

  private void usual () {
    try {
      //tostring
      assertTrue("toStringempty",
          fempty.toString().equals("{...(0 entries)...}"));
      assertTrue("toString1",
          f1.toString().equals("{...(1 entries)...}"));
      assertTrue("toString3", 
          f3.toString().equals("{...(3 entries)...}"));
      assertFalse("toString2",
          f2.toString().equals(" "));
      assertTrue("toString2.1",
          f2.toString().equals("{...(2 entries)...}"));

      //equals!
      assertTrue("equalsemptyempty", fempty.equals(fempty));
      assertTrue("equals11", f1.equals(f1));
      assertTrue("equals22", f2.equals(f2));
      assertTrue("equals33", f3.equals(f3));
      assertTrue("equals44", f4.equals(f4));
      assertTrue("equals55", f5.equals(f5));
      assertTrue("equals51", f5.equals(f1));
      assertFalse("equals12", f1.equals(f2));
      assertFalse("equals23", f2.equals(f3));
      assertFalse("equalsemptynull", fempty.equals(null));
      assertFalse("equals1null", f1.equals(null));
      assertFalse("equals2string", f2.equals("hi"));
      assertFalse("equalsemptystring", fempty.equals("hello"));
      assertTrue("equalsempty1empty2", fempty.equals(fempty2));
      assertFalse("equalsemptynull", fempty.equals(null));
      assertFalse("equalsempty1", fempty.equals(f1));
      assertFalse("equalstype1type2", f6.equals(f1));
      assertTrue("duplicates", f7.equals(f3));
      assertTrue("order", f10.equals(f3));
      assertTrue("order", f9.equals(f2));

      //hashCode
      assertTrue("laws of hashcode", f9.hashCode() == f2.hashCode());
      assertTrue("self-equality", f2.hashCode() == f2.hashCode());
      assertTrue("emptytoempty", fempty.hashCode() == fempty2.hashCode());
    }
    catch(Exception e) {
      System.out.println("Exception thrown during usual tests:");
      System.out.println(e);
      assertTrue("usual", false);
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




