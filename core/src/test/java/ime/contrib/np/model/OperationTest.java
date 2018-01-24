package ime.contrib.np.model;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class OperationTest {

    Operation o1;
    Operation o2;
    Operation o3;
    Operation o4;

    @BeforeTest
    public void setup() {
        o1 = new Operation("O1");
        o2 = new Operation("O2");
        o3 = new Operation("O3");
        o4 = new Operation("O4");

        o1.addNextOperation(o2);
        o1.addNextOperation(o3);

        o2.addPreOperation(o1);
        o2.addNextOperation(o4);

        o3.addPreOperation(o1);
        o3.addNextOperation(o4);

        o4.addPreOperation(o2);
        o4.addPreOperation(o3);
    }

    @Test
    public void testGetPrevOperations() {
        List<Operation> o4Previous = new ArrayList<Operation>();
        o4Previous.add(o2);
        o4Previous.add(o3);

        Assert.assertEquals(o4.getPreOperations(), o4Previous);
    }

    @Test
    public void testGetNextOperations() {
        List<Operation> o1Next = new ArrayList<Operation>();
        o1Next.add(o2);
        o1Next.add(o3);

        Assert.assertEquals(o1.getNextOperations(), o1Next);
    }

    @Test
    public void testGetDependencies() {
        Assert.assertEquals(o1.getDependencies().size(), 0);

        Assert.assertEquals(o2.getDependencies().size(), 1);
        Assert.assertTrue(o2.getDependencies().contains(o1));

        Assert.assertEquals(o3.getDependencies().size(), 1);
        Assert.assertTrue(o3.getDependencies().contains(o1));

        Assert.assertEquals(o4.getDependencies().size(), 3);
        Assert.assertTrue(o4.getDependencies().contains(o1));
        Assert.assertTrue(o4.getDependencies().contains(o2));
        Assert.assertTrue(o4.getDependencies().contains(o3));
    }

    @Test
    public void testHasDependencies() {
        Assert.assertFalse(o1.hasDependencies());
        Assert.assertTrue(o2.hasDependencies());
        Assert.assertTrue(o3.hasDependencies());
        Assert.assertTrue(o4.hasDependencies());
    }

}
