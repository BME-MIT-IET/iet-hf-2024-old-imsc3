package model;

import Controller.Controller;
import Model.Cistern;
import Model.PointCounter;
import Model.Pipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

public class CisternTest {

    private Cistern cistern;
    private Controller mockController;
    private PointCounter mockPointCounter;
    private Pipe mockPipe;

    @BeforeEach
    public void init() {
        mockController = mock(Controller.class);
        mockPointCounter = mock(PointCounter.class);
        mockPipe= mock(Pipe.class);

        cistern = new Cistern(mockController, mockPointCounter);
        cistern.addPipe(mockPipe);
    }

    @Test
    void testGetController() {
        assertEquals(mockController, cistern.getController());
    }

    @Test
    void testSetController() {
        Controller newController = Mockito.mock(Controller.class);
        cistern.setController(newController);
        assertEquals(newController, cistern.getController());
    }

    @Test
    public void testPickedUpFrom() {
        Pipe mockPipe = mock(Pipe.class);
        cistern.getCreatedPickupables().add(mockPipe);

        assertTrue(cistern.pickedUpFrom(mockPipe));
        assertFalse(cistern.getCreatedPickupables().contains(mockPipe));
    }

    @Test
    public void testPlacedDownToPipe() {
        assertTrue(cistern.placedDownTo(mockPipe));
        assertTrue(cistern.getPipes().contains(mockPipe));
        verify(mockPipe).addWaterNode(cistern);
    }

    @Test
    public void testWaterFlow() {
        when(mockPipe.loseWater(anyInt())).thenReturn(1);

        cistern.waterFlow();

        verify(mockPipe).loseWater(1);
        verify(mockPointCounter).addMechanicPoints(1);
    }

}

