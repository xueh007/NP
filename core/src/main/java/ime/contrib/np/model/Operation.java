package ime.contrib.np.model;

import ime.contrib.np.util.RandomUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Operation {
    protected String id;
    protected Map<Machine, Double> processTimeMap = new HashMap<Machine, Double>();
    protected Map<Machine, Double> energyConsumptionMap = new HashMap<Machine, Double>();

    protected List<Operation> preOperations = new ArrayList<Operation>();
    protected List<Operation> nextOperations = new ArrayList<Operation>();

    protected List<Machine> possibleMachines = new ArrayList<Machine>();
    
    protected Job job;

    public Operation(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public double getProcessTime(Machine m) {
        return processTimeMap.get(m);
    }

    public double getEnergyConsumption(Machine m) {
        return energyConsumptionMap.get(m);
    }

    public void addProcessTime(Machine m, double t) {
        this.processTimeMap.put(m, t);
    }

    public void addEnergyConsumptionMap(Machine m, double e) {
        this.energyConsumptionMap.put(m, e);
    }

    public List<Operation> getPreOperations() {
        return this.preOperations;
    }

    public List<Operation> getNextOperations() {
        return this.nextOperations;
    }

    public void addPreOperation(Operation operation) {
        this.preOperations.add(operation);
    }

    public void addNextOperation(Operation operation) {
        this.nextOperations.add(operation);
    }

    public Collection<Operation> getDependencies() {
        Set<Operation> dependencies = new HashSet<Operation>();

        dependencies.addAll(this.getPreOperations());
        for(Operation preOperation : getPreOperations()) {
            dependencies.addAll(preOperation.getDependencies());
        }

        return dependencies;
    }

    public boolean hasDependencies() {
        return preOperations.size() > 0 ? true : false;
    }

    public Machine getRandomMachine() {
        if (possibleMachines.size() > 0) {
            return possibleMachines.get(RandomUtil.randomInt(possibleMachines.size()));
        }
        return null;
    }

    public void addPossibleMachine(Machine m) {
        this.possibleMachines.add(m);
    }

    public List<Machine> getPossibleMachines() {
        return this.possibleMachines;
    }
    
    public void setJob(Job job) {
    	this.job = job;
    }
    
    public Job getJob() {
    	return this.job;
    }
}
