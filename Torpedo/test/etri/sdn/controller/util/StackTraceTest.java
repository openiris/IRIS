package etri.sdn.controller.util;

import static org.junit.Assert.*;

import org.junit.Test;

import etri.sdn.controller.util.StackTrace;

public class StackTraceTest {
	
	private static void methodA() {
		throw new IllegalArgumentException();
	}
	
	private static void methodB() {
		methodA();
	}

	@Test
	public void test() {
		try {
			methodB();
		} catch ( IllegalArgumentException e ) {
			String trace = StackTrace.of( e );
			assertTrue(trace.indexOf("java.lang.IllegalArgumentException") == 0);
			
			int idxA = trace.indexOf("methodA");
			int idxB = trace.indexOf("methodB");
			
			assertFalse(idxA == -1);
			assertFalse(idxB == -1);
			assertTrue( idxA < idxB );
		}
	}

}
