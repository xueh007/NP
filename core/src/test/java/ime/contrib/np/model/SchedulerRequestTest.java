package ime.contrib.np.model;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class SchedulerRequestTest {
    SchedulerRequest request;

    @BeforeTest
    public void setup() {
        request = new SchedulerRequest();
    }

    @Test
    public void testGetAllOperations() {
        Job job1 = new Job();
        Operation o11 = new Operation("o11");
        Operation o12 = new Operation("o12");

        job1.addOperation(o11);
        job1.addOperation(o12);

        Job job2 = new Job();
        Operation o21 = new Operation("o21");

        job2.addOperation(o21);

        List<Job> jobs = new ArrayList<Job>();
        jobs.add(job1);
        jobs.add(job2);

        request.jobs = jobs;

        List<Operation> expect = new ArrayList<Operation>();
        expect.add(o11);
        expect.add(o12);
        expect.add(o21);

        Assert.assertEquals(request.getAllOperations(), expect);
    }
}
