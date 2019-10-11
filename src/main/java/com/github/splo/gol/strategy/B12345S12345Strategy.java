package com.github.splo.gol.strategy;

import com.github.splo.gol.api.CellState;
import com.github.splo.gol.api.Strategy;

/**
 * A strategy where a cell is born or survives if there are between 1 and 5 living cells around.
 */
public class B12345S12345Strategy implements Strategy {
    @Override
    public CellState getNextCellState(final CellState previousState, final long livingCells) {
        if (livingCells >= 1 && livingCells <= 5) {
            return CellState.ALIVE;
        } else {
            return CellState.DEAD;
        }
    }
}
