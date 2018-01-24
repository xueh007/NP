package ime.contrib.np.model;

import java.util.ArrayList;
import java.util.List;

public class Machine {
    protected String id;
    protected double power;

    protected List<Operation> possibleOperations = new ArrayList<Operation>();

    public List<Operation> getPossibleOperations() {
        return this.possibleOperations;
    }

    public void addPossibleOperation(Operation o) {
        this.possibleOperations.add(o);
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
