package com.github.splo.gol.strategy;

import com.github.splo.gol.api.CellState;
import com.github.splo.gol.strategy.B12345S12345Strategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class B12345S12345StrategyTest {

    private B12345S12345Strategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new B12345S12345Strategy();
    }

    @Test
    void bornConditions() {
        assertThat(strategy.getNextCellState(CellState.DEAD, 1)).isEqualTo(CellState.ALIVE);
        assertThat(strategy.getNextCellState(CellState.DEAD, 2)).isEqualTo(CellState.ALIVE);
        assertThat(strategy.getNextCellState(CellState.DEAD, 3)).isEqualTo(CellState.ALIVE);
        assertThat(strategy.getNextCellState(CellState.DEAD, 4)).isEqualTo(CellState.ALIVE);
        assertThat(strategy.getNextCellState(CellState.DEAD, 5)).isEqualTo(CellState.ALIVE);
    }

    @Test
    void stayDeadConditions() {
        assertThat(strategy.getNextCellState(CellState.DEAD, 0)).isEqualTo(CellState.DEAD);
        assertThat(strategy.getNextCellState(CellState.DEAD, 6)).isEqualTo(CellState.DEAD);
        assertThat(strategy.getNextCellState(CellState.DEAD, 7)).isEqualTo(CellState.DEAD);
        assertThat(strategy.getNextCellState(CellState.DEAD, 8)).isEqualTo(CellState.DEAD);
    }

    @Test
    void stayAliveConditions() {
        assertThat(strategy.getNextCellState(CellState.ALIVE, 1)).isEqualTo(CellState.ALIVE);
        assertThat(strategy.getNextCellState(CellState.ALIVE, 2)).isEqualTo(CellState.ALIVE);
        assertThat(strategy.getNextCellState(CellState.ALIVE, 3)).isEqualTo(CellState.ALIVE);
        assertThat(strategy.getNextCellState(CellState.ALIVE, 4)).isEqualTo(CellState.ALIVE);
        assertThat(strategy.getNextCellState(CellState.ALIVE, 5)).isEqualTo(CellState.ALIVE);
    }

    @Test
    void deathConditions() {
        assertThat(strategy.getNextCellState(CellState.ALIVE, 0)).isEqualTo(CellState.DEAD);
        assertThat(strategy.getNextCellState(CellState.ALIVE, 6)).isEqualTo(CellState.DEAD);
        assertThat(strategy.getNextCellState(CellState.ALIVE, 7)).isEqualTo(CellState.DEAD);
        assertThat(strategy.getNextCellState(CellState.ALIVE, 8)).isEqualTo(CellState.DEAD);
    }
}