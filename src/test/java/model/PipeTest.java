package model;

import Model.Pipe;
import Model.PointCounter;
import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class PipeTest {

    private Pipe pipe;

    private PointCounter pointCounter;

    @BeforeEach
    public void init(){
        pointCounter = mock(PointCounter.class);
        this.pipe = new Pipe(pointCounter);
    }
    @Test
    public void constructorTest() {
        Assert.assertNotNull(pipe);
    }

    @Test
    public void gainWaterTest() {
        var res = pipe.GainWater(10);

        verify(pointCounter, never()).AddSaboteurPoints(anyInt());
        assertEquals(1, res);

    }
}
