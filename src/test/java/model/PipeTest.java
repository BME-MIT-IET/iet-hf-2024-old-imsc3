package model;

import Model.Pipe;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;

public class PipeTest {

    @Test
    public void constructorTest() {
        Pipe pipe = new Pipe();
        Assert.assertNotNull(pipe);
    }
}
