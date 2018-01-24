package ime.contrib.np.util;

import ime.contrib.np.model.IntermediaMachine;
import ime.contrib.np.model.Operation;
import ime.contrib.np.model.SchedulerResponse;
import ime.contrib.np.model.Solution;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

public class XMLWriterUtil {
    public void build(String xmlPath, SchedulerResponse response) {
        try {
            Document document = new Document();
            document.addContent(this.build(response));

            FileWriter writer = new FileWriter(xmlPath);
            XMLOutputter outputter = new XMLOutputter();

            outputter.setFormat(Format.getPrettyFormat());

            outputter.output(document, writer);

            System.out.println(MessageFormat.format(MessageUtil.getMessage("npresult.xml.success.msg"), xmlPath));

            System.out.println("The NP result has been write to file: " + xmlPath + ".");
            
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Element build(SchedulerResponse response) {
        Element e = new Element("response");
        e.setAttribute("executionTime", FormatUtil.format(response.getExecutionTime(), 2));

        List<Solution> solutions = response.getSolutions();
        for (int i = 0; i < solutions.size(); i++) {
            Element solutionElement = this.build(solutions.get(i));
            solutionElement.setAttribute("id", new Integer(i).toString());
            e.addContent(solutionElement);
        }

        return e;
    }

    public Element build(Solution solution) {
        Element e = new Element("solution");
        e.setAttribute("totalEnergyConsumption", FormatUtil.format(solution.calTotalEnergy(), 2));
        e.setAttribute("machineEnergyConsumption", FormatUtil.format(solution.calEnergyConsumption(), 2));
        e.setAttribute("idleEnergyConsumption", FormatUtil.format(solution.calIdleConsumption(), 2));
        e.setAttribute("completionTime", FormatUtil.format(solution.calMakeSpanTime(), 2));

        for (IntermediaMachine im : solution.getIntermediaMachines()) {
            e.addContent(this.build(im));
        }

        return e;
    }

    public Element build(IntermediaMachine im) {
        Element e = new Element("machine");
        e.setAttribute("id", im.getMachine().getId());
        e.setAttribute("totalEnergyConsumption", FormatUtil.format(im.calTotalEnergy(), 2));
        e.setAttribute("machineEnergyConsumption", FormatUtil.format(im.calEnergyConsumption(), 2));
        e.setAttribute("completionTime", FormatUtil.format(im.calMakeSpanTime(), 2));
        e.setAttribute("idleEnergyConsumption", FormatUtil.format(im.calIdleConsumption(), 2));
        e.setAttribute("makeSpanTime", FormatUtil.format(im.calMakeSpanTime(), 2));
        e.setAttribute("idleTime", FormatUtil.format(im.calIdleTime(), 2));
        
        for (Operation o : im.getAllOperations()) {
            e.addContent(this.build(im, o));
        }

        return e;
    }

    public Element build(IntermediaMachine im, Operation o) {
        Element e = new Element("operation");
        e.setAttribute("id", o.getId());

        e.setAttribute("startTime", FormatUtil.format(im.getTP(o).getStartTime(), 1));
        e.setAttribute("endTime", FormatUtil.format(im.getTP(o).getEndTime(im.getMachine()), 1));
        
        e.setAttribute("machineEnergyConsumption", FormatUtil.format(o.getEnergyConsumption(im.getMachine()), 2));
        
        return e;
    }

    public static XMLWriterUtil newInstance() {
        return new XMLWriterUtil();
    }
}
