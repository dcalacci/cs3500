/**
 * Mohammad Al Yahya
 * mhmdfy@ccs.neu.edu
 */

/**
 * A class that does a black-box test for FMap.
 */
public class TestFMap {
	
	/**
	 * Running the tests.
	 */
	public static void main(String[] args){
		TestFMap test = new TestFMap();
		
        test.creation();
        test.accessors();
        test.usual();
        test.accessors();	// test twice to detect side effects
        test.usual();

        test.summarize();
	}
	
	/** Instances of FMap to test. */
	private FMap<Integer, String> is0;	// {}
	private FMap<Integer, String> is1;	// {(1, a)}
	private FMap<Integer, String> is2;	// {(2, b), (1, a)}
	private FMap<Integer, String> is3;	// {(3, c), (2, b), (1, a)}
	private FMap<Integer, String> is4;	// {(4, d)}
	private FMap<Integer, String> is5;	// {(5, e), (1, a)}
	private FMap<Integer, String> is6;	// {(1, a), (5, e)}
	
	private FMap<Boolean, String> sb0;	// {}
	private FMap<Boolean, String> sb1;	// {(t, a)}
	private FMap<Boolean, String> sb2;	// {(f, b), (t, a)}
	private FMap<Boolean, String> sb3;	// {(f, c)}
	private FMap<Boolean, String> sb4;	// {(t, d), (f, b), (t, a)}
	private FMap<Boolean, String> sb5;	// {(f, e), (f, b), (t, a)}
	private FMap<Boolean, String> sb6;	// {(f, b), (f, b), (t, a)}
	
	private FMap<Integer, String> is7;
	
	
	/**
	 * Creates some FMaps to use on later tests.
	 */
	private void creation(){
		try{
			is0 = FMap.emptyMap();
			is1 = is0.add(1, "a");
			is2 = is1.add(2, "b");
			is3 = is2.add(3, "c");
			is4 = is0.add(4, "d");
			is5 = is1.add(5, "e");
			is6 = is0.add(5, "e").add(1, "a");
			
			sb0 = FMap.emptyMap();
			sb1 = sb0.add(true , "a");
			sb2 = sb1.add(false, "b");
			sb3 = sb0.add(false, "c");
			sb4 = sb2.add(true , "d");
			sb5 = sb2.add(false, "e");
			sb6 = sb2.add(false, "b");
			
			is7 = FMap.emptyMap();
			is7 = is7.add(1, "one");
			is7 = is7.add(2, "two");
			is7 = is7.add(1, "won");
			
		}
		catch(Exception e){
            System.out.println("Exception during creation tests:" + e);
            assertTrue ("creation", false);
		}
	}
	
	/**
	 * Tests the accessors of the FMap
	 */
	private void accessors(){
		try{
			assertTrue ("isEmpty1", is0.isEmpty());
			assertFalse("isEmpty2", is2.isEmpty());
			assertTrue ("isEmpty3", sb0.isEmpty());
			assertFalse("isEmpty4", sb3.isEmpty());
			
			assertTrue ("size1", is0.size() == 0);
			assertTrue ("size2", is1.size() == 1);
			assertTrue ("size3", is3.size() == 3);
			assertTrue ("size4", sb0.size() == 0);
			assertTrue ("size5", is2.size() == 2);
			assertTrue ("size6", sb5.size() == 2);
			
			assertFalse("containsKey1", is0.containsKey(1));
			assertTrue ("containsKey2", is2.containsKey(1));
			assertFalse("containsKey3", is4.containsKey(1));
			assertFalse("containsKey4", sb0.containsKey(true));
			assertFalse("containsKey5", sb3.containsKey(true));
			assertTrue ("containsKey6", sb5.containsKey(true));
			
			assertTrue ("get1", is1.get(1).equals("a"));
			assertTrue ("get2", is2.get(2).equals("b"));
			assertFalse("get3", is3.get(2).equals("c"));
			assertTrue ("get4", is5.get(5).equals("e"));
			assertTrue ("get5", sb2.get(true).equals("a"));
			assertTrue ("get6", sb2.get(false).equals("b"));
			assertFalse("get7", sb4.get(true).equals("a"));
			assertTrue ("get8", sb5.get(false).equals("e"));
		}
		catch(Exception e){
            System.out.println("Exception during accessors tests:" + e);
            assertTrue ("accessors", false);
		}
	}
	
	/**
	 * Tests for the overridden methods from Object class;
	 * namely equals, hashCode and toString methods.
	 */
	private void usual(){
		try{
			assertTrue ("equals1", is0.equals(sb0));
			assertFalse("equals2", is2.equals(sb5));
			assertTrue ("equals3", is5.equals(is6));
			assertFalse("equals4", sb4.equals(sb5));
			assertTrue ("equals5", sb2.equals(sb6));
			assertTrue ("equals6", is7.equals(is7));
			
			// equals should work both ways.
			assertTrue ("equals7", sb0.equals(is0));
			assertFalse("equals8", sb5.equals(is2));
			assertTrue ("equals9", is6.equals(is5));
			assertFalse("equals10", sb5.equals(sb4));
			assertTrue ("equals11", sb6.equals(sb2));
			
			String a = "a";
			assertFalse("equals12", is0.equals(null));
			assertFalse("equals13", is3.equals(a));
			assertFalse("equals14", sb0.equals(a));
			assertFalse("equals15", sb5.equals(null));
			
			

			
			assertTrue ("hashcode1", is0.hashCode() == sb0.hashCode());
			assertTrue ("hashcode2", is5.hashCode() == is6.hashCode());
			assertTrue ("hashcode3", sb2.hashCode() == sb6.hashCode());
			assertTrue ("hashcode4", is7.hashCode() == is7.hashCode());
			
			
			assertTrue("toString1", 
					is0.toString().equals("{...(0 entries)...}"));
			assertTrue("toString2", 
					sb4.toString().equals("{...(2 entries)...}"));
		}
		catch(Exception e){
            System.out.println("Exception during usual tests:" + e);
            assertTrue ("uaual", false);
		}
	}
	
	/**
	 * Prints out the summary of the tests performed.
	 */
	private void summarize(){
        System.out.println ("\n" + totalErrors+" errors found in " +
                            totalTests + " tests.");
	}

	
	private int totalTests = 0;		// tests run so far
	private int totalErrors = 0;	// errors so far
	
	/**
	 *  Prints failure report if the result is not true.
	 * @param name The name of the test being run
	 * @param result The result compared to the expected one.
	 */
	private void assertTrue (String name, boolean result) {
		if (! result) {
			System.out.println ("\n***** Test failed ***** "
					 			+ name + ": " +totalTests);
			totalErrors++;
			}
		totalTests++;
	}
	 
	/**
	 *  Prints failure report if the result is not false.
	 * @param name The name of the test being run
	 * @param result The result compared to the expected one.
	 */
	private void assertFalse (String name, boolean result) {
		assertTrue (name, ! result);
	}
}
