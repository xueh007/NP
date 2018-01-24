package ime.contrib.np.model;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class IntermediaMachineTest {

    IntermediaMachine machine;

    @BeforeMethod
    public void setup() {
        Machine m1 = new Machine();
        machine = new IntermediaMachine(m1);
    }

    @Test
    public void testInitF() {
        Operation o11 = new Operation("o11");
        Operation o21 = new Operation("o21");
        Operation o22 = new Operation("o22");

        o21.addNextOperation(o22);
        o22.addPreOperation(o21);

        machine.addFixedOperation(o11);
        machine.addFixedOperation(o21);
        machine.addFixedOperation(o22);

        List<Operation> expect = new ArrayList<Operation>();
        expect.add(o11);
        expect.add(o21);

        List<Operation> actual = machine.initF();

        Assert.assertEquals(actual, expect);
        Assert.assertEquals(machine.fIndex, 2);
//
//        Operation o23 = new Operation("o23");
//        o23.addPreOperation(o22);
//
//        expect.clear();
//        expect.add(o22);
//
//        actual = machine.initF();
//
//        Assert.assertEquals(actual, expect);
//        Assert.assertEquals(machine.fIndex, 3);

    }

    @Test
    public void testGetBlockedOperations() {
        Operation o11 = new Operation("o11");
        Operation o12 = new Operation("o12");
        Operation o21 = new Operation("o21");
        Operation o22 = new Operation("o22");

        machine.addFixedOperation(o11);
        machine.addFixedOperation(o12);
        machine.addFixedOperation(o21);
        machine.addFixedOperation(o22);

        machine.fIndex = 1;

        List<Operation> expect = new ArrayList<Operation>();
        expect.add(o21);
        expect.add(o22);

        Assert.assertEquals(machine.getBlockedOperations(), expect);
    }
}
