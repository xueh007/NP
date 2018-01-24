package ime.contrib.np.model;

import java.util.ArrayList;
import java.util.List;

public class Job {
    protected String id;
    protected List<Operation> operations = new ArrayList<Operation>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Operation> getOperations() {
        return this.operations;
    }

    public void addOperation(Operation operation) {
    	operation.setJob(this);
        this.operations.add(operation);
    }
}
