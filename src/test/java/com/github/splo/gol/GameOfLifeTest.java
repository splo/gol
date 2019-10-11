package com.github.splo.gol;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class GameOfLifeTest {

    @Mock
    Strategy strategy;
    @Mock
    Consumer<Grid> gridConsumer;

    @Test
    @DisplayName("computeNextGrid calls strategy and notifies listener")
    void computeNextGrid() {
        final Grid initialGrid = Grid.newBuilder()
                .setCellState(new Coordinates(0, 0), CellState.ALIVE)
                .setCellState(new Coordinates(1, 0), CellState.DEAD)
                .setCellState(new Coordinates(2, 0), CellState.ALIVE)
                .setCellState(new Coordinates(0, 1), CellState.DEAD)
                .setCellState(new Coordinates(1, 1), CellState.ALIVE)
                .setCellState(new Coordinates(2, 1), CellState.DEAD)
                .setCellState(new Coordinates(0, 2), CellState.ALIVE)
                .setCellState(new Coordinates(1, 2), CellState.DEAD)
                .setCellState(new Coordinates(2, 2), CellState.ALIVE)
                .build();
        when(strategy.getNextCellState(eq(CellState.DEAD), anyLong())).thenReturn(CellState.ALIVE);
        when(strategy.getNextCellState(eq(CellState.ALIVE), anyLong())).thenReturn(CellState.DEAD);
        final GameOfLife gameOfLife = new GameOfLife(strategy, gridConsumer, initialGrid);

        gameOfLife.computeNextGrid();

        final Grid expectedGrid = Grid.newBuilder()
                .setCellState(new Coordinates(0, 0), CellState.DEAD)
                .setCellState(new Coordinates(1, 0), CellState.ALIVE)
                .setCellState(new Coordinates(2, 0), CellState.DEAD)
                .setCellState(new Coordinates(0, 1), CellState.ALIVE)
                .setCellState(new Coordinates(1, 1), CellState.DEAD)
                .setCellState(new Coordinates(2, 1), CellState.ALIVE)
                .setCellState(new Coordinates(0, 2), CellState.DEAD)
                .setCellState(new Coordinates(1, 2), CellState.ALIVE)
                .setCellState(new Coordinates(2, 2), CellState.DEAD)
                .build();
        verify(gridConsumer).accept(expectedGrid);
        verify(strategy, times(4)).getNextCellState(CellState.DEAD, 5L);
        verify(strategy, times(5)).getNextCellState(CellState.ALIVE, 4L);
    }
}
