package com.github.splo.gol.io;

import com.github.splo.gol.api.CellState;
import com.github.splo.gol.api.Coordinates;
import com.github.splo.gol.api.Grid;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GridCodecTest {

    @Test
    void defaultGrid() {
        final var defaultGrid = Grid.newBuilder().build();

        final var encoded = GridCodec.encodeGrid(defaultGrid);
        final var decoded = GridCodec.decodeGrid(encoded);

        assertThat(encoded).isNotEmpty();
        assertThat(decoded).isEqualTo(defaultGrid);
    }

    @Test
    void gridWithData() {
        final var grid = Grid.newBuilder()
                .setWidth(4)
                .setHeight(5)
                .setCellState(new Coordinates(0, 0), CellState.ALIVE)
                .setCellState(new Coordinates(1, 0), CellState.ALIVE)
                .setCellState(new Coordinates(2, 0), CellState.DEAD)
                .setCellState(new Coordinates(3, 0), CellState.ALIVE)
                .setCellState(new Coordinates(0, 1), CellState.ALIVE)
                .setCellState(new Coordinates(1, 1), CellState.DEAD)
                .setCellState(new Coordinates(2, 1), CellState.DEAD)
                .setCellState(new Coordinates(3, 1), CellState.ALIVE)
                .setCellState(new Coordinates(0, 2), CellState.ALIVE)
                .setCellState(new Coordinates(1, 2), CellState.DEAD)
                .setCellState(new Coordinates(2, 2), CellState.DEAD)
                .setCellState(new Coordinates(3, 2), CellState.DEAD)
                .setCellState(new Coordinates(0, 3), CellState.ALIVE)
                .setCellState(new Coordinates(1, 3), CellState.ALIVE)
                .setCellState(new Coordinates(2, 3), CellState.DEAD)
                .setCellState(new Coordinates(3, 3), CellState.DEAD)
                .setCellState(new Coordinates(0, 4), CellState.DEAD)
                .setCellState(new Coordinates(1, 4), CellState.DEAD)
                .setCellState(new Coordinates(2, 4), CellState.ALIVE)
                .setCellState(new Coordinates(3, 4), CellState.ALIVE)
                .build();

        final var encoded = GridCodec.encodeGrid(grid);
        final var decoded = GridCodec.decodeGrid(encoded);

        assertThat(encoded).isNotEmpty();
        assertThat(decoded).isEqualTo(grid);
    }
}
