import ime.contrib.np.core.INPEngine;
import ime.contrib.np.core.NPEngineMaxTotalEnergy;
import ime.contrib.np.core.NPEngineMinEnergy;
import ime.contrib.np.core.NPEngineMinIdleEnergy;
import ime.contrib.np.core.NPEngineMinMakespan;
import ime.contrib.np.core.NPEngineMinTotalEnergy;
import ime.contrib.np.handler.ParetoFrontUtil;
import ime.contrib.np.model.SchedulerRequest;
import ime.contrib.np.model.SchedulerResponse;
import ime.contrib.np.util.MessageUtil;
import ime.contrib.np.util.ReportUtil;
import ime.contrib.np.util.XMLReaderUtil;
import ime.contrib.np.util.XMLWriterUtil;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        String inputXML = System.getProperty("inputXML");
        String outputDir = System.getProperty("outputDir");

        if(inputXML == null) {
            System.out.println(MessageUtil.getMessage("parameter.inputxml.invalid"));
            return ;
        }

        if(outputDir == null) {
            System.out.println(MessageUtil.getMessage("parameter.outputdir.invalid"));
            return ;
        }
        
        File resultFolder = new File(outputDir);
        if (!resultFolder.exists()) {
            resultFolder.mkdir();
        }

        SchedulerRequest request = XMLReaderUtil.newInstance().build(inputXML);
        
//        Main.maxTotalEnergy(request, resultFolder);
//        Main.minTotalEngergy(request, resultFolder);
        
//        Main.minEnergy(request, resultFolder);
//        Main.minIdleEnergy(request, resultFolder);
        
//        Main.minMakespan(request, resultFolder);
        
        Main.minBi(request, resultFolder);
    }
    
    protected static void minEnergy(SchedulerRequest request, File resultFolder) {
    	INPEngine npEngine;
        SchedulerResponse response;
        String outputXML;
        String reportFile;
        
        //Minimum Consumption Energy
        npEngine = new NPEngineMinEnergy();
        response = npEngine.run(request);
        
        outputXML = resultFolder.getAbsolutePath() + File.separator + "MinEnergy-NP-Result.xml";
        XMLWriterUtil.newInstance().build(outputXML, response);

        reportFile = resultFolder.getAbsolutePath() + File.separator + "MinEnergy-NP-Result.html";
        ReportUtil.newInstance().generate(outputXML, reportFile);
    }
    
    protected static void minIdleEnergy(SchedulerRequest request, File resultFolder) {
    	INPEngine npEngine;
        SchedulerResponse response;
        String outputXML;
        String reportFile;
        
        //Minimum Idle Energy
        npEngine = new NPEngineMinIdleEnergy();
        response = npEngine.run(request);
        
        outputXML = resultFolder.getAbsolutePath() + File.separator + "MinIdleEnergy-NP-Result.xml";
        XMLWriterUtil.newInstance().build(outputXML, response);

        reportFile = resultFolder.getAbsolutePath() + File.separator + "MinIdleEnergy-NP-Result.html";
        ReportUtil.newInstance().generate(outputXML, reportFile);
    }
    
    protected static void maxTotalEnergy(SchedulerRequest request, File resultFolder) {
    	INPEngine npEngine;
        SchedulerResponse response;
        String outputXML;
        String reportFile;
        
        // Max total Energy
        npEngine = new NPEngineMaxTotalEnergy();
        response = npEngine.run(request);
        
        outputXML = resultFolder.getAbsolutePath() + File.separator + "MaxTotalEnergy-NP-Result.xml";
        XMLWriterUtil.newInstance().build(outputXML, response);

        reportFile = resultFolder.getAbsolutePath() + File.separator + "MaxTotalEnergy-NP-Result.html";
        ReportUtil.newInstance().generate(outputXML, reportFile);
    }
    
    protected static void minTotalEngergy(SchedulerRequest request, File resultFolder) {
    	INPEngine npEngine;
        SchedulerResponse response;
        String outputXML;
        String reportFile;
        
        //Minimum Total Energy
        npEngine = new NPEngineMinTotalEnergy();
        response = npEngine.run(request);
        
        outputXML = resultFolder.getAbsolutePath() + File.separator + "MinTotalEnergy-NP-Result.xml";
        XMLWriterUtil.newInstance().build(outputXML, response);

        reportFile = resultFolder.getAbsolutePath() + File.separator + "MinTotalEnergy-NP-Result.html";
        ReportUtil.newInstance().generate(outputXML, reportFile);
    }
    
    protected static void minMakespan(SchedulerRequest request, File resultFolder) {
    	INPEngine npEngine;
        SchedulerResponse response;
        String outputXML;
        String reportFile;
        
        //Minimum Makespan
        npEngine = new NPEngineMinMakespan();
        response = npEngine.run(request);
        
        outputXML = resultFolder.getAbsolutePath() + File.separator + "MinMakespan-NP-Result.xml";
        XMLWriterUtil.newInstance().build(outputXML, response);

        reportFile = resultFolder.getAbsolutePath() + File.separator + "MinMakespan-NP-Result.html";
        ReportUtil.newInstance().generate(outputXML, reportFile);
    }
    
    protected static void minBi(SchedulerRequest request, File resultFolder) {
    	INPEngine npEngine;
        SchedulerResponse response;
        String outputXML;
        String reportFile;
        
        //Bi Minimum Total Energy & Minimum makespan
        npEngine = new NPEngineMinTotalEnergy();
        response = npEngine.run(request);
        
        outputXML = resultFolder.getAbsolutePath() + File.separator + "Bi-NP-Result.xml";
        XMLWriterUtil.newInstance().build(outputXML, response);

        reportFile = resultFolder.getAbsolutePath() + File.separator + "Bi-NP-Result.html";
        ReportUtil.newInstance().generate(outputXML, reportFile);
        
        SchedulerResponse paretoFrontResp = ParetoFrontUtil.parseToParetoFrontSolution(response);
        outputXML = resultFolder.getAbsolutePath() + File.separator + "Bi-NP-ParetoFront-Result.xml";
        XMLWriterUtil.newInstance().build(outputXML, paretoFrontResp);

        reportFile = resultFolder.getAbsolutePath() + File.separator + "Bi-NP-ParetoFront-Result.html";
        ReportUtil.newInstance().generate(outputXML, reportFile);
    }


}
