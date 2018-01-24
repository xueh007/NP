package ime.contrib.np.model;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class SolutionTest {
    @Test
    public void testAddOperation() {
        Solution solution = new Solution();

        Machine m1 = new Machine();
        Machine m2 = new Machine();

        Operation o11 = new Operation("o11");
        Operation o12 = new Operation("o12");
        Operation o21 = new Operation("o21");

        solution.addSolution(m1, o11);
        solution.addSolution(m1, o12);
        solution.addSolution(m2, o21);

        List<IntermediaMachine> actual = solution.getIntermediaMachines();

        Assert.assertEquals(actual.size(), 2);

        Assert.assertEquals(actual.get(0).getMachine(), m1);
        Assert.assertEquals(actual.get(0).getFixedOperations().size(), 2);
        Assert.assertTrue(actual.get(0).getFixedOperations().contains(o11));
        Assert.assertTrue(actual.get(0).getFixedOperations().contains(o12));

        Assert.assertEquals(actual.get(1).getMachine(), m2);
        Assert.assertEquals(actual.get(1).getFixedOperations().size(), 1);
        Assert.assertTrue(actual.get(1).getFixedOperations().contains(o21));
    }

    @Test
    public void testGetIntermediaMachine() {
        Solution solution = new Solution();

        Machine m1 = new Machine();
        Machine m2 = new Machine();

        Operation o11 = new Operation("o11");
        Operation o12 = new Operation("o12");
        Operation o21 = new Operation("o21");

        o11.addNextOperation(o12);
        o12.addPreOperation(o11);

        solution.addSolution(m1, o11);
        solution.addSolution(m1, o12);
        solution.addSolution(m2, o21);

        IntermediaMachine actual = solution.getIntermediaMachine(m1);

        Assert.assertEquals(actual.getFixedOperations().size(), 2);
        Assert.assertTrue(actual.getFixedOperations().contains(o11));
        Assert.assertTrue(actual.getFixedOperations().contains(o12));

        actual = solution.getIntermediaMachine(m2);

        Assert.assertEquals(actual.getMachine(), m2);
        Assert.assertEquals(actual.getFixedOperations().size(), 1);
        Assert.assertTrue(actual.getFixedOperations().contains(o21));
    }

    @Test
    public void testInitTimePair() {
        Solution solution = new Solution();

        Operation o11 = new Operation("o11");
        Operation o12 = new Operation("o12");
        Operation o13 = new Operation("o13");
        Operation o21 = new Operation("o21");
        Operation o22 = new Operation("o22");

        o11.addNextOperation(o12);
        o12.addPreOperation(o11);
        o12.addNextOperation(o13);
        o13.addPreOperation(o12);

        o21.addNextOperation(o22);
        o22.addPreOperation(o21);

        Machine m1 = new Machine();
        Machine m2 = new Machine();

        solution.addSolution(m1, o11);
        solution.addSolution(m1, o22);
        solution.addSolution(m1, o12);

        solution.addSolution(m2, o21);
        solution.addSolution(m2, o13);

        o11.addProcessTime(m1, 1);
        o12.addProcessTime(m1, 0.5);
        o13.addProcessTime(m2, 2);
        o21.addProcessTime(m2, 1.5);
        o22.addProcessTime(m1, 2);

        solution.initTimePair();

        Assert.assertEquals(solution.getOperationEndTime(o11), 1.0);
        Assert.assertEquals(solution.getOperationEndTime(o12), 4.0);
        Assert.assertEquals(solution.getOperationEndTime(o13), 6.0);
        Assert.assertEquals(solution.getOperationEndTime(o21), 1.5);
        Assert.assertEquals(solution.getOperationEndTime(o22), 3.5);
    }

    @Test
    public void testCalMakeSpanTime() {
        Solution solution = new Solution();

        Operation o11 = new Operation("o11");
        Operation o12 = new Operation("o12");
        Operation o13 = new Operation("o13");
        Operation o21 = new Operation("o21");
        Operation o22 = new Operation("o22");

        o11.addNextOperation(o12);
        o12.addPreOperation(o11);
        o12.addNextOperation(o13);
        o13.addPreOperation(o12);

        o21.addNextOperation(o22);
        o22.addPreOperation(o21);

        Machine m1 = new Machine();
        Machine m2 = new Machine();

        solution.addSolution(m1, o11);
        solution.addSolution(m1, o22);
        solution.addSolution(m1, o12);

        solution.addSolution(m2, o21);
        solution.addSolution(m2, o13);

        o11.addProcessTime(m1, 1);
        o12.addProcessTime(m1, 0.5);
        o13.addProcessTime(m2, 2);
        o21.addProcessTime(m2, 1.5);
        o22.addProcessTime(m1, 2);

        solution.initTimePair();

        Assert.assertEquals(solution.calMakeSpanTime(), 6.0);
    }

    @Test
    public void testCalTotalEngine() {
        Solution solution = new Solution();

        Operation o11 = new Operation("o11");
        Operation o12 = new Operation("o12");
        Operation o13 = new Operation("o13");

        Operation o21 = new Operation("o21");
        Operation o22 = new Operation("o22");

        o11.addNextOperation(o12);
        o12.addPreOperation(o11);
        o12.addNextOperation(o13);
        o13.addPreOperation(o12);

        o21.addNextOperation(o22);
        o22.addPreOperation(o21);

        Machine m1 = new Machine();
        m1.setPower(2);
        Machine m2 = new Machine();
        m2.setPower(4);

        solution.addSolution(m1, o11);
        solution.addSolution(m1, o22);
        solution.addSolution(m1, o12);

        solution.addSolution(m2, o21);
        solution.addSolution(m2, o13);

        o11.addProcessTime(m1, 1);
        o12.addProcessTime(m1, 0.5);
        o13.addProcessTime(m2, 2);
        o21.addProcessTime(m2, 1.5);
        o22.addProcessTime(m1, 2);

        o11.addEnergyConsumptionMap(m1, 2);
        o12.addEnergyConsumptionMap(m1, 3);
        o13.addEnergyConsumptionMap(m2, 4);
        o21.addEnergyConsumptionMap(m2, 5);
        o22.addEnergyConsumptionMap(m1, 6);

        solution.initTimePair();

        Assert.assertEquals(solution.calTotalEnergy(), 31.0);
    }

}
