package ime.contrib.np.model;

import java.util.ArrayList;
import java.util.List;

public class IntermediaMachine {
    protected Machine machine;
    protected List<Operation> fixedOperations = new ArrayList<Operation>();
    protected List<Operation> nonFixedOperations = new ArrayList<Operation>();
    protected int fIndex = 0;

    protected List<TimePair> tps = new ArrayList<TimePair>();

    public IntermediaMachine(Machine m) {
        this.machine = m;
    }


    public Machine getMachine() {
        return this.machine;
    }

    public List<Operation> getFixedOperations() {
        return this.fixedOperations;
    }

    public void addFixedOperation(Operation operation) {
        this.fixedOperations.add(operation);
    }

    public void addNonFixedOperation(Operation operation) {
        this.nonFixedOperations.add(operation);
    }

    public List<Operation> getBlockedOperations() {
        List<Operation> result = new ArrayList<Operation>();
        
        if (fIndex == -1) {
        	return result;
        }

        for (int i = fIndex; i < fixedOperations.size(); i++) {
            result.add(fixedOperations.get(i));
        }

        return result;
    }

    public List<Operation> getAllOperations() {
        List<Operation> result = new ArrayList<Operation>();

        result.addAll(fixedOperations);
        result.addAll(nonFixedOperations);

        return result;
    }
    
    public List<Operation> getAllAssignedOperations() {
    	List<Operation> result = new ArrayList<Operation>();
    	
    	if (fIndex == -1) {
    		return getAllOperations();
    	}
    	
    	for (int i = 0; i < fIndex; i++) {
    		result.add(fixedOperations.get(i));
    	}
    	result.addAll(nonFixedOperations);
    	
    	return result;
    }

    public List<Operation> initF() {
        List<Operation> result = new ArrayList<Operation>();

        for (int i = 0; i < fixedOperations.size(); i++) {
            Operation o = fixedOperations.get(i);
            if (o.hasDependencies()) {
                fIndex = i;
                break;
            } 

            if (i == fixedOperations.size() - 1 ) {
            	fIndex = -1;
            }
            result.add(o);
        }
        
        return result;
    }

    public List<Operation> refreshF(Operation operation, List<Operation> allAssignedOperations) {
        List<Operation> result = new ArrayList<Operation>();

        
        if (fIndex == -1) {
        	return result;
        }
        
        if (fIndex < fixedOperations.size() && fixedOperations.get(fIndex).equals(operation)) {
            for (int i = fIndex; i < fixedOperations.size(); i++) {
                Operation o = fixedOperations.get(i);
                if (o.hasDependencies() && !allAssignedOperations.containsAll(o.getPreOperations())) {
                    fIndex = i;
                    break;
                }
                
                if (i == fixedOperations.size() - 1 ) {
                	fIndex = -1;
                }
                result.add(o);
            }
        }

        return result;
    }

    public void addTimePair(TimePair tp) {
        this.tps.add(tp);
    }

    public Operation getNextTPOperation() {
        List<Operation> allOperations = getAllOperations();

        if(tps.size() == 0) {
            return allOperations.get(0);
        }

        Operation lastTPOperation = tps.get(tps.size() - 1).getOperation();

        for(int i = 0; i < allOperations.size(); i++) {
            if (allOperations.get(i) == lastTPOperation) {
                return i + 1 < allOperations.size() ? allOperations.get(i + 1) : null;
            }
        }

        return null;
    }

    public double getPreOperationEndTime() {
        if (tps.size() == 0) {
            return 0;
        }
        else {
            return tps.get(tps.size() - 1).getEndTime(machine);
        }
    }

    public TimePair getTP(Operation operation) {
        for(TimePair tp : tps) {
            if (tp.operation == operation) {
                return tp;
            }
        }

        return null;
    }

    public double calTotalEnergy() {
        return calEnergyConsumption() + calIdleConsumption();
    }

    public double calEnergyConsumption() {
        double engine = 0;

        for(TimePair tp : tps) {
            engine += tp.getOperation().getEnergyConsumption(machine);
        }

        return engine;
    }

    public double calIdleConsumption() {
        return calIdleTime() * machine.getPower() / 60;
    }
    
    public double calIdleTime() {
    	double idleTime = calMakeSpanTime();

        for(TimePair tp : tps) {
            idleTime -= tp.getOperation().getProcessTime(machine);
        }

        return idleTime;
    }

    public double calMakeSpanTime() {
        if (tps.size() == 0) {
            return 0;
        }

        return tps.get(tps.size() - 1).getEndTime(machine);
    }

    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer();
        buff.append(this.getMachine().getId());
        buff.append(": ");
        for(Operation o : this.getAllOperations()) {
            buff.append(o.getId());
            buff.append(", ");
        }

        return buff.toString();
    }
}