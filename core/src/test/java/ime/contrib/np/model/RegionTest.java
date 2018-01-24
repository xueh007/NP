package ime.contrib.np.model;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class RegionTest {
    Region root = new Region();

    Region r1 = new Region();
    Region r2 = new Region();
    Region r3 = new Region();

    Region r11 = new Region();
    Region r12 = new Region();

    @BeforeTest
    public void setup() {
        root.addNode(r1);
        root.addNode(r2);
        root.addNode(r3);

        r1.setParent(root);
        r1.addNode(r11);
        r1.addNode(r12);

        r2.setParent(root);

        r3.setParent(root);

        r11.setParent(r1);
        r12.setParent(r1);
    }

    @Test
    public void testGetParent() {
        Assert.assertEquals(r1.getParent(), root);
        Assert.assertEquals(r2.getParent(), root);
        Assert.assertEquals(r3.getParent(), root);

        Assert.assertEquals(r11.getParent(), r1);
        Assert.assertEquals(r12.getParent(), r1);
    }

    @Test
    public void testGetSubRegions() {
        List<Region> nodes = new ArrayList<Region>();
        nodes.add(r1);
        nodes.add(r2);
        nodes.add(r3);

        Assert.assertEquals(root.getSubRegions(), nodes);

        nodes.clear();
        nodes.add(r11);
        nodes.add(r12);

        Assert.assertEquals(r1.getSubRegions(), nodes);
    }

    @Test
    public void testGetSurroundingRegion() {
        List<Region> nodes = new ArrayList<Region>();
        nodes.add(r2);
        nodes.add(r3);

        Assert.assertEquals(r1.getSurroundingRegion(), nodes);

        nodes.clear();
        nodes.add(r12);
        Assert.assertEquals(r11.getSurroundingRegion(), nodes);
    }

    @Test
    public void testGetFixedOperations() {
        Machine m1 = new Machine();
        Operation o11 = new Operation("o11");
        Operation o12 = new Operation("o12");

        Machine m2 = new Machine();
        Operation o21 = new Operation("o21");

        r1.machine = m1;
        r1.operation = o11;

        r11.machine = m1;
        r11.operation = o12;

        r12.machine = m2;
        r12.operation = o21;

        List<Operation> expect = new ArrayList<Operation>();
        expect.add(o11);
        expect.add(o12);

        Assert.assertEquals(r11.getFixedOperations(m1), expect);

        expect.clear();
        expect.add(o21);

        Assert.assertEquals(r12.getFixedOperations(m2), expect);

        expect.clear();
        expect.add(o11);

        Assert.assertEquals(r12.getFixedOperations(m1), expect);
    }

    @Test
    public void testGetAllFixedOperations() {
        Machine m1 = new Machine();
        Operation o11 = new Operation("o11");
        Operation o12 = new Operation("o12");

        Machine m2 = new Machine();
        Operation o21 = new Operation("o21");

        r1.machine = m1;
        r1.operation = o11;

        r11.machine = m1;
        r11.operation = o12;

        r12.machine = m2;
        r12.operation = o21;

        List<Operation> expect = new ArrayList<Operation>();
        expect.add(o11);
        expect.add(o12);

        Assert.assertEquals(r11.getAllFixedOperations(), expect);

        expect.clear();
        expect.add(o11);
        expect.add(o21);

        Assert.assertEquals(r12.getAllFixedOperations(), expect);
    }

    @Test
    public void testToSolution() {
        Machine m1 = new Machine();

        Operation o1 = new Operation("o1");
        Operation o2 = new Operation("o2");

        r1.setMachine(m1);
        r1.setOperation(o1);

        r11.setMachine(m1);
        r11.setOperation(o2);

        List<IntermediaMachine> actual = r11.toSolution().getIntermediaMachines();

        Assert.assertEquals(actual.size(), 1);

        Assert.assertEquals(actual.get(0).getMachine(), m1);

        Assert.assertEquals(actual.get(0).getFixedOperations().size(), 2);
        Assert.assertTrue(actual.get(0).getFixedOperations().contains(o1));
        Assert.assertTrue(actual.get(0).getFixedOperations().contains(o2));
    }
}
