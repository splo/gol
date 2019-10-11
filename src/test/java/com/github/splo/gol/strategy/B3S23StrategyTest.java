package com.github.splo.gol.strategy;

import com.github.splo.gol.api.CellState;
import com.github.splo.gol.strategy.B3S23Strategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class B3S23StrategyTest {

    private B3S23Strategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new B3S23Strategy();
    }

    @Test
    void bornConditions() {
        assertThat(strategy.getNextCellState(CellState.DEAD, 3)).isEqualTo(CellState.ALIVE);
    }

    @Test
    void stayDeadConditions() {
        assertThat(strategy.getNextCellState(CellState.DEAD, 2)).isEqualTo(CellState.DEAD);
        assertThat(strategy.getNextCellState(CellState.DEAD, 4)).isEqualTo(CellState.DEAD);
    }

    @Test
    void stayAliveConditions() {
        assertThat(strategy.getNextCellState(CellState.ALIVE, 2)).isEqualTo(CellState.ALIVE);
        assertThat(strategy.getNextCellState(CellState.ALIVE, 3)).isEqualTo(CellState.ALIVE);
    }

    @Test
    void deathConditions() {
        assertThat(strategy.getNextCellState(CellState.ALIVE, 1)).isEqualTo(CellState.DEAD);
        assertThat(strategy.getNextCellState(CellState.ALIVE, 4)).isEqualTo(CellState.DEAD);
    }
}