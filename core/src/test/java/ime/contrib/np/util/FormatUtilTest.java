package ime.contrib.np.util;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FormatUtilTest {

	@Test
	public void testFormat() {
		 Assert.assertEquals(FormatUtil.format(100.9999, 2), "101");
		 Assert.assertEquals(FormatUtil.format(100.9999, 0), "101");
		 Assert.assertEquals(FormatUtil.format(100.009, 2), "100.01");
		 Assert.assertEquals(FormatUtil.format(100.009, 0), "100");
		 
		 Assert.assertEquals(FormatUtil.format(3456.009, 2), "3456.01");
	}
}
