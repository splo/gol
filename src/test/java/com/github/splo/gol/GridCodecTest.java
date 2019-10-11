package com.github.splo.gol;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GridCodecTest {

    @Test
    void defaultGrid() {
        final Grid defaultGrid = Grid.newBuilder().build();

        final byte[] encoded = GridCodec.encodeGrid(defaultGrid);
        final Grid decoded = GridCodec.decodeGrid(encoded);

        assertThat(encoded).isNotEmpty();
        assertThat(decoded).isEqualTo(defaultGrid);
    }

    @Test
    void gridWithData() {
        final Grid grid = Grid.newBuilder()
                .setWidth(4)
                .setHeight(4)
                .setCellState(new Coordinates(0, 0), CellState.ALIVE)
                .setCellState(new Coordinates(1, 0), CellState.ALIVE)
                .setCellState(new Coordinates(2, 0), CellState.ALIVE)
                .setCellState(new Coordinates(3, 0), CellState.ALIVE)
                .setCellState(new Coordinates(0, 1), CellState.ALIVE)
                .setCellState(new Coordinates(1, 1), CellState.ALIVE)
                .setCellState(new Coordinates(2, 1), CellState.ALIVE)
                .setCellState(new Coordinates(3, 1), CellState.ALIVE)
                .setCellState(new Coordinates(0, 2), CellState.ALIVE)
                .setCellState(new Coordinates(1, 2), CellState.ALIVE)
                .setCellState(new Coordinates(2, 2), CellState.ALIVE)
                .setCellState(new Coordinates(3, 2), CellState.ALIVE)
                .setCellState(new Coordinates(0, 3), CellState.ALIVE)
                .setCellState(new Coordinates(1, 3), CellState.ALIVE)
                .setCellState(new Coordinates(2, 3), CellState.ALIVE)
                .setCellState(new Coordinates(3, 3), CellState.ALIVE)
                .build();

        final byte[] encoded = GridCodec.encodeGrid(grid);
        final Grid decoded = GridCodec.decodeGrid(encoded);

        assertThat(encoded).isNotEmpty();
        assertThat(decoded).isEqualTo(grid);
    }
}
