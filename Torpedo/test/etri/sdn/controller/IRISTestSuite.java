package etri.sdn.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * This class is an executor of all test cases defined under test/ directory.
 * 
 * @author bjlee
 *
 */

@RunWith(Suite.class)
@SuiteClasses({
	/*
	 * You should list all the case class classes in here.
	 * If this feels as nonsense, consider the use of 
	 * 
	 * http://johanneslink.net/projects/cpsuite.jsp
	 */
	etri.sdn.controller.util.BasenameTest.class,
	etri.sdn.controller.util.StackTraceTest.class
})
public final class IRISTestSuite {
	
	/*
	 * You can empty this block because @SuiteClasses is used. 
	 * 
	 */
}
