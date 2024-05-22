package model;

import Model.Pipe;
import Model.PointCounter;
import Model.WaterNode;
import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

public class PipeTest {

    private Pipe pipe;

    private PointCounter pointCounter;

    @BeforeEach
    public void init() {
        pointCounter = mock(PointCounter.class);
        this.pipe = new Pipe(pointCounter);
    }

    @Test
    public void constructorTest() {
        Assert.assertNotNull(pipe);
    }

    @Test
    public void gainWaterTestWhenPipeIsFree() {
        pipe.gainWater(10);
        verifyNoInteractions(pointCounter);
    }

    @Test
    public void gainWaterTestWhenPipeIsBroken() {
        pipe.setBroken(true);
        pipe.gainWater(10);

        verify(pointCounter).addSaboteurPoints(10);
    }

    @Test
    public void loseWaterTest() {
        pipe.setHeldWater(20);
        pipe.loseWater(12);

        assertEquals(8, pipe.getHeldWater());

        pipe.loseWater(10);

        assertEquals(0, pipe.getHeldWater());
    }

    @Test
    public void AddWaterNodeShouldOnlySucceedIfPipeHasFreeNode() {
        assertTrue(pipe.addWaterNode(mock(WaterNode.class)));
        assertTrue(pipe.addWaterNode(mock(WaterNode.class)));
        assertFalse(pipe.addWaterNode(mock(WaterNode.class)));
    }

    @Test
    public void repairedTestWhenBroken() {
        pipe.setBroken(true);

        pipe.repaired();

        assertFalse(pipe.isBroken());
    }


}
