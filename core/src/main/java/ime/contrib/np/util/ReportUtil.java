package ime.contrib.np.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.DOMOutputter;

public class ReportUtil {

    public static String DEFAULT_XSLT_FILE = "np.xsl";

    public void generate(String xmlPath, String reportPath) {
        try {
            this.generate(new FileInputStream(xmlPath), new FileOutputStream(reportPath));

            System.out.println("The HTML NP report has been write to file: " + xmlPath + ".");
            System.out.println(MessageFormat.format(MessageUtil.getMessage("npreport.html.success.msg"), reportPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void generate(InputStream xml, OutputStream report) {
        Document doc = null;
        try {
            SAXBuilder builder = new SAXBuilder();
            doc = builder.build(xml);

            org.w3c.dom.Document domDocument = new DOMOutputter().output(doc);
            Source xmlSource = new DOMSource(domDocument);

            StreamResult htmlResult = new StreamResult(report);

            StreamSource xsltSource = new StreamSource(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_XSLT_FILE));
            Transformer transformer = TransformerFactory.newInstance().newTransformer(xsltSource);

            transformer.transform(xmlSource, htmlResult);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public static ReportUtil newInstance() {
        return new ReportUtil();
    }
}
