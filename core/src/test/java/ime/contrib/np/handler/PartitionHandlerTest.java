package ime.contrib.np.handler;


import ime.contrib.np.model.Machine;
import ime.contrib.np.model.Operation;
import ime.contrib.np.model.Region;
import ime.contrib.np.model.SchedulerRequest;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.lang.management.OperatingSystemMXBean;
import java.util.ArrayList;
import java.util.List;

public class PartitionHandlerTest {
    PartitionHandler handler;

    @BeforeTest
    public void setup() {
        handler = new PartitionHandler();
    }

    @Test
    public void testGetNextPartitionMachine() {
        Machine m1 = new Machine();
        Machine m2 = new Machine();
        Machine m3 = new Machine();

        SchedulerRequest request = new SchedulerRequest();

        request.addMachine(m1);
        request.addMachine(m2);
        request.addMachine(m3);

        handler.request = request;

        Assert.assertEquals(handler.getNextPartitionMachine(m1), m2);
        Assert.assertEquals(handler.getNextPartitionMachine(m2), m3);
        Assert.assertEquals(handler.getNextPartitionMachine(m3), m1);
    }

    @Test
    public void testCalToBeAssignedOperations() {
        Operation o11 = new Operation("o11");
        Operation o12 = new Operation("o12");
        Operation o13 = new Operation("o13");
        Operation o21 = new Operation("o21");

        o11.addNextOperation(o12);

        o12.addPreOperation(o11);
        o12.addNextOperation(o13);

        o13.addPreOperation(o12);

        List<Operation> possibleOperations = new ArrayList<Operation>();

        possibleOperations.add(o11);
        possibleOperations.add(o12);
        possibleOperations.add(o13);
        possibleOperations.add(o21);

        List<Operation> fixedOperations = new ArrayList<Operation>();

        fixedOperations.add(o12);

        List<Operation> expect = new ArrayList<Operation>();

        expect.add(o11);
        expect.add(o21);

        Assert.assertEquals(handler.calToBeAssignedOperations(possibleOperations, fixedOperations), expect);
    }

    @Test
    public void testDoPartition() {
        Machine m1 = new Machine();
        Machine m2 = new Machine();
        Machine m3 = new Machine();

        Operation o1 = new Operation("o1");
        Operation o2 = new Operation("o2");
        Operation o3 = new Operation("o3");
        Operation o4 = new Operation("o4");

        o1.addNextOperation(o2);
        o2.addPreOperation(o1);
        o2.addNextOperation(o3);
        o3.addPreOperation(o2);

        m3.addPossibleOperation(o2);
        m3.addPossibleOperation(o3);
        m3.addPossibleOperation(o4);

        SchedulerRequest request = new SchedulerRequest();
        request.addMachine(m1);
        request.addMachine(m2);
        request.addMachine(m3);

        Region root = new Region();
        Region r1 = new Region();

        r1.setParent(root);
        r1.setMachine(m1);
        r1.setOperation(o1);

        handler.request = request;

        List<Region> actual = handler.doPartition(r1);

        Assert.assertEquals(actual.size(), 2);

        Assert.assertEquals(actual.get(0).getParent(), r1);
        Assert.assertEquals(actual.get(0).getMachine(), m3);
        Assert.assertEquals(actual.get(0).getOperation(), o2);

        Assert.assertEquals(actual.get(1).getParent(), r1);
        Assert.assertEquals(actual.get(1).getMachine(), m3);
        Assert.assertEquals(actual.get(1).getOperation(), o4);
    }
}
