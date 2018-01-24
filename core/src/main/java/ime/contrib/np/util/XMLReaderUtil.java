package ime.contrib.np.util;

import ime.contrib.np.model.Job;
import ime.contrib.np.model.Machine;
import ime.contrib.np.model.Operation;
import ime.contrib.np.model.SchedulerRequest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.DataConversionException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class XMLReaderUtil {
    protected Map<String, Machine> machinesMap = new HashMap<String, Machine>();
    protected Map<String, Operation> operationsMap = new HashMap<String, Operation>();

    public SchedulerRequest build(String xmlPath) {
        try {
            InputStream inputStream = new FileInputStream(xmlPath);
            return build(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public SchedulerRequest build(InputStream inputStream) {
        SAXBuilder builder = new SAXBuilder();
        try {
            Document doc = builder.build(inputStream);
            return build(doc.getRootElement());
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public SchedulerRequest build(Element element) {
        SchedulerRequest request = new SchedulerRequest();

        //build machines
        List machineList = element.getChild("machines").getChildren();
        for(int i = 0; i < machineList.size(); i ++) {
            Element e = (Element)machineList.get(i);
            Machine m = buildMachine(e);
            machinesMap.put(m.getId(), m);
            request.addMachine(m);
        }

        //build jobs
        List jobList = element.getChild("jobs").getChildren();
        for(int i = 0; i < jobList.size(); i ++) {
            Element e = (Element) jobList.get(i);
            Job job = buildJob(e);
            request.addJob(job);
        }

        //Add operation dependicies link
        List dependencyList = element.getChild("dependencies").getChildren();
        for(int i = 0; i < dependencyList.size(); i ++) {
            Element e = (Element)dependencyList.get(i);
            buildDependency(e);
        }

        //add machine possible operations
        for(Operation o : operationsMap.values()) {
            for(Machine m : o.getPossibleMachines()) {
                m.addPossibleOperation(o);
            }
        }

        return request;
    }

    public Machine buildMachine(Element element) {
        Machine m = new Machine();
        try {
            m.setId(element.getAttribute("id").getValue());
            m.setPower(element.getAttribute("power").getDoubleValue());
        } catch (DataConversionException e) {
            e.printStackTrace();
        }

        return m;
    }

    public Job buildJob(Element element) {
        Job job = new Job();

        job.setId(element.getAttribute("id").getValue());

        //build Operations
        List operationList = element.getChild("operations").getChildren();
        for(int i = 0; i < operationList.size(); i ++) {
            Element e = (Element) operationList.get(i);
            Operation o = buildOperation(e);
            operationsMap.put(o.getId(), o);
            job.addOperation(o);
        }

        return job;
    }

    public Operation buildOperation(Element element) {
        Operation o = new Operation(element.getAttribute("id").getValue());

        List alternativeMachineList = element.getChild("alternativeMachines").getChildren();
        for(int i = 0; i < alternativeMachineList.size(); i ++) {
            Element e = (Element)alternativeMachineList.get(i);
            try {
                o.addPossibleMachine(machinesMap.get(e.getAttribute("id").getValue()));
                o.addProcessTime(machinesMap.get(e.getAttribute("id").getValue()), e.getAttribute("processTime").getDoubleValue());
                o.addEnergyConsumptionMap(machinesMap.get(e.getAttribute("id").getValue()), e.getAttribute("energyConsumption").getDoubleValue());
            } catch (DataConversionException e1) {
                e1.printStackTrace();
            }

        }

        return o;
    }

    public void buildDependency(Element element) {
        Operation o = operationsMap.get(element.getAttribute("operationID").getValue());
        String[] preOperations = split(element.getChild("preOperations").getValue());
        String[] nextOperations = split(element.getChild("nextOperations").getValue());

        //link pre operations
        for(int i = 0; i < preOperations.length; i++) {
            o.addPreOperation(operationsMap.get(preOperations[i]));
        }

        //link next operation
        for(int i = 0; i < nextOperations.length; i++) {
            o.addNextOperation(operationsMap.get(nextOperations[i]));
        }
    }

    protected String[] split(String str) {
        str.trim();
        if (str.equals("")) {
            return new String[0];
        }
        String[] results = str.split(",");
        for (int i = 0 ; i < results.length; i ++) {
            results[i].trim();
        }

        return results;
    }

    public static  XMLReaderUtil newInstance() {
        return new XMLReaderUtil();
    }
}
