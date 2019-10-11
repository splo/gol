package com.github.splo.gol;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GridTest {

    @Test
    void getWidth() {
        final Grid grid = Grid.newBuilder().setWidth(9).build();

        assertThat(grid.getWidth()).isEqualTo(9);
    }

    @Test
    void getHeight() {
        final Grid grid = Grid.newBuilder().setHeight(7).build();

        assertThat(grid.getHeight()).isEqualTo(7);
    }

    @Test
    void defaultWidth() {
        final Grid grid = Grid.newBuilder().build();

        assertThat(grid.getWidth()).isEqualTo(3);
    }

    @Test
    void defaultHeight() {
        final Grid grid = Grid.newBuilder().build();

        assertThat(grid.getHeight()).isEqualTo(3);
    }

    @Test
    void getCellState() {
        final Grid grid = Grid.newBuilder()
                .setWidth(3)
                .setHeight(3)
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

        assertThat(grid.getCellState(new Coordinates(0, 0))).isEqualTo(CellState.ALIVE);
        assertThat(grid.getCellState(new Coordinates(1, 0))).isEqualTo(CellState.DEAD);
        assertThat(grid.getCellState(new Coordinates(2, 0))).isEqualTo(CellState.ALIVE);
        assertThat(grid.getCellState(new Coordinates(0, 1))).isEqualTo(CellState.DEAD);
        assertThat(grid.getCellState(new Coordinates(1, 1))).isEqualTo(CellState.ALIVE);
        assertThat(grid.getCellState(new Coordinates(2, 1))).isEqualTo(CellState.DEAD);
        assertThat(grid.getCellState(new Coordinates(0, 2))).isEqualTo(CellState.ALIVE);
        assertThat(grid.getCellState(new Coordinates(1, 2))).isEqualTo(CellState.DEAD);
        assertThat(grid.getCellState(new Coordinates(2, 2))).isEqualTo(CellState.ALIVE);
    }

    @Test
    void getCellStateOutOfBounds() {
        final Grid grid = Grid.newBuilder()
                .setWidth(3)
                .setHeight(3)
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

        assertThatThrownBy(() -> grid.getCellState(new Coordinates(4, 0))) // 4 is out of bounds.
                .isInstanceOf(ArrayIndexOutOfBoundsException.class);
        assertThatThrownBy(() -> grid.getCellState(new Coordinates(1, 3))) // 3 is out of bounds.
                .isInstanceOf(ArrayIndexOutOfBoundsException.class);
    }

    @Test
    void getCellStateIsDeadByDefault() {
        final Grid grid = Grid.newBuilder().build();

        assertThat(grid.getCellState(new Coordinates(0, 0))).isEqualTo(CellState.DEAD);
        assertThat(grid.getCellState(new Coordinates(1, 0))).isEqualTo(CellState.DEAD);
        assertThat(grid.getCellState(new Coordinates(2, 0))).isEqualTo(CellState.DEAD);
        assertThat(grid.getCellState(new Coordinates(0, 1))).isEqualTo(CellState.DEAD);
        assertThat(grid.getCellState(new Coordinates(1, 1))).isEqualTo(CellState.DEAD);
        assertThat(grid.getCellState(new Coordinates(2, 1))).isEqualTo(CellState.DEAD);
        assertThat(grid.getCellState(new Coordinates(0, 2))).isEqualTo(CellState.DEAD);
        assertThat(grid.getCellState(new Coordinates(1, 2))).isEqualTo(CellState.DEAD);
        assertThat(grid.getCellState(new Coordinates(2, 2))).isEqualTo(CellState.DEAD);
    }
}
