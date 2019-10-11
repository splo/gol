package com.github.splo.gol.strategy;

import com.github.splo.gol.api.CellState;
import com.github.splo.gol.api.Strategy;

/**
 * A strategy where a cell is born when there are exactly 3 living cells around, and a living cell survives if there
 * are 2 or 3 living cells around.
 */
public class B3S23Strategy implements Strategy {
    @Override
    public CellState getNextCellState(final CellState previousState, final long livingCells) {
        if ((previousState == CellState.DEAD && livingCells == 3) ||
                (previousState == CellState.ALIVE && livingCells >= 2 && livingCells <= 3)) {
            return CellState.ALIVE;
        } else {
            return CellState.DEAD;
        }
    }
}
