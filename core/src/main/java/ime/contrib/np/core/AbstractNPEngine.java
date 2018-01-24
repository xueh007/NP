package ime.contrib.np.core;

import ime.contrib.np.handler.EvaluateHandler;
import ime.contrib.np.handler.PartitionHandler;
import ime.contrib.np.handler.SamplingHandler;
import ime.contrib.np.model.Region;
import ime.contrib.np.model.SchedulerRequest;
import ime.contrib.np.model.SchedulerResponse;
import ime.contrib.np.model.Solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract public class AbstractNPEngine implements INPEngine {

    protected PartitionHandler partitionHandler;
    protected SamplingHandler samplingHandler;
    protected EvaluateHandler evaluateHandler;

    protected List<Solution> solutions = new ArrayList<Solution>();

    public AbstractNPEngine() {
        partitionHandler = new PartitionHandler();
        samplingHandler = new SamplingHandler();
        evaluateHandler = new EvaluateHandler();
    }

    public SchedulerResponse run(SchedulerRequest request) {
        partitionHandler.setRequest(request);
        samplingHandler.setRequest(request);

        long startTime = System.currentTimeMillis();

        Region region = new Region();

        do {
        	List<Solution> sampleingSolutions = new ArrayList<Solution>();
        	Map<Solution, Region> solutionToRegion = new HashMap<Solution, Region>();
        	
            //do sampling on partition regions, 3 times sampling on each region
            for(Region r : partitionHandler.partition(region)) {
                for(int i = 0; i < 10; i++) {
                	Solution s = samplingHandler.sampling(r);

                    if (s == null) {
                        continue;
                    }
                    
                    while (evaluateHandler.evaluate(s) == false) {
                    	s = samplingHandler.sampling(r);
                    }
                    
                    sampleingSolutions.add(s);
                    solutionToRegion.put(s, r);
                }
            }
            //do sampling on surrounding regions, 1 time sampling on each region
            for(Region r : region.getSurroundingRegion()) {
                Solution s = samplingHandler.sampling(r);
                
                if (s == null) {
                    continue;
                }
                
                while (evaluateHandler.evaluate(s) == false) {
                	s = samplingHandler.sampling(r);
                }
                
                sampleingSolutions.add(s);
                solutionToRegion.put(s, r);
            }

            Solution bestSolution = this.findBestSolution(sampleingSolutions);
            
            System.out.println(bestSolution.toString());

            this.solutions.add(bestSolution);
            region = solutionToRegion.get(bestSolution);
            
        }while (region.getAllFixedOperations().size() < request.getAllOperations().size());

        long endTime = System.currentTimeMillis();

        SchedulerResponse response = new SchedulerResponse();
        response.setSolutions(getSolutions());
        response.setExecutionTime(endTime - startTime);

        return response;
    }

    /**
     * 
     * @param a - current best solution
     * @param b - solution b
     * @return true if best solution is better than solution b, false if solution b is better than current best solution
     */
    abstract protected Solution findBestSolution(List<Solution> solutions);
    
    protected List<Solution> getParetoFrontSolutions() {
    	return null;
    }
    
    protected List<Solution> getSolutions() {
    	return this.solutions;
    }
}
