package ime.contrib.np.model;

import java.util.*;

public class SchedulerRequest {
    protected List<Machine> machines = new ArrayList<Machine>();
    protected List<Job> jobs = new ArrayList<Job>();

    public void addMachine(Machine machine) {
        this.machines.add(machine);
    }

    public void addJob(Job job) {
        this.jobs.add(job);
    }

    public List<Machine> getMachines() {
        return this.machines;
    }

    public List<Operation> getAllOperations() {
        List<Operation> result = new ArrayList<Operation>();
        for (Job job : jobs) {
            result.addAll(job.getOperations());
        }

        return result;
    }
}
