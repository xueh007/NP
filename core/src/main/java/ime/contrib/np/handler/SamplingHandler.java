package ime.contrib.np.handler;

import ime.contrib.np.model.Operation;
import ime.contrib.np.model.Region;
import ime.contrib.np.model.SchedulerRequest;
import ime.contrib.np.model.Solution;
import ime.contrib.np.util.RandomUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SamplingHandler {
    protected SchedulerRequest request;
    protected Set<Operation> omega = new HashSet<Operation>();

    protected Solution solution;
    protected List<Operation> fixedOperations = new ArrayList<Operation>();
    protected List<Operation> allOperations = new ArrayList<Operation>();

    public void setRequest(SchedulerRequest request) {
        this.request = request;
    }

    public Solution sampling(Region region) {
        return this.doSampling(region);
    }

    protected Solution doSampling(Region region) {
        this.solution = region.toSolution();
        
        this.fixedOperations = region.getAllFixedOperations();
        this.allOperations = solution.getAllOperations();

        init();

        while (omega.size() > 0) {
            Operation toBeAssigned = getRandomOperationFromOmega();

            if (fixedOperations.contains(toBeAssigned)) {
            	omega.addAll(solution.refreshF(toBeAssigned));

            } else {
                solution.addNonFixedOperation(toBeAssigned);
            }

            for(Operation operation : toBeAssigned.getNextOperations()) {
            		if (solution.getAllBlockedOperations().contains(operation)) {
            			omega.addAll(solution.refreshF(operation));
            		} else if (!solution.getAllAssignedOperations().contains(operation)) {
            			this.addToOmega(operation);
            		}
            }
        }
        
        if (allOperations.size() > 0) {
            return null;
        }

        return this.solution;
    }

    protected void init() {
    	omega.addAll(solution.initF());

        for(Operation operation : request.getAllOperations()) {
            if(!operation.hasDependencies() && !solution.getAllBlockedOperations().contains(operation)) {
            	omega.add(operation);
            }
        }
    }

    protected  Operation getRandomOperationFromOmega() {
        if(omega.size() > 0) {
            List<Operation> omegaList = new ArrayList<Operation>();
            omegaList.addAll(omega);

            Operation result = omegaList.get(RandomUtil.randomInt(omegaList.size()));
            omega.remove(result);
            allOperations.remove(result);

            return result;
        }

        return null;
    }
    
    protected void addToOmega(Collection<Operation> operations) {
    	for (Operation oper : operations) {
    		this.addToOmega(oper);
    	}
    }
    
    protected void addToOmega(Operation oper) {
    	for (Operation depdOperation : oper.getDependencies()) {
    		if (omega.contains(depdOperation)) {
    			return ;
    		}
    	}
    	
    	omega.add(oper);
    }
    
    protected void printOmega() {
    	System.out.println("====================================>");
    	for (Operation o : omega) {
    		System.out.print(o.getId() + ",");
    	}
    	System.out.println();
    }
    
}
