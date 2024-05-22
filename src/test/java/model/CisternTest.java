package model;

import Controller.Controller;
import Model.Cistern;
import Model.PointCounter;
import Model.Pipe;
import Model.Mechanic;
import Model.Pump;
import View.GameView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.security.SecureRandom;
import java.util.LinkedList;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CisternTest {

    private Cistern cistern;
    private Controller mockController;
    private PointCounter mockPointCounter;
    private GameView mockGameView;

    @BeforeEach
    public void setUp() {
        mockController = mock(Controller.class);
        mockPointCounter = mock(PointCounter.class);
        mockGameView = mock(GameView.class);

        cistern = new Cistern(mockController, mockPointCounter);
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
        Pipe mockPipe = mock(Pipe.class);

        assertTrue(cistern.placedDownTo(mockPipe));
        assertTrue(cistern.getPipes().contains(mockPipe));
        verify(mockPipe).addWaterNode(cistern);
    }

    @Test
    public void testWaterFlow() {
        Pipe mockPipe = mock(Pipe.class);
        cistern.getPipes().add(mockPipe);
        when(mockPipe.loseWater(1)).thenReturn(1);

        cistern.waterFlow();

        verify(mockPipe, times(1)).loseWater(1);
        verify(mockPointCounter, times(1)).addMechanicPoints(1);
    }

}

