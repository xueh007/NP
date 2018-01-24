package ime.contrib.np.handler;

import ime.contrib.np.model.Machine;
import ime.contrib.np.model.Operation;
import ime.contrib.np.model.Region;
import ime.contrib.np.model.SchedulerRequest;

import java.util.ArrayList;
import java.util.List;

public class PartitionHandler {
    protected SchedulerRequest request;

    public void setRequest(SchedulerRequest request) {
        this.request = request;
    }

    public List<Region> partition(Region region) {
        return this.doPartition(region);
    }

    protected List<Region> doPartition(Region region) {
        Machine m = region.getMachine();
        if (m == null) {
            assert request.getMachines().size() > 0;
            m = request.getMachines().get(0);
        }
        List<Operation> toBeAssignedOperations;
        List<Region> partitionRegions = new ArrayList<Region>();

        do {
            m = getNextPartitionMachine(m);
            toBeAssignedOperations = calToBeAssignedOperations(m.getPossibleOperations(), region.getAllFixedOperations());
        } while(toBeAssignedOperations.size() == 0);

        for (Operation o : toBeAssignedOperations) {
            Region r = new Region();
            r.setMachine(m);
            r.setOperation(o);
            r.setParent(region);
            region.addNode(r);
            partitionRegions.add(r);
        }

        return partitionRegions;
    }

    protected Machine getNextPartitionMachine(Machine machine) {
        List<Machine> machines = request.getMachines();

        for(int i = 0; i < machines.size(); i++) {
            if (machines.get(i) == machine) {
                return i == machines.size() - 1 ? machines.get(0) : machines.get(i + 1);
            }
        }

        return null;
    }

    protected List<Operation> calToBeAssignedOperations(List<Operation> possibleOperations, List<Operation> fixedOperations) {
        List<Operation> results = new ArrayList<Operation>();
        results.addAll(possibleOperations);

        for(Operation o : possibleOperations) {
            if (fixedOperations.contains(o)) {
                results.remove(o);
            }
        }

        for(Operation o : possibleOperations) {
            for(Operation dependency : o.getDependencies()) {
                if (results.contains(dependency)) {
                    results.remove(o);
                    break;
                }
            }
        }

        return results;
    }


}
