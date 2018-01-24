package ime.contrib.np.util;

import ime.contrib.np.model.SchedulerRequest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.InputStream;

public class XMLReaderUtilTest {
    @Test
    public void testBuild() {
        XMLReaderUtil xmlReaderUtil = new XMLReaderUtil();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("data/test1.xml");

        SchedulerRequest request = xmlReaderUtil.build(inputStream);

        Assert.assertEquals(request.getMachines().size(), 2);

    }
}
