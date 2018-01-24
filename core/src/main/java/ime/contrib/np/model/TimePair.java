package ime.contrib.np.model;

public class TimePair {
    protected double startTime;
    protected Operation operation;

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public double getEndTime(Machine m) {
        return startTime + operation.getProcessTime(m);
    }
}
