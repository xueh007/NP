package ime.contrib.np.core;

import ime.contrib.np.model.SchedulerRequest;
import ime.contrib.np.model.SchedulerResponse;
import ime.contrib.np.util.XMLReaderUtil;
import org.testng.annotations.Test;

import java.io.InputStream;

public class NPEngineMakespanTest {
    @Test
    public void testRun() {
        INPEngine engine = new NPEngineMinMakespan();

        XMLReaderUtil xmlReaderUtil = new XMLReaderUtil();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("data/test2.xml");

        SchedulerRequest request = xmlReaderUtil.build(inputStream);

        SchedulerResponse response = engine.run(request);

        response.getSolutions();
    }
}
