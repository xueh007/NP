package ime.contrib.np.handler;

import java.util.ArrayList;
import java.util.List;

import ime.contrib.np.model.SchedulerResponse;
import ime.contrib.np.model.Solution;

public class ParetoFrontUtil {

	static public SchedulerResponse parseToParetoFrontSolution(SchedulerResponse response) {
		List<Solution> paretoFrontSolutions = new ArrayList<Solution>();
		paretoFrontSolutions.addAll(response.getSolutions());
		
		for (Solution s : response.getSolutions()) {
    		for (Solution ps : response.getSolutions()) {
    			if (s.calTotalEnergy() > ps.calTotalEnergy() && s.calMakeSpanTime() > ps.calMakeSpanTime()) {
    				paretoFrontSolutions.remove(s);
    				continue;
    			}
    		}
    	}
		
		SchedulerResponse paretoFrontResp = new SchedulerResponse();
		paretoFrontResp.setExecutionTime(response.getExecutionTime());
		paretoFrontResp.setSolutions(paretoFrontSolutions);
		
		return paretoFrontResp;
		
		
	}
}
