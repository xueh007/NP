package ime.contrib.np.core;

import ime.contrib.np.model.SchedulerRequest;
import ime.contrib.np.model.SchedulerResponse;
import ime.contrib.np.util.XMLReaderUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.InputStream;

public class NPEngineTest {
    @Test
    public void testRun() {
        NPEngine engine = new NPEngine();

        XMLReaderUtil xmlReaderUtil = new XMLReaderUtil();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("data/test1.xml");

        SchedulerRequest request = xmlReaderUtil.build(inputStream);

        SchedulerResponse response = engine.run(request);

        response.getSolutions();
    }
}
