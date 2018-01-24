package ime.contrib.np.util;

import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ReportUtilTest {
    @Test
    public void testGenerate() throws FileNotFoundException {
        InputStream xml = this.getClass().getClassLoader().getResourceAsStream("data/output1.xml");
        OutputStream report = new FileOutputStream("c:\\NP_Report_Test1.html");

        ReportUtil util = new ReportUtil();
        util.generate(xml, report);
    }
}
