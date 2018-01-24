package ime.contrib.np.model;

import java.util.List;

public class SchedulerResponse {
    protected List<Solution> solutions;
    protected long executionTime;

    public List<Solution> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<Solution> solutions) {
        this.solutions = solutions;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }
}
