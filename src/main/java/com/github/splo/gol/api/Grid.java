package com.github.splo.gol.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * A 2-dimensional grid of cells. Use {@link #newBuilder()} to construct an instance. Default width and height are 3.
 */
public class Grid {

    private final int width;
    private final int height;
    private final CellState[][] cells;

    private Grid(final Builder builder) {
        if (builder.width < 3 || builder.height < 3) {
            throw new IllegalArgumentException(String.format("Width and height [%d,%d] must be greater than 2",
                    builder.width,
                    builder.height));
        }
        this.width = builder.width;
        this.height = builder.height;
        this.cells = new CellState[height][width];
        builder.cells.forEach((coordinates, cellState) -> {
            if (coordinates.getX() >= width || coordinates.getY() >= height) {
                throw new IllegalArgumentException(String.format("Coordinates %s must be within the [%d,%d] bounds",
                        coordinates,
                        width,
                        height));
            }
            cells[coordinates.getY()][coordinates.getX()] = cellState;
        });
        Coordinates.combinationsStream(width, height).forEach(coordinates -> {
            if (cells[coordinates.getY()][coordinates.getX()] == null) {
                cells[coordinates.getY()][coordinates.getX()] = CellState.DEAD;
            }
        });
    }

    public CellState getCellState(final Coordinates coordinates) {
        return cells[coordinates.getY()][coordinates.getX()];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {

        private int width;
        private int height;
        private Map<Coordinates, CellState> cells;

        private Builder() {
            this.width = 3;
            this.height = 3;
            this.cells = new HashMap<>();
        }

        public Builder setWidth(final int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(final int height) {
            this.height = height;
            return this;
        }

        public Builder setCellState(final Coordinates coordinates, final CellState cellState) {
            this.cells.put(coordinates, cellState);
            return this;
        }

        public Grid build() {
            return new Grid(this);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Grid grid = (Grid) o;
        return width == grid.width && height == grid.height && Arrays.deepEquals(cells, grid.cells);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(width, height);
        result = 31 * result + Arrays.hashCode(cells);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        IntStream.range(-1, height + 1)
                .boxed()
                .flatMap(y -> IntStream.range(-1, width + 1).mapToObj(x -> new int[] {x, y}))
                .forEach(pair -> {
                    final int x = pair[0];
                    final int y = pair[1];
                    if (x < 0 && y < 0) {
                        sb.append('┌');
                    } else if (x >= width && y >= height) {
                        sb.append('┘');
                    } else if ((x >= width) && (y < 0)) {
                        sb.append('┐');
                    } else if ((x < 0) && (y >= height)) {
                        sb.append('└');
                    } else if (x < 0 || x >= width) {
                        sb.append('│');
                    } else if (y < 0 || y >= height) {
                        sb.append('─');
                    } else {
                        sb.append(this.getCellState(new Coordinates(x, y)) == CellState.ALIVE ? "x" : " ");
                    }
                    if (x >= width) {
                        sb.append('\n');
                    }
                });
        return sb.toString();
    }
}
