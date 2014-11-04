package etri.sdn.controller.util;

import static org.junit.Assert.*;

import org.junit.Test;

import etri.sdn.controller.util.Basename;

public class BasenameTest {

	@Test
	public void test() {
		String ret = Basename.get("this.is.test.path.jar", "jar");
		assertEquals("this.is.test.path", ret);
	}

}
