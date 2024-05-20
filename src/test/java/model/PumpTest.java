package model;

import Model.Pump;
import Model.Pipe;
import Controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class PumpTest {

    private Pump pump;
    private Pipe mockActiveIn;
    private Pipe mockActiveOut;
    private Controller mockController;

    @BeforeEach
    public void init() {

        mockActiveIn = Mockito.mock(Pipe.class);
        mockActiveOut = Mockito.mock(Pipe.class);
        mockController = Mockito.mock(Controller.class);
        pump = new Pump(mockController);

        pump.setActiveIn(mockActiveIn);
        pump.setActiveOut(mockActiveOut);
    }

    @Test
    void testWaterFlow() {
        // Arrange
        pump.setHeldWater(10);
        when(mockActiveOut.GainWater(anyInt())).thenReturn(1);
        when(mockActiveIn.LoseWater(anyInt())).thenReturn(1);
        when(mockController.getObjectName(any())).thenReturn("Pipe");

        // Act
        pump.WaterFlow();

        // Assert
        assertEquals(10, pump.getHeldWater());
        verify(mockActiveOut).GainWater(1);
        verify(mockActiveIn).LoseWater(1);
        verify(mockActiveIn).GainWater(0);
    }

    @Test
    void testWaterFlowWhenBroken() {
        // Arrange
        pump.setBroken(true);

        // Act
        pump.WaterFlow();

        // Assert
        verify(mockActiveOut, never()).GainWater(1);
        assertEquals(0, pump.getHeldWater());
    }

    @Test
    void testWaterFlowWhenEmpty() {
        // Arrange
        pump.setHeldWater(0);

        // Act
        pump.WaterFlow();

        // Assert
        verify(mockActiveOut, never()).GainWater(anyInt());
        verify(mockActiveIn).LoseWater(1);
    }
}