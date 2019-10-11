package com.github.splo.gol.api;

import com.github.splo.gol.api.Coordinates;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CoordinatesTest {

    @Test
    void getX() {
        assertThat(new Coordinates(1, 4).getX()).isEqualTo(1);
    }

    @Test
    void getY() {
        assertThat(new Coordinates(2, 3).getY()).isEqualTo(3);
    }

    @Test
    void equals() {
        assertThat(new Coordinates(2, 3)).isEqualTo(new Coordinates(2, 3));
    }

    @Test
    void notEquals() {
        assertThat(new Coordinates(1, 0)).isNotEqualTo(new Coordinates(2, 3));
    }

    @Test
    void negativeCoordinatesForbidden() {
        assertThatThrownBy(() -> new Coordinates(-1, 3)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Coordinates(1, -2)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void combinationsStream() {
        assertThat(Coordinates.combinationsStream(2, 3)).containsExactly(new Coordinates(0, 0),
                new Coordinates(1, 0),
                new Coordinates(0, 1),
                new Coordinates(1, 1),
                new Coordinates(0, 2),
                new Coordinates(1, 2));
    }
}