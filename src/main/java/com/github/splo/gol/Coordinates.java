package com.github.splo.gol;

import java.util.Objects;

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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return String.format("{%d,%d}", x, y);
    }
}
