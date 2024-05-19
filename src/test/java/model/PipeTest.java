package model;

import Model.IO_Manager;
import Model.Pipe;
import Model.PointCounter;
import Model.WaterNode;
import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static junit.framework.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
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
        pipe.GainWater(10);
        verifyNoInteractions(pointCounter);
    }

    @Test
    public void gainWaterTestWhenPipeIsBroken() {
        pipe.setBroken(true);
        pipe.GainWater(10);

        verify(pointCounter).AddSaboteurPoints(10);
    }

    @Test
    public void loseWaterTest() {
        pipe.setHeldWater(20);
        pipe.LoseWater(12);

        assertEquals(8, pipe.getHeldWater());

        pipe.LoseWater(10);

        assertEquals(0, pipe.getHeldWater());
    }

    @Test
    public void AddWaterNodeShouldOnlySucceedIfPipeHasFreeNode() {
        assertTrue(pipe.AddWaterNode(mock(WaterNode.class)));
        assertTrue(pipe.AddWaterNode(mock(WaterNode.class)));
        assertFalse(pipe.AddWaterNode(mock(WaterNode.class)));
    }

    @Test
    public void repairedTestWhenBroken() {
        pipe.setBroken(true);

        pipe.Repaired();

        assertFalse(pipe.isBroken());
    }


}
