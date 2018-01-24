package ime.contrib.np.model;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    protected List<IntermediaMachine> machines = new ArrayList<IntermediaMachine>();

    public IntermediaMachine getIntermediaMachine(Machine machine) {
        for (IntermediaMachine intermediaMachine : machines) {
            if (intermediaMachine.getMachine() == machine) {
                return intermediaMachine;
            }
        }

        return null;
    }

    public List<IntermediaMachine> getIntermediaMachines() {
        return this.machines;
    }

    public void addSolution(Machine m, Operation o) {
        IntermediaMachine machine = this.getIntermediaMachine(m);
        if (machine == null) {
            machine = new IntermediaMachine(m);
            this.machines.add(machine);
        }
        machine.addFixedOperation(o);
    }

    public List<Operation> initF() {
        List<Operation> result = new ArrayList<Operation>();

        for(IntermediaMachine m : machines) {
            result.addAll(m.initF());
        }

        return result;
    }

    public List<Operation> getAllBlockedOperations() {
        List<Operation> result = new ArrayList<Operation>();

        for(IntermediaMachine m : machines) {
            result.addAll(m.getBlockedOperations());
        }

        return result;
    }

    public List<Operation> refreshF(Operation o) {
        List<Operation> result = new ArrayList<Operation>();

        for(IntermediaMachine m : machines) {
            result.addAll(m.refreshF(o, getAllAssignedOperations()));
        }

        return result;
    }

    public void addNonFixedOperation(Operation o) {
        Machine m = o.getRandomMachine();
        IntermediaMachine machine = this.getIntermediaMachine(m);
        if (machine == null) {
            machine = new IntermediaMachine(m);
            this.machines.add(machine);
        }
        machine.addNonFixedOperation(o);
    }
    
    public List<Operation> getAllAssignedOperations() {
    	List<Operation> result = new ArrayList<Operation>();
    	
    	for(IntermediaMachine m : machines) {
    		result.addAll(m.getAllAssignedOperations());
    	}
    	
    	return result;
    }

    /**
     * if there is dead-lock detected, return false
     * @return
     */
    public boolean initTimePair() {
    	
        IntermediaMachine im = this.getFirstIntermediaMachine();
        List<Operation> allOperations = this.getAllOperations();

        IntermediaMachine lastIM = im;
        while (allOperations.size() > 0) {
            Operation o = im.getNextTPOperation();
            if (o == null) {
                im = this.getNextIntermediaMachine(im);
                if (lastIM == im) {
                	return false;
                }
                continue;
            }
            
            double preOperationEndTime = im.getPreOperationEndTime();
            double dependenciesEndTime = this.getDependenciesOperationEndTime(o);

            if (preOperationEndTime == -1 || dependenciesEndTime == -1) {
                im = this.getNextIntermediaMachine(im);
                if (lastIM == im) {
                	return false;
                }
                continue;
            }
            
            TimePair tp = new TimePair();
            tp.setOperation(o);
            tp.setStartTime(preOperationEndTime > dependenciesEndTime ? preOperationEndTime : dependenciesEndTime);
            im.addTimePair(tp);
            lastIM = im;
            allOperations.remove(o);
            im = this.getNextIntermediaMachine(im);
        }
        
        return true;
    }

    protected IntermediaMachine getNextIntermediaMachine(IntermediaMachine im) {
        for (int i = 0 ; i < machines.size(); i++) {
            if(machines.get(i) == im) {
                return i + 1 < machines.size() ? machines.get(i + 1) : machines.get(0);
            }
        }
        return null;
    }

    public List<Operation> getAllOperations() {
        List<Operation> result = new ArrayList<Operation>();

        for(IntermediaMachine im : machines) {
            result.addAll(im.getAllOperations());
        }

        return result;
    }

    public IntermediaMachine getFirstIntermediaMachine() {
        assert this.machines.size() > 0;

        return this.machines.get(0);
    }

    public double getDependenciesOperationEndTime(Operation operation) {
    	double jobEndTime =  getJobOperationEndTime(operation.getJob());
    	if(operation.getDependencies().size() == 0) {
            return jobEndTime;
        }
        double endTime = -1;
        for(Operation o : operation.getPreOperations()) {
            double t = getOperationEndTime(o);
            if (t == -1) {
            	return -1;
            }
            else if (t > endTime) {
                endTime = t;
            }
        }
        
        return endTime > jobEndTime ? endTime : jobEndTime;
    }
    
    public double getJobOperationEndTime(Job job) {
    	double endTime = 0;
    	for (Operation operation : job.getOperations()) {
    		double t = getOperationEndTime(operation);
    		if (t > endTime) {
    			endTime = t;
    		}
    	}
    	
    	return endTime;
    }

    public double getOperationEndTime(Operation operation) {
        for(IntermediaMachine im : machines) {
            TimePair tp = im.getTP(operation);
            if (tp != null) {
                return tp.getEndTime(im.getMachine());
            }
        }
        return -1;
    }

    public double calTotalEnergy() {
        double engine = 0;

        for(IntermediaMachine im : machines) {
            engine += im.calTotalEnergy();
        }

        return engine;
    }

    public double calMakeSpanTime() {
        double makeSpanTime = 0;

        for(IntermediaMachine im : machines) {
            if (makeSpanTime < im.calMakeSpanTime()) {
                makeSpanTime = im.calMakeSpanTime();
            }
        }

        return makeSpanTime;
    }

    public double calIdleConsumption() {
        double engine = 0;

        for(IntermediaMachine im : machines) {
            engine += im.calIdleConsumption();
        }

        return engine;
    }

    public double calEnergyConsumption() {
        double engine = 0;

        for(IntermediaMachine im : machines) {
            engine += im.calEnergyConsumption();
        }

        return engine;
    }

    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer();

        buff.append("---------------------------------------------\n");
        for(IntermediaMachine im : machines) {
            buff.append(im.toString());
            buff.append("\n");
        }
        buff.append("---------------------------------------------\n");
        buff.append("Total Engine: ");
        buff.append(this.calTotalEnergy());
        buff.append("  |  ");
        buff.append("Make Span Time: ");
        buff.append(this.calMakeSpanTime());
        buff.append("\n=============================================");
        return buff.toString();
    }
}
