package com.github.splo.gol.api;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * The main Game of life class that stores the current grid. It can successively compute the next generation of cells
 * of its grid according to a {@link Strategy} and report the newly generated grid to a listener.
 */
public class GameOfLife {

    private final Strategy strategy;
    private final Consumer<Grid> listener;
    private Grid grid;

    public GameOfLife(final Strategy strategy, final Consumer<Grid> listener, final Grid initialGrid) {
        this.strategy = strategy;
        this.listener = listener;
        this.grid = initialGrid;
    }

    public void computeNextGrid() {
        final Grid.Builder gridBuilder = Grid.newBuilder().setWidth(grid.getWidth()).setHeight(grid.getHeight());
        Coordinates.combinationsStream(grid.getWidth(), grid.getHeight())
                .forEach(coordinates -> gridBuilder.setCellState(coordinates, getNextCellState(grid, coordinates)));
        grid = gridBuilder.build();
        listener.accept(grid);
    }

    private CellState getNextCellState(final Grid grid, final Coordinates coordinates) {
        final var x = coordinates.getX();
        final var y = coordinates.getY();
        final var topLeft = getCellState(grid, x - 1, y - 1);
        final var top = getCellState(grid, x, y - 1);
        final var topRight = getCellState(grid, x + 1, y - 1);
        final var left = getCellState(grid, x - 1, y);
        final var center = getCellState(grid, x, y);
        final var right = getCellState(grid, x + 1, y);
        final var bottomLeft = getCellState(grid, x - 1, y + 1);
        final var bottom = getCellState(grid, x, y + 1);
        final var bottomRight = getCellState(grid, x + 1, y + 1);
        // Center cell is intentionally left out of the considered cells for the count.
        final long livingCells = Stream.of(topLeft, top, topRight, left, right, bottomLeft, bottom, bottomRight)
                .filter(cellState -> cellState == CellState.ALIVE)
                .count();
        return strategy.getNextCellState(center, livingCells);
    }

    private static CellState getCellState(final Grid grid, int x, int y) {
        // Wrap coordinates that are out of bounds (for example `-1` becomes `size-1`).
        x = x >= grid.getWidth() ? x - grid.getWidth() : x;
        x = x < 0 ? x + grid.getWidth() : x;
        y = y >= grid.getHeight() ? y - grid.getHeight() : y;
        y = y < 0 ? y + grid.getHeight() : y;
        return grid.getCellState(new Coordinates(x, y));
    }
}
