package com.github.splo.gol;

public interface Strategy {
    CellState getNextCellState(final CellState previousState, final long livingCells);
}
