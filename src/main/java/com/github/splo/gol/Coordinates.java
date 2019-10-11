package com.github.splo.gol;

import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 2-dimensional coordinates of integers: {@code {x, y}}.
 */
public class Coordinates {

    private final int x;
    private final int y;

    public Coordinates(final int x, final int y) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException(String.format("Coordinates {%d,%d} must be positive", x, y));
        }
        this.x = x;
        this.y = y;
    }

    /**
     * Build a stream that contains all combinations of coordinates from {0,0} to {maxX,maxY}.
     * The order is {0,0} to {maxX,0} then {0,1} to {maxX,1}, etc, until {maxX,maxY}.
     *
     * @param maxX the max x coordinate
     * @param maxY the max y coordinate
     * @return the stream
     */
    public static Stream<Coordinates> combinationsStream(final int maxX, final int maxY) {
        return IntStream.range(0, maxY)
                .boxed()
                .flatMap(y -> IntStream.range(0, maxX).mapToObj(x -> new Coordinates(x, y)));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Coordinates that = (Coordinates) o;
        return x == that.x && y == that.y;
    }

    @Override
    public String toString() {
        return String.format("{%d,%d}", x, y);
    }
}
