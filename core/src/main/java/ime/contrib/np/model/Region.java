package ime.contrib.np.model;

import java.util.ArrayList;
import java.util.List;

public class Region {

    protected Machine machine;
    protected Operation operation;

    protected Region parentNode;
    protected List<Region> subNodes = new ArrayList<Region>();

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void setParent(Region parent) {
        this.parentNode = parent;
    }

    public void addNode(Region node) {
        this.subNodes.add(node);
    }

    public Region getParent() {
        return parentNode;
    }

    public List<Region> getSubRegions() {
        return subNodes;
    }

    public List<Region> getSurroundingRegion() {
        Region parent = this.getParent();
        List<Region> surroundingRegions = new ArrayList<Region>();
        if (parent != null) {
            surroundingRegions = parent.getSubRegions();
            surroundingRegions.remove(this);
        }
        return surroundingRegions;
    }

    public List<Operation> getFixedOperations(Machine m) {
        List<Operation> results;

        if (this.getParent() != null) {
            results = this.getParent().getFixedOperations(m);
        } else {
            results = new ArrayList<Operation>();
        }

        if(this.machine == m) {
            results.add(this.operation);
        }

        return results;
    }

    public List<Operation> getAllFixedOperations() {
        List<Operation> results;

        if (this.getParent() != null) {
            results = this.getParent().getAllFixedOperations();
        } else {
            results = new ArrayList<Operation>();
        }

        if (operation != null) {
            results.add(this.operation);
        }

        return results;
    }

    public Solution toSolution() {
        Solution solution;

        if(this.getParent() != null) {
            solution = this.getParent().toSolution();
        } else {
            solution = new Solution();
        }

        if(machine != null && operation != null) {
            solution.addSolution(machine, operation);
        }

        return solution;
    }
}
