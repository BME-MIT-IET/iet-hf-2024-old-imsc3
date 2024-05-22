package model;

import Model.Pump;
import Model.Pipe;
import Model.PickupAble;
import Controller.Controller;
import Model.Steppable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static junit.framework.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void testGetController() {
        assertEquals(mockController, pump.getController());
    }

    @Test
    void testSetController() {
        Controller newController = Mockito.mock(Controller.class);
        pump.setController(newController);
        assertEquals(newController, pump.getController());
    }

    @Test
    void testPickedUp() {
        Steppable mockSteppable = Mockito.mock(Steppable.class);
        assertThrows(UnsupportedOperationException.class, () -> pump.pickedUp(mockSteppable));
    }

    @Test
    void testPlacedDown() {
        Steppable mockSteppable = Mockito.mock(Steppable.class);
        when(mockSteppable.placedDownTo(any(Pump.class))).thenReturn(true);

        boolean result = pump.placedDown(mockSteppable);

        assertTrue(result);
        verify(mockSteppable).placedDownTo(pump);
    }

    @Test
    void testPickedUpFrom() {
        pump.addPipe(mockActiveIn);

        boolean result = pump.pickedUpFrom(mockActiveIn);

        assertTrue(result);
        assertFalse(pump.getPipes().contains(mockActiveIn));
    }

    @Test
    void testPickedUpFromNonExistingPipe() {
        PickupAble mockPickup = Mockito.mock(PickupAble.class);

        boolean result = pump.pickedUpFrom(mockPickup);

        assertFalse(result);
    }

    @Test
    void testPlacedDownToPipe() {
        Pipe mockPipe = Mockito.mock(Pipe.class);
        when(mockPipe.addWaterNode(any(Pump.class))).thenReturn(true);

        boolean result = pump.placedDownTo(mockPipe);

        assertTrue(result);
        assertTrue(pump.getPipes().contains(mockPipe));
        verify(mockPipe).addWaterNode(pump);
    }

    @Test
    void testPlacedDownToPump() {
        Pump mockPump = Mockito.mock(Pump.class);

        boolean result = pump.placedDownTo(mockPump);

        assertFalse(result);
    }

    @Test
    void testWaterFlow() {
        // Arrange
        pump.setHeldWater(10);
        when(mockActiveOut.gainWater(anyInt())).thenReturn(1);
        when(mockActiveIn.loseWater(anyInt())).thenReturn(1);
        when(mockController.getObjectName(any())).thenReturn("Pipe");

        // Act
        pump.waterFlow();

        // Assert
        assertEquals(10, pump.getHeldWater());
        verify(mockActiveOut).gainWater(1);
        verify(mockActiveIn).loseWater(1);
        verify(mockActiveIn).gainWater(0);
    }

    @Test
    void testWaterFlowWhenBroken() {
        // Arrange
        pump.setBroken(true);

        // Act
        pump.waterFlow();

        // Assert
        verify(mockActiveOut, never()).gainWater(1);
        assertEquals(0, pump.getHeldWater());
    }

    @Test
    void testWaterFlowWhenEmpty() {
        // Arrange
        pump.setHeldWater(0);

        // Act
        pump.waterFlow();

        // Assert
        verify(mockActiveOut, never()).gainWater(anyInt());
        verify(mockActiveIn).loseWater(1);
    }

    @Test
    void testWaterFlow_WithMaxCapacity() {
        // Arrange
        pump.setHeldWater(20);
        when(mockActiveOut.gainWater(anyInt())).thenReturn(1);
        when(mockActiveIn.loseWater(anyInt())).thenReturn(1);

        // Act
        pump.waterFlow();

        // Assert
        assertEquals(20, pump.getHeldWater());
        verify(mockActiveOut).gainWater(1);
        verify(mockActiveIn).loseWater(1);
        verify(mockActiveIn).gainWater(0);
    }

    @Test
    void testAddPipe() {
        Pipe mockPipe = Mockito.mock(Pipe.class);

        assertTrue(pump.addPipe(mockPipe));
        assertTrue(pump.getPipes().contains(mockPipe));
    }

    @Test
    void testAddPipe_MaximumPipesReached() {
        for (int i = 0; i < 10; i++) {
            pump.addPipe(Mockito.mock(Pipe.class));
        }
        Pipe mockPipe = Mockito.mock(Pipe.class);

        assertFalse(pump.addPipe(mockPipe));
        assertFalse(pump.getPipes().contains(mockPipe)); 
    }

}