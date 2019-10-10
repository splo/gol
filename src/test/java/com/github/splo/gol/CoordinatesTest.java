package com.github.splo.gol;

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
}